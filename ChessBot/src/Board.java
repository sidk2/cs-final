import java.util.ArrayList;

public class Board
{
    private ArrayList<Piece> board;
    private Boolean[][] isOccupied;
    private int value;
    public Board(boolean full) {
        board = new ArrayList<>();
        isOccupied = new Boolean[8][8];
        for(int i = 0; i < 8; i++) {
            isOccupied[i][0] = true;
            isOccupied[i][1] = true;
            isOccupied[i][6] = true;
            isOccupied[i][7] = true;
        }
        
        for(int i = 0; i < 8; i++) {
            board.add( new Pawn(i, 1, true) );
            board.add( new Pawn(i, 6, false) );
        }
        
        board.add( new Rook(0, 0, true) );
        board.add( new Knight(1, 0, true) );
        board.add( new Bishop(2, 0, true));
        board.add( new Queen(3, 0, true) );
        board.add( new King(4, 0, true) );
        board.add( new Bishop(5, 0, true));
        board.add( new Knight(6, 0, true) );
        board.add( new Rook(7, 0, true) );
        
        board.add( new Rook(0, 7, false) );
        board.add( new Knight(1, 7, false) );
        board.add( new Bishop(2, 7, false));
        board.add( new Queen(3, 7, false) );
        board.add( new King(4, 7, false) );
        board.add( new Bishop(5, 7, false));
        board.add( new Knight(6, 7, false) );
        board.add( new Rook(7, 7, false) );

    }
    
    public Board(ArrayList<Piece> board) {
        this.board = board;
        isOccupied = new Boolean[8][8];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                isOccupied[i][j] = false;
            }
        }
        for(int i = 0; i < board.size(); i++) {
            isOccupied[board.get( i ).getLoc().getXPos()][board.get( i ).getLoc().getYPos()] = true;
        }
    }
    
    public ArrayList<Piece> getBoard(){
        return board;
    }
    public int getValue()
    {
    	int val = 0;
		ArrayList<Piece> p = getBoard();
		for(Piece piece: p)
		{
			val+=piece.getValue();
		}
		return val;
    }
    public Boolean[][] getOccupied(){
        return isOccupied;
    }
    
    public Boolean check(Location loc) {
        int x = loc.getXPos();
        int y = loc.getYPos();
        return !isOccupied[x][y];
    }
    public ArrayList<Board> getPossibleMoves( boolean color) {
        for(int i = 0; i < this.getBoard().size(); i++) {
            if(this.getBoard().get( i ).getColor() == color) {
                if(this.getBoard().get( i ).isInCheck( this, i )) {
                    return null;
                }
            }
        }
        
        ArrayList<Board> allNextMoves = new ArrayList<>();
        
        for(int i = 0; i < this.getBoard().size(); i++) {
            //System.out.println(b.getBoard().size());
            if(this.getBoard().get( i ).getColor() == color) {
                ArrayList<Board> temp = this.getBoard().get( i ).findMoves( this, i );
                    
                if(temp == null) {
                    continue;
                }

                for(Board board : temp) {
                    allNextMoves.add( board );
                }
                
            }
        }
        return allNextMoves;
    }
	public Board getBestBoard(boolean color)
	{
		Board best = null;
		for (Board board:(new AllMoves(this)).findAllMoves(this, color))
		{
			if(best == null)
			{
				best = board;
			}
			else
			{
				if (best.getValue()<board.getValue())
				{
					best = board;
				}
			}
		}
		return best;
	}
    public void printBoard() {
        char[][] board = new char[8][8];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = 'E';
            }
        }
        ArrayList<Piece> pieces = getBoard();
        //System.out.println(pieces == null);
        for(Piece i : pieces) {
            
            if(i instanceof Pawn) {
                board[i.getLoc().getXPos()][i.getLoc().getYPos()] = 'P';
            }
            if(i instanceof Knight) {
                board[i.getLoc().getXPos()][i.getLoc().getYPos()] = 'N';
            }
            if(i instanceof Bishop) {
                board[i.getLoc().getXPos()][i.getLoc().getYPos()] = 'B';
            }
            if(i instanceof Rook) {
                board[i.getLoc().getXPos()][i.getLoc().getYPos()] = 'R';
            }
            if(i instanceof Queen) {
                board[i.getLoc().getXPos()][i.getLoc().getYPos()] = 'Q';
            }
            if(i instanceof King) {
                board[i.getLoc().getXPos()][i.getLoc().getYPos()] = 'K';
            }
//            if(i.getColor() == true) {
//                board[i.getLoc().getXPos()][i.getLoc().getYPos()] = 'W';
//            }
//            else {
//                board[i.getLoc().getXPos()][i.getLoc().getYPos()] = 'B';
//            }
            
            //board[i.getLoc().getXPos()][i.getLoc().getYPos()] = "(" + i.getLoc().getXPos() + " , " + i.getLoc().getYPos() + ")";
        }
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}