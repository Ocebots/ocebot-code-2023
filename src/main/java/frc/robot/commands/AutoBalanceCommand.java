package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.abstracts.TimedCommandBuilder;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;

public class AutoBalanceCommand {
    public static Command getCommand(DrivetrainSubsystem drivetrainSubsystem, GyroSubsystem gyro) {
        return new SequentialCommandGroup(
                gyro.setLevelCommand(),
                TimedCommandBuilder.of(drivetrainSubsystem.driveBackward(1).until(() -> !gyro.isLevel()), 1.5),
                drivetrainSubsystem.driveMotorIdleCommand(true),
                TimedCommandBuilder.of(drivetrainSubsystem.driveBackward(0.3).until(gyro::isLevel), 4)
        );
    }
}
