package org.usfirst.frc.team1160.robot.commands.auto.intake;

import org.usfirst.frc.team1160.robot.commands.auto.lift.WaitLift;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeExtend;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeRetract;
import org.usfirst.frc.team1160.robot.commands.intake.IntakeRotate;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBoxClamp extends CommandGroup {

    public AutoBoxClamp() {
    	addSequential(new IntakeExtend());
    	addSequential(new WaitLift(0.3));
		addSequential(new IntakeRotate(0.7),0.3);
		addSequential(new IntakeRetract());
		addSequential(new IntakeRotate(0.3),0.3);
		addSequential(new IntakeExtend());
		addSequential(new IntakeRotate(-0.7),0.3);
    }
}
