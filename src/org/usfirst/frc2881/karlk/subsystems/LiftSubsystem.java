package org.usfirst.frc2881.karlk.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import org.usfirst.frc2881.karlk.OI;
import org.usfirst.frc2881.karlk.commands.ControlArm;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;

/**
 * This handles the arm and the claw at the end
 * of the arm that is used to deliver cubes to the
 * switch and the scale.
 */
public class LiftSubsystem extends PIDSubsystem implements SendableWithChildren {
    //define constants for scale and switch height
    public static final double UPPER_SCALE_HEIGHT = 6;
    public static final double LOWER_SCALE_HEIGHT = 4;
    public static final double SWITCH_HEIGHT = 3.5;
    public static final double ZERO_ARM_HEIGHT = 0;

    //grab hardware objects from RobotMap and add them into the LiveWindow at the same time
    //by making a call to the SendableWithChildren method add.
    private final WPI_TalonSRX armMotor = add(RobotMap.liftSubsystemArmMotor);
    private final Encoder armEncoder = add(RobotMap.liftSubsystemArmEncoder);
    private final DigitalInput limitSwitch = add(RobotMap.liftSubsystemRevMagneticLimitSwitch);
    private final Solenoid claw = add(RobotMap.liftSubsystemClaw);
    private final Solenoid armInitialDeploy = add(RobotMap.liftSubsystemArmInitialDeploy);

    private NeutralMode armNeutralMode;

    // Initialize your subsystem here
    public LiftSubsystem() {
        super("LiftSubsystem", 1.0, 0.0, 0.0);
        /*This makes a call to the PIDSubsystem constructor
        PIDSubsystem(double p, double i, double d)
        that instantiates a PIDSubsystem that will use the given p, i and d values.*/
        setAbsoluteTolerance(1.0 / 12);  //Set the absolute error which is considered tolerable for use with OnTarget.
        getPIDController().setContinuous(false);
        setName("LiftSubsystem", "PIDSubsystem Controller");
        setInputRange(0, 6);
        setOutputRange(-1.0, 1.0);
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }

    public void reset() {
        getPIDController().reset();
        armEncoder.reset();
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ControlArm());
    }

    @Override
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;

        return armEncoder.pidGet();
    }

    @Override
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
        armMotor.pidWrite(output);
    }

    public void armControl(double speed) {
        // Use 'squaredInputs' to get better control at low speed
        armMotor.set(Math.copySign(speed * speed, speed));
    }

    public void setArmNeutralMode(NeutralMode neutralMode) {
        if (this.armNeutralMode != neutralMode) {
            this.armNeutralMode = neutralMode;
            armMotor.setNeutralMode(neutralMode);
        }
    }

    /*Not sure how to do this with the Rev Magnetic Limit Switch,
    so am commenting out this code until we can figure that out.
    public boolean checkTopLimit(){
        return limitSwitch.get();
    }

    public boolean checkBottomLimit(){
        return limitSwitch.get();
    }
*/
    public double checkEncoder() {
        return armEncoder.getDistance();
    }

    public void resetEncoder() {
        armEncoder.reset();
    }

    public void setClaw(boolean deploy) {
        claw.set(deploy);
    }

    public void armInitialDeploy(boolean deploy) {
        armInitialDeploy.set(deploy);
    }

}
