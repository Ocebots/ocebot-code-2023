package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.hardware.VictorMotorController;

import java.util.Set;

public class DrivetrainSubsystem extends SubsystemBase implements AutoCloseable {
    private final CANSparkMax driveLeftSpark = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushed);
    private final CANSparkMax driveRightSpark = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushed);
    private final VictorSPX driveLeftVictor = new VictorSPX(3);
    private final VictorSPX driveRightVictor = new VictorSPX(4);

    private final DifferentialDrive differentialDrive = new DifferentialDrive(new MotorControllerGroup(driveLeftSpark, new VictorMotorController(driveLeftVictor)), new MotorControllerGroup(driveRightSpark, new VictorMotorController(driveRightVictor)));

    public DrivetrainSubsystem() {
    }

    public void arcadeDrive(double speed, double turn) {
        differentialDrive.arcadeDrive(turn, speed, false);
    }

    //idle mode for drive motors function
    public void driveMotorIdle(boolean shouldBrake) {
        if (shouldBrake) {
            driveLeftSpark.setIdleMode(CANSparkMax.IdleMode.kBrake);
            driveLeftVictor.setNeutralMode(NeutralMode.Brake);
            driveRightSpark.setIdleMode(CANSparkMax.IdleMode.kBrake);
            driveRightVictor.setNeutralMode(NeutralMode.Brake);
        }
        else {
            driveLeftSpark.setIdleMode(CANSparkMax.IdleMode.kCoast);
            driveLeftVictor.setNeutralMode(NeutralMode.Coast);
            driveRightSpark.setIdleMode(CANSparkMax.IdleMode.kCoast);
            driveRightVictor.setNeutralMode(NeutralMode.Coast);
        }
    }

    public Command driveMotorIdleCommand(boolean shouldBrake) {
        DrivetrainSubsystem drivetrainSubsystem = this;
        return new Command() {
            @Override
            public Set<Subsystem> getRequirements() {
                return Set.of(drivetrainSubsystem);
            }

            @Override
            public boolean isFinished() {
                drivetrainSubsystem.driveMotorIdle(shouldBrake);
                return true;
            }
        };
    }

    public Command driveCommand(double speed, double turn) {
        DrivetrainSubsystem drivetrainSubsystem = this;

        return new Command(){
            @Override
            public void initialize() {
                drivetrainSubsystem.arcadeDrive(speed, turn);
            }

            @Override
            public void execute() {
                drivetrainSubsystem.arcadeDrive(speed, turn);
            }

            @Override
            public void end(boolean interrupted) {
                drivetrainSubsystem.arcadeDrive(0, 0);
            }

            @Override
            public Set<Subsystem> getRequirements() {
                return Set.of(drivetrainSubsystem);
            }
        };
    }

    public Command driveForward(double speed) {
        return driveCommand(speed, 0);
    }

    public Command driveBackward(double speed) {
        return driveForward(-speed);
    }

    public Command turnLeft(double turn) {
        return driveCommand(0, turn);
    }

    public Command turnRight(double turn) {
        return turnLeft(-turn);
    }

    @Override
    public void close() throws Exception {
        this.differentialDrive.stopMotor();
        this.differentialDrive.close();
        this.driveLeftSpark.close();
        this.driveRightSpark.close();
    }
}
