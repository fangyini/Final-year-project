package sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.Scanner;

import javax.swing.JFrame;

import lejos.hardware.Audio;
import lejos.hardware.Brick;
import lejos.hardware.RemoteBTDevice;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.remote.ev3.RemoteRequestAudio;
import lejos.remote.ev3.RemoteRequestEV3;
import lejos.remote.ev3.RemoteRequestPilot;
import lejos.remote.ev3.RemoteRequestRegulatedMotor;
import lejos.remote.ev3.RemoteRequestSampleProvider;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;


public class Robot extends BasicRobot {
	RemoteRequestRegulatedMotor motorL;
	RemoteRequestRegulatedMotor motorR;
	RemoteRequestRegulatedMotor motor;
	RemoteRequestPilot pilot;
	RemoteRequestSampleProvider sensor;
	RemoteRequestAudio sound;
	//Audio sound;
	//EV3IRSensor ir;
	//SampleProvider distance;
	
	
	//SampleProvider distance = new EV3IRSensor(SensorPort.S1);
	
	
	
	//Audio sound = brick.getAudio();
	
	private StringBuilder messages;
	private boolean closed=false;
	private boolean straight=false;
	
	int slow_turn=70;
	int fast_turn=80;
	int travel_speed=25;
	int turning_speed = 80;
	

	
	public int detect(){
	
		int a = 999;
 
        try {

        			float[] sample = new float[1];
        			sensor.fetchSample(sample, 0);
        			int dist = (int)sample[0];
                    
                    if (dist < 20) {
                    	

                    	System.out.println("Obstacle detected at distance(cm): " + dist);
                    	sound.systemSound(0);
                    	a = dist;                             
                    }
                    else {
                    	a = 999;
                    }
    	
        }
        catch(java.lang.Exception e) {
        	System.err.println("Sensor Error.");
            System.out.println(e.toString());
        }
		return a;
	}


	public Robot(){
		messages=new StringBuilder();
	}
	public void setClosed(){
		closed=true;
	}
	public void setOpen(){
		closed=false;
	}
	public void connectToRobot(String address)throws Exception{
		messages.setLength(0);
		connect(address);
		setOpen();
		RemoteRequestEV3 brick=getBrick();
		
		try{
			messages.append("Creating Pilot" + '\n');
			motorR=(RemoteRequestRegulatedMotor)brick.createRegulatedMotor("C",'L');
			motorL=(RemoteRequestRegulatedMotor)brick.createRegulatedMotor("B",'L');
			motorR.setSpeed(200);
			motorL.setSpeed(200);
			pilot=(RemoteRequestPilot)brick.createPilot(4, 9, "B", "C"+'\n');
			messages.append("Created Pilot"+'\n');
			sensor = (RemoteRequestSampleProvider)brick.createSampleProvider("S1","lejos.hardware.sensor.EV3IRSensor","Distance");	
			sound = (RemoteRequestAudio) brick.getAudio();
		}catch(Exception e){
			System.out.println("Catch exception.");
			System.err.println("Error in connecting to the EV3");
			brick.disConnect();
			throw e;
		}
	}
	public void close(){
		messages.setLength(0);
		if(!closed){
			setClosed();
			super.disconnected=true;
			
			if(motor!=null){
				try{
					motor.stop();
					motor.close();
					sensor.close();
					System.out.println("Closing motor...\n");
					//Delay.msDelay(1000);
				}catch(Exception e){
					System.err.println("Problem closing Motor");
				}
			}
			
			try{
				pilot.stop();
				messages.append("Closing Pilot"+'\n');
				pilot.close();
				System.out.println("Closing pilot...\n");
				//Delay.msDelay(1000);
			}catch(Exception e){
				System.err.println("Problem closing Pilot");
			}
			
			if(motorR!=null & motorL!=null){
				try{
					motorR.stop();
					messages.append("Closing Right Motor" + '\n');
					motorR.close();
					//Delay.msDelay(1000);
				}catch(Exception e){
					System.err.println("Problem closing motors");
				}
				
				try{
					motorL.stop();
					messages.append("Closing Left Motor" + '\n');
					motorL.close();
					//Delay.msDelay(1000);
				}catch(Exception e){
					System.err.println("Problem closing motors");
				}
			}
			super.close();
		}
		setClosed();
	}
	
	public RegulatedMotor getMotor(){
		return motor;
	}
	
	public void setTravelSpeed (int travelSpeed){
		travel_speed=travelSpeed;
	}
	
	public void short_forward(){
		pilot.setLinearSpeed(travel_speed);
		if(!straight){
			straight=true;
		}
		pilot.travel(10);
	}
	
	public void short_backward(){
		pilot.setLinearSpeed(travel_speed);
		if(!straight){
			straight=true;
		}
		
		pilot.travel(-10);
	}
	
	public void short_left(int turning_speed){
		pilot.setAngularSpeed(turning_speed);
		pilot.rotate(15);
		straight=false;
	}
	
	
	public void short_right(int turning_speed){
		pilot.setAngularSpeed(turning_speed);
		pilot.rotate(-15);
		straight=false;
	}
	
	
	public void stop(){
		pilot.stop();
	}
	
	public void right(){
		pilot.setAngularSpeed(100);
		pilot.rotate(-90);
		straight=false;
	}
	
	
	
	public String getMessages(){
		return messages.toString();
	}
	
	public boolean isMoving(){
		return pilot.isMoving();
	}
	
	public boolean isMovingStraight(){
		return pilot.isMoving()&&straight;
	}
	
	public boolean isTurning(){
		return pilot.isMoving()&&!straight;
	}
	
	public void setposition(String a, String b, String c){
		TextLCD lcd = getBrick().getTextLCD();
		lcd.clear();
		lcd.drawString(a, 2, 3);
		lcd.drawString(b, 2, 4);
		lcd.drawString(c, 2, 5);
	}
	

}
