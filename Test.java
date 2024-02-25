import java.util.Arrays;
import java.util.Random;

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
        p1.addTile(new Tile(1));
        p1.addTile(new Tile(2));
        p1.addTile(new Tile(3));
        p1.addTile(new Tile(4));
        p1.addTile(new Tile(5));
        p1.addTile(new Tile(6));
        p1.addTile(new Tile(7));
        p1.addTile(new Tile(8));
        p1.addTile(new Tile(9));
        p1.addTile(new Tile(10));
        p1.addTile(new Tile(11));
        p1.addTile(new Tile(12));
        p1.addTile(new Tile(13));
        p1.addTile(new Tile(14));
        System.out.println("-----------------------");
        System.out.println(p1.findLongestChain());
        System.out.println(p1.checkWinning());

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

        
        Tile[] tiles;
        tiles = new Tile[104];
        int currentTile = 0;

        // four copies of each value, no jokers
        for (int i = 1; i <= 26; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i);
            }
        }

        Random rnd = new Random();
        
        
        for (int i = 0; i < 200; i++)
        {
            int r1 = rnd.nextInt(tiles.length);
            int r2 = rnd.nextInt(tiles.length);

        Tile tempTile = new Tile(tiles[r1].getValue());
        tiles [r1] = tiles [r2];
        tiles [r2] = tempTile;
        }

        System.out.println(Arrays.toString(tiles));
    
        
    }
    
}
