

import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;


public class mwl implements MouseWheelListener{

	//create containers for reference objects
	Main m;
	ml hisMouseListener;
	
	//constructor
    public mwl(Main mm, ml hisML) {
    	//System.out.println("mwl");
    	m=mm;
    	hisMouseListener=hisML;
    	
        m.addMouseWheelListener(this);
    }
    
    //call function from ml class that changes the subfunction
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		hisMouseListener.mouseWheelScrolled(e.getWheelRotation());
	}
    
    
}