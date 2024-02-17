import java.util.ArrayList;
import java.util.Random;

public class SimplifiedOkeyGame {

    Player[] players;
    Tile[] tiles;
    int tileCount;

    Random rnd = new Random();
    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public SimplifiedOkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[104];
        int currentTile = 0;

        // four copies of each value, no jokers
        for (int i = 1; i <= 26; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i);
            }
        }

        tileCount = 104;
    }

    /*
     * distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles, this method assumes the tiles are already shuffled
     */
    //Serdar Kara
    //Updated
    public void distributeTilesToPlayers() {
        int distributionStartPoint = 0;
        int tileNum;
        for (int i = 0; i < players.length; i++) {
            
            Player currentPlayer = players[i];
            if (i == 0) {
                tileNum = 15;
            }else{
                tileNum = 14;
            }
            for (int j = distributionStartPoint; j < distributionStartPoint + tileNum; j++) {
                currentPlayer.addTile(tiles[j]);
            }
            distributionStartPoint = distributionStartPoint + tileNum;
        }
    }

    /*
     * get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    // Oğuzhan Demir
    public String getLastDiscardedTile() 
    {
        // Firstly check discarded tile
        if (lastDiscardedTile != null) {
            players[currentPlayerIndex].addTile(lastDiscardedTile);
            // Give the discarded tile to the current player
            String discardedTileString = lastDiscardedTile.toString();
            lastDiscardedTile = null; 
            // Reset lastDiscardedTile since it has been picked up to 
                
            return discardedTileString;
        } else {
            System.out.println("No tile has been discarded yet.");
            return null;
            // tile has not been discarded, this method gives an error
        }
    }

    /*
     * get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * and it will be given to the current player
     * returns the toString method of the tile so that we can print what we picked
     */
    // Oğuzhan Demir
    public String getTopTile() 
    {
         // Check if there are still tiles in the stack
        if (tileCount > 0) {
            Tile topTile = tiles[--tileCount];
             // Get the top tile
            players[currentPlayerIndex].addTile(topTile);
             // Give the tile to the current player
            return topTile.toString();
        } else {
            System.out.println("No more tiles in the stack.");
            return null;
            // If the tile is exhausted it gives a warning.
        }
        
    }

    /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    // Oğuzhan Demir
    public void shuffleTiles() {
        for (int i = 0; i < 100; i++)
        { 
            int r1 = rnd.nextInt(tiles.length);
            int r2 = rnd.nextInt(tiles.length);
            int t = tiles[r1].getValue();

            tiles[r1].setValue(tiles[r2].getValue());
            tiles[r2].setValue(t);
        }
    }

    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game. use checkWinning method of the player class to determine
     */
    public boolean didGameFinish() {

        boolean isAnyoneWins = false;

        //For each loop is used in order to visit every player and check whether they finished or not.
        for (Player player : players) {
            if ( player.checkWinning() )
            {
                isAnyoneWins = true;
            }
        }
        return isAnyoneWins;
    }

    /* finds the player who has the highest number for the longest chain
     * if multiple players have the same length may return multiple players
     */
    //Serdar Kara
    public Player[] getPlayerWithHighestLongestChain() {
        Player[] winners;
        int winnersLength = 0;

        ArrayList<Player> winnerList = new ArrayList<>();
        int longestChain = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i].findLongestChain() > longestChain) {
                winnersLength = 0;
                winnerList.clear();
                winnerList.add(players[i]);
                winnersLength++;
                longestChain = players[i].findLongestChain();
            }else if (players[i].findLongestChain() > longestChain) {
                winnerList.add(players[i]);
                winnersLength++;
            }
        }
        winners = new Player[winnersLength];
        for (int i = 0; i < winnersLength; i++) {
            winners[i] = winnerList.get(i);
        }

        return winners;
        
    }
    
    /*
     * checks if there are more tiles on the stack to continue the game
     */
    public boolean hasMoreTileInStack() {
        return tileCount != 0;
    }

    public Tile addTopTile()
    {
        if (tileCount > 0)
        {
            return tiles[tileCount - 1];
        }
        else 
        {
            System.out.println("No more tiles in the stack");
            return null;
        }
    }

    /*
     * TODO: pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * you should check if getting the discarded tile is useful for the computer
     * by checking if it increases the longest chain length, if not get the top tile
     */
    public void pickTileForComputer() {
        int currentChainLength = players[currentPlayerIndex].findLongestChain();
        int possibleChainLength;

        players[currentPlayerIndex].addTile(addTopTile());
        possibleChainLength = players[currentPlayerIndex].findLongestChain();

        players[currentPlayerIndex].removeLastTile();

        if (possibleChainLength <= currentChainLength)
        {
            getTopTile();
        }
        else 
        {
            // o zaman atılan taşı alacak ama nasıl?
            players[currentPlayerIndex].addTile(lastDiscardedTile);
        }
    }

    /*
     * TODO: Current computer player will discard the least useful tile.
     * you may choose based on how useful each tile is
     */
    public void discardTileForComputer() {
        boolean isDuplicated = false;
        int indexOfFirstDup = 0;
        int indexOfSecondDup = 0;

        //Trying to find duplicated element first
        for ( int i = 0; i < players[currentPlayerIndex].numberOfTiles; i++)
        {
            for ( int j = i; j <  players[currentPlayerIndex].numberOfTiles; j++)
        }
       
    }

    /*
     * discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
        lastDiscardedTile = players[currentPlayerIndex].getAndRemoveTile(tileIndex);
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}
