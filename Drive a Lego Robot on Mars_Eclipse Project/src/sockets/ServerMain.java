package sockets;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.lcd.TextLCD;

//import fyp.Client;
//import fyp.Clients;

/**
 * Created by prog on 12.03.15.
 */
public class ServerMain {
    public static void main(String[] args) throws Exception{
    	Robot ev3 = new Robot();
		ev3.connectToRobot("10.0.1.1");
		
		
		TextLCD lcd = ev3.getBrick().getTextLCD();
		lcd.clear();
		lcd.drawString("Hello", 4, 3);
		
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		
        try {
            Clients clients = new Clients();
            ServerSocket serverSocket = new ServerSocket(16000);
            
            while (true) {
                System.out.println("Wait client");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                Client client = new Client(socket, clients, ev3);
                clients.addClient(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    	/*Desktop.getDesktop().open(new File("/Users/Adia/Desktop/Marsrover_socket5.unity3d"));
        
    	Desktop desk=Desktop.getDesktop();  
    	try  
    	{  
    	    File file=new File("Antennas 312a 2016.pptx");//创建一个java文件系统  
    	    desk.open(file); //调用open（File f）方法打开文件   
    	}catch(Exception e)  
    	{  
    	    System.out.println(e.toString());  
    	}*/
        
        
    }
}
