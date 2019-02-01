package org.usfirst.frc.team1160.robot.commands.auto.lift;

import org.usfirst.frc.team1160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WaitLift extends Command {
	
	private double finishTime;
    public WaitLift(double finishTime) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.lift);
    	this.finishTime = finishTime;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lift.resetTime();
    	Robot.lift.startTime();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.lift.done(finishTime);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
