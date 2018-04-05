/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
	
	/**TODO:
	 * 1. Regenerate paths with new accl value of 4 - DONE
	 * 2. Scale auto - DONE
	 * 3. New switch autos (center) - DONE
	 * 4. Optimize command groups (what works best in parallel)?
	 * 5. 2 cube auto (scale or switch)
	 * 6. Far side scale - DONE
	 * 7. Check auto functionality
	 * 	a. paths
	 * 	b. AutoBoxSpit()
	 * 8. rename auto paths to fit new csv naming conventions
	 */

package org.usfirst.frc.team1160.robot;

import java.io.File;

import org.usfirst.frc.team1160.robot.commands.auto.drive.*;
import org.usfirst.frc.team1160.robot.commands.auto.intake.*;
import org.usfirst.frc.team1160.robot.commands.auto.paths.*;
import org.usfirst.frc.team1160.robot.commands.auto.paths.scale.*;
import org.usfirst.frc.team1160.robot.commands.auto.paths.switch_.*;
import org.usfirst.frc.team1160.robot.subsystems.Climber;
import org.usfirst.frc.team1160.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1160.robot.subsystems.Intake;
import org.usfirst.frc.team1160.robot.subsystems.Lift;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot implements TrajectoryWaypoints,RobotMap,AutoSettings{
	public static OI oi;
	public Command autonomousCommand;
	public static DriveTrain dt;
	public static Intake intake;
	public static Climber climber;
	public static Lift lift;
	public static Trajectory /*segment_one,segment_two,segment_three, *///These three should be useless
							 segment_one_left,segment_one_right,    //The rest of these should be more useful
							 segment_two_left,segment_two_right,
							 segment_three_left,segment_three_right,
							 segment_four_left,segment_four_right;
	public static String gameData;
	public static char switchPosition, scalePosition, oppSwitchPosition;
	private static int autoChoice;
	
	@Override
	public void robotInit() {
		dt = DriveTrain.getInstance();
		intake = Intake.getInstance();
		climber = Climber.getInstance();
		lift = Lift.getInstance();
		oi = OI.getInstance();
		
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		//System.out.println(System.getProperty("java.library.path"));

		//autonomousCommand = new TurnAngle(-25.67);
		//autonomousCommand = new LoadFollowTrajectory(Robot.segment_one_left,Robot.segment_one_right);
		loadTrajectories(0);
		
	}

	
	/*
	 * loadTrajectories()
	 * Thursday morning: now this is the good stuff, this is what you wanna run 25/7
	 * Friday morning: for god's sake please make sure there are break statements after every case in the switch
	 * 
	 */
	
	/**
	 * CSV naming conventions:
	 * <starting position>_<ending side>_<ending objective>_<number of cubes>_<speed>_<trajectory number>_<trajectory side>
	 * CENTER_LEFT_SWITCH_TWO_FAST_1_LEFT to represent the first segment of the left trajectory of choice 1, for example
	 * 
	 * autoChoice() cases:
	 * 1:  CENTER | LEFT SWITCH  | TWO CUBES |
	 * 2:  CENTER | RIGHT SWITCH | TWO CUBES |
	 * 3:  CENTER | LEFT SWITCH  | ONE CUBE  |
	 * 4:  CENTER | RIGHT SWITCH | ONE CUBE  |
	 * 5:  LEFT   | LEFT SCALE   | TWO CUBES |
	 * 6:  LEFT   | RIGHT SCALE  | TWO CUBES |
	 * 7:  LEFT   | LEFT SCALE   | ONE CUBE  |
	 * 8:  LEFT   | RIGHT SCALE  | ONE CUBE  |
	 * 9:  LEFT   | LEFT SWITCH  | TWO CUBES |
	 * 10: LEFT   | RIGHT SWITCH | TWO CUBES |
	 * 11: LEFT   | LEFT SWITCH  | ONE CUBE  |
	 * 12: LEFT   | RIGHT SWITCH | ONE CUBE  |
	 * 13: RIGHT  | LEFT SCALE   | TWO CUBES |
	 * 14: RIGHT  | RIGHT SCALE  | TWO CUBES |
	 * 15: RIGHT  | LEFT SCALE   | ONE CUBE  |
	 * 16: RIGHT  | RIGHT SCALE  | ONE CUBE  |
	 * 17: RIGHT  | LEFT SWITCH  | TWO CUBES |
	 * 18: RIGHT  | RIGHT SWITCH | TWO CUBES |
	 * 19: RIGHT  | LEFT SWITCH  | ONE CUBE  |
	 * 20: RIGHT  | RIGHT SWITCH | ONE CUBE  |
	 * 
	 * 
	 */
	
	public void loadTrajectories(int choice) {
		File left,right;
		String baseFilepath = "/home/lvuser/motionProfiles/";
		switch (choice)
		{
			default: //move to the auto line, no cubes, slow
				/*
				left = new File(baseFilepath + "X_X_AUTOLINE_NONE_SLOW_LEFT.csv");
				right = new File(baseFilepath + "X_X_AUTOLINE_NONE_SLOW_RIGHT.csv");
				*/
				left = new File(baseFilepath + "SCALE_OPTIMAL_LEFT.csv");
				right = new File(baseFilepath + "SCALE_OPTIMAL_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "REVERSE_1_LEFT.csv");
				right = new File(baseFilepath + "REVERSE_1_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);	
				
				left = new File(baseFilepath + "FORWARD_1_LEFT.csv");
				right = new File(baseFilepath + "FORWARD_1_RIGHT.csv");
				segment_three_left = Pathfinder.readFromCSV(left);
				segment_three_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "REVERSE_2_LEFT.csv");
				right = new File(baseFilepath + "REVERSE_2_RIGHT.csv");
				segment_four_left = Pathfinder.readFromCSV(left);
				segment_four_right = Pathfinder.readFromCSV(right);
				//autonomousCommand = new X_AutoLine();
				autonomousCommand = new GenericFollow();
				
				break;
				
				
				
		}
	}
	
	public void chooseAuto() {
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if (gameData.length() > 0) {
			switchPosition = gameData.charAt(0);
			scalePosition = gameData.charAt(1);
			oppSwitchPosition = gameData.charAt(2);
			System.out.println("Game Data = " + gameData);
			
		}
		
		//autoChoice = AutoSettings.choosePath(switchPosition,scalePosition);
	}
	
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		
	}

	@Override
	public void autonomousInit() {
		
		//loadTrajectories(autoChoice);
		
		autonomousCommand.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		autonomousCommand.cancel();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}
