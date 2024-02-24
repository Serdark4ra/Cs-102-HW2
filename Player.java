import java.util.Arrays;

public class Player {
    private String playerName;
    private Tile[] playerTiles;
    private int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     *  checks this player's hand to determine if this player is winning 
     * the player with a complete chain of 14 consecutive numbers wins the game
     * note that the player whose turn is now draws one extra tile to have 15 tiles in hand,
     * and the extra tile does not disturb the longest chain and therefore the winning condition
     * check the assigment text for more details on winning condition
     */
    //MAY
    public boolean checkWinning() {
        /*boolean isWinning;
        //Count how many tile disturbs the chain. It shouldn't be more than 1.
        int disturbance = 0;

        //i starts from one because if statement checks also the previous element
        for ( int i = 1; i < this.numberOfTiles && disturbance < 2; i++ )
        {
            //Checks the current tile with the previous tile.
            if ( !this.playerTiles[i].canFormChainWith( this.playerTiles[i-1]) )
            {
                disturbance = disturbance + 1;
                if ( i + 1 != this.numberOfTiles && (this.playerTiles[i].getValue() - this.playerTiles[i-1].getValue()) != 0)
                {
                    disturbance = 2;
                }
            }
        }

        if ( disturbance == 2 )
        {
            isWinning = false;
        }
        else
        {
            isWinning = true;
        }

        return isWinning;*/
        boolean isWinning = true; // Assume winning unless proven otherwise
    int consecutiveCount = 1; // Initialize consecutive count to 1
    int disturbance = 0;

    // Iterate through the tiles
    for (int i = 1; i < this.numberOfTiles && disturbance < 2; i++) {
        // Check if the current tile can form a chain with the previous tile
        if (this.playerTiles[i].getValue() - this.playerTiles[i - 1].getValue() == 1) {
            consecutiveCount++; // Increment consecutive count if tiles are consecutive
        } else {
            disturbance++; // Increment disturbance if tiles are not consecutive
            if (disturbance == 2) {
                isWinning = false; // If more than one disturbance, set isWinning to false
            }
        }
    }

    // Check if the player has exactly 14 consecutive tiles
    if (consecutiveCount != 14 || this.numberOfTiles != 14) {
        isWinning = false;
    }

    return isWinning;
    }
    

    /*
     * used for finding the longest chain in this player hand
     * this method should iterate over playerTiles to find the longest chain
     * of consecutive numbers, used for checking the winning condition
     * and also for determining the winner if tile stack has no tiles
     */

    //YBB
    public int findLongestChain() {
        int longestChain = 0;
        int lenghtOfChain = 1;
    
        // until the second last tile
        for (int index = 1; index < this.numberOfTiles; index++) {
            
            if (playerTiles[index - 1].canFormChainWith(playerTiles[index])) {
                lenghtOfChain++;
            }else if (playerTiles[index].getValue() == playerTiles[index - 1].getValue()) {
                continue;
            }
             else {
                if (lenghtOfChain > longestChain) {
                    longestChain = lenghtOfChain; // Update the longest chain's length
                }
                lenghtOfChain = 1;
            }
        }
    
        // Check the last tile separately
        if (lenghtOfChain > longestChain) {
            longestChain = lenghtOfChain;
        }
    
        return longestChain;
    
    }

    /**
     * beyler tüm metodlara su java doc yorumlarını ekleyelim bi ara.
     * @param index
     * @return
     */
    public Tile getAndRemoveTile(int index) {
        
        Tile targetTile = playerTiles[index];
        //shifting all the tiles to the left by one
        for ( int i = index; i < numberOfTiles - 1; i++)
        { 
            playerTiles[i] = playerTiles [i + 1];
        }
        this.playerTiles[numberOfTiles - 1] = null;
        this.numberOfTiles--;

        return targetTile; // return desired tile 
    }

    /*
     * adds the given tile to this player's hand keeping the ascending order
     * this requires you to loop over the existing tiles to find the correct position,
     * then shift the remaining tiles to the right by one
     */

    //YBB
    // Updated by Serdar to avoid compare method error
    public void addTile(Tile t) {
        boolean isPlaceFound = false;
        int suitablePlace = this.numberOfTiles;

        for (int i = 0; i < this.numberOfTiles && !isPlaceFound; i++) {
            if (playerTiles[i].getValue() >= t.getValue()) {
                suitablePlace = i;
                isPlaceFound= true;
            }
        }

        for (int i = this.numberOfTiles; i > suitablePlace; i--) {
            playerTiles[i] = playerTiles[i-1];
        }

        playerTiles[suitablePlace] = t;
        this.numberOfTiles ++;
    }

    /*
     * finds the index for a given tile in this player's hand
     */
    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].matchingTiles(t)) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    /*
     * displays the tiles of this player
     */
    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            if (playerTiles[i] != null) {
                System.out.print(playerTiles[i].toString() + " ");
            }
        }
        System.out.println();
    }

    /**
     * This method separates the chains that user have as seperated Tile arrays.
     * @return
     */
    public Tile[][] seperateChains() {
        Tile[][] chains = new Tile[15][15];
        int chainIndex = 0;
        int tileIndex = 0;

        //Initial insertion
        chains[chainIndex][tileIndex] = this.playerTiles[tileIndex];
        tileIndex = tileIndex + 1;

        //For loop guarantees not to exceed the playerTiles array
        for ( int i = 0; i < this.numberOfTiles - 1; i++ )
        {
            //If there cannot be a chain anymore, start new chain.
            if ( !this.playerTiles[i].canFormChainWith(this.playerTiles[i + 1]) )
            {
                //Shrink the chain and adjust variables.
                chains[chainIndex] = Arrays.copyOf(chains[chainIndex], tileIndex);
                chainIndex = chainIndex + 1;
                tileIndex = 0;
            }
            //Insert tiles.
            chains[chainIndex][tileIndex] = this.playerTiles[i + 1];
            tileIndex = tileIndex + 1;
        }

        chains[chainIndex] = Arrays.copyOf(chains[chainIndex], tileIndex);

        //Shrink the complete array and return.
        chains = Arrays.copyOf(chains, chainIndex + 1);
        return chains;
    }

    public void addTiles(Tile t)
    {
        this.playerTiles[14] = t;
    }

    public void removeLastTile()
    {
        this.playerTiles[playerTiles.length - 1] = null;
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
    public int getNumberOfTiles(){
        return numberOfTiles;
    }
}
