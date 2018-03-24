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

	
	/**
	 * SHIT TO TAKE CARE OF BEFORE EACH MATCH
	 * 
	 * HARDCODED_POSITION
		 * 1 - Left
		 * 2 - Center
		 * 3 - Right
	 * FAST_SWITCH
	 	 * true - have a nice fast center switch auto (it's also untested so lol)
	 	 * false - do not have a nice fast center switch auto
 	 * SCALE
 	 	 * true - go for the scale OVER THE SWITCH
 	 	 * false - we're not going to run untested code!
	 */
	public static final int HARDCODED_POSITION = 3;
	public static final boolean FAST_SWITCH = true;
	public static final boolean SCALE_OVER_SWITCH = true;
	public static final boolean SCALE = true;
	/*
	 * General
	 */
	public static final int PCM = 4;							//PCM Port
	
	/*
	 * DriveTrain
	 */
	public static int DT_LEFT_1 = 2;							//Ports
	public static int DT_LEFT_2 = 10;
	public static int DT_RIGHT_1 = 3;
	public static int DT_RIGHT_2 = 11;
	public static final int DT_SOLENOID_0 = 1;
	public static final int DT_SOLENOID_1 = 6;
	
	public static int DT_ENC_COUNTS_PER_REV = 512;				//Encoder counts per revolution
	public static double WHEEL_BASE_DISTANCE = 2.53;
															//Gyro constants
	public static final double GYRO_KP = 1.2 * (-1.0/80.0);	//Pathfinder
	
	public static final double GYRO_KP_2 = .55*2.5 * (-1.0/80.0); //TurnAngle
	public static final double GYRO_KI = -3*3/(1.3*100);
	public static final double GYRO_KD = 3*2.5*1.3/40 * (-1.0/80.0); //0.01 * (-1.0/80.0);//TURN ANGLE KP
	public static final double GYRO_TOLERANCE = 0.75;				//Smaller value means higher accuracy but more time spent
	public static final double TURN_TIMEOUT = 2.5;
																//achieving said accuracy
	public static final double GYRO_CAP = 0.4; //max speed of the turn during TurnAngle
	
	public static final double DRIVE_KP = 0.8;					//Drivetrain Talon PID constants
	public static final double DRIVE_KI = 0;
	public static final double DRIVE_KD = 0;
	public static final double DRIVE_KF = 0;
	
	public static final double LEFT_KP = 0.6;//0				//MotionProfiling Constants for Low Gear
	public static final double LEFT_KI = 0;
	public static final double LEFT_KD = 0.2;//0.2
	public static final double LEFT_KF = 0.125;
	public static final double LEFT_KA = 0;
	
	public static final double RIGHT_KP = 0.6;//0.8;
	public static final double RIGHT_KI = 0;
	public static final double RIGHT_KD = 0.2;//0.2
	public static final double RIGHT_KF = 0.127;
	public static final double RIGHT_KA = 0;
	
	public static final double MAX_VELOCITY = 7;				//Drivetrain trajectory constants
	public static final double MAX_ACCELERATION = 4; //TODO: Change to 4 and remake
	public static final double MAX_JERK = 10;
	public static final double TIME_BETWEEN_POINTS = 0.02;			//Time (in seconds) between each Waypoint of the config
	
	/*
	 * Intake
	 */
	public static final int LEFT_INTAKE_SPARK = 0;				//Ports
	public static final int RIGHT_INTAKE_SPARK = 1;
	public static final int LEFT_INTAKE_SOLENOID = 0;
	public static final int RIGHT_INTAKE_SOLENOID = 7;
	
	public static final double SWITCH_AUTO_SPIT_SPEED = 0.5;
	public static final double SCALE_AUTO_SPIT_SPEED = 1.0;


	/*
	 * Lift
	 * 
	 * REMEMBER TO ADD GEAR RATIO TO ENC_COUNTS_PER_REV
	 */
	public static final int LEFT_BRAKE_SOLENOID = 3;			//Ports
	public static final int RIGHT_BRAKE_SOLENOID = 4;
	public static final int  LIFT_MOTOR_LEFT = 0;
	public static final int  LIFT_MOTOR_RIGHT = 1;
	
	public static int LIFT_ENC_COUNTS_PER_REV = 4096;			//Encoder counts per revolution
	
	public static final double	LIFT_LEFT_KF = 0;				//PID "Motion Magic" Constants - LEFT
	public static final double	LIFT_LEFT_KP = 0;				//They still need to be tuned
	public static final double	LIFT_LEFT_KI = 0;
	public static final double	LIFT_LEFT_KD = 0;
	
	public static final int	LIFT_LEFT_MOTION_CRUISE_VELOCITY = 0; //LEFT
	public static final int	LIFT_LEFT_MOTION_ACCELERATION = 0;	//These are in native encoder units per 100 meters
	
	public static final double	LIFT_RIGHT_KF = 0;				//PID "Motion Magic" Constants - RIGHT
	public static final double	LIFT_RIGHT_KP = 0;
	public static final double	LIFT_RIGHT_KI = 0;
	public static final double	LIFT_RIGHT_KD = 0;
	//These constants are in native units per 100m
	public static final int	LIFT_RIGHT_MOTION_CRUISE_VELOCITY = 0; //RIGHT
	public static final int	LIFT_RIGHT_MOTION_ACCELERATION = 0;
	
	public static final int CARRY_HEIGHT = 750; //poggers
	public static final int SWITCH_HEIGHT = 16000; //height of the switch, in right side encoder units
	public static final int SCALE_HEIGHT = 39000; //height of the scale, in right side encoder units
	

	
	/*
	 * Climber
	 */
	public static final int LATCH_LEFT_SOLENOID = 2;			//Ports
	public static final int LATCH_RIGHT_SOLENOID = 5;
	public static final int CLIMBER_MOTOR_1 = 12;
	public static final int CLIMBER_MOTOR_2 = 13;

}
