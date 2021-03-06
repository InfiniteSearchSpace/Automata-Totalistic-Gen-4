import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;


public class DotPlot extends JPanel {
	
	float[] floats = new float[3];
	Graphics g;
	int[][] graphData;
	int[] graphValsMono;
	int[][] graphValsMany;
	Random r = new Random();
	int[][][] uniAudit;
	Universe u;
	float gvMax = Integer.MIN_VALUE;
	float gvMin = Integer.MAX_VALUE;
	int viewType = 0;
	int targetType = 2;
	
	int monotone = 255;
	
	public DotPlot(int xx, int yy, int ww, int hh, Universe uu){
		setSize(ww, hh);
    	setLocation(xx, yy);
    	graphData = new int[ww][hh];
    	graphValsMono = new int[ww];
    	graphValsMany = new int[ww][17];
    	u=uu;
	}
	
	/* Class Map
	 * Init: DotPlot()
	 * Run: repaint() (called by stat frame)
	 *   Choose Function
	 *   Find L/U bounds in graph data: get_gvMinMax()
	 *   Get colour & position ready
	 *   Draw
	 *   
	 * 
	 *   
	 */
	
	
	//repaint() calls this
    @Override
    public void paintComponent(Graphics g) {
    	//Begin paintComponent output
        super.paintComponent(g); 	       
        doDrawing(g);
    }
	
	private void doDrawing(Graphics g) { 
    	//Begin Draw configuration
    	Graphics2D g2d = (Graphics2D) g; 
    	
    	if(targetType == 0) {countPoplation();get_gvMinMax();drawMono(g2d);}
    	if(targetType == 1) {countNewBirths();get_gvMinMax();drawMono(g2d);}
    	if(targetType == 2) {getFreqy();get_gvMinMaxMany();drawMany(g2d);}
    	
	    //get_gvMinMax(); //find Ubound & Lbound
	    
	    //repaint();

    }
   
	
	private void drawMono(Graphics2D g2d) {
			//some sort of magic.
		for (int i = 0; i < getWidth(); i++) {
	        for (int j = 0; j < getHeight(); j++) {
	        	
	        	monotone = 255;
	        	//relative
	        	if(viewType == 0) {
		        	if(graphValsMono[i] != 0) {
		        		if(
		        				
		        			getHeight()-j == (int) (
		        				( ((float)graphValsMono[i]-gvMin) / (gvMax-gvMin) ) * ( (float)getHeight() ) 
		        			) 
		        			
		        		) {
		        			monotone = 0;
		        		}
		        	}
	        	}
	        	
	        	//absolute
	        	if(viewType == 1) {
	        		if(graphValsMono[i] != 0) {
		        		if(
		        				
		        			getHeight()-j == (int) (
		        				( ((float)graphValsMono[i]) / (gvMax) ) * ( (float)getHeight() ) 
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
	}
	
	private void drawMany(Graphics2D g2d) {
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				//monotone = 255;
				g2d.setColor(Color.getHSBColor(1, 0, 1)); //background
				
				for (int k = 0; k < graphValsMany[i].length; k++) {
	        	
					if(getHeight()-j == (int) (
							(((float)graphValsMany[i][k]-gvMin) / (gvMax-gvMin)) * ((float)getHeight()) 
					)) {
		        		//monotone = (int)( ((float)graphValsMany[i].length/(float)k)*(float)255 );
						
						float ageLimit = 17;
						float univ = (float)graphValsMany[i].length;
		    			float mxv = (float)k;
		    			if(mxv > ageLimit){ mxv = ageLimit; }
		    			
		    			float hue = univ/mxv;
		    			
		    			g2d.setColor(Color.getHSBColor((float) hue, 1, 1));
		        	}
		        		

				}
				
				
				
				//prepare to draw
			    //floats = Color.RGBtoHSB(monotone, monotone, monotone, floats);
			    //g2d.setColor(Color.getHSBColor(floats[0],floats[1],floats[2]));
			    
			    //draw

			    g2d.drawLine(i,j,i,j);
	        }
	    }
	}
	
	//pops the un-needed datapoint and updates buffered universe ref
	private void getGraphVal_prepare() {
		removeLeftmost();
		uniAudit = u.snapshotUniverse;
	}
	
	private void removeLeftmost() {
		for(int i = 0; i < graphValsMono.length-1; i++) {
			graphValsMono[i] = graphValsMono[i+1];
		}
	}
	
	private void getGraphVal_prepareMany() {
		removeLeftmostMany();
		uniAudit = u.snapshotUniverse;
	}
	
	private void removeLeftmostMany() {
		for(int i = 0; i < graphValsMany.length-1; i++) {
			graphValsMany[i] = graphValsMany[i+1];
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
		
		graphValsMono[graphValsMono.length-1] = population;
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
		
		graphValsMono[graphValsMono.length-1] = population;
	}
	
	private void getFreqy() {
		getGraphVal_prepareMany();
		
		int[] popFreq = new int[17];
		
		for(int i = 0; i < uniAudit.length; i++) {
			for (int j = 0; j < uniAudit[i].length; j++) {
				for (int k = 0; k < uniAudit[i][j].length; k++) {
					if(uniAudit[i][j][k] <= 16 && uniAudit[i][j][k] > 0) {popFreq[uniAudit[i][j][k]-1]++;} else if(uniAudit[i][j][k] != 0) {popFreq[16]++;}
				}
			}
    	}
		
		/*String sOut = "";
		for(int i = 0; i < 17; i++) {
			sOut+=popFreq[i]+",";
		}
		System.out.println(sOut);*/
		
		//graphValsMany[width][17]
		graphValsMany[graphValsMany.length-1] = popFreq;
	}
	
	private void get_gvMinMax() {
		if(viewType == 0) {
			gvMax = Integer.MIN_VALUE;
			gvMin = Integer.MAX_VALUE;
		}
		
		for(int i = 0; i < graphValsMono.length; i++) {
			if (graphValsMono[i] > gvMax) {gvMax = graphValsMono[i];}
			if (graphValsMono[i] < gvMin) {gvMin = graphValsMono[i];}
		}
	}
	
	private void get_gvMinMaxMany() {
		if(viewType == 0) {
			gvMax = Integer.MIN_VALUE;
			gvMin = Integer.MAX_VALUE;
		}
		
		for(int i = 0; i < graphValsMany.length; i++) {
			for(int j = 0; j < graphValsMany[i].length; j++) {
				if (graphValsMany[i][j] > gvMax) {gvMax = graphValsMany[i][j];}
				if (graphValsMany[i][j] < gvMin) {gvMin = graphValsMany[i][j];}
			}
		}
	}
	
	public void clearGraph() {

		graphValsMono = new int[getWidth()];
		graphValsMany = new int[getWidth()][17];
		
	}
		
}
