package net.sf.l2j.gameserver.network.serverpackets;

import java.text.DecimalFormat;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.data.sql.ClanTable;
import net.sf.l2j.gameserver.data.xml.PlayerData;
import net.sf.l2j.gameserver.model.WorldObject.PolyType;
import net.sf.l2j.gameserver.model.actor.Creature;
import net.sf.l2j.gameserver.model.actor.FakePc;
import net.sf.l2j.gameserver.model.actor.Npc;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.Summon;
import net.sf.l2j.gameserver.model.actor.instance.Monster;
import net.sf.l2j.gameserver.model.actor.instance.Pet;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.model.actor.template.PlayerTemplate;
import net.sf.l2j.gameserver.model.base.Sex;
import net.sf.l2j.gameserver.model.pledge.Clan;

import enginemods.EngineModsManager;

public abstract class AbstractNpcInfo extends L2GameServerPacket
{
	protected int _x, _y, _z, _heading;
	protected int _idTemplate;
	protected boolean _isAttackable, _isSummoned;
	protected int _mAtkSpd, _pAtkSpd;
	protected int _runSpd, _walkSpd;
	protected int _rhand, _lhand, _chest, _enchantEffect;
	protected double _collisionHeight, _collisionRadius;
	protected int _clanCrest, _allyCrest, _allyId, _clanId;
	
	protected String _name = "", _title = "";
	
	public AbstractNpcInfo(Creature cha)
	{
		_isSummoned = cha.isShowSummonAnimation();
		_x = cha.getX();
		_y = cha.getY();
		_z = cha.getZ();
		_heading = cha.getHeading();
		_mAtkSpd = cha.getMAtkSpd();
		_pAtkSpd = cha.getPAtkSpd();
		_runSpd = cha.getStat().getBaseRunSpeed();
		_walkSpd = cha.getStat().getBaseWalkSpeed();
	}
	
	/**
	 * Packet for Npcs
	 */
	public static class NpcInfo extends AbstractNpcInfo
	{
		private final Npc _npc;
		
		public NpcInfo(Npc cha, Creature attacker)
		{
			super(cha);
			_npc = cha;
			
			_enchantEffect = _npc.getEnchantEffect();
			_isAttackable = _npc.isAutoAttackable(attacker);
			
			// Support for polymorph.
			if (_npc.getPolyType() == PolyType.NPC)
			{
				_idTemplate = _npc.getPolyTemplate().getIdTemplate();
				_rhand = _npc.getPolyTemplate().getRightHand();
				_lhand = _npc.getPolyTemplate().getLeftHand();
				_collisionHeight = _npc.getPolyTemplate().getCollisionHeight();
				_collisionRadius = _npc.getPolyTemplate().getCollisionRadius();
			}
			else
			{
				_idTemplate = _npc.getTemplate().getIdTemplate();
				_rhand = _npc.getRightHandItem();
				_lhand = _npc.getLeftHandItem();
				_collisionHeight = _npc.getCollisionHeight();
				_collisionRadius = _npc.getCollisionRadius();
			}
			
			if (_npc.getTemplate().isUsingServerSideName())
				_name = _npc.getName();
			
			if (_npc.isChampion())
				_title = "Champion";
			else if (_npc.getTemplate().isUsingServerSideTitle())
				_title = _npc.getTitle();
			
			String title = EngineModsManager.onSeeNpcTitle(cha.getObjectId());
			if (title != null)
				_title = title;
			
			if (Config.SHOW_NPC_LVL && _npc instanceof Monster)
				_title = "Lv " + _npc.getLevel() + (_npc.getTemplate().getAggroRange() > 0 ? "* " : " ") + _title;
			
			// NPC crest system
			if (Config.SHOW_NPC_CREST && _npc.getCastle() != null && _npc.getCastle().getOwnerId() != 0)
			{
				Clan clan = ClanTable.getInstance().getClan(_npc.getCastle().getOwnerId());
				_clanCrest = clan.getCrestId();
				_clanId = clan.getClanId();
				_allyCrest = clan.getAllyCrestId();
				_allyId = clan.getAllyId();
			}
		}
		
		@Override
		protected void writeImpl()
		{
			FakePc fpc = _npc.getFakePc();
            if (fpc != null)
            {
            	 writeC(0x03);
                 writeD(_x);
                 writeD(_y);
                 writeD(_z);
                 writeD(_heading);
                 writeD(_npc.getObjectId());
                 writeS(fpc.name); // visible name
                 writeD(fpc.race);
                 writeD(fpc.sex);
                 writeD(fpc.clazz);
                
                 writeD(0x00); // PAPERDOLL_HAIR
                 writeD(0x00); //
                 writeD(fpc.pdRHand);
                 writeD(fpc.pdLHand);
                 writeD(fpc.pdGloves);
                 writeD(fpc.pdChest);
                 writeD(fpc.pdLegs);
                 writeD(fpc.pdFeet);
                 writeD(fpc.pdHair);
                 writeD(fpc.pdRHand);
                 writeD(fpc.pdHair);
                 writeD(fpc.pdHair2);
                
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 writeH(0x00);
                 
                 writeD(0x00);
                 writeD(0x00);
                
                 writeD(_mAtkSpd);
                 writeD(_pAtkSpd);
                
                 writeD(0x00);
                 writeD(0x00);
                
                 writeD(_runSpd);
                 writeD(_walkSpd);
                 writeD(_runSpd); // swim run speed
                 writeD(_walkSpd); // swim walk speed
                 writeD(_runSpd); // fly run speed
                 writeD(_walkSpd); // fly walk speed
                 writeD(_runSpd);
                 writeD(_walkSpd);
                 writeF(_npc.getStat().getMovementSpeedMultiplier());
                 writeF(_npc.getStat().getAttackSpeedMultiplier());   
                
                 PlayerTemplate pctmpl = PlayerData.getInstance().getTemplate(fpc.clazz);
                 if(fpc.sex == 0) {
               	  writeF(pctmpl.getCollisionRadiusBySex(Sex.MALE));
               	  writeF(pctmpl.getCollisionHeightBySex(Sex.MALE));
                 }else {
               	  writeF(pctmpl.getCollisionRadiusBySex(Sex.FEMALE));
               	  writeF(pctmpl.getCollisionHeightBySex(Sex.FEMALE));
                 }
                
                 writeD(fpc.hairStyle);
                 writeD(fpc.hairColor);
                 writeD(fpc.face);
               
                 if (_npc instanceof Monster)
                     writeS(fpc.title + " - HP " + new DecimalFormat("#.##").format(100.0 * _npc.getCurrentHp() / _npc.getMaxHp()) + "%"); // visible title
                 else
                    writeS(fpc.title);
                
                 writeD(0x00); // clan id
                 writeD(0x00); // clan crest id
                 writeD(0x00); // ally id
                 writeD(0x00); // ally crest id
                
                 writeD(0x00);
                 writeC(0x01); // standing = 1 sitting = 0
                
                 writeC(_npc.isRunning() ? 1 : 0); // running = 1 walking = 0
                 writeC(_npc.isInCombat() ? 1 : 0);
                 writeC(_npc.isAlikeDead() ? 1 : 0);
                
                 writeC(0x00);
                 writeC(0x00);
                 writeC(0x00);
                
                 writeH(0x00); // cubic count
                
                 writeC(0x00); // invisible = 1 visible =0
                 writeD(0x00); // abnormal effect
                 writeC(0x00); // isFlying() ? 2 : 0
                 writeH(0x00); // getRecomHave(): Blue value for name (0 = white, 255 = pure blue)
                
                 writeD(fpc.clazz);
                 writeD(0x00); // ?
                 writeD(0x00); // ?
                 writeC(fpc.enchantEffect);
                 writeC(0x00); // team circle around feet 1= Blue, 2 = red
                
                 writeD(0x00); // getClanCrestLargeId()
                
                 writeC(0x00); // isNoble(): Symbol on char menu ctrl+I
                 writeC(fpc.hero); // Hero Aura
               
                 writeC(0x00); // 0x01: Fishing Mode (Cant be undone by setting back to 0)
                 writeD(0x00);
                 writeD(0x00);
                 writeD(0x00);
                
                 writeD(fpc.nameColor);
                 writeD(_heading);
                 writeD(0x00); // pledge class
                 writeD(0x00); // pledge type
                 writeD(fpc.titleColor);
                 writeD(0x00); // cursed weapon level
             
            }
            else
            {
				writeC(0x16);
				
				writeD(_npc.getObjectId());
				writeD(_idTemplate + 1000000);
				writeD(_isAttackable ? 1 : 0);
				
				writeD(_x);
				writeD(_y);
				writeD(_z);
				writeD(_heading);
				
				writeD(0x00);
				
				writeD(_mAtkSpd);
				writeD(_pAtkSpd);
				writeD(_runSpd);
				writeD(_walkSpd);
				writeD(_runSpd);
				writeD(_walkSpd);
				writeD(_runSpd);
				writeD(_walkSpd);
				writeD(_runSpd);
				writeD(_walkSpd);
				
				writeF(_npc.getStat().getMovementSpeedMultiplier());
				writeF(_npc.getStat().getAttackSpeedMultiplier());
				
				writeF(_collisionRadius);
				writeF(_collisionHeight);
				
				writeD(_rhand);
				writeD(_chest);
				writeD(_lhand);
				
				writeC(1); // name above char
				writeC(_npc.isRunning() ? 1 : 0);
				writeC(_npc.isInCombat() ? 1 : 0);
				writeC(_npc.isAlikeDead() ? 1 : 0);
				writeC(_isSummoned ? 2 : 0);
				
				writeS(_name);
				writeS(_title);
				
				writeD(0x00);
				writeD(0x00);
				writeD(0x00);
				
				writeD(_npc.getAbnormalEffect());
				
				writeD(_clanId);
				writeD(_clanCrest);
				writeD(_allyId);
				writeD(_allyCrest);
				
				writeC(_npc.isFlying() ? 2 : 0);
				writeC(_npc.getTeam()); //writeC(0x00);
				
				writeF(_collisionRadius);
				writeF(_collisionHeight);
				
				writeD(_enchantEffect);
				writeD(_npc.isFlying() ? 1 : 0);
			}
		}
	}
		
	/**
	 * Packet for summons
	 */
	public static class SummonInfo extends AbstractNpcInfo
	{
		private final Summon _summon;
		private final Player _owner;
		private int _summonAnimation = 0;
		
		public SummonInfo(Summon cha, Player attacker, int val)
		{
			super(cha);
			_summon = cha;
			_owner = _summon.getOwner();
			
			_summonAnimation = val;
			if (_summon.isShowSummonAnimation())
				_summonAnimation = 2; // override for spawn
				
			_isAttackable = _summon.isAutoAttackable(attacker);
			_rhand = _summon.getWeapon();
			_lhand = 0;
			_chest = _summon.getArmor();
			_enchantEffect = _summon.getTemplate().getEnchantEffect();
			_title = (_owner == null || !_owner.isOnline()) ? "" : _owner.getName();
			_idTemplate = _summon.getTemplate().getIdTemplate();
			
			_collisionHeight = _summon.getCollisionHeight();
			_collisionRadius = _summon.getCollisionRadius();
			
			// NPC crest system
			if (Config.SHOW_SUMMON_CREST && _owner != null && _owner.getClan() != null)
			{
				Clan clan = ClanTable.getInstance().getClan(_owner.getClanId());
				_clanCrest = clan.getCrestId();
				_clanId = clan.getClanId();
				_allyCrest = clan.getAllyCrestId();
				_allyId = clan.getAllyId();
			}
		}
		
		@Override
		protected void writeImpl()
		{
			if (_owner != null && _owner.getAppearance().getInvisible())
				return;
			
			writeC(0x16);
			
			writeD(_summon.getObjectId());
			writeD(_idTemplate + 1000000);
			writeD(_isAttackable ? 1 : 0);
			
			writeD(_x);
			writeD(_y);
			writeD(_z);
			writeD(_heading);
			
			writeD(0x00);
			
			writeD(_mAtkSpd);
			writeD(_pAtkSpd);
			writeD(_runSpd);
			writeD(_walkSpd);
			writeD(_runSpd);
			writeD(_walkSpd);
			writeD(_runSpd);
			writeD(_walkSpd);
			writeD(_runSpd);
			writeD(_walkSpd);
			
			writeF(_summon.getStat().getMovementSpeedMultiplier());
			writeF(_summon.getStat().getAttackSpeedMultiplier());
			
			writeF(_collisionRadius);
			writeF(_collisionHeight);
			
			writeD(_rhand);
			writeD(_chest);
			writeD(_lhand);
			
			writeC(1); // name above char
			writeC(_summon.isRunning() ? 1 : 0);
			writeC(_summon.isInCombat() ? 1 : 0);
			writeC(_summon.isAlikeDead() ? 1 : 0);
			writeC(_summonAnimation);
			
			writeS(_name);
			writeS(_title);
			
			writeD(_summon instanceof Pet ? 0x00 : 0x01);
			writeD(_summon.getPvpFlag());
			writeD(_summon.getKarma());
			
			writeD(_summon.getAbnormalEffect());
			
			writeD(_clanId);
			writeD(_clanCrest);
			writeD(_allyId);
			writeD(_allyCrest);
			
			writeC(0x00);
			writeC(_summon.getTeam());
			
			writeF(_collisionRadius);
			writeF(_collisionHeight);
			
			writeD(_enchantEffect);
			writeD(0x00);
		}
	}
			
	/**
	 * Packet for morphed PCs
	 */
	public static class PcMorphInfo extends AbstractNpcInfo
	{
		private final Player _pc;
		private final NpcTemplate _template;
		private final int _swimSpd;
		
		public PcMorphInfo(Player cha, NpcTemplate template)
		{
			super(cha);
			_pc = cha;
			_template = template;
			
			_swimSpd = cha.getStat().getBaseSwimSpeed();
			
			_rhand = _template.getRightHand();
			_lhand = _template.getLeftHand();
			
			_collisionHeight = _template.getCollisionHeight();
			_collisionRadius = _template.getCollisionRadius();
			
			_enchantEffect = _template.getEnchantEffect();
		}
		
		@Override
		protected void writeImpl()
		{
			writeC(0x16);
			
			writeD(_pc.getObjectId());
			writeD(_pc.getPolyId() + 1000000);
			writeD(1);
			
			writeD(_x);
			writeD(_y);
			writeD(_z);
			writeD(_heading);
			
			writeD(0x00);
			
			writeD(_mAtkSpd);
			writeD(_pAtkSpd);
			writeD(_runSpd);
			writeD(_walkSpd);
			writeD(_swimSpd);
			writeD(_swimSpd);
			writeD(_runSpd);
			writeD(_walkSpd);
			writeD(_runSpd);
			writeD(_walkSpd);
			
			writeF(_pc.getStat().getMovementSpeedMultiplier());
			writeF(_pc.getStat().getAttackSpeedMultiplier());
			
			writeF(_collisionRadius);
			writeF(_collisionHeight);
			
			writeD(_rhand);
			writeD(0);
			writeD(_lhand);
			
			writeC(1); // name above char
			writeC(_pc.isRunning() ? 1 : 0);
			writeC(_pc.isInCombat() ? 1 : 0);
			writeC(_pc.isAlikeDead() ? 1 : 0);
			writeC(0); // 0 = teleported, 1 = default, 2 = summoned
			
			writeS(_name);
			writeS(_title);
			
			writeD(0x00);
			writeD(0x00);
			writeD(0x00);
			
			writeD(_pc.getAbnormalEffect());
			
			writeD(0x00);
			writeD(0x00);
			writeD(0x00);
			writeD(0x00);
			
			writeC(0x00);
			writeC(0x00);
			
			writeF(_collisionRadius);
			writeF(_collisionHeight);
			
			writeD(_enchantEffect);
			writeD(0x00);
		}
	}
}
