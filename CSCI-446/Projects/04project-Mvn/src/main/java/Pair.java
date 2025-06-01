public class Pair {
    int x;
    int y;
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pair)) return false;
        Pair objectPair = (Pair) object;
        if(objectPair.x != this.x || objectPair.y != this.y){
            return false;
        }
        return true;
    }

}
