package org.usfirst.frc.team1160.robot.subsystems;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class PID extends Subsystem implements RobotMap{

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private WPI_TalonSRX leftController,rightController;
	private static PID instance;
	
	private PID(WPI_TalonSRX lController, WPI_TalonSRX rController) {
		leftController = lController;
		rightController = rController;
	
		leftController.config_kP(0, 0, 0);
		leftController.config_kI(0, 0, 0);
		leftController.config_kD(0, 0, 0);
		rightController.config_kP(0, 0, 0);
		rightController.config_kI(0, 0, 0);
		rightController.config_kD(0, 0, 0);	
	}
	
	public static PID getInstance(WPI_TalonSRX lController, WPI_TalonSRX rController) {
		if(instance == null) {
			instance = new PID(lController, rController);
		}
		return instance;
	}
	
	public void goDistance(double distance) {
		leftController.set(ControlMode.Position,(distance*RobotMap.CONTROLLER_CONSTANT));
		rightController.set(ControlMode.Position,(distance*RobotMap.CONTROLLER_CONSTANT));
	}
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

