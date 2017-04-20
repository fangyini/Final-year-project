package sockets;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.SwingConstants;

public class GUI {

	public JFrame frame;
	private JTextField x_text;
	private JTextField y_text;
	private JTextField z_text;
	private JTextField dire_text;
	private JTextField warn_text;
	public String delayst;
	public String speedst;
	public String turnst;
	public JComboBox delay;
	public JComboBox speed;
	public JComboBox turning;
	

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		frame.setBounds(100, 100, 550, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[-18.00][][454.00,grow]", "[][][][][][][][][]"));
		
		JLabel lblDriveALego = new JLabel("Drive a LEGO EV3 Robot on Mars");
		lblDriveALego.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		frame.getContentPane().add(lblDriveALego, "cell 1 0 2 1,alignx center,aligny center");
		
		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		frame.getContentPane().add(lblSettings, "cell 1 2,alignx center,aligny center");
		
		JLabel lblDelay = new JLabel("Delay");
		frame.getContentPane().add(lblDelay, "flowx,cell 2 2,alignx left,aligny baseline");
		
		JLabel lblNewLabel = new JLabel("Position");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		frame.getContentPane().add(lblNewLabel, "cell 1 4,alignx center,aligny center");
		
		JLabel lblX = new JLabel("x");
		frame.getContentPane().add(lblX, "flowx,cell 2 4");
		
		JLabel lblDirection = new JLabel("Direction");
		lblDirection.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		frame.getContentPane().add(lblDirection, "cell 1 6,alignx center,aligny center");
		
		dire_text = new JTextField();
		dire_text.setEditable(false);
		frame.getContentPane().add(dire_text, "cell 2 6,growx");
		dire_text.setColumns(10);
		
		JLabel direction_text = new JLabel("Warning");
		direction_text.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		frame.getContentPane().add(direction_text, "cell 1 8,alignx center,aligny center");
		
		warn_text = new JTextField();
		warn_text.setEditable(false);
		frame.getContentPane().add(warn_text, "cell 2 8,growx");
		warn_text.setColumns(10);
		
		x_text = new JTextField();
		x_text.setEditable(false);
		frame.getContentPane().add(x_text, "cell 2 4");
		x_text.setColumns(10);
		
		JLabel lblY = new JLabel("y");
		frame.getContentPane().add(lblY, "cell 2 4");
		
		y_text = new JTextField();
		y_text.setEditable(false);
		frame.getContentPane().add(y_text, "cell 2 4");
		y_text.setColumns(10);
		
		JLabel lblZ = new JLabel("z");
		frame.getContentPane().add(lblZ, "cell 2 4");
		
		z_text = new JTextField();
		z_text.setEditable(false);
		frame.getContentPane().add(z_text, "cell 2 4");
		z_text.setColumns(10);
		
		String[] delaystr = { "0","1.3s","4m","24m"};
		delay = new JComboBox(delaystr);
		frame.getContentPane().add(delay, "cell 2 2");
		delay.setSelectedIndex(0);
		//delay.addItemListener((ItemListener) this);
		
		String[] speedset = { "5", "10", "15", "30"};
		JLabel lblSpeed = new JLabel("Speed");
		frame.getContentPane().add(lblSpeed, "cell 2 2");
		
		
		speed = new JComboBox(speedset);
		frame.getContentPane().add(speed, "cell 2 2");
		speed.setSelectedIndex(0);
		
		String[] turningset = {"5","10","15","30"};
		JLabel lblTurnningSpeed = new JLabel("Turning Speed");
		frame.getContentPane().add(lblTurnningSpeed, "cell 2 2");
		
		turning = new JComboBox(turningset);
		frame.getContentPane().add(turning, "cell 2 2,alignx center,aligny center");
		turning.setSelectedIndex(2);
		
		frame.setVisible(true);
	}
	
	public String delay (){
		return (String)delay.getSelectedItem();
	}
	
	public String speed(){
		return (String)speed.getSelectedItem();
	}
	
	public String turning(){
		return (String)turning.getSelectedItem();
	}
	
	public void setx(String x_axle){
		x_text.setText(x_axle);
	}
	
	public void sety(String y_axle){
		y_text.setText(y_axle);
	}
	
	public void setz(String z_axle){
		z_text.setText(z_axle);
	}
	
	public void setdire(String direction){
		dire_text.setText(direction);
	}
	
	public void warning (String warn){
		warn_text.setText(warn);
	}
	


}
