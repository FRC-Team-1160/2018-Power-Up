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
import org.usfirst.frc.team1160.robot.commands.auto.paths.scale.parallel.*;
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
	public static Trajectory segment_one,segment_two,segment_three, //These three should be useless
							 segment_one_left,segment_one_right,    //The rest of these should be more useful
							 segment_two_left,segment_two_right,
							 segment_three_left,segment_three_right;
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
		autonomousCommand = new X_AutoLine();
		loadTrajectories(0);
		
	}
	
	
	/*
	 * generateSegments()
	 * Wednesday night: let's be even more honest, you should only have to run this once after the csv loading code is made
	 * Thursday afternoon: you better not ever call this method
	 
	 public void generateSegments(int choice) {
		switch (choice) {
			case 1: //Center to Left Switch
				segment_one = dt.generateTrajectorySetup(CENTER_LEFT_SWITCH_1);
				segment_two = dt.generateTrajectorySetup(CENTER_LEFT_SWITCH_2);
				segment_three = dt.generateTrajectorySetup(CENTER_LEFT_SWITCH_3);
				autonomousCommand = new Center_LeftSwitch();
				break;
			case 2: //Left to Left Switch
				segment_one = dt.generateTrajectorySetup(X_X_SWITCH_1);
				segment_two = dt.generateTrajectorySetup(X_X_SWITCH_2);
				autonomousCommand = new Left_LeftSwitch();
				break;
			case 3: //Right to Right Switch
				segment_one = dt.generateTrajectorySetup(X_X_SWITCH_1);
				segment_two = dt.generateTrajectorySetup(X_X_SWITCH_2);
				autonomousCommand = new Right_RightSwitch();
				break;
			case 4: //Center to Left Switch backwards
				segment_one = dt.generateTrajectorySetup(CENTER_LEFT_SWITCH_1_BACKWARDS);
				segment_two = dt.generateTrajectorySetup(CENTER_LEFT_SWITCH_2_BACKWARDS);
				segment_three = dt.generateTrajectorySetup(CENTER_LEFT_SWITCH_3_BACKWARDS);
				//autonomousCommand = new Center_LeftSwitchBW();
				break;
			case 5: //Center to Right Switch
				segment_one = dt.generateTrajectorySetup(CENTER_RIGHT_SWITCH_1);
				segment_two = dt.generateTrajectorySetup(CENTER_RIGHT_SWITCH_2);
				segment_three = dt.generateTrajectorySetup(CENTER_RIGHT_SWITCH_3);
				autonomousCommand = new Center_RightSwitch();
				break;
			case 6: //Custom
				segment_one = dt.generateTrajectorySetup(POINTS_1);
				autonomousCommand = new GenericFollow(segment_one);
				break;
			default:
				System.out.println("Hold this L");
				break;
		}
	}
	*/
	
	/*
	 * saveTrajectories(), saveTrajectoriesAll()
	 * Wednesday night: let's be honest, you should only have to run this code once
	 * Thursday afternoon: please don't ever call this method thanks
	
	public void saveTrajectories(int choice) {
		File file_one;
		switch (choice) {
			case 1:
				generateSegments(1);
				file_one = new File("CENTER_LEFT_SWITCH_1.csv");
				Pathfinder.writeToFile(file_one, segment_one);
				file_one = new File("CENTER_LEFT_SWITCH_2.csv");
				Pathfinder.writeToFile(file_one, segment_two);
				file_one = new File("CENTER_LEFT_SWITCH_3.csv");
				Pathfinder.writeToFile(file_one, segment_three);
				break;
			case 2:
			case 3:
				generateSegments(2);
				file_one = new File("X_X_SWITCH_1.csv");
				Pathfinder.writeToFile(file_one, segment_one);
				file_one = new File("X_X_SWITCH_2.csv");
				Pathfinder.writeToFile(file_one, segment_two);
				break;
			case 5: 
				generateSegments(5);
				file_one = new File("CENTER_RIGHT_SWITCH_1.csv");
				Pathfinder.writeToFile(file_one, segment_one);
				file_one = new File("CENTER_RIGHT_SWITCH_2.csv");
				Pathfinder.writeToFile(file_one, segment_two);
				file_one = new File("CENTER_RIGHT_SWITCH_3.csv");
				Pathfinder.writeToFile(file_one, segment_three);
				break;
			default:
				System.out.println("Hold this even bigger L");
				break;		
		}
	}
	public void saveTrajectoriesAll()
	{	//Kobe: "Just save them all"
		File file_one;
		generateSegments(1);
		//file_one = new File("CENTER_LEFT_SWITCH_1.csv");
		file_one = new File("CLS1.csv");
		System.out.println("Attempted to save center left switch 1");
		Pathfinder.writeToFile(file_one, segment_one);
		System.out.println("Successfully saved center left switch 1");
		file_one = new File("CENTER_LEFT_SWITCH_2.csv");
		Pathfinder.writeToFile(file_one, segment_two);
		file_one = new File("CENTER_LEFT_SWITCH_3.csv");
		Pathfinder.writeToFile(file_one, segment_three);
		generateSegments(2);
		file_one = new File("X_X_SWITCH_1.csv");
		Pathfinder.writeToFile(file_one, segment_one);
		file_one = new File("X_X_SWITCH_2.csv");
		Pathfinder.writeToFile(file_one, segment_two);
		generateSegments(5);
		file_one = new File("CENTER_RIGHT_SWITCH_1.csv");
		Pathfinder.writeToFile(file_one, segment_one);
		file_one = new File("CENTER_RIGHT_SWITCH_2.csv");
		Pathfinder.writeToFile(file_one, segment_two);
		file_one = new File("CENTER_RIGHT_SWITCH_3.csv");
		Pathfinder.writeToFile(file_one, segment_three);
		
		System.out.println("All paths saved to csv");
	}
	*/

	
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
	 * 1:  CENTER | LEFT SWITCH  | TWO CUBES | FAST |
	 * 2:  CENTER | RIGHT SWITCH | TWO CUBES | FAST |
	 * 3:  CENTER | LEFT SWITCH  | TWO CUBES | SLOW |
	 * 4:  CENTER | RIGHT SWITCH | TWO CUBES | SLOW | 
	 * 5:  CENTER | LEFT SWITCH  | ONE CUBE  | FAST |
	 * 6:  CENTER | RIGHT SWITCH | ONE CUBE  | FAST |
	 * 7:  CENTER | LEFT SWITCH  | ONE CUBE  | SLOW |
	 * 8:  CENTER | RIGHT SWITCH | ONE CUBE  | SLOW |
	 * 9:  LEFT   | LEFT SCALE   | TWO CUBES | FAST |
	 * 10: LEFT   | RIGHT SCALE  | TWO CUBES | FAST |
	 * 11: LEFT   | LEFT SCALE   | TWO CUBES | SLOW |
	 * 12: LEFT   | RIGHT SCALE  | TWO CUBES | SLOW |
	 * 13: LEFT   | LEFT SCALE   | ONE CUBE  | FAST |
	 * 14: LEFT   | RIGHT SCALE  | ONE CUBE  | FAST |
	 * 15: LEFT   | LEFT SCALE   | ONE CUBE  | SLOW |
	 * 16: LEFT   | RIGHT SCALE  | ONE CUBE  | SLOW |
	 * 17: LEFT   | LEFT SWITCH  | TWO CUBES | FAST |
	 * 18: LEFT   | RIGHT SWITCH | TWO CUBES | FAST |
	 * 19: LEFT   | LEFT SWITCH  | TWO CUBES | SLOW |
	 * 20: LEFT   | RIGHT SWITCH | TWO CUBES | SLOW |
	 * 21: LEFT   | LEFT SWITCH  | ONE CUBE  | FAST |
	 * 22: LEFT   | RIGHT SWITCH | ONE CUBE  | FAST |
	 * 23: LEFT   | LEFT SWITCH  | ONE CUBE  | SLOW |
	 * 24: LEFT   | RIGHT SWITCH | ONE CUBE  | SLOW |
	 * 25: RIGHT  | LEFT SCALE   | TWO CUBES | FAST |
	 * 26: RIGHT  | RIGHT SCALE  | TWO CUBES | FAST |
	 * 27: RIGHT  | LEFT SCALE   | TWO CUBES | SLOW |
	 * 28: RIGHT  | RIGHT SCALE  | TWO CUBES | SLOW |
	 * 29: RIGHT  | LEFT SCALE   | ONE CUBE  | FAST |
	 * 30: RIGHT  | RIGHT SCALE  | ONE CUBE  | FAST |
	 * 31: RIGHT  | LEFT SCALE   | ONE CUBE  | SLOW |
	 * 32: RIGHT  | RIGHT SCALE  | ONE CUBE  | SLOW |
	 * 33: RIGHT  | LEFT SWITCH  | TWO CUBES | FAST |
	 * 34: RIGHT  | RIGHT SWITCH | TWO CUBES | FAST |
	 * 35: RIGHT  | LEFT SWITCH  | TWO CUBES | SLOW | 
	 * 36. RIGHT  | RIGHT SWITCH | TWO CUBES | SLOW |
	 * 37: RIGHT  | LEFT SWITCH  | ONE CUBE  | FAST |
	 * 38: RIGHT  | RIGHT SWITCH | ONE CUBE  | FAST |
	 * 39: RIGHT  | LEFT SWITCH  | ONE CUBE  | SLOW |
	 * 40: RIGHT  | RIGHT SWITCH | ONE CUBE  | SLOW |
	 */
	
	public void loadTrajectories(int choice) {
		File left,right;
		String baseFilepath = "/home/lvuser/motionProfiles/";
		switch (choice)
		{
			case 5: //center to left switch, one cube, fast
				left = new File(baseFilepath + "CENTER_LEFT_SWITCH_ONE_FAST_1_LEFT.csv");
				right = new File(baseFilepath + "CENTER_LEFT_SWITCH_ONE_FAST_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "CENTER_LEFT_SWITCH_ONE_FAST_2_LEFT.csv");
				right = new File(baseFilepath + "CENTER_LEFT_SWITCH_ONE_FAST_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Center_LeftSwitch_Fast();
				break;
				
			case 6: //center to right switch, one cube, fast
				left = new File(baseFilepath + "CENTER_RIGHT_SWITCH_ONE_FAST_1_LEFT.csv");
				right = new File(baseFilepath + "CENTER_RIGHT_SWITCH_ONE_FAST_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "CENTER_RIGHT_SWITCH_ONE_FAST_2_LEFT.csv");
				right = new File(baseFilepath + "CENTER_RIGHT_SWITCH_ONE_FAST_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Center_RightSwitch_Fast();
				break;
			
			case 15: //left to left scale, one cube, slow
				left = new File(baseFilepath + "X_X_SCALE_ONE_SLOW_LEFT.csv");
				right = new File(baseFilepath + "X_X_SCALE_ONE_SLOW_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Left_LeftScale_Parallel();
				break;
				
			case 16: //left to right scale, one cube, slow
				left = new File(baseFilepath + "X_Y_SCALE_ONE_SLOW_1_LEFT.csv");
				right = new File(baseFilepath + "X_Y_SCALE_ONE_SLOW_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "X_Y_SCALE_ONE_SLOW_2_LEFT.csv");
				right = new File(baseFilepath + "X_Y_SCALE_ONE_SLOW_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Left_RightScale_Parallel();
				break;
			
			case 23: //left to left switch, one cube, slow
				left = new File(baseFilepath + "X_X_SWITCH_ONE_SLOW_1_LEFT.csv");
				right = new File(baseFilepath + "X_X_SWITCH_ONE_SLOW_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "X_X_SWITCH_ONE_SLOW_2_LEFT.csv");
				right = new File(baseFilepath + "X_X_SWITCH_ONE_SLOW_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Left_LeftSwitch();
				break;
				
			case 31: //right to left scale, one cube, slow
				left = new File(baseFilepath + "X_Y_SCALE_ONE_SLOW_1_LEFT.csv");
				right = new File(baseFilepath + "X_Y_SCALE_ONE_SLOW_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "X_Y_SCALE_ONE_SLOW_2_LEFT.csv");
				right = new File(baseFilepath + "X_Y_SCALE_ONE_SLOW_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Right_LeftScale_Parallel();
				break;

			case 32: //right to right scale, one cube, slow
				left = new File(baseFilepath + "X_X_SCALE_ONE_SLOW_LEFT.csv");
				right = new File(baseFilepath + "X_X_SCALE_ONE_SLOW_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Right_RightScale_Parallel();
				break;
			
			case 40: //right to right switch, one cube, slow
				left = new File(baseFilepath + "X_X_SWITCH_ONE_SLOW_1_LEFT.csv");
				right = new File(baseFilepath + "X_X_SWITCH_ONE_SLOW_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "X_X_SWITCH_ONE_SLOW_2_LEFT.csv");
				right = new File(baseFilepath + "X_X_SWITCH_ONE_SLOW_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Right_RightSwitch();
				break;
				
			default: //move to the auto line, no cubes, slow
				/*
				left = new File(baseFilepath + "X_X_AUTOLINE_NONE_SLOW_LEFT.csv");
				right = new File(baseFilepath + "X_X_AUTOLINE_NONE_SLOW_RIGHT.csv");
				*/
				left = new File(baseFilepath + "CURVE_LEFT.csv");
				right = new File(baseFilepath + "CURVE_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				autonomousCommand = new X_AutoLine();
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
		
		autoChoice = AutoSettings.choosePath(switchPosition,scalePosition);
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
