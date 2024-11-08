package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.data.SkillTable;
import net.sf.l2j.gameserver.data.xml.SkillTreeData;
import net.sf.l2j.gameserver.data.xml.SpellbookData;
import net.sf.l2j.gameserver.model.L2ShortCut;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.instance.Fisherman;
import net.sf.l2j.gameserver.model.actor.instance.Folk;
import net.sf.l2j.gameserver.model.actor.instance.VillageMaster;
import net.sf.l2j.gameserver.model.holder.skillnode.ClanSkillNode;
import net.sf.l2j.gameserver.model.holder.skillnode.FishingSkillNode;
import net.sf.l2j.gameserver.model.holder.skillnode.GeneralSkillNode;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ExStorageMaxCount;
import net.sf.l2j.gameserver.network.serverpackets.ShortCutRegister;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

public class RequestAcquireSkill extends L2GameClientPacket
{
	private int _skillId;
	private int _skillLevel;
	private int _skillType;
	
	@Override
	protected void readImpl()
	{
		_skillId = readD();
		_skillLevel = readD();
		_skillType = readD();
	}
	
	@Override
	protected void runImpl()
	{
		// Not valid skill data, return.
		if (_skillId <= 0 || _skillLevel <= 0)
			return;
		
		// Incorrect player, return.
		final Player player = getClient().getActiveChar();
		if (player == null)
			return;
		
		// Incorrect npc, return.
		final Folk folk = player.getCurrentFolk();
		if (folk == null || !folk.canInteract(player))
			return;
		
		// Skill doesn't exist, return.
		final L2Skill skill = SkillTable.getInstance().getInfo(_skillId, _skillLevel);
		if (skill == null)
			return;
		
		switch (_skillType)
		{
			// General skills.
			case 0:
				// Player already has such skill with same or higher level.
				int skillLvl = player.getSkillLevel(_skillId);
				if (skillLvl >= _skillLevel)
					return;
				
				// Requested skill must be 1 level higher than existing skill.
				if (skillLvl != _skillLevel - 1)
					return;
				
				// Search if the asked skill exists on player template.
				final GeneralSkillNode gsn = player.getTemplate().findSkill(_skillId, _skillLevel);
				if (gsn == null)
					return;
				
				// Not enought SP.
				if (player.getSp() < gsn.getCorrectedCost())
				{
					player.sendPacket(SystemMessageId.NOT_ENOUGH_SP_TO_LEARN_SKILL);
					folk.showSkillList(player);
					return;
				}
				
				// Get spellbook and try to consume it.
				final int bookId = SpellbookData.getInstance().getBookForSkill(_skillId, _skillLevel);
				if (bookId > 0 && !player.destroyItemByItemId("SkillLearn", bookId, 1, folk, true))
				{
					player.sendPacket(SystemMessageId.ITEM_MISSING_TO_LEARN_SKILL);
					folk.showSkillList(player);
					return;
				}
				
				// Consume SP.
				player.removeExpAndSp(0, gsn.getCorrectedCost());
				
				// Add skill new skill.
				player.addSkill(skill, true);
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.LEARNED_SKILL_S1).addSkillName(skill));
				
				// Update player and return.
				updateShortCuts(player);
				player.sendSkillList();
				folk.showSkillList(player);
				break;
			
			// Common skills.
			case 1:
				// Player already has such skill with same or higher level.
				skillLvl = player.getSkillLevel(_skillId);
				if (skillLvl >= _skillLevel)
					return;
				
				// Requested skill must be 1 level higher than existing skill.
				if (skillLvl != _skillLevel - 1)
					return;
				
				final FishingSkillNode fsn = SkillTreeData.getInstance().getFishingSkillFor(player, _skillId, _skillLevel);
				if (fsn == null)
					return;
				
				if (!player.destroyItemByItemId("Consume", fsn.getItemId(), fsn.getItemCount(), folk, true))
				{
					player.sendPacket(SystemMessageId.ITEM_MISSING_TO_LEARN_SKILL);
					Fisherman.showFishSkillList(player);
					return;
				}
				
				player.addSkill(skill, true);
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.LEARNED_SKILL_S1).addSkillName(skill));
				
				if (_skillId >= 1368 && _skillId <= 1372)
					player.sendPacket(new ExStorageMaxCount(player));
				
				updateShortCuts(player);
				player.sendSkillList();
				Fisherman.showFishSkillList(player);
				break;
			
			// Pledge skills.
			case 2:
				if (!player.isClanLeader())
					return;
				
				final ClanSkillNode csn = SkillTreeData.getInstance().getClanSkillFor(player, _skillId, _skillLevel);
				if (csn == null)
					return;
				
				if (player.getClan().getReputationScore() < csn.getCost())
				{
					player.sendPacket(SystemMessageId.ACQUIRE_SKILL_FAILED_BAD_CLAN_REP_SCORE);
					VillageMaster.showPledgeSkillList(player);
					return;
				}
				
				if (Config.LIFE_CRYSTAL_NEEDED && !player.destroyItemByItemId("Consume", csn.getItemId(), csn.getItemCount(), folk, true))
				{
					player.sendPacket(SystemMessageId.ITEM_MISSING_TO_LEARN_SKILL);
					VillageMaster.showPledgeSkillList(player);
					return;
				}
				
				player.getClan().takeReputationScore(csn.getCost());
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.S1_DEDUCTED_FROM_CLAN_REP).addNumber(csn.getCost()));
				
				player.getClan().addNewSkill(skill);
				
				VillageMaster.showPledgeSkillList(player);
				return;
		}
	}
	
	private void updateShortCuts(Player player)
	{
		if (_skillLevel > 1)
		{
			for (L2ShortCut sc : player.getAllShortCuts())
			{
				if (sc.getId() == _skillId && sc.getType() == L2ShortCut.TYPE_SKILL)
				{
					L2ShortCut newsc = new L2ShortCut(sc.getSlot(), sc.getPage(), L2ShortCut.TYPE_SKILL, _skillId, _skillLevel, 1);
					player.sendPacket(new ShortCutRegister(newsc));
					player.registerShortCut(newsc);
				}
			}
		}
	}
}