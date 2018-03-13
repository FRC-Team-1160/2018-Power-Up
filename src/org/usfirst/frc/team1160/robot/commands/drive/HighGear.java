package org.usfirst.frc.team1160.robot.commands.drive;

import org.usfirst.frc.team1160.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class HighGear extends Command{

	
	public HighGear() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.dt);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.dt.setHighGear();
    }
    @Override
    protected void execute() {
    	
    }
    
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected void end() {
		
	}
	@Override
	protected void interrupted() {
		
	}

}
