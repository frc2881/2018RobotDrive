package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;

/**
 * This command should only run for the end of the game.
 * Extends the arm used for climbing on the tower.
 * It uses the climbing subsystem, not the lift subsystem, because it only needs one, and the climbing subsystem
 * already has its solenoid in it
 */
public class ArmInitialDeploy extends Command {
    private boolean deploy;

    public ArmInitialDeploy(boolean deploy) {
        super("ArmInitialDeploy" + (deploy ? "Extended" : "Retracted"));
        requires(Robot.liftSubsystem);
        this.deploy = deploy;
    }

    @Override
    protected void initialize() {
        System.out.print("Arm Initial Deploy has started");
    }

    @Override
    protected boolean isFinished() {
        if (RobotMap.liftSubsystemArmInitialDeploy.get() == deploy) {
            return true;
        }
        return Robot.compressorSubsystem.hasEnoughPressureForArmDeploy();
    }

    // Called just before this Command runs the first time
    @Override
    protected void end() {
        //turning the piston to true as soon as the 'button' is pressed
        Robot.liftSubsystem.armInitialDeploy(deploy);
        System.out.print("Arm Initial Deploy has ended: " + deploy);
    }

}
