package org.usfirst.frc.team1160.robot.commands.auto.lift;

import org.usfirst.frc.team1160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * jk it's not fucking magical
 * TODO: Configure the shit outta encoders
 */
public class MotionMagic extends Command {

	private double setpoint;
    public MotionMagic(double setpoint) {
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
    	Robot.lift.setSetpoint(setpoint);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
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
