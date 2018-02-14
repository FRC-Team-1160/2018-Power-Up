package org.usfirst.frc.team1160.robot.subsystems;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.climb.JoyControl;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem implements RobotMap{
	
	private static Climber instance;
	private WPI_VictorSPX climberMotor1, climberMotor2;
	private DoubleSolenoid latch;

	private Climber()
	{
		climberMotor1 = new WPI_VictorSPX(CLIMBER_MOTOR_1);
		climberMotor2 = new WPI_VictorSPX(CLIMBER_MOTOR_2);
		latch = new DoubleSolenoid(LATCH_LEFT_SOLENOID,LATCH_RIGHT_SOLENOID);
	}
	
	public static Climber getInstance()
	{
		if (instance == null)
		{
			instance = new Climber();
		}
		return instance;
	}
	
	public void climb (double speed)
	{
		climberMotor1.set(speed);
		climberMotor2.set(speed);
	}
	
	public void latchExtend(){
		latch.set(DoubleSolenoid.Value.kForward);
	}
	
	public void latchRetract(){
		latch.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void joyControl() {
		climb(Robot.oi.getClimbStick().getY());
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new JoyControl());
    }
}

