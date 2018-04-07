package org.usfirst.frc.team1160.robot.commands.auto.paths.scale;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderButNotYaw;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryLiftClamp;
import org.usfirst.frc.team1160.robot.commands.auto.drive.WaitDrivetrain;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeRotate;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Left_LeftScale_Two extends CommandGroup implements RobotMap{

    public Left_LeftScale_Two() {
    	System.out.println("left to left scale, two cube");
		addSequential(new FollowTrajectoryLiftClamp(Robot.segment_one_left,Robot.segment_one_right, SCALE_HEIGHT));
		addSequential(new ResetEncoderYaw());
		addSequential(new WaitDrivetrain(0.05));
		addSequential(new IntakeRotate(SCALE_AUTO_SPIT_SPEED),0.3);
    }
}
