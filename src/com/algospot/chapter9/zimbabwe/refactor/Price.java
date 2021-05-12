package com.algospot.chapter9.zimbabwe.refactor;

final public class Price{
    private final int totalDigits;
    private final long price;
    private final int digit;

    public Price(int totalDigits){
        this.totalDigits = totalDigits;
        price = 0;
        digit = 0;
    }

    private Price(int totalDigits, long price, int digit){
        this.totalDigits = totalDigits;
        this.price = price;
        this.digit = digit;
    }

    public boolean isDrainage(int divisor){
        return price % divisor == 0;
    }

    public boolean isSmallThan(String targetPrice){
        return price < Long.valueOf(targetPrice);
    }

    public Price append(int value){
        return new Price(totalDigits, price + value * (long)Math.pow(10, totalDigits - digit - 1), digit + 1);
    }

    public boolean isNotPartiallyEqual(String targetPrice){
        long divisor = (long)Math.pow(10, totalDigits - digit);
        return Long.valueOf(targetPrice) / divisor != price / divisor;
    }

    public int mod(int divisor){
        return (int)(price % divisor);
    }
}
