package org.usfirst.frc.team1160.robot.commands.intake;

//import org.usfirst.frc.team1160.robot.commands.auto.drive.WaitDrivetrain;
//import org.usfirst.frc.team1160.robot.commands.auto.lift.WaitLift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PlaceBox extends CommandGroup {

    public PlaceBox() {
    	addSequential(new IntakeRetract());
    	//addSequential(new WaitLift(0.1));
    	addSequential(new IntakeRotate(0.5),0.25);
    	addSequential(new IntakeExtend());
    	addSequential(new IntakeRotate(0.5),0.25);

        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
