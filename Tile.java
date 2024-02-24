public class Tile {
    
    //attributes
    private int value;

    //constructor
    public Tile(int value) {
        this.value = value;
    }

    /**
     * Checks the tiles if they have the same value
     * @param t Tile
     * @return true if they are matching, false otherwise
     */
    public boolean matchingTiles(Tile t) {
        
        if (t.getValue() == this.getValue()) 
        {
            return true;
        }

        return false;
    }

    /**
     * Compares the values of the two tiles.
     * @param t The tile which will be compared 
     * @return 1 if given tile has smaller in value
     * @return 0 if they have the same value
     * @return -1 if the given tile has higher value
     */
    public int compareTo(Tile t) {
        if (t.value < this.getValue()) 
        {
            return 1;
        }
        else if (t.value == this.getValue()) 
        {
            return 0;
        }
        return -1;
    }

    /**
     * determines if this tile and given tile can form a chain together
     * @param t Tile which will be checked
     * @return true if the two tiles can form chain.
     */
    public boolean canFormChainWith(Tile t) {
        
        if (Math.abs(t.getValue() - this.getValue()) == 1) 
        {
            return true;
        }
        return false;
    }

    public String toString() {
        return "" + this.getValue();
    }

    /**
     * 
     * @return the value of the tile 
     */
    public int getValue() {
        return this.value;
    }

    /**
     * changes the value of the tile
     * @param i new value of the tile
     */
    public void setValue(int i)
    {
        this.value = i;
    }

}
