package org.usfirst.frc.team1160.robot.subsystems;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.lift.LiftJoyControl;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift extends Subsystem implements RobotMap{
	private static Lift instance;
	private WPI_TalonSRX liftLeft, liftRight;
	private DoubleSolenoid brake;

	private Lift()
	{
		liftLeft = new WPI_TalonSRX(LIFT_MOTOR_LEFT);
		liftRight = new WPI_TalonSRX(LIFT_MOTOR_RIGHT);
		brake = new DoubleSolenoid(PCM,BRAKE_LEFT_SOLENOID,BRAKE_RIGHT_SOLENOID);
		
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
	
	public void setPercentOutput(double percentOutput) { //this probably should not be used since the lift is going to be a pid-exclusive
		liftLeft.set(ControlMode.PercentOutput,-percentOutput);
		liftRight.set(ControlMode.PercentOutput,percentOutput);
	}
	
	public void printLiftSpeed() {
		SmartDashboard.putNumber("left lift speed", liftLeft.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("right lift speed", liftRight.getSelectedSensorVelocity(0));
	}
	
	public void resetLiftEncoders() {
		liftLeft.setSelectedSensorPosition(0,0,100);
		liftRight.setSelectedSensorPosition(0, 0, 100);
	}
	
	public void brakeEngage(){
		brake.set(DoubleSolenoid.Value.kForward);
	}
	
	public void brakeRelease(){
		brake.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void joyControl() {
		
		if (Robot.oi.getClimbStick().getY() > 0)
		{
			setPercentOutput(-1*(Robot.oi.getClimbStick().getY()));
			//System.out.println("REDUCED LIFT SPEED: " + Robot.oi.getClimbStick().getY() * .50);
		}
		else
		{
			setPercentOutput(-1*(Robot.oi.getClimbStick().getY()*.50));
			//System.out.println("NORMAL LIFT SPEED: " + Robot.oi.getClimbStick().getY());
		}
		
		//setPercentOutput(-1*(Robot.oi.getClimbStick().getY()));
		//System.out.println("NORMAL LIFT SPEED: " + Robot.oi.getClimbStick().getY());		
		SmartDashboard.putNumber("Lift Position Left", liftLeft.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Lift Position Right", liftRight.getSelectedSensorPosition(0));
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new LiftJoyControl());
    }
}
