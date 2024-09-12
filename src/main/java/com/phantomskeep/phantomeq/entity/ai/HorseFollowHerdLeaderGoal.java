package com.phantomskeep.phantomeq.entity.ai;

import com.mojang.datafixers.DataFixUtils;
import com.phantomskeep.phantomeq.entity.AbstractPhantHorse;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;
import java.util.function.Predicate;

public class HorseFollowHerdLeaderGoal extends Goal {
   private static final int INTERVAL_TICKS = 200;
   private final AbstractPhantHorse mob;
   private int timeToRecalcPath;
   private int nextStartTick;

   public HorseFollowHerdLeaderGoal(AbstractPhantHorse p_25249_) {
      this.mob = p_25249_;
      this.nextStartTick = this.nextStartTick(p_25249_);
   }

   protected int nextStartTick(AbstractPhantHorse abstractHorse) {
      return reducedTickDelay(200 + abstractHorse.getRandom().nextInt(200) % 20);
   }

   public boolean canUse() {
      if (this.mob.hasFollowers()) {
         return false;
      } else if (this.mob.isFollower()) {
         return true;
      } else if (this.nextStartTick > 0) {
         --this.nextStartTick;
         return false;
      } else {
         this.nextStartTick = this.nextStartTick(this.mob);
         Predicate<AbstractPhantHorse> predicate = (follower) -> {
            return follower.canBeFollowed() || !follower.isFollower();
         };
         List<? extends AbstractPhantHorse> list = this.mob.level.getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), predicate);
         AbstractPhantHorse AbstractPhantHorse = DataFixUtils.orElse(list.stream().filter(com.phantomskeep.phantomeq.entity.AbstractPhantHorse::canBeFollowed).findAny(), this.mob);
         AbstractPhantHorse.addFollowers(list.stream().filter((follower) -> {
            return !follower.isFollower();
         }));
         return this.mob.isFollower();
      }
   }

   public boolean canContinueToUse() {
      return this.mob.isFollower() && this.mob.inRangeOfLeader();
   }

   public void start() {
      this.timeToRecalcPath = 0;
   }

   public void stop() {
      this.mob.stopFollowing();
   }

   public void tick() {
      if (--this.timeToRecalcPath <= 0) {
         this.timeToRecalcPath = this.adjustedTickDelay(10);
         this.mob.pathToLeader();
      }
   }
}