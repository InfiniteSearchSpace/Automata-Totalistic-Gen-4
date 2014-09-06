
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.Border;
 
public class jMenuMain implements ActionListener {

    ml mML;
    Main m;
    automataLib a;
    
    //constructor, captures UI controller: ml
    public jMenuMain(ml myml, automataLib aa) {
		mML = myml;
		a = aa;
		//System.out.println(a);
	}

    //instanciate & deploy menu bar
	public void setGUI(Main mm) {
    	m = mm;
    	
        jMenuMain menuMain = new jMenuMain(mML, a);
        m.setJMenuBar(menuMain.createMenuBar());
	}

	public JMenuBar createMenuBar() {

		
		JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        
        
        //menu bar
        menuBar = new JMenuBar();
        
        //create new menu
        menu = new JMenu("Universe");
        menu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        menuBar.add(menu);
        
        //Populate menu
        menuItem = new JMenuItem("Play/Pause");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu.addSeparator();
        
        menuItem = new JMenuItem("Erase Layer");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Erase All");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu.addSeparator();

        menuItem = new JMenuItem("Reseed Layer");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Reseed All");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Set Seed");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu.addSeparator();
        
        menuItem = new JMenuItem("Capture Image");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Image Every ? Frames");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        
      //create new menu
        
        menu = new JMenu("Cursor Tools");
        menu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        menuBar.add(menu);
        
        //Populate menu
        menuItem = new JMenuItem("Place Solid Blocks");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Place Random Blocks");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Erase Blocks");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menu.addSeparator();
        
        menuItem = new JMenuItem("Set Tool Size");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Set Tool Value");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Set Tool Distribution");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Set Tool Value Variation");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        
       
        

      //create new menu
        
        menu = new JMenu("Editor");
        menu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        menuBar.add(menu);
        
        menuItem = new JMenuItem("New Editor");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu.addSeparator();
        
        menuItem = new JMenuItem("Tool: Copy");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Tool: Paste");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        
        
        //create new menu
        menu = new JMenu("Mousewheel Bind");
        menu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        menuBar.add(menu);

        //Populate menu
        menuItem = new JMenuItem("Tool Subfunctions");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Z-layers");
        menuItem.addActionListener(this);
        menu.add(menuItem);
                
        menuItem = new JMenuItem("Tool Size");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Tool Value");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Colour Schemes");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        return menuBar;
    }
 
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        
        if(source.getText() == "Play/Pause") {mML.toggleStart();}

        if(source.getText() == "Erase Layer") {mML.eraseLayer();}
        if(source.getText() == "Erase All") {mML.eraseAll();}
        if(source.getText() == "Capture Image") {new getImage(a.u,"",-1);}
        
        if(source.getText() == "Image Every ? Frames") {
        	
        	String str = JOptionPane.showInputDialog(m, "Take a screenshot every x frames:", "16");
        	if(str != null) {
        		a.u.record = Integer.parseInt(str);
    		}
        	
        	str = JOptionPane.showInputDialog(m, "Pixel Magnification", "2");
        	if(str != null) {
        		a.u.recordZoom = Integer.parseInt(str);
    		}
        	
        	a.u.name(); 
        }
    	
        if(source.getText() == "Reseed Layer") 	{mML.reseedLayer(mML.seedRand, mML.seedVal);}
        if(source.getText() == "Reseed All") 	{mML.reseedAll(mML.seedRand, mML.seedVal);}
        if(source.getText() == "Set Seed") 		{mML.dialogReseed();}
       
        if(source.getText() == "Place Solid Blocks") 	{mML.setFunctionType(0);setBlocks();mML.blockSize = -1;mML.toolRand = -1;mML.toolVar = -1;mML.resetVal = true;}
        if(source.getText() == "Place Random Blocks") 	{mML.setFunctionType(1);setBlocks();mML.blockSize = -1;mML.toolRand = -1;mML.toolVar = -1;mML.resetVal = true;}
        if(source.getText() == "Erase Blocks") 			{mML.setFunctionType(2);setBlocks();mML.blockSize = -1;mML.toolRand = -1;mML.toolVar = -1;mML.resetVal = true;}

        if(source.getText() == "Tool: Copy") 					{mML.setFunctionType(3);mML.myFunction = 2;setBlocks();mML.blockSize = -1;mML.toolRand = -1;mML.toolVar = -1;mML.resetVal = true;}
        if(source.getText() == "Tool: Paste") 					{mML.setFunctionType(3);mML.myFunction = 1;setBlocks();mML.blockSize = -1;mML.toolRand = -1;mML.toolVar = -1;mML.resetVal = true;}
        
        if(source.getText() == "Set Tool Size") 				{mML.dialogSetBlockSize();}
        if(source.getText() == "Set Tool Value") 				{mML.dialogSetBlockVal();}
        if(source.getText() == "Set Tool Distribution") 		{mML.dialogSetBlockRand();}
        if(source.getText() == "Set Tool Value Variation") 		{mML.dialogSetBlockVar();}
        
        
        if(source.getText() == "Tool Subfunctions") {mML.mwPos = mML.myFunction; 				mML.mwMax = mML.fcnt; 				mML.cycleNum = 0;}
        if(source.getText() == "Z-layers") 			{mML.mwPos = mML.s.zdraw; 		mML.mwMax = mML.s.zz; 	mML.cycleNum = 1;}
        if(source.getText() == "Tool Size") 		{mML.mwMax = 100; 					mML.cycleNum = 3;}
        if(source.getText() == "Tool Value") 		{mML.mwMax = 100; 					mML.cycleNum = 4;}
        if(source.getText() == "Colour Schemes")	{mML.mwMax = mML.totalColourSchemes;	mML.cycleNum = 7;}
        
        
        if(source.getText() == "New Editor") 		{
        	//System.out.println("New Editor:" + a);
        	ToggleFrame TF = new ToggleFrame(mML, a);
        	TF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
        
        mML.refresh();
    }
 
    private void setBlocks(){
    	mML.mwPos = mML.myFunction;
    	mML.mwMax = mML.fcnt;
    	mML.cycleNum = 0;
    }
    
    
    
    
    
    
}