import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SimplifiedOkeyGame {

    private Player[] players;
    private Tile[] tiles;
    private int tileCount;

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

    /**
     * Distributes Tiles to players starting from the top Tile
     */
    public void distributeTilesToPlayers() {
        
        int tileNum;

        for (int i = 0; i < players.length; i++)
        {            
            if (i == 0) 
            {
                tileNum = 15; //first player gets 15 tiles
            } 
            else 
            {
                tileNum = 14; //others get 14
            }
           
            for (int j = 0; j < tileNum; j++)
            {
                players[i].addTile(tiles[tileCount - 1]);
                tiles[tileCount - 1] = null;
                tileCount--;
            }
        }
    }

    /**
     * gets the last discarded tile for the current player
     * @return the value of the tile as a String
     */
    public String getLastDiscardedTile() {
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

    /**
     * get the top tile from tiles array for the current player
     * @return the value of the tile as a String 
     */
    public String getTopTile() {
        // Checks if there are still tiles in the stack
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

    /**
     * randomly shuffles tiles
     */
    public void shuffleTiles()
    {
        for (int i = 0; i < 200; i++)
        {
            int r1 = rnd.nextInt(tiles.length);
            int r2 = rnd.nextInt(tiles.length);
            
            Tile tempTile = new Tile(tiles[r1].getValue());
            tiles [r1] = tiles [r2];
            tiles [r2] = tempTile;
        }
    }

    /**
     * determines whether the game is finished or not
     * @return true if the game is finished
     */
    public boolean didGameFinish() {

        boolean isGameFinished = false;
        if (players[currentPlayerIndex].checkWinning() == true) {
            isGameFinished = true;
        }else if (this.tileCount == 0) {
            isGameFinished = true;
        }
        return isGameFinished;

        
    }

    /**
     * finds the player who has the highest number for the longest chain
     * if multiple players have the same length may return multiple players
     * @return Player array containing players (or just one player) with longest chain
     */
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
            } else if (players[i].findLongestChain() == longestChain) {
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

    /**
     * checks if there are more tiles on the stack to continue the game
     * @return true if there is still tile in the stack, otherwise false
     */
    public boolean hasMoreTileInStack() {
        return tileCount != 0;
    }

    public Tile addTopTile() {
        if (tileCount > 0) {
            return tiles[tileCount - 1];
        } else {
            System.out.println("No more tiles in the stack");
            return null;
        }
    }

    /*
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * you should check if getting the discarded tile is useful for the computer
     * by checking if it increases the longest chain length, if not get the top tile
     */
    public void pickTileForComputer() {
        Player currentPlayer = players[currentPlayerIndex];
        int currentChainLength = players[currentPlayerIndex].findLongestChain();
        currentPlayer.addTile(lastDiscardedTile);
        if (currentPlayer.findLongestChain() <= currentChainLength) {
            currentPlayer.getAndRemoveTile(currentPlayer.findPositionOfTile(lastDiscardedTile));
            getTopTile();
        }
    }
    
    /**
     * Current computer player will discard the least useful tile.
     */
    public void discardTileForComputer() {
       
        boolean isDone = false;
        int numberOfTiles = players[currentPlayerIndex].getNumberOfTiles();
        int theIndex = 0;
               
        //Trying to find duplicated element first
        for ( int i = 0; i < numberOfTiles - 1  && !isDone; i++ )
        {
            if ( 0 == players[currentPlayerIndex].getTiles()[i].compareTo(players[currentPlayerIndex].getTiles()[i + 1]) )
            {
                //this.players[currentPlayerIndex].getAndRemoveTile(i);
                theIndex = i;
                isDone = true;
            }
        }


        if ( !isDone )
        {
            //Get the tiles as seperated chain array
            Tile[][] chains = players[currentPlayerIndex].seperateChains();

            //Determine the shortest tile chain
            int indexOfShortestChain = 0;
            int indexOfLargestChain = 0;

            for ( int i = 0; i < chains.length; i++ )
            {
                if ( chains[i].length > chains[indexOfLargestChain].length )
                {
                    indexOfLargestChain = i;
                }
            }

            for ( int i = 0; i < chains.length; i++ )
            {   if ( chains[i].length == chains[indexOfShortestChain].length )
                {
                    if ( Math.abs(indexOfLargestChain - indexOfShortestChain) <= Math.abs(indexOfLargestChain - i) )
                    {
                        indexOfShortestChain = i;
                    } 
                }
                else if ( chains[i].length < chains[indexOfShortestChain].length )
                {
                    indexOfShortestChain = i;
                }
            }

            //Determine the right or left
            //If the smallest chain is first one, discard first tile
            if ( indexOfShortestChain == 0)
            {
                //this.players[currentPlayerIndex].getAndRemoveTile(0);
                theIndex = 0;
            }

            //If the smallest chain is last one, discard last tile
            else if ( indexOfShortestChain == chains.length - 1)
            {
                //this.players[currentPlayerIndex].getAndRemoveTile(numberOfTiles);
                theIndex = numberOfTiles - 1;
            }

            //If it is somewhere in the middle:
            else
            {
                //Locate the start index of the chain
                int firstIndexOfShortest = 0;
                int lastIndexOfShortest = 0;

                int i = 0;
                while ( i < indexOfShortestChain )
                {
                    firstIndexOfShortest = firstIndexOfShortest + chains[i].length;
                    i = i + 1;
                }

                lastIndexOfShortest = firstIndexOfShortest + chains[indexOfShortestChain].length - 1;

                //If the smallest chain has neigbourhood with the longest in one side, discard from the other side.
                if ( Math.abs(indexOfShortestChain - indexOfLargestChain) == 1 )
                {
                    int diff = indexOfLargestChain - indexOfShortestChain;
                    int removeIndex = 0;
                    
                    if ( diff == -1)
                    {
                        removeIndex = lastIndexOfShortest;
                    }
                    else
                    {
                        removeIndex = firstIndexOfShortest;
                    }
                    //this.players[currentPlayerIndex].getAndRemoveTile(removeIndex);
                    theIndex = removeIndex;
                }

                //Look the one after and before number.
                else
                {
                    Tile[] shortestChain = chains[indexOfShortestChain];
                    Tile[] rightChain = chains[indexOfShortestChain + 1];
                    Tile[] leftChain = chains[indexOfShortestChain - 1];
                    int scoreWithRight = 0;
                    int scoreWithLeft = 0;


                    //Total sequence length of merge - Distance from nearby chain
                    scoreWithRight =  shortestChain.length + rightChain.length - (rightChain[0].getValue() - shortestChain[shortestChain.length - 1].getValue());
                    scoreWithLeft =  shortestChain.length + leftChain.length - (leftChain[leftChain.length - 1].getValue() - shortestChain[0].getValue()); 

                    //Find the biggest score, discard tile from the other side.
                    if ( scoreWithLeft > scoreWithRight )
                    {
                        theIndex = firstIndexOfShortest;
                        //this.players[currentPlayerIndex].getAndRemoveTile(firstIndexOfShortest);
                    } 
                    else if ( scoreWithRight > scoreWithLeft )
                    {
                        theIndex = lastIndexOfShortest;
                        //this.players[currentPlayerIndex].getAndRemoveTile(lastIndexOfShortest);
                    }
                    //Discard right or left randomly.
                    else
                    {
                        int random = (int)(Math.random() * 100);

                        if ( random <= 50)
                        {
                            theIndex = lastIndexOfShortest;
                            //this.players[currentPlayerIndex].getAndRemoveTile(lastIndexOfShortest);
                        }
                        else
                        {
                            theIndex = firstIndexOfShortest;
                            //this.players[currentPlayerIndex].getAndRemoveTile(lastIndexOfShortest);
                        }
                    }
                }
            }
        }

        if ( theIndex <= 14 || theIndex >= 0)
        {
            this.discardTile(theIndex);
        }
        else
        {
            System.out.println("Error occured. The index is: " + theIndex + " First tile discarding..");
            this.discardTile(0);
        }
    }

    /**
     * discards the current player's tile at given index
     * and sets lastDiscardedTile variable and remove that tile from that player's tiles
     * @param tileIndex index of the tile that will be discarded
     */
    public void discardTile(int tileIndex) {
        lastDiscardedTile = players[currentPlayerIndex].getAndRemoveTile(tileIndex);
    }

    public void displayDiscardInformation() {
        if (lastDiscardedTile != null) {
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
        if (index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }
    
    public Player[] getPlayers(){
        return players;
    }
    
    /**
     * 
     * @return all values of all tiles in the current player's hand.
     */
    public String getTileValues()
    {
        return Arrays.toString(players[currentPlayerIndex].getTiles());
    }
}
