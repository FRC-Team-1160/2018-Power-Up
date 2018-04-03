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
	private Timer timer,timerCheck;
		//timerCheck is supposed to run only upon the turnAngle method
		//hitting ninety degrees and activating the checking clause
	private DoubleSolenoid ballShifter;
	
	
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
	private double proportion;
	private double integral; 
		
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
		timerCheck = new Timer();
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

		leftMaster.set(ControlMode.PercentOutput, Math.pow(-(Robot.oi.getMainstick().getZ() - Robot.oi.getMainstick().getY()), 1));
		//leftSlave.set(ControlMode.PercentOutput, -(Robot.oi.getMainstick().getZ() - Robot.oi.getMainstick().getY()));
		
		rightMaster.set(ControlMode.PercentOutput, Math.pow(-(Robot.oi.getMainstick().getZ() + Robot.oi.getMainstick().getY()),1));
		//rightSlave.set(ControlMode.PercentOutput, -(Robot.oi.getMainstick().getZ() + Robot.oi.getMainstick().getY()));
		
		printYaw();
		printEncoderDistance();
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
	public void resetTurnAngleIntegral() {
		integral = 0;
	}

	public void turnAngle(double targetAngle) { //ghetto PID with the navX sensor
 		angle_difference_now = targetAngle - gyro.getYaw();
 		proportion = GYRO_KP_2 * angle_difference;
 		deltaTime = getTime();
 		derivative = GYRO_KD * (angle_difference_now - angle_difference)/deltaTime;
 		if (Math.abs(angle_difference_now) < 20) {
 			integral += GYRO_KI*deltaTime*(angle_difference_now);
 		}
 		angle_difference = angle_difference_now;
 		
 		SmartDashboard.putNumber("turnAngle PercentOutput input", proportion+derivative+integral);
 		leftMaster.set(ControlMode.PercentOutput, GYRO_KF+proportion+derivative+integral);
 		rightMaster.set(ControlMode.PercentOutput, GYRO_KF+proportion+derivative+integral);
 		/*
 		if (proportion+derivative+integral <= GYRO_CAP) {
	 		leftMaster.set(ControlMode.PercentOutput, proportion+derivative+integral);
	 		rightMaster.set(ControlMode.PercentOutput, proportion+derivative+integral);
 		}
 		else {
 			leftMaster.set(GYRO_CAP);
 			rightMaster.set(GYRO_CAP);
 		*/
 		
 		printYaw();
 		resetTime();
 		startTime();
 	}
	
	public void turnAngleCheck(double targetAngle) {
		resetTimeCheck();
		startTimeCheck();
		while (getTimeCheck() < 1) {
			turnAngle(targetAngle);
		}
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
		SmartDashboard.putString("Gear/Speed", "low");
	}
	
	public void setHighGear() {
		ballShifter.set(DoubleSolenoid.Value.kForward);
		SmartDashboard.putString("Gear/Speed", "high");
	}
	
	/*
	 * Data Logging Methods
	 */
	public void printEncoderDistance(){
		SmartDashboard.putNumber("left distance (feet)", Math.PI*WHEEL_DIAMETER*leftMaster.getSelectedSensorPosition(0)/2259.0);
		SmartDashboard.putNumber("right distance (feet)",Math.PI*WHEEL_DIAMETER*rightMaster.getSelectedSensorPosition(0)/2259.0);
	}
	public void printEncoderDistanceConsoleFeet() {
		//System.out.println("left side: " + (double)leftMaster.getSelectedSensorPosition(0)/1438 + " ft");
		//System.out.println("right side: " + (double)rightMaster.getSelectedSensorPosition(0)/1438 + " ft");
	}
	
	public void printEncoderVelocity(){
		SmartDashboard.putNumber("left velocity", (double)Math.PI*WHEEL_DIAMETER*leftMaster.getSelectedSensorVelocity(0)*10/2259);
		SmartDashboard.putNumber("right velocity", (double)Math.PI*WHEEL_DIAMETER*rightMaster.getSelectedSensorVelocity(0)*10/2259);
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
	public void resetTimeCheck() {
		timerCheck.reset();
	}
	public void startTimeCheck() {
		timerCheck.start();
	}
	public void stopTimeCheck() {
		timerCheck.stop();
	}
	public double getTimeCheck() {
		return timerCheck.get();
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
		left.configureEncoder(leftMaster.getSelectedSensorPosition(0),2259,WHEEL_DIAMETER);
		right.configureEncoder(rightMaster.getSelectedSensorPosition(0),2259,WHEEL_DIAMETER);
		left.configurePIDVA(LEFT_KP,LEFT_KI,LEFT_KD,LEFT_KF,LEFT_KA);
		right.configurePIDVA(RIGHT_KP,RIGHT_KI,RIGHT_KD, RIGHT_KF,RIGHT_KA);
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
	
	public void loadLeftEncoderFollower(Trajectory traj) { //from a csv!
		left = new EncoderFollower(traj);
	}
	
	public void loadRightEncoderFollower(Trajectory traj) { //also from a csv!
		right = new EncoderFollower(traj);
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
	
	public EncoderFollower getLeftEncoderFollower() {
		return left;
	}
	public EncoderFollower getRightEncoderFollower() {
		return right;
	}
	
	public double totalTimestep = 0;
	public int numberOfIterations = -1;
	
	public void followTrajectory() {
		System.out.println("TIME STEP: " + timer.get());
		totalTimestep += timer.get();
		numberOfIterations += 1;
		timer.reset();
		timer.start();
		
		double l = left.calculate(-leftMaster.getSelectedSensorPosition(0));
		double r = right.calculate(rightMaster.getSelectedSensorPosition(0));
		/*
		SmartDashboard.putNumber("left raw - auto",l);
		SmartDashboard.putNumber("right raw - auto",r);
		*/
		
		double gyro_heading = gyro.getYaw()*-1;
		double desired_heading = Pathfinder.r2d(left.getHeading());
		
		double angleError = Pathfinder.boundHalfDegrees(desired_heading-gyro_heading);
		double turn = GYRO_KP * angleError;
		SmartDashboard.putNumber("AngleError: ", angleError);
		//leftMaster.setInverted(true);
		leftMaster.set(ControlMode.PercentOutput,-(l+turn));
		rightMaster.set(ControlMode.PercentOutput,r-turn);
		/*
		SmartDashboard.putNumber("left master percentoutput",-(l+turn));
		SmartDashboard.putNumber("right master percentoutput", r-turn);
		*/
		//System.out.println("we got here");
		//leftMaster.set(ControlMode.PercentOutput,-0.5);
		//rightMaster.set(ControlMode.PercentOutput,0.5);
	}
	
	@Override
	protected void initDefaultCommand() {
    	setDefaultCommand(new ManualDrive());
	}

}
