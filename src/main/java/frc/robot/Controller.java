package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Controller {
    private final XboxController controller = new XboxController(0);

    private static final double DEAD_ZONE_SIZE = 0.05; // axis range from -1 to 1 with 0 being the default

    public double getForward() {
        return deadZones(DEAD_ZONE_SIZE, controller.getLeftY());
    }

    public double getTurn() {
        return deadZones(DEAD_ZONE_SIZE, controller.getRightX());
    }

    public double getArmUp() {
        return deadZones(DEAD_ZONE_SIZE, controller.getRightTriggerAxis());
    }

    public double getArmDown() {
        return deadZones(DEAD_ZONE_SIZE, controller.getLeftTriggerAxis());
    }

    public boolean getIntakeForward() {
        return controller.getRightBumperPressed();
    }

    public boolean getIntakeBackward() { // TODO: figure out trigger buttons
        return controller.getLeftBumperPressed();
    }

    private double deadZones(double size, double value) {
        if (Math.abs(value) < size) {
            return 0;
        }

        return value;
    }
}
