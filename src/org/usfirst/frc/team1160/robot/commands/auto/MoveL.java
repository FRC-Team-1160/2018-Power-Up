package org.usfirst.frc.team1160.robot.commands.auto;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MoveL extends CommandGroup {

    public MoveL() {
        addSequential(new ResetEncoderYaw());
    	addSequential(new FollowTrajectory(Robot.segment_one));
        addSequential(new TurnAngle(-90));
        addSequential(new ResetEncoderYaw());
        addSequential(new FollowTrajectory(Robot.segment_one));
    }
}
