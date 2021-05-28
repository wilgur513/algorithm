package com.boj.solved.bfs.p13460;

import java.util.*;

class Ball {
    char type;
    int y, x;

    public Ball(char type, int y, int x) {
        this.type = type;
        this.y = y;
        this.x = x;
    }
}

class Map {
    char[][] map;

    public Map(char[][] map) {
        this.map = map;
    }

    Map removeBall(Ball ball) {
        char[][] newMap = cloneMap();
        newMap[ball.y][ball.x] = '.';
        return new Map(newMap);
    }

    Map moveBall(Ball ball, int newY, int newX) {
        char[][] newMap = cloneMap();
        newMap[ball.y][ball.x] = '.';
        newMap[newY][newX] = ball.type;
        return new Map(newMap);
    }

    private char[][] cloneMap() {
        char[][] newMap = new char[map.length][];
        for(int y = 0; y < map.length; y++) {
            newMap[y] = new char[map[y].length];
            for(int x = 0; x < map[y].length; x++) {
                newMap[y][x] = map[y][x];
            }
        }
        return newMap;
    }

    public Ball[] findBalls() {
        return new Ball[]{findBall('R'), findBall('B')};
    }

    private Ball findBall(char type) {
        for(int y = 0; y < map.length; y++) {
            for(int x = 0; x < map[y].length; x++) {
                if(map[y][x] == type){
                    return new Ball(type, y, x);
                }
            }
        }

        return new Ball(type, -1,- 1);
    }

    boolean isHole(int y, int x) {
        return map[y][x] == 'O';
    }

    boolean isEmpty(int y, int x) {
        return isValid(y, x) && map[y][x] == '.';
    }

    private boolean isValid(int y, int x) {
        return (0 <= y && y < map.length) && (0 <= x && x < map[y].length);
    }

    boolean isRedBallInHole() {
        Ball ball = findBall('R');
        return ball.y == -1 && ball.x == -1;
    }

    boolean isBlueBallInHole() {
        Ball ball = findBall('B');
        return ball.y == -1 && ball.x == -1;
    }

    @Override
    public boolean equals(Object obj) {
        Map m = (Map)obj;

        for(int y = 0; y < map.length; y++) {
            for(int x = 0; x < map[y].length; x++) {
                if(map[y][x] != m.map[y][x])
                    return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;

        for(Ball ball : findBalls()) {
            result *= 27;
            result += ball.y;
            result *= 27;
            result += ball.x;
        }

        return result;
    }
}

abstract class TiltHandler {
    int newY, newX;

    Ball[] sortBalls(Ball[] balls) {
        return new Ball[]{front(balls), behind(balls)};
    }

    public int newY() {
        return newY;
    }

    public int newX() {
        return newX;
    }

    protected abstract Ball behind(Ball[] balls);
    protected abstract Ball front(Ball[] balls);
    public abstract void setInit(Ball ball);
    abstract void moveNext();
    abstract int adjustY();
    abstract int adjustX();
}

class LeftTiltHandler extends TiltHandler {
    @Override
    protected Ball behind(Ball[] balls) {
        return balls[0].x < balls[1].x ? balls[1] : balls[0];
    }

    @Override
    protected Ball front(Ball[] balls) {
        return balls[0].x < balls[1].x ? balls[0] : balls[1];
    }

    @Override
    public void setInit(Ball ball) {
        newY = ball.y;
        newX = ball.x - 1;
    }

    @Override
    void moveNext() {
        newX--;
    }

    @Override
    int adjustY() {
        return newY;
    }

    @Override
    int adjustX() {
        return newX + 1;
    }
}

class RightTiltHandler extends TiltHandler {
    @Override
    protected Ball behind(Ball[] balls) {
        return balls[0].x < balls[1].x ? balls[0] : balls[1];
    }

    @Override
    protected Ball front(Ball[] balls) {
        return balls[0].x < balls[1].x ? balls[1] : balls[0];
    }

    @Override
    public void setInit(Ball ball) {
        newY = ball.y;
        newX = ball.x + 1;
    }

    @Override
    void moveNext() {
        newX++;
    }

    @Override
    int adjustY() {
        return newY;
    }

    @Override
    int adjustX() {
        return newX - 1;
    }
}

class UpTiltHandler extends TiltHandler {
    @Override
    protected Ball behind(Ball[] balls) {
        return balls[0].y < balls[1].y ? balls[1] : balls[0];
    }

    @Override
    protected Ball front(Ball[] balls) {
        return balls[0].y < balls[1].y ? balls[0] : balls[1];
    }

    @Override
    public void setInit(Ball ball) {
        newY = ball.y - 1;
        newX = ball.x;
    }

    @Override
    void moveNext() {
        newY--;
    }

    @Override
    int adjustY() {
        return newY + 1;
    }

    @Override
    int adjustX() {
        return newX;
    }
}

class DownTiltHandler extends TiltHandler {
    @Override
    protected Ball behind(Ball[] balls) {
        return balls[0].y < balls[1].y ? balls[0] : balls[1];
    }

    @Override
    protected Ball front(Ball[] balls) {
        return balls[0].y < balls[1].y ? balls[1] : balls[0];
    }

    @Override
    public void setInit(Ball ball) {
        newY = ball.y + 1;
        newX = ball.x;
    }

    @Override
    void moveNext() {
        newY++;
    }

    @Override
    int adjustY() {
        return newY - 1;
    }

    @Override
    int adjustX() {
        return newX;
    }
}

class Player {
    Map tilt(Map map, TiltHandler handler) {
        Ball[] balls = handler.sortBalls(map.findBalls());
        Map moveFrontBall = moveBall(map, balls[0], handler);
        return moveBall(moveFrontBall, balls[1], handler);
    }

    private Map moveBall(Map map, Ball ball, TiltHandler handler) {
        handler.setInit(ball);
        return moveBallHelp(map, ball, handler);
    }

    private Map moveBallHelp(Map map, Ball ball, TiltHandler handler) {
        if(map.isHole(handler.newY(), handler.newX())){
            return map.removeBall(ball);
        }

        if(!map.isEmpty(handler.newY(), handler.newX())){
            return map.moveBall(ball, handler.adjustY(), handler.adjustX());
        }

        handler.moveNext();
        return moveBallHelp(map, ball, handler);
    }
}

class Game {
    static class Pair {
        Map map;
        int count;

        public Pair(Map map, int count) {
            this.map = map;
            this.count = count;
        }
    }

    static final TiltHandler LEFT = new LeftTiltHandler();
    static final TiltHandler RIGHT = new RightTiltHandler();
    static final TiltHandler UP = new UpTiltHandler();
    static final TiltHandler DOWN = new DownTiltHandler();
    static final Player PLAYER = new Player();
    private Set<Map> visited;

    int minCountOfTilt(Map map) {
        visited = new HashSet<>();
        return minCountOfTilt(firstTiltedMapPair(map));
    }

    private Queue<Pair> firstTiltedMapPair(Map map) {
        Queue<Pair> queue = new LinkedList<>();
        queue.addAll(tiltedAllWayMapList(map, 1));
        return queue;
    }

    private List<Pair> tiltedAllWayMapList(Map map, int count) {
        List<Pair> result = new ArrayList<>();
        result.addAll(makePairList(LEFT, map, count));
        result.addAll(makePairList(RIGHT, map, count));
        result.addAll(makePairList(UP, map, count));
        result.addAll(makePairList(DOWN, map, count));
        return result;
    }

    private List<Pair> makePairList(TiltHandler handler, Map map, int count) {
        Map tiltedMap = PLAYER.tilt(map, handler);

        if(!visited.contains(tiltedMap)) {
            visited.add(tiltedMap);
            return Arrays.asList(new Pair(tiltedMap, count));
        }

        return new ArrayList<>();
    }

    private int minCountOfTilt(Queue<Pair> queue) {
        if(queue.isEmpty()){
            return -1;
        }

        Pair current = queue.remove();
        Map map = current.map;
        int count = current.count;

        if(map.isRedBallInHole() && !map.isBlueBallInHole()){
            return count;
        }

        if(!map.isBlueBallInHole() && count < 10) {
            queue.addAll(tiltedAllWayMapList(map, count + 1));
        }

        return minCountOfTilt(queue);
    }
}

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        System.out.println(game.minCountOfTilt(makeMap()));
    }

    private static Map makeMap() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        char[][] map = new char[n][m];

        for(int y = 0; y < n; y++) {
            String line = scanner.next();
            map[y] = line.toCharArray();
        }

        return new Map(map);
    }
}
