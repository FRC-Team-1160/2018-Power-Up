package org.usfirst.frc.team1160.robot.subsystems;

import org.usfirst.frc.team1160.robot.OI;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.lift.BrakeEngage;
import org.usfirst.frc.team1160.robot.commands.lift.LiftManualControl;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

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
		//brake = new DoubleSolenoid(BRAKE_LEFT_SOLENOID,BRAKE_RIGHT_SOLENOID);
		
		liftLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
		liftRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
		
		configTalons();
		
	}
	
	public static Lift getInstance()
	{
		if (instance == null)
		{
			instance = new Lift();
		}
		return instance;
	}
	
	public void configTalons() {
		/*
		 * TODO: Check which motor needs to be inverted (May not be necessary if the leads are inverted, but then the encoders on one side will out of phase)
		 * 
		 * liftLeft.setSensorPhase(true);
		 * liftLeft.setInverted(true);
		 * liftRight.setSensorPhase(true);
		 * liftRight.setInverted(true);
		*/
		
		//Set relevant frame periods to be at least as fast as periodic rate
		liftLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
		liftLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);
		liftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
		liftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);
		
		//Set the peak(max) and nominal(min) outputs
		liftLeft.configNominalOutputForward(0, 0);
		liftLeft.configNominalOutputReverse(0, 0);
		liftLeft.configPeakOutputForward(1, 0);
		liftLeft.configPeakOutputReverse(-1, 0);

		liftRight.configNominalOutputForward(0, 0);
		liftRight.configNominalOutputReverse(0, 0);
		liftRight.configPeakOutputForward(1, 0);
		liftRight.configPeakOutputReverse(-1, 0);
		
		//Set Closed Loop PID Gains
		liftLeft.selectProfileSlot(0, 0);
		liftLeft.config_kF(0, LIFT_LEFT_KF, 0);
		liftLeft.config_kP(0, LIFT_LEFT_KP, 0);
		liftLeft.config_kI(0, LIFT_LEFT_KI, 0);
		liftLeft.config_kD(0, LIFT_LEFT_KD, 0);
		
		liftRight.selectProfileSlot(0, 0);
		liftRight.config_kF(0, LIFT_RIGHT_KF, 0);
		liftRight.config_kP(0, LIFT_RIGHT_KP, 0);
		liftRight.config_kI(0, LIFT_RIGHT_KI, 0);
		liftRight.config_kD(0, LIFT_RIGHT_KD, 0);
		
		//Set Cruise(max) Velocity and Acceleration
		liftLeft.configMotionCruiseVelocity(LIFT_LEFT_MOTION_CRUISE_VELOCITY, 0);
		liftLeft.configMotionAcceleration(LIFT_LEFT_MOTION_ACCELERATION, 0);
		
		liftRight.configMotionCruiseVelocity(LIFT_RIGHT_MOTION_CRUISE_VELOCITY, 0);
		liftRight.configMotionAcceleration(LIFT_RIGHT_MOTION_ACCELERATION, 0);
		
		liftLeft.setSelectedSensorPosition(0, 0, 0);
		liftRight.setSelectedSensorPosition(0, 0, 0);

	}

	/*
	 * Uses Motion Magic mode to move the lift to a position in ticks
	 * The passed position value will most likely be arbitrary, determined through testing
	 * TODO: Check if targetPosition needs to be inverted for either motor
	 */
	public void set(double targetPosition) {
		liftLeft.set(ControlMode.MotionMagic, targetPosition);
		liftRight.set(ControlMode.MotionMagic, targetPosition);
		
	}
	
	//TODO: Check if either sensor needs to be inverted
	//TODO: Add acceptableError to the RobotMap as a constant, and remove as a parameter
	public boolean getFinished(double targetPosition, double acceptableError) {
		return(Math.abs(targetPosition - (double)liftLeft.getSelectedSensorPosition(0)) < acceptableError && 
				Math.abs(targetPosition - (double)liftRight.getSelectedSensorPosition(0)) < acceptableError);
	}
	
	public void brakeEngage(){
		brake.set(DoubleSolenoid.Value.kForward);
	}
	
	public void brakeRelease(){
		brake.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void joyControl() {
		//TODO: Check Direction 
		liftLeft.set(ControlMode.PercentOutput, OI.getInstance().getMainstick().getY()/2);
		liftRight.set(ControlMode.PercentOutput, OI.getInstance().getMainstick().getY()/2);
		
		SmartDashboard.putNumber("Motor %VBus", OI.getInstance().getMainstick().getY()/2);
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new LiftManualControl());
    }
}
