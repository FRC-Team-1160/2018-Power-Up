/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1160.robot;

import org.usfirst.frc.team1160.robot.commands.*;
import org.usfirst.frc.team1160.robot.commands.auto.*;
import org.usfirst.frc.team1160.robot.commands.auto.drive.TurnAngle;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxClamp;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxSpit;
import org.usfirst.frc.team1160.robot.commands.auto.lift.BangBangFramework;
import org.usfirst.frc.team1160.robot.commands.auto.lift.BangBangMove;
import org.usfirst.frc.team1160.robot.commands.auto.paths.TurnLift;
import org.usfirst.frc.team1160.robot.commands.climb.*;
import org.usfirst.frc.team1160.robot.commands.drive.*;
import org.usfirst.frc.team1160.robot.commands.intake.*;
import org.usfirst.frc.team1160.robot.commands.lift.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements RobotMap{
	private static OI instance;
	Joystick mainStick, climbStick, dsStick;
	JoystickButton intakeEat,intakeSpit,intakeFastSpit,intakeSet,intakeStop,intakeExtend,intakeRetract,intakeExtendRetract,
				   engageBrake,releaseBrake,
				   highGear, lowGear,
				   extendClimber, retractClimber,
				   climbUp,climbDown,
				   resetEncodersYaw,
				   bangBangScale,bangBangSwitch,bangBangCarry,
				   turnAngle,
				   fullExtend,fullRetract,
				   dsEngageBrake,dsReleaseBrake,dsExtendClimber,dsRetractClimber,dsClimbUp,dsClimbDown,
				   placeBox,
				   liftEncodersReset;
	
	public static OI getInstance() {
		if(instance == null) {
			instance = new OI();
		}
		return instance;
	}
	
	private OI() {
		mainStick = new Joystick(0); //dual action
		climbStick = new Joystick(1); //attack 3
		dsStick = new Joystick(2); //generic driverstation joystick
		createButtons();
	}
	
	private void createButtons() {
		
		/*
		 * Dual Action
		 */
		resetEncodersYaw = new JoystickButton(mainStick,9);
		placeBox = new JoystickButton(mainStick,4);
		
		//fullExtend = new JoystickButton(mainStick,8);
		
		highGear = new JoystickButton(mainStick,7);
		lowGear = new JoystickButton(mainStick,8);
		
		intakeEat = new JoystickButton(mainStick,5);
		intakeSpit = new JoystickButton(mainStick,6);
		//intakeFastSpit = new JoystickButton(mainStick,10);
		//liftEncodersReset = new JoystickButton(mainStick,10);
		intakeSet = new JoystickButton(mainStick,10);
		
		intakeExtend = new JoystickButton(mainStick,3);
		intakeRetract = new JoystickButton(mainStick,2);
		
		bangBangCarry = new JoystickButton(mainStick,1);
		
		/*
		 * Attack 3
		 */
		
		climbUp = new JoystickButton(climbStick,6);
		climbDown = new JoystickButton(climbStick,7);
		
		engageBrake = new JoystickButton(climbStick,1);
		releaseBrake = new JoystickButton(climbStick,3);
		
		extendClimber = new JoystickButton(climbStick,8);
		retractClimber = new JoystickButton(climbStick,9);
		
		//intakeExtendRetract = new JoystickButton(climbStick,11);
		
		turnAngle = new JoystickButton(climbStick,10);

		bangBangScale = new JoystickButton(climbStick,5);
		bangBangSwitch = new JoystickButton(climbStick,4);
		
		/*
		 * Generic
		 */
		//dsHighGear = new JoystickButton(dsStick,)
		//dsLowGear = new JoystickButton(dsStick,)
		dsEngageBrake = new JoystickButton(dsStick,1);
		dsReleaseBrake = new JoystickButton(dsStick,2);
		dsExtendClimber = new JoystickButton(dsStick,3);
		dsRetractClimber = new JoystickButton(dsStick,4);
		dsClimbUp = new JoystickButton(dsStick,5);
		dsClimbDown = new JoystickButton(dsStick,6);
		tieButtons();	
	}
	private void tieButtons() {
		
		intakeExtend.whenPressed(new IntakeExtend());
		intakeRetract.whenPressed(new IntakeRetract());
		
		//fullExtend.whenPressed(new Toggle());
		
		resetEncodersYaw.whenPressed(new ResetEncoderYaw());
		placeBox.whenPressed(new PlaceBox());
		
		highGear.whenPressed(new HighGear());
		lowGear.whenPressed(new LowGear());
		
		intakeEat.whileHeld(new IntakeRotate(-0.7));
		intakeSpit.whileHeld(new IntakeRotate(0.5));
		//liftEncodersReset.whenPressed(new ResetLiftEncoders());
		intakeSet.whileHeld(new IntakeSet(0.5));
		//intakeFastSpit.whileHeld(new IntakeRotate(0.8));
		//intakeBigSpit.?
		
		extendClimber.whenPressed(new LatchExtend());
		retractClimber.whenPressed(new LatchRetract());
		
		climbUp.whileHeld(new Climb(0.75));
		climbDown.whileHeld(new Climb(-0.75));
		
		engageBrake.whenPressed(new BrakeEngage());
		releaseBrake.whenPressed(new BrakeRelease());

		turnAngle.whenPressed(new TurnAngle(180));
		
		bangBangCarry.whenPressed(new BangBangMove(CARRY_HEIGHT));
		bangBangScale.whenPressed(new BangBangMove(SCALE_HEIGHT));
		bangBangSwitch.whenPressed(new BangBangMove(SWITCH_HEIGHT));
		
		//pyramid second layer height: 5150
		
		//setLift.whileHeld(new SetLift(0.1));
		
	}
	
	
	public Joystick getMainstick() {
		return mainStick;
	}
	
	public Joystick getClimbStick() {
		return climbStick;
	}
}              