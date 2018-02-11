package org.usfirst.frc.team1160.robot.subsystems; //this is a package

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeStop;
import edu.wpi.first.wpilibj.Spark;

/**
 *
 */
public class Intake extends Subsystem implements RobotMap{

    private static Intake instance;
    private Spark left;
    private Spark right;
    
    private Intake() {
    	//plugged into pd
    	left = new Spark(LEFT_INTAKE_SPARK);
    	right = new Spark(RIGHT_INTAKE_SPARK);
    }
    
    public static Intake getInstance() {
    	if (instance==null)
    	{
    		instance = new Intake();
    	}
    	return instance;
    }
    
    public void set(double speed)
    {
    	if (Math.abs(speed) > 1)
    	{
    		System.out.println("The set method only takes speeds from -1 to 1");
    		return;
    	}
    	left.set(speed);
    	right.set(speed);
   }

    public void rotate(double speed)
    {
    	if (Math.abs(speed) > 1)
    	{
    		System.out.println("The rotate method only takes speeds from -1 to 1");
    		return;
    	}
    	left.set(-speed);
    	right.set(speed);
   }

    public void stop(){
    	left.set(0);
    	right.set(0);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new IntakeStop());
    }
}

