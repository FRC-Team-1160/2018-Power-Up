package org.usfirst.frc.team1160.robot.commands.auto.paths.scale.parallel;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryLift;
import org.usfirst.frc.team1160.robot.commands.auto.drive.LoadFollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.TurnAngle;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxClamp;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeRotate;
import org.usfirst.frc.team1160.robot.commands.lift.BrakeRelease;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Right_RightScale_Parallel extends CommandGroup implements RobotMap{

    public Right_RightScale_Parallel() {
        System.out.println("right to right scale");
        addSequential(new AutoBoxClamp());
        addSequential(new ResetEncoderYaw());
        addSequential(new FollowTrajectoryLift(Robot.segment_one_left,Robot.segment_one_right,SCALE_HEIGHT));
        addSequential(new TurnAngle(-90));
        addSequential(new ResetEncoderYaw());
        addSequential(new IntakeRotate(SCALE_AUTO_SPIT_SPEED),0.3);
        addSequential(new TurnAngle(-90));
        addSequential(new BrakeRelease());

    }
}
