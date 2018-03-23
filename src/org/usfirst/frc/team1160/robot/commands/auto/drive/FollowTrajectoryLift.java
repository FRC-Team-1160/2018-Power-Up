package org.usfirst.frc.team1160.robot.commands.auto.drive;

import org.usfirst.frc.team1160.robot.commands.auto.lift.BangBangMove;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Trajectory;

/**
 *
 */
public class FollowTrajectoryLift extends CommandGroup {
  
    public FollowTrajectoryLift(Trajectory leftTraj,Trajectory rightTraj,int height) {
    	
        addParallel(new LoadFollowTrajectory(leftTraj,rightTraj));
        addParallel(new BangBangMove(height));
    }
}
