package org.usfirst.frc.team1160.robot.commands.auto.paths;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderButNotYaw;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryIntake;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryIntakeBackwards;
import org.usfirst.frc.team1160.robot.commands.auto.drive.LoadFollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.LoadFollowTrajectoryBackwards;
import org.usfirst.frc.team1160.robot.commands.auto.drive.WaitDrivetrain;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxClamp;
import org.usfirst.frc.team1160.robot.commands.auto.lift.BangBangMove;
import org.usfirst.frc.team1160.robot.commands.auto.lift.WaitLift;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeExtend;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeRotate;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Trajectory;

/**
 *
 */
public class AutoLine extends CommandGroup implements RobotMap{

    public AutoLine() {
    	addSequential(new ResetEncoderYaw());
    	addSequential(new LoadFollowTrajectory(Robot.segment_one_left,Robot.segment_one_right));
    	addSequential(new ResetEncoderYaw());
		addSequential(new WaitDrivetrain(2));
		
		addSequential(new LoadFollowTrajectoryBackwards(Robot.segment_one_left,Robot.segment_one_right));
    	addSequential(new ResetEncoderYaw());
		addSequential(new WaitDrivetrain(0.05));
    }
}
