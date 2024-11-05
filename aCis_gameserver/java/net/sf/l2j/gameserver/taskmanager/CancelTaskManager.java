package net.sf.l2j.gameserver.taskmanager;

import java.util.Vector;

import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.Creature;
/**
 * @author Williams
 *
 */
public class CancelTaskManager implements Runnable {
    private final Creature target;
    private final Vector<L2Skill> cancelledBuffs;

    public CancelTaskManager(Creature target, Vector<L2Skill> cancelledBuffs) {
        this.target = target;
        this.cancelledBuffs = cancelledBuffs;
    }

    @Override
    public void run() {
        if (target != null && !target.isDead()) {
            for (L2Skill skill : cancelledBuffs) {
                // Aquí aplicas el buff de nuevo al objetivo
                skill.getEffects(target, target); // o el método que uses para aplicar el buff
            }
        }
    }
}
