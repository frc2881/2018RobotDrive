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

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;
/**
 *
 */
public class DriveInHighGear extends Command {
    public DriveInHighGear() {
        requires(Robot.driveSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //Turn the piston to true to set it to high gear
        Robot.driveSubsystem.highGear();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute(){
        double left = Robot.oi.driver.getY(GenericHID.Hand.kLeft);
        double right = Robot.oi.driver.getY(GenericHID.Hand.kRight);
        Robot.driveSubsystem.tankDrive(left, right);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        //Turn the piston to false to set it back to low gear
        Robot.driveSubsystem.lowGear();
    }
}
