
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
 
public class jMenuEditor implements ActionListener {

	ToggleFrame TF;
	ml ML;
	automataLib a;
	boolean bLiveRuleUpdate = false;
		
	int[][] nbrhood;
    //constructor, captures UI controller: ml
    public jMenuEditor(ToggleFrame TFrame, ml mML, automataLib aa) {
    	ML = mML;
    	TF = TFrame;
        TF.setJMenuBar(createMenuBar());
        a=aa;
        //System.out.println(a);
	}

	public JMenuBar createMenuBar() {

		JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        //JMenu submenu;
        
        //menu bar
        menuBar = new JMenuBar();
        
        //create new menu
        menu = new JMenu("Clipboard");
        menu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Erase Panels");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menu.addSeparator();
        
        menuItem = new JMenuItem("Copy To Clip");
        menuItem.addActionListener(this);
        menu.add(menuItem); 
        
        menuItem = new JMenuItem("Paste From Clip");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        
        menu = new JMenu("Automaton Config");
        menu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        menuBar.add(menu);
        
        //Populate menu
        
       
        
        menuItem = new JMenuItem("Get Rule");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Get Neighbourhood");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        
        menu.addSeparator();
        
        menuItem = new JMenuItem("Set Rule");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Set Neighbourhood");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu.addSeparator();

        menuItem = new JMenuItem("Nudge Rule");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu.addSeparator();
        
        menuItem = new JMenuItem("Toggle Live Rule Updating");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
       /* menu.add(menuItem);menu.addSeparator();
        
        menuItem = new JMenuItem("[Override] Toggle Animated Neighbourhood");
        menuItem.addActionListener(this);
        menu.add(menuItem);*/
        
        
        
        
        
        
        menu = new JMenu("Import/Export");
        menu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        menuBar.add(menu);
        
         menuItem = new JMenuItem("Import Rule");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Import Neighbourhood");
        menuItem.addActionListener(this);
        menu.add(menuItem);
       
        
        menu.addSeparator();
        menuItem = new JMenuItem("Export Rule");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Export Neighbourhood");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        
        menu.addSeparator();
         
       

        menuItem = new JMenuItem("Open Rule Database File");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        
        
        
        
        menu = new JMenu("Help");
        menu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        menuBar.add(menu);

        menuItem = new JMenuItem("Controls & Interaction");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Get & Set");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Rule Layout");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Neighbour Layout");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Import/Export");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        return menuBar;
    }
 
    public void actionPerformed(ActionEvent e) {
    	
        JMenuItem source = (JMenuItem)(e.getSource());
  
        
        if(source.getText() == "Erase Panels") 				{bLiveRuleUpdate = false;TF.resetPanels();} 	
        
        if(source.getText() == "Get Neighbourhood") 	{bLiveRuleUpdate = false;getNeighbourhood();} 	
        
        if(source.getText() == "Get Rule") 				{getRule();} 
        
        if(source.getText() == "Paste From Clip") 		{bLiveRuleUpdate = false;pasteFromClip();} 	
        
        if(source.getText() == "Copy To Clip") 			{copyToClip();} 	

        if(source.getText() == "Set Neighbourhood") 	{bLiveRuleUpdate = false; setNeighbourhood();}

        if(source.getText() == "Set Rule") 				{setRule();}

        if(source.getText() == "Import Rule") 				{importRule();}
        
        if(source.getText() == "Export Rule") 				{exportRule();}
        
        if(source.getText() == "Import Neighbourhood") 				{importNeighbourhood();}
        
        if(source.getText() == "Export Neighbourhood") 				{exportNeighbourhood();}
        
       // if(source.getText() == "[Override] Toggle Animated Neighbourhood") 	{if(a.bAnimNbr == false) {a.bAnimNbr = true;} else {a.bAnimNbr = false;}}
        
        if(source.getText() == "Toggle Live Rule Updating") {
        	 if(bLiveRuleUpdate == false) {bLiveRuleUpdate = true;} else {bLiveRuleUpdate = false;}
    	}
        
        if(source.getText() == "Nudge Rule") {
        	getRuleNudge();setRule();
        }
        
        
        if(source.getText() == "Open Rule Database File") { 
        	try {
        		callPresets(new File("Presets.txt"));
        	} catch (IOException e1) {
        		// TODO Auto-generated catch block
        	}
        }
        
        
        
        
        
       /* "Rule Layout"
        ""
        ""*/
        
        if(source.getText() == "Controls & Interaction") {
        	JOptionPane.showMessageDialog(null, "Left Click:\nIncrease the value of the cell by one.");
        	JOptionPane.showMessageDialog(null, "Right Click:\nDecrease the value of the cell by one.");
        	JOptionPane.showMessageDialog(null, "Middle Click:\nInsert the chosen value into the clicked cell.");
        }
        
        if(source.getText() == "Get & Set") {
        	JOptionPane.showMessageDialog(null, "The 'Get' functions will pull the current rule or neighbourhood into the editor, so it may be reconfigured.\nThe 'Set' functions confirm & apply changes made to a rule or neighbourhood.");
        }
        
        if(source.getText() == "Rule Layout") {
        	JOptionPane.showMessageDialog(null, "Rules encode the actions to perform, after the neighbourhood is assessed.");
        	JOptionPane.showMessageDialog(null, "Rules are represented numerically, and structured as follows:\n\n[-2]           : Begin Rule.\n[#][#][#]  : Rule parameters.\n[     ...    ]  : Many parameter sets are allowed.\n[-3]           : End Rule.");
        	JOptionPane.showMessageDialog(null, "Rule parameters are horizontal sets of three numbers, such as [6][8][1].\n\nThe rule above means: 'If there are 6, 7, or 8 of my neighbours who are not zero, set my value to 1.'");
        }
        
        if(source.getText() == "Neighbour Layout") {
        	JOptionPane.showMessageDialog(null, "Neighbourhoods encode the set of nearby cells to assess, in order to determine the next state of 'this' cell.");
        	JOptionPane.showMessageDialog(null, "Neighbourhoods are represented numerically, and structured as follows:\n\n[1]  [1]  [1]\n[1]  [-1] [1]\n[1]  [1]  [1]\n\nThe value '1' represents a neighbour to assess.\nThe value '-1' represents the current cell, to whom the neighbourhood belongs.");
        	
        }
        
        if(source.getText() == "Import/Export") {
        	JOptionPane.showMessageDialog(null, "Exporting a rule or neighbourhood will read the current state of the Editor, and convert it into a text format.\nThis can be saved for later use.");
        	JOptionPane.showMessageDialog(null, "Importing a rule or neighbourhood will prompt the user to enter an Exported code, and will place the resulting object into the Editor.");
          }
        
    }
 
    
    public void setRule() {
    	/*
    	 *  [ int Start, int End, int val]
    	 * 
     	 */
    	
    	
    	int[][][] intAr = new int[TF.pp.length][TF.pp[0].length][1];
    	for(int i = 0; i < TF.pp.length; i++){
    		for(int j = 0; j < TF.pp[i].length; j++){
        		intAr[j][i][0] = TF.pp[j][i].val;
        	}
    	}
    		
    	int ii=0, jj=0;
    	int ruleLen=0;
    		
    	for(int i = 0; i < TF.pp.length; i++){
    		for(int j = 0; j < TF.pp[i].length; j++){
        		if(intAr[i][j][0] == -2) {ii=i; jj=j;}
        	}
    	}
    		
    	for(int i = 0; i < TF.pp.length; i++){
    		for(int j = 0; j < TF.pp[i].length; j++){
        		if(intAr[i][j][0] == -3) {ruleLen = (j-jj)-1;}
        	}
    	}
    		
    	int[][] arRule = new int[ruleLen][3];
    		
    		
    		
    	for(int i = 0; i < ruleLen; i++) {
    		for(int j = 0; j < arRule[i].length; j++) {
    			arRule[i][j] = intAr[ii+j][jj+i+1][0];
    		}
    	}
    		
    	a.arTF_Ruleset = arRule;
    	
    	String sOutput = "";
    	for(int i = 0; i < ruleLen; i++) {
    		sOutput += ":";
    		for(int j = 0; j < arRule[i].length; j++) {
    			sOutput+=arRule[i][j]+",";
    		}
    	}
    	System.out.println(sOutput);
    	
    }
    
    public void setNeighbourhood() {
    	//set up data array, 
    	int[][][] intAr = new int[TF.pp.length][TF.pp[0].length][1];
    	//and fill it from the editor's data
    	for(int i = 0; i < TF.pp.length; i++){
    		for(int j = 0; j < TF.pp[i].length; j++){
        		intAr[j][i][0] = TF.pp[j][i].val;
        	}
    	}
    	
    	//get the centre cell (-1)
    	int ii=0; 
    	int jj=0;
    		
    	for(int i = 0; i < TF.pp.length; i++){
    		for(int j = 0; j < TF.pp[i].length; j++){
        		if(intAr[i][j][0] == -1) {ii=i;jj=j;}
        	}
    	}
    	
    	//get number of == 1 cells
    	int nbrCount = 0;
    	for(int i = 0; i < TF.pp.length; i++){
    		for(int j = 0; j < TF.pp[i].length; j++){
        		if(intAr[j][i][0] == 1) {
        			nbrCount++;
        		}
        	}
    	}
    		
    	//get 1-cell locations
    	nbrhood = new int[nbrCount][3];
    	int placedNbr = nbrCount;
    		
    	for(int i = 0; i < TF.pp.length; i++){
    		for(int j = 0; j < TF.pp[i].length; j++){
        		if(intAr[i][j][0] == 1) {
        			nbrhood[nbrCount-placedNbr][0]=i;
        			nbrhood[nbrCount-placedNbr][1]=j;
        			nbrhood[nbrCount-placedNbr][2] = 0;
        			placedNbr--;
        		}
        	}
    	}
    		        		
    	//transform cells by (-ii, -jj)
    	for(int i = 0; i < nbrhood.length; i++){
    		nbrhood[i][0]-=ii;
    		nbrhood[i][1]-=jj;
    	}

    	//set the neighbourhood to the one just constructed 
    	a.n.NBH = nbrhood;
    	
    	
    	/*String s = "";
    	
    	for(int i = 0; i < nbrhood.length; i++){
    		s+=":";
    		for(int j = 0; j < nbrhood[i].length; j++){
        		s += nbrhood[i][j] + ",";
        	}
    	}
    	
    	System.out.println(s);*/
    	
    }
    
    public void copyToClip() {
    	int[][][] intAr = new int[TF.pp.length][TF.pp[0].length][1];
    	for(int i = 0; i < TF.pp.length; i++){
    		for(int j = 0; j < TF.pp[i].length; j++){
        		intAr[j][i][0] = TF.pp[j][i].val;
        	}
    	}
    		
    	ML.d.setArray(intAr);
    }
    
    public void pasteFromClip(){
    	if(ML.copyPasteBlockSize == TF.gridSize) {
	    	TF.resetPanels();
	    	int[][][] intAr = ML.d.getArray();
	    	for(int i = 0; i < TF.pp.length; i++){
	    		for(int j = 0; j < TF.pp[i].length; j++){
	        		TF.pp[j][i].setVal(intAr[j][i][0]);
	        	}
	    	}
	    		
	    	TF.updatePanelColors();
    	} else {
    		JOptionPane.showMessageDialog(null, "Cursor Size and Editor Size must be identical.\nEditor: " + TF.gridSize + "\nCursor Tool Size: " + ML.copyPasteBlockSize);
    	}
    }
    
    public void getRule() {
    	TF.resetPanels();

    	int start = 1;
    	int end = start + a.arTF_Ruleset.length +1;
    	
    	TF.pp[1][start].setVal(-2);
    	TF.pp[1][end].setVal(-3);
    	
    	for(int i = 0; i < a.arTF_Ruleset.length; i++){
    		TF.pp[1][i+start+1].setVal(a.arTF_Ruleset[i][0]);
    		TF.pp[2][i+start+1].setVal(a.arTF_Ruleset[i][1]);
    		TF.pp[3][i+start+1].setVal(a.arTF_Ruleset[i][2]);
    	}
    		
    	
    	TF.updatePanelColors();
    }
    
    public void getRuleNudge() {
    	Random r = new Random();
    	TF.resetPanels();

    	int start = 1;
    	int end = start + a.arTF_Ruleset.length +1;
    	
    	TF.pp[1][start].setVal(-2);
    	TF.pp[1][end].setVal(-3);
    	
    	for(int i = 0; i < a.arTF_Ruleset.length; i++){
    		TF.pp[1][i+start+1].setVal(a.arTF_Ruleset[i][0]+(r.nextInt(2)-1));
    		TF.pp[2][i+start+1].setVal(a.arTF_Ruleset[i][1]+(r.nextInt(2)-1));
    		TF.pp[3][i+start+1].setVal(a.arTF_Ruleset[i][2]);
    	}
    		
    	
    	TF.updatePanelColors();
    }
    
    public void exportRule() {
    	/*
    	 *  [ int Start, int End, int val]
    	 * 
     	 */
    	
    	
    	int[][][] intAr = new int[TF.pp.length][TF.pp[0].length][1];
    	for(int i = 0; i < TF.pp.length; i++){
    		for(int j = 0; j < TF.pp[i].length; j++){
        		intAr[j][i][0] = TF.pp[j][i].val;
        	}
    	}
    		
    	int ii=0, jj=0;
    	int ruleLen=0;
    		
    	for(int i = 0; i < TF.pp.length; i++){
    		for(int j = 0; j < TF.pp[i].length; j++){
        		if(intAr[i][j][0] == -2) {ii=i; jj=j;}
        	}
    	}
    		
    	for(int i = 0; i < TF.pp.length; i++){
    		for(int j = 0; j < TF.pp[i].length; j++){
        		if(intAr[i][j][0] == -3) {ruleLen = (j-jj)-1;}
        	}
    	}
    		
    	int[][] arRule = new int[ruleLen][3];
    		
    		
    		
    	for(int i = 0; i < ruleLen; i++) {
    		for(int j = 0; j < arRule[i].length; j++) {
    			arRule[i][j] = intAr[ii+j][jj+i+1][0];
    		}
    	}
    		
    	//a.arTF_Ruleset = arRule;
    	
    	String exportRuleString = "";
    	for(int i = 0; i < ruleLen; i++) {
    		exportRuleString += ":";
    		for(int j = 0; j < arRule[i].length; j++) {
    			exportRuleString+=arRule[i][j]+",";
    		}
    	}
    	System.out.println(exportRuleString);
    	
    	
    	JOptionPane.showInputDialog(null, "This is the text rule. It should be automatically copied to your system clipboard.\nIf not, copy (CTRL-C) it to the local clipboard, which will be cleared on program exit.", exportRuleString);
    	SystemClip sysClip = new SystemClip(); 
    	sysClip.setSystemClip(exportRuleString);
    }
    
    public void importRule() {
    	TF.resetPanels();

    	String str = JOptionPane.showInputDialog(null, "Input Rule", "");
    	//:4,8,0,:3,3,1,:0,1,0, //CGoL
    	int ruleLines = 0;
    	
    	for(int i = 0; i < str.length(); i++) {
    		if(":".equals(str.substring(i, i+1))) {
    			ruleLines++;
    		}
    	}
    	
    	int validColumnCount = 3;
    	
    	int[][] importedRule = new int[ruleLines][validColumnCount];
    	
    	int thisColon = 0;
    	int nextColon = 0;
    	int thisComma = 0;
    	int nextComma = 0;
    	
    	//For each line to be parsed
    	for(int i = 0; i < ruleLines; i++) {
    		//Identify rule line start & end
    		nextColon = str.indexOf(":", thisColon+1);
    		if(nextColon == -1) {nextColon = str.length();}
    		
    		//set up colon debug printer
    		String sOut = "TC:" + thisColon + " NC:" + nextColon+ " sOut: ";
    		//for each character in the range between the current colon and the next colon (start of next line)
    		for(int j = thisColon; j < nextColon; j++) {
    			sOut += str.substring(j, j+1);
    		}
    		//Print the debug string
    		System.out.println(sOut);
    		
    		//count commas
			int commaLines = 0;
	    	for(int j = thisColon; j < nextColon; j++) {
	    		if(",".equals(str.substring(j, j+1))) {
	    			commaLines++;
	    		}
	    	}
	    	
	    	if(commaLines != 3) {
	    		JOptionPane.showMessageDialog(null,"Syntax Error: Only " +validColumnCount+ " filled columns should be used. You used: " + commaLines + " on rule line: " + i + ".");
	    	}
	    	
	    	//Print debug comma string
	    	System.out.println("i"+i+" commas:" + commaLines);
	    	
			//set up comma debug printer
	    	thisComma = thisColon;
			for(int j = 0; j < commaLines; j++) {
				nextComma = str.indexOf(",", thisComma+1);
	    		if(nextComma == -1) {nextComma = str.length();}
	    		
				String sOut2 = "TCmm:" + thisComma + " NCmm:" + nextComma+ " sOut2: ";
				String impRuleParam = "";
				for(int k = thisComma+1; k < nextComma; k++) {
	    			sOut2 += str.substring(k, k+1);
	    			
	    			impRuleParam += str.substring(k, k+1);
	    		}
				System.out.println(sOut2);
				
				//asign to rule array
				importedRule[i][j] = Integer.parseInt(impRuleParam);
				
				//Go for another round
	    		thisComma = nextComma;
			}
			
			//Go for another round
    		thisColon = nextColon;

    	}
    	
		String finalArrayDebugChecker = "";
    		
    	for(int i = 0; i < importedRule.length; i++) {
    		finalArrayDebugChecker += ":";
    		for(int j = 0; j < importedRule[i].length; j++) {
    			finalArrayDebugChecker += importedRule[i][j] + ",";
    		}
    	}
    	System.out.println(finalArrayDebugChecker);
    	
    	String clPrintout = "";
		
    	for(int i = 0; i < importedRule.length; i++) {
    		clPrintout += ":";
    		for(int j = 0; j < importedRule[i].length; j++) {
    			clPrintout += importedRule[i][j] + ",";
    		}
    		System.out.println(clPrintout);
    	}
    	
    	
    	
    	int start = 1;
    	int end = start + ruleLines +1;
    	
    	TF.pp[1][start].setVal(-2);
    	TF.pp[1][end].setVal(-3);
    	
    	a.arTF_Ruleset = new int[(end-start)-1][3];
    	
    	for(int i = 0; i < a.arTF_Ruleset.length; i++){
    		for(int j = 0; j < a.arTF_Ruleset[i].length; j++){
    			//System.out.println("DEBUG:" + " i:"+ i + " j:"+ j + " a.arTF_Ruleset.length:"+ a.arTF_Ruleset.length + " a.arTF_Ruleset[i].length:" + a.arTF_Ruleset[i].length + " end-start:" + (end-start));
	    		TF.pp[j+1][i+start+1].setVal(importedRule[i][j]);
    		}
    	}
    		
    	
    	TF.updatePanelColors();
    	
    	//apply it
    	setRule();
    }
    
    public void importNeighbourhood() {
    	TF.resetPanels();

    	String str = JOptionPane.showInputDialog(null, "Input Neighbourhood", "");
    	
    	//:-1,-1,0,:-1,0,0,:-1,1,0,:0,-1,0,:0,1,0,:1,-1,0,:1,0,0,:1,1,0, //CGoL
    	
    	//:-1,-1,0,
    	//:-1,0,0,
    	//:-1,1,0,
    	//:0,-1,0,
    	//:0,1,0,
    	//:1,-1,0,
    	//:1,0,0,
    	//:1,1,0, //CGoL
    	
    	int ruleLines = 0;
    	
    	for(int i = 0; i < str.length(); i++) {
    		if(":".equals(str.substring(i, i+1))) {
    			ruleLines++;
    		}
    	}
    	
    	//System.out.println(ruleLines);
    	
    	int xyz = 3;
    	
    	int[][] importedNbr = new int[ruleLines][xyz];
    	
    	int thisColon = 0;
    	int nextColon = 0;
    	int thisComma = 0;
    	int nextComma = 0;
    	
    	//For each line to be parsed
    	for(int i = 0; i < ruleLines; i++) {
    		//Identify line start & end
    		nextColon = str.indexOf(":", thisColon+1);
    		if(nextColon == -1) {nextColon = str.length();}
    		
    		//set up colon debug printer
    		/*String sOut = "TC:" + thisColon + " NC:" + nextColon+ " sOut: ";
    		//for each character in the range between the current colon and the next colon (start of next line)
    		for(int j = thisColon; j < nextColon; j++) {
    			sOut += str.substring(j, j+1);
    		}
    		//Print the debug string
    		System.out.println(sOut);*/
    		
    		//count commas
			int commaLines = 0;
	    	for(int j = thisColon; j < nextColon; j++) {
	    		if(",".equals(str.substring(j, j+1))) {
	    			commaLines++;
	    		}
	    	}
	    	
	    	if(commaLines != 3) {
	    		JOptionPane.showMessageDialog(null,"Syntax Error: Three numbers ( [x][y][z] ) should be used. You used: " + commaLines + " on rule line: " + i + ".");
	    	}	    	
	    	
	    	//Print debug comma string
	    	//System.out.println("i"+i+" commas:" + commaLines);
	    	
	    	
			//set up comma debug printer
	    	thisComma = thisColon;
			for(int j = 0; j < commaLines; j++) {
				nextComma = str.indexOf(",", thisComma+1);
	    		if(nextComma == -1) {nextComma = str.length();}
	    		
				//String sOut2 = "TCmm:" + thisComma + " NCmm:" + nextComma+ " sOut2: ";
				String impNbrParam = "";
				for(int k = thisComma+1; k < nextComma; k++) {
	    			//sOut2 += str.substring(k, k+1);
	    			
	    			impNbrParam += str.substring(k, k+1);
	    		}
				//System.out.println(sOut2);
				
				//asign to rule array
				importedNbr[i][j] = Integer.parseInt(impNbrParam);
				
				//Go for another round
	    		thisComma = nextComma;
			}
			/**/
	    	
	    	
			//Go for another round
    		thisColon = nextColon;
			
    	}
    	
    	
    	for(int i = 0; i < importedNbr.length; i++) {
    		for(int j = 0; j < importedNbr[i].length; j++) {
    			System.out.println(importedNbr[i][j] + ",");
    		}
    	}
    	
     	int centCell = ((TF.pp.length/2)-1);
     	
    	for(int i = 0; i < importedNbr.length; i++){
    		TF.pp[importedNbr[i][0]+centCell][importedNbr[i][1]+centCell].setVal(1);
    	}
    		
    	TF.pp[centCell][centCell].setVal(-1);
    	
    	TF.updatePanelColors();
    	
    	//apply it
    	bLiveRuleUpdate = false; 
    	setNeighbourhood();
    }
    
    public void getNeighbourhood() {
    	TF.resetPanels();
     	int centCell = ((TF.pp.length/2)-1);
     	
    	for(int i = 0; i < a.n.NBH.length; i++){
    		TF.pp[a.n.NBH[i][0]+centCell][a.n.NBH[i][1]+centCell].setVal(1);
    	}
    		
    	TF.pp[centCell][centCell].setVal(-1);
    	
    	TF.updatePanelColors();
    }
    
    public void exportNeighbourhood(){
    	//set up data array, 
    	int[][][] intAr = new int[TF.pp.length][TF.pp[0].length][1];
    	//and fill it from the editor's data
    	for(int i = 0; i < TF.pp.length; i++){
    		for(int j = 0; j < TF.pp[i].length; j++){
        		intAr[j][i][0] = TF.pp[j][i].val;
        	}
    	}
    	
    	//get the centre cell (-1)
    	int ii=0; 
    	int jj=0;
    		
    	for(int i = 0; i < TF.pp.length; i++){
    		for(int j = 0; j < TF.pp[i].length; j++){
        		if(intAr[i][j][0] == -1) {ii=i;jj=j;}
        	}
    	}
    	
    	//get number of == 1 cells
    	int nbrCount = 0;
    	for(int i = 0; i < TF.pp.length; i++){
    		for(int j = 0; j < TF.pp[i].length; j++){
        		if(intAr[j][i][0] == 1) {
        			nbrCount++;
        		}
        	}
    	}
    		
    	//get 1-cell locations
    	nbrhood = new int[nbrCount][3];
    	int placedNbr = nbrCount;
    		
    	for(int i = 0; i < TF.pp.length; i++){
    		for(int j = 0; j < TF.pp[i].length; j++){
        		if(intAr[i][j][0] == 1) {
        			nbrhood[nbrCount-placedNbr][0]=i;
        			nbrhood[nbrCount-placedNbr][1]=j;
        			nbrhood[nbrCount-placedNbr][2] = 0;
        			placedNbr--;
        		}
        	}
    	}
    		        		
    	//transform cells by (-ii, -jj)
    	for(int i = 0; i < nbrhood.length; i++){
    		nbrhood[i][0]-=ii;
    		nbrhood[i][1]-=jj;
    	}

    	String s = "";
    	
    	for(int i = 0; i < nbrhood.length; i++){
    		s+=":";
    		for(int j = 0; j < nbrhood[i].length; j++){
        		s += nbrhood[i][j] + ",";
        	}
    	}
    	
    	System.out.println(s);
    	
    	JOptionPane.showInputDialog(null, "This is the text neighbourhood. It should be automatically copied to your system clipboard.\nIf not, copy (CTRL-C) it to the local clipboard, which will be cleared on program exit.", s);
    	SystemClip sysClip = new SystemClip(); 
    	sysClip.setSystemClip(s);
    }
    
    public static void callPresets(File f) throws IOException {
    	if(!f.exists()) {
    		String presetGithubPath = "https://github.com/InfiniteSearchSpace/Automata-Totalistic-Gen-4/tree/master/CellularAutomata/Omniscience";
    		JOptionPane.showInputDialog(null, "Automata Presets not found.\nDownload the 'Presets' plain text file, and place it in the same directory as this program:",presetGithubPath);
    		SystemClip sysClip = new SystemClip(); 
        	sysClip.setSystemClip(presetGithubPath);
    	} else {
	        Desktop desk = Desktop.getDesktop();
	        desk.open(f);
    	}
    }
    
}