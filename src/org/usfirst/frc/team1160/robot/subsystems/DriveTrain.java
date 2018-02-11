package org.usfirst.frc.team1160.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.usfirst.frc.team1160.robot.*;
import org.usfirst.frc.team1160.robot.commands.drive.ManualDrive;

//TODO: Implement Pathfinder encoder followers and heading control (add a gyro/ NavX)
//TODO: Remember that you can't configure ENC COUNTS PER REV

public class DriveTrain extends Subsystem implements RobotMap{

	public static DriveTrain instance;
	public WPI_TalonSRX leftMaster, rightMaster;
	public WPI_VictorSPX leftSlave,rightSlave;
	
	private Timer timer;

	private boolean lowGear;
	private DoubleSolenoid leftBallShifter, rightBallShifter;
		
	public static DriveTrain getInstance() {
		if(instance == null) {
			instance = new DriveTrain();
		}
		return instance;
	}
	
	private DriveTrain() {
		leftMaster = new WPI_TalonSRX(DT_LEFT_1);
		leftSlave = new WPI_VictorSPX(DT_LEFT_2);
		rightMaster = new WPI_TalonSRX(DT_RIGHT_1);
		rightSlave = new WPI_VictorSPX(DT_RIGHT_2);
		timer = new Timer();

		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
		
		leftBallShifter = new DoubleSolenoid(LEFT_SOLENOID_0,LEFT_SOLENOID_1);
		rightBallShifter = new DoubleSolenoid(RIGHT_SOLENOID_0,RIGHT_SOLENOID_1);
		setFollower();
		
	}

	/*
	 * Talon Configuration Methods
	 */
	public void setFollower()
	{
		leftSlave.set(ControlMode.Follower,DT_LEFT_1);
		rightSlave.set(ControlMode.Follower,DT_RIGHT_1);
		
	}

	/*
	 * Drive Methods
	 */
	public void manualDrive() {
		
		leftMaster.set(ControlMode.PercentOutput, -(Robot.oi.getMainstick().getZ() - Robot.oi.getMainstick().getY()));
		rightMaster.set(ControlMode.PercentOutput, -(Robot.oi.getMainstick().getZ() + Robot.oi.getMainstick().getY()));
		
	}

	/*
	 * Encoder Methods
	 */
	public void resetPosition() {
		leftMaster.setSelectedSensorPosition(0,0,0);
		rightMaster.setSelectedSensorPosition(0,0,0);
				
	}
	
	/*
	 * Ball Shifter Methods
	 */
	public void setLowGear() {
		leftBallShifter.set(DoubleSolenoid.Value.kForward);
		rightBallShifter.set(DoubleSolenoid.Value.kForward);
		lowGear = true;
		SmartDashboard.putString("Gear/Speed", "low");
	}
	
	public void setHighGear() {
		leftBallShifter.set(DoubleSolenoid.Value.kReverse);
		rightBallShifter.set(DoubleSolenoid.Value.kReverse);
		lowGear = false;
		SmartDashboard.putString("Gear/Speed", "high");
	}
	
	/*
	 * Data Logging Methods
	 */
	public void printEncoderDistance(){
		SmartDashboard.putNumber("left revolutions", leftMaster.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("right revolutions",rightMaster.getSelectedSensorPosition(0));
	}
	
	public void printEncoderVelocity(){
		SmartDashboard.putNumber("left velocity", leftMaster.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("right velocity", rightMaster.getSelectedSensorVelocity(0));
	}
	
	public void printVoltage(String name,WPI_TalonSRX talon){
		SmartDashboard.putNumber(name + " voltage",talon.getMotorOutputVoltage());
	}
	
	public void printVoltage(String name,WPI_VictorSPX victor){
		SmartDashboard.putNumber(name + " voltage",victor.getMotorOutputVoltage());
	}
	
	public void printVoltageMass(){
		printVoltage("leftMaster",leftMaster);
		printVoltage("leftSlave",leftSlave);
		printVoltage("rightMaster",rightMaster);
		printVoltage("rightSlave",rightSlave);
	}
	
	/*
	 * Timer Methods
	 */
	public void resetTime(){
		timer.reset();
	}
	
	public void startTime(){
		timer.start();
	}
	
	public void stopTime(){
		timer.stop();
	}
	
	public double getDeltaTime(){
		return timer.get();
	}
	
	public void printDeltaTime(){
		SmartDashboard.putNumber("delta time",getDeltaTime());
	}
	
	public void driveDistance(double input) {//this is in revolutions
		leftMaster.set(ControlMode.Position,-input);
		rightMaster.set(ControlMode.Position,input);
	}
	@Override
	protected void initDefaultCommand() {
    	setDefaultCommand(new ManualDrive());
	}

}
