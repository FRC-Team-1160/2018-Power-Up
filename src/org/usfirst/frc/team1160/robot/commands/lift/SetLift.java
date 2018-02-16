package org.usfirst.frc.team1160.robot.commands.lift;

import org.usfirst.frc.team1160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetLift extends Command{

	private double percentOutput;
	
	public SetLift(double percentOutput) {       
    	requires(Robot.lift);
    	
    	this.percentOutput = percentOutput;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lift.setPercentOutput(percentOutput);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
