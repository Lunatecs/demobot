/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

	private WPI_TalonSRX leftTalon = new WPI_TalonSRX(3); 
	private WPI_VictorSPX leftVictor = new WPI_VictorSPX(2);
	
	private WPI_TalonSRX rightTalon = new WPI_TalonSRX(4); 
	private WPI_VictorSPX rightVictor = new WPI_VictorSPX(1); 

	private DifferentialDrive drive = null;
	
	private Joystick joyStick = new Joystick(0);

  private AnalogInput ai = new AnalogInput(3);
  private AnalogPotentiometer potentiometer = new AnalogPotentiometer(ai,1080, 30);

  private TalonSRX intake = new TalonSRX(5);

  private static final double UPLIMIT = 780.0;
  private static final double LOWLIMIT = 680.0;

  private boolean runningIntake = false;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    this.m_period = .005;

    leftVictor.setNeutralMode(NeutralMode.Brake);
    leftTalon.setNeutralMode(NeutralMode.Brake);

    rightVictor.setNeutralMode(NeutralMode.Brake);
    rightTalon.setNeutralMode(NeutralMode.Brake);

    leftVictor.follow(leftTalon);
		
		rightVictor.follow(rightTalon);
		
    drive = new DifferentialDrive(leftTalon, rightTalon);
    
    

    intake.setNeutralMode(NeutralMode.Brake);

    intake.config_kP(0, .8, 10);

    intake.configAllowableClosedloopError(0, 20, 10);

    intake.setSelectedSensorPosition(0, 0, 10);

    intake.setSensorPhase(true);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  //  SmartDashboard.putNumber("Potentiometer",this.potentiometer.get());
  //  SmartDashboard.putData(this.potentiometer);
  //  SmartDashboard.putNumber("Period", this.getPeriod());
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

  }

  int count = 0;

  /**
   * This function is called periodically during operator control.
   */
  double loop = 0.0;

  @Override
  public void teleopPeriodic() {

    double speed      = joyStick.getRawAxis(1);
		double rotation   = joyStick.getRawAxis(4);
    double intakeUp   = joyStick.getRawAxis(2);
    double intakeDown = joyStick.getRawAxis(3);
    double degrees    = potentiometer.get();
    boolean green     = joyStick.getRawButton(1);
    boolean red       = joyStick.getRawButton(2);
    boolean blue      = joyStick.getRawButton(3);
    boolean yellow    = joyStick.getRawButton(4);
    boolean left      = joyStick.getRawButton(5);

    double speed2 = speed;
    
    if(Math.abs(speed) >= 0.2 && Math.abs(rotation) <= .2){
      loop++;
      speed2 = speed * 0.005 * loop;
      if((speed < 0 && speed2<speed) || (speed > 0 && speed2>speed) || left) {
        speed2=speed; 
      }
    } else {
      speed2=speed;
      loop = 0.0;
    } 
    
		if(Math.abs(speed) > .2 || Math.abs(rotation) > .2) {


			drive.arcadeDrive(-speed2, rotation);


    } else {
			drive.arcadeDrive(0.0, 0.0);
    }
    




    SmartDashboard.putNumber("Speed", -speed2);
    SmartDashboard.putNumber("Loop", loop);

    SmartDashboard.putNumber("Potent in teleop", degrees);
    SmartDashboard.putNumber("Encoder", intake.getSelectedSensorPosition(0));

    SmartDashboard.putBoolean("Up", intakeUp > .2 && degrees < UPLIMIT);
    SmartDashboard.putBoolean("Down",intakeDown > .2 && degrees > LOWLIMIT);

    
    //yellow button
    if(yellow) {
      intake.set(ControlMode.Position, 1700);
      runningIntake = true;
    }

    //red button
    if(red) {
      intake.set(ControlMode.Position, 0);
      runningIntake = true;
    }
    
    if(intake.getSelectedSensorPosition(0) > 1700 || intake.getSelectedSensorPosition(0) < 0) {
        runningIntake = false;  
    }

    if(intakeUp > .5) intakeUp = .5;
    if(intakeDown > .5) intakeDown = .5;
    
    if(intakeUp > .2 && degrees < UPLIMIT) {
      intake.set(ControlMode.PercentOutput, intakeUp);
      runningIntake = false;
    } else if(intakeDown > .2 && degrees > LOWLIMIT) {
      intake.set(ControlMode.PercentOutput, -intakeDown);
      runningIntake = false;
    } else {
      if(!runningIntake) {
        intake.set(ControlMode.PercentOutput, 0);
        SmartDashboard.putBoolean("Intake Zero", true);
      } else {
        SmartDashboard.putBoolean("Intake Zero", false);
      }
    }
    
    //green bottom
    if(green) {
      intake.setSelectedSensorPosition(0, 0, 10);
    }


    

    SmartDashboard.putBoolean("Running Intake", runningIntake);
    SmartDashboard.putNumber("Period", this.getPeriod());
    SmartDashboard.putString("Control Mode", intake.getControlMode().toString());

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
