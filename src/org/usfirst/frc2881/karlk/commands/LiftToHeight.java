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

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

/**
 *
 */
public class LiftToHeight extends Command {
    private final double height;

    public LiftToHeight(double height) {
        super("Lift to Height: " + height);
        requires(Robot.liftSubsystem);
        this.height = height;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("Lift to Height has started: " + height);
        //Set the setpoint for the lift
        Robot.liftSubsystem.setSetpoint(this.height);
        //Enable PID loop
        Robot.liftSubsystem.enable();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        //When encoder reads described number of ticks (within tolerance)
        return Robot.liftSubsystem.onTarget();
    }

    @Override
    protected void interrupted() {
        //stop PID loop
        Robot.liftSubsystem.disable();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        //rumbles joysticks when finished
        new RumbleYes(Robot.oi.manipulator).start();
        //stop PID loop
        Robot.liftSubsystem.disable();
        System.out.println("Lift to Scale has finished: " + height);
    }

}
