package net.sf.l2j.gameserver.data;

import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.model.actor.FakePc;

/**
 * @author destr
 *
 */
public class FakePcsTable
{
  /** The logger<br> */
  private static Logger _log = Logger.getLogger(FakePcsTable.class.getName());

  private HashMap<Integer, FakePc> _fakePcs = new HashMap<>();

  private FakePcsTable()
  {
      loadData();
  }

  private void loadData()
  {
      _fakePcs.clear();     

      try (Connection con = L2DatabaseFactory.getInstance().getConnection();    	  
    	  PreparedStatement stmt = con.prepareStatement("SELECT * FROM `fake_pcs`");
    	  ResultSet rset = stmt.executeQuery())
    	  {
			FakePc fpc = null;

			while (rset.next())
			{
			    fpc = new FakePc();

			    int npcId = rset.getInt("npc_id");
			    fpc.race = rset.getInt("race");
			    fpc.sex = rset.getInt("sex");
			    fpc.clazz = rset.getInt("class");
			    fpc.title = rset.getString("title");
			    fpc.titleColor = Integer.decode("0x" + rset.getString("title_color"));
			    fpc.name = rset.getString("name");
			    fpc.nameColor = Integer.decode("0x" + rset.getString("name_color"));
			    fpc.hairStyle = rset.getInt("hair_style");
			    fpc.hairColor = rset.getInt("hair_color");
			    fpc.face = rset.getInt("face");
			    fpc.mount = rset.getByte("mount");
			    fpc.team = rset.getByte("team");
			    fpc.hero = rset.getByte("hero");
			    fpc.pdUnder = rset.getInt("pd_under");
			    fpc.pdUnderAug = rset.getInt("pd_under_aug");
			    fpc.pdHead = rset.getInt("pd_head");
			    fpc.pdHeadAug = rset.getInt("pd_head_aug");
			    fpc.pdRHand = rset.getInt("pd_rhand");
			    fpc.pdRHandAug = rset.getInt("pd_rhand_aug");
			    fpc.pdLHand = rset.getInt("pd_lhand");
			    fpc.pdLHandAug = rset.getInt("pd_lhand_aug");
			    fpc.pdGloves = rset.getInt("pd_gloves");
			    fpc.pdGlovesAug = rset.getInt("pd_gloves_aug");
			    fpc.pdChest = rset.getInt("pd_chest");
			    fpc.pdChestAug = rset.getInt("pd_chest_aug");
			    fpc.pdLegs = rset.getInt("pd_legs");
			    fpc.pdLegsAug = rset.getInt("pd_legs_aug");
			    fpc.pdFeet = rset.getInt("pd_feet");
			    fpc.pdFeetAug = rset.getInt("pd_feet_aug");
			    fpc.pdBack = rset.getInt("pd_back");
			    fpc.pdBackAug = rset.getInt("pd_back_aug");
			    fpc.pdLRHand = rset.getInt("pd_lrhand");
			    fpc.pdLRHandAug = rset.getInt("pd_lrhand_aug");
			    fpc.pdHair = rset.getInt("pd_hair");
			    fpc.pdHairAug = rset.getInt("pd_hair_aug");
			    fpc.pdHair2 = rset.getInt("pd_hair2");
			    fpc.pdHair2Aug = rset.getInt("pd_hair2_aug");
			    fpc.pdRBracelet = rset.getInt("pd_rbracelet");
			    fpc.pdRBraceletAug = rset.getInt("pd_rbracelet_aug");
			    fpc.pdLBracelet = rset.getInt("pd_lbracelet");
			    fpc.pdLBraceletAug = rset.getInt("pd_lbracelet_aug");
			    fpc.pdDeco1 = rset.getInt("pd_deco1");
			    fpc.pdDeco1Aug = rset.getInt("pd_deco1_aug");
			    fpc.pdDeco2 = rset.getInt("pd_deco2");
			    fpc.pdDeco2Aug = rset.getInt("pd_deco2_aug");
			    fpc.pdDeco3 = rset.getInt("pd_deco3");
			    fpc.pdDeco3Aug = rset.getInt("pd_deco3_aug");
			    fpc.pdDeco4 = rset.getInt("pd_deco4");
			    fpc.pdDeco4Aug = rset.getInt("pd_deco4_aug");
			    fpc.pdDeco5 = rset.getInt("pd_deco5");
			    fpc.pdDeco5Aug = rset.getInt("pd_deco5_aug");
			    fpc.pdDeco6 = rset.getInt("pd_deco6");
			    fpc.pdDeco6Aug = rset.getInt("pd_deco6_aug");
			    fpc.enchantEffect = rset.getInt("enchant_effect");
			    fpc.pvpFlag = rset.getInt("pvp_flag");
			    fpc.karma = rset.getInt("karma");
			    fpc.fishing = rset.getByte("fishing");
			    fpc.fishingX = rset.getInt("fishing_x");
			    fpc.fishingY = rset.getInt("fishing_y");
			    fpc.fishingZ = rset.getInt("fishing_z");
			    fpc.invisible = rset.getByte("invisible");
			    _fakePcs.put(npcId, fpc);
			}

    	  }
		catch (Exception e)
		{
			 _log.log(Level.SEVERE, "Error while creating fake pc table: " + e.getMessage(), e);
		}	     
  }

  public void reloadData()
  {
      loadData();
  }

  public FakePc getFakePc(int npcId)
  {
      return _fakePcs.get(npcId);
  }

  public static FakePcsTable getInstance()
  {
      return SingletonHolder._instance;
  }

  private static class SingletonHolder
  {
      protected static final FakePcsTable _instance = new FakePcsTable();
  }
}
