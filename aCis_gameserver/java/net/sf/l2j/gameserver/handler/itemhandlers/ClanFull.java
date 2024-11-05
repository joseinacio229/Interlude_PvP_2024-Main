package net.sf.l2j.gameserver.handler.itemhandlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import net.sf.l2j.Config;
import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.data.SkillTable;
import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.Playable;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.model.pledge.Clan;

public class ClanFull implements IItemHandler
{
    @Override
    public void useItem(Playable playable, ItemInstance item, boolean forceUse) 
    {
        if (!(playable instanceof Player)) return;

        Player activeChar = (Player) playable;

        // Verifica que el jugador tenga un clan
        if (activeChar.getClan() == null) 
        {
            activeChar.sendMessage("You are not part of a clan.");
            return;
        }

        int itemId = item.getItemId();

        if (activeChar.isClanLeader())
        {
            // Ítem para subir al nivel máximo y agregar reputación y habilidades
            if (itemId == Config.CLAN_FULL_ITEM_ID)
            {
                if (activeChar.getClan().getLevel() >= Config.CLAN_MAX_LEVEL)
                {
                    activeChar.sendMessage("Your clan is already at maximum level!");
                    return;
                }

                activeChar.getClan().changeLevel(Config.CLAN_MAX_LEVEL);
                activeChar.getClan().addReputationScore(Config.CLAN_FULL_REPUTATION);
                addClanSkills(activeChar);  // Call method to add skills and save them

                activeChar.sendMessage("Your clan Level/Skills/Reputation have been updated!");
            }
            // Ítem para establecer nivel de clan personalizado
            else if (itemId == Config.CLAN_SET_LEVEL_ITEM_ID)
            {
                if (activeChar.getClan().getLevel() >= Config.CLAN_SET_LEVEL)                
                {
                    activeChar.sendMessage("You cannot set the clan level higher than " + Config.CLAN_SET_LEVEL + ".");
                    return;
                }

                activeChar.getClan().changeLevel(Config.CLAN_SET_LEVEL);
                activeChar.sendMessage("Your clan level has been set to " + Config.CLAN_SET_LEVEL + "!");
            }
            // Ítem para solo agregar reputación
            else if (itemId == Config.CLAN_REPUTATION_ITEM_ID)
            {
                activeChar.getClan().addReputationScore(Config.CLAN_REPUTATION);
                activeChar.sendMessage("Your clan reputation has been increased by " + Config.CLAN_REPUTATION + "!");
            }            

            // Destruir el ítem utilizado
            activeChar.destroyItem("Consume", item.getObjectId(), 1, null, true);
            activeChar.sendSkillList();
            activeChar.getClan().updateClanInDB();
            activeChar.broadcastUserInfo();
        }
        else
        {
            activeChar.sendMessage("You are not the clan leader.");
        }
    }

    // Método para agregar las habilidades del clan
    private void addClanSkills(Player activeChar)
    {
        for (int s : Config.CLAN_SKILLS)
        {
            L2Skill clanSkill = SkillTable.getInstance().getInfo(s, SkillTable.getInstance().getMaxLevel(s));
            activeChar.getClan().addNewSkill(clanSkill);
        }
        
        // Store the clan skills in the database
        storeClanSkills(activeChar);
    }  
    
    private void storeClanSkills(Player player) {
        Clan clan = player.getClan();

        // Usar try-with-resources para manejar los recursos automáticamente
        try (Connection con = L2DatabaseFactory.getInstance().getConnection();
             PreparedStatement psDelete = con.prepareStatement("DELETE FROM clan_skills WHERE clan_id = ?")) 
        {
            // Eliminar las habilidades del clan actuales de la base de datos
            psDelete.setInt(1, clan.getClanId());
            psDelete.executeUpdate();  // Ejecuta la eliminación

            // Insertar las habilidades actuales del clan
            String insertSQL = "INSERT INTO clan_skills (clan_id, skill_id, skill_level, skill_name) VALUES (?, ?, ?, ?) " +
                               "ON DUPLICATE KEY UPDATE skill_level = VALUES(skill_level), skill_name = VALUES(skill_name)";
            
            try (PreparedStatement psInsert = con.prepareStatement(insertSQL)) {
                Set<Integer> insertedSkillIds = new HashSet<>();

                for (L2Skill skill : clan.getClanSkills().values()) {
                    if (insertedSkillIds.add(skill.getId())) { // Solo agregar si no está presente
                    	psInsert.setInt(1, clan.getClanId());   // skill_level
                        psInsert.setInt(2, skill.getId());      // skill_id
                        psInsert.setInt(3, skill.getLevel());   // clan_id
                        psInsert.setString(4, skill.getName());  // skill_name
                        psInsert.addBatch();  // Añadir al batch
                    }
                }

                psInsert.executeBatch();  // Ejecutar el batch de inserción
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Deberías usar un logger en lugar de esto
        }
    }

}

