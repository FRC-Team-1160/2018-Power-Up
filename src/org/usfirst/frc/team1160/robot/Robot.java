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
	public static Trajectory segment_one_left,segment_one_right,
							 segment_two_left,segment_two_right,
							 segment_three_left,segment_three_right,
							 segment_four_left,segment_four_right,
							 segment_five_left, segment_five_right,
							 segment_six_left, segment_six_right;
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
	 * 1: LEFT | LEFT SCALE   | TWO CUBES +
	 * 2: LEFT | LEFT SCALE   | ONE CUBE +/
	 * 3: LEFT | RIGHT SCALE  | TWO CUBES + **LOL DW ABT THAT RN
	 * 4: LEFT | RIGHT SCALE  | ONE CUBE +
	 * 5: LEFT | LEFT SWITCH  | TWO CUBES **NOT VIABLE
	 * 6: LEFT | LEFT SWITCH  | ONE CUBE **NOT VIABLE
	 * 7: LEFT | RIGHT SWITCH | TWO CUBES **NOT VIABLE
	 * 8: LEFT | RIGHT SWITCH | ONE CUBE  **NOT VIABLE
	 * 9:  CENTER | LEFT SWITCH | TWO CUBES +
	 * 10: CENTER | LEFT SWITCH | ONE CUBE +
	 * 11: CENTER | RIGHT SWITCH| TWO CUBES +
	 * 12: CENTER | RIGHT SWITCH| ONE CUBE +
	 * 13: RIGHT | LEFT SCALE  | TWO CUBES +
	 * 14: RIGHT | LEFT SCALE  | ONE CUBE +
	 * 15: RIGHT | RIGHT SCALE | TWO CUBES **LOL DW ABT THAT RN
	 * 16: RIGHT | RIGHT SCALE | ONE CUBE 
	 * 17: RIGHT | LEFT SWITCH | TWO CUBES **NOT VIABLE
	 * 18: RIGHT | LEFT SWITCH | ONE CUBE  **NOT VIABLE
	 * 19: RIGHT | RIGHT SWITCH| TWO CUBES **NOT VIABLE
	 * 20: RIGHT | RIGHT SWITCH| ONE CUBE **NOT VIABLE
	 */
	
	public void loadTrajectories(int choice) {
		File left,right;
		String baseFilepath = "/home/lvuser/motionProfiles/";
		switch (choice)
		{
			case 1: //left to left scale, two cubes		
				left = new File(baseFilepath + "LEFT_LEFTSCALE_1_LEFT.csv");
				right = new File(baseFilepath + "LEFT_LEFTSCALE_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "LEFT_LEFTSCALE_2_LEFT.csv");
				right = new File(baseFilepath + "LEFT_LEFTSCALE_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "LEFT_LEFTSCALE_3_LEFT.csv");
				right = new File(baseFilepath + "LEFT_LEFTSCALE_3_RIGHT.csv");
				segment_three_left = Pathfinder.readFromCSV(left);
				segment_three_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "LEFT_LEFTSCALE_4_LEFT.csv");
				right = new File(baseFilepath + "LEFT_LEFTSCALE_4_RIGHT.csv");
				segment_four_left = Pathfinder.readFromCSV(left);
				segment_four_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "LEFT_LEFTSCALE_5_LEFT.csv");
				right = new File(baseFilepath + "LEFT_LEFTSCALE_5_RIGHT.csv");
				segment_five_left = Pathfinder.readFromCSV(left);
				segment_five_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "LEFT_LEFTSCALE_6_LEFT.csv");
				right = new File(baseFilepath + "LEFT_LEFTSCALE_6_RIGHT.csv");
				segment_six_left = Pathfinder.readFromCSV(left);
				segment_six_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Left_LeftScale_Two();
				break;
				
			case 2: //left to left scale, one cube
				left = new File(baseFilepath + "LEFT_LEFTSCALE_1_LEFT.csv");
				right = new File(baseFilepath + "LEFT_LEFTSCALE_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "LEFT_LEFTSCALE_2_LEFT.csv");
				right = new File(baseFilepath + "LEFT_LEFTSCALE_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Left_LeftScale_One();
				break;
				
			case 3: //left to right scale, two cubes
				
			case 4: //left to right scale, one cube
				left = new File(baseFilepath + "LEFT_RIGHTSCALE_1_LEFT.csv");
				right = new File(baseFilepath + "LEFT_RIGHTSCALE_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "LEFT_RIGHTSCALE_2_LEFT.csv");
				right = new File(baseFilepath + "LEFT_RIGHTSCALE_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Left_RightScale_One();
				break;
				
			case 6: //left to left switch. one cube
				left = new File(baseFilepath + "LEFT_LEFTSWITCH_1_LEFT.csv");
				right = new File(baseFilepath + "LEFT_LEFTSWITCH_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				autonomousCommand = new Left_LeftSwitch_One();
				break;
				
			case 9: //center to left switch, two cubes
				left = new File(baseFilepath + "CENTER_LEFTSWITCH_1_LEFT.csv");
				right = new File(baseFilepath + "CENTER_LEFTSWITCH_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "CENTER_LEFTSWITCH_2_LEFT.csv");
				right = new File(baseFilepath + "CENTER_LEFTSWITCH_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "CENTER_LEFTSWITCH_3_LEFT.csv");
				right = new File(baseFilepath + "CENTER_LEFTSWITCH_3_RIGHT.csv");
				segment_three_left = Pathfinder.readFromCSV(left);
				segment_three_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "CENTER_LEFTSWITCH_4_LEFT.csv");
				right = new File(baseFilepath + "CENTER_LEFTSWITCH_4_RIGHT.csv");
				segment_four_left = Pathfinder.readFromCSV(left);
				segment_four_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "CENTER_LEFTSWITCH_5_LEFT.csv");
				right = new File(baseFilepath + "CENTER_LEFTSWITCH_5_RIGHT.csv");
				segment_five_left = Pathfinder.readFromCSV(left);
				segment_five_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Center_LeftSwitch_Two();
				break;
				
			case 10: //center to left switch, one cube
				left = new File(baseFilepath + "CENTER_LEFTSWITCH_1_LEFT.csv");
				right = new File(baseFilepath + "CENTER_LEFTSWITCH_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Center_LeftSwitch_One();
				break;
			case 11: //center to right switch, two cubes
				left = new File(baseFilepath + "CENTER_RIGHTSWITCH_1_LEFT.csv");
				right = new File(baseFilepath + "CENTER_RIGHTSWITCH_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "CENTER_RIGHTSWITCH_2_LEFT.csv");
				right = new File(baseFilepath + "CENTER_RIGHTSWITCH_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "CENTER_RIGHTSWITCH_3_LEFT.csv");
				right = new File(baseFilepath + "CENTER_RIGHTSWITCH_3_RIGHT.csv");
				segment_three_left = Pathfinder.readFromCSV(left);
				segment_three_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "CENTER_RIGHTSWITCH_4_LEFT.csv");
				right = new File(baseFilepath + "CENTER_RIGHTSWITCH_4_RIGHT.csv");
				segment_four_left = Pathfinder.readFromCSV(left);
				segment_four_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "CENTER_RIGHTSWITCH_5_LEFT.csv");
				right = new File(baseFilepath + "CENTER_RIGHTSWITCH_5_RIGHT.csv");
				segment_five_left = Pathfinder.readFromCSV(left);
				segment_five_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Center_RightSwitch_Two();
				break;
			case 12: //center to right switch, one cube
				left = new File(baseFilepath + "CENTER_RIGHTSWITCH_1_LEFT.csv");
				right = new File(baseFilepath + "CENTER_RIGHTSWITCH_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Center_RightSwitch_One();
				break;
			case 13: //right to left scale, two cubes
				
			case 14: //right to left scale, one cube
				left = new File(baseFilepath + "RIGHT_LEFTSCALE_1_LEFT.csv");
				right = new File(baseFilepath + "RIGHT_LEFTSCALE_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "RIGHT_LEFTSCALE_2_LEFT.csv");
				right = new File(baseFilepath + "RIGHT_LEFTSCALE_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Right_LeftScale_One();
				break;
			case 15: //right to right scale, two cubes
				left = new File(baseFilepath + "RIGHT_RIGHTSCALE_1_LEFT.csv");
				right = new File(baseFilepath + "RIGHT_RIGHTSCALE_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "RIGHT_RIGHTSCALE_2_LEFT.csv");
				right = new File(baseFilepath + "RIGHT_RIGHTSCALE_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "RIGHT_RIGHTSCALE_3_LEFT.csv");
				right = new File(baseFilepath + "RIGHT_RIGHTSCALE_3_RIGHT.csv");
				segment_three_left = Pathfinder.readFromCSV(left);
				segment_three_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "RIGHT_RIGHTSCALE_4_LEFT.csv");
				right = new File(baseFilepath + "RIGHT_RIGHTSCALE_4_RIGHT.csv");
				segment_four_left = Pathfinder.readFromCSV(left);
				segment_four_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "RIGHT_RIGHTSCALE_5_LEFT.csv");
				right = new File(baseFilepath + "RIGHT_RIGHTSCALE_5_RIGHT.csv");
				segment_five_left = Pathfinder.readFromCSV(left);
				segment_five_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "RIGHT_RIGHTSCALE_6_LEFT.csv");
				right = new File(baseFilepath + "RIGHT_RIGHTSCALE_6_RIGHT.csv");
				segment_six_left = Pathfinder.readFromCSV(left);
				segment_six_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Left_LeftScale_Two();
				break;
			case 16: //right to right scale, one cube
				left = new File(baseFilepath + "RIGHT_RIGHTSCALE_1_LEFT.csv");
				right = new File(baseFilepath + "RIGHT_RIGHTSCALE_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				
				left = new File(baseFilepath + "RIGHT_RIGHTSCALE_2_LEFT.csv");
				right = new File(baseFilepath + "RIGHT_RIGHTSCALE_2_RIGHT.csv");
				segment_two_left = Pathfinder.readFromCSV(left);
				segment_two_right = Pathfinder.readFromCSV(right);
				
				autonomousCommand = new Right_RightScale_One();
				break;
			case 20: //right to right switch, one cube
				left = new File(baseFilepath + "RIGHT_RIGHTSWITCH_1_LEFT.csv");
				right = new File(baseFilepath + "RIGHT_RIGHTSWITCH_1_RIGHT.csv");
				segment_one_left = Pathfinder.readFromCSV(left);
				segment_one_right = Pathfinder.readFromCSV(right);
				autonomousCommand = new Right_RightSwitch_One();
				break;
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
		
		autoChoice = AutoSettings.chooseAuto(switchPosition,scalePosition);
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
