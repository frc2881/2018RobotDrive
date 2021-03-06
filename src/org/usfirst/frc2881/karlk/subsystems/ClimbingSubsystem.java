package org.usfirst.frc2881.karlk.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.filters.LinearDigitalFilter;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;
import org.usfirst.frc2881.karlk.actuators.SmoothSpeedController;
import org.usfirst.frc2881.karlk.commands.Climb;

/**
 * This subsystem handles the winch
 * the robot needs to climb.  It should be disabled until
 * the end of the match.  It does NOT manage the arm
 * extension also needed at the end.
 */
public class ClimbingSubsystem extends Subsystem implements SendableWithChildren {
    //grab hardware objects from RobotMap and add them into the LiveWindow at the same time
    //by making a call to the SendableWithChildren method add.
    private final SpeedController winch = add(RobotMap.climbingSubsystemWinch);
    private final SmoothSpeedController smoothWinch = add(RobotMap.climbingSubsystemSmoothWinch);
    private final SpeedController mover = add(RobotMap.climbingSubsystemMover);
    private final LinearDigitalFilter currentMovingAverage;

    public ClimbingSubsystem(){
        currentMovingAverage = LinearDigitalFilter.movingAverage(new PIDSource() {
            @Override
            public void setPIDSourceType(PIDSourceType pidSource) {
            }

            @Override
            public PIDSourceType getPIDSourceType() {
                return PIDSourceType.kRate;
            }

            @Override
            public double pidGet() {
                return RobotMap.otherPowerDistributionPanel.getCurrent(RobotMap.climbingSubsystemMoverPdpChannel);
            }
        }, 15);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new Climb());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void moveClimber(boolean forward){
        if (forward) {
            mover.set(1);
        }
        else {
            mover.set(-1);
        }
    }

    public void stopClimber(){
        mover.set(0);
    }

    public void climb(double speed) {
        //This method sets the speed to the number specified in the trigger, as long as speed value is positive
        if (speed >= 0.001) {
            smoothWinch.set(speed);
            Robot.liftSubsystem.setArmNeutralMode(NeutralMode.Coast);
            Robot.liftSubsystem.setArmAssistOff();
        } else {
            // we are assuming that we cannot run the winch backward (because last year the winch was a ratchet)
            smoothWinch.set(0);
            Robot.liftSubsystem.setArmNeutralMode(NeutralMode.Brake);
        }
    }

    public boolean isMoveClimberFinished(){
        boolean pushing = (currentMovingAverage.pidGet() > 7.5);
        return pushing;
    }

    public void resetCurrentMovingAverage(){
        currentMovingAverage.reset();
    }

}

