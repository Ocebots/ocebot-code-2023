package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.abstracts.TimedCommandBuilder;

public class IntakeSubsystem extends SubsystemBase implements AutoCloseable {
    private static final int AMP_LIMIT = 25;
    private static final double SPEED = 1;

    private static final int HOLD_AMP_LIMIT = 5;
    private static final double HOLD_SPEED = 0.07;

    private final CANSparkMax intake = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless);

    private LastUsed lastUsed = LastUsed.None;

    @Override
    public void close() throws Exception {
        intake.set(0);
        intake.close();
    }

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
    private Command runIntakeCommand(boolean forward) {
        return TimedCommandBuilder.of(() -> runIntake(SPEED * (forward ? 1 : -1), AMP_LIMIT), () -> runIntake(0, AMP_LIMIT), 1, this);
    }

    /**
     * Cube in and cone out
     */
    public Command intakeForwardCommand() {
        lastUsed = LastUsed.Forward;
        return runIntakeCommand(true);
    }

    public Command cubeInCommand() {
        return this.intakeForwardCommand();
    }

    public Command coneOutCommand() {
        return this.intakeForwardCommand();
    }

    /**
     * Cube out and cone in
     */
    public Command intakeBackwardCommand() {
        lastUsed = LastUsed.Back;
        return runIntakeCommand(false);
    }

    public Command cubeOutCommand() {
        return this.intakeBackwardCommand();
    }

    public Command coneInCommand() {
        return this.intakeBackwardCommand();
    }

    @Override
    public void periodic() {
        if (lastUsed == LastUsed.None) {
            return;
        }

        runIntake(HOLD_SPEED * (lastUsed == LastUsed.Forward ? 1 : -1), HOLD_AMP_LIMIT);
    }
}
