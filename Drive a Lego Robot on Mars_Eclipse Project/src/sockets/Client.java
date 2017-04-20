package sockets;

import org.json.JSONException;
import org.json.JSONObject;

import lejos.utility.Delay;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Created by prog on 13.03.15.
 */
public class Client {
    private Socket client;
    private ReceiveListener listener;
    private InputStream inputStream;
    private OutputStream outputStream;
    private String id = UUID.randomUUID().toString();
    public volatile Position position;
    public volatile Rotation rotation;
    Robot robot=new Robot();
    GUI window = new GUI();

    
    public Client(Socket client, ReceiveListener listener, Robot robot) throws IOException {
    	window.frame.addWindowListener(new WindowAdapter(){
    		public void windowClose (WindowEvent windowEvent){
    			robot.close();
    			System.exit(0);
    		}
    	});
    	this.robot = robot;
    	this.client = client;
        this.listener = listener;
        inputStream = client.getInputStream();
        outputStream = client.getOutputStream();
        position = new Position();
        rotation = new Rotation();
        new ReadThread().start();
        sendStart();
    }

    public String getId() {
        return id;
    }

    private void sendStart(){
        try {
            JSONObject json = new JSONObject();
            json.put("action", "start");
            json.put("id", id);
            JSONObject pos = new JSONObject();
            pos.put("X", position.x);
            pos.put("Y", position.y);
            pos.put("Z", position.z);
            json.put("position", pos);
            JSONObject rot = new JSONObject ();
            rot.put("X", rotation.x);
            rot.put("Y", rotation.y);
            rot.put("Z", rotation.z);
            rot.put("W", rotation.w);
            json.put("rotation", rot);
            sendToClient(json.toString());
        } catch (JSONException e) {
        	System.err.println("Problem in sending data to client");
            e.printStackTrace();
        }
    }

    public void sendToClient(String json){
        System.out.println("sendToClient");
        try {

            byte[] bytes = json.getBytes();
            byte[] bytesSize = intToByteArray(json.length());
            outputStream.write(bytesSize, 0, 4);
            outputStream.write(bytes, 0, bytes.length);
            outputStream.flush();
        } catch (IOException e) {
        	System.err.println("Problem in sending data to client");
            e.printStackTrace();
        }
    }

    private class ReadThread extends Thread{
        @Override
        public void run() {
            super.run();
            byte[] bytes = new byte[1024];
            while (!client.isClosed()){
                try {
                    int data = inputStream.read(bytes);
                    if (data != -1){
                        String string = new String(bytes, 0, data);
                        System.out.println(string);
                        JSONObject jsonObject = new JSONObject(string);
                        JSONObject pos = jsonObject.optJSONObject("position");
                            position.x = pos.optString("X");
                            position.y = pos.optString("Y");
                            position.z = pos.optString("Z");
                            
                        robot.setposition(String.valueOf(position.x),String.valueOf(position.y),String.valueOf(position.z));
                            
                        window.setx(position.x);
                        window.sety(position.y);
                        window.setz(position.z);

                        JSONObject rot = jsonObject.optJSONObject("rotation");
                            rotation.x = rot.optString("X");
                            rotation.y = rot.optString("Y");
                            rotation.z = rot.optString("Z");
                            rotation.w = rot.optString("W");
                        
                        //int spe = window.speed();
                        int spe = Integer.parseInt(window.speed());
                        robot.setTravelSpeed(spe);
                        
                        //int tur = window.turning();
                        int tur = Integer.parseInt(window.turning());
                        String del = window.delay();
                        int time = 0;
                        
                        switch(del)
                        {
                        case "0":
                        	time = 0;
                        	break;
                        case "1.3s":
                        	time = 1300;
                        	break;
                        case "4m":
                        	time = 240000;
                        	break;
                        case "24m":
                        	time = 1440000;
                        	break;
                        }
                        
                        String direction = jsonObject. optString("action");
                        Delay.msDelay(time);

                        switch (direction)
                        {
                        case "goforward":
                        	robot.short_forward();
                        	window.setdire("Forward");
                        	break;
                        case "goback":
                        	robot.short_backward();
                        	window.setdire("Backward");
                        	break;
                        case "goleft":
                        	robot.short_left(tur);
                        	window.setdire("Left");
                        	break;
                        case "goright":
                        	robot.short_right(tur);
                        	window.setdire("Right");
                        	break;
                        }
                        int a = robot.detect();
                        if (a == 999){
                        	window.warning("");
                        }
                        else{
                        	window.warning("Obstacle detected at distance(cm): " + a);
                        }
                    

                        listener.dataReceive(Client.this, string);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                } catch (JSONException e) {
                	System.err.println("Error in reading data");
                    e.printStackTrace();
                }
            }
            System.out.println("Close");
        }
    }

    public static byte[] intToByteArray(int a)
    {
        byte[] ret = new byte[4];
        ret[0] = (byte) (a & 0xFF);
        ret[1] = (byte) ((a >> 8) & 0xFF);
        ret[2] = (byte) ((a >> 16) & 0xFF);
        ret[3] = (byte) ((a >> 24) & 0xFF);
        return ret;
    }
    
    /*class FrameListener extends WindowAdapter
    {
       public void windowClosing(WindowEvent e)
      {
    	robot.close();
        System.exit(0);
      }
    }*/
}


