package org.usfirst.frc.team1160.robot.commands.auto.paths;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.LoadFollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.LoadFollowTrajectoryHighGear;
import org.usfirst.frc.team1160.robot.commands.auto.drive.TurnAngle;
import org.usfirst.frc.team1160.robot.commands.auto.drive.WaitDrivetrain;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxClamp;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeExtend;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class X_AutoLine extends CommandGroup {

    public X_AutoLine() {
    	System.out.println("Auto line");
    	addSequential(new AutoBoxClamp());
    	addSequential(new ResetEncoderYaw());
    	addSequential(new LoadFollowTrajectory(Robot.segment_one_left,Robot.segment_one_right));
    	//addSequential(new LoadFollowTrajectoryHighGear(Robot.segment_one_left,Robot.segment_one_right));
    	addSequential(new TurnAngle(-90));
    }
}
