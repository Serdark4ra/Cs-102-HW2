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
     * en üstteki karttan başlayarak oyunculara kartları dağıtır.
     */
    public void distributeTilesToPlayers() {
        
        int tileNum;

        for (int i = 0; i < players.length; i++)
        {            
            if (i == 0) 
            {
                tileNum = 15;
            } 
            else 
            {
                tileNum = 14;
            }
           
            for (int j = 0; j < tileNum; j++)
            {
                players[i].addTile(tiles[tileCount - 1]);
                tiles[tileCount - 1] = null;
                tileCount--;
            }
        }
    }

    /*
     * get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we
     * picked
     */
    // Oğuzhan Demir
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

    /*
     * get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top
     * tile)
     * and it will be given to the current player
     * returns the toString method of the tile so that we can print what we picked
     */
    // Oğuzhan Demir
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

    /*
     * should randomly shuffle the tiles array before game starts
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

    
    public void shuffleTiles2() {
        
        for (int i = 0; i < 100; i++) {
            int r1 = rnd.nextInt(tiles.length);
            int r2 = rnd.nextInt(tiles.length);
            int t = tiles[r1].getValue();

            tiles[r1].setValue(tiles[r2].getValue());
            tiles[r2].setValue(t);
        }
    }

    /*
     * finished the game. use checkWinning method of the player class to determine
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

    /*
     * finds the player who has the highest number for the longest chain
     * if multiple players have the same length may return multiple players
     */
    // Serdar Kara
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

    /*
     * checks if there are more tiles on the stack to continue the game
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
     * discardTileForComputer2 düzeltilince bunu silebiliriz.
     */
    public void discardTileForComputer2()
    {
        this.discardTile(0);
    }

    /*
     * Current computer player will discard the least useful tile.
     * you may choose based on how useful each tile is
     */
    //-MAY
    public void discardTileForComputer(boolean devModeOn) {
        
        if (devModeOn)
        {
            System.out.println("Before discarding: " + Arrays.toString(this.players[currentPlayerIndex].getTiles()) );
        }

        boolean isDone = false;
        int numberOfTiles = players[currentPlayerIndex].getNumberOfTiles();
        int theIndex = 0;
        
        //Trying to find duplicated element first
        
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
            //If it is not possible to come close with neither with the right nor left, chose right or left randomly.
            //Not sure how to do
            //if ( chains[shortestIndex][chains[shortestIndex].length - 1] )
        }

        if ( theIndex <= 14 || theIndex >= 0)
        {

            this.discardTile(theIndex);

            if (devModeOn)
            {
                System.out.println("After discarding: " + Arrays.toString(this.players[currentPlayerIndex].getTiles()) );
            }
        }
        else
        {
            System.out.println("Error occured. The index is: " + theIndex + " First tile discarding..");
            this.discardTile(0);
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
    
    public void displayAllTilesInHands()
    {
        for (Player p : players)
        {
            System.out.println("player: " + p.getName());
            p.displayTiles();
        }
    }

    public void ortadakiKartlariGöster()
    {
        for (int i = 0; i < tileCount; i++)
        {
            System.out.print(" " + tiles[i]);
        }
    }

}
