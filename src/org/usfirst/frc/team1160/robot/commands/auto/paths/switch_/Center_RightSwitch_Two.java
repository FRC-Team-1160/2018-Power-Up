package org.usfirst.frc.team1160.robot.commands.auto.paths.switch_;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderButNotYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryBackwardsLiftDown;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryIntake;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryLift;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryLiftClamp;
import org.usfirst.frc.team1160.robot.commands.auto.drive.LoadFollowTrajectoryBackwards;
import org.usfirst.frc.team1160.robot.commands.auto.drive.WaitDrivetrain;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeRotate;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Center_RightSwitch_Two extends CommandGroup implements RobotMap{

    public Center_RightSwitch_Two() {
    	
    	System.out.println("center to right switch, two cube");
    	
    	addSequential(new FollowTrajectoryLiftClamp(Robot.segment_one_left,Robot.segment_one_right, SWITCH_HEIGHT));
    	addSequential(new ResetEncoderButNotYaw());
		addSequential(new IntakeRotate(SWITCH_AUTO_SPIT_SPEED),0.3);
		addSequential(new IntakeRotate(0),0.01);
    	addSequential(new ResetEncoderButNotYaw());
    	

		addSequential(new FollowTrajectoryBackwardsLiftDown(Robot.segment_two_left, Robot.segment_two_right));
    	addSequential(new ResetEncoderButNotYaw());
    	
    	addSequential(new FollowTrajectoryIntake(Robot.segment_three_left, Robot.segment_three_right, 0.5, 1));
    	addSequential(new ResetEncoderButNotYaw());
    
    	
    	addSequential(new LoadFollowTrajectoryBackwards(Robot.segment_four_left, Robot.segment_four_right));
    	addSequential(new ResetEncoderButNotYaw());
    	
    	addSequential(new FollowTrajectoryLift(Robot.segment_one_left,Robot.segment_one_right, SWITCH_HEIGHT));
    	addSequential(new ResetEncoderButNotYaw());
		addSequential(new IntakeRotate(SWITCH_AUTO_SPIT_SPEED),0.3);
		addSequential(new IntakeRotate(0),0.01);

    }
}
