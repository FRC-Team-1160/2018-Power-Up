package org.usfirst.frc.team1160.robot.commands.auto.drive;

import org.usfirst.frc.team1160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Trajectory;

import org.usfirst.frc.team1160.robot.TrajectoryWaypoints;

/**
 *
 */
public class FollowTrajectory extends Command implements TrajectoryWaypoints{

	int n;
	Trajectory traj;
	
    public FollowTrajectory(Trajectory traj) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.dt);
    	this.traj = traj;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	n = 0;
    	//Robot.dt.generateModifiers(traj);
    	System.out.println("We got here in FollowTrajectory");
    	Robot.dt.setLowGear();
    	Robot.dt.resetPosition();
		Robot.dt.generateModifiers(traj);
    	Robot.dt.configureEncoderFollowers();
    	Robot.dt.resetAngleDifference();
    	Robot.dt.resetTime();
    	Robot.dt.startTime();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.followTrajectory();
    	System.out.println("Time: " + Robot.dt.getTime());
    	n++;
    	Robot.dt.printYaw();
    	System.out.println("n = " + n);
    	Robot.dt.printEncoderDistance();
    	Robot.dt.printEncoderVelocity();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.dt.getTrajectory().segments.length == n);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.dt.resetPosition();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.dt.resetPosition();
    }
}
