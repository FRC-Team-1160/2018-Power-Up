package org.usfirst.frc.team1160.robot.commands.auto.paths.scale;

import org.usfirst.frc.team1160.robot.Robot;
import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.ResetEncoderYaw;
import org.usfirst.frc.team1160.robot.commands.auto.drive.LoadFollowTrajectory;
import org.usfirst.frc.team1160.robot.commands.auto.drive.TurnAngle;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxClamp;
import org.usfirst.frc.team1160.robot.commands.auto.intake.AutoBoxSpit;
import org.usfirst.frc.team1160.robot.commands.auto.paths.TurnLift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Left_LeftScale extends CommandGroup implements RobotMap{

    public Left_LeftScale() {
       System.out.println("left to left scale");
       addSequential(new AutoBoxClamp());
       addSequential(new ResetEncoderYaw());
       addSequential(new LoadFollowTrajectory(Robot.segment_one_left,Robot.segment_one_right));
       addSequential(new TurnAngle(90));
       addSequential(new ResetEncoderYaw());
       addSequential(new AutoBoxSpit());
       
    }
}
