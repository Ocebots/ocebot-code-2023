package frc.robot.commands;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.abstracts.TimedCommandBuilder;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;

public class AutoTurnCommand {
    private static double startingDegree = 0;

    public static Command getCommand(DrivetrainSubsystem drivetrainSubsystem, GyroSubsystem gyroSubsystem) {
        return new SequentialCommandGroup(
                gyroSubsystem.setAngleAxis(ADIS16470_IMU.IMUAxis.kX), // or maybe kZ
                Commands.runOnce(() -> startingDegree = gyroSubsystem.getAngle()),
                drivetrainSubsystem.turnLeft(0.3).until(() -> Math.abs(gyroSubsystem.getAngle() - startingDegree) < 5 )
//                TimedCommandBuilder.of(drivetrainSubsystem.turnLeft(0.4), 2)
        );
    }
}
