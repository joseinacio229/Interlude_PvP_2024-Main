package net.sf.l2j.gameserver.model.actor.instance;

import java.util.Calendar;
import java.util.StringTokenizer;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.data.xml.TeleportLocationData;
import net.sf.l2j.gameserver.events.dynamiczones.PvpZoneManager;
import net.sf.l2j.gameserver.events.eventengine.EventManager;
import net.sf.l2j.gameserver.instancemanager.CastleManager;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.model.location.Location;
import net.sf.l2j.gameserver.model.location.TeleportLocation;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

import mods.autofarm.AutofarmPlayerRoutine;

/**
 * An instance type extending {@link Folk}, used for teleporters.<br>
 * <br>
 * A teleporter allows {@link Player}s to teleport to a specific location, for a fee.
 */
public final class Gatekeeper extends Folk
{
	public Gatekeeper(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public String getHtmlPath(int npcId, int val)
	{
		String filename = "";
		if (val == 0)
			filename = "" + npcId;
		else
			filename = npcId + "-" + val;
		
		return "data/html/teleporter/" + filename + ".htm";
	}
	
	@Override
	public void onBypassFeedback(Player player, String command)
	{
		final AutofarmPlayerRoutine bot = player.getBot();
		
		// Generic PK check. Send back the HTM if found and cancel current action.
		if (!Config.KARMA_PLAYER_CAN_USE_GK && player.getKarma() > 0 && showPkDenyChatWindow(player, "teleporter"))
			return;
		
		if (EventManager.getInstance().getActiveEvent() != null && EventManager.getInstance().getActiveEvent().isInEvent(player))
		{
			player.sendMessage("You cannot leave the event.");
			return;
		}
		
		if (command.startsWith("goto"))
		{
			final StringTokenizer st = new StringTokenizer(command, " ");
			st.nextToken();
			
			// No more tokens.
			if (!st.hasMoreTokens())
				return;
			
			// No interaction possible with the NPC.
			if (!canInteract(player))
				return;
			
			// Retrieve the list.
			final TeleportLocation list = TeleportLocationData.getInstance().getTeleportLocation(Integer.parseInt(st.nextToken()));
			if (list == null)
				return;
			
			// Siege is currently in progress in this location.
			if (CastleManager.getInstance().getActiveSiege(list.getX(), list.getY(), list.getZ()) != null)
			{
				player.sendPacket(SystemMessageId.CANNOT_PORT_VILLAGE_IN_SIEGE);
				return;
			}
			
			// The list is for noble, but player isn't noble.
			if (list.isNoble() && !player.isNoble())
			{
				final NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
				html.setFile("data/html/teleporter/nobleteleporter-no.htm");
				html.replace("%objectId%", getObjectId());
				html.replace("%npcname%", getName());
				player.sendPacket(html);
				
				player.sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}
			
			// Retrieve price list. Potentially cut it by 2 depending of current date.
			int price = list.getPrice();
			
			if (!list.isNoble())
			{
				Calendar cal = Calendar.getInstance();
				if (cal.get(Calendar.HOUR_OF_DAY) >= 20 && cal.get(Calendar.HOUR_OF_DAY) <= 23 && (cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7))
					price /= 2;
			}
			
			// Delete related items, and if successful teleport the player to the location.
			if (player.destroyItemByItemId("Teleport ", (list.isNoble()) ? 6651 : 57, price, this, true))
				player.teleToLocation(list, 20);
			
			if (player.isAutoFarm())
			{
				bot.stop();
				player.setAutoFarm(false);
			}
			
			player.sendPacket(ActionFailed.STATIC_PACKET);
		}
		else if (command.startsWith("Chat"))
		{
			int val = 0;
			try
			{
				val = Integer.parseInt(command.substring(5));
			}
			catch (IndexOutOfBoundsException ioobe)
			{
			}
			catch (NumberFormatException nfe)
			{
			}
			
			// Show half price HTM depending of current date. If not existing, use the regular "-1.htm".
			if (val == 1)
			{
				Calendar cal = Calendar.getInstance();
				if (cal.get(Calendar.HOUR_OF_DAY) >= 20 && cal.get(Calendar.HOUR_OF_DAY) <= 23 && (cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7))
				{
					final NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
					
					String content = HtmCache.getInstance().getHtm("data/html/teleporter/half/" + getNpcId() + ".htm");
					if (content == null)
						content = HtmCache.getInstance().getHtmForce("data/html/teleporter/" + getNpcId() + "-1.htm");
					
					html.setHtml(content);
					html.replace("%objectId%", getObjectId());
					html.replace("%npcname%", getName());
					player.sendPacket(html);
					
					player.sendPacket(ActionFailed.STATIC_PACKET);
					return;
				}
			}
			showChatWindow(player, val);
		}
		else if (command.equals("pvp"))
		{
			if (!Config.KARMA_PLAYER_CAN_USE_GK && player.getKarma() > 0)
			{
				player.sendMessage("Go away, you're not welcome here.");
				return;
			}			
			
			if(Config.ENABLE_DYNAMIC_PVP_ZONES) {
				int[] loc = PvpZoneManager.getInstance().getCurrentZone().getGkSpawnLocation();
				player.teleToLocation(new Location(loc[0], loc[1], loc[2]),0);
			}else {
				player.sendMessage("Dynamic PvP Zones are disabled.");
			}
		}
		else
			super.onBypassFeedback(player, command);
	}
	
	@Override
	public void showChatWindow(Player player, int val)
	{
		// Generic PK check. Send back the HTM if found and cancel current action.
		if (!Config.KARMA_PLAYER_CAN_USE_GK && player.getKarma() > 0 && showPkDenyChatWindow(player, "teleporter"))
			return;
		
		showChatWindow(player, getHtmlPath(getNpcId(), val));
	}
}