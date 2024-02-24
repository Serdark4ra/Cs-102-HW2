public class Test {
    public static void main(String[] args) {
        

        // test matchingTiles

        Tile tile1 = new Tile(5);
        Tile tile2 = new Tile(5);
        Tile tile3 = new Tile(10);
        
        System.out.println();
        System.out.println("test matchingTiles");

        System.out.println("Expected: true");
        System.out.println(tile1.matchingTiles(tile2));

        System.out.println("Expected: false");
        System.out.println(tile1.matchingTiles(tile3));

        
        //test tile compareTo

        System.out.println();
        System.out.println("test tile compareTo");

        System.out.println("Expected: 0");
        System.out.println(tile1.compareTo(tile2));

        System.out.println("Expected: -1");
        System.out.println(tile1.compareTo(tile3));

        System.out.println("Expected: 1");
        System.out.println(tile3.compareTo(tile1));

        // test canFormChainWith

        Tile tile4 = new Tile(6);

        System.out.println();
        System.out.println("test canFormChainWith");

        System.out.println("Expected: true");
        System.out.println(tile1.canFormChainWith(tile4));

        System.out.println("Expected: false");
        System.out.println(tile1.canFormChainWith(tile3));

        //test tile toString

        System.out.println();
        System.out.println("test tile toString");

        System.out.println("Expected: 5");
        System.out.println(tile1);

        System.out.println("Expected: 10");
        System.out.println(tile3);

        System.out.println();
        System.out.println("End of test for Tile");
        System.out.println();

        Player p1 = new Player("ALTAN");
        p1.addTile(new Tile(3));
        p1.addTile(new Tile(4));
        p1.addTile(new Tile(4));
        p1.addTile(new Tile(4));
        p1.addTile(new Tile(5));
        p1.addTile(new Tile(6));
        p1.addTile(new Tile(7));
        p1.addTile(new Tile(8));
        p1.addTile(new Tile(9));
        p1.addTile(new Tile(15));
        p1.addTile(new Tile(16));
        p1.addTile(new Tile(17));
        p1.addTile(new Tile(18));
        p1.addTile(new Tile(22));
        System.out.println("-----------------------");
        System.out.println(p1.findLongestChain());

        // Test for checkWinning

        Player A = new Player("Ahmet");
        Player B = new Player("Banu");
        Player C = new Player("Ceylin");
        Player D = new Player("Dilan"); 

        // Test for findLongestChain

        // Test for getAndRemoveTile

        // Test for addTile

        // Test for findPositionOfTile

        // Test for displayTiles

        // Test for seperateChains

        

    }
}
