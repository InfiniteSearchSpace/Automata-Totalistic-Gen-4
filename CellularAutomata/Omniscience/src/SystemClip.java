import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;


public class SystemClip implements ClipboardOwner{

	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
		// TODO Auto-generated method stub
		
	}

    public void setSystemClip(String s){
        StringSelection sSel = new StringSelection(s);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(sSel, this);
    }
    
}
