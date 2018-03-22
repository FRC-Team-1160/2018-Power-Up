/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1160.robot;

import java.io.File;

import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.MoveForward;
import org.usfirst.frc.team1160.robot.commands.auto.drive.TurnAngle;
import org.usfirst.frc.team1160.robot.commands.auto.paths.*;
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
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot implements TrajectoryWaypoints{
	public static OI oi;
	public Command autonomousCommand;
	public static DriveTrain dt;
	public static Intake intake;
	public static Climber climber;
	public static Lift lift;
	public static Trajectory segment_one,segment_two,segment_three;
	public static String gameData;
	public static char switchPosition, scalePosition, oppSwitchPosition;
	
	//Auto path choosing variables
	// 0 = default (in case code reads incorrectly)
	// 1 = left; 2 = middle; 3 = right
	private int startingPosition,
	/* autoChoices
	 * drive Straight(default): 0 
	 * Center to Left Switch: 1
	 * Left to Left Switch: 2
	 * Right to Right Switch: 3
	 * Center to Left Switch Backwards: 4
	 * Center to Right Switch: 5
	 */
	autoChoice;
	//to be used when scale autos are added
	private boolean goSwitch, goScale;
	
	
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
		
		
		/*
		 * generateSegments(),saveTrajectories(),loadTrajectories() Parameters
		 * 
		 * Center to Left Switch: 1
		 * Left to Left Switch: 2
		 * Right to Right Switch: 3
		 * Center to Left Switch Backwards: 4
		 * Center to Right Switch: 5
		 */
		//saveTrajectoriesAll();
		autonomousCommand = new TurnAngle(90);
		
	}
	
	public void generateSegments(int choice) { //let's be even more honest, you should only have to run this once after the csv loading code is made
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
				segment_one = dt.generateTrajectorySetup(POINTS_2);
				autonomousCommand = new GenericFollow(segment_one);
				break;
			default:
				System.out.println("Hold this L");
				break;
			
			
		}
	}
	
	public void saveTrajectories(int choice) { //let's be honest, you should only have to run this code once
		File file_one;
		switch (choice) {
			case 1:
				generateSegments(1);
				file_one = new File("CENTER_LEFT_SWITCH_1.traj");
				Pathfinder.writeToFile(file_one, segment_one);
				file_one = new File("CENTER_LEFT_SWITCH_2.traj");
				Pathfinder.writeToFile(file_one, segment_two);
				file_one = new File("CENTER_LEFT_SWITCH_3.traj");
				Pathfinder.writeToFile(file_one, segment_three);
				break;
			case 2:
			case 3:
				generateSegments(2);
				file_one = new File("X_X_SWITCH_1.traj");
				Pathfinder.writeToFile(file_one, segment_one);
				file_one = new File("X_X_SWITCH_2.traj");
				Pathfinder.writeToFile(file_one, segment_two);
				break;
			case 5: 
				generateSegments(5);
				file_one = new File("CENTER_RIGHT_SWITCH_1.traj");
				Pathfinder.writeToFile(file_one, segment_one);
				file_one = new File("CENTER_RIGHT_SWITCH_2.traj");
				Pathfinder.writeToFile(file_one, segment_two);
				file_one = new File("CENTER_RIGHT_SWITCH_3.traj");
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
		//file_one = new File("CENTER_LEFT_SWITCH_1.traj");
		file_one = new File("CLS1.traj");
		System.out.println("Attempted to save center left switch 1");
		Pathfinder.writeToFile(file_one, segment_one);
		System.out.println("Successfully saved center left switch 1");
		file_one = new File("CENTER_LEFT_SWITCH_2.traj");
		Pathfinder.writeToFile(file_one, segment_two);
		file_one = new File("CENTER_LEFT_SWITCH_3.traj");
		Pathfinder.writeToFile(file_one, segment_three);
		generateSegments(2);
		file_one = new File("X_X_SWITCH_1.traj");
		Pathfinder.writeToFile(file_one, segment_one);
		file_one = new File("X_X_SWITCH_2.traj");
		Pathfinder.writeToFile(file_one, segment_two);
		generateSegments(5);
		file_one = new File("CENTER_RIGHT_SWITCH_1.traj");
		Pathfinder.writeToFile(file_one, segment_one);
		file_one = new File("CENTER_RIGHT_SWITCH_2.traj");
		Pathfinder.writeToFile(file_one, segment_two);
		file_one = new File("CENTER_RIGHT_SWITCH_3.traj");
		Pathfinder.writeToFile(file_one, segment_three);
		
		System.out.println("All paths saved to csv");
	}
	
	public void loadTrajectories(int choice) { //now this is the good stuff, this is what you wanna run 25/7
		File file_one;
		switch (choice)
		{
			case 1:
				file_one = new File("CENTER_LEFT_SWITCH_1.traj");
				segment_one = Pathfinder.readFromFile(file_one);
				file_one = new File("CENTER_LEFT_SWITCH_2.traj");
				segment_two = Pathfinder.readFromFile(file_one);
				file_one = new File("CENTER_LEFT_SWITCH_3.traj");
				segment_three = Pathfinder.readFromFile(file_one);
				autonomousCommand = new Center_LeftSwitch();
				break;
			case 2:
				file_one = new File("X_X_SWITCH_1.traj");
				segment_one = Pathfinder.readFromFile(file_one);
				file_one = new File("X_X_SWITCH_2.traj");
				segment_two = Pathfinder.readFromFile(file_one);
				autonomousCommand = new Left_LeftSwitch();
				break;
			case 3:
				file_one = new File("X_X_SWITCH_1.traj");
				segment_one = Pathfinder.readFromFile(file_one);
				file_one = new File("X_X_SWITCH_2.traj");
				segment_two = Pathfinder.readFromFile(file_one);
				autonomousCommand = new Right_RightSwitch();
				break;
			case 5:
				file_one = new File("CENTER_RIGHT_SWITCH_1.traj");
				segment_one = Pathfinder.readFromFile(file_one);
				file_one = new File("CENTER_RIGHT_SWITCH_2.traj");
				segment_two = Pathfinder.readFromFile(file_one);
				file_one = new File("CENTER_RIGHT_SWITCH_3.traj");
				segment_three = Pathfinder.readFromFile(file_one);
				autonomousCommand = new Center_RightSwitch();
				break;
			default:
				System.out.println("Jesus christ man");
				break;
				
				
		}
	}

	public void chooseAuto() {
		/* autoChoices
		 * drive Straight(default): 0 
		 * Center to Left Switch: 1
		 * Left to Left Switch: 2
		 * Right to Right Switch: 3
		 * Center to Left Switch Backwards: 4
		 * Center to Right Switch: 5
		 */
		
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if (gameData.length() > 0) {
			switchPosition = gameData.charAt(0);
			scalePosition = gameData.charAt(1);
			oppSwitchPosition = gameData.charAt(2);
		}
		startingPosition = (int) SmartDashboard.getNumber("Starting Position (1-Left, 2-Mid, 3-Right)", 0);
		
		
		if (startingPosition == 2 && switchPosition == 'L') {
			autoChoice = 1;
		}
		else if (startingPosition == 1 && switchPosition == 'L') {
			autoChoice = 2;
		}
		else if (startingPosition == 3 && switchPosition == 'R') {
			autoChoice = 3;
		}
		else if (startingPosition == 2 && switchPosition == 'R') {
			autoChoice = 5;
		}
		else {
			autoChoice = 0;
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

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
		chooseAuto();
		
		loadTrajectories(autoChoice);
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		
		// schedule the autonomous command (example)
		autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
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

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
