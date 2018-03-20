package org.usfirst.frc.team1160.robot.commands.auto.lift;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * this shit's for the lift
 * to be used only if motion magic is a steaming heap of shit
 */
public class BangBangFramework extends Command implements RobotMap{

	private double setpoint;
	private double error;
	private int errorDirection; //-1 or 1
    public BangBangFramework(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.lift);
    	this.setpoint = setpoint;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	error = setpoint - Robot.lift.getSetpoint();
    	errorDirection = (int)(error / Math.abs(error));
    	if (errorDirection*0.3 > 0) { //Only go up!
    		Robot.lift.setPercentOutput(-errorDirection*0.70);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return ((Math.abs(error) < 1000) || (Robot.lift.getSetpoint() >= LIFT_CEILING)); //arbitrary ceiling
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.setPercentOutput(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.lift.setPercentOutput(0);
    }
}
