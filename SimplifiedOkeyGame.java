import java.util.ArrayList;
import java.util.Random;

public class SimplifiedOkeyGame {

    Random rnd = new Random();

    Player[] players;
    Tile[] tiles;
    int tileCount;

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
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles, this method assumes the tiles are already shuffled
     */
    //Serdar Kara
    public void distributeTilesToPlayers() {
        for (int i = 0; i < 15; i++) {
            players[0].playerTiles[i] = tiles[i];
        }
        for (int i = 15; i < 29; i++) {
            players[1].playerTiles[i- 15] = tiles[i];
        }
        for (int i = 29; i < 43; i++) {
            players[2].playerTiles[i - 29] = tiles[i];
        }
        for (int i = 43; i < 57; i++) {
            players[3].playerTiles[i - 43] = tiles[i];
        }
        for (int i = 0; i < players.length; i++) {
            if(i == 0){
                players[i].numberOfTiles = 15;
            }else{
                players[i].numberOfTiles = 14;
            }
        }
    }

    /*
     * TODO: get the last discarded tile for the current player
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
     * TODO: get the top tile from tiles array for the current player
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

    public void shuffleTiles() {
        
        for (int i = 0; i < 100; i++)
        { 
            int r1 = rnd.nextInt(tiles.length);
            int r2 = rnd.nextInt(tiles.length);
            int t = tiles[r1].getValue();

            tiles[r1].setValue(tiles[r2].getValue());
            tiles[r2].setValue(t);
        }
     
        /* for (int i = 0; i < tiles.length - 1; i++) 
        {
            int randomIndex = (int) (Math.random() * (i + 1));
            // Swap elements of tiles
            Tile temporaryTile = tiles[i];
            tiles[i] = tiles[randomIndex];
            tiles[randomIndex] = temporaryTile;
        } */

    }

    /**
     * checks if the game is finished or not
     * @return the 
     */
    public boolean didGameFinish() {
        
        boolean isFinish = false;
        
        for (Player p : players)
        {
            if (p.checkWinning())
            {
                isFinish = true;
            }
        }

        if (!hasMoreTileInStack())
        {
            isFinish = true;
        }

        return isFinish;
    }

    /* TODO: finds the player who has the highest number for the longest chain
     * if multiple players have the same length may return multiple players
     */
    //Serdar Kara
    public Player[] getPlayerWithHighestLongestChain() {
        
        Player[] winners;
        int winnersLength = 0;
        ArrayList<Player> winnerList = new ArrayList<>();
        int longestChain = 0;
        
        for (int i = 0; i < players.length; i++) {
            
            if (players[i].findLongestChain() > longestChain) 
            {
                winnersLength = 0;
                winnerList.clear();
                winnerList.add(players[i]);
                winnersLength++;
                longestChain = players[i].findLongestChain();
            }
            else if (players[i].findLongestChain() > longestChain) 
            {
                winnerList.add(players[i]);
                winnersLength++;
            }
        }
        
        winners = new Player[winnersLength];
        
        for (int i = 0; i < winnersLength; i++) 
        {
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

    /**
     * calısmıyor olabilir fikrim yok - altan
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

    }

    /*
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
        Player currentPlayer =  players[currentPlayerIndex];
        lastDiscardedTile = currentPlayer.playerTiles[tileIndex];
        for (int i = tileIndex; i < currentPlayer.numberOfTiles; i++) {
            currentPlayer.playerTiles[i-1] = currentPlayer.playerTiles[i];
        }
        currentPlayer.playerTiles[currentPlayer.numberOfTiles] = null;
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
        
        if(index >= 0 && index <= 3) 
        {
            players[index] = new Player(name);
        }
    }

}
