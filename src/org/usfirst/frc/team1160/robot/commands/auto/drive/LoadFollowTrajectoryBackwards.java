package org.usfirst.frc.team1160.robot.commands.auto.drive;

import org.usfirst.frc.team1160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Trajectory;

/**
 *
 */
public class LoadFollowTrajectoryBackwards extends Command {
	
	int n;
	Trajectory leftTraj,rightTraj;
	

    public LoadFollowTrajectoryBackwards(Trajectory leftTraj,Trajectory rightTraj) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.dt);
    	this.leftTraj = leftTraj;
    	this.rightTraj = rightTraj;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	n = leftTraj.segments.length;
    	Robot.dt.setLowGear();
    	Robot.dt.resetPosition();
    	Robot.dt.loadLeftEncoderFollower(rightTraj);
    	Robot.dt.loadRightEncoderFollower(leftTraj);
    	Robot.dt.configureEncoderFollowers();
    	Robot.dt.resetEncoderFollowersBackwards();
    	Robot.dt.resetAngleDifference();
    	Robot.dt.resetTime();
    	Robot.dt.startTime();
    	Robot.dt.setPercentOutput(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.followTrajectoryBackwards();
    	//System.out.println("Time: " + Robot.dt.getTime());
    	if (n > 0) {
	    	double leftProjectedVelocity = Robot.dt.getLeftEncoderFollower().getSegment().velocity;
			double rightProjectedVelocity = Robot.dt.getRightEncoderFollower().getSegment().velocity;
			SmartDashboard.putNumber("leftProjectedVelocity",leftProjectedVelocity);
			SmartDashboard.putNumber("rightProjectedVelocity", -rightProjectedVelocity);
    	}
    	n--;
    	Robot.dt.printYaw();
    	System.out.println("n = " + n);
    	Robot.dt.printEncoderDistance();
    	Robot.dt.printEncoderVelocity();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (n == 0);
        //look, let's be honest: leftTraj.segments.length = rightTraj.segments.length
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("AVERAGE TIMESTEP: " + Robot.dt.totalTimestep/Robot.dt.numberOfIterations);
    	Robot.dt.setPercentOutput(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
