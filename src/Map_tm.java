import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.ArrayList;
import java.util.List;

public final class Map_tm {

    /*
        Not sure if graph object is represented with RenderEngine, and if not, what I am supposed to use.
        Also not sure how to make Mapper class
     */
    private RenderEngine_tm engine;
    private List<Tile_tm> tiles;
    //private Mapper mapper;
    private int lowestX,lowestY,highestX,highestY;

    public Map_tm(int lowestX, int lowestY, int highestX, int highestY){
        this.lowestX = lowestX;
        this.lowestY = lowestY;
        this.highestX = highestX;
        this.highestY = highestY;
        tiles = new ArrayList<Tile_tm>();
        Tile_tm spawn = new SpawnTile(new Tile_tm[4],null,0,0,0,0);
        tiles.add(spawn);
        engine = null;
        //mapper = null;
    }

    /*
    Not sure how to implement this method
     */
    public void remap(){

    }

    public int getLowestX(){
        return lowestX;
    }

    public int getLowestY(){
        return lowestY;
    }

    public int getHighestX(){
        return highestX;
    }

    public int getHighestY(){
        return highestY;
    }

    public Tile_tm getTile(int x, int y){
        if(x < lowestX || x > highestX || y < lowestY || y > highestY) return null;
        for(Tile_tm t: tiles){
            if(x > t.getStartXPos() && x < t.getEndXPos() && y > t.getStartYPos() && y < t.getEndYPos()) return t;
        }
        return null;
    }
}
