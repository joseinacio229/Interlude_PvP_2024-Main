package net.sf.l2j.gameserver.events.eventengine.event;

import java.util.HashMap;
import java.util.Map;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.events.eventengine.AbstractEvent;
import net.sf.l2j.gameserver.events.eventengine.EventInformation;
import net.sf.l2j.gameserver.events.eventengine.EventManager;
import net.sf.l2j.gameserver.events.eventengine.EventResTask;
import net.sf.l2j.gameserver.events.eventengine.EventState;
import net.sf.l2j.gameserver.events.eventengine.EventTeam;
import net.sf.l2j.gameserver.model.actor.Npc;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.network.serverpackets.SocialAction;

/**
 * @author Anarchy
 *
 */
public class CTF extends AbstractEvent
{
	private Map<Npc, EventTeam> flags = new HashMap<>();
	private Map<Npc, Player> flagWielders = new HashMap<>();
	
	public CTF()
	{
		super("CTF", 3, Config.CTF_RUNNING_TIME);
		addTeam(Config.CTF_TEAM_1_NAME, Config.CTF_TEAM_1_COLOR, Config.CTF_TEAM_1_LOCATION);
		addTeam(Config.CTF_TEAM_2_NAME, Config.CTF_TEAM_2_COLOR, Config.CTF_TEAM_2_LOCATION);
		eventRes = new EventResTask(this);
		eventInfo = new EventInformation(this, Config.CTF_TEAM_1_NAME+": %team1Score% | "+Config.CTF_TEAM_2_NAME+": %team2Score%");
		eventInfo.addReplacement("%team1Score%", teams.get(0).getName().equals(Config.CTF_TEAM_1_NAME) ? teams.get(0).getScore() : teams.get(1).getScore());
		eventInfo.addReplacement("%team2Score%", teams.get(0).getName().equals(Config.CTF_TEAM_2_NAME) ? teams.get(0).getScore() : teams.get(1).getScore());
	}
	
	@Override
	public void run()
	{
		EventManager.getInstance().setActiveEvent(this);
		openRegistrations();
		schedule(() -> start(), (Config.EVENT_REGISTRATION_TIME*60)+1);
	}
	
	@Override
	protected void cleanUp()
	{
		for (Player player : flagWielders.values())
			player.destroyItemByItemId("Event flag.", 6718, 1, null, false);
		flagWielders.clear();
		flags.clear();
		super.cleanUp();
	}
	
	@Override
	protected void start()
	{
		if (!enoughRegistered(Config.CTF_MIN_PLAYERS))
		{
			abort();
			return;
		}
		
		flags.put(spawnNpc(65534, Config.CTF_TEAM_1_FLAG_LOCATION, teams.get(0).getName()), teams.get(0));
		flags.put(spawnNpc(65534, Config.CTF_TEAM_2_FLAG_LOCATION, teams.get(1).getName()), teams.get(1));
		
		super.start();
	}
	
	@Override
	protected void end()
	{
		if (!draw())
		{
			announceTopTeams(1);
			rewardTopTeams(1, Config.CTF_WINNER_REWARDS);
		}
		else
		{
			announce("The event ended in a draw.", true);
			rewardTopInDraw(Config.CTF_DRAW_REWARDS);
		}
		super.end();
	}
	
	@Override
	protected void increaseScore(Player player, int count)
	{
		super.increaseScore(player, count);
		for (int itemId : Config.CTF_ON_SCORE_REWARDS.keySet())
			player.addItem("Event reward.", itemId, Config.CTF_ON_SCORE_REWARDS.get(itemId), null, true);
		eventInfo.addReplacement("%team1Score%", teams.get(0).getName().equals(Config.TVT_TEAM_1_NAME) ? teams.get(0).getScore() : teams.get(1).getScore());
		eventInfo.addReplacement("%team2Score%", teams.get(0).getName().equals(Config.TVT_TEAM_2_NAME) ? teams.get(0).getScore() : teams.get(1).getScore());
	}
	
	@Override
	public boolean isAutoAttackable(Player attacker, Player target)
	{
		return getTeam(attacker) != getTeam(target);
	}
	
	@Override
	public void onKill(Player killer, Player victim)
	{
		if (flagWielders.values().contains(victim))
		{
			victim.destroyItemByItemId("Event flag.", 6718, 1, null, false);
			victim.broadcastUserInfo();
			announce("The "+getTeam(killer).getName()+" flag has been returned!", false);
			Npc remove = null;
			for (Npc flag : flags.keySet())
			{
				if (flags.get(flag) == getTeam(killer))
				{
					remove = flag;
					break;
				}
			}
			if (remove != null)
				flagWielders.remove(remove);
		}
		
		eventRes.addPlayer(victim);
	}
	
	@Override
	public boolean onInterract(Player player, Npc npc)
	{
		if (flags.keySet().contains(npc))
		{
			if (getState() != EventState.RUNNING)
				return true;
			
			if (flags.get(npc) == getTeam(player) && flagWielders.values().contains(player))
			{
				player.destroyItemByItemId("Event flag.", 6718, 1, null, false);
				player.broadcastUserInfo();
				announce(player.getName()+" has scored for "+flags.get(npc).getName()+"!", false);
				increaseScore(player, 1);
				Npc remove = null;
				for (Npc flag : flagWielders.keySet())
				{
					if (flagWielders.get(flag) == player)
					{
						remove = flag;
						break;
					}
				}
				flagWielders.remove(remove);
				return true;
			}
			if (flags.get(npc) != getTeam(player) && !flagWielders.keySet().contains(npc))
			{
				ItemInstance flag = player.addItem("Event flag.", 6718, 1, null, false);
				player.useEquippableItem(flag, true);
				player.broadcastUserInfo();
				announce(player.getName()+" has got the "+flags.get(npc).getName()+" flag!", false);
				player.broadcastPacket(new SocialAction(player, 16));
				flagWielders.put(npc, player);
				return true;
			}
		}
		
		return true;
	}
	
	@Override
	public boolean canHeal(Player healer, Player target)
	{
		return getTeam(healer) == getTeam(target);
	}
	
	@Override
	public boolean canAttack(Player attacker, Player target)
	{
		return getTeam(attacker) != getTeam(target) && getState() == EventState.RUNNING;
	}
	
	@Override
	public boolean canUseItem(Player player, int itemId)
	{
		return !flagWielders.values().contains(player);
	}
	
	@Override
	public boolean allowDiePacket(Player player)
	{
		return false;
	}
}