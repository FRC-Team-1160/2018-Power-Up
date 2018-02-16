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
	 * 
	 * 
	 */
	public static int DT_LEFT_1 = 2;
	public static int DT_LEFT_2 = 10;
	public static int DT_RIGHT_1 = 3;
	public static int DT_RIGHT_2 = 11;
	
	public static int DT_ENC_COUNTS_PER_REV = 512;
	
	public static final int DT_SOLENOID_0 = 2;
	public static final int DT_SOLENOID_1 = 5;

	public static final double GYRO_KG = 0.8 * (-1.0/80.0);
	public static final double GYRO_L = 0.5;
	public static final double GYRO_R = -0.5;
	public static final double GYRO_TOLERANCE = 2;
	
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
	public static final int BRAKE_LEFT_SOLENOID = 0;
	public static final int BRAKE_RIGHT_SOLENOID = 7;
	
	public static final int  LIFT_MOTOR_LEFT = 0;
	public static final int  LIFT_MOTOR_RIGHT = 1;
	
	public static int LIFT_ENC_COUNTS_PER_REV = 4096;
	
	//Motion Magic Constants - NEED TO BE TUNED
	public static final double	LIFT_LEFT_KF = 0;
	public static final double	LIFT_LEFT_KP = 0;
	public static final double	LIFT_LEFT_KI = 0;
	public static final double	LIFT_LEFT_KD = 0;
	//These constants are in native units per 100m
	public static final int	LIFT_LEFT_MOTION_CRUISE_VELOCITY = 0;
	public static final int	LIFT_LEFT_MOTION_ACCELERATION = 0;
	
	public static final double	LIFT_RIGHT_KF = 0;
	public static final double	LIFT_RIGHT_KP = 0;
	public static final double	LIFT_RIGHT_KI = 0;
	public static final double	LIFT_RIGHT_KD = 0;
	//These constants are in native units per 100m
	public static final int	LIFT_RIGHT_MOTION_CRUISE_VELOCITY = 0;
	public static final int	LIFT_RIGHT_MOTION_ACCELERATION = 0;
	

	
	/*
	 * Climber
	 * 
	 * PLACEHOLVERS
	 */
	public static final int LATCH_LEFT_SOLENOID = 3;
	public static final int LATCH_RIGHT_SOLENOID = 4;
	
	public static final int  CLIMBER_MOTOR_1 = 12;
	public static final int  CLIMBER_MOTOR_2 = 13;

}
