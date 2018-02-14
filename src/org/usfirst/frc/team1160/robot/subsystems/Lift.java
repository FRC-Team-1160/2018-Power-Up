package org.usfirst.frc.team1160.robot.subsystems;

import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.lift.BrakeEngage;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem implements RobotMap{
	private static Lift instance;
	private WPI_TalonSRX liftLeft, liftRight;
	private DoubleSolenoid brake;

	private Lift()
	{
		liftLeft = new WPI_TalonSRX(LIFT_MOTOR_LEFT);
		liftRight = new WPI_TalonSRX(LIFT_MOTOR_RIGHT);
		//brake = new DoubleSolenoid(BRAKE_LEFT_SOLENOID,BRAKE_RIGHT_SOLENOID);
		
		liftLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
		liftRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
		
		//TODO: Look Into MotionMagic for smooth lift control (Remember that you can no longer configure ENC_CTS_PER_REV
		
	}
	
	public static Lift getInstance()
	{
		if (instance == null)
		{
			instance = new Lift();
		}
		return instance;
	}
	
	
	public void brakeEngage(){
		brake.set(DoubleSolenoid.Value.kForward);
	}
	
	public void brakeRelease(){
		brake.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void initDefaultCommand() {
		//setDefaultCommand(new BrakeEngage());
    }
}
