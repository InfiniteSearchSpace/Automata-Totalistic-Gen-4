
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class ToggleFrame extends JFrame {
	
	ClickPanel[][] pp;
	automataLib a;
	jMenuEditor jmen;
	
	int pXCount = 0;
	int pYCount = 0;
	int panelSize = 16;
	int lastsize = panelSize;
	int maxPanelVal = 0;
	int gridSize = 20;
	
	public ToggleFrame(ml ML, automataLib aa) {

		String str = JOptionPane.showInputDialog(null, "Grid Size:", gridSize);
    	if(str != null) {
    		gridSize = Integer.parseInt(str);
		}
		
		a=aa;
		jmen = new jMenuEditor(this, ML, a);
		initTF(gridSize);
		
	}
	
	public void initTF(int blockSize){
		
		int panels = blockSize;
		if(panels == -1) {panels = lastsize;} else {lastsize = panels;}
		int frameSize = (panelSize+1)*panels;
		
		setLayout(null);
		
		int frx = frameSize+8+8;
		int fry = frameSize+30+22+8;

		if(frx < 400) { frx = 400; }
		if(fry < 96) { fry = 96; }
		setSize(frx, fry);
		
		
		setVisible(true);
		setResizable(false);
		setLocation(a.m.getWidth()+100, 32);
		
		int panelsX = (frameSize/(panelSize+1));
		int panelsY = (frameSize/(panelSize+1));
		pXCount = panelsX;
		pYCount = panelsY;
		pp = new ClickPanel[panelsX][panelsY];
		
		for(int i = 0; i < panelsX; i++) {
			for(int j = 0; j < panelsY; j++) {
	    		pp[i][j] = new ClickPanel(this, i, j, panelSize);
	    		add(pp[i][j]);
			}
		}

	}

	
	public int [][] getPanelStates() {
		int[][] output = new int[pp.length][pp[0].length];
    	//String ss = "";
    	
    	for(int j = 0; j < pp.length; j++) {
    		for(int i = 0; i < pp[j].length; i++) {
    			
        		output[i][j] = pp[i][j].val;
        		//ss+=output[i][j] + ",";
        		
        	}
    		//ss+="\n";
    	}
    	//System.out.println(ss);
    	return output;
	}
	
	public void resetPanels() {
    	for(int j = 0; j < pp.length; j++) {
    		for(int i = 0; i < pp[j].length; i++) {
    			pp[i][j].setVal(0);
        	}
    	}
    	updatePanelColors();
	}
	
	public int getMax(){
		maxPanelVal=0;
		for(int i = 0; i < pYCount; i++) {
			for(int j = 0; j < pXCount; j++) {
	    		if (maxPanelVal < Math.abs(pp[i][j].val)) {
	    			maxPanelVal =Math.abs(pp[i][j].val);
	    		}
			}
		}
		//System.out.println(maxPanelVal);
		return maxPanelVal;
	}
	
	public void updatePanelColors(){
		for(int i = 0; i < pYCount; i++) {
			for(int j = 0; j < pXCount; j++) {
	    		pp[i][j].updateBG();
			}
		}
	}
	
}
