package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class LunatecsDrive {

    private WPI_TalonSRX right;
    private WPI_TalonSRX left;  
    private DifferentialDrive drive;
    private static final double DEADZONE = 0.2;
    private boolean reset = true;
    private double loopCount = 0.0;
    private static final double RAMPSPEEDUP = 0.003;

    public LunatecsDrive(WPI_TalonSRX left, WPI_TalonSRX right) {
        this.left = left;
        this.right = right;
        this.drive = new DifferentialDrive(left, right);
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
            resetLoopCount();
            reset = true;
        }
        
    }

    private void calibrateEncodersToZero(){
            left.setSelectedSensorPosition(0,0,10);
            right.setSelectedSensorPosition(0,0,10);
        
    }

    boolean startForward = true;

    private void goStraight(double speed) {

				int encoderLeft = -this.left.getSelectedSensorPosition(0);
				int encoderRight = this.right.getSelectedSensorPosition(0);

				int average = (encoderLeft + encoderRight)/2;
			
				int diffRight = average - encoderRight;
				int diffLeft = average - encoderLeft;
				
				double correcting = .0000275;
			
				double powerLeft = speed + (correcting * (double)diffLeft);
				double powerRight = speed + (correcting * (double)diffRight);
			
				drive.tankDrive(powerLeft, powerRight);

            }

    private void resetLoopCount() {
        loopCount=0;
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