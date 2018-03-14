/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1160.robot;

import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.TurnAngle;
import org.usfirst.frc.team1160.robot.commands.climb.Climb;
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
				   climbUp,climbDown,
				   resetEncodersYaw,
				   setLift,
				   turnAngle;
	
	public static OI getInstance() {
		if(instance == null) {
			instance = new OI();
		}
		return instance;
	}
	
	private OI() {
		mainStick = new Joystick(0); //dual action
		climbStick = new Joystick(1); //attack
		createButtons();
	}
	
	private void createButtons() {
		resetEncodersYaw = new JoystickButton(mainStick,9);
		
		highGear = new JoystickButton(mainStick,4);
		lowGear = new JoystickButton(mainStick,1);
		
		intakeEat = new JoystickButton(mainStick,5);
		intakeSpit = new JoystickButton(mainStick,6);
		
		extendClimber = new JoystickButton(mainStick,3);
		retractClimber = new JoystickButton(mainStick,2);
		
		//climbUp = new JoystickButton(climbStick,6);
		climbDown = new JoystickButton(climbStick,7);
		
		engageBrake = new JoystickButton(climbStick,3);
		releaseBrake = new JoystickButton(climbStick,1);
		
		turnAngle = new JoystickButton(climbStick, 6);
		
		//setLift = new JoystickButton(climbStick,1);
		
		tieButtons();	
	}
	private void tieButtons() {
		resetEncodersYaw.whenPressed(new ResetEncoderYaw());
		
		highGear.whenPressed(new HighGear());
		lowGear.whenPressed(new LowGear());
		
		intakeEat.whileHeld(new IntakeRotate(0.5));
		intakeSpit.whileHeld(new IntakeRotate(-0.5));
		
		extendClimber.whenPressed(new LatchExtend());
		retractClimber.whenPressed(new LatchRetract());
		
		//.climbUp.whileHeld(new Climb(0.75));
		climbDown.whileHeld(new Climb(-0.75));
		
		engageBrake.whenPressed(new BrakeEngage());
		releaseBrake.whenPressed(new BrakeRelease());

		turnAngle.whenPressed(new TurnAngle(90));
		
		//setLift.whileHeld(new SetLift(0.1));
		
	}
	
	
	public Joystick getMainstick() {
		return mainStick;
	}
	
	public Joystick getClimbStick() {
		return climbStick;
	}
}              