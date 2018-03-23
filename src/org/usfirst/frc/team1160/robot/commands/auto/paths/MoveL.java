package org.usfirst.frc.team1160.robot.commands.auto.paths;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.GenerateFollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.TurnAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MoveL extends CommandGroup {

    public MoveL() {
        addSequential(new ResetEncoderYaw());
    	addSequential(new GenerateFollowTrajectory(Robot.segment_one));
        addSequential(new TurnAngle(-90));
        addSequential(new ResetEncoderYaw());
        addSequential(new GenerateFollowTrajectory(Robot.segment_one));
    }
}
