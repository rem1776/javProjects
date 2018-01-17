package minesweeper;

import java.util.Random;

public class Board
{
    //width and length tiles in board(its a square)
    public static final int SIZE = 16;
    
    //number of mines on board
    public static final int MINES = 40;
    
    //count of cleared mines 
    private int count;
    
    //starting time
    private double time;
    
    //2 dimensional array for mines and numbers
    // -1 is mine, 0 is empty, 1 is 1 mine nearby etc..
    int[][] boardArray;
    
    //2d bool array for if a block is clicked
    boolean[][] isClicked;
    
    //2d bool array for flagged blocks
    boolean[][] flagged;
    
    
    public Board()
    {
        boardArray = new int[SIZE +2][SIZE +2];
        isClicked = new boolean[SIZE + 2][SIZE +2];
        flagged = new boolean[SIZE + 2][SIZE + 2];
              
        setUpBoard();
    }
    public void setUpBoard()
    {
        clearBoard();
        placeMines();
        findNums();
        
        time = System.nanoTime();
        count = 0;
        
    }
    public void placeMines()
    {
        Random g = new Random();
        
        for(int i=0; i < MINES;i++)
        {
            int row = g.nextInt(SIZE-1) + 1;
            int col = g.nextInt(SIZE-1) + 1;
            
            //while the random block is already a mine find a new rand block
            while(boardArray[row][col] == -1)
            {
                row = g.nextInt(SIZE-1) +1;
                col = g.nextInt(SIZE-1) +1;
            }
            //set block as mine
            boardArray[row][col] = -1;
        }
    }
    //finds number of adjacent mines
    public void findNums()
    {
        for(int x=1; x < SIZE + 1; x++)
            for(int y=1; y < SIZE + 1 ; y++)
            {
                //if block is not a bomb
                if(boardArray[x][y] != -1)
                {
                    //checks adjacent blocks and adds 1 for each bomb
                    for(int j= -1; j < 2 ; j++)
                        for(int k= -1; k < 2; k++)
                        {
                            if(boardArray[x + j][y + k] == -1)
                                boardArray[x][y]++;
                        }
                }
                    
            }
    }
    //sets every value on board to zero and every clicked to false
    //also sets border values outside screen to ten
    public void clearBoard()
    {
        for(int x=0; x < boardArray.length; x++)
            for(int y=0; y < boardArray[x].length; y++)
            {
                boardArray[x][y] = 0;
                flagged[x][y] = false;
                isClicked[x][y] = false;
            }
        
        for(int j=0; j < boardArray.length; j++)
        {
            boardArray[0][j] = 9;
            boardArray[boardArray.length - 1][j] = 9;
            
            boardArray[j][0] = 9;
            boardArray[j][boardArray.length - 1] = 9;
        }
        
        
    }
    public int[][] getBoardArray()
    {
        return boardArray;
    }
    
    public boolean[][] getIsClicked()
    {
        return isClicked;
    }
    //@param coordinates for clicked block
    public void setClicked(int x, int y)
    {
        isClicked[x][y] = true;
        count++;
        
        if(boardArray[x][y] == 0)
        {
            openNeighbors(x, y);
        }
    }
    //opens blocks neighboring empty blocks using recursion
    public void openNeighbors(int x, int y) 
    {
        for(int j= -1; j < 2 ; j++)
            for(int k= -1; k < 2; k++)
            {
                //doesnt check original block
                if(!(j == 0 && k == 0))
                {
                    if(boardArray[x + j][y + k] >= 0 && boardArray[x + j][y + k] != 9 && !isClicked[x+j][y+k])
                    {
                        setClicked(x+j, y+k);
                    }
                    
                }
                
            }
    }
    public void setFlagged(int x, int y, boolean flag)
    {
        flagged[x][y] = flag;      
    }
    public boolean getFlagged(int x, int y)
    {
        return flagged[x][y];
    }
    public boolean checkWin()
    {
       //if count of cleared blocks is less than board size minus # of mines user wins
       if(count == (SIZE*SIZE) - (MINES))
       {
           getTime();
           return true;
       }
       else
           return false;
    }

    public int getTime() 
    {
        return (int) ( (System.nanoTime() - time) * Math.pow(10, -9));
    }
}