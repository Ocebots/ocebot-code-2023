package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.abstracts.TimedCommandBuilder;

public class ArmSubsystem extends SubsystemBase {
    private final CANSparkMax arm = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);

    private static final int AMP_LIMIT = 20;
    public static final double SPEED = 0.4;

    public ArmSubsystem() {
        arm.setSmartCurrentLimit(AMP_LIMIT);
    }

    public void setArmSpeed(double percent) {
        arm.set(percent);
    }

    private Command armCommand(boolean forward) {
        return TimedCommandBuilder.of(() -> setArmSpeed(0.5 * (forward ? -1 : 1)), () -> setArmSpeed(0), 3, this);
    }

    public Command raiseArm() {
        return armCommand(true);
    }

    public Command lowerArm() {
        return armCommand(false);
    }
}
