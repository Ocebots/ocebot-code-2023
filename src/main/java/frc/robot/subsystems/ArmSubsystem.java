package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.abstracts.TimedCommandBuilder;

public class ArmSubsystem extends SubsystemBase implements AutoCloseable {
    private final CANSparkMax arm = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);

    private static final int AMP_LIMIT = 20;
    public static final double SPEED = 0.4;

    public ArmSubsystem() {
        arm.setSmartCurrentLimit(AMP_LIMIT);
    }

    public void setArmSpeed(double percent) {
        arm.set(percent);
    }

    private CommandBase armCommand(boolean forward) {
        return TimedCommandBuilder.of(() -> setArmSpeed(0.5 * (forward ? -1 : 1)), () -> setArmSpeed(0), 3, this);
    }

    public CommandBase raiseArm() {
        return armCommand(true);
    }

    public CommandBase lowerArm() {
        return armCommand(false);
    }

    @Override
    public void close() throws Exception {
        arm.set(0);
        arm.close();
    }
}
