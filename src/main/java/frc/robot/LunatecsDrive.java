package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class LunatecsDrive {

    private WPI_TalonSRX right;
    private WPI_TalonSRX left;  
    private DifferentialDrive drive;
    
    private double loopCount = 0.0;
    private static final double RAMPSPEEDUP = 0.005;

    public LunatecsDrive(WPI_TalonSRX left, WPI_TalonSRX right) {
        this.left = left;
        this.right = right;
        this.drive = new DifferentialDrive(left, right);
    }

    public void arcadeDrive(double speed, double rotation, boolean rampUp) {

    }

    private void goStraight(double speed) {

    }

    private double rampUp(double speed) {
        loopCount++;
        double finalSpeed = speed * RAMPSPEEDUP * loopCount;
        if((speed < 0 && finalSpeed < speed) || (speed > 0 && finalSpeed > speed)) {
            finalSpeed=speed; 
        }
        return finalSpeed;
    }

}