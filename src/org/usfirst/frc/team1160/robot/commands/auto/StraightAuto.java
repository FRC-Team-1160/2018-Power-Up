package org.usfirst.frc.team1160.robot.commands.auto;

import org.usfirst.frc.team1160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StraightAuto extends Command{
	
	
	private double currentTime;
	public StraightAuto() {       
    	requires(Robot.dt);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.dt.setLowGear();
    	Robot.dt.resetTime();
    	Robot.dt.startTime();
    	Robot.dt.resetPosition();
    	Robot.dt.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.setPercentOutputGyro(0.5,0);
    	currentTime = Robot.dt.getTime();
    	Robot.dt.printEncoderVelocity();
    	Robot.dt.printEncoderDistance();
    	Robot.dt.printYaw();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return currentTime > 1.5;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
