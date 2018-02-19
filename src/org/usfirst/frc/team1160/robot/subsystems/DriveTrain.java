package org.usfirst.frc.team1160.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Config;
import jaci.pathfinder.Trajectory.FitMethod;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import jaci.pathfinder.Waypoint;

import org.usfirst.frc.team1160.robot.*;
import org.usfirst.frc.team1160.robot.commands.drive.*;

//TODO: Implement Pathfinder encoder followers
//TODO: Remember that you can't configure ENC COUNTS PER REV
//TODO: Implement basic drivetrain PID control

public class DriveTrain extends Subsystem implements RobotMap,TrajectoryWaypoints{

	public static DriveTrain instance;
	private WPI_TalonSRX leftMaster, rightMaster;
	private WPI_VictorSPX leftSlave,rightSlave;
	private AHRS gyro;
	private Compressor comp;
	private Timer timer;
	
	private EncoderFollower left,right;
	private Trajectory traj;
	private TankModifier modifier;
	private Config config;

	private boolean lowGear;
	private DoubleSolenoid ballShifter;
		
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
		gyro = new AHRS(Port.kMXP);
		comp = new Compressor(PCM);
		comp.start();
		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
		
		
		ballShifter = new DoubleSolenoid(PCM,DT_SOLENOID_0,DT_SOLENOID_1);
		setFollower();
		
	}

	/*
	 * Talon Configuration Methods
	 */
	public void setFollower()
	{
		leftSlave.follow(leftMaster);
		rightSlave.follow(rightMaster);
		
	}

	public void configureVoltage() {
		/*
		 * Prevents motor controllers from attempting to instantaneously ramp to full voltage.
		 */
		leftMaster.configOpenloopRamp(0.5,0);
		leftSlave.configOpenloopRamp(0.5,0);
		rightMaster.configOpenloopRamp(0.5,0);
		rightSlave.configOpenloopRamp(0.5,0);
		
		
		/*
		 * Constrains the maximum voltage output of the motor controllers to limit variability in drive performance due to battery voltage.
		 */
		leftMaster.configVoltageCompSaturation(11,0);
		leftSlave.configVoltageCompSaturation(11,0);
		rightMaster.configVoltageCompSaturation(11,0);
		rightSlave.configVoltageCompSaturation(11,0);
		
		leftMaster.enableVoltageCompensation(true);
		leftSlave.enableVoltageCompensation(true);
		rightMaster.enableVoltageCompensation(true);
		rightSlave.enableVoltageCompensation(true);
		
		leftMaster.configVoltageMeasurementFilter(32,0);
		leftSlave.configVoltageMeasurementFilter(32,0);
		rightMaster.configVoltageMeasurementFilter(32,0);
		rightSlave.configVoltageMeasurementFilter(32,0);
	}
	
	/*
	 * Drive Methods
	 */
	public void manualDrive() {


		leftMaster.set(ControlMode.PercentOutput, -(Robot.oi.getMainstick().getZ() - Robot.oi.getMainstick().getY()));
		//leftSlave.set(ControlMode.PercentOutput, -(Robot.oi.getMainstick().getZ() - Robot.oi.getMainstick().getY()));
		
		rightMaster.set(ControlMode.PercentOutput, -(Robot.oi.getMainstick().getZ() + Robot.oi.getMainstick().getY()));
		//rightSlave.set(ControlMode.PercentOutput, -(Robot.oi.getMainstick().getZ() + Robot.oi.getMainstick().getY()));
		
		printYaw();
	}
	public void fullThrottle() {
		leftMaster.set(ControlMode.PercentOutput,1);
		rightMaster.set(ControlMode.PercentOutput,1);
	}
	public void setPercentOutput(double percentOutput) {
		leftMaster.set(ControlMode.PercentOutput, -1.02*percentOutput);
		rightMaster.set(ControlMode.PercentOutput, percentOutput);
		}

	public void setPercentOutputGyro(double percentOutput, double targetAngle) {
		double angleError = targetAngle - gyro.getYaw();
		double kTurn = -0.05;
		double turnCorrection = angleError*kTurn;
		
		SmartDashboard.putNumber("Angle Error", angleError);
		
		leftMaster.set(ControlMode.PercentOutput, -percentOutput + turnCorrection);
		rightMaster.set(ControlMode.PercentOutput, percentOutput + turnCorrection);
		}
	public void turnAngle(double percentOutput, double targetAngle) { //ghetto PID with the navX sensor
 		double angle_difference = targetAngle - gyro.getYaw();
 		double turn = GYRO_KG * angle_difference;
 		leftMaster.set(ControlMode.PercentOutput,percentOutput + turn);
 		rightMaster.set(ControlMode.PercentOutput,-1*percentOutput - turn);
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
		ballShifter.set(DoubleSolenoid.Value.kForward);
		lowGear = true;
		SmartDashboard.putString("Gear/Speed", "low");
	}
	
	public void setHighGear() {
		ballShifter.set(DoubleSolenoid.Value.kReverse);
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
	
	public void drivePercentOutput(double input) {//this is in revolutions
		leftMaster.set(ControlMode.Position,-input);
		rightMaster.set(ControlMode.Position,input);
	}

	public void resetGyro()
	{
		gyro.reset();
	}
	public void zeroGyro() {
		gyro.zeroYaw();
	}
	
	public void printYaw() {
		SmartDashboard.putNumber("Current Yaw", gyro.getYaw());
	}
	public AHRS getGyro() {
		return gyro;
	}
	
	public void pidOn() {
		
		leftMaster.config_kP(0, DRIVE_KP, 0);
		leftMaster.config_kI(0, DRIVE_KI, 0);
		leftMaster.config_kD(0, DRIVE_KD, 0);
		
		rightMaster.config_kP(0, DRIVE_KP, 0);
		rightMaster.config_kI(0, DRIVE_KI, 0);
		rightMaster.config_kD(0, DRIVE_KD, 0);
		
		//left.configurePIDVA(DRIVE_KP,DRIVE_KI,DRIVE_KD,0,0);
		//right.configurePIDVA(DRIVE_KP,DRIVE_KI,DRIVE_KD,0,0);
	}
	public void pidOff() {
		leftMaster.config_kP(0, 0, 0);
		leftMaster.config_kI(0, 0, 0);
		leftMaster.config_kD(0, 0, 0);
		//leftMaster.config_kF(0, 0, 0);
		
		rightMaster.config_kP(0, 0, 0);
		rightMaster.config_kI(0, 0, 0);
		rightMaster.config_kD(0, 0, 0);
		//rightMaster.config_kF(0, 0, 0);
	}
	public void drivePosition(double distance) {
		leftMaster.set(ControlMode.Position,distance);
		rightMaster.set(ControlMode.Position,distance);
	}
	
	public void generateTrajectory(Waypoint[] points) { //custom generateTrajectory()
		config = new Config(FitMethod.HERMITE_CUBIC, Config.SAMPLES_HIGH, TIME_BETWEEN_POINTS, MAX_VELOCITY, MAX_ACCELERATION, MAX_JERK);
		traj = Pathfinder.generate(points, config);
		modifier = new TankModifier(traj).modify(WHEEL_BASE_DISTANCE);
		left = new EncoderFollower(modifier.getLeftTrajectory());
		right = new EncoderFollower(modifier.getRightTrajectory());
	}
	
	public void generateTrajectory() { //uses point arrays from TrajectoryWaypoints.java
		config = new Config(FitMethod.HERMITE_CUBIC, Config.SAMPLES_HIGH, TIME_BETWEEN_POINTS, MAX_VELOCITY, MAX_ACCELERATION, MAX_JERK);
		traj = Pathfinder.generate(POINTS_1, config);
		modifier = new TankModifier(traj).modify(WHEEL_BASE_DISTANCE);
		left = new EncoderFollower(modifier.getLeftTrajectory());
		right = new EncoderFollower(modifier.getRightTrajectory());
		
		for (int i = 0;i < traj.length();i++) {
			Segment seg = traj.get(i);
			
			 System.out.printf("%f,%f,%f,%f,%f,%f,%f,%f\n", 
		        seg.dt, seg.x, seg.y, seg.position, seg.velocity, 
		        seg.acceleration, seg.jerk, seg.heading);
			 
		}
	}
	
	public void followTrajectory() {
		double l = left.calculate(leftMaster.getSelectedSensorPosition(0));
		double r = right.calculate(rightMaster.getSelectedSensorPosition(0));
		
		double gyro_heading = gyro.getYaw() * -1;
		double desired_heading = Pathfinder.r2d(left.getHeading());
		
		double angleError = Pathfinder.boundHalfDegrees(desired_heading-gyro_heading);
		double turn = GYRO_KG * angleError;
		
		leftMaster.set(-l-turn);
		rightMaster.set(-r+turn);
		
	}
	
	@Override
	protected void initDefaultCommand() {
    	setDefaultCommand(new FullThrottle());
	}

}
