package frc.robot.subsystems;

import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.networktables.NTSendableBuilder;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.Set;

public class GyroSubsystem extends SubsystemBase implements AutoCloseable, NTSendable {
    private final ADIS16470_IMU gyro = new ADIS16470_IMU();
    private double levelValue = 0;

    public GyroSubsystem() {
        gyro.setYawAxis(ADIS16470_IMU.IMUAxis.kY);
    }

    public Command setLevelCommand() {
        GyroSubsystem gyroSubsystem = this;

        return new Command(){

            @Override
            public Set<Subsystem> getRequirements() {
                return Set.of(gyroSubsystem);
            }

            @Override
            public boolean isFinished() {
                levelValue = gyroSubsystem.getAngle();
                return true;
            }
        };
    }

    public boolean isLevel() {
        return Math.abs(this.getAngle() - this.levelValue) < 5 || Math.abs(this.getAngle() - (this.levelValue + 360)) < 5 || Math.abs(gyro.getAngle() - (this.levelValue - 360)) < 5;
    }

    public double getAngle() {
        return gyro.getAngle();
    }

    @Override
    public void close() throws Exception {
        gyro.close();
    }

    @Override
    public void initSendable(NTSendableBuilder builder) {
        builder.setSmartDashboardType("Gyro");
        builder.addDoubleProperty("Pitch Angle", this::getAngle, null);
        builder.addBooleanProperty("Is Level", this::isLevel, null);
    }
}
