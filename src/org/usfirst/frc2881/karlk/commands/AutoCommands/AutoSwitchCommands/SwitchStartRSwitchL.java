package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands;

import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class SwitchStartRSwitchL extends AbstractAutoCommand {

    public SwitchStartRSwitchL(){

        addSequential(new DriveForward((165.0 + 1 ) /12));

        addSequential(new TurnToHeading(270, true));

        addSequential(new DriveForward((234.565 - 6 - 27.8) / 12));

        addSequential(new TurnToHeading(180, true));

        addSequential(new DriveForward((62.0 - 27.8 - 6)/12));

        addSequential(new TurnToHeading(90, true));

        addSequential(new LiftToHeight(LiftSubsystem.SWITCH_HEIGHT, false));

        addSequential(new DriveForward((23.625 - 6)/12));

        addSequential(new SetClaw(ClawState.OPEN));
    }


    // Called just before this Command runs the first time

}
