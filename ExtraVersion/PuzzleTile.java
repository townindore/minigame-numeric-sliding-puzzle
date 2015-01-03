import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A PuzzleTile class; this class inherits from JPanel.
 *
 * The tiles do not have any numbers painted on them yet.
 * The only indication you will be able to visibly see at this point
 * is that the 0 tile should be white and the rest gray.
 * 
 * @author   Laurissa
 * @version  1.0
 * @author   Paula
 * @version  1.1
 */

public class PuzzleTile extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tileNumber;
	private int fontsize = 30;
	
	public void setFontSize(int size){this.fontsize=size;};
	
    /**
     * This constructor takes in the number you would like
     * displayed on this panel and saves it as the String
     * instance variable tileNumber.
     * Note that you will still have to paint it onto the panel.
     * You should do that by overriding the method:
     *     public void paintComponent(Graphics g){}.
     *
     * @param number The number to paint on the panel.
     */
	
    public PuzzleTile(int number) {
        super();
        if (number == 0) {
            this.setBackground(Color.white);
        	}
        else {
            this.setBackground(Color.darkGray);
        	}
        //Add MouseListen to the tiles
        this.addMouseListener(listener);
        this.setTileNumber("" + number);
	}
    
    //Defining mouse listener to the tile
    PuzzleTile tile = this;
	MouseListener listener= new MouseListener(){
		@Override
		public void mouseClicked(MouseEvent arg0) {
			
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
			if (tile.getTileNumber()!=0)tile.setBackground(Color.GRAY);
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			if (tile.getTileNumber()!=0)tile.setBackground(Color.darkGray);
		}
	};
    
    //This method paints tileNumber on the tile panel
    public void paintComponent(Graphics g){
        int tnum = Integer.parseInt(tileNumber);
    	super.paintComponent(g);
    	g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, this.fontsize));
        int wid = this.getWidth();
        int hei = this.getHeight();
        if (tnum!=0)g.drawString(""+tnum,wid/2,hei/2);
    }
    
	/**
	 * @param tileNumber the tileNumber to set
	 */
	public void setTileNumber(String tileNumber) {
		this.tileNumber = tileNumber;
	}

	/**
	 * @return the tileNumber
	 */
	public int getTileNumber() {
		return Integer.parseInt(tileNumber);
	}

    // You will need to provide more methods to interact with your 
    // main program. 
}