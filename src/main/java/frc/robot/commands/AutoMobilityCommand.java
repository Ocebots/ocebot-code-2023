package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.abstracts.TimedCommandBuilder;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AutoMobilityCommand {
    public static Command getCommand(DrivetrainSubsystem drivetrainSubsystem) {
        return new SequentialCommandGroup(
                TimedCommandBuilder.of(drivetrainSubsystem.driveBackward(0.3), 4.75),
                drivetrainSubsystem.driveMotorIdleCommand(true)
        );
    }
}
