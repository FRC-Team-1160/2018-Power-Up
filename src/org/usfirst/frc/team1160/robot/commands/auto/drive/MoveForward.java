package org.usfirst.frc.team1160.robot.commands.auto.drive;

import org.usfirst.frc.team1160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 3/13/18
 * THIS IS A PID BASED DISTANCE MOVEMENT METHOD, BUT WE REALLY DON'T NEED THIS BECAUSE IT RELIES ON TALON ENCODER CONSTANTS.
 * WE USE ENCODER FOLLOWERS. THEREFORE, THIS METHOD IS PRETTY MUCH IRRELEVANT.
 */
public class MoveForward extends Command {

	double distance;
    public MoveForward(double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.dt);
    	this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.dt.pidOn();
    	Robot.dt.setLowGear();
    	Robot.dt.resetPosition();
    	Robot.dt.resetGyro();
    	Robot.dt.drivePosition(distance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.dt.pidOff();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.dt.pidOff();
    }
}
