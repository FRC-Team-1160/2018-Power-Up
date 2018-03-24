/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

	/**
	 * generateSegments(),saveTrajectories(),loadTrajectories() Parameters - autoChoices() logic
	 * drive Straight(default): 0 
	 * Center to Left Switch: 1
	 * Left to Left Switch: 2
	 * Right to Right Switch: 3
	 * Center to Left Switch Backwards: 4
	 * Center to Right Switch: 5
	 * Center to Left Switch Fast: 6
	 * Center to Right Switch Fast: 7
	 * Left to Left Scale: 8
	 * Right to Right Scale: 9
	 * Left to Right Scale: 10
	 * Right to Left Scale: 11
	 */
	
	/**TODO:
	 * 1. Regenerate paths with new accl value of 4 - DONE
	 * 2. Scale auto
	 * 3. New switch autos (center) - DONE
	 * 4. Optimize command groups (what works best in parallel)?
	 * 5. 2 cube auto (scale or switch)
	 * 6. Far side scale
	 * 7. Check auto functionality
	 * 	a. paths
	 * 	b. AutoBoxSpit()
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
public class Robot extends TimedRobot implements TrajectoryWaypoints,RobotMap{
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
	
	//Auto path choosing variables
	// 0 = default (in case code reads incorrectly)
	// 1 = left; 2 = middle; 3 = right
	private int startingPosition, autoChoice;
	//to be used when scale autos are added
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		dt = DriveTrain.getInstance();
		intake = Intake.getInstance();
		climber = Climber.getInstance();
		lift = Lift.getInstance();
		oi = OI.getInstance();
		
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		//System.out.println(System.getProperty("java.library.path"));

		autonomousCommand = new X_AutoLine();
		
	}
	
	
	/*
	 * generateSegments()
	 * Wednesday night: let's be even more honest, you should only have to run this once after the csv loading code is made
	 * Thursday afternoon: you better not ever call this method
	 */
	
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
	
	/*
	 * saveTrajectories(), saveTrajectoriesAll()
	 * Wednesday night: let's be honest, you should only have to run this code once
	 * Thursday afternoon: please don't ever call this method thanks
	 */
	
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

	
	/*
	 * loadTrajectories()
	 * Thursday morning: now this is the good stuff, this is what you wanna run 25/7
	 * Friday morning: for god's sake please make sure there are break statements after every case in the switch
	 */
	public void loadTrajectories(int choice) {
		File left,right;
		String baseFilepath = "/home/lvuser/motionProfiles/";
		switch (choice)
		{
			case 1: //center to left switch
				left = new File(baseFilepath + "CENTER_LEFT_SWITCH_1_LEFT.csv");
				right = new File(baseFilepath + "CENTER_LEFT_SWITCH_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "CENTER_LEFT_SWITCH_2_LEFT.csv");
				right = new File(baseFilepath + "CENTER_LEFT_SWITCH_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "CENTER_LEFT_SWITCH_3_LEFT.csv");
				right = new File(baseFilepath + "CENTER_LEFT_SWITCH_3_RIGHT.csv");
				segment_three_left = Pathfinder.readFromCSV(left);
				segment_three_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Center_LeftSwitch();
				break;
			case 2: //left to left switch
				left = new File(baseFilepath + "X_X_SWITCH_1_LEFT.csv");
				right = new File(baseFilepath + "X_X_SWITCH_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "X_X_SWITCH_2_LEFT.csv");
				right = new File(baseFilepath + "X_X_SWITCH_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Left_LeftSwitch();
				break;
			case 3: //right to right switch
				left = new File(baseFilepath + "X_X_SWITCH_1_LEFT.csv");
				right = new File(baseFilepath + "X_X_SWITCH_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "X_X_SWITCH_2_LEFT.csv");
				right = new File(baseFilepath + "X_X_SWITCH_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Right_RightSwitch();
				break;
			case 5: //center to right switch
				left = new File(baseFilepath + "CENTER_RIGHT_SWITCH_1_LEFT.csv");
				right = new File(baseFilepath + "CENTER_RIGHT_SWITCH_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "CENTER_RIGHT_SWITCH_2_LEFT.csv");
				right = new File(baseFilepath + "CENTER_RIGHT_SWITCH_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "CENTER_RIGHT_SWITCH_3_LEFT.csv");
				right = new File(baseFilepath + "CENTER_RIGHT_SWITCH_3_RIGHT.csv");
				segment_three_left = Pathfinder.readFromCSV(left);
				segment_three_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Center_RightSwitch();
				break;
			case 6: //center to left switch fast
				left = new File(baseFilepath + "CENTER_LEFT_SWITCH_FAST_1_LEFT.csv");
				right = new File(baseFilepath + "CENTER_LEFT_SWITCH_FAST_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "CENTER_LEFT_SWITCH_FAST_2_LEFT.csv");
				right = new File(baseFilepath + "CENTER_LEFT_SWITCH_FAST_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Center_LeftSwitch_Fast();
				break;
			case 7: //center to right switch fast
				left = new File(baseFilepath + "CENTER_RIGHT_SWITCH_FAST_1_LEFT.csv");
				right = new File(baseFilepath + "CENTER_RIGHT_SWITCH_FAST_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "CENTER_RIGHT_SWITCH_FAST_2_LEFT.csv");
				right = new File(baseFilepath + "CENTER_RIGHT_SWITCH_FAST_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Center_RightSwitch_Fast();
				break;
			case 8: //left to left scale
				left = new File(baseFilepath + "X_X_SCALE_LEFT.csv");
				right = new File(baseFilepath + "X_X_SCALE_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				autonomousCommand = new Left_LeftScale_Parallel();
				break;
			case 9: //right to right scale
				left = new File(baseFilepath + "X_X_SCALE_LEFT.csv");
				right = new File(baseFilepath + "X_X_SCALE_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				autonomousCommand = new Right_RightScale_Parallel();
				break;
			case 10: //left to right scale
				left = new File(baseFilepath + "X_Y_SCALE_1_LEFT.csv");
				right = new File(baseFilepath + "X_Y_SCALE_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				left = new File(baseFilepath + "X_Y_SCALE_2_LEFT.csv");
				right = new File(baseFilepath + "X_Y_SCALE_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				autonomousCommand = new Left_RightScale_Parallel();
			case 11: //right to left scale
				left = new File(baseFilepath + "X_Y_SCALE_1_LEFT.csv");
				right = new File(baseFilepath + "X_Y_SCALE_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				left = new File(baseFilepath + "X_Y_SCALE_2_LEFT.csv");
				right = new File(baseFilepath + "X_Y_SCALE_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				autonomousCommand = new Right_LeftScale_Parallel();
			default: //move to the auto line
				left = new File(baseFilepath + "X_X_AUTOLINE_LEFT.csv");
				right = new File(baseFilepath + "X_X_AUTOLINE_RIGHT.csv");
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
		
		//startingPosition = (int) SmartDashboard.getNumber("Starting Position (1-Left, 2-Mid, 3-Right)", 0);
		
		startingPosition = HARDCODED_POSITION; //this is a hardcoded fallback
		
		if(PRIORITIZE_OPPOSITE_SCALE) {
			if (startingPosition == 1 && scalePosition == 'R') {
				autoChoice = 10;
			}
			else if (startingPosition == 3 && scalePosition == 'L') {
				autoChoice = 11;
			}
		}
		else {
			if (startingPosition == 1 && 
				scalePosition == 'L' && 
				(switchPosition != 'L' || SCALE_OVER_SWITCH) &&
				SCALE) {
					autoChoice = 8;
			}
			else if (startingPosition == 3 && 
					 scalePosition == 'R' && 
					 (switchPosition != 'R' || SCALE_OVER_SWITCH) &&
						SCALE) {
						autoChoice = 9;
			}
			
			else if (startingPosition == 2 && 
					 switchPosition == 'L' && 
					 !FAST_SWITCH) {
						autoChoice = 1;
			}
			else if (startingPosition == 1 && 
					 switchPosition == 'L') {
						autoChoice = 2;
			}
			else if (startingPosition == 3 && 
					 switchPosition == 'R' ) {
						autoChoice = 3;
			}
			else if (startingPosition == 2 && 
					 switchPosition == 'R' && 
					 !FAST_SWITCH) {
						autoChoice = 5;
			}
			else if (startingPosition == 2 && 
					 switchPosition == 'L' && 
					 FAST_SWITCH) {
				autoChoice = 6;
			}
			else if (startingPosition == 2 && 
					 switchPosition == 'R' && 
					 FAST_SWITCH) {
				autoChoice = 7;
			}
			else {
				autoChoice = 0;
			}
		}
		
		
	}
	
	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		
	}

	@Override
	public void autonomousInit() {
		
		chooseAuto();
		loadTrajectories(autoChoice);
		
		autonomousCommand.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
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
