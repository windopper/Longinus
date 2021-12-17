package Gliese581cMobs;

import Mob.EntityManager;
import Mob.MobListManager;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityFox;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.level.World;
import org.bukkit.Bukkit;
import org.bukkit.entity.Fox;

import java.lang.reflect.Constructor;

public class FoxRat extends EntityFox {



    public FoxRat(EntityTypes<? extends EntityFox> entitytypes, World world, EntityFox master) {

        super(entitytypes, world);

        MobListManager.MobList mobList = MobListManager.MobList.폭스랫;
        Fox craftFox = (Fox) this.getBukkitEntity();
        IChatBaseComponent name = IChatBaseComponent.a(mobList.getName());
        this.setCustomName(name);
        this.setCustomNameVisible(true);
        this.getWorld().addEntity(this);
        craftFox.setBaby();

        try {
            Class<?> outer = Class.forName("net.minecraft.world.entity.animal.EntityFox");
            Class<?> ch =  null;
            for(Class a : outer.getDeclaredClasses()) {
                Bukkit.broadcastMessage(a.getName());
                if(a.getName().equals("net.minecraft.world.entity.animal.EntityFox$h")) {
                    ch = a;
                }
            }
            Constructor<?> conn = ch.getConstructors()[0];
            conn.setAccessible(true);
            this.bP.a(0, (PathfinderGoalFollowParent) conn.newInstance(this, master, 1.25D));
        }
        catch(Exception e){
            e.printStackTrace();
        }



        EntityManager.getinstance(craftFox, mobList).setShowNameTag(true);
    }



    @Override
    public void initPathfinder() {

        this.bP.a(1, new PathfinderGoalMeleeAttack(this, 1.5, true));
        this.bP.a(2, new PathfinderGoalRandomStroll(this, 0.6D));
        this.bP.a(3, new PathfinderGoalLookAtPlayer(this, EntityPlayer.class, 8.0F));
        this.bP.a(4, new PathfinderGoalRandomLookaround(this));

        this.bQ.a(2, new PathfinderGoalNearestAttackableTarget<EntityVillager>(this, EntityVillager.class, true, true));
        //this.bQ.a(3, new PathfinderGoalNearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true, true));

    }
}
