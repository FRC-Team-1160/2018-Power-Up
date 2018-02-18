/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1160.robot;

import org.usfirst.frc.team1160.robot.commands.climb.LatchExtend;
import org.usfirst.frc.team1160.robot.commands.climb.LatchRetract;
import org.usfirst.frc.team1160.robot.commands.drive.HighGear;
import org.usfirst.frc.team1160.robot.commands.drive.LowGear;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeRotate;
import org.usfirst.frc.team1160.robot.commands.lift.BrakeEngage;
import org.usfirst.frc.team1160.robot.commands.lift.BrakeRelease;
import org.usfirst.frc.team1160.robot.commands.lift.SetLift;

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
	Joystick mainStick, climbStick;
	JoystickButton intakeEat,intakeSpit,intakeSet,intakeStop,
				   engageBrake,releaseBrake,
				   highGear, lowGear,
				   extendClimber, retractClimber,
				   
				   setLift;
	
	public static OI getInstance() {
		if(instance == null) {
			instance = new OI();
		}
		return instance;
	}
	
	private OI() {
		mainStick = new Joystick(0);
		climbStick = new Joystick(1);
		createButtons();
	}
	
	private void createButtons() {
		highGear = new JoystickButton(mainStick,2);
		lowGear = new JoystickButton(mainStick,4);
		
		intakeEat = new JoystickButton(mainStick,5);
		intakeSpit = new JoystickButton(mainStick,6);
		
		extendClimber = new JoystickButton(climbStick,8);
		retractClimber = new JoystickButton(climbStick,9);
		
		engageBrake = new JoystickButton(climbStick,1);
		releaseBrake = new JoystickButton(climbStick,3);
		
		//setLift = new JoystickButton(climbStick,1);
		
		tieButtons();	
	}
	private void tieButtons() {
		
		highGear.whenPressed(new HighGear());
		lowGear.whenPressed(new LowGear());
		
		intakeEat.whileHeld(new IntakeRotate(0.5));
		intakeSpit.whileHeld(new IntakeRotate(-0.5));
		
		extendClimber.whenPressed(new LatchExtend());
		retractClimber.whenPressed(new LatchRetract());
		
		engageBrake.whenPressed(new BrakeEngage());
		releaseBrake.whenPressed(new BrakeRelease());
		
		setLift.whileHeld(new SetLift(0.1));
		
	}
	
	
	public Joystick getMainstick() {
		return mainStick;
	}
	
	public Joystick getClimbStick() {
		return climbStick;
	}
}
