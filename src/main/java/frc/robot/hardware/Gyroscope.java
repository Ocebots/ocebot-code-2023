package frc.robot.hardware;

import edu.wpi.first.wpilibj.I2C;

import java.nio.ByteBuffer;

public class Gyroscope { // TODO: Error handling
  private static final int ADDRESS = 0x1; // TODO: I don't remember the address

  private static final int VERIFY_REGISTER_ADDRESS = 0x0F;
  private static final byte[] VERIFY_REGISTER_VALUE = new byte[]{0x69}; // This is the real value, I'm not lying

  private final I2C communicationPort;

  public Gyroscope() {
    this.communicationPort = new I2C(I2C.Port.kMXP, ADDRESS);
  }

  public boolean verify() {
    return this.communicationPort.verifySensor(VERIFY_REGISTER_ADDRESS, 1, VERIFY_REGISTER_VALUE); // TODO: This size might be wrong
  }

  public double getPitch() {
    throw new Error("Not done yet");
  }

  private int getValue(int address1, int address2) { // TODO: this math is funky
    int raw = (readAddress(address2) << 8) | readAddress(address1);

    raw = raw - 1;

    return ~raw;
  }

  private byte readAddress(int address) {
    byte[] value = new byte[1];

    communicationPort.read(address, 1, value);

    return value[0];
  }
}
