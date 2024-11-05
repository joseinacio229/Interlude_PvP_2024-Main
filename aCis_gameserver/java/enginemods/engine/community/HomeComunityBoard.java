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
package enginemods.engine.community;

import java.util.Map.Entry;

import enginemods.data.ConfigData;
import enginemods.engine.AbstractMods;

import java.util.StringTokenizer;

import enginemods.holders.RewardHolder;
import enginemods.util.builders.html.Html;
import enginemods.util.builders.html.HtmlBuilder;
import enginemods.util.builders.html.HtmlBuilder.HtmlType;
import enginemods.util.builders.html.L2UI;
import enginemods.util.builders.html.L2UI_CH3;
import net.sf.l2j.Config;
import net.sf.l2j.gameserver.data.ItemTable;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.base.Experience;

/***
 * @author fissban
 */
public class HomeComunityBoard extends AbstractMods
{
	public HomeComunityBoard()
	{
		registerMod(ConfigData.ENABLE_BBS_HOME);
	}
	
	@Override
	public void onModState()
	{
		//
	}
	
	@Override
	public boolean onCommunityBoard(Player player, String command)
	{
		StringTokenizer st = new StringTokenizer(command, ";");
		// _bbshome
		String event = st.nextToken();
		
		if (event.equals("_bbshome"))
		{
			// page
			String page = st.hasMoreTokens() ? st.nextToken() : "game";
			
			HtmlBuilder hb = new HtmlBuilder(HtmlType.COMUNITY_TYPE);
			hb.append(Html.HTML_START);
			hb.append("<center>");
			hb.append("<br>");
			hb.append(marcButton(page));
			hb.append("<table border=0 cellspacing=0 cellpadding=0>");
			hb.append("<tr>");
			hb.append(newMenu("GAME INFO", "game"));
			hb.append(newMenu("SERVER INFO", "server"));
			hb.append(newMenu("NOTICIAS", "noticias"));
			hb.append(newMenu("PROMOCIONES", "promociones"));
			hb.append("</tr>");
			hb.append("</table>");
			hb.append(marcButton(page));
			
			hb.append("<br>");
			
			switch (page)
			{
				case "game":
				{
					// hb.append(UtilHtml.htmlHeadCommunity("GAME INFORMATION"));
					
					hb.append("<table fixwidth=529 border=0 cellspacing=0 cellpadding=0>");
					hb.append("<tr>");
					
					hb.append("<td fixwidth=325>");
					hb.append(gameTableMode("Rebirth", true));
					hb.append(gameTableMode("Aio", true));
					hb.append(gameTableMode("Vip", true));
					hb.append(gameTableMode("enchant", true));
					hb.append(gameTableMode("rates", true));
					hb.append(gameTableMode("VoteReward", ConfigData.ENABLE_VoteReward));
					hb.append(gameTableMode("Enchant Abnormal Effect", ConfigData.ENABLE_EnchantAbnormalEffectArmor));
					hb.append(gameTableMode("Title And Name Color", ConfigData.ENABLE_ColorAccordingAmountPvPorPk));
					// hb.append(gameTableMode("Pvp Reward", ConfigData.ENABLE_PvpReward));
					hb.append(gameTableMode("Spree Kills", ConfigData.ENABLE_SpreeKills));
					hb.append(gameTableMode("Announce Kill Boss", ConfigData.ENABLE_AnnounceKillBoss));
					hb.append(gameTableMode("Sell Buff", true));
					hb.append(gameTableMode("Anti Bot", ConfigData.ENABLE_AntiBot));
					hb.append(gameTableMode("Offline Shop", ConfigData.OFFLINE_TRADE_ENABLE || ConfigData.OFFLINE_SELLBUFF_ENABLE));
					hb.append("</td>");
					
					String opc = st.hasMoreTokens() ? st.nextToken() : "Rebirth";
					
					hb.append("<td fixwidth=204 height=200>");
					hb.append(getTopInfo(opc));
					
					hb.append("<table width=204 border=0 cellspacing=0 cellpadding=0>");
					hb.append("<tr>");
					hb.append("<td>");
					
					switch (opc)
					{
						case "Rebirth":
							hb.append(infoRebirth());
							break;
						
						case "Aio":
							hb.append(infoAio());
							break;
						
						case "Vip":
							hb.append(infoVip());
							break;
						
						case "rates":
							hb.append(infoRates());
							break;
						
						case "enchant":
							hb.append(infoEnchant());
							break;
						
						case "VoteReward":
							hb.append(infoVoteReward());
							break;
						
						case "Enchant Abnormal Effect":
							hb.append(infoEnchantAbnormalEffect());
							break;
						
						case "Title And Name Color":
							hb.append(infoTitleAndNameColor());
							break;
						
						case "Spree Kills":
							hb.append(infoSpreeKills());
							break;
						
						case "Sell Buff":
							hb.append(infoSellBuff());
							break;
						
						case "Anti Bot":
							hb.append(infoAntiBot());
							break;
						
						case "Offline Shop":
							hb.append(infoOfflineShop());
							break;
					}
					
					hb.append("</td>");
					hb.append("</tr>");
					hb.append("</table>");
					hb.append("</td>");
					
					hb.append("</tr>");
					hb.append("</table>");
					
					break;
				}
				case "noticias":
				{
					// hb.append(UtilHtml.htmlHeadCommunity("NOTICIAS"));
					break;
				}
				case "server":
				{
					// hb.append(UtilHtml.htmlHeadCommunity("SERVER INFO"));
					break;
				}
				case "promociones":
				{
					// hb.append(UtilHtml.htmlHeadCommunity("PROMOCIONES"));
					break;
				}
			}
			
			// end
			hb.append("</center>");
			hb.append(Html.HTML_END);
			sendCommunity(player, hb.toString());
			return true;
		}
		
		return false;
	}
	
	// XXX SERVER INFO ---------------------------------------------------------------------------------------------
	
	@SuppressWarnings("unused")
	private static String serverInfo(Player player)
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.COMUNITY_TYPE);
		
		// barras superiores
		hb.append("<table border=0 cellspacing=0 cellpadding=0>");
		hb.append("<tr>");
		hb.append("<td>");
		hb.append("<table width=100 border=0 cellspacing=0 cellpadding=0>");// marco top -> start
		hb.append("<tr>");
		hb.append("<td width=16 valign=top align=center height=22>", Html.newImage(L2UI_CH3.FrameBackLeft, 16, 22), "</td>");
		hb.append("<td width=68 valign=top align=center height=22>", Html.newImage(L2UI_CH3.FrameBackMid, 68, 22), "</td>");
		hb.append("<td width=16 valign=top align=center height=22>", Html.newImage(L2UI_CH3.FrameBackRight, 16, 22), "</td>");
		hb.append("</tr>");
		hb.append("</table>");
		hb.append("</td><td>");
		hb.append("<table width=460 border=0 cellspacing=0 cellpadding=0>");// marco top -> start
		hb.append("<tr>");
		hb.append("<td width=16 valign=top align=center height=22>", Html.newImage(L2UI_CH3.FrameBackLeft, 16, 22), "</td>");
		hb.append("<td width=68 valign=top align=center height=22>", Html.newImage(L2UI_CH3.FrameBackMid, 428, 22), "</td>");
		hb.append("<td width=16 valign=top align=center height=22>", Html.newImage(L2UI_CH3.FrameBackRight, 16, 22), "</td>");
		hb.append("</tr>");
		hb.append("</table>");
		hb.append("</td></tr>");
		hb.append("</table>");
		
		hb.append("<table border=0 cellspacing=0 cellpadding=0>");
		hb.append("<tr>");
		// lateral izquierdo
		hb.append("<td align=center>");
		hb.append("");
		hb.append("");
		hb.append("");
		hb.append("");
		hb.append("");
		hb.append("");
		hb.append("");
		hb.append("");
		hb.append("");
		hb.append("");
		hb.append("");
		
		return hb.toString();
	}
	
	// XXX MISC ONCOMMUNITYBOARD -----------------------------------------------------------------------------------
	
	private static String marcButton(String page)
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.HTML_TYPE);
		hb.append("<table border=0 cellspacing=0 cellpadding=0>");
		hb.append("<tr>");
		hb.append("<td>", Html.newImage(page.equals("game") ? "L2UI_CH3.fishing_bar1" : "L2UI_CH3.ssq_cell1", 100, 1), "</td>");
		hb.append("<td>", Html.newImage(page.equals("server") ? "L2UI_CH3.fishing_bar1" : "L2UI_CH3.ssq_cell1", 100, 1), "</td>");
		hb.append("<td>", Html.newImage(page.equals("noticias") ? "L2UI_CH3.fishing_bar1" : "L2UI_CH3.ssq_cell1", 100, 1), "</td>");
		hb.append("<td>", Html.newImage(page.equals("promociones") ? "L2UI_CH3.fishing_bar1" : "L2UI_CH3.ssq_cell1", 100, 1), "</td>");
		hb.append("</tr>");
		hb.append("</table>");
		return hb.toString();
	}
	
	private static String newMenu(String butonName, String bypass)
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.HTML_TYPE);
		hb.append("<td><button value=\"", butonName, "\" action=\"bypass _bbshome;", bypass, "\" width=100 height=32 back=L2UI_CH3.refinegrade3_21 fore=L2UI_CH3.refinegrade3_22></td>");
		return hb.toString();
	}
	
	private static String gameTableMode(String mod, boolean status)
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.HTML_TYPE);
		hb.append("<table fixwidth=325 height=21 border=0 cellspacing=0 cellpadding=0>");
		hb.append("<tr>");
		hb.append("<td align=center valign=center fixwidth=150>", mod, "</td>");
		hb.append("<td align=center fixwidth=75><button value=INFO action=\"bypass _bbshome;game;", mod, "\" width=75 height=21 back=L2UI_ch3.Btn1_normalOn fore=L2UI_ch3.Btn1_normal></td>");
		hb.append("<td align=center fixwidth=100>", status ? Html.newFontColor("3CFF00", "Enable") : Html.newFontColor("FF0000", "Disable"), "</td>");
		hb.append("</tr>");
		hb.append("</table>");
		return hb.toString();
	}
	
	// XXX INFOS ---------------------------------------------------------------------------------------------------
	private static String getTopInfo(String bypass)
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.HTML_TYPE);
		hb.append("<table width=204 height=22 border=0 cellspacing=0 cellpadding=0>");
		hb.append("<tr>");
		hb.append("<td width=16 valign=top align=center height=22>", Html.newImage("L2UI_CH3.FrameBackLeft", 16, 22), "</td>");
		hb.append("<td width=172 align=center height=22><button value=\"", bypass, "\" width=172 height=22 back=L2UI_CH3.FrameBackMid fore=L2UI_CH3.FrameBackMid></td>");
		hb.append("<td width=16 valign=top align=center height=22>", Html.newImage("L2UI_CH3.FrameBackRight", 16, 22), "</td>");
		hb.append("</tr>");
		hb.append("</table>");
		return hb.toString();
	}
	
	private static String infoRebirth()
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.HTML_TYPE);
		hb.append("<br>");
		hb.append("<center>");
		hb.append("Al llegar al level ", Html.newFontColor("LEVEL", Experience.MAX_LEVEL - 1), "<br1>");
		hb.append("podras reiniciar tu personaje a level 1<br1>");
		hb.append("y con ello ganaras puntos extras,<br1>");
		hb.append("que podras sumarlos a tus stats<br1>");
		hb.append("y quizas estar un paso mas cerca de ser<br1>");
		hb.append("un ", Html.newFontColor("LEVEL", "Dios!"), "<br1>");
		hb.append("Tu mision comienza con APOLO<br1>");
		hb.append("</center>");
		return hb.toString();
	}
	
	private static String infoOfflineShop()
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.HTML_TYPE);
		hb.append("<br>");
		hb.append("<center>");
		hb.append("Podran dejar sus personajes<br1>");
		hb.append("vendiendo o comprando en modo<br1>");
		hb.append("offline, esto tambien incluye<br1>");
		hb.append("al sistema sellbuff<br1>");
		hb.append("</center>");
		return hb.toString();
	}
	
	private static String infoAntiBot()
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.HTML_TYPE);
		hb.append("<br>");
		hb.append("<center>");
		hb.append("Sistema para controlar<br1>");
		hb.append("que no esten usando programas<br1>");
		hb.append("externos que lo ayuden a levelear<br1>");
		hb.append("</center>");
		return hb.toString();
	}
	
	private static String infoSellBuff()
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.HTML_TYPE);
		hb.append("<br>");
		hb.append("<center>");
		hb.append("Podran dejar a sus personajes<br1>");
		hb.append("vendiendo sus buffs<br1>");
		hb.append("al parecio que ustedes decidan<br1>");
		hb.append("usando el comando ", Html.newFontColor("LEVEL", ".sellbuff"), "<br1>");
		hb.append("y si desean cancelarlo deberan usar<br1>");
		hb.append("el comando ", Html.newFontColor("LEVEL", ".cancelsellbuff"), ".<br1>");
		hb.append("Si se desconectan sus pjs seguiran<br1>");
		hb.append("en el juego en modo offline<br1>");
		hb.append("</center>");
		return hb.toString();
	}
	
	private static String infoSpreeKills()
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.HTML_TYPE);
		hb.append("<br>");
		hb.append("<center>");
		hb.append("Cada ves que maten a un oponente<br1>");
		hb.append("saldra un anuncio y un sonido<br1>");
		hb.append("anunciando su victoria, con<br1>");
		hb.append("cada victoria consecutiva<br1>");
		hb.append("el mensaje y el sonido seran diferentes<br1>");
		hb.append("</center>");
		return hb.toString();
	}
	
	private static String infoEnchantAbnormalEffect()
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.HTML_TYPE);
		hb.append("<br>");
		hb.append("<center>");
		hb.append("En el momento en que tengan<br1>");
		hb.append("un set completo y este sea<br1>");
		hb.append("+", Html.newFontColor("LEVEL", ConfigData.ENCHANT_EFFECT_LVL), " su personaje va a adquirir<br1>");
		hb.append("un nuevo y llamatico efecto.<br1>");
		hb.append("</center>");
		return hb.toString();
	}
	
	private static String infoTitleAndNameColor()
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.HTML_TYPE);
		hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
		hb.append("<table width=204 border=0 cellspacing=0 cellpadding=0 bgcolor=CC99FF>");
		hb.append("<tr>");
		hb.append("<td align=center fixwidth=104><button value=\"PvP\" width=104 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("<td align=center fixwidth=100><button value=\"Color\" width=100 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("</tr>");
		hb.append("</table>");
		
		for (Entry<Integer, String> pvp : ConfigData.PVP_COLOR_NAME.entrySet())
		{
			hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
			hb.append("<table width=204 border=0 cellspacing=0 cellpadding=0>");
			hb.append("<tr>");
			hb.append("<td align=center fixwidth=104>", pvp.getKey(), "</td>");
			hb.append("<td align=center fixwidth=100>", Html.newFontColor(pvp.getValue(), "color"), "</td>");
			hb.append("</tr>");
			hb.append("</table>");
		}
		
		hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
		hb.append("<table width=204 border=0 cellspacing=0 cellpadding=0 bgcolor=CC99FF>");
		hb.append("<tr>");
		hb.append("<td align=center fixwidth=104><button value=\"Pk\" width=104 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("<td align=center fixwidth=100><button value=\"Color\" width=100 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("</tr>");
		hb.append("</table>");
		for (Entry<Integer, String> pk : ConfigData.PK_COLOR_TITLE.entrySet())
		{
			hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
			hb.append("<table width=204 border=0 cellspacing=0 cellpadding=0>");
			hb.append("<tr>");
			hb.append("<td align=center fixwidth=104>", pk.getKey(), "</td>");
			hb.append("<td align=center fixwidth=100>", Html.newFontColor(pk.getValue(), "color"), "</td>");
			hb.append("</tr>");
			hb.append("</table>");
		}
		
		hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
		return hb.toString();
	}
	
	private static String infoVoteReward()
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.HTML_TYPE);
		
		hb.append("<br>", Html.newFontColor("LEVEL", "Vote Reward"), "<br>");
		hb.append("Hopzone: ", ConfigData.ENABLE_HOPZONE ? Html.newFontColor("3CFF00", "Enable") : Html.newFontColor("FF0000", "Disable"), "<br1>");
		hb.append("Topzone: ", ConfigData.ENABLE_TOPZONE ? Html.newFontColor("3CFF00", "Enable") : Html.newFontColor("FF0000", "Disable"), "<br1>");
		hb.append("Network: ", ConfigData.ENABLE_NETWORK ? Html.newFontColor("3CFF00", "Enable") : Html.newFontColor("FF0000", "Disable"), "<br1>");
		
		hb.append("<center>Detalles:</center><br1>");
		
		for (Entry<Integer, RewardHolder> entry : ConfigData.VOTE_REWARDS.entrySet())
		{
			hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
			int voteCount = entry.getKey();
			RewardHolder reward = entry.getValue();
			
			hb.append(Html.newFontColor("LEVEL", "Votos"), ": ", voteCount, "<br1>");
			hb.append(Html.newFontColor("LEVEL", "Premios"), ": ", ItemTable.getInstance().getTemplate(reward.getRewardId()).getName(), " - ", reward.getRewardCount(), "<br1>");
		}
		hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
		return hb.toString();
	}
	
	private static String infoAio()
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.HTML_TYPE);
		hb.append("<br>");
		hb.append("El sistema AIO le da a un player<br1>");
		hb.append("el poder de ", Html.newFontColor("LEVEL", "buffear a sus companieros"), "<br1>");
		hb.append("con todos los buffs de los<br1>");
		hb.append("diferentes personaje supports.<br1>");
		hb.append("Este a su ves tendra una mayor<br1>");
		hb.append("recuperacion de MP y casteo.<br>");
		hb.append("No podra salir de las zonas de paz<br1>");
		hb.append("o interactuar con numersos NPC.");
		return hb.toString();
	}
	
	private static String infoVip()
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.HTML_TYPE);
		
		hb.append("<br><font color=LEVEL>General Rate:</font>");
		hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
		hb.append("<table width=204 border=0 cellspacing=0 cellpadding=0>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><button value=\"Type\" width=100 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("<td fixwidth=104 align=center><button value=\"Bonus\" width=104 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("</tr>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><font color=3366FF>EXP:</font></td>");
		hb.append("<td fixwidth=104 align=center><font color=LEVEL>", (int) ConfigData.VIP_BONUS_XP + 100, "%</font></td>");
		hb.append("</tr>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><font color=3366FF>SP:</font></td>");
		hb.append("<td fixwidth=104 align=center><font color=LEVEL>", (int) ConfigData.VIP_BONUS_SP + 100, "%</font></td>");
		hb.append("</tr>");
		hb.append("</table>");
		hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
		hb.append("<br><font color=LEVEL>Drop Rate:</font>");
		hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
		hb.append("<table width=204 border=0 cellspacing=0 cellpadding=0>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100><button value=\"Bonus\" width=100 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("<td fixwidth=52><button value=\"Amount\" width=52 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("<td fixwidth=52><button value=\"Chance\" width=52 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("</tr>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><font color=3366FF>Normal:</font></td>");
		hb.append("<td fixwidth=52 align=center><font color=LEVEL>", (int) ConfigData.VIP_BONUS_DROP_NORMAL_AMOUNT + 100, "%</font></td>");
		hb.append("<td fixwidth=52 align=center><font color=LEVEL>", (int) ConfigData.VIP_BONUS_DROP_NORMAL_AMOUNT + 100, "%</font></td>");
		hb.append("</tr>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><font color=3366FF>Spoil:</font></td>");
		hb.append("<td fixwidth=52 align=center><font color=LEVEL>", (int) ConfigData.VIP_BONUS_DROP_SPOIL_AMOUNT + 100, "%</font></td>");
		hb.append("<td fixwidth=52 align=center><font color=LEVEL>", (int) ConfigData.VIP_BONUS_DROP_SPOIL_CHANCE + 100, "%</font></td>");
		hb.append("</tr>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><font color=3366FF>Seed:</font></td>");
		hb.append("<td fixwidth=52 align=center><font color=LEVEL>", (int) ConfigData.VIP_BONUS_DROP_SEED_AMOUNT + 100, "%</font></td>");
		hb.append("<td fixwidth=52 align=center><font color=LEVEL>", (int) ConfigData.VIP_BONUS_DROP_SEED_AMOUNT + 100, "%</font></td>");
		hb.append("</tr>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><font color=3366FF>Herb:</font></td>");
		hb.append("<td fixwidth=52 align=center><font color=LEVEL>", (int) ConfigData.VIP_BONUS_DROP_HERB_AMOUNT + 100, "%</font></td>");
		hb.append("<td fixwidth=52 align=center><font color=LEVEL>", (int) ConfigData.VIP_BONUS_DROP_HERB_AMOUNT + 100, "%</font></td>");
		hb.append("</tr>");
		hb.append("</table>");
		hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
		return hb.toString();
	}
	
	private static String infoEnchant()
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.HTML_TYPE);
		
		hb.append("<br><center>", Html.newFontColor("LEVEL", "Enchant Info"), "</center>");
		
		hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
		hb.append("<table width=204 border=0 cellspacing=0 cellpadding=0>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><button value=\"Type\" width=100 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("<td fixwidth=104 align=center><button value=\"Status\" width=104 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("</tr>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><font color=3366FF>Max:</font></td>");
		hb.append("<td fixwidth=104 align=center><font color=LEVEL>", Config.ENCHANT_SAFE_MAX_FULL, "</font></td>");
		hb.append("</tr>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><font color=3366FF>Safe:</font></td>");
		hb.append("<td fixwidth=104 align=center><font color=LEVEL>", Config.ENCHANT_SAFE_MAX, "</font></td>");
		hb.append("</tr>");
		hb.append("</table>");
		hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
		
		hb.append("<br><center>", Html.newFontColor("LEVEL", "Rates"), "</center>");
		
		hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
		hb.append("<table width=204 border=0 cellspacing=0 cellpadding=0>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100><button value=Type width=100 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("<td fixwidth=52><button value=Min width=52 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("<td fixwidth=52><button value=Max width=52 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("</tr>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><font color=3366FF>Normal:</font></td>");
		hb.append("<td fixwidth=52 align=center><font color=LEVEL>", 45, "%</font></td>");
		hb.append("<td fixwidth=52 align=center><font color=LEVEL>", 75, "%</font></td>");
		hb.append("</tr>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><font color=3366FF>Bless:</font></td>");
		hb.append("<td fixwidth=52 align=center><font color=LEVEL>", 75, "%</font></td>");
		hb.append("<td fixwidth=52 align=center><font color=LEVEL>", 85, "%</font></td>");
		hb.append("</tr>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><font color=3366FF>Crytal:</font></td>");
		hb.append("<td fixwidth=52 align=center><font color=LEVEL>", 85, "%</font></td>");
		hb.append("<td fixwidth=52 align=center><font color=LEVEL>", 100, "%</font></td>");
		hb.append("</tr>");
		hb.append("</table>");
		hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
		return hb.toString();
	}
	
	private static String infoRates()
	{
		HtmlBuilder hb = new HtmlBuilder(HtmlType.HTML_TYPE);
		
		hb.append("<br><center>", Html.newFontColor("LEVEL", "Rate Info"), "</center>");
		
		hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
		hb.append("<table width=204 border=0 cellspacing=0 cellpadding=0>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><button value=\"Type\" width=100 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("<td fixwidth=104 align=center><button value=\"Chance\" width=104 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("</tr>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><font color=3366FF>EXP:</font></td>");
		hb.append("<td fixwidth=104 align=center><font color=LEVEL>", Config.RATE_XP, "%</font></td>");
		hb.append("</tr>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><font color=3366FF>SP:</font></td>");
		hb.append("<td fixwidth=104 align=center><font color=LEVEL>", Config.RATE_SP, "%</font></td>");
		hb.append("</tr>");
		hb.append("</table>");
		hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
		
		hb.append("<br><center>", Html.newFontColor("LEVEL", "Rate Others"), "</center>");
		
		hb.append("<table width=204 border=0 cellspacing=0 cellpadding=0>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><button value=\"Type\" width=100 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("<td fixwidth=104 align=center><button value=\"Status\" width=104 height=19 back=L2UI_CH3.amountbox2 fore=L2UI_CH3.amountbox2></td>");
		hb.append("</tr>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><font color=3366FF>Adena:</font></td>");
		hb.append("<td fixwidth=104 align=center><font color=LEVEL>", Config.RATE_DROP_ADENA, "%</font></td>");
		hb.append("</tr>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><font color=3366FF>Drop:</font></td>");
		hb.append("<td fixwidth=104 align=center><font color=LEVEL>", Config.RATE_DROP_ITEMS, "%</font></td>");
		hb.append("</tr>");
		hb.append("<tr>");
		hb.append("<td fixwidth=100 align=center><font color=3366FF>Spoil:</font></td>");
		hb.append("<td fixwidth=104 align=center><font color=LEVEL>", Config.RATE_DROP_SPOIL, "%</font></td>");
		hb.append("</tr>");
		hb.append("</table>");
		hb.append(Html.newImage(L2UI.SquareGray, 204, 1));
		return hb.toString();
	}
}
