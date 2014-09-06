import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Surf extends JPanel {

	
	//reference coordinates
	int xx; 
	int yy;
	int zz;
	
	//Display colour contianers
    int col1 = 0;
    int col2 = 0;
    int col3 = 0; 						//RGB colour ints
    float[] floats = new float[3]; 		//holds rgb -> hsb conversion vals  
    double pGradient; 					//used for calculation of the gradient
    double pNegGradient; 					//used for calculation of the gradient
    
    //config
    public boolean paused = true; 		//boolean that vetos display progression
    public boolean upaused = true; 		//boolean that vetos universe progression
    int zdraw = 0; 						//which z layer do I show, if z is a fixed reference?
    int colourScheme = -1;
    
    Universe u; 						//main logic container
    
    /***********************************************************************************************/
    
    
    //constructor
    public Surf(int myX, int myY, int myZ, int xloc, int yloc, Universe uni) { 

    	//set display reference
    	u = uni;
    	
    	//Define display size
    	xx=myX;
    	yy=myY;
    	zz=myZ;
    	
    	//set up the display
    	setSize(myX, myY);
    	setLocation(xloc, yloc);
    }
    

    //main display output
    private void doDrawing(Graphics g) { 
    	//Begin Draw configuration
    	
    	if(!upaused) {u.updateUniverse();} 	//perform a full cycle of logical iteration
    	
	    u.maxValAudit(zdraw); 				//only audit maxval for this z-value
	    Graphics2D g2d = (Graphics2D) g; 	//some sort of magic.
	    /**/int k=zdraw;/**/ 				//for fixed z-value, processes in layers
	       
	    for (int i = 0; i < xx; i++) {
	        for (int j = 0; j < yy; j++) {
	        	
	        	if(colourScheme == 0) {
		            //calculate positive-value gradient: white < grey < black
		        	
			        pGradient = 	/*255-*/	( ((double) u.universe[i][j][k]/u.maxVal) *255);
			        
			        pNegGradient = 	255-		( ((double) u.universe[i][j][k]/u.minVal) *255);
			        
			        int pp = (int) pGradient;
			        int pn = (int) pNegGradient;
			                    
			        //give individual colours to specific values
			        if(u.universe[i][j][k]==0) {
			        	col3=185; col2=195; col1=215;
			        } else if(u.universe[i][j][k]<0) {
			        	col3=0; col2=255-pn; col1=255;
			        } else {
			        	 col3=pp; col2=pp; col1 = pp;
			        }
			        
			        if(u.universe[i][j][k]==1 && u.maxVal != 1) {col3=180; col2=180; col1=0;}
			        if(u.universe[i][j][k]==1 && u.maxVal == 1) {col3=0; col2=0; col1=0;}
	        	}
	        	
	        	if(colourScheme == 1) {
			        if(u.universe[i][j][k]==0) {
			        	col3=255; col2=255; col1=255;
			        } else {
			        	col3=0; col2=0; col1=0;
			        }
	        	}
	        	
	        	if(colourScheme == 2) {
			        if(u.universe[i][j][k]==0) {
			        	col3=255; col2=255; col1=255;
			        } else if(u.universe[i][j][k]>=1){
			        	col3=0; col2=0; col1=0;
			        } else {
			        	col3=0; col2=0; col1=255;
			        }
	        	}
	        	
	        	if(colourScheme == 3) {
			        if(u.universe[i][j][k]==0) {
			        	col3=255; col2=255; col1=255;
			        } else if(u.universe[i][j][k]>=1){
			        	col3=0; col2=0; col1=0;
			        } else {
			        	col3=0; col2=0; col1=255;
			        }
			        if(u.universe[i][j][k]==1 && u.maxVal != 1) {col3=180; col2=180; col1=0;}
	        	}
	        	
	        	if(colourScheme == -1) {
	        		float ageLimit = 16;
	        		if(u.maxVal > ageLimit && u.universe[i][j][k] > ageLimit) {
	        			g2d.setColor(Color.getHSBColor(1, 0, 1));
	        		} else {
	        			
	        			float univ = (float)u.universe[i][j][k];
	        			float mxv = (float)u.maxVal;
	        			
	        			
	        			if(mxv > ageLimit){ mxv = ageLimit; /*univ = univ/ageLimit;*/}
	        			
	        			
		        		float hue = univ/mxv;
		        		float antihue = univ/(float)u.minVal;
		        			        		
		        		if(u.universe[i][j][k] < 0) {
		        			g2d.setColor(Color.getHSBColor((float) antihue, 1, 1));
		        		} else if(u.universe[i][j][k]==0) {
		        			g2d.setColor(Color.getHSBColor((float) 0, 0, (float)0.25));
		        		} else {
		        			g2d.setColor(Color.getHSBColor((float) hue, 1, 1));
		        		}
		        		
		        		if(u.maxVal == 1 && u.universe[i][j][k] > 0) {
		        			g2d.setColor(Color.getHSBColor(1, 0, 1));
		        		}
		        		
	        		}
	        		
	        		
	        		//System.out.println(hue);
	        		
	        	} else {
			        //prepare to draw
			        floats = Color.RGBtoHSB(col1, col2, col3, floats);
			        g2d.setColor(Color.getHSBColor(floats[0],floats[1],floats[2]));
			                    
			        //draw
	        	}

			    g2d.drawLine(i,j,i,j);
	        }
	    }
	        
	    //draw and recurve
	    if(!paused && upaused) {
	    	paused = true;
	    	repaint();
	    } else { repaint(); }
       //End Drawing cycle
    }
   
    
    //repaint() calls this
    @Override
    public void paintComponent(Graphics g) {
    	//Begin paintComponent output
        if(!paused){
        	super.paintComponent(g); 	       
        	doDrawing(g);
        }				
    }
    
}