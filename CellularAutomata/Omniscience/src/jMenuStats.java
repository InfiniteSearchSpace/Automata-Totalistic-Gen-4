
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
 
public class jMenuStats implements ActionListener {

	StatisticFrame SF;
	ml ML;
	automataLib a;
		
    public jMenuStats(StatisticFrame SFrame, ml mML, automataLib aa) {
    	ML = mML;
    	SF = SFrame;
        SF.setJMenuBar(createMenuBar());
        a=aa;
	}

	public JMenuBar createMenuBar() {

		JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        
        //menu bar
        menuBar = new JMenuBar();
        
        //create new menu
        menu = new JMenu("View");
        menu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Absolute");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Relative");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu.addSeparator();
        
        menuItem = new JMenuItem("Clear Data");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        
        //create new menu
        menu = new JMenu("Graph Target");
        menu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Population");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("New Births");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Frequency");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        //create new menu
        menu = new JMenu("Help");
        menu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Views");
        menuItem.addActionListener(this);
        menu.add(menuItem); 
        
        menuItem = new JMenuItem("Target");
        menuItem.addActionListener(this);
        menu.add(menuItem);
    	
        return menuBar;
    }
 
    public void actionPerformed(ActionEvent e) {
    	
        JMenuItem source = (JMenuItem)(e.getSource());
  
        if(source.getText() == "Absolute") 			{SF.dp.viewType = 1;} 	
        if(source.getText() == "Relative") 			{SF.dp.viewType = 0;}  
         
        if(source.getText() == "Population") 		{SF.dp.targetType = 0;}   
        if(source.getText() == "New Births") 		{SF.dp.targetType = 1;}   
        if(source.getText() == "Frequency") 		{SF.dp.targetType = 2;}  

        if(source.getText() == "Views") 		{JOptionPane.showMessageDialog(null, "Absolute: Remember the highest value reached, even when the current graph value range is far from it.\nRelative: Fit the graph to the full space allowed, by using the highest value on the graph only.");}   
        if(source.getText() == "Target") 		{JOptionPane.showMessageDialog(null, "Population: Count each live cell.\nNew Births: Only count cells with the value 1.\nFrequency: Plots the frequency of Age-Values of cells in the specified range");}   
        
        if(source.getText() == "Clear Data") {SF.dp.clearGraph();}
    
    }
 
    
   
    
}