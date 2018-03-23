package org.usfirst.frc.team1160.robot.commands.auto.paths.switch_;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.FollowTrajectoryLift;
import org.usfirst.frc.team1160.robot.commands.auto.drive.LoadFollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.TurnAngle;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxClamp;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxSpit;
import org.usfirst.frc.team1160.robot.commands.auto.paths.TurnLift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Center_LeftSwitch_Fast extends CommandGroup implements RobotMap{

    public Center_LeftSwitch_Fast() {
        System.out.println("center to left switch, faster");
        addSequential(new AutoBoxClamp());
    	addSequential(new ResetEncoderYaw());
    	addSequential(new LoadFollowTrajectory(Robot.segment_one_left,Robot.segment_one_right));
    	addSequential(new TurnAngle(-25.67));
    	addSequential(new ResetEncoderYaw());
    	addSequential(new FollowTrajectoryLift(Robot.segment_two_left,Robot.segment_two_right,SWITCH_HEIGHT));
    	//addSequential(new LoadFollowTrajectory(Robot.segment_two_left,Robot.segment_two_right));
    	addSequential(new AutoBoxSpit());
    }
}