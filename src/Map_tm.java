import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joseph Grieser
 */
public final class Map_tm {

    /*
        Not sure if graph object is represented with RenderEngine, and if not, what I am supposed to use.
        Also not sure how to make Mapper class
     */
    //private RenderEngine_tm engine;
    private List<Tile_tm> tiles;
    private Mapper mapper;
    private int lowestX,lowestY,highestX,highestY;

    public Map_tm(int lowestX, int lowestY, int highestX, int highestY){
        this.lowestX = lowestX;
        this.lowestY = lowestY;
        this.highestX = highestX;
        this.highestY = highestY;
        tiles = new ArrayList<Tile_tm>();
        Tile_tm spawn = new SpawnTile(null,0,0);
        tiles.add(spawn);
        //engine = null;
        mapper = null;
    }

    /*
    Not sure how to implement this method
     */

    /**
     * sets path for tiles
     * @param fileName file path will be read from
     */
    public void remap(String fileName) {
        try {
            mapper.setData(fileName);
        }
        catch(IOException e){
            System.out.println(fileName + " not found");
        }
    }

    /**
     * gets lowest possible x value in map
     * @return lowest possible x value in map
     */
    public int getLowestX(){
        return lowestX;
    }

    /**
     * gets lowest possible y value in map
     * @return lowest possible y value in map
     */
    public int getLowestY(){
        return lowestY;
    }

    /**
     * gets highest possible x value in map
     * @return highest possible x value in map
     */
    public int getHighestX(){
        return highestX;
    }

    /**
     * gets highest possible y value in map
     * @return highest possible y value in map
     */
    public int getHighestY(){
        return highestY;
    }

    /**
     * gets tile at coordinates
     * @param x x-coordinate for tile
     * @param y y-coordinate for tile
     * @return tile at coordinates; null if coordinates out of bounds or no tile at coordinates
     */
    public Tile_tm getTile(int x, int y){
        if(x < lowestX || x > highestX || y < lowestY || y > highestY) return null;
        for(Tile_tm t: tiles){
            if(x > t.getPosX() && x < t.getEndXPos() && y > t.getPosY() && y < t.getEndYPos()) return t;
        }
        return null;
    }

    /**
     * Enum of directions to neighboring tiles; may move to Mapper class
     */
    public enum Direction{

        NORTH {
            @Override
            public int index() {
                return 0;
            }

            @Override
            public int reverse(){
                return 2;
            }
        },

        EAST{
            public int index(){
                return 1;
            }

            public int reverse(){
                return 3;
            }
        },

        SOUTH{
            public int index(){
                return 2;
            }

            public int reverse(){
                return 0;
            }
        },

        WEST{
            public int index(){
                return 3;
            }

            public int reverse(){
                return 1;
            }
        };

        /**
         * gets index for neighbor array in tile
         * @return index for neighbor array in tile
         */
        public abstract int index();

        /**
         * gets index of opposite direction for neighbor array in tile
         * @return index of opposite direction for neighbor array in tile
         */
        public abstract int reverse();

//        NORTH(0),EAST(1),SOUTH(2),WEST(3);
//
//        Direction(int index){
//            this.index = index;
//            reverse = 3 - index + 1;
//        }
//
//        public int index;
//        public int reverse;
    }
}
