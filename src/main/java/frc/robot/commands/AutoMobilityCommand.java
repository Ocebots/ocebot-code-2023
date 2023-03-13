package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.abstracts.TimedCommandBuilder;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AutoMobilityCommand {
    public static Command getCommand(DrivetrainSubsystem drivetrainSubsystem) {
        return TimedCommandBuilder.of(drivetrainSubsystem.driveBackward(0.75), 2);
    }
}
