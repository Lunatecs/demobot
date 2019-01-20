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

    boolean startForward = true;

    private void goStraight(double speed) {
        if(startForward){ 
					this.left.setSelectedSensorPosition(0, 0, 10);
					this.right.setSelectedSensorPosition(0, 0, 10);
					startForward = false;
				
				}

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

    private double rampUp(double speed) {
        loopCount++;
        double finalSpeed = speed * RAMPSPEEDUP * loopCount;
        if((speed < 0 && finalSpeed < speed) || (speed > 0 && finalSpeed > speed)) {
            finalSpeed=speed; 
        }
        return finalSpeed;
    }

}