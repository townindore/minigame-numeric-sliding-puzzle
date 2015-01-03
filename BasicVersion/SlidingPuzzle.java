import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SlidingPuzzle extends JFrame {
	private JPanel jContentPane = null;
	private int dms = 3;
	private PuzzleTile tilesloc[][] = null;//This is the tiles' location
	private PuzzleTile tilesseq[]= null;//This is the tiles' sequence
	int blankx=-1;int blanky=-1;//This is the location of blank tile
	
	public int getDMS(){return this.dms;}//getter for Dimension Value
	
	public static void main(String[] args){
		SlidingPuzzle puzzle = new SlidingPuzzle();
		puzzle.setVisible(true);
		puzzle.dms=3;
		int dms = puzzle.dms;
		puzzle.tilesloc = new PuzzleTile[dms][dms];
		puzzle.tilesseq = new PuzzleTile[dms*dms];
		
		//Initialize the tiles in the sequence
		for (int i=0;i<dms*dms;i++)puzzle.tilesseq[i] = new PuzzleTile(i);
		
		//Shuffle all the tiles
		puzzle.shuffleTiles(puzzle.tilesseq);
		/*The following insures that the parity of InversedPair is EVEN
		 *So that the Sliding Puzzle Game can have a valid solution.
		 */
		while(!puzzle.parityCheck(puzzle.tilesseq)){ 
			PuzzleTile copy = null;
			int m = (int)(Math.random()*puzzle.tilesseq.length);
			int n = (int)(Math.random()*puzzle.tilesseq.length);
			copy = puzzle.tilesseq[m];
			puzzle.tilesseq[m] = puzzle.tilesseq[n];
			puzzle.tilesseq[n] = copy;
		};
		//Add the tiles to the frame panel and make them visible
		for (int j=0;j<dms;j++){	
			for (int k=0;k<dms;k++){
					puzzle.tilesloc[j][k]=puzzle.tilesseq[j*dms+k];
					puzzle.add(puzzle.tilesloc[j][k]);
					puzzle.tilesloc[j][k].setFontSize(39-3*dms);
					puzzle.tilesloc[j][k].setBounds(k*(400/dms)+2*k,j*(380/dms)+2*j,400/dms,380/dms);
					puzzle.tilesloc[j][k].setVisible(true);
					}
			}
		
		//Add MouseListener to the tiles to detect a blank tile near by
		for (int j=0;j<dms;j++){	
			for (int k=0;k<dms;k++){
						final PuzzleTile tile = puzzle.tilesloc[j][k];
						final SlidingPuzzle puzzlegame = puzzle;
						final int m = j;final int n = k;
						MouseListener dectectblank = new MouseListener(){
						public void mousePressed(MouseEvent arg0) {}
						public void mouseReleased(MouseEvent arg0){
							//checks if a blankTile is adjacent and commute if does
							PuzzleTile blankt = null;
							blankt = puzzlegame.checkBlankTile(tile,m,n);
							if(blankt!=null)puzzlegame.commuteTiles(tile, blankt);
							
							//check if player has win and give response
							puzzlegame.checkWin();
						}
						public void mouseClicked(MouseEvent e) {}
						public void mouseEntered(MouseEvent e){}
						public void mouseExited(MouseEvent e){}
					};
					puzzle.tilesloc[j][k].addMouseListener(dectectblank);
			}
		}
	}
	
	//This Method shuffle all the tiles
	public void shuffleTiles(PuzzleTile []tile){
		int length = tile.length;
		PuzzleTile tilecopy = null;
		for (int i=0;i<length; i++) {  
			int r = (int)(Math.random()*length);
			tilecopy = tile[i];
			tile[i] = tile[r];  
			tile[r] = tilecopy;
			}
	}
		
	//This Method Check the parity check of the inversedPari Tiles
	public boolean parityCheck(PuzzleTile []tile){
		int v = 0;
		int length = tile.length;
		int zeroIndex = 0;
		for(int i=0; i<length; i++){  
			for(int j=i+1; j<length; j++){  
			if(tile[i].getTileNumber()>tile[j].getTileNumber()){  
				v++;  
				}  
			}  
			if(tile[i].getTileNumber() == 0) zeroIndex = i;  
		}
		int lineIndex = zeroIndex%this.dms+1;
		int colIndex = zeroIndex/this.dms+1;
		return (lineIndex + colIndex + v) % 2 == 0;
	}
	
	
	
	//This Method Check if there's any blank tile near the checked tile
	public PuzzleTile checkBlankTile(PuzzleTile tile,int x,int y){
		PuzzleTile blanktile = null;
		blankx=-1;blanky=-1;
		//1. Checking the Left Edge
		if(x==0){
			//1.1 When checked tile at the Up Corner
			if (y==0){
				if(tilesloc[x][y+1].getTileNumber()==0){blankx=x;blanky=y+1;}
				else if(tilesloc[x+1][y].getTileNumber()==0){blankx=x+1;blanky=y;}
			}
			//1.2 When checked tile at the Bottom Corner
			else if(y==dms-1){
				if(tilesloc[x][y-1].getTileNumber()==0){blankx=x;blanky=y-1;}
				else if(tilesloc[x+1][y].getTileNumber()==0){blankx=x+1;blanky=y;}
			}
			//1.3 When at the edge
			else {
				if(tilesloc[x][y+1].getTileNumber()==0){blankx=x;blanky=y+1;}
				else if(tilesloc[x][y-1].getTileNumber()==0){blankx=x;blanky=y-1;}
				else if(tilesloc[x+1][y].getTileNumber()==0){blankx=x+1;blanky=y;}
			}
		}
		
		//2. Checking the Right Edge
		else if(x==dms-1){
			//2.1 When checked tile at the Up Corner
			if (y==0){
				if(tilesloc[x-1][y].getTileNumber()==0){blankx=x-1;blanky=y;}
				else if(tilesloc[x][y+1].getTileNumber()==0){blankx=x;blanky=y+1;}
			}
			//2.2 When checked tile at the Down Corner
			else if(y==dms-1){
				if(tilesloc[x-1][y].getTileNumber()==0){blankx=x-1;blanky=y;}
				else if(tilesloc[x][y-1].getTileNumber()==0){blankx=x;blanky=y-1;}
			}
			//2.3 When at the edge
			else {
				if(tilesloc[x][y+1].getTileNumber()==0){blankx=x;blanky=y+1;}
				else if(tilesloc[x][y-1].getTileNumber()==0){blankx=x;blanky=y-1;}
				else if(tilesloc[x-1][y].getTileNumber()==0){blankx=x-1;blanky=y;}
			}
		}
		
		//3. Checking the Up Edge Middle
		else if(y==0&&x!=0&&x!=dms-1){
				if(tilesloc[x-1][y].getTileNumber()==0){blankx=x-1;blanky=y;}
				else if(tilesloc[x+1][y].getTileNumber()==0){blankx=x+1;blanky=y;}
				else if(tilesloc[x][y+1].getTileNumber()==0){blankx=x;blanky=y+1;}
			}
		//4. Checking the Down Edge Middle
		else if(y==dms-1&&x!=0&&x!=dms-1){
				if(tilesloc[x-1][y].getTileNumber()==0){blankx=x-1;blanky=y;}
				else if(tilesloc[x+1][y].getTileNumber()==0){blankx=x+1;blanky=y;}
				else if(tilesloc[x][y-1].getTileNumber()==0){blankx=x;blanky=y-1;}
			}
		//5. Checking Anywhere in the center
		else
			{	if(tilesloc[x][y-1].getTileNumber()==0){blankx=x;blanky=y-1;}
				else if(tilesloc[x][y+1].getTileNumber()==0){blankx=x;blanky=y+1;}
				else if(tilesloc[x-1][y].getTileNumber()==0){blankx=x-1;blanky=y;}
				else if(tilesloc[x+1][y].getTileNumber()==0){blankx=x+1;blanky=y;}
			}
		if(blankx>=0&&blanky>=0){blanktile=tilesloc[blankx][blanky];return blanktile;}
		else return null;
	}
	
	//This method check if the player wins
	public void checkWin(){
		boolean isWin=true;
		for (int j=0;j<dms;j++){
			for (int k=0;k<dms;k++){
				if(tilesloc[j][k].getTileNumber()!=j*dms+k)isWin=false;
			}
		}
		if(isWin){
			int response = JOptionPane.showConfirmDialog(this,
			"You have solved the Sliding Puzzle!\nWould you like to play another round?",
			"Congratulations!",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			System.out.print(response);
			if (response==0){
				for(int i=0; i<dms; i++){  
					for(int j=0; j<dms; j++){
						this.remove(tilesloc[i][j]);
						this.repaint();
						this.validate();
						}
					}
				this.tilesloc=new PuzzleTile[dms][dms];
				//Initialize the tiles in the sequence
				for (int i=0;i<dms*dms;i++)this.tilesseq[i] = new PuzzleTile(i);
				
				//Shuffle all the tiles again
				this.shuffleTiles(this.tilesseq);
				
				/*The following insures that the parity of InversedPair is EVEN
				 *So that the Sliding Puzzle Game can have a valid solution.
				 */
				while(!this.parityCheck(this.tilesseq)){ 
					int length = this.tilesseq.length;
					int r = (int)(Math.random()*length);
					int s = (int)(Math.random()*length);
					PuzzleTile copy = null;
					copy = this.tilesseq[r];
					this.tilesseq[r] = this.tilesseq[s];
					this.tilesseq[s] = copy;;
				};
				//Add the tiles to the frame panel and make them visible
				for (int j=0;j<dms;j++){	
					for (int k=0;k<dms;k++){
							this.tilesloc[j][k]=this.tilesseq[j*dms+k];
							this.add(this.tilesloc[j][k]);
							this.tilesloc[j][k].setBounds(0+k*(390/dms)+2*k,0+j*(380/dms)+2*j,390/dms,380/dms);
							this.tilesloc[j][k].setVisible(true);
							}
					}
				
				//Add MouseListener to the tiles to detect a blank tile near by
				for (int j=0;j<dms;j++){	
					for (int k=0;k<dms;k++){
								final PuzzleTile tile = this.tilesloc[j][k];
								final SlidingPuzzle puzzlegame = this;
								final int m = j;final int n = k;
								MouseListener dectectblank = new MouseListener(){
								public void mousePressed(MouseEvent arg0) {}
								public void mouseReleased(MouseEvent arg0){
									//checks if a blankTile is adjacent and commute if does
									PuzzleTile blankt = null;
									blankt = puzzlegame.checkBlankTile(tile,m,n);
									if(blankt!=null)puzzlegame.commuteTiles(tile, blankt);
									
									//check if player has win and give response
									puzzlegame.checkWin();
								}
								public void mouseClicked(MouseEvent e) {}
								public void mouseEntered(MouseEvent e){}
								public void mouseExited(MouseEvent e){}
							};
							this.tilesloc[j][k].addMouseListener(dectectblank);
					}
				}
				
			}else if (response==1)System.exit(1);
		};
	}
	
	//This method commute the two tiles
	public void commuteTiles(PuzzleTile tile1,PuzzleTile tile2){
		Color bgccopy =tile1.getBackground();
		int tilenum = tile1.getTileNumber();
		tile1.setBackground(tile2.getBackground());
		tile1.setTileNumber(""+tile2.getTileNumber());
		tile2.setBackground(bgccopy);
		tile2.setTileNumber(""+tilenum);
	}
	
	/**
	 * This is the default constructor
	 */
	public SlidingPuzzle() {
		super();
		initialize();
	}
	
	//This method initializes the program
	private void initialize() {
		this.setSize(400, 400);
		this.setContentPane(getJContentPane());
		this.setTitle("Numeric Sliding Puzzle");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(400, 150);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
		}
		return jContentPane;
	}
}
