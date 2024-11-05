/*
 * L2J_EngineMods
 * Engine developed by Fissban.
 *
 * This software is not free and you do not have permission
 * to distribute without the permission of its owner.
 *
 * This software is distributed only under the rule
 * of www.devsadmins.com.
 * 
 * Contact us with any questions by the media
 * provided by our web or email marco.faccio@gmail.com
 */
package enginemods.engine.mods;

import java.util.ArrayList;
import java.util.List;

import enginemods.data.ConfigData;
import enginemods.engine.AbstractMods;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.base.ClassRace;
import net.sf.l2j.gameserver.model.base.ClassType;
import net.sf.l2j.gameserver.model.holder.IntIntHolder;
import net.sf.l2j.gameserver.network.serverpackets.ExShowScreenMessage;
import net.sf.l2j.gameserver.network.serverpackets.ExShowScreenMessage.SMPOS;

/**
 * @author fissban
 */
public class NewCharacterCreated extends AbstractMods
{
	private static List<Integer> _players = new ArrayList<>();
	
	public NewCharacterCreated()
	{
		registerMod(true);// TODO missing enable/disable config
	}
	
	@Override
	public void onModState()
	{
		//
	}
	
	@Override
	public void onCreateCharacter(Player player)
	{
		// a new title for the character is assigned.
		player.setTitle(ConfigData.NEW_CHARACTER_CREATED_TITLE);
		
		_players.add(player.getObjectId());
	}
	
	@Override
	public void onEnterWorld(Player player)
	{
		if (_players.contains(player.getObjectId()))
		{				
			if (ConfigData.NEW_CHARACTER_CREATED_GIVE_BUFF)
			{					
				if (player.isMageClass() || (player.getTemplate().getRace() == ClassRace.ORC && 
	            (player.getClassId().getType() == ClassType.MYSTIC || player.getClassId().getType() == ClassType.PRIEST)))
				{
					// buffs list is delivered
					for (IntIntHolder bsh : ConfigData.NEW_CHARACTER_CREATED_BUFFS_MAGE)
					{
						L2Skill skill = bsh.getSkill();
						if (skill != null)
						{
							skill.getEffects(player, player);
						}
					}
				}
				else
				{	
					// buffs list is delivered
					for (IntIntHolder bsh : ConfigData.NEW_CHARACTER_CREATED_BUFFS_WARRIOR)
					{
						L2Skill skill = bsh.getSkill();
						if (skill != null)
						{
							skill.getEffects(player, player);
						}
					}
				}	
			}
			
			if (!ConfigData.NEW_CHARACTER_CREATED_SEND_SCREEN_MSG.equals(""))
			{
				player.sendPacket(new ExShowScreenMessage(ConfigData.NEW_CHARACTER_CREATED_SEND_SCREEN_MSG, 10000, SMPOS.TOP_CENTER, false));
			}
			
			_players.remove(Integer.valueOf(player.getObjectId()));
		}
	}	
}

