package net.sf.l2j.gameserver.network.clientpackets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map.Entry;
import java.util.logging.Level;

import net.sf.l2j.Config;
import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.communitybbs.Manager.MailBBSManager;
import net.sf.l2j.gameserver.data.SkillTable;
import net.sf.l2j.gameserver.data.SkillTable.FrequentSkill;
import net.sf.l2j.gameserver.data.manager.CoupleManager;
import net.sf.l2j.gameserver.data.xml.AdminData;
import net.sf.l2j.gameserver.data.xml.AnnouncementData;
import net.sf.l2j.gameserver.data.xml.MapRegionData.TeleportType;
import net.sf.l2j.gameserver.events.ArenaTask;
import net.sf.l2j.gameserver.events.dynamiczones.PvpZoneManager;
import net.sf.l2j.gameserver.events.partyfarm.PartyFarm;
import net.sf.l2j.gameserver.instancemanager.CastleManager;
import net.sf.l2j.gameserver.instancemanager.ClanHallManager;
import net.sf.l2j.gameserver.instancemanager.DimensionalRiftManager;
import net.sf.l2j.gameserver.instancemanager.PetitionManager;
import net.sf.l2j.gameserver.instancemanager.SevenSigns;
import net.sf.l2j.gameserver.instancemanager.SevenSigns.CabalType;
import net.sf.l2j.gameserver.instancemanager.SevenSigns.SealType;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.World;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.instance.ClassMaster;
import net.sf.l2j.gameserver.model.base.ClassRace;
import net.sf.l2j.gameserver.model.entity.Castle;
import net.sf.l2j.gameserver.model.entity.ClanHall;
import net.sf.l2j.gameserver.model.entity.Siege;
import net.sf.l2j.gameserver.model.entity.Siege.SiegeSide;
import net.sf.l2j.gameserver.model.holder.IntIntHolder;
import net.sf.l2j.gameserver.model.olympiad.Olympiad;
import net.sf.l2j.gameserver.model.pledge.Clan;
import net.sf.l2j.gameserver.model.pledge.Clan.SubPledge;
import net.sf.l2j.gameserver.model.zone.ZoneId;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.network.serverpackets.Die;
import net.sf.l2j.gameserver.network.serverpackets.EtcStatusUpdate;
import net.sf.l2j.gameserver.network.serverpackets.ExMailArrived;
import net.sf.l2j.gameserver.network.serverpackets.ExRedSky;
import net.sf.l2j.gameserver.network.serverpackets.ExShowScreenMessage;
import net.sf.l2j.gameserver.network.serverpackets.ExStorageMaxCount;
import net.sf.l2j.gameserver.network.serverpackets.FriendList;
import net.sf.l2j.gameserver.network.serverpackets.HennaInfo;
import net.sf.l2j.gameserver.network.serverpackets.ItemList;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.PlaySound;
import net.sf.l2j.gameserver.network.serverpackets.PledgeShowMemberListAll;
import net.sf.l2j.gameserver.network.serverpackets.PledgeShowMemberListUpdate;
import net.sf.l2j.gameserver.network.serverpackets.PledgeSkillList;
import net.sf.l2j.gameserver.network.serverpackets.PledgeStatusChanged;
import net.sf.l2j.gameserver.network.serverpackets.QuestList;
import net.sf.l2j.gameserver.network.serverpackets.ShortCutInit;
import net.sf.l2j.gameserver.network.serverpackets.SkillCoolTime;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.network.serverpackets.UserInfo;
import net.sf.l2j.gameserver.scripting.Quest;
import net.sf.l2j.gameserver.scripting.QuestState;
import net.sf.l2j.gameserver.scripting.ScriptManager;
import net.sf.l2j.gameserver.taskmanager.GameTimeTaskManager;

import enginemods.EngineModsManager;

public class EnterWorld extends L2GameClientPacket
{
	private static final String LOAD_PLAYER_QUESTS = "SELECT name,var,value FROM character_quests WHERE charId=?";
	
	@Override
	protected void readImpl()
	{
	}
	
	@Override
	protected void runImpl()
	{
		final Player activeChar = getClient().getActiveChar();
		if (activeChar == null)
		{
			_log.warning("EnterWorld failed! activeChar is null...");
			getClient().closeNow();
			return;
		}
		
		final int objectId = activeChar.getObjectId();
		
		if (activeChar.isGM())
		{
			if (Config.GM_STARTUP_INVULNERABLE && AdminData.getInstance().hasAccess("admin_invul", activeChar.getAccessLevel()))
				activeChar.setIsInvul(true);
			
			if (Config.GM_STARTUP_INVISIBLE && AdminData.getInstance().hasAccess("admin_hide", activeChar.getAccessLevel()))
				activeChar.getAppearance().setInvisible();
			
			if (Config.GM_STARTUP_SILENCE && AdminData.getInstance().hasAccess("admin_silence", activeChar.getAccessLevel()))
				activeChar.setInRefusalMode(true);
			
			if (Config.GM_STARTUP_AUTO_LIST && AdminData.getInstance().hasAccess("admin_gmlist", activeChar.getAccessLevel()))
				AdminData.getInstance().addGm(activeChar, false);
			
			if (Config.GM_SUPER_HASTE)
				SkillTable.getInstance().getInfo(7029, 4).getEffects(activeChar, activeChar);			
			
			else
				AdminData.getInstance().addGm(activeChar, true);
		}
		
		// Set dead status if applies
		if (activeChar.getCurrentHp() < 0.5) {
			activeChar.setIsDead(true);
			
			//effect on die
			if(Config.ENABLE_EFFECT_ON_DIE) {				 
				// Make Sky Red For 7 Seconds.
				ExRedSky packet = new ExRedSky(7);
				sendPacket(packet);
			}
		
		}	
			
		// Clan checks.
		final Clan clan = activeChar.getClan();
		if (clan != null)
		{
			activeChar.sendPacket(new PledgeSkillList(clan));
			
			// Refresh player instance.
			clan.getClanMember(objectId).setPlayerInstance(activeChar);
			
			final SystemMessage msg = SystemMessage.getSystemMessage(SystemMessageId.CLAN_MEMBER_S1_LOGGED_IN).addCharName(activeChar);
			final PledgeShowMemberListUpdate update = new PledgeShowMemberListUpdate(activeChar);
			
			// Send packets to others members.
			for (Player member : clan.getOnlineMembers())
			{
				if (member == activeChar)
					continue;
				
				member.sendPacket(msg);
				member.sendPacket(update);
			}
			
			// Send a login notification to sponsor or apprentice, if logged.
			if (activeChar.getSponsor() != 0)
			{
				final Player sponsor = World.getInstance().getPlayer(activeChar.getSponsor());
				if (sponsor != null)
					sponsor.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.YOUR_APPRENTICE_S1_HAS_LOGGED_IN).addCharName(activeChar));
			}
			else if (activeChar.getApprentice() != 0)
			{
				final Player apprentice = World.getInstance().getPlayer(activeChar.getApprentice());
				if (apprentice != null)
					apprentice.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.YOUR_SPONSOR_S1_HAS_LOGGED_IN).addCharName(activeChar));
			}
			
			// Add message at connexion if clanHall not paid.
			final ClanHall clanHall = ClanHallManager.getInstance().getClanHallByOwner(clan);
			if (clanHall != null && !clanHall.getPaid())
				activeChar.sendPacket(SystemMessageId.PAYMENT_FOR_YOUR_CLAN_HALL_HAS_NOT_BEEN_MADE_PLEASE_MAKE_PAYMENT_TO_YOUR_CLAN_WAREHOUSE_BY_S1_TOMORROW);
			
			for (Castle castle : CastleManager.getInstance().getCastles())
			{
				final Siege siege = castle.getSiege();
				if (!siege.isInProgress())
					continue;
				
				final SiegeSide type = siege.getSide(clan);
				if (type == SiegeSide.ATTACKER)
					activeChar.setSiegeState((byte) 1);
				else if (type == SiegeSide.DEFENDER || type == SiegeSide.OWNER)
					activeChar.setSiegeState((byte) 2);
			}
			
			activeChar.sendPacket(new PledgeShowMemberListAll(clan, 0));
			
			for (SubPledge sp : clan.getAllSubPledges())
				activeChar.sendPacket(new PledgeShowMemberListAll(clan, sp.getId()));
			
			activeChar.sendPacket(new UserInfo(activeChar));
			activeChar.sendPacket(new PledgeStatusChanged(clan));
		}
		
		// Updating Seal of Strife Buff/Debuff
		if (SevenSigns.getInstance().isSealValidationPeriod() && SevenSigns.getInstance().getSealOwner(SealType.STRIFE) != CabalType.NORMAL)
		{
			CabalType cabal = SevenSigns.getInstance().getPlayerCabal(objectId);
			if (cabal != CabalType.NORMAL)
			{
				if (cabal == SevenSigns.getInstance().getSealOwner(SealType.STRIFE))
					activeChar.addSkill(FrequentSkill.THE_VICTOR_OF_WAR.getSkill(), false);
				else
					activeChar.addSkill(FrequentSkill.THE_VANQUISHED_OF_WAR.getSkill(), false);
			}
		}
		else
		{
			activeChar.removeSkill(FrequentSkill.THE_VICTOR_OF_WAR.getSkill().getId(), false);
			activeChar.removeSkill(FrequentSkill.THE_VANQUISHED_OF_WAR.getSkill().getId(), false);
		}
		
		if (Config.PLAYER_SPAWN_PROTECTION > 0)
			activeChar.setSpawnProtection(true);
		
		activeChar.spawnMe();
		
		// Engage and notify partner.
		if (Config.ALLOW_WEDDING)
		{
			for (Entry<Integer, IntIntHolder> coupleEntry : CoupleManager.getInstance().getCouples().entrySet())
			{
				final IntIntHolder couple = coupleEntry.getValue();
				if (couple.getId() == objectId || couple.getValue() == objectId)
				{
					activeChar.setCoupleId(coupleEntry.getKey());
					break;
				}
			}
		}
		
		if (ArenaTask.is_started() && Config.ARENA_MESSAGE_ENABLED)
			activeChar.sendPacket(new ExShowScreenMessage(Config.ARENA_MESSAGE_TEXT, Config.ARENA_MESSAGE_TIME, 2, true));

		// CHECK EQUIPMENT PVP RESTRICTION
		activeChar.checkEquipmentXPvps();
		
		// Announcements, welcome & Seven signs period messages
		activeChar.sendPacket(SystemMessageId.WELCOME_TO_LINEAGE);
		
		activeChar.loadAutoFarmSettings();
		
		if(activeChar.isSummonAttack()) {
			activeChar.sendPacket(new SystemMessage(SystemMessageId.ACTIVATE_SUMMON_ACTACK));			
		}
		
		if(activeChar.isAntiKsProtected()) {
			activeChar.sendPacket(new SystemMessage(SystemMessageId.ACTIVATE_RESPECT_HUNT));
		}
		
		activeChar.sendPacket(SevenSigns.getInstance().getCurrentPeriod().getMessageId());
		AnnouncementData.getInstance().showAnnouncements(activeChar, false);
		
		if(Config.PCB_ENABLE)
		{
			activeChar.showPcBangWindow();
		}
		
		// if player is DE, check for shadow sense skill at night
		if (activeChar.getRace() == ClassRace.DARK_ELF && activeChar.hasSkill(L2Skill.SKILL_SHADOW_SENSE))
			activeChar.sendPacket(SystemMessage.getSystemMessage((GameTimeTaskManager.getInstance().isNight()) ? SystemMessageId.NIGHT_S1_EFFECT_APPLIES : SystemMessageId.DAY_S1_EFFECT_DISAPPEARS).addSkillName(L2Skill.SKILL_SHADOW_SENSE));
		
		activeChar.getMacroses().sendUpdate();
		activeChar.sendPacket(new UserInfo(activeChar));
		activeChar.sendPacket(new HennaInfo(activeChar));
		activeChar.sendPacket(new FriendList(activeChar));
		// activeChar.queryGameGuard();
		activeChar.sendPacket(new ItemList(activeChar, false));
		activeChar.sendPacket(new ShortCutInit(activeChar));
		activeChar.sendPacket(new ExStorageMaxCount(activeChar));
		
		// no broadcast needed since the player will already spawn dead to others
		if (activeChar.isAlikeDead())
			activeChar.sendPacket(new Die(activeChar));
		
		activeChar.updateEffectIcons();
		activeChar.sendPacket(new EtcStatusUpdate(activeChar));
		activeChar.sendSkillList();
		
		// Load quests.
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement(LOAD_PLAYER_QUESTS);
			statement.setInt(1, objectId);
			
			ResultSet rs = statement.executeQuery();
			while (rs.next())
			{
				final String questName = rs.getString("name");
				
				// Test quest existence.
				final Quest quest = ScriptManager.getInstance().getQuest(questName);
				if (quest == null)
				{
					_log.warning("Quest: Unknown quest " + questName + " for player " + activeChar.getName());
					continue;
				}
				
				// Each quest get a single state ; create one QuestState per found <state> variable.
				final String var = rs.getString("var");
				if (var.equals("<state>"))
				{
					new QuestState(activeChar, quest, rs.getByte("value"));
					
					// Notify quest for enterworld event, if quest allows it.
					if (quest.getOnEnterWorld())
						quest.notifyEnterWorld(activeChar);
				}
				// Feed an existing quest state.
				else
				{
					final QuestState qs = activeChar.getQuestState(questName);
					if (qs == null)
					{
						_log.warning("Quest: Unknown quest state " + questName + " for player " + activeChar.getName());
						continue;
					}
					
					qs.setInternal(var, rs.getString("value"));
				}
			}
			rs.close();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Quest: could not insert char quest:", e);
		}
		
		activeChar.sendPacket(new QuestList(activeChar));
		
		// Unread mails make a popup appears.
		if (Config.ENABLE_COMMUNITY_BOARD && MailBBSManager.getInstance().checkUnreadMail(activeChar) > 0)
		{
			activeChar.sendPacket(SystemMessageId.NEW_MAIL);
			activeChar.sendPacket(new PlaySound("systemmsg_e.1233"));
			activeChar.sendPacket(ExMailArrived.STATIC_PACKET);
		}
		
		// Clan notice, if active.
		if (Config.ENABLE_COMMUNITY_BOARD && clan != null && clan.isNoticeEnabled())
		{
			final NpcHtmlMessage html = new NpcHtmlMessage(0);
			html.setFile("data/html/clan_notice.htm");
			html.replace("%clan_name%", clan.getName());
			html.replace("%notice_text%", clan.getNotice().replaceAll("\r\n", "<br>").replaceAll("action", "").replaceAll("bypass", ""));
			sendPacket(html);
		}
		else if (Config.SERVER_NEWS)
		{
			final NpcHtmlMessage html = new NpcHtmlMessage(0);
			html.setFile("data/html/servnews.htm");
			sendPacket(html);
		}
		
		PetitionManager.getInstance().checkPetitionMessages(activeChar);
		
		activeChar.onPlayerEnter();
		
		sendPacket(new SkillCoolTime(activeChar));
		
		// If player logs back in a stadium, port him in nearest town.
		if (Olympiad.getInstance().playerInStadia(activeChar))
			activeChar.teleToLocation(TeleportType.TOWN);
		
		if (DimensionalRiftManager.getInstance().checkIfInRiftZone(activeChar.getX(), activeChar.getY(), activeChar.getZ(), false))
			DimensionalRiftManager.getInstance().teleportToWaitingRoom(activeChar);
		
		if (activeChar.getClanJoinExpiryTime() > System.currentTimeMillis())
			activeChar.sendPacket(SystemMessageId.CLAN_MEMBERSHIP_TERMINATED);
		
		// Attacker or spectator logging into a siege zone will be ported at town.
		if (!activeChar.isGM() && (!activeChar.isInSiege() || activeChar.getSiegeState() < 2) && activeChar.isInsideZone(ZoneId.SIEGE))
			activeChar.teleToLocation(TeleportType.TOWN);
		
		EngineModsManager.onEnterWorld(activeChar);
		
		if (PartyFarm.is_started() && Config.PARTY_FARM_BY_TIME_OF_DAY) {
			activeChar.sendPacket(new CreatureSay(0, Say2.HERO_VOICE, "[Party Farm] : ", Config.PARTY_FARM_MESSAGE_TEXT));
			activeChar.sendPacket(new ExShowScreenMessage(Config.PARTY_FARM_MESSAGE_TEXT, 3));
		}
		
		if(Config.ENABLE_DYNAMIC_PVP_ZONES) {
			activeChar.sendPacket(new CreatureSay(0, Say2.SHOUT, "[Dynamic PvP Zone] Current zone: ", PvpZoneManager.getInstance().getCurrentZone().getName()));
		}
		
		ClassMaster.showQuestionMark(activeChar);
		
		activeChar.sendPacket(ActionFailed.STATIC_PACKET);
	}
		
	
	@Override
	protected boolean triggersOnActionRequest()
	{
		return false;
	}
}