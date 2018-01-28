package org.usfirst.frc2881.karlk.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;
import org.usfirst.frc2881.karlk.commands.DriveWithController;

/**
 * This handles all of the robot movement motors, the high
 * gear piston and the drop omni pancake, as well as
 * the NavX and the encoders.
 */
public class DriveSubsystem extends Subsystem implements SendableWithChildren {
    public enum IntakeLocation {
        FRONT, BACK
    }
    //0.33 * 3 = 1 drive at full speed until reaching 0.03
    static final double kP = 0.03;
    static final double kI = 0.00;
    static final double kD = 0.00;
    static final double kF = 0.00;

    PIDController turnPID;
    //double rotateToAngleRate;

    public DriveSubsystem(){

        //requires(Robot.DriveSubsystem);
        turnPID = new PIDController(kP, kI, kD, kF, RobotMap.driveSubsystemNavX, new PIDOutput() {

            @Override
            public void pidWrite(double output) {

            }
        });

        turnPID.setInputRange(-180, 180);
        turnPID.setOutputRange(-1.0, 1.0);
        turnPID.setAbsoluteTolerance(5);
        turnPID.setContinuous(true);
        turnPID.disable();

        //depending on whether we need to turn or not, one or the other would be used
        /*turnPOV.setSetpoint(getDriverPOVAngle());
        rotateToAngleRate = 0;
        turnPOV.enable();       this needs to be put in a new method

        Robot.driveSubsystem.rotate(rotateToAngleRate);
        this goes somewhere else

        @Override
    public void pidWrite(double output) {
        rotateToAngleRate = output;
        }
        This ends up in the pidwrite place up top
        */

    }



    //grab hardware objects from RobotMap and add them into the LiveWindow at the same time
    //by making a call to the SendableWithChildren method add.
    private final SpeedController leftRearMotor = add(RobotMap.driveSubsystemLeftRearMotor);
    private final SpeedController leftFrontMotor = add(RobotMap.driveSubsystemLeftFrontMotor);
    private final SpeedControllerGroup driveLeft = add(RobotMap.driveSubsystemDriveLeft);
    private final SpeedController rightRearMotor = add(RobotMap.driveSubsystemRightRearMotor);
    private final SpeedController rightFrontMotor = add(RobotMap.driveSubsystemRightFrontMotor);
    private final SpeedControllerGroup driveRight = add(RobotMap.driveSubsystemDriveRight);
    private final DifferentialDrive driveTrain = add(RobotMap.driveSubsystemDriveTrain);
    private final Solenoid dropOmniPancake = add(RobotMap.driveSubsystemDropOmniPancake);
    private final Encoder leftEncoder = add(RobotMap.driveSubsystemLeftEncoder);
    private final Encoder rightEncoder = add(RobotMap.driveSubsystemRightEncoder);
    private final Solenoid gearShift = add(RobotMap.driveSubsystemGearShift);

    private IntakeLocation intakeLocation = IntakeLocation.FRONT;

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithController());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void setIntakeLocation(IntakeLocation intakeLocation) {
        this.intakeLocation = intakeLocation;
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        // Use 'squaredInputs' to get better control at low speed

        if (intakeLocation == IntakeLocation.FRONT) {
            driveTrain.tankDrive(leftSpeed, rightSpeed, true);
        } else {
            driveTrain.tankDrive(-rightSpeed, -leftSpeed, true);
        }
    }

    public void rotate(double speed) {
        driveTrain.tankDrive(speed, -speed, false);
    }

    public void initializeTurnToHeading () {
        //depending on whether we need to turn or not, one or the other would be used
        turnPOV.setSetpoint(getDriverPOVAngle());
        rotateToAngleRate = 0;
        turnPOV.enable();
    }


}
    public void executeTurnToHeading () {
        Robot.driveSubsystem.rotate(rotateToAngleRate);
        turnPOV.setSetpoint(getDriverPOVAngle());

    }
    public void changeHeadingTurnToHeading () {
        int angle = Robot.oi.driver.getPOV();
        if (angle > 180) {
            angle = angle - 360;
    }
    public void isFinishedTurnToHeading () {
        return turnPOV.onTarget();
    }
    public void endTurnToHeading () {
        turnPOV.disable();
    }
    public void highGear() {
        gearShift.set(true);
    }

    public void lowGear() {
        gearShift.set(false);
    }

    public void dropOmniPancakePiston(boolean deploy) {
        dropOmniPancake.set(deploy);
    }
}
