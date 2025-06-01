package wumpus;

import java.util.ArrayList;
import java.util.HashSet;

import wumpus.Environment.Element;

public class Tile {
    private final int x, y, w, h;
    private final HashSet<Environment.Element> elements = new HashSet<>();

    public Tile(int position, int width, int height) {
        x = position % width;
        y = position / width;
        w = width;
        h = height;
    }


    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public int getIndex() {
        return (x + y * w);
    }

    public int getIndex(int x, int y) {
        return (x + y * w);
    }

    public int[] getAdjacent() {
        int[] adjacent = {-1,-1,-1,-1};
        int north = y - 1;
        int east  = x + 1;
        int south = y + 1;
        int west  = x - 1;

        if (north >= 0){
            adjacent[0] =getIndex(x,north);
        }
        if (east < w){
            adjacent[1] =getIndex(east,y);
        }
        if (south < h){
            adjacent[2] =getIndex(x,south);
        }
        if (west >= 0){
            adjacent[3] =getIndex(west,y);
        }
        return adjacent;
    }

    public ArrayList<Integer> getArrowPath(Environment.Direction direction, Tile neighbor) {
        ArrayList<Integer> arrowPath = new ArrayList<>();
        switch (direction){
            case NORTH -> {
                int northY = neighbor.getY();
                while (northY > 0 && northY < h){
                    Integer tileIndex = getIndex(x,northY);
                    arrowPath.add(tileIndex);
                    northY--;
                }
            }
            case EAST -> {
                int eastX = neighbor.getX();
                while (eastX > 0 && eastX < w){
                    Integer tileIndex = getIndex(eastX,y);
                    arrowPath.add(tileIndex);
                    eastX--;
                }
            }
            case SOUTH -> {
                int southY = neighbor.getY();
                while (southY > -1 && southY <= h){
                    Integer tileIndex = getIndex(x,southY);
                    arrowPath.add(tileIndex);
                    southY--;
                }
            }
            case WEST -> {
                int westX = neighbor.getX();
                while (westX > -1 && westX <= w){
                    Integer tileIndex = getIndex(x,y);
                    arrowPath.add(tileIndex);
                    westX--;
                }
            }
        }
        return arrowPath;
    }

    public void clear() {
        elements.clear();
    }

    public boolean isEmpty(){
        return elements.isEmpty();
    }

    public boolean cellContains(Element element) {
        return elements.contains(element);
    }

    public void setItem(Element element) {
        elements.add(element);
    }

    public void remove(Environment.Element element) {
        elements.remove(element);
    }

}


