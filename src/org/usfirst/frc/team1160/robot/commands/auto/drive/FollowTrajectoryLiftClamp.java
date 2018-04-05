package org.usfirst.frc.team1160.robot.commands.auto.drive;

import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxClamp;
import org.usfirst.frc.team1160.robot.commands.auto.lift.BangBangMove;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Trajectory;

/**
 *
 */
public class FollowTrajectoryLiftClamp extends CommandGroup {
  
    public FollowTrajectoryLiftClamp(Trajectory leftTraj,Trajectory rightTraj,int height) {
        addParallel(new LoadFollowTrajectory(leftTraj,rightTraj));
        addSequential(new AutoBoxClamp());
        addSequential(new BangBangMove(height));
    }
}
