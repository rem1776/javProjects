package minesweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author ryan
 * 11/15/15
 * main class that renders game
 */
public class Minesweeper extends JPanel implements MouseListener
{    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//board object containing arrays for various values for minefield
    Board board;
    
    //int for number of flags
    private int flags;
    
    //sets attributes, adds listener, and instantiates board class
    public Minesweeper()
    {        
        this.setSize(GameWindow.WINDOW_SIZE, GameWindow.WINDOW_SIZE);
        this.setVisible(true);
        this.addMouseListener(this);       
        
        board = new Board();
        flags = 0;
    }
    //runs window class which adds components to frame
    public static void main(String[] args) 
    {
        new GameWindow();
        
    }
    //paints board on panel
    public void paint(Graphics g)
    {
        int tileSize = GameWindow.WINDOW_SIZE / Board.SIZE;
        int cornerX,cornerY;
        
        //set font size
        g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 20));
        
        g.setColor(Color.WHITE);
        g.fillRect(0, GameWindow.WINDOW_SIZE - 30 , GameWindow.WINDOW_SIZE , 100);

        g.setColor(Color.BLACK);
        g.drawString("Bombs left: " + (Board.MINES - flags), GameWindow.WINDOW_SIZE/2 - 100 , GameWindow.WINDOW_SIZE +10);
        
        //draws blue blocks for each value in board array with a 1 pixel border
        for(int x=1; x < Board.SIZE + 1 ; x++)
            for(int y=1; y < Board.SIZE + 1; y++)
            {
               //finds top right corner for each tile
               cornerX = (x-1) * tileSize;
               cornerY = (y-1) * tileSize;
               
               if(board.getIsClicked()[x][y])
               {
                   //clear block
                    g.setColor(Color.white);
                    g.fillRect(cornerX, cornerY, tileSize-1, tileSize-1);
                    //draw value if no adjacent mines
                    g.setColor(Color.black);
                    if(board.getBoardArray()[x][y] != 0)
                    {
                    	if(board.getBoardArray()[x][y] == -1)
                    		g.drawString("*", cornerX + 20 , cornerY + 20);    
                    	else
                    		g.drawString("" +  board.getBoardArray()[x][y], cornerX + 20 , cornerY + 20);    
                    }
               }
               else
               {
                   g.setColor(Color.blue);
                   g.fillRect((x-1) * tileSize, (y-1) * tileSize, tileSize-1, tileSize-1); 
                   
                   if(board.getFlagged(x, y))
                   {
                        g.setColor(Color.red);
                        g.fillPolygon(new int[]{ cornerX, cornerX , cornerX + tileSize},
                                new int[] {cornerY, cornerY + tileSize, cornerY + (tileSize /2)} , 3);
                   }
               }
            }
    }
    //finds block clicked on when mouse is clicked
    @Override
    public void mousePressed(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        
        //divides the x and y of the click location by the size of the tiles(in pixels) 
        x = x / (GameWindow.WINDOW_SIZE / Board.SIZE);
        y = y / (GameWindow.WINDOW_SIZE / Board.SIZE);
        
        //adds one to each value to account for extra rows and cols
        x++;
        y++;
        //if left click clear block
        if(e.getButton() == MouseEvent.BUTTON1 && !board.getIsClicked()[x][y])
        {
            board.setClicked(x, y);
            board.setFlagged(x, y , false);

        }
        
        //if right click set block as flagged and exit method
        if(e.getButton() == MouseEvent.BUTTON3 )
        {
            if(!board.getIsClicked()[x][y] && board.getFlagged(x, y))
            {
                board.setFlagged(x, y, false);
                flags--;
            }
            else if(!board.getIsClicked()[x][y] )
            {
                board.setFlagged(x, y, true);
                flags++;
            }
            
            repaint();
            return;
        }
        
        //update board
        repaint();
          
        //if bomb is clicked on, show game over and exit game
        if(board.getBoardArray()[x][y] == -1)
        {   
            //asks user if wants to play again
            if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Game Over... Play Again?", "", JOptionPane.YES_NO_OPTION))
            {
                flags = 0;
                board.setUpBoard();
                repaint();
            }
            else
                System.exit(0);
            
        }
        //if the amount of cleared blocks is the amount of blocks that arent mines
        if(board.checkWin())
            if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "You Won in "+ board.getTime() +" seconds... Play Again?", "", JOptionPane.YES_NO_OPTION))
            {
                //resets flag count and resets board
                flags = 0;
                board.setUpBoard();
                repaint();
            }
        	//exit game
            else
            {
                System.exit(0);
            }
   
        
    }
    @Override
    public void mouseReleased(MouseEvent me) {        
    }
    @Override
    public void mouseEntered(MouseEvent me) {       
    }
    @Override
    public void mouseExited(MouseEvent me) {    
    }
    @Override
    public void mouseClicked(MouseEvent me) {
    }
    
    
}
