// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

import java.util.Set;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController controller =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  private final ControllerUtil.SlowStart forwardSlow = new ControllerUtil.SlowStart(() -> ControllerUtil.deadZone(controller.getLeftY()), 1);
  private final ControllerUtil.SlowStart turnSlow = new ControllerUtil.SlowStart(() -> ControllerUtil.deadZone(controller.getRightX()), 1, 0.25, 1);

  private final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
  private final ArmSubsystem armSubsystem = new ArmSubsystem();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    drivetrainSubsystem.driveMotorIdle(true);
  }

  /**
   * Use this method to define your trigger->command mappings.
   */
  private void configureBindings() {
    controller.y().whileTrue(armSubsystem.raiseArm());
    controller.a().whileTrue(armSubsystem.lowerArm());

    controller.leftBumper().whileTrue(intakeSubsystem.intakeBackwardCommandTele());
    controller.rightBumper().whileTrue(intakeSubsystem.intakeForwardCommandTele());
    controller.b().whileTrue(new Command() {
      @Override
      public Set<Subsystem> getRequirements() {
        return Set.of(); // Doesn't need a requirement because it doesn't modify drive values
      }

      @Override
      public void initialize() {
        drivetrainSubsystem.driveMotorIdle(true);
      }

      @Override
      public void end(boolean interrupted) {
        drivetrainSubsystem.driveMotorIdle(false);
      }
    });
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }

  public void teleopPeriodic() {
    double turn = turnSlow.get();

    double triggerTurn = controller.getLeftTriggerAxis() - controller.getRightTriggerAxis();

    if (ControllerUtil.deadZone(triggerTurn) != 0) {
      turn = triggerTurn / 4;
    }

    this.drivetrainSubsystem.arcadeDrive(forwardSlow.get() * (controller.getHID().getBButton() ? 0.3 : 1), turn);
  }

  public void teleopInit() {
    drivetrainSubsystem.driveMotorIdle(false);
  }
}
