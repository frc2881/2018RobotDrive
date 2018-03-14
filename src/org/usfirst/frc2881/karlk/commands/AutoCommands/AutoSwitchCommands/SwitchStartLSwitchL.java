package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.SwitchPosition;
import org.usfirst.frc2881.karlk.commands.DeployOmnis;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.commands.SetGrasper;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class SwitchStartLSwitchL extends AbstractAutoCommand {
    private final SwitchPosition side;

    public SwitchStartLSwitchL(SwitchPosition side){
        this.side = side;

        double distanceToSwitch = 101.0;
        double angle = Math.atan(-22.0 / distanceToSwitch);

        addSequential(new TurnToHeading(angle * 180/Math.PI, true));

        addSequential(new ConditionalCommand(new DriveForward(distanceToSwitch/Math.cos(angle) / 12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.SIDE;
            }
        });

        addSequential(new TurnToHeading(90, true));

        addSequential(new ConditionalCommand(new DriveForward(40.56/ 12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });
        addSequential(new ConditionalCommand(new TurnToHeading(0, true)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });

        addSequential(new SetGrasper(IntakeSubsystem.GrasperState.OPEN));

        addSequential(new LiftToHeight(LiftSubsystem.SWITCH_HEIGHT, false));

        addSequential(new ConditionalCommand(new DriveForward(35.0/12), new DriveForward(18.06/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });
        addSequential(new SetClaw(ClawState.OPEN));
    }


    // Called just before this Command runs the first time

}
