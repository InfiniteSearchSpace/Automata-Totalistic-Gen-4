import java.io.File;
import java.util.Random;


public class Universe {

	//define the container of the universe array 
	public int[][][] universe; 				//Dimensions considered x, y, z
	public int[][][] snapshotUniverse;		//Stores a unchanged copy of the whole exact universal state

	public int maxVal; //for strictly display purposes according to philosophy. Knowing or referencing this value would cause universal nonlocality.
	public int minVal;
	
	int xlen;
	int ylen;
	int zlen; 
	
	int record = -1;
	int recordZoom = 1;
	
	int recordCounter = 0;
	int imageNumber = 0;
	
	int genNumber = 0;
	
	Random r = new Random();
	
	public boolean paused = false;
	
	boolean statsLive = false;
	StatisticFrame statAr;
	
	automataLib a;
	dataSources d;
	
	String folder;
	
	//set the universal array
	public Universe(int x, int y, int z, automataLib aa) { 
		xlen = x;
		ylen = y;
		zlen = z;
		
		a=aa;
		
		universe = new int[x][y][z];
		snapshotUniverse = new int[x][y][z];

		resetAr(0); 			//sets each x,y,z to 0; instanciating the array.
	}
	
	
	public void name() {
		
		recordCounter = 0;
		imageNumber = 0;
		
		folder = String.valueOf(r.nextInt(10));
		
		for(int i = 0; i < 5; i++){
			folder = r.nextInt(10) + folder;
		}
		new File(folder).mkdirs();
	}
	
	//hard resets all the xyz values to val, resets maxval = 1.
	public void resetAr(int val) { 

		
		for(int i = 0; i < universe.length; i++) {
			for (int j = 0; j < universe[0].length; j++) {
				for (int k = 0; k < universe[0][0].length; k++) {
					universe[i][j][k] = val;
				}
			}
		}
		
		
	}
	
	//resets the z-layer only, to this value
	public void resetArZ(int val, int zz) { 

		
		for(int i = 0; i < universe.length; i++) {
			for (int j = 0; j < universe[0].length; j++) {
					universe[i][j][zz] = val;
			}
		}
		
		
	}

	
	//Snapshots the universe in it's current state, storing all values exactly as is, in snapshotUniverse[][][]
	public void snap() {
		for(int i = 0; i < universe.length; i++) {
			for (int j = 0; j < universe[0].length; j++) {
				for (int k = 0; k < universe[0][0].length; k++) {
					snapshotUniverse[i][j][k] = universe[i][j][k]; 	//copy the array
				}
			}
		}
	}
	
	//Takes the Z frame and resets it to the maxval for just that frame
	public /*int*/void maxValAudit(int k) {
		maxVal = 1;
		minVal = -1;
		//int total = 0;
		for(int i = 0; i < universe.length; i++) {
			for (int j = 0; j < universe[0].length; j++) {
				int univ = universe[i][j][k];
				if(univ > maxVal) { maxVal = univ; }
				if(univ < minVal) { minVal = univ; }
				//total+=universe[i][j][k];
			}
		}
		//return total;
	}
	
	/////////////////////////////////////////////
	//            UNIVERSE CONTROL             //
	/////////////////////////////////////////////
	
    //Garden of Eden, absolute seed for t=0
    public void runOnce(int rand, int val) {
    	a.setTargetUni(this);
    	a.seedAll(rand,val,1);
    }
    	
    //handles the universal calculations 
    public void updateUniverse() { 
    	if (!paused) {
    		
	    	snap(); 					//snapshots the board to u.snapshotUniverse to ensure all functions have equal execution options
	    	a.setTargetUni(this);	//Ensures that the logic controller acts apon THIS universe

	    	for(int i = 0; i < xlen; i++) {
				for (int j = 0; j < ylen; j++) {
					for (int k = 0; k < zlen; k++) {
						a.readInstructions(i, j, k);
					}
				}
	    	}
	    	
	    	if(record != -1){
	    		if(recordCounter%(record) == 0){
	    			new GetVid(this, folder, imageNumber, recordZoom);
	    			imageNumber++;
	    		}
	    		recordCounter++;
			} 
	    	
	    	if(statsLive && statAr != null) {
	    		statAr.updateGraph();
	    	}
	    	
	    	genNumber++;
	    	
	    	
    	}
    }

	
}
