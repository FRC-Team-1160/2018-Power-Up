/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1160.robot;

import org.usfirst.frc.team1160.robot.commands.auto.FollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.MoveForward;
import org.usfirst.frc.team1160.robot.commands.auto.TurnAngle;
import org.usfirst.frc.team1160.robot.commands.auto.paths.Center_LeftSwitch;
import org.usfirst.frc.team1160.robot.commands.auto.paths.MoveL;
import org.usfirst.frc.team1160.robot.subsystems.Climber;
import org.usfirst.frc.team1160.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1160.robot.subsystems.Intake;
import org.usfirst.frc.team1160.robot.subsystems.Lift;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
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
		System.out.println(System.getProperty("java.library.path"));
		
		generateSegments(3);
		/*
		segment_one = dt.generateTrajectorySetup(POINTS_1);
		segment_two = dt.generateTrajectorySetup(POINTS_1);
		//autonomousCommand = new TurnAngle(90);
		autonomousCommand = new MoveL();
		//autonomousCommand = new TurnAngle(-45);
		 */
		autonomousCommand = new Center_LeftSwitch();
	}
	
	public void generateSegments(int choice) {
		switch (choice) {
			case 1: //Center to Left Switch
				segment_one = dt.generateTrajectorySetup(CENTER_LEFT_SWITCH_1);
				segment_two = dt.generateTrajectorySetup(CENTER_LEFT_SWITCH_2);
				segment_three = dt.generateTrajectorySetup(CENTER_LEFT_SWITCH_3);
				break;
			case 2: //X to X Switch
				segment_one = dt.generateTrajectorySetup(X_X_SWITCH_1);
				segment_two = dt.generateTrajectorySetup(X_X_SWITCH_2);
			case 3: //Center to Left Switch backwards
				segment_one = dt.generateTrajectorySetup(CENTER_LEFT_SWITCH_1_BACKWARDS);
				segment_two = dt.generateTrajectorySetup(CENTER_LEFT_SWITCH_2_BACKWARDS);
				segment_three = dt.generateTrajectorySetup(CENTER_LEFT_SWITCH_3_BACKWARDS);
				
			default:
				System.out.println("Hold this L");
				break;
			
			
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
