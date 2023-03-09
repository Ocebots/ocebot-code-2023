package frc.robot.commands.abstracts;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.ArmSubsystem;

import java.util.Set;

public class TimedCommandBuilder {
    public static Command of(Runnable start, Runnable end, double duration, Subsystem... subsystems) {
        return TimedCommandBuilder.fromCommand(new CommandBase() {
            @Override
            public Set<Subsystem> getRequirements() {
                return Set.of(subsystems);
            }

            @Override
            public void initialize() {
                start.run();
            }

            @Override
            public void end(boolean interrupted) {
                end.run();
            }
        }, duration);
    }

    public static Command fromCommand(Command command, double duration) {
        double startTime = Timer.getFPGATimestamp();

        return command.until(() -> Timer.getFPGATimestamp() - startTime >= duration);
    }
}
