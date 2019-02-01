package org.usfirst.frc.team1160.robot.commands.auto.paths;

import org.usfirst.frc.team1160.robot.RobotMap;
import org.usfirst.frc.team1160.robot.commands.auto.drive.TurnAngle;
import org.usfirst.frc.team1160.robot.commands.auto.lift.BangBangMove;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TurnLift extends CommandGroup implements RobotMap{

    public TurnLift(int height,double angle) {
    	addParallel(new TurnAngle(angle));
    	addParallel(new BangBangMove(height));
    }
}
