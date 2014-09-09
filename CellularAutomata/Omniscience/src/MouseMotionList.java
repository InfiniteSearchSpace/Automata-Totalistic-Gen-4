
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


public class MouseMotionList implements MouseMotionListener{

	ml MML;
	Main mm;
	
	public MouseMotionList(Main m, ml mml){
        mm = m;
        MML = mml;
        m.addMouseMotionListener(this);
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getModifiers() == 16){
			MML.mousePressed(null);
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
