// A Jared Wagner Production

import java.util.Scanner;

public class Runner {
    // Declerations
    Board bd = new Board();
    AIMover auto;
    Scanner kb = new Scanner(System.in);
    int gameType = 0;
    String[] gameModes = {"Two Player: Head to Head","Level 1 AI: Random Mover","Level 2 AI: Offensive Mover","Level 3 AI: Defensive Mover","Level 4 AI: Defensive and Offensive Mover"};
    boolean again = false;
    boolean go = false;
    int curPlayer = bd.getup();
    boolean debug = true;
    boolean change = false;

    // Actual running method
    public void start(){
        // Declerations
        int pos[] = {0,0};
        String result = "";
        // Setup
        setup();
        // Game loop
        do {
            // Tell the players who is up
            System.out.println("\nPlayer " + (curPlayer + 1) + " you are up");

            // Have the player or computer move
            if (curPlayer==1&&gameType!=0) {
                // Computer
                // Show the grid
                System.out.println(bd.grid());
                // Get the move from the ai
                pos = auto.move(bd);
                // Check the position and make move
                result = bd.move(pos,curPlayer +1 );
                // Print the result of the move
                System.out.println("\n" + result);
            }
            else {
                // Human
                // Loop for humna to move to a valid space
                do {
                    // Show the grid
                    System.out.println(bd.grid());
                    // Get the position
                    pos = position(curPlayer);
                    // Check the position and make move
                    result = bd.move(pos,curPlayer + 1);
                    // Print the result of the move
                    System.out.println("\n" + result);
                } while (result.equals("Already taken"));
            }
            // Check if the game is over
            if (result.equals("Player " + (curPlayer + 1) + " Won")||result.equals("Game resulted in a tie")) {
                // Print ending board
                System.out.println(bd);
                //Clear old board
                bd.clear();
                // Ask for second game
                goAgain();
                go=false;
                if(again) {
                    do {
                        try {
                            System.out.print("\nWould you like select a new game type: ");
                            change = kb.nextBoolean();
                            curPlayer = bd.getup();
                            go = true;
                        } catch (Exception e){
                            System.out.println("\nPlease enter either true or false");
                            kb.nextLine();
                            go = false;
                        }
                    } while (!go);
                    if (change) {
                        setup();
                    }
                }
            }
            // Advance to next player
            curPlayer = (curPlayer + 1)%2;
        } while (again||result.equals("Next Move"));
        // End game
        System.out.println("Thanks for playing!");
    }
    // Second game loop query
    public void goAgain() {
        go = false;
        again = false;
        do {
            try {
                System.out.print("\nWould you like to go again?: ");
                again = kb.nextBoolean();
                curPlayer = bd.getup();
                go = true;
            } catch (Exception e){
                System.out.println("\nPlease enter either true or false");
                kb.nextLine();
                go = false;
            }
        } while(!go);
    }
    // Set up game
    public void setup() {
        boolean custom = false;
        char p1 = '1';
        char p2 = '2';
        char bk = '0';
        //Set up game type
        do {
            try {
                System.out.println("What type of game do you want to play?");
                System.out.println("0. " + gameModes[0]);
                System.out.println("1. " + gameModes[1]);
                System.out.println("2. " + gameModes[2]);
                System.out.println("3. " + gameModes[3]);
                System.out.println("4. " + gameModes[4]);
                System.out.print("Type the number for the game mode: ");
                gameType = kb.nextInt();
            } catch (Exception e){
                System.out.println("Please enter either 0, 1, 2, 3, or 4");
                kb.nextLine();
            }
        } while(gameType>=5||gameType<0);
        System.out.println("Gamemode: " + gameType + ". " + gameModes[gameType] + " Selected");
        // Ask for debug move
        if (gameType!=0) {
            go = false;
            do {
                try {
                    System.out.println("Would you like to see computer move logic?");
                    System.out.print("Type true or false: ");
                    debug = kb.nextBoolean();
                    go = true;
                } catch (Exception e) {
                    System.out.println("Please enter either true or false");
                    kb.nextLine();
                    go = false;
                }
            } while (!go);
            System.out.println("Display computer move logic?: " + debug);
        }
        // Ask for custom characters
        /*
        go = false;
        do {
            try {
                System.out.println("Would you like to set custom characters?");
                System.out.print("Type true or false: ");
                custom = kb.nextBoolean();
                go = true;
            } catch (Exception e){
                System.out.println("Please enter either true or false");
                kb.nextLine();
                go = false;
            }
        } while (!go);
        System.out.println("Use custom characters?: " + custom);
        // Set custom characters
        if (custom) {
            //P1
            do {
                try {
                    System.out.println("Please enter a character for player 1");
                    System.out.print("Type a character: ");
                    p1 = kb.nextLine().charAt(0);
                    System.out.println("Player 1: " + p1);
                } catch (Exception e){
                    System.out.println("Please enter a valid character");
                    kb.nextLine();
                }
            } while(p1!='1');
            // P2
            do {
                try {
                    System.out.println("Please enter a character for player 2");
                    System.out.print("Type a character: ");
                    p2 = kb.nextLine().charAt(0);
                    System.out.println("Player 2: " + p2);
                } catch (Exception e){
                    System.out.println("Please enter a valid character");
                    kb.nextLine();
                }
            } while(p2!='2');
            //Bk
            do {
                try {
                    System.out.println("Please enter a character for blank space");
                    System.out.print("Type a character: ");
                    bk = kb.nextLine().charAt(0);
                    System.out.println("Blank Space: " + bk);
                } catch (Exception e){
                    System.out.println("Please enter a valid character");
                    kb.nextLine();
                }
            } while(bk!='0');
            bd.setPlayers(p1,p2,bk);
        }
        */
        // Set up auto mode
        if (gameType!=0) {
            auto = new AIMover(gameType,debug);
        }
    }
    // Call position
    public int[] position (int p) {
        int[] pos = {3,3};
        while (pos[0]>2||pos[0]<0) {
            try {
                System.out.print("Enter a valid row number between 1 and 3: ");
                pos[0] = kb.nextInt()-1;
                System.out.println();
            } catch (Exception e) {
                System.out.println("Please enter either 1, 2, or 3");
                kb.nextLine();
            }
        }
        while (pos[1]>2||pos[1]<0) {
            try {
                System.out.print("Enter a valid column number between 1 and 3: ");
                pos[1] = kb.nextInt()-1;
                System.out.println();
            } catch (Exception e) {
                System.out.println("Please enter either 1, 2, or 3");
                kb.nextLine();
            }
        }

        return pos;
    }
}
