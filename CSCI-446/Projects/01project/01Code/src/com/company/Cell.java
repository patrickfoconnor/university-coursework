package com.company;

public class Cell {
    private int value;
    private boolean changeFlag;

    public Cell(){
        value = -1;
        changeFlag = false;
    }

    public int getValue() {
        return value;
    }

    public boolean getFlag(){
        return changeFlag;
    }

    public void setValue(int inValue){
        value = inValue;
    }
    public void setChangeFlag(boolean flag){
        changeFlag = flag;
    }
}
