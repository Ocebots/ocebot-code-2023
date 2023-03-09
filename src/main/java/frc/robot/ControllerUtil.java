package frc.robot;

import edu.wpi.first.wpilibj.Timer;

import java.util.function.Function;
import java.util.function.Supplier;

public class ControllerUtil {
    private static final double DEADZONE_SIZE = 0.1;
    public static double deadZone(double value) {
        if (Math.abs(value) < DEADZONE_SIZE) {
            return 0;
        }

        return value;
    }

    public static class SlowStart {
        private double startTime = -1;
        private final Supplier<Double> supplier;
        private final Function<Double, Double> slowStart;

        public SlowStart(Supplier<Double> supplier, double time, double startValue, double endValue) {
            this.supplier = supplier;
            this.slowStart = getSlowStart(startValue, endValue, time);
        }

        public SlowStart(Supplier<Double> supplier, double time) {
            this.supplier = supplier;
            this.slowStart = getSlowStart(0, 1, time);
        }

        public double get() {
            if (supplier.get() > 0 && startTime == -1) {
                startTime = Timer.getFPGATimestamp();
            }

            return slowStart.apply(Timer.getFPGATimestamp() - startTime) * supplier.get();
        }

        private static Function<Double, Double> getSlowStart(double startValue, double endValue, double time) {
            return (currentTime) -> Math.max(Math.min((currentTime / time) * (endValue - startValue) + startValue, endValue), startValue);
        }
    }
}
