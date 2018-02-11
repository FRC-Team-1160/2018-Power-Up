/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1160.robot;

/*
import org.usfirst.frc.team1160.robot.commands.constantSpeed;
import org.usfirst.frc.team1160.robot.commands.climb.Climb;
import org.usfirst.frc.team1160.robot.commands.dSolenoid.gearForward;
import org.usfirst.frc.team1160.robot.commands.dSolenoid.gearReverse;
import org.usfirst.frc.team1160.robot.commands.intake.intakeRun;
import org.usfirst.frc.team1160.robot.commands.piston.Left;
import org.usfirst.frc.team1160.robot.commands.piston.Right;
*/
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private static OI instance;
	Joystick mainstick;
	JoystickButton gearForward,gearReverse,gearSwitch,gearOff,
				   resetPosition,constantSpeed,
				   intakeEat,intakeSpit,intakeStop,intakeRotate,
				   climbUp,
				   pistonLeft,pistonRight;
	
	public static OI getInstance() {
		if(instance == null) {
			instance = new OI();
		}
		return instance;
	}
	
	private OI() {
		mainstick = new Joystick(0);
		createButtons();
	}
	
	private void createButtons() {
		//gearSwitch = new JoystickButton(mainstick,3);
		gearForward = new JoystickButton(mainstick,4); //low gear
		gearReverse = new JoystickButton(mainstick,2); //high gear
		//resetPosition = new JoystickButton(mainstick,1);
		constantSpeed = new JoystickButton(mainstick,6);
		intakeEat = new JoystickButton(mainstick,7);
		intakeRotate = new JoystickButton(mainstick,1);
		intakeStop = new JoystickButton(mainstick,3);
		intakeSpit = new JoystickButton(mainstick,8);
		climbUp = new JoystickButton(mainstick,5);
		pistonLeft = new JoystickButton(mainstick,9);
		pistonRight = new JoystickButton(mainstick,10);
		tieButtons();	
	}
	private void tieButtons() {
		
		/*
		//gearSwitch.whenPressed(new gearSwitch());
		gearForward.whenPressed(new gearForward());
		gearReverse.whenPressed(new gearReverse());
		//resetPosition.whenPressed(new resetEncoderPosition());
		constantSpeed.whileHeld(new constantSpeed());
		intakeEat.whenPressed(new intakeSet(1.0));
		intakeRotate.whenPressed(new intakeRotate(0.3));
		intakeStop.whenPressed(new intakeStop());
		intakeSpit.whenPressed(new intakeSet(-0.3));
		climbUp.whileHeld(new Climb(0.5));
		pistonLeft.whileHeld(new Left());
		pistonRight.whileHeld(new Right()); 
		*/
		
	}
	
	
	public Joystick getMainstick() {
		return mainstick;
	}
}
