import java.util.Scanner;

public class ApplicationMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SimplifiedOkeyGame game = new SimplifiedOkeyGame();

        System.out.print("Please enter your name: ");
        String playerName = sc.next();

        game.setPlayerName(0, playerName);
        game.setPlayerName(1, "John");
        game.setPlayerName(2, "Jane");
        game.setPlayerName(3, "Ted");

        game.createTiles();
        game.shuffleTiles();
        game.distributeTilesToPlayers();
        //game.displayAllTilesInHands();
        // developer mode is used for seeing the computer players hands, to be used for debugging
        System.out.print("Play in developer's mode with other player's tiles visible? (Y/N): ");
        char devMode = sc.next().charAt(0);
        boolean devModeOn = devMode == 'Y';
        
        boolean firstTurn = true;
        boolean gameContinues = true;
        int playerChoice = -1;

        while(gameContinues) {

            int currentPlayer = game.getCurrentPlayerIndex();           
            System.out.println(game.getCurrentPlayerName() + "'s turn.");            
            
            if(currentPlayer == 0) {
                // this is the human player's turn

                game.displayCurrentPlayersTiles();
                game.displayDiscardInformation();
                
                //-----buranın altı silinecek sadece hatayı çözmek için ekledim
                /*  game.displayAllTilesInHands();
                    System.out.println("----------");
                    game.ortadakiKartlariGöster(); */
                //------buranın üstü silinecek

                System.out.println("What will you do?");

                if(!firstTurn) {
                    // after the first turn, player may pick from tile stack or last player's discard
                    System.out.println("1. Pick From Tiles");
                    System.out.println("2. Pick From Discard");
                }
                else{
                    // on first turn the starting player does not pick up new tile
                    System.out.println("1. Discard Tile");
                }

                

                // after the first turn we can pick up
                if(!firstTurn) {
                    // To ensure user enters 1 or 2 
                    // Serdar Kara
                    boolean playerChoiceValidityTest = true;
                    while (playerChoiceValidityTest) {
                        System.out.print("Your choice: ");
                        playerChoice = sc.nextInt();
                        if (playerChoice == 2 || playerChoice == 1) {
                            playerChoiceValidityTest = false;
                        }else{
                            System.out.println("Invalid Choice Number!");
                        }
                    }
                    if(playerChoice == 1) {
                        System.out.println("You picked up: " + game.getTopTile());
                        firstTurn = false;
                    }
                    else if(playerChoice == 2) {
                        System.out.println("You picked up: " + game.getLastDiscardedTile());
                    }

                    // display the hand after picking up new tile
                    game.displayCurrentPlayersTiles();
                }
                else{
                    // after first turn it is no longer the first turn
                    firstTurn = false;
                }

                gameContinues = !game.didGameFinish() && game.hasMoreTileInStack();

                if(gameContinues) {
                    // if game continues we need to discard a tile using the given index by the player
                    System.out.println("Which tile you will discard?");                    

                    
                    // This part guarentees player gives an integer (positive) and program doesn't crash -YBB
                    String tileDiscardText = "Discard the tile in index: ";
                    String outIndexTileError = "Invalid index. Please choose a tile to discard between 0 and 14";

                    do {
                        System.out.print(tileDiscardText);
                        while (!sc.hasNextInt()) {      
                            System.out.println(outIndexTileError); // if no integer, then give error message and ask again - YBB
                            System.out.print(tileDiscardText);
                            sc.nextLine();                            
                        }
                        playerChoice = sc.nextInt();

                        if (playerChoice < 0 || playerChoice > 14){
                            System.out.println(outIndexTileError);
                        }
                        
                    } while (playerChoice < 0 || playerChoice > 14); // also handles the range of the index is correct - YBB

                    // make sure the given index is correct, should be 0 <= index <= 14
                    
                    game.discardTile(playerChoice);
                    game.passTurnToNextPlayer();

                }
                else{
                    if(!game.didGameFinish()) {
                        // if we finish the hand we win
                        System.out.println("Congratulations, you win!");    
                    }
                    else{
                        // the game ended with no more tiles in the stack
                        // determine the winner based on longest chain lengths of the players
                        // use getPlayerWithHighestLongestChain method of game for this task
                        // Serdar Kara
                        Player[] winners = game.getPlayerWithHighestLongestChain();
                        System.out.print("Winners are : ");
                        for (int i = 0; i < winners.length; i++) {
                            System.out.print(winners[i].getName() + " ");
                        }


                    }
                }
            }
            else{
                // this is the computer player's turn
                if(devModeOn) {
                    game.displayCurrentPlayersTiles();
                }
                
                // computer picks a tile from tile stack or other player's discard
                game.pickTileForComputer();

                gameContinues = !game.didGameFinish() && game.hasMoreTileInStack();

                if(gameContinues) {
                    // if game did not end computer should discard
                    game.discardTileForComputer();
                    game.passTurnToNextPlayer();
                }
                else{
                    if(game.didGameFinish()) { // ! is removed to solve wrong player wins error 
                        // current computer character wins
                        System.out.println(game.getCurrentPlayerName() + " wins.");
                    }
                    else{
                        //  the game ended with no more tiles in the stack
                        // determine the winner based on longest chain lengths of the players
                        // use getPlayerWithHighestLongestChain method of game for this task
                        // Serdar Kara
                        Player[] winners = game.getPlayerWithHighestLongestChain();
                        if (winners.length == 1 ) {
                            System.out.print("Winner is : ");
                        }else{
                            System.out.print("Winners are : ");
                        }
                        
                        for (int i = 0; i < winners.length; i++) {
                            System.out.print(winners[i].getName() + " ");
                        }
                    }
                }
            }
        }

        sc.close();
    }
}