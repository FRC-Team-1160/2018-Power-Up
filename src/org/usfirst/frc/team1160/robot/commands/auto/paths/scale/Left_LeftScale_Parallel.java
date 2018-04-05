package org.usfirst.frc.team1160.robot.commands.auto.paths.scale;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderButNotYaw;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryLift;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryLiftClamp;
import org.usfirst.frc.team1160.robot.commands.auto.drive.LoadFollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.TurnAngle;
import org.usfirst.frc.team1160.robot.commands.auto.drive.WaitDrivetrain;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxClamp;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxSpit;
import org.usfirst.frc.team1160.robot.commands.auto.paths.TurnLift;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeRotate;
import org.usfirst.frc.team1160.robot.commands.lift.BrakeRelease;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Left_LeftScale_Parallel extends CommandGroup implements RobotMap{

	public Left_LeftScale_Parallel() {
		System.out.println("left to left scale");
		addSequential(new FollowTrajectoryLiftClamp(Robot.segment_one_left,Robot.segment_one_right, SCALE_HEIGHT));
		addSequential(new ResetEncoderButNotYaw());
		addSequential(new WaitDrivetrain(0.05));
		addSequential(new IntakeRotate(SCALE_AUTO_SPIT_SPEED),0.3);
	}
}
