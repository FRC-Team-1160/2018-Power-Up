package org.usfirst.frc.team1160.robot.commands.auto.paths.scale;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderButNotYaw;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryClamp;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryLift;
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
public class Right_LeftScale_Parallel extends CommandGroup implements RobotMap{

    public Right_LeftScale_Parallel() {
       System.out.println("right to left scale");
       addSequential(new FollowTrajectoryClamp(Robot.segment_one_left,Robot.segment_one_right));
		addSequential(new WaitDrivetrain(0.05));
		addSequential(new ResetEncoderButNotYaw());
		addSequential(new WaitDrivetrain(0.05));
		addSequential(new FollowTrajectoryLift(Robot.segment_two_left,Robot.segment_two_right,SCALE_HEIGHT));
		addSequential(new IntakeRotate(SCALE_AUTO_SPIT_SPEED),0.3);
       //spit the fucking box out
       /*
       addSequential(new ResetEncoderYaw());
       addSequential(new IntakeRotate(SCALE_AUTO_SPIT_SPEED),0.3);
       */
    }
}
