public class Tile {
    
    //attributes
    private int value;

    /*
     * creates tile using the given value. False jokers are not included in this game.
     */
    public Tile(int value) {
        this.value = value;
    }

    /*
     *  should check if the given tile t and this tile have the same value 
     * return true if they are matching, false otherwise
     */
    public boolean matchingTiles(Tile t) {
        
        if (t.getValue() == this.getValue()) 
        {
            return true;
        }

        return false;
    }

    /*
     *  should compare the order of these two tiles
     * return 1 if given tile has smaller in value
     * return 0 if they have the same value
     * return -1 if the given tile has higher value
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

    /*
     *  should determine if this tile and given tile can form a chain together
     * this method should check the difference in values of the two tiles
     * should return true if the absoulute value of the difference is 1 (they can form a chain)
     * otherwise, it should return false (they cannot form a chain)
     */
    public boolean canFormChainWith(Tile t) {
        
        if (Math.abs(t.getValue() - this.getValue()) == 1) 
        {
            return true;
        }
        return false;
    }

    public void setValue(int i)
    {
        this.value = i;
    }

    public String toString() {
        return "" + this.getValue();
    }

    public int getValue() {
        return this.value;
    }

}
