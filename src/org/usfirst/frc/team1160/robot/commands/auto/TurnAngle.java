package org.usfirst.frc.team1160.robot.commands.auto;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnAngle extends Command implements RobotMap{

	public double targetAngle;
    
	public TurnAngle(double targetAngle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.dt);
    	this.targetAngle = targetAngle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.dt.setLowGear();
    	Robot.dt.resetPosition();
    	Robot.dt.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.dt.turnAngle(targetAngle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(Robot.dt.getGyro().getYaw() - targetAngle) < GYRO_TOLERANCE;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Relative Yaw: " + Robot.dt.getGyro().getYaw());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
