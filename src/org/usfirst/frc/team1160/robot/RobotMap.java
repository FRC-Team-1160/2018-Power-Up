/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1160.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public interface RobotMap {
	

	/*
	 * DriveTrain
	 * 
	 * PLACEHOLDERS
	 * 
	 * REMEMBER TO ADD GEAR RATIO TO ENC_COUNTS_PER_REV
	 */
	public static int DT_LEFT_1 = 0;
	public static int DT_LEFT_2 = 1;
	public static int DT_RIGHT_1 = 2;
	public static int DT_RIGHT_2 = 3;
	
	public static int DT_ENC_COUNTS_PER_REV = 512;
	
	public static final int LEFT_SOLENOID_0 = 1;
	public static final int LEFT_SOLENOID_1 = 6;
	public static final int RIGHT_SOLENOID_0 = 0;
	public static final int RIGHT_SOLENOID_1 = 7;
	
	
	/*
	 * Intake
	 */
	public static final int LEFT_INTAKE_SPARK = 0;
	public static final int RIGHT_INTAKE_SPARK = 1;
	

	/*
	 * Lift
	 * 
	 * PLACEHOLDERS
	 * 
	 * REMEMBER TO ADD GEAR RATIO TO ENC_COUNTS_PER_REV
	 */
	public static final int BRAKE_LEFT_SOLENOID = 2;
	public static final int BRAKE_RIGHT_SOLENOID = 5;
	
	public static final int  LIFT_MOTOR_LEFT = 0;
	public static final int  LIFT_MOTOR_RIGHT = 0;
	
	public static int LIFT_ENC_COUNTS_PER_REV = 1024;
	
	/*
	 * Climber
	 * 
	 * PLACEHOLVERS
	 */
	public static final int LATCH_LEFT_SOLENOID = 2;
	public static final int LATCH_RIGHT_SOLENOID = 5;
	
	public static final int  CLIMBER_MOTOR_1 = 0;
	public static final int  CLIMBER_MOTOR_2 = 0;

}
