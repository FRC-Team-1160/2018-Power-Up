package org.usfirst.frc.team1160.robot.commands.auto.paths.switch_;

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
public class Left_LeftSwitch_One extends CommandGroup implements RobotMap{

    public Left_LeftSwitch_One() {
    	System.out.println("left to left switch, one cube");
    	addSequential(new FollowTrajectoryLiftClamp(Robot.segment_one_left,Robot.segment_one_right, SWITCH_HEIGHT));
    	addSequential(new ResetEncoderYaw());
    	addSequential(new WaitDrivetrain(0.05));
		addSequential(new IntakeRotate(SWITCH_AUTO_SPIT_SPEED),0.3);
    }

}
