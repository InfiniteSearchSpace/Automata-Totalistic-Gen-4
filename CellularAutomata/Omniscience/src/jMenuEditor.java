
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        menuItem = new JMenuItem("--> Paste From Clip");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Copy To Clip -->");
        menuItem.addActionListener(this);
        menu.add(menuItem); 
        
        menu = new JMenu("Rule Config");
        menu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        menuBar.add(menu);
        
        //Populate menu
        menuItem = new JMenuItem("--> Get Neighbourhood");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("--> Get Rule");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu.addSeparator();
        
        menuItem = new JMenuItem("Set Neighbourhood -->");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Set Rule -->");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu.addSeparator();
        
        menuItem = new JMenuItem("Import Rule [WIP!]");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Toggle Live Rule Updating");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        return menuBar;
    }
 
    public void actionPerformed(ActionEvent e) {
    	
        JMenuItem source = (JMenuItem)(e.getSource());
  
        
        if(source.getText() == "Erase Panels") 				{bLiveRuleUpdate = false;TF.resetPanels();} 	
        
        if(source.getText() == "--> Get Neighbourhood") 	{bLiveRuleUpdate = false;getNeighbourhood();} 	
        
        if(source.getText() == "--> Get Rule") 				{getRule();} 
        
        if(source.getText() == "--> Paste From Clip") 		{bLiveRuleUpdate = false;pasteFromClip();} 	
        
        if(source.getText() == "Copy To Clip -->") 			{copyToClip();} 	

        if(source.getText() == "Set Neighbourhood -->") 	{bLiveRuleUpdate = false; setNeighbourhood();}

        if(source.getText() == "Set Rule -->") 				{setRule();}
        
        if(source.getText() == "Import Rule") 				{importRule();}
        
        if(source.getText() == "Toggle Live Rule Updating") {
        	 if(bLiveRuleUpdate == false) {bLiveRuleUpdate = true;} else {bLiveRuleUpdate = false;}
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
    	int[][][] intAr = new int[TF.pp.length][TF.pp[0].length][1];
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

    	a.n.NBH = nbrhood;
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
    	TF.resetPanels();
    	int[][][] intAr = ML.d.getArray();
    	for(int i = 0; i < TF.pp.length; i++){
    		for(int j = 0; j < TF.pp[i].length; j++){
        		TF.pp[j][i].setVal(intAr[j][i][0]);
        	}
    	}
    		
    	TF.updatePanelColors();
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
    
    public void importRule() {
    	TF.resetPanels();

    	String str = JOptionPane.showInputDialog(a.m, "Input Rule", ":,");
    	//:4,8,0,:3,3,1,:0,1,0, //CGoL
    	int ruleLines = 0;
    	
    	for(int i = 0; i < str.length(); i++) {
    		if(":".equals(str.substring(i, i+1))) {
    			ruleLines++;
    		}
    	}
    	
    	int[][] importedRule = new int[ruleLines][3];
    	
    	for(int i = 0; i < str.length(); i++) {
    		//Integer.parseInt(str.substring(i*3, i*3+1));
    		
    		//set 'starting point' index to the first :
    		//find the number between : and the first ,
    		//find the number between first , and second ,
    		//find the number between second , and third ,
    		//on encountering next : move the 'starting point' index to it
    		
    	/*	importedRule[i][0] = ;
    		importedRule[i][1] = ;
    		importedRule[i][2] = ;*/
    	}
		    	
    	int start = 1;
    	int end = start + ruleLines +1;
    	
    	TF.pp[1][start].setVal(-2);
    	TF.pp[1][end].setVal(-3);
    	
    	for(int i = 0; i < a.arTF_Ruleset.length; i++){
    		TF.pp[1][i+start+1].setVal(importedRule[i][0]);
    		TF.pp[2][i+start+1].setVal(importedRule[i][1]);
    		TF.pp[3][i+start+1].setVal(importedRule[i][2]);
    	}
    		
    	
    	TF.updatePanelColors();
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
    
}