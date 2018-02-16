// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;

/**
 *
 */
public class RobotPrep extends CommandGroup {

    public RobotPrep() {
        addSequential(new SetGrasper(IntakeSubsystem.GrasperState.OPEN));
        addSequential(new ConditionalCommand(new DriveForward(1.5)) {
            @Override
            protected boolean condition() {
                return DriverStation.getInstance().isAutonomous();
            }
        });
        addSequential(new ArmInitialDeploy(false));
        addSequential(new CalibrateArmEncoder());
    }

    @Override
    protected void initialize() {
        System.out.println("Autonomous command has started");
    }

    @Override
    protected void end() {
        System.out.println("Autonomous command has ended");
    }
}
