package org.usfirst.frc2881.karlk.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;
import org.usfirst.frc2881.karlk.commands.ControlRollers;

/**
 * This handles the grasper wall and the rollers
 * that are used to intake the cube at the ground level.
 */
public class IntakeSubsystem extends Subsystem implements SendableWithChildren {
    public enum GrasperState {OPEN, CLOSED}

    public enum RollerState {
        EXTENDED, RETRACTED
    }

    //grab hardware objects from RobotMap and add them into the LiveWindow at the same time
    //by making a call to the SendableWithChildren method add.
    private final PowerDistributionPanel pdp = RobotMap.otherPowerDistributionPanel;
    private final Solenoid grasper = add(RobotMap.intakeSubsystemGrasper);
    private final AnalogInput intakeDetectorShortIR = add(RobotMap.intakeSubsystemIntakeDetectorShortIR);
    private final AnalogInput intakeDetectorLongIR = add(RobotMap.intakeSubsystemIntakeDetectorLongIR);
    private final SpeedController intakeRollerLeft = add(RobotMap.intakeSubsystemIntakeRollerLeft);
    private final SpeedController intakeRollerRight = add(RobotMap.intakeSubsystemIntakeRollerRight);
    private final SpeedControllerGroup intakeRollerGroup = add(RobotMap.intakeSubsystemIntakeRollerGroup);
    private final Solenoid rollerExtensionPiston = add(RobotMap.liftSubsystemRollerExtension);
    private final double thresholdDetectedIR = 0.65;//volts (linear regression for inches is too unreliable)
    private final double thresholdLoadedIR = 2.8;//volts (linear regression for inches is too unreliable)

    public static double EJECT_SPEED = -.8;
    public static double INTAKE_SPEED = .5;

    @Override
    public void initDefaultCommand() {
         setDefaultCommand(new ControlRollers());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    //Sets the rollers forwards if roll is true and backwards if roll is false
    public void rollers(double speed) {
        intakeRollerLeft.set(speed);
        intakeRollerRight.set(speed * .9);
    }

    public void turnRollers(double speed){
            intakeRollerLeft.set(speed);
            intakeRollerRight.set(-speed * .9);
    }

    //Stops the rollers (put at the end of the command)
    public void stopRollers() {
        intakeRollerGroup.set(0);
    }

    public void setGrasper(GrasperState state) {
        grasper.set(state == GrasperState.CLOSED);
    }

    public GrasperState getGrasper() {
        if (grasper.get() == true) {
            return GrasperState.CLOSED;
        }
        else {
            return GrasperState.OPEN;
        }
    }

    public boolean getRollers(){
        return (intakeRollerGroup.get() >= 0.05 || intakeRollerLeft.get() >= 0.05 || intakeRollerRight.get() >= 0.05);
    }

    //has true/false option to test each sensor individually
    public boolean cubeDetected() {
        return (intakeDetectorShortIR.pidGet() >= thresholdDetectedIR &&
            intakeDetectorLongIR.pidGet() >= (thresholdDetectedIR+0.1));
            //TODO is there a way to use the exact number for linReg?
    }

    public void loadCube(){
        if (intakeRollerGroup.get()>=.05){
            intakeRollerGroup.set(0);
        }
        if ((intakeDetectorShortIR.pidGet() + 0.15) < intakeDetectorLongIR.pidGet()) {
            intakeRollerLeft.set((((intakeDetectorLongIR.pidGet() - intakeDetectorShortIR.pidGet()) /
                    intakeDetectorShortIR.pidGet())+1)*0.5);
            intakeRollerRight.set(0.5);
        }
        else if ((intakeDetectorShortIR.pidGet() - 0.15) > intakeDetectorLongIR.pidGet()) {
            intakeRollerRight.set((((intakeDetectorShortIR.pidGet() - intakeDetectorLongIR.pidGet()) /
                    intakeDetectorLongIR.pidGet())+1)*0.5);
            intakeRollerLeft.set(0.5);
        }
        else{
            intakeRollerGroup.set(0.5);
        }
    }
    public boolean cubeLoaded(){
        return (intakeDetectorShortIR.pidGet() <= thresholdLoadedIR);
    }
    //TODO use five volt voltage to make the sensors give same values even when battery is low

    //This method allows us to make changes to the property this.angle in Shuffleboard
    //It is called automatically when you call SmartDashboard.putData() in OI.java.
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("EJECT_SPEED", () -> this.EJECT_SPEED, (speed) -> this.EJECT_SPEED = speed);
        builder.addDoubleProperty("INTAKE_SPEED", () -> this.INTAKE_SPEED, (speed) -> this.INTAKE_SPEED = speed);
    }

    public void setRollerExtensionPiston(boolean state) {
        if (Robot.compressorSubsystem.hasEnoughPressureForShifting()) {
            rollerExtensionPiston.set(state);
        } else {
            DriverStation.reportWarning("Not enough pressure to extend rollers", false);
        }
    }

    public boolean getRollerState() {
        return rollerExtensionPiston.get();
    }

    public double getHighestRollerCurrent(){
        return Math.max(
                pdp.getCurrent(RobotMap.intakeSubsystemRollerLeftPdpChannel),
                pdp.getCurrent(RobotMap.intakeSubsystemRollerRightPdpChannel));
    }


}

