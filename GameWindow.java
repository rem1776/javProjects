package minesweeper;

import javax.swing.JFrame;

public class GameWindow extends JFrame
{   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static int WINDOW_SIZE = 600;
    
    public GameWindow()
    {
        //sets name of window
        super("Java Minesweeper");
        //sets attributes of window
        this.setSize(WINDOW_SIZE - 7 , WINDOW_SIZE + 50);       //adds 25 to account for title panel
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        //declares instantiates and adds main class
        Minesweeper game = new Minesweeper();
        this.add(game);
    }
   
    
}