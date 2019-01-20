package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class LunatecsDrive {

    private WPI_TalonSRX right;
    private WPI_TalonSRX left;  
    private DifferentialDrive drive;
    private static final double DEADZONE = 0.2;
    private boolean reset = true;

    public LunatecsDrive(WPI_TalonSRX left, WPI_TalonSRX right) {
        //Here lies the souls of the robotics students
    }

    public void arcadeDrive(double speed, double rotation, boolean rampUp) {
        if(Math.abs(speed) >= DEADZONE || Math.abs(rotation) >= DEADZONE){
            double speed2 = speed;
            if(rampUp && Math.abs(speed) > DEADZONE){
                speed2 = rampUp(speed);
            }

            if(Math.abs(speed) >= DEADZONE && Math.abs(rotation) <= DEADZONE){
                if(reset){
                    calibrateEncodersToZero();
                    reset = false;
                }

                goStraight(speed2);

            } else {
                drive.arcadeDrive(speed2, rotation);
                reset = true;
            }

        } else {
            drive.arcadeDrive(0,0);
            reset = true;
        }
        
    }

    private void calibrateEncodersToZero(){
            left.setSelectedSensorPosition(0,0,10);
            right.setSelectedSensorPosition(0,0,10);
        
    }

    private void goStraight(double speed) {

    }

    private double rampUp(double speed) {
        return 1.0;
    }

}