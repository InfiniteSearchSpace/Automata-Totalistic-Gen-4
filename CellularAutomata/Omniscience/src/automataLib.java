

import java.util.Random;


public class automataLib {
	//parent components
	Universe u;
	Main m;
	
	//for random functions
	Random r = new Random();
	
	//used to define neighbourhoods
	neighbours n; 
	Anim_nbrs an;
	
	//Global vars that hold the size of the world array
	int m_xSize;
	int m_ySize;
	
	boolean bAnimNbr = false;
	
	//holds the rule as defined in the toggleframe (default CGoL)
	//						Rule Line 0 - 2
	//						Start, End, NewValue
	int[][] arTF_Ruleset = {{4,8,0},{3,3,1},{0,1,0}}; 
	
	//constructor
	public automataLib(Main mm, int xSize, int ySize) {
		m = mm;
		n= new neighbours(0);
		an= new Anim_nbrs(0);
		m_xSize=xSize;
		m_ySize=ySize;
	}
	
	//takes a universe and the dataSources object for writing
	public void setTargetUni(Universe uu) {
		u=uu;
	}

	
	/*  = = = = = = = = = = = = = = = = = = = = 
	 *  = = = = = = = = = = = = = = = = = = = = 
	 * 		 	Value   Read/Write
	 *  = = = = = = = = = = = = = = = = = = = = 
	 *  = = = = = = = = = = = = = = = = = = = = 
	 */ 
	
	//takes array co-ord, modification int, array param length, and returns wrap position.
    public int getWrap(int val, int mod, int len) { 
    	if((val+mod) % len < 0) {
    		return len+((val+mod) % len);
    	} else {
    		return (val+mod) % len;
    	}
    }

    //sets the specified cell to specified value
    public void placeval(int xx, int yy, int zz, int rand, int v) { 
    	if(r.nextInt(rand) == 0) {
    		u.universe[xx][yy][zz] = v;
    	}
    }
    
    public int getval(int xx, int yy, int zz, int rand, int v) { 
    	if(r.nextInt(rand) == 0) {
    		return u.universe[xx][yy][zz];
    	} else {
    		return 0;
    	}
    }
	
  //draws a line of val. Can be solid or rand, can be veto'd, optional overwrite with 0.    
   public void plcLn(int xx, int yy, int zz, int rand, int xnullx, int val, int veto, int placeO, int len, int blockSize, int toolVar) {
    	
    	if(r.nextInt(veto) == 0) { //chance to not write the line
		    for(int i = 1; i < ((len-xx)/2)+1; i++) {
		    	if(r.nextInt(rand) == 0) {
		    		placeval((xx+i)+((blockSize-1)/2), yy+((blockSize-1)/2), zz, 1, val+r.nextInt(toolVar));
		    	} else if(placeO > 0) {placeval((xx+i)+((blockSize-1)/2)-1, yy+((blockSize-1)/2), zz, 1, 0);}
		    }
    	}
    	
    }
    
    public void plcDataLn(int xx, int yy, int zz, int rand, int xnullx, int val, int veto, int placeO, int len, int blockSize, int toolVar, dataSources d, int softwrite) {
    	
    	if(r.nextInt(veto) == 0) { //chance to not write the line
		    for(int i = 1; i < ((len-xx)/2)+1; i++) {
		    	val = d.readNext();
		    	if(val != 0 || softwrite == 0) { //softwrite
			    	if(r.nextInt(rand) == 0) {
			    		placeval((xx+i)+((blockSize-1)/2), yy+((blockSize-1)/2), zz, 1, val+r.nextInt(toolVar));
			    	} else if(placeO > 0) {placeval((xx+i)+((blockSize-1)/2)-1, yy+((blockSize-1)/2), zz, 1, 0);}
		    	}
		    }
    	}
    	
    }
    
    
    public int[] getDataLn(int xx, int yy, int zz, int rand, int xnullx, int val, int veto, int placeO, int len, int blockSize, int toolVar) {
    	
    	int[] dataLine = new int[blockSize]; 
    	
    	if(r.nextInt(veto) == 0) { //chance to not get the line
		    for(int i = 1; i < ((len-xx)/2)+1; i++) {
		    	int dataInt = 0;
		    	
		    	if(r.nextInt(rand) == 0) {
		    		dataInt = getval((xx+i)+((blockSize-1)/2), yy+((blockSize-1)/2), zz, 1, val+r.nextInt(toolVar));
		    	} else if(placeO > 0) {dataInt = getval((xx+i)+((blockSize-1)/2)-1, yy+((blockSize-1)/2), zz, 1, 0);}
		    	
		    	dataLine[i-1]=dataInt;
		    }
    	}
    	
    	return dataLine;
    	
    }
    
    
    //with chance, set cell to val 
    public void seed(int xx, int yy, int zz, int rand, int xnullx, int val){ //chance to seed location
    	if(r.nextInt(rand) == 0) {placeval(xx, yy, zz, 1, val);}
    }
    
    public void nonUniformSeed() {
    	//Select several random points
    	int numPoints = r.nextInt((int)Math.sqrt((double)u.universe.length))+5;
    	int[][] centerPoints = new int[numPoints][3];
    	for(int i = 0; i < numPoints; i++) {
    		for(int j = 0; j < 2; j++) {
    	    	centerPoints[i][j] = r.nextInt(u.universe.length);
    		}
    	}
    	
    	//Each point has a random 'strength' value
    	for(int i = 0; i < numPoints; i++) {
    		centerPoints[i][2] = r.nextInt(500)+300;
    	}
    	
    	//Random chance to place seeded cell is a function of the distance from the closest seedpoint in a square hitbox
    	
    	
    	
    	for(int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[i].length; j++) {
				//for (int k = 0; k < u.universe[i][j].length; k++) {
					
					int nearestPoint = r.nextInt(centerPoints.length);
					
					int xLen = 1;
			    	int yLen = 1;
			    	int distToPoint = 1;
			    	
					for(int l = 0; l < 10; l++) {
				    	xLen = Math.abs(i - centerPoints[nearestPoint][0]);
				    	yLen = Math.abs(j - centerPoints[nearestPoint][1]);
				    	distToPoint = xLen+yLen;
				    	if(distToPoint > 128) {nearestPoint = r.nextInt(centerPoints.length);} else {break;}
				    }
			    	
					int rand = (distToPoint*distToPoint)/(centerPoints[nearestPoint][2])+1;

					
			    	seed(i,j,0,rand,-1,1);
				//}
			}
    	}
    		//if equal distance, roll a dice
    	//overlay a light normal random seed
    	//create solid and empty regions
    }
    
    //with chance, reset pixel to val, applies to whole universe
    public void seedAll(int rand, int val, int rndVar){
    	
    	for(int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
				for (int k = 0; k < u.universe[0][0].length; k++) {
			    	seed(i,j,k,rand,-1,val+r.nextInt(rndVar));
				}
			}
    	}
    } 
    
    //chance to seed every pixel on this layer
    public void seedZ(int rand, int zz, int val, int rndVar){ 
    	for(int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
			    seed(i,j,zz,rand, 0, val+r.nextInt(rndVar));
			}
    	}
    }

    
	/*  = = = = = = = = = = = = = = = = = = = = 
	 *  = = = = = = = = = = = = = = = = = = = = 
	 * 		 	Neighbourhood Checks
	 *  = = = = = = = = = = = = = = = = = = = = 
	 *  = = = = = = = = = = = = = = = = = = = = 
	 */ 

	
	//counts the neighbours not equal to val
	public int nbrCountNotVal(int xx, int yy, int zz, int val){
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			if(u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)] != val) {isOne++;}
		}
		
		return isOne;
	}
	 
	
	public int nbrCountNotVal_Animated(int xx, int yy, int zz, int val){
		int isOne = 0;
		int numFrames = an.NBH.length;
		
		for(int i = 0; i < an.NBH[numFrames-1].length; i++) {
			if(u.snapshotUniverse[getWrap(xx, an.NBH[u.genNumber%numFrames][i][0], u.universe.length)][getWrap(yy, an.NBH[u.genNumber%numFrames][i][1], u.universe[0].length)][getWrap(zz, an.NBH[u.genNumber%numFrames][i][2], u.universe[0][0].length)] != val) {isOne++;}
		}
		
		return isOne;
	}
	
	public int nbrSum(int xx, int yy, int zz, int val){
		int isOne = 0;

		for(int i = 0; i < n.NBH.length; i++) {
			isOne += u.snapshotUniverse[getWrap(xx, n.NBH[i][0], u.universe.length)][getWrap(yy, n.NBH[i][1], u.universe[0].length)][getWrap(zz, n.NBH[i][2], u.universe[0][0].length)];
		}
		
		return isOne;
	}
	 
	
	/*  = = = = = = = = = = = = = = = = = = = = 
	 *  = = = = = = = = = = = = = = = = = = = = 
	 * 		 	Automata
	 *  = = = = = = = = = = = = = = = = = = = = 
	 *  = = = = = = = = = = = = = = = = = = = = 
	 */ 
	
	
	/*		Parameters
	 * Default: xx, yy, zz
	 * RandomProc
	 * Threshold
	 * 
	 * 
	 */

	
	public void arrayRuleIncrement(int xx, int yy, int zz){
		int nbrCount = 0;
		/*if(!bAnimNbr){*/
		//if(u.snapshotUniverse[xx][yy][zz] == 0){
		nbrCount = nbrCountNotVal(xx,yy,zz,0);
		
		/*} else {
			nbrCount = nbrSum(xx,yy,zz,0);/*nbrCountNotVal_Animated(xx,yy,zz,0);*/
		/*}*/
		
		for(int i = 0; i < arTF_Ruleset.length; i++) {
			if(nbrCount >= arTF_Ruleset[i][0] && nbrCount <= arTF_Ruleset[i][1]) {
				if(arTF_Ruleset[i][2] != 0) {
					u.universe[xx][yy][zz] += arTF_Ruleset[i][2]; //increment
					//u.universe[xx][yy][zz] = arTF_Ruleset[i][2]; //set
				} else {
					u.universe[xx][yy][zz]=0;
				}
			}
		}
		//}
	}
		
	/////////////////////////////////////////////
	/////////Select Instruction to run///////////
	/////////////////////////////////////////////
																								  
	public void readInstructions(int xx, int yy, int zz) {
		arrayRuleIncrement(xx,yy,zz);
	}

	
}
