package com.algospot.chapter9.zimbabwe.refactor;

import java.util.*;

final public class PossiblePriceCounter{
    private final static int DIVISOR = 1000000007;
    private static String currentPrice;
    private static int divisor;
    private static List<Integer> values;
    private static int[][] result;

    private PossiblePriceCounter() { }

    public static int count(String e, int m){
        initialize(e, m);

        return countHelp(new Price(values.size()),new Visitor(values.size()));
    }

    private static void initialize(String e, int m) {
        currentPrice = e;
        divisor = m;
        values = new ArrayList<>();
        result = new int[m][1 << e.length()];

        for(int i=0; i<e.length(); i++){
            int value = Integer.valueOf(String.valueOf(e.charAt(i)));
            values.add(value);
        }

        for(int i=0; i<m; i++)
            Arrays.fill(result[i], -1);

        Collections.sort(values);
    }

    private static int countHelp(Price price, Visitor visitor){
        if(visitor.isAllVisited()){
            if(price.isSmallThan(currentPrice) && price.isDrainage(divisor))
                return 1;
            else
                return 0;
        }

        return selectNextValueAndCountIfPossiblePrice(price, visitor);
    }

    private static int selectNextValueAndCountIfPossiblePrice(Price price, Visitor visitor) {
        if(result[price.mod(divisor)][visitor.bitMask()] != -1 && price.isNotPartiallyEqual(currentPrice))
            return result[price.mod(divisor)][visitor.bitMask()];

        result[price.mod(divisor)][visitor.bitMask()] = 0;

        for(int node=0; node<values.size(); node++)
            if(visitor.isNotVisited(node) && visitor.isNotPreviousValue(values.get(node))) {
                result[price.mod(divisor)][visitor.bitMask()] += makeNewPriceAndGetNumberOfPossiblePrice(price, visitor, node);
                result[price.mod(divisor)][visitor.bitMask()] %= DIVISOR;
            }

        return result[price.mod(divisor)][visitor.bitMask()];
    }

    private static int makeNewPriceAndGetNumberOfPossiblePrice(Price price, Visitor visitor, int node) {
        Price newPrice = price.append(values.get(node));
        return getNumberOfPossiblePrice(newPrice, visitor, node);
    }

    private static int getNumberOfPossiblePrice(Price price, Visitor visitor, int node) {
        if(price.isSmallThan(currentPrice)){
            visitor.setPreviousValue(values.get(node));
            Visitor newVisitor = visitor.visit(node);

            return countHelp(price, newVisitor);
        }

        return 0;
    }
}
