package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.AutoSwitchCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.commands.SetGrasper;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class ScaleSwitchL extends AbstractAutoCommand {

    public ScaleSwitchL(String gameData, StartingLocation start){

        addSequential(new AutoSwitchCommand(start, gameData));

        addSequential(new ConditionalCommand(new DriveForward((136.465- 26.4)/12), new DriveForward((55.965 - 26.4)/12)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });

        addSequential(new TurnToHeading(0, true));

        addSequential(new DriveForward((95.265 - 5.4 - 26.4)/12)); //(324)

        addSequential(new SetGrasper(IntakeSubsystem.GrasperState.OPEN));

        addSequential(new ConditionalCommand(new TurnToHeading(270, true), new TurnToHeading(90, true)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });
        addSequential(new LiftToHeight(LiftSubsystem.UPPER_SCALE_HEIGHT, false));
        addSequential(new DriveForward((38.785 - 5.4)/12)); // goes an inch under the scale
        addSequential(new SetClaw(ClawState.OPEN));

        addSequential(new DriveForward(-38.785/12));

        addSequential(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT, false));
    }


    // Called just before this Command runs the first time

}
