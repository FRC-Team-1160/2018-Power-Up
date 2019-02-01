package org.usfirst.frc.team1160.robot.commands.auto.intake;

import org.usfirst.frc.team1160.robot.commands.intake.IntakeRotate;
import org.usfirst.frc.team1160.robot.commands.intake.ToggleTwice;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FullExtend extends CommandGroup {

    public FullExtend() {
    	addParallel(new IntakeRotate(-0.5),2);
    	addParallel(new ToggleTwice());
    }
}
