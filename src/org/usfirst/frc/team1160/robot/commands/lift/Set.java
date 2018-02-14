package org.usfirst.frc.team1160.robot.commands.lift;

import org.usfirst.frc.team1160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Set extends Command{

	private double targetPosition, acceptableError; 
	public Set(double targetPosition, double acceptableError) {
	        // Use requires() here to declare subsystem dependencies
	        // eg. requires(chassis);
	    	requires(Robot.lift);
	    	
	    	this.targetPosition = targetPosition;
	    	this.acceptableError = acceptableError;
	    }

	    // Called just before this Command runs the first time
	    protected void initialize() {
	    	Robot.lift.brakeRelease();
	    }

	    // Called repeatedly when this Command is scheduled to run
	    protected void execute() {
	    	Robot.lift.set(targetPosition);
	    }

	    // Make this return true when this Command no longer needs to run execute()
	    protected boolean isFinished() {
	        return Robot.lift.getFinished(targetPosition, acceptableError);
	    }

	    // Called once after isFinished returns true
	    protected void end() {
	    	Robot.lift.brakeEngage();
	    }

	    // Called when another command which requires one or more of the same
	    // subsystems is scheduled to run
	    protected void interrupted() {
	    	Robot.lift.brakeEngage();
	    }

}
