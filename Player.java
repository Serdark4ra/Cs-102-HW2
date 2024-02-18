public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

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
        boolean isWinning;
        //Count how many tile disturbs the chain. It shouldn't be more than 1.
        int disturbance = 0;

        //i starts from one because if statement checks also the previous element
        for ( int i = 1; i < this.numberOfTiles && disturbance < 2; i++ )
        {
            //Checks the current tile with the previous tile.
            if ( !this.playerTiles[i].canFormChainWith( this.playerTiles[i-1]) )
            {
                disturbance = disturbance + 1;
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

        return isWinning;
    }
    

    /*
     *  used for finding the longest chain in this player hand
     * this method should iterate over playerTiles to find the longest chain
     * of consecutive numbers, used for checking the winning condition
     * and also for determining the winner if tile stack has no tiles
     */

    //YBB
    public int findLongestChain() {
        int longestChain = 0;
        int longChain = 1;
        boolean isChain = false;

        for (int index = 0; index < this.numberOfTiles - 1; index++) {
            //You can use canFormChainWith() method. -Akif
            if ( playerTiles[index].getValue() + 1 == playerTiles[index + 1].getValue()){
                longChain ++;
            }
            else {
                if (longChain > longestChain){
                    longestChain = longChain;                    
                }
                longChain = 1;
            }
        }

        return longestChain;
    }

    /*
     * removes and returns the tile in given index position
     */

    //YBB
    public Tile getAndRemoveTile(int index) {
        Tile targetTile = playerTiles[index];
        
        for ( int i = index; i < playerTiles.length; i++){ //shifting all the tiles to the left by one
            playerTiles[index] = playerTiles [index + 1];
        }
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
        
        /*for (int i = 0; i < playerTiles.length; i++) {
            if ( this.playerTiles[i].compareTo(t) == 0 || this.playerTiles[i].compareTo(t) == -1 ){
                for (int j = 14; j > i; j--){ // this shifts the remaining tiles one to the right
                    playerTiles[j+1] = playerTiles[j];
                }
                playerTiles[i] = t; 
            }
            
        }*/

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

        //Not done..
        while ( tileIndex <= this.numberOfTiles )
        {
            do 
            {
                chains[chainIndex][tileIndex] = this.playerTiles[tileIndex];
                tileIndex = tileIndex + 1;
            }while ( this.playerTiles[tileIndex].canFormChainWith(this.playerTiles[tileIndex - 1]) );

            chains[chainIndex] = this.shrinkTileArray(chains[chainIndex] , tileIndex);
            tileIndex = 0;
            chainIndex = chainIndex + 1;
        }

        
        return chains;
    }

    private Tile[] shrinkTileArray(Tile[] tiles, int bound)
    {
        Tile[] shrinkedTiles = new Tile[bound + 1];

        for ( int i = 0; i <= bound; i++)
        {
            shrinkedTiles[i] = tiles[i];
        }
        
        return shrinkedTiles;
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
}
