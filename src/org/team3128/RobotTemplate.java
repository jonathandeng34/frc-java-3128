package org.team3128;

import org.team3128.listener.ListenerManager;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

/*
 * THIS FILE SHOULD NOT BE MODIFIED
 * --------------------------------
 * It serves as a link to the Global class
 * Events triggered here will be forwarded to the Global class
 *
 * Do not call these functions under any circumstances. Do not modify this
 * class under any circumstances.
 *
 * AUTOGENERATED. DO NOT EDIT UNDER PENALTY OF 42.
 *
 * THIS FILE IS YOUR SOUL.
 */

public class RobotTemplate extends IterativeRobot 
{
	Global global;
	
    public void robotInit()
    {
        Log.info("Global", "Welcome to the FRC Team 3128 No-Longer-Event System version 3!");
        
        global = new Global();
        
        global.initializeRobot();
    }

    public void disabledInit()
    {
    	global.initializeDisabled();
    }

    // ARE YOU CHANGING THINGS?
   
    boolean autonomousHasBeenInit = false;
    public void autonomousInit()
    {
        if(!autonomousHasBeenInit) {
            global._listenerManagerXbox.removeAllListeners();
            global.initializeAuto();
            autonomousHasBeenInit = true;
            teleopHasBeenInit = false;
        }
    }
   
    // TURN BACK NOW.
    // YOUR CHANGES ARE NOT WANTED HERE.
   
    boolean teleopHasBeenInit = false;
    public void teleopInit() {
        if(!teleopHasBeenInit) {
            global._listenerManagerXbox.removeAllListeners();
            global.initializeTeleop();
            teleopHasBeenInit = true;
            autonomousHasBeenInit = false;
        }
    }
   
    public void disabledPeriodic()
    {
        try
		{
			Thread.sleep(100);
		}
        catch (InterruptedException e)
		{
			return;
		}
    }

    // YOU'D BETTER NOT CHANGE ANYTHING
   
    public void autonomousPeriodic()
    {   
		Scheduler.getInstance().run();
    }

    // DO YOU REALLY WANT TO MODIFY YOUR SOUL?
   
    public void teleopPeriodic()
    {        
    	for(ListenerManager manager : global._listenerManagers)
    	{
    		manager.tick();
    	}
        
        try
		{
			Thread.sleep(20);
		}
        catch (InterruptedException e)
		{
			return;
		}
    }
}

