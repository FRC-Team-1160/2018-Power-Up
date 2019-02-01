package org.usfirst.frc.team1160.robot.commands.drive;

import org.usfirst.frc.team1160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ManualDrive extends Command {

    public ManualDrive() {       
    	requires(Robot.dt);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.dt.setManual();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.manualDrive();
    	Robot.dt.printEncoderDistance();
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
