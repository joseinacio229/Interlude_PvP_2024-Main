package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.events.eventengine.EventManager;
import net.sf.l2j.gameserver.instancemanager.SevenSignsFestival;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.zone.ZoneId;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.taskmanager.AttackStanceTaskManager;

import enginemods.EngineModsManager;

public final class Logout extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}
	
	@Override
	protected void runImpl()
	{
		final Player player = getClient().getActiveChar();
		if (player == null)
			return;
		
		// Verificar si el jugador está en modo de prueba de skin
        if (player.isTryingSkin())
        {
            player.sendMessage("You cannot log out while trying a skin. Please wait for the trial to end.");
            player.sendPacket(ActionFailed.STATIC_PACKET);
        	 return;
        }         
		
		if (player.getActiveEnchantItem() != null || player.isLocked())
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (player.isInsideZone(ZoneId.MULTI_FUNCTION) && !Config.LOGOUT_ZONE)
		{
			player.sendMessage("You cannot Logout while inside a Multifunction zone.");
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (player.isInsideZone(ZoneId.NO_RESTART))
		{
			player.sendPacket(SystemMessageId.NO_LOGOUT_HERE);
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (AttackStanceTaskManager.getInstance().isInAttackStance(player))
		{
			player.sendPacket(SystemMessageId.CANT_LOGOUT_WHILE_FIGHTING);
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (EventManager.getInstance().getActiveEvent() != null && EventManager.getInstance().getActiveEvent().isInEvent(player))
		{
			player.sendMessage("You cannot logout while in an event.");
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (player.isFestivalParticipant() && SevenSignsFestival.getInstance().isFestivalInitialized())
		{
			player.sendPacket(SystemMessageId.NO_LOGOUT_HERE);
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (EngineModsManager.onExitWorld(player))
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		player.removeFromBossZone();
		player.logout();
	}
}