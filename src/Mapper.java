import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Joseph Grieser
 */
public final class Mapper {

    private List<Tile_tm> tiles;
    private String data;
    private final int SIZE = 256;
    private Tile_tm last;

    public Mapper(String fileName) throws IOException{
        tiles = new ArrayList<Tile_tm>();
        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));
            data = in.readUTF();
        } catch(FileNotFoundException e){
            System.out.println(fileName + " not found");
        }
    }

    /**
     * gets data of tile path from file
     * @param fileName file containing tile path
     * @throws IOException file not found
     */
    public void setData(String fileName) throws IOException{
        //try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));
            data = in.readUTF();
        //} catch(IOException e){
        //    System.out.println(fileName + " not found");
        //}
        setTiles();
    }
    
    /**
     * Gets the data of the tile path from direct input- mostly for debugging
     * @param data The tile path 
     */
    public void setDataNoFile(String data) {
    	this.data = data;
    	setTiles();
    }

    /**
     * An alternative method of map population. This reads our data string by going one 
     * Token at a time- the token's length depends on which parcel of data is being analyzed.
     */
    public void setTilesAlt() {
    	if(data.indexOf(';') == -1) {
    		System.out.println("INVALID DATA- NO ACTION TAKEN");
    		return;
    	}
    	String coord = ""; //The coordinate pair at the start of data- this will be the position of source.
    	double xCoord = -1; //This is going to be the xCoordinate for the tile we are ~currently~ placing 
    	double yCoord; //This is going to be the yCoordinate for the tile we are ~currently~ placing  
    	int i = 0; 
    	//Find source tile coordinates
    	while(i < data.length()) {
    		if((data.charAt(i) < 48 && data.charAt(i) > 57) &&(data.charAt(i) != ';')) {
    			coord = data.substring(0, i);
    			i = data.length();
    		}
    		i++;
    	}
    	if(coord == "") 
    		coord = data;
    	xCoord = Double.parseDouble(coord.substring(0, coord.indexOf(';')));
    	yCoord = Double.parseDouble(coord.substring(coord.indexOf(';') + 1, coord.length()));
    	
    	//Create source tile
    	
    	//Create all subsequent tiles 
    	
    	
    }
    
    /**
     * sets tiles to path
     */
    public void setTiles(){
        char[] chars = data.toCharArray();
        //StringTokenizer tokenizer;
        if(chars[1] != ';') return;
        last = new SpawnTile(null,chars[0],chars[2]);
        last.setEntranceDist(0);
        tiles.add(last);
        double x = last.getPosX();
        double y = last.getPosY();
        Tile_tm tm;
        for(int i = 3; i < chars.length - 1; i+=2){
            switch(chars[i+1]){
                case 'N':
                    tm = new TrapTile(null,x+SIZE,y+SIZE);
                    tm.setEntranceDist(last.getEntranceDist()+1);
                    last = tm;
                    x = last.getPosX();
                    y = last.getPosY();
                    setTileType(tm,chars[i]);
                    break;
                case 'D':
                    tm = new DoorTile(null,x+SIZE,y+SIZE);
                    tm.setEntranceDist(last.getEntranceDist()+1);
                    last = tm;
                    x = last.getPosX();
                    y = last.getPosY();
                    setTileType(tm, chars[i]);
                    break;
                case 'T':
                    tm = new TreasureTile(null,x+SIZE,y+SIZE);
                    tm.setEntranceDist(last.getEntranceDist()+1);
                    last = tm;
                    x = last.getPosX();
                    y = last.getPosY();
                    setTileType(tm, chars[i]);
                    break;
            }
        }
    }

    /**
     * sets tile type and direction of tile from neighbor
     * @param tile tile being added to tiles
     * @param direction direction of tile compared to neighbor
     */
    private void setTileType(Tile_tm tile, char direction){
        switch(direction){
            case 'N':
                last.setNeighbor(tile, Map_tm.Direction.NORTH);
                tiles.add(tile);
                break;
            case 'E':
                last.setNeighbor(tile, Map_tm.Direction.EAST);
                tiles.add(tile);
                break;
            case 'S':
                last.setNeighbor(tile, Map_tm.Direction.SOUTH);
                tiles.add(tile);
                break;
            case 'W':
                last.setNeighbor(tile, Map_tm.Direction.WEST);
                tiles.add(tile);
                break;
        }
    }
}
