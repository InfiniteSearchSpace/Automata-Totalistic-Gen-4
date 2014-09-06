import java.util.Random;


public class dataSources {
	
	public int[][][] sources;
	
	int xPos;
	int yPos;
	int zPos;
	Random r = new Random();
	ml m;
	
	public dataSources() {
		//setArray(new int[][][] {	{{r.nextInt(2)-1}},{{r.nextInt(2)-1}},{{r.nextInt(8)-4}},{{r.nextInt(4)-2}},{{r.nextInt(2)-1}}	,{{r.nextInt(16)-8}},{{r.nextInt(16)-8}}	,{{r.nextInt(16)-8}},{{r.nextInt(16)-8}}		});
		setArray(new int[][][] {	{{0}},{{0}},{{0}},{{0}},{{0}}	,{{0}},{{0}}	,{{0}},{{0}}		});
	}
	
	public int readNext() {
		
		xPos++;
		if(xPos>sources.length-1) {
			xPos=0;
			yPos++;
		}
		
		if(yPos>sources[0].length-1) {
			yPos=0;
			zPos++;
		}
		
		if(zPos>sources[0][0].length-1) {
			zPos=0;
		}
		
		return sources[xPos][yPos][zPos];
		
	}

	public void setArray(int[][][] ar){
		sources = ar;
	}
	
	public int[][][] getArray(){
		return sources;
	}
	
	public void reset(){
		xPos = 0;
		yPos = 0;
		zPos = 0;
	}
	
}
