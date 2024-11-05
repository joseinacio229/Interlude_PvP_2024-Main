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
package enginemods.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import enginemods.enums.WeekDayType;
import enginemods.holders.RewardHolder;
import enginemods.util.UtilProperties;
import net.sf.l2j.gameserver.model.holder.IntIntHolder;
import net.sf.l2j.gameserver.model.location.Location;
import net.sf.l2j.gameserver.skills.Stats;

/**
 * @author fissban
 */
public class ConfigData
{
	protected static final Logger LOG = Logger.getLogger(ConfigData.class.getName());
	
	// Files
	private static final String ANTI_BOOT_FILE = "./config/engine/AntiBot.properties";
	private static final String FAKES_PLAYERS_FILE = "./config/engine/FakePlayers.properties";
	private static final String COMMUNITY_FILE = "./config/engine/Community.properties";
	private static final String OFFLINE_SHOP_FILE = "./config/engine/SellBuff.properties";
	private static final String VIP_FILE = "./config/engine/Vip.properties";
	private static final String AIO_FILE = "./config/engine/Aio.properties";
	private static final String NEW_CHARACTER_CREATED_FILE = "./config/engine/NewCharacterCreated.properties";
	private static final String RANDOM_BOSS_SPAWN_FILE = "./config/engine/RandomBossSpawn.properties";	
	private static final String BONUS_WEEKEND_FILE = "./config/engine/BonusWeekend.properties";
	private static final String CHAMPIONS_FILE = "./config/engine/Champions.properties";
	private static final String ANNOUNCE_KILL_BOOS_FILE = "./config/engine/AnnounceKillBoss.properties";
	private static final String COLOR_AMOUNT_PVP_FILE = "./config/engine/ColorAccordingAmountPvPorPk.properties";
	private static final String ENCHANT_ABNORMAL_EFFECT_FILE = "./config/engine/EnchantAbnormalEffectArmor.properties";
	private static final String PVP_REWARD_FILE = "./config/engine/PvpReward.properties";
	private static final String SPREE_KILLS_FILE = "./config/engine/SpreeKills.properties";
	private static final String SUBCLASS_ACUMULATIVES_FILE = "./config/engine/SubClassAcumulatives.properties";
	private static final String CITY_ELPY_FILE = "./config/engine/CityElpys.properties";
	private static final String NPC_BUFFER_SCHEME = "./config/engine/NpcbufferScheme.properties";
	
	// Npc Buffer Scheme ------------------------------------------------------------------------------------------------ //
	public static boolean NPC_BUFFER_FREE_BUFFS;
	public static int NPC_BUFFER_BUFF_PRICE; // precio de usar cada buff, sea del tipo q sea.
	public static int NPC_BUFFER_BUFF_SET_PRICE; // precio de los buffs pre-establecidos
	public static int NPC_BUFFER_CONSUMABLE_ID; // item a cobrar
	public static int NPC_BUFFER_BUFF_REMOVE_PRICE;
    public static int NPC_BUFFER_SCHEME_BUFF_PRICE;
    public static int NPC_BUFFER_SCHEMES_PER_PLAYER;
    public static int NPC_BUFFER_MAX_SCHEME_BUFFS;
    public static int NPC_BUFFER_MAX_SCHEME_DANCE;
    // Npc Buffer Scheme 2 ------------------------------------------------------------------------------------------------ //
 	public static boolean NPC_BUFFER_FREE_BUFFS_2;
 	public static int NPC_BUFFER_BUFF_PRICE_2; // precio de usar cada buff, sea del tipo q sea.
 	public static int NPC_BUFFER_BUFF_SET_PRICE_2; // precio de los buffs pre-establecidos
 	public static int NPC_BUFFER_CONSUMABLE_ID_2; // item a cobrar
 	public static int NPC_BUFFER_BUFF_REMOVE_PRICE_2;
    public static int NPC_BUFFER_SCHEME_BUFF_PRICE_2;
    public static int NPC_BUFFER_SCHEMES_PER_PLAYER_2;
    public static int NPC_BUFFER_MAX_SCHEME_BUFFS_2;
    public static int NPC_BUFFER_MAX_SCHEME_DANCE_2;
    // Npc Buffer Scheme 3 ------------------------------------------------------------------------------------------------ //
  	public static boolean NPC_BUFFER_FREE_BUFFS_3;
  	public static int NPC_BUFFER_BUFF_PRICE_3; // precio de usar cada buff, sea del tipo q sea.
  	public static int NPC_BUFFER_BUFF_SET_PRICE_3; // precio de los buffs pre-establecidos
  	public static int NPC_BUFFER_CONSUMABLE_ID_3; // item a cobrar
  	public static int NPC_BUFFER_BUFF_REMOVE_PRICE_3;
    public static int NPC_BUFFER_SCHEME_BUFF_PRICE_3;
    public static int NPC_BUFFER_SCHEMES_PER_PLAYER_3;
    public static int NPC_BUFFER_MAX_SCHEME_BUFFS_3;
    public static int NPC_BUFFER_MAX_SCHEME_DANCE_3;
    // Npc Buffer Scheme 4 ------------------------------------------------------------------------------------------------ //
  	public static boolean NPC_BUFFER_FREE_BUFFS_4;
  	public static int NPC_BUFFER_BUFF_PRICE_4; // precio de usar cada buff, sea del tipo q sea.
  	public static int NPC_BUFFER_BUFF_SET_PRICE_4; // precio de los buffs pre-establecidos
  	public static int NPC_BUFFER_CONSUMABLE_ID_4; // item a cobrar
  	public static int NPC_BUFFER_BUFF_REMOVE_PRICE_4;
    public static int NPC_BUFFER_SCHEME_BUFF_PRICE_4;
    public static int NPC_BUFFER_SCHEMES_PER_PLAYER_4;
    public static int NPC_BUFFER_MAX_SCHEME_BUFFS_4;
    public static int NPC_BUFFER_MAX_SCHEME_DANCE_4;
	// City Elpy ------------------------------------------------------------------------------------------------ //
	public static boolean ELPY_Enabled;
	public static List<WeekDayType> ELPY_ENABLE_DAY = new ArrayList<>();
	public static int ELPY;
	public static int ELPY_COUNT;
	public static int ELPY_RANGE_SPAWN;
	public static int ELPY_EVENT_TIME;
	public static List<Location> ELPY_LOC = new ArrayList<>();
	public static List<RewardHolder> ELPY_REWARDS = new ArrayList<>();
	// Fakes Players -------------------------------------------------------------------------------------------- //
	public static boolean ENABLE_fakePlayers;
	public static int FAKE_LEVEL = 78;
	public static int FAKE_MAX_PVP = 500;
	public static int FAKE_MAX_PK = 100;
	public static int FAKE_CHANCE_SIT = 30;
	public static int FAKE_CHANCE_HAS_CLAN = 50;
	public static int SPAWN_RANGE = 200;
	// Community ------------------------------------------------------------------------------------------------- //
	public static boolean ENABLE_BBS_REGION;
	public static boolean ENABLE_BBS_CLAN;
	
	public static boolean ENABLE_BBS_HOME;
	// Auction ------------------------------
	public static boolean ENABLE_BBS_MEMO;
	// comision en adena al comenzar la venta
	public static int COMMISION_FOR_START_SELL = 10000;
	public static int COMMISION_FOR_START_SELL_ID = 57;
	// comision en porcentaje al finalizar la venta.
	public static int COMMISION_FOR_END_SELL = 10;
	
	// Rebirth ------------------------------
	public static boolean ENABLE_BBS_FAVORITE;
	// cantidad maxima de rebirths
	public static int MAX_REBIRTH = 10;
	// puntos que se ganaran por cada rebirth para repartir en los stats -> CON,STR,DEX,WIT.....
	public static int STAT_POINT_PER_REBIRTH = 4;
	// puntos que se ganaran por cada rebirth para repartir en el arbol de habilidades.
	public static int MASTERY_POINT_PER_REBIRTH = 2;
	// nivel en que quedara el personaje luego de renacer
	public static int LVL_REBIRTH = 1;
	// Offline Shop ---------------------------------------------------------------------------------------------- //
	public static boolean OFFLINE_TRADE_ENABLE;
	public static boolean OFFLINE_SELLBUFF_ENABLE;
	public static boolean OFFLINE_MODE_IN_PEACE_ZONE;
	public static boolean OFFLINE_MODE_NO_DAMAGE;
	public static int OFFLINE_MAX_DAYS;
	public static boolean OFFLINE_DISCONNECT_FINISHED;
	public static boolean OFFLINE_SET_NAME_COLOR;
	public static int OFFLINE_NAME_COLOR;
	// Configs ColorAccordingAmountPvPorPk ------------------------------------------------------------------------ //
	public static boolean ENABLE_ColorAccordingAmountPvPorPk;
	public static Map<Integer, String> PVP_COLOR_NAME = new LinkedHashMap<>();
	public static Map<Integer, String> PK_COLOR_TITLE = new LinkedHashMap<>();
	// Configs EnchantAbnormalEffectArmor ------------------------------------------------------------------------- //
	public static boolean ENABLE_EnchantAbnormalEffectArmor;
	public static int ENCHANT_EFFECT_LVL;
	// Configs PvpReward ------------------------------------------------------------------------------------------ //
	public static boolean ENABLE_PvpReward;
	public static List<RewardHolder> PVP_REWARDS = new ArrayList<>();
	public static int PVP_TIME;
	// Configs SpreeKills ----------------------------------------------------------------------------------------- //
	public static boolean ENABLE_SpreeKills;
	public static Map<Integer, String> ANNOUNCEMENTS_KILLS = new LinkedHashMap<>();
	// Configs SubClassAcumulatives ------------------------------------------------------------------------------- //
	public static boolean ENABLE_SubClassAcumulatives;
	public static boolean ACUMULATIVE_PASIVE_SKILLS;
	public static Set<Integer> DONT_ACUMULATIVE_SKILLS_ID = new HashSet<>();
	// Config AnnounceKillBoss ------------------------------------------------------------------------------------ //
	public static boolean ENABLE_AnnounceKillBoss;
	public static List<WeekDayType> ANNOUNCE_KILL_BOSS_ENABLE_DAY = new ArrayList<>();
	public static String ANNOUNCE_KILL_BOSS;
	public static String ANNOUNCE_KILL_GRANDBOSS;
	// Config Champions ------------------------------------------------------------------------------------------- //
	public static boolean ENABLE_Champions;
	public static List<WeekDayType> CHAMPION_ENABLE_DAY = new ArrayList<>();
	public static int CHANCE_SPAWN_WEAK;
	public static int CHANCE_SPAWN_SUPER;
	public static int CHANCE_SPAWN_HARD;
	public static Map<Stats, Double> CHAMPION_STAT_WEAK = new HashMap<>();
	public static Map<Stats, Double> CHAMPION_STAT_SUPER = new HashMap<>();
	public static Map<Stats, Double> CHAMPION_STAT_HARD = new HashMap<>();
	public static List<RewardHolder> CHAMPION_REWARD_WEAK = new ArrayList<>();
	public static List<RewardHolder> CHAMPION_REWARD_SUPER = new ArrayList<>();
	public static List<RewardHolder> CHAMPION_REWARD_HARD = new ArrayList<>();
	
	public static double CHAMPION_WEAK_BONUS_RATE_EXP;
	public static double CHAMPION_WEAK_BONUS_RATE_SP;
	public static double CHAMPION_SUPER_BONUS_RATE_EXP;
	public static double CHAMPION_SUPER_BONUS_RATE_SP;
	public static double CHAMPION_HARD_BONUS_RATE_EXP;
	public static double CHAMPION_HARD_BONUS_RATE_SP;
	
	public static double CHAMPION_WEAK_BONUS_DROP;
	public static double CHAMPION_SUPER_BONUS_DROP;
	public static double CHAMPION_HARD_BONUS_DROP;
	public static double CHAMPION_WEAK_ADENAS;
	public static double CHAMPION_SUPER_ADENAS;
	public static double CHAMPION_HARD_ADENAS;
	public static double CHAMPION_WEAK_SEAL_STONES;
	public static double CHAMPION_SUPER_SEAL_STONES;
	public static double CHAMPION_HARD_SEAL_STONES;
	public static double CHAMPION_WEAK_BONUS_SPOIL;
	public static double CHAMPION_SUPER_BONUS_SPOIL;
	public static double CHAMPION_HARD_BONUS_SPOIL;
	public static double CHAMPION_BONUS_HERB;
	public static double CHAMPION_BONUS_SEED;
	
	// Config Bonus Exp/Sp ---------------------------------------------------------------------------------------- //
	public static boolean ENABLE_BonusWeekend;
	public static List<WeekDayType> BONUS_WEEKEND_ENABLE_DAY = new ArrayList<>();
	public static double BONUS_WEEKEND_RATE_EXP;
	public static double BONUS_WEEKEND_RATE_SP;
	
	public static double BONUS_WEEKEND_DROP;
	public static double BONUS_WEEKEND_SPOIL;
	public static double BONUS_WEEKEND_HERB;
	public static double BONUS_WEEKEND_SEED;
	// Vote Reward ------------------------------------------------------------------------------------------------ //
	public static boolean ENABLE_VoteReward;
	public static int TIME_CHECK_VOTES;
	public static Map<Integer, RewardHolder> VOTE_REWARDS = new LinkedHashMap<>();
	public static boolean ENABLE_HOPZONE;
	public static boolean ENABLE_TOPZONE;
	public static boolean ENABLE_NETWORK;
	public static String HOPZONE_URL;
	public static String TOPZONE_URL;
	public static String NETWORK_URL;
	
	public static boolean ENABLE_VoteRewardIndivualHopzone;
	public static boolean ENABLE_VoteRewardIndivualTopzone;
	public static boolean ENABLE_VoteRewardIndivualNetwork;
	public static int INDIVIDUAL_VOTE_TIME_VOTE;
	public static RewardHolder INDIVIDUAL_VOTE_REWARD = new RewardHolder(57, 1000);
	public static int INDIVIDUAL_VOTE_TIME_CAN_VOTE;// hs
	// AntiBot ---------------------------------------------------------------------------------------------------- //
	public static boolean ENABLE_AntiBot;
	public static int TIME_CHECK_ANTIBOT;// segundos
	public static int KILLER_MONSTERS_ANTIBOT;
	public static boolean KILLER_MONSTERS_ANTIBOT_INCREASE_LEVEL;
	// Random Boss Spawn ------------------------------------------------------------------------------------------ //
	public static boolean ENABLE_RandomBossSpawn;
	public static List<WeekDayType> RANDOM_BOSS_SPAWN_ENABLE_DAY = new ArrayList<>();
	public static int RANDOM_BOSS_SPWNNED_TIME;
	public static List<Integer> RANDOM_BOSS_NPC_ID = new ArrayList<>();
	public static List<RewardHolder> RANDOM_BOSS_REWARDS = new ArrayList<>();
	// NewCharacterCreated ---------------------------------------------------------------------------------------- //
	public static String NEW_CHARACTER_CREATED_TITLE;
	public static boolean NEW_CHARACTER_CREATED_GIVE_BUFF;
	public static List<IntIntHolder> NEW_CHARACTER_CREATED_BUFFS_WARRIOR = new ArrayList<>();
	public static List<IntIntHolder> NEW_CHARACTER_CREATED_BUFFS_MAGE = new ArrayList<>();
	public static String NEW_CHARACTER_CREATED_SEND_SCREEN_MSG = "";
	// AIO -------------------------------------------------------------------------------------------------------- //
	public static Map<Stats, Double> AIO_STATS = new HashMap<>();
	
	public static boolean AIO_CAN_EXIT_PEACE_ZONE;
	public static String AIO_TITLE;
	public static List<IntIntHolder> AIO_LIST_SKILLS = new ArrayList<>();
	public static List<IntIntHolder> AIO_LIST_SKILLS_2 = new ArrayList<>();
	public static boolean AIO_SET_MAX_LVL;
	public static int AIO_ITEM_ID;
	
	public static boolean ALLOW_AIO_NCOLOR;
	public static int AIO_NCOLOR;
	public static boolean ALLOW_AIO_TCOLOR;
	public static int AIO_TCOLOR;
	public static boolean INTERACT_NPC;
	// VIP -------------------------------------------------------------------------------------------------------- //
	public static Map<Stats, Double> VIP_STATS = new HashMap<>();
	public static double VIP_BONUS_XP;
	public static double VIP_BONUS_SP;
	public static double VIP_BONUS_DROP_NORMAL_AMOUNT;
	public static double VIP_BONUS_DROP_HERB_AMOUNT;
	public static double VIP_BONUS_DROP_SPOIL_AMOUNT;
	public static double VIP_BONUS_DROP_SEED_AMOUNT;
	
	public static double VIP_BONUS_DROP_NORMAL_CHANCE;
	public static double VIP_BONUS_DROP_HERB_CHANCE;
	public static double VIP_BONUS_DROP_SPOIL_CHANCE;
	public static double VIP_BONUS_DROP_SEED_CHANCE;
	
	public static boolean ALLOW_VIP_NCOLOR;
	public static int VIP_NCOLOR;
	public static boolean ALLOW_VIP_TCOLOR;
	public static int VIP_TCOLOR;
	
	/** Vip Coin */
	public static int VIP_ITEM_ID_1_DAY;	
	public static int VIP_ITEM_ID_7_DAY;	
	public static int VIP_ITEM_ID_15_DAY;	
	public static int VIP_ITEM_ID_30_DAY;	
	
	/** Aio Coin */
	public static int AIO_ITEM_ID_7_DAY;	
	public static int AIO_ITEM_ID_15_DAY;	
	public static int AIO_ITEM_ID_30_DAY;	
	
	public static void load()
	{
		loadAntibot();
		loadFakes();
		loadCommunity();
		loadOfflineShop();
		loadVip();
		loadAio();
		loadNewCharacter();
		loadRandomBoss();		
		loadEventBonusWeekend();
		loadChampions();
		loadAnnounceKillBoss();
		loadColorAmountPvp();
		loadEnchantAbnormalEffect();
		loadPvpReward();
		loadSpreeKills();
		loadSubclassAcumulatives();
		cityElpys();
		loadnpcbufferscheme();
	}
	
	private static void loadnpcbufferscheme()
	{
		UtilProperties config = load(NPC_BUFFER_SCHEME);
		
		NPC_BUFFER_FREE_BUFFS = Boolean.parseBoolean(config.getProperty("FreeBuffs", "false"));
		NPC_BUFFER_BUFF_PRICE = Integer.parseInt(config.getProperty("BuffPrice", "100000"));
        NPC_BUFFER_BUFF_SET_PRICE = Integer.parseInt(config.getProperty("BuffSetPrice", "1500000"));
        NPC_BUFFER_CONSUMABLE_ID = Integer.parseInt(config.getProperty("ConsumableId", "57"));
        NPC_BUFFER_BUFF_REMOVE_PRICE = Integer.parseInt(config.getProperty("BuffRemovePrice", "1000"));
        NPC_BUFFER_SCHEME_BUFF_PRICE = Integer.parseInt(config.getProperty("SchemeBuffPrice", "3000000"));
        NPC_BUFFER_SCHEMES_PER_PLAYER = Integer.parseInt(config.getProperty("SchemesPerPlayer", "3"));
        NPC_BUFFER_MAX_SCHEME_BUFFS = Integer.parseInt(config.getProperty("MaxSchemeBuffs", "32"));
        NPC_BUFFER_MAX_SCHEME_DANCE = Integer.parseInt(config.getProperty("MaxSchemeDance", "16")); 
        
        NPC_BUFFER_FREE_BUFFS_2 = Boolean.parseBoolean(config.getProperty("FreeBuffs2", "false"));
		NPC_BUFFER_BUFF_PRICE_2 = Integer.parseInt(config.getProperty("BuffPrice2", "100000"));
        NPC_BUFFER_BUFF_SET_PRICE_2 = Integer.parseInt(config.getProperty("BuffSetPrice2", "1500000"));
        NPC_BUFFER_CONSUMABLE_ID_2 = Integer.parseInt(config.getProperty("ConsumableId2", "57"));
        NPC_BUFFER_BUFF_REMOVE_PRICE_2 = Integer.parseInt(config.getProperty("BuffRemovePrice2", "1000"));
        NPC_BUFFER_SCHEME_BUFF_PRICE_2 = Integer.parseInt(config.getProperty("SchemeBuffPrice2", "3000000"));
        NPC_BUFFER_SCHEMES_PER_PLAYER_2 = Integer.parseInt(config.getProperty("SchemesPerPlayer2", "3"));
        NPC_BUFFER_MAX_SCHEME_BUFFS_2 = Integer.parseInt(config.getProperty("MaxSchemeBuffs2", "32"));
        NPC_BUFFER_MAX_SCHEME_DANCE_2 = Integer.parseInt(config.getProperty("MaxSchemeDance2", "16"));   

        NPC_BUFFER_FREE_BUFFS_3 = Boolean.parseBoolean(config.getProperty("FreeBuffs3", "false"));
		NPC_BUFFER_BUFF_PRICE_3 = Integer.parseInt(config.getProperty("BuffPrice3", "100000"));
        NPC_BUFFER_BUFF_SET_PRICE_3 = Integer.parseInt(config.getProperty("BuffSetPrice3", "1500000"));
        NPC_BUFFER_CONSUMABLE_ID_3 = Integer.parseInt(config.getProperty("ConsumableId3", "57"));
        NPC_BUFFER_BUFF_REMOVE_PRICE_3 = Integer.parseInt(config.getProperty("BuffRemovePrice3", "1000"));
        NPC_BUFFER_SCHEME_BUFF_PRICE_3 = Integer.parseInt(config.getProperty("SchemeBuffPrice3", "3000000"));
        NPC_BUFFER_SCHEMES_PER_PLAYER_3 = Integer.parseInt(config.getProperty("SchemesPerPlayer3", "3"));
        NPC_BUFFER_MAX_SCHEME_BUFFS_3 = Integer.parseInt(config.getProperty("MaxSchemeBuffs3", "32"));
        NPC_BUFFER_MAX_SCHEME_DANCE_3 = Integer.parseInt(config.getProperty("MaxSchemeDance3", "16"));   
        
        NPC_BUFFER_FREE_BUFFS_4 = Boolean.parseBoolean(config.getProperty("FreeBuffs4", "false"));
		NPC_BUFFER_BUFF_PRICE_4 = Integer.parseInt(config.getProperty("BuffPrice4", "100000"));
        NPC_BUFFER_BUFF_SET_PRICE_4 = Integer.parseInt(config.getProperty("BuffSetPrice4", "1500000"));
        NPC_BUFFER_CONSUMABLE_ID_4 = Integer.parseInt(config.getProperty("ConsumableId4", "57"));
        NPC_BUFFER_BUFF_REMOVE_PRICE_4 = Integer.parseInt(config.getProperty("BuffRemovePrice4", "1000"));
        NPC_BUFFER_SCHEME_BUFF_PRICE_4 = Integer.parseInt(config.getProperty("SchemeBuffPrice4", "3000000"));
        NPC_BUFFER_SCHEMES_PER_PLAYER_4 = Integer.parseInt(config.getProperty("SchemesPerPlayer4", "3"));
        NPC_BUFFER_MAX_SCHEME_BUFFS_4 = Integer.parseInt(config.getProperty("MaxSchemeBuffs4", "32"));
        NPC_BUFFER_MAX_SCHEME_DANCE_4 = Integer.parseInt(config.getProperty("MaxSchemeDance4", "16"));   
        
        
	}
	
	private static void loadAntibot()
	{
		UtilProperties config = load(ANTI_BOOT_FILE);
		
		ENABLE_AntiBot = config.getProperty("Enable_AntiBot", false);
		TIME_CHECK_ANTIBOT = config.getProperty("TimeCheckAntiBot", 30);
		KILLER_MONSTERS_ANTIBOT = config.getProperty("KillerMonstersCount", 30);
		KILLER_MONSTERS_ANTIBOT_INCREASE_LEVEL = config.getProperty("KillerMonstersCountIncreaseLevel", true);
	}
	
	private static void loadFakes()
	{
		UtilProperties config = load(FAKES_PLAYERS_FILE);
		
		ENABLE_fakePlayers = config.getProperty("EnableFakePlayers", true);
		FAKE_LEVEL = config.getProperty("FakeLevel", 81);
		FAKE_MAX_PVP = config.getProperty("FakeMaxPvP", 10);
		FAKE_MAX_PK = config.getProperty("FakeMaxPk", 10);
		FAKE_CHANCE_SIT = config.getProperty("FakeChanceSit", 30);
		FAKE_CHANCE_HAS_CLAN = config.getProperty("FakeChanceHasClan", 50);
		SPAWN_RANGE = config.getProperty("FakeSpawnRange", 200);
	}
	
	private static void loadCommunity()
	{
		UtilProperties config = load(COMMUNITY_FILE);
		
		ENABLE_BBS_REGION = config.getProperty("EnableBbsRegion", false);
		ENABLE_BBS_CLAN = config.getProperty("EnableBbsClan", false);
		ENABLE_BBS_HOME = config.getProperty("EnableBbsHome", false);
		// Auction
		ENABLE_BBS_MEMO = config.getProperty("EnableBbsMemo", false);
		COMMISION_FOR_START_SELL = config.getProperty("CommisionForStartSell", 10000);
		COMMISION_FOR_START_SELL_ID = config.getProperty("CommisionForStartSellId", 57);
		COMMISION_FOR_END_SELL = config.getProperty("CommisionForEndSell", 10);
		// rebirth
		ENABLE_BBS_FAVORITE = config.getProperty("EnableBbsFavorite", false);
		MAX_REBIRTH = config.getProperty("MaxRebirth", 10);
		STAT_POINT_PER_REBIRTH = config.getProperty("StatPointPerRebirth", 10);
		MASTERY_POINT_PER_REBIRTH = config.getProperty("MasteryPointPerRebirth", 2);
		LVL_REBIRTH = config.getProperty("LvlRebirth", 1);
	}
	
	private static void loadOfflineShop()
	{
		UtilProperties config = load(OFFLINE_SHOP_FILE);
		
		OFFLINE_TRADE_ENABLE = false; //config.getProperty("OfflineTradeEnable", false);
		OFFLINE_SELLBUFF_ENABLE = config.getProperty("OfflineSellBuffEnable", false);
		OFFLINE_MODE_IN_PEACE_ZONE = config.getProperty("OfflineModeInPeaceZone", false);
		OFFLINE_MODE_NO_DAMAGE = config.getProperty("OfflineModeNoDamage", false);
		OFFLINE_SET_NAME_COLOR = config.getProperty("OfflineSetNameColor", false);
		OFFLINE_NAME_COLOR = Integer.decode("0x" + config.getProperty("OfflineNameColor", 808080));
		OFFLINE_MAX_DAYS = config.getProperty("OfflineMaxDays", 10);
		OFFLINE_DISCONNECT_FINISHED = config.getProperty("OfflineDisconnectFinished", true);
	}
	
	private static void loadVip()
	{
		UtilProperties config = load(VIP_FILE);
		
		VIP_STATS = parseStats(config, "_vip");
		VIP_BONUS_XP = config.getProperty("VipBonusExp", 1.3);
		VIP_BONUS_SP = config.getProperty("VipBonusSp", 1.3);
		VIP_BONUS_DROP_NORMAL_AMOUNT = config.getProperty("VipBonusDropAmountNormal", 1.0);
		VIP_BONUS_DROP_HERB_AMOUNT = config.getProperty("VipBonusDropAmountHerb", 1.0);
		VIP_BONUS_DROP_SPOIL_AMOUNT = config.getProperty("VipBonusDropAmountSpoil", 1.0);
		VIP_BONUS_DROP_SEED_AMOUNT = config.getProperty("VipBonusDropAmountSeed", 1.0);
		
		VIP_BONUS_DROP_NORMAL_CHANCE = config.getProperty("VipBonusDropChanceNormal", 1.0);
		VIP_BONUS_DROP_HERB_CHANCE = config.getProperty("VipBonusDropChanceHerb", 1.0);
		VIP_BONUS_DROP_SPOIL_CHANCE = config.getProperty("VipBonusDropChanceSpoil", 1.0);
		VIP_BONUS_DROP_SEED_CHANCE = config.getProperty("VipBonusDropChanceSeed", 1.0);
		
		ALLOW_VIP_NCOLOR = config.getProperty("AllowVipNameColor", false);
		VIP_NCOLOR = Integer.decode("0x" + config.getProperty("VipNameColor", "88AA88"));
		ALLOW_VIP_TCOLOR = config.getProperty("AllowVipTitleColor", false);
		VIP_TCOLOR = Integer.decode("0x" + config.getProperty("VipTitleColor", "88AA88"));
		VIP_ITEM_ID_1_DAY = Integer.parseInt(config.getProperty("Vip_ItemId_1Day", "9972")); 
		VIP_ITEM_ID_7_DAY = Integer.parseInt(config.getProperty("Vip_ItemId_7Day", "9973")); 
		VIP_ITEM_ID_15_DAY = Integer.parseInt(config.getProperty("Vip_ItemId_15Day", "9974")); 
		VIP_ITEM_ID_30_DAY = Integer.parseInt(config.getProperty("Vip_ItemId_30Day", "9975")); 
	}
	
	private static void loadAio()
	{
		UtilProperties config = load(AIO_FILE);
		
		AIO_STATS = parseStats(config, "_aio");
		AIO_CAN_EXIT_PEACE_ZONE = config.getProperty("AioCanExitPeaceZone", false);
		AIO_TITLE = config.getProperty("AioTitle", "-=[ AIO ]=-");
		AIO_LIST_SKILLS = parseBuff(config, "AioSkills");
		AIO_SET_MAX_LVL = config.getProperty("AioSetMaxLevel", false);
		AIO_ITEM_ID = config.getProperty("AioItemId", 8207);
		
		ALLOW_AIO_NCOLOR = config.getProperty("AllowAioNameColor", false);
		AIO_NCOLOR = Integer.decode("0x" + config.getProperty("AioNameColor", "88AA88"));
		ALLOW_AIO_TCOLOR = config.getProperty("AllowAioTitleColor", false);
		AIO_TCOLOR = Integer.decode("0x" + config.getProperty("AioTitleColor", "88AA88"));
		INTERACT_NPC = config.getProperty("InteractNpc", true);
		
		AIO_ITEM_ID_7_DAY = Integer.parseInt(config.getProperty("Aio_ItemId_7Day", "9976")); 
		AIO_ITEM_ID_15_DAY = Integer.parseInt(config.getProperty("Aio_ItemId_15Day", "9977")); 
		AIO_ITEM_ID_30_DAY = Integer.parseInt(config.getProperty("Aio_ItemId_30Day", "9978")); 
	}
	
	private static void loadNewCharacter()
	{
		UtilProperties config = load(NEW_CHARACTER_CREATED_FILE);
		
		NEW_CHARACTER_CREATED_TITLE = config.getProperty("NewCharacterCreatedTitle", "L2DevsCustom");
		NEW_CHARACTER_CREATED_GIVE_BUFF = config.getProperty("NewCharacterCreatedBuff", false);
		NEW_CHARACTER_CREATED_BUFFS_WARRIOR = parseBuff(config, "NewCharacterCreatedBuffsWarriorList");
		NEW_CHARACTER_CREATED_BUFFS_MAGE = parseBuff(config, "NewCharacterCreatedBuffsMageList");
		NEW_CHARACTER_CREATED_SEND_SCREEN_MSG = config.getProperty("NewCharacterCreatedSendScreenMsg", "http://l2devsadmins.com/");
	}
	
	public static List<IntIntHolder> parseBuff(Properties config, String key) {
	    List<IntIntHolder> buffs = new ArrayList<>();
	    String buffList = config.getProperty(key);
	    
	    if (buffList != null && !buffList.isEmpty()) {
	        String[] buffsArray = buffList.split(";");
	        for (String buff : buffsArray) {
	            try {
	                String[] skillData = buff.split(",");
	                int skillId = Integer.parseInt(skillData[0].trim());
	                int skillLevel = Integer.parseInt(skillData[1].trim());
	                buffs.add(new IntIntHolder(skillId, skillLevel));
	            } catch (Exception e) {
	                System.err.println("Error parsing buff format for key: " + key + " - " + e.getMessage());
	            }
	        }
	    }
	    return buffs;
	}
	
	private static void loadRandomBoss()
	{
		UtilProperties config = load(RANDOM_BOSS_SPAWN_FILE);
		
		ENABLE_RandomBossSpawn = config.getProperty("Enable_RandomBossSpawn", false);
		RANDOM_BOSS_SPAWN_ENABLE_DAY = parseWeekDay(config, "RandomBossSpawnEnableDay");
		String aux = config.getProperty("RandomBossSpawnId", "10447,10450,10444,10490").trim();
		for (String info : aux.split(","))
		{
			RANDOM_BOSS_NPC_ID.add(Integer.parseInt(info));
		}
		RANDOM_BOSS_REWARDS = parseReward(config, "RandomBossRewards");
		RANDOM_BOSS_SPWNNED_TIME = config.getProperty("RandomBossSpawnTime", 10);
	}
		
	private static void loadEventBonusWeekend()
	{
		UtilProperties config = load(BONUS_WEEKEND_FILE);
		
		ENABLE_BonusWeekend = config.getProperty("Enable_BonusExpSp", false);
		BONUS_WEEKEND_ENABLE_DAY = parseWeekDay(config, "BonusExpSpEnableDay");
		BONUS_WEEKEND_RATE_EXP = config.getProperty("BonusExp", 1.2);
		BONUS_WEEKEND_RATE_SP = config.getProperty("BonusSp", 1.2);
		
		BONUS_WEEKEND_DROP = config.getProperty("BonusDrop", 1.2);
		BONUS_WEEKEND_SPOIL = config.getProperty("BonusSpoil", 1.2);
		BONUS_WEEKEND_HERB = config.getProperty("BonusHerb", 1.2);
		BONUS_WEEKEND_SEED = config.getProperty("BonusSeed", 1.2);
	}
	
	private static void loadChampions()
	{
		UtilProperties config = load(CHAMPIONS_FILE);
		
		ENABLE_Champions = config.getProperty("Enable_Champions", false);
		CHAMPION_ENABLE_DAY = parseWeekDay(config, "ChampionEnableDay");
		
		CHANCE_SPAWN_WEAK = config.getProperty("ChanceSpawnWeak", 10);
		CHANCE_SPAWN_SUPER = config.getProperty("ChanceSpawnSuper", 10);
		CHANCE_SPAWN_HARD = config.getProperty("ChanceSpawnHard", 10);
		
		CHAMPION_STAT_WEAK = parseStats(config, "_Weak");
		CHAMPION_STAT_SUPER = parseStats(config, "_Super");
		CHAMPION_STAT_HARD = parseStats(config, "_Hard");
		CHAMPION_REWARD_WEAK = parseReward(config, "RewardsToKillWeak");
		CHAMPION_REWARD_SUPER = parseReward(config, "RewardsToKillSuper");
		CHAMPION_REWARD_HARD = parseReward(config, "RewardsToKillHard");
		
		CHAMPION_WEAK_BONUS_RATE_EXP = config.getProperty("BonusWeakExp", 1.2);
		CHAMPION_WEAK_BONUS_RATE_SP = config.getProperty("BonusWeakSp", 1.2);
		CHAMPION_SUPER_BONUS_RATE_EXP = config.getProperty("BonusSuperExp", 1.2);
		CHAMPION_SUPER_BONUS_RATE_SP = config.getProperty("BonusSuperSp", 1.2);
		CHAMPION_HARD_BONUS_RATE_EXP = config.getProperty("BonusHardExp", 1.2);
		CHAMPION_HARD_BONUS_RATE_SP = config.getProperty("BonusHardSp", 1.2);
		
		CHAMPION_WEAK_ADENAS = config.getProperty("BonusWeakAdenas", 1.2);
		CHAMPION_SUPER_ADENAS = config.getProperty("BonusSuperAdenas", 1.2);
		CHAMPION_HARD_ADENAS = config.getProperty("BonusHardAdenas", 1.2);
		CHAMPION_WEAK_SEAL_STONES = config.getProperty("BonusWeakSealStone", 1.2);
		CHAMPION_SUPER_SEAL_STONES = config.getProperty("BonusSuperSealStone", 1.2);
		CHAMPION_HARD_SEAL_STONES = config.getProperty("BonusHardSealStone", 1.2);
		
		CHAMPION_WEAK_BONUS_DROP = config.getProperty("BonusWeakDrop", 1.2);
		CHAMPION_SUPER_BONUS_DROP = config.getProperty("BonusSuperDrop", 1.2);
		CHAMPION_HARD_BONUS_DROP = config.getProperty("BonusHardDrop", 1.2);
		CHAMPION_WEAK_BONUS_SPOIL = config.getProperty("BonusweakSpoil", 1.2);
		CHAMPION_SUPER_BONUS_SPOIL = config.getProperty("BonusSuperSpoil", 1.2);
		CHAMPION_HARD_BONUS_SPOIL = config.getProperty("BonusHardSpoil", 1.2);
		CHAMPION_BONUS_HERB = config.getProperty("BonusHerb", 1.2);
		CHAMPION_BONUS_SEED = config.getProperty("BonusSeed", 1.2);
	}
	
	private static void loadAnnounceKillBoss()
	{
		UtilProperties config = load(ANNOUNCE_KILL_BOOS_FILE);
		
		ENABLE_AnnounceKillBoss = config.getProperty("Enable_AnnounceKillBoss", false);
		ANNOUNCE_KILL_BOSS_ENABLE_DAY = parseWeekDay(config, "AnnounceKillBossEnableDay");
		ANNOUNCE_KILL_BOSS = config.getProperty("AnnounceKillBoss", "%s1 ah matado al RaidBoss %s2");
		ANNOUNCE_KILL_GRANDBOSS = config.getProperty("AnnounceKillGrandBoss", "%s1 ah matado al GrandBoss %s2");
	}
	
	private static void loadColorAmountPvp()
	{
		UtilProperties config = load(COLOR_AMOUNT_PVP_FILE);
		String aux = "";
		
		ENABLE_ColorAccordingAmountPvPorPk = config.getProperty("Enable_ColorAccordingAmountPvPorPk", false);
		aux = config.getProperty("PvpColorName").trim();
		for (String colorInfo : aux.split(";"))
		{
			final String[] infos = colorInfo.split(",");
			PVP_COLOR_NAME.put(Integer.valueOf(infos[0]), infos[1]);
		}
		aux = config.getProperty("PkColorTitle").trim();
		for (String colorInfo : aux.split(";"))
		{
			final String[] infos = colorInfo.split(",");
			PK_COLOR_TITLE.put(Integer.valueOf(infos[0]), infos[1]);
		}
	}
	
	private static void loadEnchantAbnormalEffect()
	{
		UtilProperties config = load(ENCHANT_ABNORMAL_EFFECT_FILE);
		
		ENABLE_EnchantAbnormalEffectArmor = config.getProperty("Enable_EnchantAbnormalEffectArmor", false);
		ENCHANT_EFFECT_LVL = config.getProperty("EnchantEffectLevel", 6);
	}
	
	private static void loadPvpReward()
	{
		UtilProperties config = load(PVP_REWARD_FILE);
		
		ENABLE_PvpReward = config.getProperty("Enable_PvpReward", false);
		PVP_REWARDS = parseReward(config, "PvpReward");
		PVP_TIME = config.getProperty("PvpTime", 5000);
	}
	
	private static void loadSpreeKills()
	{
		UtilProperties config = load(SPREE_KILLS_FILE);
		
		ENABLE_SpreeKills = config.getProperty("Enable_SpreeKills", false);
		String aux = config.getProperty("AnnouncementsKills").trim();
		for (String announcements : aux.split(";"))
		{
			final String[] infos = announcements.split(",");
			ANNOUNCEMENTS_KILLS.put(Integer.valueOf(infos[0]), infos[1]);
		}
	}
	
	private static void loadSubclassAcumulatives()
	{
		UtilProperties config = load(SUBCLASS_ACUMULATIVES_FILE);
		
		ENABLE_SubClassAcumulatives = config.getProperty("Enable_SubClassAcumulatives", false);
		ACUMULATIVE_PASIVE_SKILLS = config.getProperty("AcumulativePasiveSkill", false);
		String aux = config.getProperty("DontAcumulativeSkills").trim();
		for (String subAcuInfo : aux.split(","))
		{
			DONT_ACUMULATIVE_SKILLS_ID.add(Integer.valueOf(subAcuInfo));
		}
	}
	
	private static void cityElpys()
	{
		UtilProperties config = load(CITY_ELPY_FILE);
		
		ELPY_Enabled = config.getProperty("Enabled", true);
		ELPY_ENABLE_DAY = parseWeekDay(config, "EnableDay");
		ELPY = config.getProperty("MobId", 20432);
		ELPY_COUNT = config.getProperty("MobCount", 50);
		ELPY_RANGE_SPAWN = config.getProperty("EventRangeSpawn", 1000);
		ELPY_EVENT_TIME = config.getProperty("EventTime", 60);
		ELPY_LOC = parseLocation(config, "EventLoc");
		ELPY_REWARDS = parseReward(config, "EventRewards");
	}
	
	// MISC ------------------------------------------------------------------------------------------------------- //
	private static UtilProperties load(String filename)
	{
		UtilProperties result = new UtilProperties();
		File file = new File(filename);
		
		try
		{
			result.load(new File(filename));
		}
		catch (Exception e)
		{
			LOG.warning("Error loading config : " + file.getName() + "!");
		}
		
		return result;
	}
	
	// PARSERS ---------------------------------------------------------------------------------------------------- //
	private static Map<Stats, Double> parseStats(UtilProperties propertie, String endConfigName)
	{
		Map<Stats, Double> map = new HashMap<>();
		
		for (Stats st : Stats.values())
		{
			map.put(st, propertie.getProperty(st.name() + endConfigName, 1.0));
		}
		
		return map;
	}
	
	private static List<Location> parseLocation(UtilProperties propertie, String configName)
	{
		List<Location> auxReturn = new ArrayList<>();
		
		String aux = propertie.getProperty(configName).trim();
		for (String loc : aux.split(";"))
		{
			final String[] infos = loc.split(",");
			auxReturn.add(new Location(Integer.parseInt(infos[0]), Integer.parseInt(infos[1]), Integer.parseInt(infos[2])));
		}
		
		return auxReturn;
	}
	
	private static List<RewardHolder> parseReward(UtilProperties propertie, String configName)
	{
		List<RewardHolder> auxReturn = new ArrayList<>();
		
		String aux = propertie.getProperty(configName).trim();
		for (String pvpReward : aux.split(";"))
		{
			final String[] infos = pvpReward.split(",");
			if (infos.length > 2)
			{
				auxReturn.add(new RewardHolder(Integer.valueOf(infos[0]), Integer.valueOf(infos[1]), Integer.valueOf(infos[2])));
				
			}
			else
			{
				auxReturn.add(new RewardHolder(Integer.valueOf(infos[0]), Integer.valueOf(infos[1])));
			}
		}
		
		return auxReturn;
	}
	
	private static List<WeekDayType> parseWeekDay(UtilProperties propertie, String configName)
	{
		List<WeekDayType> auxReturn = new ArrayList<>();
		
		String aux = propertie.getProperty(configName, "SUNDAY").trim();
		for (String info : aux.split(","))
		{
			auxReturn.add(WeekDayType.valueOf(info));
		}
		
		return auxReturn;
	}
	
	
}