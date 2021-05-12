package com.algospot.chapter9.numbergame.wrong;

import java.util.*;

class States{
    enum State{
        IDLE(0), PLAYER1(1), PLAYER2(2), DISCARD(3);

        private final int value;

        State(int value) {
            this.value = value;
        }
    }

    private int size;
    private State[] states;
    private final List<Integer> list;

    public States(int size, List<Integer> list) {
        this.size = size;
        this.list = list;
        states = new State[size];

        for(int i=0; i<size; i++)
            states[i] = State.IDLE;
    }

    private States(int size, List<Integer> list, State[] states){
        this.size = size;
        this.list = list;
        this.states = states;
    }

    public boolean isFinished() {
        for(int i=0; i<size; i++){
            if(states[i] == State.IDLE)
                return false;
        }

        return true;
    }

    public int getScore(boolean player) {
        State s = player ? State.PLAYER1 : State.PLAYER2;
        int result = 0;

        for(int i=0; i<size; i++)
            if(states[i] == s)
                result += list.get(i);

        return result;
    }

    public boolean canDiscard() {
        return getRightIdleIndex() - getLeftIdleIndex() >= 2;
    }

    public States discardLeft(){
        int index = getLeftIdleIndex();
        State[] newStates = states.clone();
        newStates[index] = State.DISCARD;
        newStates[index + 1] = State.DISCARD;

        return new States(size, list, newStates);
    }

    public States discardRight(){
        int index = getRightIdleIndex();
        State[] newStates = states.clone();
        newStates[index] = State.DISCARD;
        newStates[index - 1] = State.DISCARD;

        return new States(size, list, newStates);
    }

    public States takeLeft(boolean player){
        int index = getLeftIdleIndex();
        State[] newStates = states.clone();
        newStates[index] = player ? State.PLAYER1 : State.PLAYER2;

        return new States(size, list, newStates);
    }

    public States takeRight(boolean player){
        int index = getRightIdleIndex();
        State[] newStates = states.clone();
        newStates[index] = player ? State.PLAYER1 : State.PLAYER2;

        return new States(size, list, newStates);
    }

    public int hash(){
        int result = 0;

        for(int i=0; i<size; i++){
            result += states[i].value;
            result *= 4;
        }

        return result;
    }

    private int getLeftIdleIndex() {
        int index = 0;

        for (int i = 0; i < size; i++)
            if (states[i] == State.IDLE) {
                index = i;
                break;
            }
        return index;
    }

    private int getRightIdleIndex() {
        int index = 0;

        for (int i = size - 1; i >= 0; i--)
            if (states[i] == State.IDLE) {
                index = i;
                break;
            }

        return index;
    }
}

public class Main {
    enum Result{
        LOSE(0), DRAW(1), WIN(2);

        private int value;

        private Result(int value){
            this.value = value;
        }

        public Result reverse(){
            if(value == 0)
                return Result.WIN;
            else if(value == 2)
                return Result.LOSE;
            else
                return Result.DRAW;
        }

        public Result maxValue(Result arg){
            return value < arg.value ? arg : this;
        }
    }

    private static Scanner scanner = new Scanner(System.in);

    private static void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() {
        int n = scanner.nextInt();
        List<Integer> list = makeList(n);
        Result result = play(new States(n, list), true);
        System.out.println(reconstruct(new States(n, list), result, true));
    }

    private static List<Integer> makeList(int size) {
        List<Integer> list = new ArrayList<>();

        for(int i=0; i<size; i++)
            list.add(scanner.nextInt());

        return list;
    }

    private static Result play(States states, boolean player) {
        if(states.isFinished()){
            int player1Score = states.getScore(true);
            int player2Score = states.getScore(false);

            if(player1Score == player2Score)
                return Result.DRAW;
            else if(player1Score < player2Score)
                return player ? Result.LOSE : Result.WIN;
            else
                return player ? Result.WIN : Result.LOSE;
        }

        Result result = Result.LOSE;

        if(states.canDiscard()) {
            result = result.maxValue(play(states.discardLeft(), !player).reverse());
            result = result.maxValue(play(states.discardRight(), !player).reverse());
        }

        result = result.maxValue(play(states.takeLeft(player), !player).reverse());
        result = result.maxValue(play(states.takeRight(player), !player).reverse());

        return result;
    }

    private static int reconstruct(States states, Result result, boolean player){
        if(states.isFinished()){
            return states.getScore(true) - states.getScore(false);
        }

        if(states.canDiscard()){
            if(result == play(states.discardLeft(), !player).reverse()){
                return reconstruct(states.discardLeft(), result.reverse(), !player);
            }

            if(result == play(states.discardRight(), !player).reverse()){
                return reconstruct(states.discardRight(), result.reverse(), !player);
            }
        }

        if(result == play(states.takeLeft(player), !player).reverse())
            return reconstruct(states.takeLeft(player), result.reverse(), !player);

        if(result == play(states.takeRight(player), !player).reverse())
            return reconstruct(states.takeRight(player), result.reverse(), !player);

        throw new AssertionError();
    }

    public static void main(String[] args) {
        Main.run();
    }
}
