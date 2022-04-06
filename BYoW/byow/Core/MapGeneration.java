package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class MapGeneration {
    public static final int width = 50;
    public static final int height = 50;
    public static final Random seed = new Random(2000);
    public static final ArrayList<Position> roomPos = new ArrayList();
    public static void addRoom(TETile[][] world, int width, int height){
        int maxHeight = width + 2;
        int maxWidth = height + 2;
        for(int i = 0; i < maxWidth; i++){
            for(int j = 0; j < maxHeight;j++) {
                if (i == 0 || i == maxWidth - 1) {
                    world[i][j] = Tileset.WALL;
                    continue;
                }
                if(j == 0 || j == maxHeight - 1){
                    world[i][j] = Tileset.WALL;
                    continue;
                }
                else{
                    world[i][j] = Tileset.GRASS;
                }
            }
        }

    }
    public static void addOutDoor(TETile[][] input, int size,int pos){

    }

    public static void addHallway(TETile[][] input, int size, int type){
        if(type == 0) {
             horHallWay(input,size);
        }
        else if(type == 1) {
            verHallWay(input, size);
        }
        else{
            throw new IllegalArgumentException("The creator is not smart enough, plz enter two type of hall way");

        }
    }
    public static TETile[][] horHallWay(TETile[][]input,int size){
        int height = 3;
        int width = size + 2;
        for(int i = 0; i < width;i++){
            input[i][0] = Tileset.WALL;
            if(i == 0 || i == width - 1){
                input[i][1] = Tileset.WALL;
            }
            else{
                input[i][1] = Tileset.FLOOR;
            }
            input[i][2] = Tileset.WALL;
        }
        return input;
    }
    public static TETile[][] verHallWay(TETile[][]input, int size){
        int height = 3;
        int width = size + 2;
        for(int i = 0; i < width;i++){
            input[0][i] = Tileset.WALL;
            if(i == 0 || i == width - 1){
                input[1][i] = Tileset.WALL;
            }
            else{
                input[1][i] = Tileset.FLOOR;
            }
            input[2][i] = Tileset.WALL;
        }
        return input;

    }

    //Generating Random world
    private static void randomGenerateRoom(TETile[][] world) {
       int randomW = seed.nextInt(10);
       int randomH = seed.nextInt(10);
       int num_Room = seed.nextInt(24);
       for(int i = 0 ;i < num_Room; i++){
           addRoom(world,randomH,randomW);
       }
    }

    //Test map
    public static void fileBoardWithNothing(TETile[][] world){
        int WIDTH = world.length;
        int HEIGHT = world[0].length;
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

    }
    public static void drawMap(TETile[][] world){
        addHallway(world,6,1);
    }
    public static void addPosition(int numRoom){
        for(int i = 0; i < numRoom; i++){
            int width = seed.nextInt(MapGeneration.width);
            int height = seed.nextInt(MapGeneration.height);
            Position temp = new Position(width,height);
            roomPos.add(temp);
        }
    }
    //Test Room Position point;
    public static void drawPosition(TETile[][] world){
        for(int i = 0; i < roomPos.size();i++){
            Position temp = roomPos.get(i);
            world[temp.x][temp.y] = Tileset.WALL;
        }
    }

    public static class Position {
        public int x;
        public int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }
    public static void main(String[] args) {
        TERenderer mapTest = new TERenderer();
        mapTest.initialize(width,height);
        TETile[][] world = new TETile[width][height];
        fileBoardWithNothing(world);

        drawMap(world);

        mapTest.renderFrame(world);
    }
}
