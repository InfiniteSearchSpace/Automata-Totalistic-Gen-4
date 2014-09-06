public class neighbours {
	int[][] NBH;
	
	public neighbours(int countNbr) {

		//Toggleframe input, conway neighbourhood default
		NBH = new int[][] {
			{-1,-1,0},
			{-1,0,0},
			{-1,1,0},
			{0,-1,0},
			{0,1,0},
			{1,-1,0},
			{1,0,0},
			{1,1,0}
		};
		
	}
	
	//called to define a neighbour's position (x,y,z,neighbour id)
	public void setNBH(int xx, int yy, int zz, int nbr) {
		NBH[nbr][0] = xx;
		NBH[nbr][1] = yy;
		NBH[nbr][2] = zz;
	}

}