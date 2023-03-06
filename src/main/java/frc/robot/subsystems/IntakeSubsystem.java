package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.abstracts.TimedCommandBuilder;

public class IntakeSubsystem extends SubsystemBase {
    private static final int AMP_LIMIT = 25;
    private static final double SPEED = 1;

    private static final int HOLD_AMP_LIMIT = 5;
    private static final double HOLD_SPEED = 0.07;

    private final CANSparkMax intake = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless);

    private LastUsed lastUsed = LastUsed.None;

    public enum LastUsed {
        Forward,
        Back,
        None
    }

    public  IntakeSubsystem() {
    }

    private void runIntake(double speed, int amps) {
        intake.set(speed);
        intake.setSmartCurrentLimit(amps);
    }

    /**
     * @param forward makes the speed positive
     */
    private CommandBase runIntakeCommand(boolean forward) {
        return TimedCommandBuilder.of(() -> runIntake(SPEED * (forward ? 1 : -1), AMP_LIMIT), () -> runIntake(0, AMP_LIMIT), 1, this);
    }

    /**
     * Cube in and cone out
     */
    public CommandBase intakeForwardCommand() {
        lastUsed = LastUsed.Forward;
        return runIntakeCommand(true);
    }

    /**
     * Cube out and cone in
     */
    public CommandBase intakeBackwardCommand() {
        lastUsed = LastUsed.Back;
        return runIntakeCommand(false);
    }

    @Override
    public void periodic() {
        if (lastUsed == LastUsed.None) {
            return;
        }

        runIntake(HOLD_SPEED * (lastUsed == LastUsed.Forward ? 1 : -1), HOLD_AMP_LIMIT);
    }
}
