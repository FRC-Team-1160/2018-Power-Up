package org.usfirst.frc.team1160.robot.commands.auto.paths.scale.sequential;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryLift;
import org.usfirst.frc.team1160.robot.commands.auto.drive.LoadFollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.TurnAngle;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxClamp;
import org.usfirst.frc.team1160.robot.commands.auto.lift.BangBangMove;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeRotate;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Right_RightScale_Sequential extends CommandGroup implements RobotMap{

    public Right_RightScale_Sequential() {
        System.out.println("right to right scale");
        addSequential(new AutoBoxClamp());
        addSequential(new ResetEncoderYaw());
        addSequential(new LoadFollowTrajectory(Robot.segment_one_left,Robot.segment_one_right));
        addSequential(new BangBangMove(SCALE_HEIGHT));
        addSequential(new TurnAngle(-90));
        addSequential(new ResetEncoderYaw());
        addSequential(new IntakeRotate(SCALE_AUTO_SPIT_SPEED),0.3);
        addSequential(new TurnAngle(90));
    }
}
