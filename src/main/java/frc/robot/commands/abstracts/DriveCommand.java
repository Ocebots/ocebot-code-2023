package frc.robot.commands.abstracts;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.DrivetrainSubsystem;

import java.util.Set;

public abstract class DriveCommand implements Command {
    protected final DrivetrainSubsystem drivetrainSubsystem;

    public DriveCommand(DrivetrainSubsystem subsystem) {
        this.drivetrainSubsystem = subsystem;
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return Set.of(this.drivetrainSubsystem);
    }
}
