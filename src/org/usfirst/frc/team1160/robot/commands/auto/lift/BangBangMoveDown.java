package org.usfirst.frc.team1160.robot.commands.auto.lift;

import org.usfirst.frc.team1160.robot.commands.lift.BrakeEngage;
import org.usfirst.frc.team1160.robot.commands.lift.BrakeRelease;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BangBangMoveDown extends CommandGroup {

    public BangBangMoveDown(int height) {
    	addSequential(new BrakeRelease());
    	addSequential(new BangBangFrameworkDown(height,-0.125)); //16000 for switch, 39000 for scale
    	addSequential(new WaitLift(.05));
    	addSequential(new BrakeEngage());
    }
}
