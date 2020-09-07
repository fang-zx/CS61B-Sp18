package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.Transaction;

import java.util.*;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 101;
    public static final int HEIGHT = 49;
    private static int wallsAround[][] = new int[WIDTH][HEIGHT];
    private static Random random;
    private static List<Room> rooms;
    private static boolean visited[][] = new boolean[WIDTH][HEIGHT];

    enum Direction { UP, DOWN, LEFT, RIGHT; }
    private static class Position {
        int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    /** A rectangle room, (startX, startY) is the coordinate of it bottom left corner */
    private static class Room {
        int startX, startY;
        int height, width;

        public Room (int startX, int startY, int width, int height) {
            this.startX = startX;
            this.startY = startY;
            this.width = width;
            this.height = height;
        }
    }


    private static boolean isOverlap(Room b) {
        if (b.startX == 0 || b.startY == 0 || b.startX + b.width == WIDTH ||  b.startY + b.height == HEIGHT) {
            return true;
        }
        for (int i = 0; i < rooms.size(); i++) {
            Room a = rooms.get(i);
            int ax1 = a.startX, ax2 = a.startX + a.width, ay1 = a.startY, ay2 = a.startY + a.height;
            int bx1 = b.startX, bx2 = b.startX + b.width, by1 = b.startY, by2 = b.startY + b.height;

            if (ax1 <= bx1 && ax2 >= bx1 || bx1 <= ax1 && bx2 >= ax1) {
                if ((ay1 <= by1 && ay2 >= by1) || (by1 <= ay1 && by2 >= ay1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void fillARoom(TETile[][] world, Room r) {
        for (int i = r.startX; i < r.startX + r.width; i++) {
            for (int j = r.startY; j < r.startY + r.height; j++) {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }
    private void createRooms(TETile[][] world, int n) {
        int nAttempt = random.nextInt(99) + 10;
        for (int i = 0; i < nAttempt; i++) {
            int size = (random.nextInt(2) + 1) * 2 + 1;
            int width = size;
            int height = size;
            int regularity = random.nextInt(size / 2 + 1) * 2;

            if (0 == random.nextInt(100) % 2) {
                width += regularity;
            } else {
                height += regularity;
            }
            int xPos = random.nextInt((WIDTH - width) / 2 * 2 + 1);
            int yPos = random.nextInt((HEIGHT - height) / 2 * 2 + 1);

            Room newRoom = new Room(xPos, yPos, width, height);
            if (!isOverlap(newRoom)) {
                fillARoom(world, newRoom);
                rooms.add(newRoom);
            }

        }
    }
    private boolean isWall(TETile[][] world, int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y > HEIGHT) return false;
        return world[x][y] == Tileset.WALL;
    }

    private boolean isWall(TETile[][] world, Position p) {
        return world[p.x][p.y] == Tileset.WALL;
    }


    private boolean canCarve(TETile[][] world, int x, int y, Direction directionFrom) {
        if (x == 0 ||  x == WIDTH - 1 || y == 0 ||y == HEIGHT - 1 || visited[x][y] == true) {
            return false;
        }
        switch (directionFrom) {
            case UP: return isWall(world, x - 1, y) && isWall(world, x + 1, y) && isWall(world, x, y - 1);
            case DOWN: return isWall(world, x - 1, y) && isWall(world, x + 1, y) && isWall(world, x, y + 1);
            case LEFT: return isWall(world, x + 1, y) && isWall(world, x, y - 1) && isWall(world, x, y + 1);
            case RIGHT: return isWall(world, x - 1, y) && isWall(world, x, y - 1) && isWall(world, x, y + 1);
            default: return false;
        }
    }

    private void initVisited() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                visited[i][j] = false;
            }
        }
    }
    public void growMaze(TETile[][] world, Position pos) {
        Stack<Position> stack = new Stack<>();
        initVisited();
        stack.push(pos);
        visited[pos.x][pos.y] = true;

        while (!stack.empty()) {
            Position now = stack.peek();
            visited[now.x][now.y] = true;
            world[now.x][now.y] = Tileset.FLOOR;
            List<Position> avail = new ArrayList<>();
            if (canCarve(world, now.x + 1, now.y, Direction.LEFT)) {
                avail.add(new Position(now.x + 1, now.y));
            }
            if (canCarve(world, now.x - 1, now.y, Direction.RIGHT)) {
                avail.add(new Position(now.x - 1, now.y));
            }
            if (canCarve(world, now.x, now.y + 1, Direction.DOWN)) {
                avail.add(new Position(now.x, now.y + 1));
            }
            if (canCarve(world, now.x, now.y - 1, Direction.UP)) {
                avail.add(new Position(now.x, now.y - 1));
            }
            if (avail.isEmpty()) {
                stack.pop();
            } else {
                int rand = random.nextInt(avail.size());
                stack.push(avail.get(rand));
            }
            ter.renderFrame(world);
        }
    }

    public void countWallsAround(TETile[][] world) {
        for (int k = 1; k < WIDTH - 1; k++) {
            for (int l = 1; l < HEIGHT - 1; l++) {
                wallsAround[k][l] = 0;
                for (int i = k - 1; i <= k + 1; i++) {
                    for (int j = l - 1; j <= l + 1; j++) {
                        if (i == k && j == l) continue;
                        else {
                            if (world[i][j] == Tileset.WALL) {
                                wallsAround[k][l]++;
                            }
                        }
                    }
                }
            }
        }
    }
    public void processMap(TETile[][] world, Position p) {

        if (world[p.x][p.y] == Tileset.WALL) {
            if (wallsAround[p.x][p.y] < 5 || wallsAround[p.x][p.y] >= 7) {
                world[p.x][p.y] = Tileset.FLOOR;
            }
        } else {
            if (wallsAround[p.x][p.y] >= 4 || wallsAround[p.x][p.y] < 2) {
                world[p.x][p.y] = Tileset.WALL;
            }
        }
    }

    private int wallsAround(TETile[][] world, int x, int y) {
        int walls = 0;
        if (isWall(world, x+1, y)) walls++;
        if (isWall(world, x-1, y)) walls++;
        if (isWall(world, x, y+1)) walls++;
        if (isWall(world, x, y-1)) walls++;
        return walls;
    }
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }



    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        // gotta change later
        int seed = Integer.parseInt(input);
        random = new Random(seed);

        int nHallway;
        int nRoom = random.nextInt(30);
        rooms = new ArrayList<>(nRoom);

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                finalWorldFrame[i][j] = Tileset.WALL;
            }
        }

        createRooms(finalWorldFrame, nRoom);
        ter.renderFrame(finalWorldFrame);


        boolean flag = true;
            for (int i = 1; i < WIDTH && flag; i += 2) {
                for (int j = 1; j < HEIGHT && flag; j += 2) {
                    Position pos = new Position(i, j);
                    if (isWall(finalWorldFrame, pos) && wallsAround(finalWorldFrame, i, j) == 4) {
                        growMaze(finalWorldFrame, pos);
                        flag = false;
                        //processMap(finalWorldFrame, pos);
                    }
                }
            }


        return finalWorldFrame;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Game game = new Game();
        TETile[][] world =  game.playWithInputString("12");
        ter.renderFrame(world);
    }
}
