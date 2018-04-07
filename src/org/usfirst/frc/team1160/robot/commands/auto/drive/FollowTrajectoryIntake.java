package org.usfirst.frc.team1160.robot.commands.auto.drive;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxClamp;
import org.usfirst.frc.team1160.robot.commands.auto.lift.WaitLift;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeExtend;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeRotate;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Trajectory;

/**
 *
 */
public class FollowTrajectoryIntake extends CommandGroup {

    public FollowTrajectoryIntake(Trajectory leftTraj,Trajectory rightTraj, double delay, double intakeTimeout) {
    	addParallel(new LoadFollowTrajectory(leftTraj,rightTraj));
    	addSequential(new WaitLift(delay));
    	addSequential(new IntakeRotate(-0.7),intakeTimeout);
    	addSequential(new IntakeRotate(0),0.05);

    	
    	
    }
}
 