import java.awt.Color;
import java.awt.Component;
import java.util.Random;
 
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;



@SuppressWarnings("serial")
public class Main extends JFrame {

	public Main(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//set default sizes
		int x=320, y = 180, z=1;
		
		//prompt user for program dimensions
		String str = JOptionPane.showInputDialog(null, "Length", x);
    	if(str != null) {
    		x = Integer.parseInt(str);
    	}
    	
    	//use widescreen 16:9 ratio
    	int y_widescreen = (int)((float)x*((float)9/(float)16));
    	
    	//prompt user for program dimensions
    	str = JOptionPane.showInputDialog(null, "Height", y_widescreen);
    	if(str != null) {
    		y = Integer.parseInt(str);
    	}
    	
    	//prompt user for program dimensions
    	/* str = JOptionPane.showInputDialog(null, "Depth", z);
    	if(str != null) {
    		z = Integer.parseInt(str);
    	}*/
    	
    	//Init
		setSizes(x, y, z);
	}
	
	public void setSizes(int xSize, int ySize, int zSize) {
		
		//minimum window frame lengths
		int minMenuXLen = 460;
        int minMenuYLen = 150;
        
		//Textbox for info display
        JLabel l = new JLabel();
        
        //Performs all automata functions for the sim; 							
    	automataLib a = new automataLib(this,xSize,ySize); 
    	
    	//Array that stores the world
    	Universe u= new Universe(xSize, 	ySize,  zSize, 	a);											
    	
    	//Check if the window frame is large enough
    	int xxsize = (xSize+2+2) + 6 + 2 +100;
    	int yysize = (ySize+2) + 60;
    	
    	//resize it if needed
    	if(xSize < minMenuXLen) {if(xxsize < minMenuXLen){xxsize = minMenuXLen;}}
		if(ySize < minMenuYLen) {if(yysize < minMenuYLen){yysize = minMenuYLen;}}
		
    	//Set parameters for surface/display panel
        //        		 (	Width,	Height,		Depth,		X,		Y,	Assigned universe)
    	Surf s = new Surf(	xSize, 	ySize, 		u.zlen,		4,		4, 	u);
    	
    	//Mouselistener for user input; also controls many UI functions
    	ml myml = new ml(this,s,l);	

    	//MouseWheelListener for user input;
    	mwl mymwl = new mwl(this,myml);	
    	
    	//MouseMotionListener for user input;									
    	MouseMotionList mMot = new MouseMotionList(this, myml);			
    	
    	//add Menu Gui
    	jMenuMain jmen = new jMenuMain(myml,a);								
    	
    	//Textbox for info display
    	l.setLayout(null); 
    	l.setBounds(xSize+16, 2, 80+40, 8*(10+4));
    	l.setVisible(true);
    	l.setText("Menu");
    	
    	//Layout for Main window/frame
    	this.setLayout(null);
    	this.setSize(xxsize, yysize);
    	this.setVisible(true);
    	this.setResizable(false);
    	this.setLocation(96, 32);
    	
    	//Assemble the components
    	this.add(l);
    	this.add(s);
    	
    	//Configure initial state
    	jmen.setGUI(this);
    	
    	//show menu on start
    	myml.updateListing(); 	
    	
    	//Steps a frame so the universe is visible, unpauses
    	myml.toggleStart(); 
    	
    	//second toggle re-pauses
    	myml.toggleStart(); 
    	
    	//makes sure first surface is visible
    	u.runOnce(1,0);
    	
    	//inital seed
    	u.runOnce(myml.seedRand,myml.seedVal);
    	    	
    	//update screen
    	myml.refresh();
  
    	
	}
	
	
	
	public static void main(String[] args) {
		
        SwingUtilities.invokeLater(new Runnable() { 
            @Override
            public void run() { //breaks program's static dependance
            	new Main(); //Main window/frame
            }
        });
    }
    
	
	
}












