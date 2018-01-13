// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc2881.karlk.subsystems;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc2881.karlk.RobotMap;
import org.usfirst.frc2881.karlk.commands.DrivewithJoysticks;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
    private final SpeedController leftRearMotor = RobotMap.driveSubsystemLeftRearMotor;
    private final SpeedController leftFrontMotor = RobotMap.driveSubsystemLeftFrontMotor;
    private final SpeedControllerGroup driveLeft = RobotMap.driveSubsystemDriveLeft;
    private final SpeedController rightRearMotor = RobotMap.driveSubsystemRightRearMotor;
    private final SpeedController rightFrontMotor = RobotMap.driveSubsystemRightFrontMotor;
    private final SpeedControllerGroup driveRight = RobotMap.driveSubsystemDriveRight;
    private final DifferentialDrive driveTrain = RobotMap.driveSubsystemDriveTrain;
    private final Solenoid dropOmniPancake = RobotMap.driveSubsystemDropOmniPancake;
    private final AnalogGyro navX = RobotMap.driveSubsystemNavX;
    private final Encoder leftEncoder = RobotMap.driveSubsystemLeftEncoder;
    private final Encoder rightEncoder = RobotMap.driveSubsystemRightEncoder;
    private final Solenoid gearShift = RobotMap.driveSubsystemGearShift;

    @Override
    public void initDefaultCommand() {

        setDefaultCommand(new DrivewithJoysticks());

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}

