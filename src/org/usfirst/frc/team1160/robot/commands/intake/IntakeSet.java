package org.usfirst.frc.team1160.robot.commands.intake;

import org.usfirst.frc.team1160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 3/14/18 THIS IS FUNCTIONALLY USELESS WE LITERALLY DO NOT USE THIS COMMAND
 */
public class IntakeSet extends Command {
	
	double speed;

    public IntakeSet(double speed) {
    	requires(Robot.intake);
    	this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.set(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
