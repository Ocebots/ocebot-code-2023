package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class VictorMotorController implements MotorController {
    private final VictorSPX controller;

    public VictorMotorController(VictorSPX controller) {
        this.controller = controller;
    }
    @Override
    public void set(double speed) {
        controller.set(ControlMode.PercentOutput, speed);
    }

    @Override
    public double get() {
        return controller.getMotorOutputPercent();
    }

    @Override
    public void setInverted(boolean isInverted) {
        controller.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return controller.getInverted();
    }

    @Override
    public void disable() {
        stopMotor();
    }

    @Override
    public void stopMotor() {
        set(0);
    }

    public void setNeutralMode(NeutralMode mode) {
        controller.setNeutralMode(mode);
    }
}
