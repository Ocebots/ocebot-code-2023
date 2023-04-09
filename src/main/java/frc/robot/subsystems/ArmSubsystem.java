package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.abstracts.TimedCommandBuilder;
import java.util.Set;

public class ArmSubsystem extends SubsystemBase implements AutoCloseable {
    private final CANSparkMax arm = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);

    private static final int AMP_LIMIT = 20;
    public static final double SPEED = 0.4;

    public ArmSubsystem() {
        arm.setSmartCurrentLimit(AMP_LIMIT);
        arm.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void setArmSpeed(double percent) {
        arm.set(percent);
    }

    private Command armCommandAuto(boolean forward) {
        return TimedCommandBuilder.of(() -> setArmSpeed(SPEED * (forward ? -1 : 1)), () -> setArmSpeed(0), 2, this);
    }

    public Command raiseArm() {
        return armCommandAuto(true);
    }

    public Command lowerArm() {
        return armCommandAuto(false);
    }

    private Command armCommand(boolean forward) {
        return Commands.runEnd(() -> this.setArmSpeed(SPEED * (forward ? -1 : 1)), () -> this.setArmSpeed(0), this);
    }

    public Command raiseArmTele() {
        return armCommand(true);
    }

    public Command lowerArmTele() {
        return armCommand(false);
    }

    @Override
    public void close() throws Exception {
        arm.set(0);
        arm.close();
    }
}
