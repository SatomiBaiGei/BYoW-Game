package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    public static final int width = 50;
    public static final int height = 50;
    private static final Random RANDOM = new Random(1000);

    //method addHexagon, which include the tiles enginer and the target that how big the hexagon you want

    public static void drawRow(Position p, TETile tile, TETile[][] tiles, int length){
        for(int dx = 0 ; dx < length; dx++){
            tiles[p.x + dx][p.y] = tile;
        }
    }

    public static void addHexagonHelper(TETile[][] tiles, Position p, TETile tile,int b, int t){
        Position startRow = p.shift(b,0);
        drawRow(startRow, tile, tiles, t);
        if( b> 0){
            Position nextPos = p.shift(0, -1);
            addHexagonHelper(tiles,nextPos,tile,b - 1, t+2);
        }
        Position startOfReflectedRow = startRow.shift(0,-(2*b + 1));
        drawRow(startOfReflectedRow,tile,tiles, t);

    }
    public static void addHexagon1(TETile[][] tiles, Position p,TETile t, int size){
        if(size < 2){
            throw  new IllegalArgumentException("Enter valid size.");
        }
        addHexagonHelper(tiles,p, t, size - 1, size);

    }

    /**
     *Gets the position of the top right neighbor of a hexagon at Position p.
     * N is the size of the hexagon we are tessellating
     */

    public static Position getTopRightNeighbor(Position p, int n ){
        return p.shift(2*n - 1, n);

    }
    public static Position getBottomRightNeighbor(Position p, int n){
        return p.shift(2*n - 1, -n);
    }


    public static void drawWorld(TETile[][] tiles, Position p,int hexSize, int tessSize){
       //Draw the first hexagon;
        addHexColumn(tiles,p ,hexSize,tessSize);

        //Expand up to the right;
        for(int i = 1; i < tessSize; i++){
            p = getTopRightNeighbor(p, hexSize);
            addHexColumn(tiles, p, hexSize, tessSize + i);
       }
       //Expand down and to the right;
        for(int i = tessSize - 2; i >= 0; i-- ){
            p = getBottomRightNeighbor(p, hexSize);
            addHexColumn(tiles,p, hexSize, tessSize + i);
        }
    }

    /**
     * Adds a column of hexagons, each of whose biomes are chosen randomly to the
     * world at position p. Each hexagons are of size SIZE

     */
    public static void addHexColumn(TETile[][] tiles, Position p, int size, int num){
        if(num < 1){
            throw new IllegalArgumentException("Size is not big enough");
        }
        //Draw this hexagon
        addHexagon1(tiles,p,randomTile(),size);
        if(num > 1){
            Position bottomNeighbor = getBottomNeighbor(p, size);
            addHexColumn(tiles, bottomNeighbor,size, num - 1);
        }
    }

    //n is the size of Hexagon
    public static Position getBottomNeighbor(Position p,int n){
        return p.shift(0, -2*n);
    }


    public static void fileBoardWithNothing(TETile[][] world){
        for(int i = 0; i < world.length;i++){
            for(int j = 0; j < world[0].length;j++){
                world[i][j] = Tileset.NOTHING;
            }
        }
    }
    private static class Position{
        int x;
        int y;
        Position(int x, int y){
            this.x = x;
            this.y = y;
        }
        public Position shift(int dx, int dy){
            return new Position(this.x + dx, this.y+dy);
        }
    }
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.SAND;
            case 4: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }
    public static void main(String[] args) {
        TERenderer tre = new TERenderer();
        tre.initialize(width, height);

        int size = 2;
        TETile[][] world = new TETile[width][height];
        fileBoardWithNothing(world);
        Position anchor = new Position(12,34);
        drawWorld(world,anchor,3 ,4);
        /**
         * addHexagon(world, 3,5,size,Tileset.WATER);
         *   addHexagon(world, 8,8,5,Tileset.FLOWER);
         *      addHexagon(world, 13,23,3,Tileset.FLOOR);
         */
        tre.renderFrame(world);
    }
}
