import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.ArrayList;
import java.util.List;

public final class Map_tm {

    private Graph graph;
    private List<Tile_tm> tiles;
    private Mapper mapper;
    private int x1,y1,x2,y2;

    public Map_tm(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y2;
        this.x2 = x2;
        this.y2 = y2;
        tiles = new ArrayList<Tile_tm>();
        graph = null;
        mapper = null;
    }

    public void remap(){

    }

    public int getX1(){
        return x1;
    }

    public int getY1(){
        return y1;
    }

    public int getX2(){
        return x2;
    }

    public int getY2(){
        return y2;
    }
}
