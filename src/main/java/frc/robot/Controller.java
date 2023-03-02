package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Controller {
    private final XboxController controller = new XboxController(0);

    public double getForward() {
        return controller.getLeftY();
    }

    public double getTurn() {
        return controller.getRightX();
    }

    public boolean getArmUp() {
        return controller.getLeftBumperPressed();
    }

    public boolean getArmDown() { // TODO: figure out trigger buttons
        return controller.getAButtonPressed();
    }

    public boolean getIntakeForward() {
        return controller.getRightBumperPressed();
    }

    public boolean getIntakeBackward() { // TODO: figure out trigger buttons
        return controller.getBButtonPressed();
    }
}
