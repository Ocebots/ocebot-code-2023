package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.abstracts.TimedCommandBuilder;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class CubeAutoCommand {
    public static SequentialCommandGroup getCommand(ArmSubsystem armSubsystem, IntakeSubsystem intakeSubsystem) {
        return new SequentialCommandGroup(armSubsystem.raiseArm(), TimedCommandBuilder.of(intakeSubsystem.cubeOutCommand(), 1), armSubsystem.raiseArm());
    }
}
