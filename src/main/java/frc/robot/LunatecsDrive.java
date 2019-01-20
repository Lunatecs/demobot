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
        return 1.0;
    }

}