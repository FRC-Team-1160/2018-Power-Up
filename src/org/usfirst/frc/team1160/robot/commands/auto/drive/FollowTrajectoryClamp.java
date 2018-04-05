package org.usfirst.frc.team1160.robot.commands.auto.drive;

import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxClamp;
import org.usfirst.frc.team1160.robot.commands.auto.lift.BangBangMove;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Trajectory;

/**
 *
 */
public class FollowTrajectoryClamp extends CommandGroup {
  
    public FollowTrajectoryClamp(Trajectory leftTraj,Trajectory rightTraj) {
        addParallel(new LoadFollowTrajectory(leftTraj,rightTraj));
        addSequential(new AutoBoxClamp());
    }
}
