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
	
	/*
	 * turnAngle variables
	 */
	private double deltaTime;
	private double angle_difference_now;
	private double angle_difference;
	private double derivative;
	private double turn;
	

	//private boolean lowGear;
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
		//printEncoderDistance();
		printEncoderVelocity();
	}
	public void resetEncodersYaw() {
		resetGyro();
		resetPosition();
	}
	public void fullThrottle() {
		leftMaster.set(ControlMode.PercentOutput,1);
		rightMaster.set(ControlMode.PercentOutput,1);
	}
	public void setPercentOutput(double percentOutput) {
		leftMaster.set(ControlMode.PercentOutput, -1.02*percentOutput);
		rightMaster.set(ControlMode.PercentOutput, percentOutput);
		}
	
	public void resetAngleDifference() {
		angle_difference = 0;
	}

	public void turnAngle(double targetAngle) { //ghetto PID with the navX sensor
		deltaTime = getTime();
 		angle_difference_now = targetAngle - gyro.getYaw();
 		turn = GYRO_KP_2 * angle_difference;
 		derivative = GYRO_KD * (angle_difference_now - angle_difference)/deltaTime;
 		angle_difference = angle_difference_now;
 		
 		leftMaster.set(ControlMode.PercentOutput, turn+derivative);
 		rightMaster.set(ControlMode.PercentOutput, turn+derivative);
 		printYaw();
 		resetTime();
 		startTime();
 	}
 	
	/*
	 * Encoder Methods
	 */
	public void resetPosition() {
		leftMaster.setSelectedSensorPosition(0,0,100);
		rightMaster.setSelectedSensorPosition(0,0,100);
				
	}
	
	/*
	 * Ball Shifter Methods
	 */
	public void setLowGear() {
		ballShifter.set(DoubleSolenoid.Value.kReverse);
		//lowGear = true;
		SmartDashboard.putString("Gear/Speed", "low");
		//System.out.println("LOW GEAR");
	}
	
	public void setHighGear() {
		ballShifter.set(DoubleSolenoid.Value.kForward);
		//lowGear = false;
		SmartDashboard.putString("Gear/Speed", "high");
		//System.out.println("HIGH GEAR");
	}
	
	/*
	 * Data Logging Methods
	 */
	public void printEncoderDistance(){
		SmartDashboard.putNumber("left revolutions", leftMaster.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("right revolutions",rightMaster.getSelectedSensorPosition(0));
	}
	public void printEncoderDistanceConsoleFeet() {
		//System.out.println("left side: " + (double)leftMaster.getSelectedSensorPosition(0)/1438 + " ft");
		//System.out.println("right side: " + (double)rightMaster.getSelectedSensorPosition(0)/1438 + " ft");
	}
	
	public void printEncoderVelocity(){
		SmartDashboard.putNumber("left velocity", (double)leftMaster.getSelectedSensorVelocity(0)*10/1438);
		SmartDashboard.putNumber("right velocity", (double)rightMaster.getSelectedSensorVelocity(0)*10/1438);
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
	
	public double getTime(){
		return timer.get();
	}
	
	public boolean done(double finishTime) {
		return (timer.get() >= finishTime);
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
		//System.out.println("Current Yaw: " + gyro.getYaw());
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
	
	
	
	/*
	 * Encoder follower shit (trajectory stuff)
	 */
	public void configureEncoderFollowers() {
		left.configureEncoder(leftMaster.getSelectedSensorPosition(0),2259,6.0/12);
		right.configureEncoder(rightMaster.getSelectedSensorPosition(0),2259,6.0/12);
		left.configurePIDVA(LEFT_KP,0,0,LEFT_KF,0);
		right.configurePIDVA(RIGHT_KP, 0, 0, RIGHT_KF, 0);
	}
	
	
	public void generateTrajectory(Waypoint[] points) { //custom generateTrajectory()
		config = new Config(FitMethod.HERMITE_CUBIC, Config.SAMPLES_HIGH, TIME_BETWEEN_POINTS, MAX_VELOCITY, MAX_ACCELERATION, MAX_JERK);
		traj = Pathfinder.generate(points,config);
		modifier = new TankModifier(traj).modify(WHEEL_BASE_DISTANCE);
		left = new EncoderFollower(modifier.getLeftTrajectory());
		right = new EncoderFollower(modifier.getRightTrajectory());
	}
	
	public Trajectory generateTrajectorySetup(Waypoint[] points) {
		config = new Config(FitMethod.HERMITE_CUBIC, Config.SAMPLES_HIGH, TIME_BETWEEN_POINTS, MAX_VELOCITY, MAX_ACCELERATION, MAX_JERK);
		traj = Pathfinder.generate(points,config);
		return traj;
	}
	
	public void generateModifiers(Trajectory traj) { //generate modifiers based off the given trajectory
		modifier = new TankModifier(traj).modify(WHEEL_BASE_DISTANCE);
		left = new EncoderFollower(modifier.getLeftTrajectory());
		right = new EncoderFollower(modifier.getRightTrajectory());
	}
	
	
	public Trajectory getTrajectory() {
		return traj;
	}
	public void resetLeftEncoderFollower() {
		left.reset();
	}
	public void resetRightEncoderFollower() {
		right.reset();
	}
	public void resetEncoderFollowers() {
		left.reset();
		right.reset();
	}
	
	public void followTrajectory() {
		double l = left.calculate(-leftMaster.getSelectedSensorPosition(0));
		double r = right.calculate(rightMaster.getSelectedSensorPosition(0));
		
		
		SmartDashboard.putNumber("left raw - auto",l);
		SmartDashboard.putNumber("right raw - auto",r);
		
		double gyro_heading = gyro.getYaw()*-1;
		double desired_heading = Pathfinder.r2d(left.getHeading());
		
		
		
		double angleError = Pathfinder.boundHalfDegrees(desired_heading-gyro_heading);
		double turn = 0 * angleError;
		SmartDashboard.putNumber("AngleError: ", angleError);
		//leftMaster.setInverted(true);
		leftMaster.set(ControlMode.PercentOutput,-(l+turn));
		rightMaster.set(ControlMode.PercentOutput,r-turn);
		SmartDashboard.putNumber("left master percentoutput",-(l+turn));
		SmartDashboard.putNumber("right master percentoutput", r-turn);
		//System.out.println("we got here");
		//leftMaster.set(ControlMode.PercentOutput,-0.5);
		//rightMaster.set(ControlMode.PercentOutput,0.5);
	}
	
	@Override
	protected void initDefaultCommand() {
    	setDefaultCommand(new ManualDrive());
	}

}
