package org.usfirst.frc.team1160.robot.commands.auto.paths;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.GenerateFollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.LoadFollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.TurnAngle;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxClamp;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxSpit;
import org.usfirst.frc.team1160.robot.commands.auto.lift.BangBangMove;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeExtend;
import org.usfirst.frc.team1160.robot.commands.intake.Toggle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Center_LeftSwitch extends CommandGroup implements RobotMap{

    public Center_LeftSwitch() {
    	System.out.println("center to left switch");
    	addSequential(new AutoBoxClamp());
    	addSequential(new ResetEncoderYaw());
    	//addSequential(new GenerateFollowTrajectory(Robot.segment_one));
    	addSequential(new LoadFollowTrajectory(Robot.segment_one_left,Robot.segment_one_right));
    	addSequential(new TurnAngle(-45));
    	addSequential(new ResetEncoderYaw());
    	//addSequential(new GenerateFollowTrajectory(Robot.segment_two));
    	addSequential(new LoadFollowTrajectory(Robot.segment_two_left,Robot.segment_two_right));
    	addSequential(new TurnLift(SWITCH_HEIGHT,45));
    	/*
    	addSequential(new TurnAngle(45));
    	addSequential(new BangBangMove(SWITCH_HEIGHT));
    	*/
    	addSequential(new ResetEncoderYaw());
    	//addSequential(new GenerateFollowTrajectory(Robot.segment_three));
    	addSequential(new LoadFollowTrajectory(Robot.segment_three_left,Robot.segment_three_right));
    	addSequential(new AutoBoxSpit());
    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
