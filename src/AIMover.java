// A Jared Wagner Production

import java.util.Random;
import java.util.ArrayList;

public class AIMover {
    // Declerations
    Random num = new Random();
    int type;
    boolean debug = true;

    // Constructor
    public AIMover(int t, boolean d) {
        type = t;
        debug = d;
    }

    // Move call from outside
    public int[] move(Board bd) {
        int[] pos = {0,0};
        switch (type) {
            case 1: pos = level1(bd); break;
            case 2: pos = level2(bd); break;
            case 3: pos = level3(bd); break;
            case 4: pos = level4(bd); break;
            default: pos = level1(bd); break;
        }
        System.out.println("Computer Making Move to Position: " + (pos[0]+1) + "," + (pos[1]+1));
        return pos;
    }

    // Random place
    private int[] level1(Board bd) {
        if (debug) {
            System.out.println("Computer Making Random Move");
        }
        int[] pos = {0,0};
        boolean valid = false;
        do {
            pos[0] = num.nextInt(3);
            pos[1] = num.nextInt(3);
            valid = bd.view(pos);
        } while (!valid);
        return pos;
    }

    // Offensive mover
    private int[] level2(Board bd) {
        int[] pos = {0,0};
        String out = "";
        pos = checkFor2(bd.getGrid(),2);
        if (pos[0]==3) {
            if (debug) {
                System.out.println("Computer Found No Winning Offensive Move");
            }
            pos = checkForOpen(bd, 2);
            out = "Computer Found an Other Offensive Move";
            if (pos[0] == 3) {
                out = "Computer Found No Other Offensive Move";
            }
            if (debug) {
                System.out.println(out);
            }
            if (pos[0] == 3) {
                pos = level1(bd);
            }
        } else {
            if (debug) {
                System.out.println("Computer Making Winning Offensive Move");
            }
        }
        return pos;
    }

    // Defensive mover
    private int[] level3(Board bd) {
        int[] pos = {0,0};
        String out = "";
        pos = checkFor2(bd.getGrid(),1);
        if (pos[0]==3) {
            if (debug) {
                System.out.println("Computer Found No Critical Defensive Move");
            }
            pos = checkForOpen(bd, 1);
            out = "Computer Found an Other Defensive Move";
            if (pos[0] == 3) {
                out = "Computer Found No Other Defensive Move";
            }
            if (debug) {
                System.out.println(out);
            }
            if (pos[0] == 3) {
                pos = level1(bd);
            }
        } else {
            if (debug) {
                System.out.println("Computer Making Critical Defensive Move");
            }
        }
        return pos;
    }

    // Both Defensive and Offensive Mover
    private int[] level4(Board bd) {
        if (debug) {
            System.out.println("Computer Deciding On A Move");
        }
        int[] oPos = checkFor2(bd.getGrid(),2);
        int[] dPos = checkFor2(bd.getGrid(),1);
        String out = "";
        // Check if there is a double position
        if (oPos[0]==dPos[0]&&oPos[1]==dPos[1]&&oPos[0]!=3&&bd.view(oPos)&&bd.view(dPos)) {
            if (debug) {
                System.out.println("Computer Making Critical Defensive and Winning Offensive Move");
            }
            return oPos;
            // Check if there is a winning offensive move
        } else if (oPos[0]!=3&&bd.view(oPos)) {
            if (debug) {
                System.out.println("Computer Making Winning Offensive Move");
            }
            return oPos;
            // Check if there is a blocking defensive move
        } else if (dPos[0]!=3&&bd.view(dPos)) {
            if (debug) {
                System.out.println("Computer Making Critical Defensive Move");
            }
            return dPos;
            // Check if there is another offensive move available
        } else if (oPos[0]==3||dPos[0]==3) {
            if (debug) {
                System.out.println("Computer Found No Critical Defensive or Winning Offensive Move ");
            }
            oPos = checkForOpen(bd,2);
            dPos = checkForOpen(bd,1);
            out = "Computer Found an Other Offensive Move";
            // Check if there is any places to move offensively
            if(oPos[0]==3&&dPos[0]==3) {
                out = "Computer Found No Other Offensive or Defensive Move";
            } else if (oPos[0]!=3&&dPos[0]!=3){
                out = "Computer Found Both Other Offensive and Defensive Moves";
            } else if (oPos[0]!=3){
                out = "Computer Found No Other Defensive Move";
            } else {
                out = "Computer Found No Other Offensive Move";
            }
            if (debug) {
                System.out.println(out);
            }
            if(oPos[0]==3&&dPos[0]==3) {
                return level1(bd);
            } else if (oPos[0]!=3&&dPos[0]!=3){
                if (debug) {
                    System.out.println("Choosing Random Other Move Type");
                }
                if (bd.getup()==0) {
                    if (debug) {
                        System.out.println("Chose Random Offensive Move");
                    }
                    return oPos;
                } else {
                    if (debug) {
                        System.out.println("Chose Random Defensive Move");
                    }
                    return dPos;
                }
            } else if (oPos[0]!=3){
                if (debug) {
                    System.out.println("Making Other Offensive Move");
                }
                return oPos;
            } else {
                if (debug) {
                    System.out.println("Making Other Defensive Move");
                }
                return dPos;
            }
        } else {
            return level1(bd);
        }

    }

    // Check for 2 of a specified kind in the grid
    private int[] checkFor2(int[][] grid,int player) {
        ArrayList<int[]> outPos = new ArrayList<int[]>();
        int[] pos = {0,0};
        int count = 0;

        // Check the rows
        // Loop rows
        for(int i = 0; i < 3; i++) {
            // Loop values in the row
            for (int col: grid[i]) {
                // Check if of the value being checked
                if (col == player) {
                    // Add to the number of that player in the row
                    count++;
                }
            }
            // Check if two in that row
            if (count == 2) {
                // Figure out where in that row the open space is
                for (int j = 0; j < 3; j++) {
                    // Check the current place
                    if (grid[i][j]==0) {
                        // Store the place
                        pos[0] = i; pos[1] = j;
                        // Add the place
                        outPos.add(pos);
                    }
                }
            }
            count = 0;
        }

        // Across a column
        // Loop columns
        for (int i = 0; i < 3; i++) {
            // Loop values in the column
            for (int j = 0; j < 3; j++) {
                // Check if the value being checked
                if (grid[j][i]==player) {
                    // Add to the number of that player in the column
                    count ++;
                }
            }
            // Check if two in that row
            if (count==2 ) {
                // Figure out where in that column the open space is
                for (int j = 0; j < 3; j++) {
                    // Check the current place
                    if (grid[j][i]==0) {
                        // Store the place
                        pos[0] = j; pos[1] = i;
                        // Add the place
                        outPos.add(pos);
                    }
                }
            }
            count = 0;
        }

        // Cross 1
        //Check each space
        if (grid[0][0]==player) {
            count ++;
        }
        if (grid[1][1]==player) {
            count++;
        }
        if (grid[2][2]==player) {
            count++;
        }
        if (count==2) {
            // Figure out the space that is open in the row
            if (grid[0][0]==0) {
                pos[0] = 0; pos[1] = 0;
                outPos.add(pos);
            }
            if (grid[1][1]==0) {
                pos[0] = 1; pos[1] = 1;
                outPos.add(pos);
            }
            if (grid[2][2]==0) {
                pos[0] = 2; pos[1] = 2;
                outPos.add(pos);
            }
        }
        count=0;


        // Cross 2
        //Check each space
        if (grid[0][2]==player) {
            count ++;
        }
        if (grid[1][1]==player) {
            count++;
        }
        if (grid[2][0]==player) {
            count++;
        }
        if (count==2) {
            // Figure out the space that is open in the row
            if (grid[0][2]==0) {
                pos[0] = 0; pos[1] = 2;
                outPos.add(pos);
            }
            if (grid[1][1]==0) {
                pos[0] = 1; pos[1] = 1;
                outPos.add(pos);
            }
            if (grid[2][0]==0) {
                pos[0] = 2; pos[1] = 0;
                outPos.add(pos);
            }
        }
        count=0;

        // No space with 2
        if (outPos.size()==0) {
            pos[0] = 3;
            pos[1] = 3;
            outPos.add(pos);
        }

        return outPos.get(num.nextInt(outPos.size()));
    }

    // Check for the specified player and return a random eligible spaces
    private int[] checkForOpen(Board bd, int player) {
        ArrayList<int[]> outPos = new ArrayList<int[]>();
        ArrayList<int[]> inPos = new ArrayList<int[]>();
        int[] pos = {0,0};

        // Loop the rows
        for (int i = 0; i <3; i++) {
            // Loop the columns
            for (int j= 0 ;j < 3; j++) {
                // Look for the player
                if (bd.getGrid()[i][j]==player) {
                    // Store Current Position with the player in question
                    pos[0] = i; pos[1] = j;
                    // Find the places available around the player
                    inPos = checkAround(bd,pos,player);
                    // Add each to the list of places to move
                    for(int[] here: inPos) {
                        outPos.add(here);
                    }
                }
            }
        }

        // Default
        if (outPos.size()==0) {
            pos[0] = 3;
            pos[1] = 3;
            outPos.add(pos);
        }

        // Return one of the available positions at random
        return outPos.get(num.nextInt(outPos.size()));
    }

    // Check spaces for player around position
    private ArrayList<int[]> checkAround(Board bd,  int[] inPos, int player) {
        ArrayList<int[]> outPos = new ArrayList<int[]>();
        int[] pos = new int[2];
        int temp = 0;
        int[][] grid;

        grid= new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = bd.getGrid()[i][j];
            }
        }

        // Check up
        try {
            pos[0] = inPos[0]-1;
            pos[1] = inPos[1];
            temp = bd.getGrid()[pos[0]][pos[1]];
            if (bd.getGrid()[pos[0]][pos[1]]==0) {
                grid[pos[0]][pos[1]]=player;
                if (checkFor2(grid,player)[0]!=3) {
                    outPos.add(pos);
                }
            }
        } catch (Exception e) {
            // Index out of bounds
        }
        pos = new int[2];
        grid= new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = bd.getGrid()[i][j];
            }
        }

        // Check down
        try {
            pos[0] = inPos[0]+1;
            pos[1] = inPos[1];
            temp = grid[pos[0]][pos[1]];
            if (grid[pos[0]][pos[1]]==0) {
                grid[pos[0]][pos[1]]=player;
                if (checkFor2(grid,player)[0]!=3) {
                    outPos.add(pos);
                }
            }
        } catch (Exception e) {
            // Index out of bounds
        }
        pos = new int[2];
        grid= new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = bd.getGrid()[i][j];
            }
        }

        // Check left
        try {
            pos[0] = inPos[0];
            pos[1] = inPos[1]-1;
            temp = grid[pos[0]][pos[1]];
            if (grid[pos[0]][pos[1]]==0) {
                grid[pos[0]][pos[1]]=player;
                if (checkFor2(grid,player)[0]!=3) {
                    outPos.add(pos);
                }
            }
        } catch (Exception e) {
            // Index out of bounds
        }
        pos = new int[2];
        grid= new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = bd.getGrid()[i][j];
            }
        }

        // Check right
        try {
            pos[0] = inPos[0];
            pos[1] = inPos[1]+1;
            temp = grid[pos[0]][pos[1]];
            if (grid[pos[0]][pos[1]]==0) {
                grid[pos[0]][pos[1]]=player;
                if (checkFor2(grid,player)[0]!=3) {
                    outPos.add(pos);
                }
            }
        } catch (Exception e) {
            // Index out of bounds
        }
        pos = new int[2];
        grid= new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = bd.getGrid()[i][j];
            }
        }

        // Check upper right
        try {
            pos[0] = inPos[0]-1;
            pos[1] = inPos[1]+1;
            temp = grid[pos[0]][pos[1]];
            if (grid[pos[0]][pos[1]]==0) {
                grid[pos[0]][pos[1]]=player;
                if (checkFor2(grid,player)[0]!=3) {
                    outPos.add(pos);
                }
            }
        } catch (Exception e) {
            // Index out of bounds
        }
        pos = new int[2];
        grid= new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = bd.getGrid()[i][j];
            }
        }

        // Check upper left
        try {
            pos[0] = inPos[0]-1;
            pos[1] = inPos[1]-1;
            temp = grid[pos[0]][pos[1]];
            if (grid[pos[0]][pos[1]]==0) {
                grid[pos[0]][pos[1]]=player;
                if (checkFor2(grid,player)[0]!=3) {
                    outPos.add(pos);
                }
            }
        } catch (Exception e) {
            // Index out of bounds
        }
        pos = new int[2];
        grid= new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = bd.getGrid()[i][j];
            }
        }

        // Check lower right
        try {
            pos[0] = inPos[0]+1;
            pos[1] = inPos[1]+1;
            temp = grid[pos[0]][pos[1]];
            if (grid[pos[0]][pos[1]]==0) {
                grid[pos[0]][pos[1]]=player;
                if (checkFor2(grid,player)[0]!=3) {
                    outPos.add(pos);
                }
            }
        } catch (Exception e) {
            // Index out of bounds
        }
        pos = new int[2];
        grid= new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = bd.getGrid()[i][j];
            }
        }

        // Check lower left
        try {
            pos[0] = inPos[0]+1;
            pos[1] = inPos[1]-1;
            temp = grid[pos[0]][pos[1]];
            if (grid[pos[0]][pos[1]]==0) {
                grid[pos[0]][pos[1]]=player;
                if (checkFor2(grid,player)[0]!=3) {
                    outPos.add(pos);
                }
            }
        } catch (Exception e) {
            // Index out of bounds
        }
        pos = new int[2];
        grid= new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = bd.getGrid()[i][j];
            }
        }

        // Default
        if (outPos.size()==0) {
            pos[0]=3; pos[1]=3;
            outPos.add(pos);
        }
        return outPos;
    }
}
