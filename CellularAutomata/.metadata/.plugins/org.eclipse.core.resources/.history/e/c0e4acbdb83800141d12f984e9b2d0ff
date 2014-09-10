
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class StatisticFrame extends JFrame {
	
	ClickPanel[][] pp;
	automataLib a;
	jMenuStats jmen;
	DotPlot dp;
	
	int pXCount = 0;
	int pYCount = 0;
	int panelSize = 16;
	int lastsize = panelSize;
	int maxPanelVal = 0;
	int gridSize = 20;
	
	public StatisticFrame(ml ML, automataLib aa) {
		
		a=aa;
		
		a.u.statsLive = true;
		a.u.statAr = this;
		
		jmen = new jMenuStats(this, ML, a);
		
		initSF();
		
		graphInit();
	}
	
	public void initSF(){
		
		int frameSize = 350;
		
		setLayout(null);
		
		setSize(frameSize, 200);
		
		setVisible(true);
		setResizable(false);
		setLocation(96, 32);
		
		

	}

	public void graphInit() {
		dp = new DotPlot(4,4,getWidth()-16,getHeight()-64,a.u);
		add(dp);
		dp.repaint();
	}
	
	public void updateGraph(){
		dp.repaint();
	}
}
