package org.usfirst.frc2881.karlk.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc2881.karlk.RobotMap;
import org.usfirst.frc2881.karlk.actuators.SmoothSpeedController;

/**
 * This handles the grasper wall and the rollers
 * that are used to intake the cube at the ground level.
 */
public class IntakeSubsystem extends Subsystem implements SendableWithChildren {
    public enum GrasperState {OPEN, CLOSED}

    //grab hardware objects from RobotMap and add them into the LiveWindow at the same time
    //by making a call to the SendableWithChildren method add.
    private final PowerDistributionPanel pdp = RobotMap.otherPowerDistributionPanel;
    private final Solenoid grasper = add(RobotMap.intakeSubsystemGrasper);
    private final Ultrasonic intakeDetectorUltrasonic = add(RobotMap.intakeSubsystemIntakeDetectorUltrasonic);
    private final AnalogInput intakeDetectorIR = add(RobotMap.intakeSubsystemIntakeDetectorIR);
    private final SpeedController intakeRollerLeft = add(RobotMap.intakeSubsystemIntakeRollerLeft);
    private final SpeedController intakeRollerRight = add(RobotMap.intakeSubsystemIntakeRollerRight);
    private final SpeedControllerGroup intakeRollerGroup = add(RobotMap.intakeSubsystemIntakeRollerGroup);
    private final int intakeRollerLeftPdpChannel = RobotMap.INTAKE_SUBSYSTEM_INTAKE_ROLLER_LEFT_PDP_CHANNEL;
    private final int intakeRollerRightPdpChannel = RobotMap.INTAKE_SUBSYSTEM_INTAKE_ROLLER_RIGHT_PDP_CHANNEL;
    private final Timer timer = new Timer();
    private final double thresholdUltrasonic = 6;//inches
    private final double thresholdIR = 1.65;//volts
    private final SmoothSpeedController smoothIntakeRoller = add(new SmoothSpeedController(intakeRollerGroup, 0, .25));

    public static double EJECT_SPEED = -.4;
    public static double INTAKE_SPEED = .5;

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    public void resetTimer() {
        timer.reset();
        timer.start();
    }

    public double getTimer() {
        return timer.get();
    }

    //Sets the rollers forwards if roll is true and backwards if roll is false
    public void rollers(double speed) {
            smoothIntakeRoller.set(speed);
        }

    //Stops the rollers (put at the end of the command)
    public void stopRollers() {
        smoothIntakeRoller.set(0);
    }

    public void setGrasper(GrasperState state) {
        grasper.set(state == GrasperState.OPEN);
    }

    public double getMotorCurrent() {
        if (pdp.getCurrent(intakeRollerLeftPdpChannel) > pdp.getCurrent(intakeRollerRightPdpChannel)) {
            return pdp.getCurrent(intakeRollerLeftPdpChannel);
        } else {
            return pdp.getCurrent(intakeRollerRightPdpChannel);
        }
    }

    //has true/false option to test each sensor individually
    public boolean cubeDetected(boolean ultrasonic) {
        if ((ultrasonic == true && intakeDetectorUltrasonic.getRangeInches() <= thresholdUltrasonic) ||
                (ultrasonic == false && intakeDetectorIR.pidGet() <= thresholdIR)) {
            return true;
        }
        return false;
    }
    //This method allows us to make changes to the property this.angle in Shuffleboard
    //It is called automatically when you call SmartDashboard.putData() in OI.java.
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("EJECT_SPEED", () -> this.EJECT_SPEED, (speed) -> this.EJECT_SPEED = speed);
        builder.addDoubleProperty("INTAKE_SPEED", () -> this.INTAKE_SPEED, (speed) -> this.INTAKE_SPEED = speed);
    }
}

