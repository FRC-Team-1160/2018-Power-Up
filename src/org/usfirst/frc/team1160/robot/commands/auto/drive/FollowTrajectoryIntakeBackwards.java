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
public class FollowTrajectoryIntakeBackwards extends CommandGroup {

    public FollowTrajectoryIntakeBackwards(Trajectory leftTraj,Trajectory rightTraj) {
    	addSequential(new ResetEncoderYaw());
    	addParallel(new LoadFollowTrajectoryBackwards(leftTraj,rightTraj));
    	//addSequential(new AutoBoxClamp());
    	/*
    	addSequential(new WaitLift(2));
    	addSequential(new IntakeRotate(0.5),0.7);
    	addSequential(new WaitLift(0.1));
    	addSequential(new IntakeExtend());
    	addSequential(new IntakeRotate(0.5),0.3);
    	*/
    	
    }
}
 