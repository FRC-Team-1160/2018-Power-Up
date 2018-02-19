package org.usfirst.frc.team1160.robot.commands.auto;

import org.usfirst.frc.team1160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FollowTrajectory extends Command {

	int n;
    public FollowTrajectory() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.dt);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	n = 0;
    	Robot.dt.setLowGear();
    	Robot.dt.resetPosition();
    	Robot.dt.resetEncoderFollowers();
    	Robot.dt.configureEncoderFollowers();
    	Robot.dt.zeroGyro();
    	Robot.dt.resetTime();
    	Robot.dt.startTime();
    	System.out.println("we got here fuckers");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.followTrajectory();
    	System.out.println("Time: " + Robot.dt.getTime());
    	n++;
    	System.out.println("Angle: " + Robot.dt.getGyro().getYaw());
    	System.out.println("n = " + n);
    	Robot.dt.printEncoderDistanceConsoleFeet();
    	Robot.dt.printEncoderDistance();
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
