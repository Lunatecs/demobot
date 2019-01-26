package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CheckEncoderViability{
   
    private int zeroLimit;
    private double deadZone;
    private int currentCount = 0;


    public CheckEncoderViability(int userZeroLimit, double userDeadZone){
        zeroLimit = userZeroLimit;
        deadZone = userDeadZone;
    }

    public int countZero(double encoderRelay){
        if(encoderRelay < deadZone){
            return currentCount++;
        } else {
            return currentCount;
        }
    }
    
    public int countZero(int encoderRelay){
        return countZero((double)encoderRelay);
    }
    
    public boolean encoderHealth(){
        if(currentCount >= zeroLimit){
            
            SmartDashboard.putBoolean("Selected encoder is dead", true);

            return false;
        } else {
            SmartDashboard.putBoolean("Selected encoder is dead", false);
            return true;
            
        }
    }

}