package org.usfirst.frc.team1160.robot.commands.auto.paths;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.FollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.TurnAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Center_RightSwitch extends CommandGroup {

    public Center_RightSwitch() {
    	addSequential(new ResetEncoderYaw());
    	addSequential(new FollowTrajectory(Robot.segment_one));
    	
    	//and then now we manipulate the lift
    }
}
