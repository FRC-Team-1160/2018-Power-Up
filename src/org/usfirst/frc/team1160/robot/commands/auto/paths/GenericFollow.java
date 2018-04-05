package org.usfirst.frc.team1160.robot.commands.auto.paths;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderButNotYaw;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryIntake;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryIntakeBackwards;
import org.usfirst.frc.team1160.robot.commands.auto.drive.LoadFollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.LoadFollowTrajectoryBackwards;
import org.usfirst.frc.team1160.robot.commands.auto.drive.WaitDrivetrain;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxClamp;
import org.usfirst.frc.team1160.robot.commands.auto.lift.BangBangMove;
import org.usfirst.frc.team1160.robot.commands.auto.lift.WaitLift;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeExtend;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeRotate;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Trajectory;

/**
 *
 */
public class GenericFollow extends CommandGroup implements RobotMap{

    public GenericFollow(/*Trajectory traj*/) {
    	//addSequential(new AutoBoxClamp());
    	addSequential(new ResetEncoderButNotYaw());
    	addSequential(new LoadFollowTrajectory(Robot.segment_one_left,Robot.segment_one_right));
    	addSequential(new ResetEncoderButNotYaw());
		addSequential(new WaitDrivetrain(0.05));
		
    	addSequential(new LoadFollowTrajectoryBackwards(Robot.segment_two_left,Robot.segment_two_right));
    	addSequential(new ResetEncoderButNotYaw());
		addSequential(new WaitDrivetrain(0.05));
    	
    	addSequential(new LoadFollowTrajectory(Robot.segment_three_left,Robot.segment_three_right));
    	addSequential(new ResetEncoderButNotYaw());
		addSequential(new WaitDrivetrain(0.05));
    	
    	addSequential(new LoadFollowTrajectoryBackwards(Robot.segment_four_left,Robot.segment_four_right));
    	addSequential(new ResetEncoderButNotYaw());
    	
    	
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
