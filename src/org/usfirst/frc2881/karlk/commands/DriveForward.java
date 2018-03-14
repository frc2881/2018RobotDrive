package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;

/**
 * Move the robot forward the specified amount
 */
public class DriveForward extends Command {
    private double distance;
    private boolean omnis;

    public DriveForward(double distance) {
        this(distance, false);
    }

    public DriveForward(double distance, boolean omnis) {
        requires(Robot.driveSubsystem);
        this.distance = distance;
        this.omnis = omnis;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //Make a call to the subsystem to use a PID loop controller in the subsystem
        //to set the heading based on the angle passed into the method.
        System.out.println("Autonomous driving " + distance + " ft: " + Robot.driveSubsystem.getLocation());
        Robot.driveSubsystem.initializeDriveForward(distance, RobotMap.driveSubsystemNavX.pidGet(), omnis);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //Calls to the subsystem to update the angle if controller value has changed
        double speed = Robot.driveSubsystem.getStraightSpeed();
        double rotate = Robot.driveSubsystem.getRotateToAngleRate();
        Robot.driveSubsystem.autonomousArcadeDrive(speed, rotate);
        //Robot.driveSubsystem.arcadeDrive(speed,speed);
        //System.out.println("set speed to " + speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        //asking the PID loop have we reached our position
        return Robot.driveSubsystem.isFinishedDriveForward(omnis);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        //call the drive subsystem to make sure the PID loop is disabled
        Robot.driveSubsystem.endDriveForward(omnis);
        System.out.println("Drive Forward has ended: " + Robot.driveSubsystem.getLocation());
    }

    //This method allows us to make changes to the property this.distance in Shuffleboard
    //It is called automatically when you call SmartDashboard.putData() in OI.java.
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Drive Forward Distance", () -> distance, (distance) -> this.distance = distance);
    }
}
