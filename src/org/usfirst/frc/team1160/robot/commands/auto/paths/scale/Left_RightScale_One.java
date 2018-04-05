package org.usfirst.frc.team1160.robot.commands.auto.paths.scale;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderButNotYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryClamp;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryLift;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryLiftClamp;
import org.usfirst.frc.team1160.robot.commands.auto.drive.WaitDrivetrain;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeRotate;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Left_RightScale_One extends CommandGroup implements RobotMap{

    public Left_RightScale_One() {
		System.out.println("left to right scale, one cube");
		addSequential(new FollowTrajectoryClamp(Robot.segment_one_left,Robot.segment_one_right));
		addSequential(new WaitDrivetrain(0.05));
		addSequential(new ResetEncoderButNotYaw());
		addSequential(new WaitDrivetrain(0.05));
		addSequential(new FollowTrajectoryLift(Robot.segment_two_left,Robot.segment_two_right,SCALE_HEIGHT));
		addSequential(new IntakeRotate(SCALE_AUTO_SPIT_SPEED),0.3);
    }
}
