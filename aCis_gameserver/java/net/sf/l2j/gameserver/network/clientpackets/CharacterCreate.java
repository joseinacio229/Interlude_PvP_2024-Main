package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.commons.lang.StringUtil;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.data.ItemTable;
import net.sf.l2j.gameserver.data.SkillTable;
import net.sf.l2j.gameserver.data.sql.PlayerInfoTable;
import net.sf.l2j.gameserver.data.xml.PlayerData;
import net.sf.l2j.gameserver.idfactory.IdFactory;
import net.sf.l2j.gameserver.model.L2ShortCut;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.World;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.template.PlayerTemplate;
import net.sf.l2j.gameserver.model.base.ClassRace;
import net.sf.l2j.gameserver.model.base.ClassType;
import net.sf.l2j.gameserver.model.base.Sex;
import net.sf.l2j.gameserver.model.holder.skillnode.GeneralSkillNode;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.model.item.kind.Item;
import net.sf.l2j.gameserver.network.serverpackets.CharCreateFail;
import net.sf.l2j.gameserver.network.serverpackets.CharCreateOk;
import net.sf.l2j.gameserver.network.serverpackets.CharSelectInfo;
import net.sf.l2j.gameserver.scripting.Quest;
import net.sf.l2j.gameserver.scripting.ScriptManager;

import enginemods.EngineModsManager;

@SuppressWarnings("unused")
public final class CharacterCreate extends L2GameClientPacket
{
	// cSdddddddddddd
	private String _name;
	private int _race;
	private byte _sex;
	private int _classId;
	private int _int;
	private int _str;
	private int _con;
	private int _men;
	private int _dex;
	private int _wit;
	private byte _hairStyle;
	private byte _hairColor;
	private byte _face;
	
	@Override
	protected void readImpl()
	{
		_name = readS();
		_race = readD();
		_sex = (byte) readD();
		_classId = readD();
		_int = readD();
		_str = readD();
		_con = readD();
		_men = readD();
		_dex = readD();
		_wit = readD();
		_hairStyle = (byte) readD();
		_hairColor = (byte) readD();
		_face = (byte) readD();
	}
	
	@Override
	protected void runImpl()
	{
		if (!StringUtil.isValidPlayerName(_name))
		{
			sendPacket(new CharCreateFail((_name.length() > 16) ? CharCreateFail.REASON_16_ENG_CHARS : CharCreateFail.REASON_INCORRECT_NAME));
			return;
		}
		
		if (_face > 2 || _face < 0)
		{
			sendPacket(new CharCreateFail(CharCreateFail.REASON_CREATION_FAILED));
			return;
		}
		
		if (_hairStyle < 0 || (_sex == 0 && _hairStyle > 4) || (_sex != 0 && _hairStyle > 6))
		{
			sendPacket(new CharCreateFail(CharCreateFail.REASON_CREATION_FAILED));
			return;
		}
		
		if (_hairColor > 3 || _hairColor < 0)
		{
			sendPacket(new CharCreateFail(CharCreateFail.REASON_CREATION_FAILED));
			return;
		}
		
		if (PlayerInfoTable.getInstance().getCharactersInAcc(getClient().getAccountName()) >= 7)
		{
			sendPacket(new CharCreateFail(CharCreateFail.REASON_TOO_MANY_CHARACTERS));
			return;
		}
		
		if (PlayerInfoTable.getInstance().getPlayerObjectId(_name) > 0)
		{
			sendPacket(new CharCreateFail(CharCreateFail.REASON_NAME_ALREADY_EXISTS));
			return;
		}
		
		final PlayerTemplate template = PlayerData.getInstance().getTemplate(_classId);
		if (template == null || template.getClassBaseLevel() > 1)
		{
			sendPacket(new CharCreateFail(CharCreateFail.REASON_CREATION_FAILED));
			return;
		}
		
		final Player newChar = Player.create(IdFactory.getInstance().getNextId(), template, getClient().getAccountName(), _name, _hairStyle, _hairColor, _face, Sex.values()[_sex]);
		if (newChar == null)
		{
			sendPacket(new CharCreateFail(CharCreateFail.REASON_CREATION_FAILED));
			return;
		}
		
		newChar.setCurrentCp(newChar.getMaxCp());
		newChar.setCurrentHp(newChar.getMaxHp());
		newChar.setCurrentMp(newChar.getMaxMp());
		
		// send acknowledgement
		sendPacket(CharCreateOk.STATIC_PACKET);
		
		World.getInstance().addObject(newChar);
		
		newChar.getPosition().set(template.getSpawn());
		newChar.setTitle("");
		
		newChar.registerShortCut(new L2ShortCut(0, 0, 3, 2, -1, 1)); // attack shortcut
		newChar.registerShortCut(new L2ShortCut(3, 0, 3, 5, -1, 1)); // take shortcut
		newChar.registerShortCut(new L2ShortCut(10, 0, 3, 0, -1, 1)); // sit shortcut
		
		for (int itemId : template.getItemIds())
		{
			final ItemInstance item = newChar.getInventory().addItem("Init", itemId, 1, newChar, null);
			if (itemId == 5588) // tutorial book shortcut
				newChar.registerShortCut(new L2ShortCut(11, 0, 1, item.getObjectId(), -1, 1));
			
			if (item.isEquipable())
			{
				if (newChar.getActiveWeaponItem() == null || !(item.getItem().getType2() != Item.TYPE2_WEAPON))
					newChar.getInventory().equipItemAndRecord(item);
			}
		}
		
		for (GeneralSkillNode skill : newChar.getAvailableAutoGetSkills())
		{
			if (skill.getId() == 9115 || skill.getId() == 9115)
				newChar.registerShortCut(new L2ShortCut(1, 0, 2, skill.getId(), 1, 1));
			
			if (skill.getId() == 1216)
				newChar.registerShortCut(new L2ShortCut(9, 0, 2, skill.getId(), 1, 1));
		}
		
		if (!Config.DISABLE_TUTORIAL)
		{
			if (newChar.getQuestState("Tutorial") == null)
			{
				Quest q = ScriptManager.getInstance().getQuest("Tutorial");
				if (q != null)
					q.newQuestState(newChar).setState(Quest.STATE_STARTED);
			}
		}
		
		if (Config.CUSTOM_STARTER_ITEMS_ENABLED) {
	        if (newChar.isMageClass() || (newChar.getTemplate().getRace() == ClassRace.ORC && 
	            (newChar.getClassId().getType() == ClassType.MYSTIC || newChar.getClassId().getType() == ClassType.PRIEST))) {
	            // Añadir items para magos
	            for (int[] reward : Config.STARTING_CUSTOM_ITEMS_M) {
	                if (ItemTable.getInstance().createDummyItem(reward[0]).isStackable())
	                    newChar.getInventory().addItem("Starter Items Mage", reward[0], reward[1], newChar, null);
	                else
	                    for (int i = 0; i < reward[1]; ++i)
	                        newChar.getInventory().addItem("Starter Items Mage", reward[0], 1, newChar, null);
	            }
	            
	        } else 
	        	{
		            // Añadir items para luchadores
		            for (int[] reward : Config.STARTING_CUSTOM_ITEMS_F)
		            {
		                if (ItemTable.getInstance().createDummyItem(reward[0]).isStackable())
		                    newChar.getInventory().addItem("Starter Items Fighter", reward[0], reward[1], newChar, null);
		                else
		                    for (int i = 0; i < reward[1]; ++i)
		                        newChar.getInventory().addItem("Starter Items Fighter", reward[0], 1, newChar, null);
		            }			         
	        	}
	    }
		EngineModsManager.onCreateCharacter(newChar);
		
		newChar.setOnlineStatus(true, false);
		newChar.deleteMe();
		
		final CharSelectInfo cl = new CharSelectInfo(getClient().getAccountName(), getClient().getSessionId().playOkID1);
		getClient().getConnection().sendPacket(cl);
		getClient().setCharSelectSlot(cl.getCharacterSlots());
	}
}