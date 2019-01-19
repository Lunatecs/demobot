package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class LunatecsDrive {

    private WPI_TalonSRX right;
    private WPI_TalonSRX left;  
    private DifferentialDrive drive;

    public LunatecsDrive(WPI_TalonSRX left, WPI_TalonSRX right) {

    }

    public void arcadeDrive(double speed, double rotation, boolean rampUp) {

    }

    private void goStraight(double speed) {

    }

    private double rampUp(double speed) {
        return 1.0;
    }

}