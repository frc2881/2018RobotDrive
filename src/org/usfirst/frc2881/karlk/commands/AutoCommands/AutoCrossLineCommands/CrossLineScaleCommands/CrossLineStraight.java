package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCrossLineCommands.CrossLineScaleCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutonomousRobotFinish;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class CrossLineStraight extends AbstractAutoCommand {

    public CrossLineStraight(StartingLocation start){

        double angle = Math.atan2(49.0, 100.0);

        addSequential(new DriveForward((141.0 + 17.8)/12));
        addSequential(new ConditionalCommand(new TurnToHeading(-angle * 180/ Math.PI, true), new TurnToHeading(angle * 180/Math.PI, true)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.RIGHT;
            }
        });

        addSequential(new AutonomousRobotFinish());

        addSequential(new LiftToHeight(LiftSubsystem.UPPER_SCALE_HEIGHT, false));

        addSequential(new DriveForward(100.0 / Math.cos(angle) / 12));
    }


    // Called just before this Command runs the first time

}
