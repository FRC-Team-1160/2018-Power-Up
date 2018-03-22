package org.usfirst.frc.team1160.robot.commands.auto.paths;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.TurnAngle;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxSpit;
import org.usfirst.frc.team1160.robot.commands.auto.lift.BangBangMove;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeExtend;
import org.usfirst.frc.team1160.robot.commands.intake.Toggle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Center_RightSwitch extends CommandGroup {

    public Center_RightSwitch() {
    	addSequential(new IntakeExtend());
    	addSequential(new ResetEncoderYaw());
    	addSequential(new FollowTrajectory(Robot.segment_one));
    	addSequential(new TurnAngle(45));
    	addSequential(new ResetEncoderYaw());
    	addSequential(new FollowTrajectory(Robot.segment_two));
    	addSequential(new TurnAngle(-45));
    	addSequential(new BangBangMove());
    	addSequential(new ResetEncoderYaw());
    	addSequential(new FollowTrajectory(Robot.segment_three));
    	addSequential(new AutoBoxSpit());
    	
    	//and then now we manipulate the lift
    }
}
