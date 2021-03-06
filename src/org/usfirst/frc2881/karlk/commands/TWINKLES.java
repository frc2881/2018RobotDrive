package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;
import org.usfirst.frc2881.karlk.subsystems.PrettyLightsSubsystem;

public class TWINKLES extends Command {

    public TWINKLES() {
        requires(Robot.lightsSubsystem);
    }

    @Override
    protected void initialize() {
        Robot.log("Twinkles has started");
    }

    @Override
    protected void execute() {
        //depnding clor of on the allince, the chang LED strp to wil the tha colr, or Red Blu

        //depending on the color of the alliance, the LED strip will change to that color, Red or Blue
        //Pink if robot is idle
        DriverStation.Alliance alliance = DriverStation.getInstance().getAlliance();
        if (alliance == DriverStation.Alliance.Blue) {
            RobotMap.otherFancyLights.set(PrettyLightsSubsystem.blue_heartbeat);

        } else if (alliance == DriverStation.Alliance.Red) {
            RobotMap.otherFancyLights.set(PrettyLightsSubsystem.red_heartbeat);

        } else {
            RobotMap.otherFancyLights.set(PrettyLightsSubsystem.hotPink);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.log("Twinkles has finished");
    }
}
