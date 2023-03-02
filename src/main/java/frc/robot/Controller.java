package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Controller {
    private final XboxController controller = new XboxController(0);

    private static final double DEAD_ZONE_SIZE = 0.05;

    public double getForward() {
        return deadZones(DEAD_ZONE_SIZE, controller.getLeftY());
    }

    public double getTurn() {
        return deadZones(DEAD_ZONE_SIZE, controller.getRightX());
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

    private double deadZones(double size, double value) {
        if (Math.abs(value) < size) {
            return 0;
        }

        return value;
    }
}
