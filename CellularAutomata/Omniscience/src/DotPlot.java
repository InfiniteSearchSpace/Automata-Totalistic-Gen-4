import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;


public class DotPlot extends JPanel {
	
	float[] floats = new float[3];
	Graphics g;
	int[][] graphData;
	int[] graphVals;
	Random r = new Random();
	int[][][] uniAudit;
	Universe u;
	float gvMax = Integer.MIN_VALUE;
	float gvMin = Integer.MAX_VALUE;
	int viewType = 0;
	int targetType = 0;
	
	int monotone = 255;
	
	public DotPlot(int xx, int yy, int ww, int hh, Universe uu){
		setSize(ww, hh);
    	setLocation(xx, yy);
    	graphData = new int[ww][hh];
    	graphVals = new int[ww];
    	u=uu;
	}
	
	//repaint() calls this
    @Override
    public void paintComponent(Graphics g) {
    	//Begin paintComponent output
        super.paintComponent(g); 	       
        doDrawing(g);
    }
	
	private void doDrawing(Graphics g) { 
    	//Begin Draw configuration
    	
    	Graphics2D g2d = (Graphics2D) g; 	//some sort of magic.
    	
    	if(targetType == 0) {countPoplation();}
    	if(targetType == 1) {countNewBirths();}
    	//if(targetType == 1) {countModeVal();}
    	
	    
	    get_gvMinMax();
	    for (int i = 0; i < getWidth(); i++) {
	        for (int j = 0; j < getHeight(); j++) {
	        	
	        	monotone = 255;
	        	//relative
	        	if(viewType == 0) {
		        	if(graphVals[i] != 0) {
		        		if(
		        				
		        			getHeight()-j == (int) (
		        				( ((float)graphVals[i]-gvMin) / (gvMax-gvMin) ) * ( (float)getHeight() ) 
		        			) 
		        			
		        		) {
		        			monotone = 0;
		        		}
		        	}
	        	}
	        	
	        	//absolute
	        	if(viewType == 1) {
	        		if(graphVals[i] != 0) {
		        		if(
		        				
		        			getHeight()-j == (int) (
		        				( ((float)graphVals[i]) / (gvMax) ) * ( (float)getHeight() ) 
		        			) 
		        			
		        		) {
		        			monotone = 0;
		        		}
		        	}
	        	}
	        	
	        	//prepare to draw
			    floats = Color.RGBtoHSB(monotone, monotone, monotone, floats);
			    g2d.setColor(Color.getHSBColor(floats[0],floats[1],floats[2]));
			    

			    //draw

			    g2d.drawLine(i,j,i,j);
	        }
	    }
	    
	    //repaint();

    }
   
	private void getGraphVal_prepare() {
		removeLeftmost();
		uniAudit = u.snapshotUniverse;
	}
	
	private void removeLeftmost() {
		for(int i = 0; i < graphVals.length-1; i++) {
			graphVals[i] = graphVals[i+1];
		}
	}
	
	private void countPoplation() {
		getGraphVal_prepare();
		
		int population = 0;
		for(int i = 0; i < uniAudit.length; i++) {
			for (int j = 0; j < uniAudit[i].length; j++) {
				for (int k = 0; k < uniAudit[i][j].length; k++) {
					if(uniAudit[i][j][k] != 0) {population++;}
				}
			}
    	}
		
		graphVals[graphVals.length-1] = population;
	}
	
	private void countNewBirths() {
		getGraphVal_prepare();
		
		int population = 0;
		for(int i = 0; i < uniAudit.length; i++) {
			for (int j = 0; j < uniAudit[i].length; j++) {
				for (int k = 0; k < uniAudit[i][j].length; k++) {
					if(uniAudit[i][j][k] == 1) {population++;}
				}
			}
    	}
		
		graphVals[graphVals.length-1] = population;
	}
	
	
	
	private void get_gvMinMax() {
		if(viewType == 0) {
			gvMax = Integer.MIN_VALUE;
			gvMin = Integer.MAX_VALUE;
		}
		
		for(int i = 0; i < graphVals.length; i++) {
			if (graphVals[i] > gvMax) {gvMax = graphVals[i];}
			if (graphVals[i] < gvMin) {gvMin = graphVals[i];}
		}
	}
}
