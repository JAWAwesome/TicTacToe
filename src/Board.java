// A Jared Wagner Production

import java.util.Random;

public class Board {
    // Declerations
    private int[][] grid = {{0,0,0},{0,0,0},{0,0,0}};
    Random num = new Random();
    private int p1S = 0;
    private int p2S = 0;
    private int moves = 0;
    private char p1 = '1';
    private char p2 = '2';
    private char bk = '0';

    // Setup utils
    private int getp1S() {
        return p1S;
    }

    private int getp2S() {
        return p2S;
    }

    public int getup() {
        return num.nextInt(2);
    }

    public void clear() {
        grid[0][0]=grid[0][1]=grid[0][2]=grid[1][0]=grid[1][1]=grid[1][2]=grid[2][0]=grid[2][1]=grid[2][2]=0;
        moves = 0;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setPlayers(char setp1, char setp2, char setbk) {
        p1 = setp1; p2 = setp2; bk = setbk;
    }

    // Check the space
    private boolean checkSpace(int[] pos, int p) {
        // Check if open
        if (grid[pos[0]][pos[1]]==0) {
            grid[pos[0]][pos[1]]=p;
            moves++;
            return true;
        }
        else {
            return false;
        }
    }

    // AI Looker
    public boolean view(int[] pos) {
        if (grid[pos[0]][pos[1]]==0) {
            return true;
        }
        else {
            return false;
        }
    }

    // Check for score
    private int checkScore() {
        // Winning player
            // 0 None
            // 1 P1
            // 2 P2
            // 3 Tie
        int p = 0;

        // Across a row
        for(int[] row: grid) {
            if (row[0]==row[1]&&row[1]==row[2]&&row[0]!=0) {
                p = row[0];
            }
        }

        // Across a column
        for (int i = 0; i < 3; i++) {
            if (grid[0][i]==grid[1][i]&&grid[1][i]==grid[2][i]&&grid[0][i]!=0) {
                p = grid[0][i];
            }
        }

        // Cross
        if (grid[0][0]==grid[1][1]&&grid[1][1]==grid[2][2]&&grid[0][0]!=0) {
            p = grid[0][0];
        }
        if (grid[0][2]==grid[1][1]&&grid[1][1]==grid[2][0]&&grid[0][2]!=0) {
            p = grid[0][2];
        }

        // Tie
        if (moves>8&&p==0) {
            p = 3;
        }

        return p;
    }

    // Make a move
    public String move(int[] pos, int p) {
        if(checkSpace(pos,p)) {
            int result = checkScore();
            String out = "";
            switch (result) {
                case 0: out = "Next Move"; break;
                case 1: out = "Player 1 Won"; p1S++; break;
                case 2: out = "Player 2 Won"; p2S++; break;
                case 3: out = "Game resulted in a tie"; break;
            }
            return out;
        }
        else {
            return "Already taken";
        }
    }

    // Print Scores
    private String scores() {
        return "Player 1's Score: " + p1S + "\nPlayer 2's Score: " + p2S;
    }

    // Print Grid
    public String grid() {
        String out = "\n";
        for (int row[]: grid) {
            for (int col: row) {
                if (col==1) {
                    out = out + p1 + "\t";
                } else if (col==2) {
                    out = out + p2 + "\t";
                } else {
                    out = out + bk + "\t";
                }
            }
            out = out + "\n";
        }
        return out;
    }

    // Print all
    public String toString() {
        return grid() + scores();
    }
}
