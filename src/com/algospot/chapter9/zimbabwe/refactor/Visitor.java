package com.algospot.chapter9.zimbabwe.refactor;

final public class Visitor{
    private final int totalDigits;
    private final int bitMask;
    private int previousValue;

    public Visitor(int totalDigits) {
        this.totalDigits = totalDigits;
        bitMask = 0;
        previousValue = -1;
    }

    private Visitor(int totalDigits, int bitMask){
        this.totalDigits = totalDigits;
        this.bitMask = bitMask;
        previousValue = -1;
    }

    public boolean isAllVisited(){
        return bitMask == (1 << totalDigits) - 1;
    }

    public boolean isNotVisited(int index){
        return (bitMask & (1<< index)) == 0;
    }

    public Visitor visit(int index){
        return new Visitor(totalDigits, bitMask | (1 << index));
    }

    public int bitMask(){
        return bitMask;
    }

    public boolean isNotPreviousValue(int value){
        return (previousValue == -1 || previousValue != value);
    }

    public void setPreviousValue(int value) {
        previousValue = value;
    }
}
