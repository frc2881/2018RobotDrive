// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc2881.karlk.commands.AutoCommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCrossLineCommands.AutoCrossLineCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands.AutoScaleCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.AutoSwitchCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoOptions;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoStrategy;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.CrossLineLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.SwitchPosition;
import org.usfirst.frc2881.karlk.commands.AutonomousRobotPrep;
import org.usfirst.frc2881.karlk.commands.AutonomousWatchDog;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.RobotPrep;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;

/**
 *
 */
public class AutoCommand extends AbstractAutoCommand {

    public AutoCommand(StartingLocation start, AutoOptions auto,
                       SwitchPosition side, AutoStrategy strategy, double waitTime) {

        //NEW
        //LEFT:     (x, y)  (6, 27.8)
        //RIGHT:    (x, y)  (5.4, 26.4)

        //OLD
        //LEFT:     (x, y)  (15.5, 16.8)
        //RIGHT:    (x, y)  (14, 17.8)

        //TODO FIX ADJUSTABLE WAIT TIME BEFORE AUTO
        //TODO FIX SWITCH AND SCALE MEASUREMENTS (MAYBE TEST AUTO WITH TAPES FOR HOW FAR IT"S SUPPOSED TO GO VS HOW FAR IT WENT)
        //TODO FIND LOWEST VOLTAGE NEEDED TO LIFT ARM WITH ALL CLIMBING WEIGHT
        //TODO MAKE AUTO SWITCH OVERRIDE SIDE GO AROUND BACK OF SWITCH
        //TODO INCORPORATE WORKING SCALE CODE (START C, SCALE R) INTO REST OF SCALE CODE

        //TODO CHANGE OVERRIDE AUTO START DRIVE FORWARD FOR SCALE CODE

        //AUTONOMOUS TO-DO LIST

        String gameData = DriverStation.getInstance().getGameSpecificMessage();

        addSequential(new WaitCommand(waitTime));

        addSequential(new AutonomousRobotPrep());

        if (strategy == AutoStrategy.SAFE_AUTO_LEFT || strategy == AutoStrategy.SAFE_AUTO_RIGHT) {
            addSequential(new SafeAuto(start, auto, side, gameData, strategy));

        } else if (strategy == AutoStrategy.OVERRIDE) {
            addSequential(new OverrideAuto(start, auto, side, gameData, strategy));
        }
    }

    @Override
    protected void initialize() {
        super.initialize();
        new AutonomousWatchDog(this).start();//Makes sure that if the robot is in danger of falling over, autonomous gets canceled
    }
}
