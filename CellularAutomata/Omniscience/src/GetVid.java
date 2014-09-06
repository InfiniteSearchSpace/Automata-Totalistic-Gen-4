import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class GetVid {
	String myfolder;
	int imagenum;
	int pixelMultiplier;
	
	public GetVid(Universe u, String folder, int imagenumber, int pixMulti) {
		myfolder = folder;
		imagenum = imagenumber;
		Random r = new Random();
		pixelMultiplier = pixMulti;
		
		BufferedImage buff = new BufferedImage(u.universe.length*pixelMultiplier, u.universe[0].length*pixelMultiplier, BufferedImage.TYPE_INT_RGB);
		int cellRGB = 0;
		for (int i = 0; i < u.universe.length; i++) {
			for (int j = 0; j < u.universe[0].length; j++) {
	
	        	float ageLimit = 16;	
	        	float univ = (float)u.universe[i][j][0];
        		float mxv = (float)u.maxVal;
        		
        		if(mxv > ageLimit){ mxv = ageLimit;}
        		
	        	float hue = univ/mxv;
	        	float antihue = univ/(float)u.minVal;	
	        	
	        	
	        	
        		if(u.maxVal > ageLimit && univ > ageLimit) {
        			cellRGB = Color.HSBtoRGB(1, 0, 1);
        		} else if(u.universe[i][j][0] < 0) {
        			cellRGB = Color.HSBtoRGB((float) antihue, (float)1, (float)1);
        		} else if(u.universe[i][j][0]==0) {
        			cellRGB = Color.HSBtoRGB((float) 0, (float)0, (float)0);
        		} else if(mxv == 1 && univ > 0) {
        			cellRGB= Color.HSBtoRGB(1, 0, 1);
        		} else {
        			cellRGB = Color.HSBtoRGB((float) hue, (float)1, (float)1);
        		}
        		
        		
        		for(int k = 0; k < pixelMultiplier; k++) {
        			for(int l = 0; l < pixelMultiplier; l++) {
        				buff.setRGB(i*pixelMultiplier+k, 		j*pixelMultiplier+l, 		cellRGB);
        			}
        		}
        		
			}
		}
		
		
		try {
			String imageName = "";
			
			if(imagenum != -1) {
				System.out.println(imagenum);
				String zeroes = "00000000";
				int imageNumLen = String.valueOf(imagenum).length();
				for (int i = 0; i < imageNumLen; i++) {
					zeroes = zeroes.substring(0, 8-imageNumLen);
				}
				
				imageName = "im" + zeroes + imagenum;
				
				if(myfolder != "") {
					imageName = myfolder + "/" + imageName;
				}
				
			} else {
				
				imageName = "";
				for(int i = 0; i < 8; i++) {
					imageName = r.nextInt(10) + imageName;
				}
			}
			
			ImageIO.write(buff, "bmp", new File(imageName+".bmp"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
