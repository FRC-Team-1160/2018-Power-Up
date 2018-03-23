package org.usfirst.frc.team1160.robot.commands.auto.paths;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.GenerateFollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.LoadFollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.TurnAngle;
import org.usfirst.frc.team1160.robot.commands.auto.drive.WaitDrivetrain;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxSpit;
import org.usfirst.frc.team1160.robot.commands.auto.lift.WaitLift;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeExtend;
import org.usfirst.frc.team1160.robot.commands.intake.Toggle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Right_RightSwitch extends CommandGroup {

    public Right_RightSwitch() {
    	System.out.println("right to right switch");
    	//addSequential(new WaitLift(3));
    	addSequential(new IntakeExtend());
    	addSequential(new ResetEncoderYaw());
    	//addSequential(new GenerateFollowTrajectory(Robot.segment_one));
    	addSequential(new LoadFollowTrajectory(Robot.segment_one_left,Robot.segment_one_right));
    	addSequential(new TurnAngle(-90));
    	addSequential(new WaitDrivetrain(0.5));
    	addSequential(new ResetEncoderYaw());
    	//addSequential(new GenerateFollowTrajectory(Robot.segment_two));
    	addSequential(new LoadFollowTrajectory(Robot.segment_two_left,Robot.segment_two_right));
    	addSequential(new AutoBoxSpit());
    	
    	//and then now we manipulate the lift
    }
}
