package org.usfirst.frc.team1160.robot.commands.auto.lift;

import org.usfirst.frc.team1160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * this shit's for the lift
 * to be used only if motion magic is a steaming heap of shit
 */
public class BangBang extends Command {

	private double setpoint;
	private double error;
	private int errorDirection; //-1 or 1
    public BangBang(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.lift);
    	this.setpoint = setpoint;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lift.brakeRelease();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	error = setpoint - Robot.lift.getSetpoint();
    	errorDirection = (int)(error / Math.abs(error));
    	Robot.lift.setPercentOutput(errorDirection*0.3);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(error) < 10);
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
