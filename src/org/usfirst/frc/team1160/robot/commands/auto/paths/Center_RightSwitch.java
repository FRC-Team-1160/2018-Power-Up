package org.usfirst.frc.team1160.robot.commands.auto.paths;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.GenerateFollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.LoadFollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.TurnAngle;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxSpit;
import org.usfirst.frc.team1160.robot.commands.auto.lift.BangBangMove;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeExtend;
import org.usfirst.frc.team1160.robot.commands.intake.Toggle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Center_RightSwitch extends CommandGroup implements RobotMap{

    public Center_RightSwitch() {
    	System.out.println("center to right switch");
    	addSequential(new IntakeExtend());
    	addSequential(new ResetEncoderYaw());
    	//addSequential(new GenerateFollowTrajectory(Robot.segment_one));
    	addSequential(new LoadFollowTrajectory(Robot.segment_one_left,Robot.segment_one_right));
    	addSequential(new TurnAngle(45));
    	addSequential(new ResetEncoderYaw());
    	//addSequential(new GenerateFollowTrajectory(Robot.segment_two));
    	addSequential(new LoadFollowTrajectory(Robot.segment_two_left,Robot.segment_two_right));
    	addSequential(new TurnAngle(-45));																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																															
    	addSequential(new BangBangMove(SWITCH_HEIGHT));
    	addSequential(new ResetEncoderYaw());
    	//addSequential(new GenerateFollowTrajectory(Robot.segment_three));
    	addSequential(new LoadFollowTrajectory(Robot.segment_three_left,Robot.segment_three_right));
    	addSequential(new AutoBoxSpit());
    }
}
