package org.usfirst.frc.team1160.robot.commands.auto.drive;

import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.auto.lift.BangBangMove;
import org.usfirst.frc.team1160.robot.commands.auto.lift.BangBangMoveDown;
import org.usfirst.frc.team1160.robot.commands.auto.lift.WaitLift;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Trajectory;

/**
 *
 */
public class FollowTrajectoryLiftDown extends CommandGroup implements RobotMap{
  
    public FollowTrajectoryLiftDown(Trajectory leftTraj,Trajectory rightTraj) {
        addParallel(new LoadFollowTrajectory(leftTraj,rightTraj));
        addSequential(new WaitLift(0.25));
        addParallel(new BangBangMoveDown(DOWN_HEIGHT));
    }
}
