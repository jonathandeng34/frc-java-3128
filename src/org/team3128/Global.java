package org.team3128;

import org.team3128.autonomous.AutoConfig;
import org.team3128.drive.ArcadeDrive;
import org.team3128.hardware.encoder.angular.AnalogPotentiometerEncoder;
import org.team3128.hardware.encoder.velocity.QuadratureEncoderLink;
import org.team3128.hardware.encoder.velocity.TachLink;
import org.team3128.hardware.motor.MotorLink;
import org.team3128.hardware.motor.speedcontrol.PIDSpeedTarget;
import org.team3128.listener.IListenerCallback;
import org.team3128.listener.Listenable;
import org.team3128.listener.ListenerManager;
import org.team3128.util.VelocityPID;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;

/**
 * The Global class is where all of the hardware objects that represent the robot are stored.
 * It also sets up the control bindings.
 * 
 * Its functions are called only by RobotTemplate.
 * @author Jamie
 *
 */
public class Global
{
	public ListenerManager _listenerManager;
	
	public MotorLink _pidTestMotor;
	
	public MotorLink leftMotors;
	public MotorLink rightMotors;
	public QuadratureEncoderLink _testEncoder;
	
	public AnalogPotentiometerEncoder testAngleEncoder;
	
	public Servo testServo;
	
	public int testServoCount = 0;
	
	
	//public HolonomicDrive _drive;
	
	public ArcadeDrive _drive;
	
	public Global()
	{	
		_listenerManager = new ListenerManager(new Joystick(Options.instance()._controllerPort));
		
		leftMotors = new MotorLink();
		leftMotors.addControlledMotor(new Talon(1));
		leftMotors.addControlledMotor(new Talon(4));
		rightMotors = new MotorLink();
		rightMotors.addControlledMotor(new Talon(2));
		rightMotors.addControlledMotor(new Talon(3));
		
		_testEncoder = new QuadratureEncoderLink(4,	3, 128);
		_pidTestMotor = new MotorLink(new PIDSpeedTarget(0, _testEncoder , new VelocityPID(15, 0, 0)));
		_pidTestMotor.addControlledMotor(new Talon(0));
		
		testAngleEncoder = new AnalogPotentiometerEncoder(0);
		testServo = new Servo(5);
		
		_drive = new ArcadeDrive(leftMotors, rightMotors, _listenerManager);

	}

	void initializeRobot()
	{
		
		TachLink link = new TachLink(0, 54);
		
		Log.debug("Global", "Tachometer: " + link.getRaw());
	}

	void initializeDisabled()
	{

	}

	void initializeAuto()
	{
		new Thread(() -> AutoConfig.initialize(this), "AutoConfig").start();
	}
	
	void initializeTeleop()
	{
		IListenerCallback updateDrive = () -> _drive.steer();
		
		_listenerManager.addListener(Listenable.JOY1X, updateDrive);
		_listenerManager.addListener(Listenable.JOY1Y, updateDrive);
		
		_listenerManager.addListener(Listenable.ALWAYS, () ->
		{
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			testServo.setAngle(++testServoCount);
			Log.debug("Global", "Servo set to " + testServoCount);
			if(testServoCount == 165)
			{
				testServoCount = 15;
			}
		});
		
//		_listenerManager.addListener(Listenable.JOY2Y, () -> 
//		{
//			double speed = _listenerManager.getRawDouble(Listenable.JOY2Y);
//			speed = speed > .1 ? speed * 25.0 : 0;
//			_pidTestMotor.setControlTarget(speed);
//		});
	}
}
