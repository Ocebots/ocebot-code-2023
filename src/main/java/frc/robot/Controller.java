package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Controller {
    private final XboxController controller = new XboxController(0);

    private static final double DEAD_ZONE_SIZE = 0.05; // axis range from -1 to 1 with 0 being the default

    public double getForward() {
        return -1 * deadZones(DEAD_ZONE_SIZE, controller.getRightX());
    }

    public double getTurn() {
        return -1 * deadZones(DEAD_ZONE_SIZE, controller.getLeftY());
    }

    public boolean getArmUp() {
        return controller.getAButton();
    }

    public boolean getArmDown() {
        return controller.getYButton();
    }

    public boolean getIntakeForward() {
        return controller.getRightBumper();
    }

    public boolean getIntakeBackward() { // TODO: figure out trigger buttons
        return controller.getLeftBumper();
    }

    private double deadZones(double size, double value) {
        if (Math.abs(value) < size) {
            return 0;
        }

        return value;
    }
}
