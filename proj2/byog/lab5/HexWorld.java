package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;

    private static final Random RANDOM = new Random();
    static class Position {
        int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static int hexRowWidth(int s, int i) {
        if (i < s) {
            return s + i * 2;
        } else {
            return 5 * s - 2 * i - 2;
        }
    }

    public static int hexRowOffset(int s, int i) {
        if (i < s) {
            return s - i - 1;
        } else {
            return i - s;
        }
    }

    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        for (int yi = p.y; yi < p.y + 2 * s; yi++) {
            int offset = hexRowOffset(s, yi - p.y);
            int width = hexRowWidth(s, yi - p.y);

            for (int xi = p.x + offset; xi < p.x + offset + width; xi++) {
                world[xi][yi] = t;
            }
        }
    }

    private static void setPositions(Position[] pos, int s) {
        int a = 2 * s - 1;
        int curIdx = 0;
        for (int i = 0; i < 9; i++) {
            if (i % 2 == 0) {
                pos[curIdx++] = new Position(2 * a, i * s);
                if (i == 2 || i == 4 || i == 6) {
                    pos[curIdx++] = new Position(0, i * s);
                    pos[curIdx++] = new Position(4 * a, i * s);
                }
            } else {
                pos[curIdx++] = new Position(a, i * s);
                pos[curIdx++] = new Position(3 * a, i * s);
            }
        }
    }

    /** Picks a RANDOM tile with a 33% change of being
     *  a wall, 33% chance of being a flower, and 33%
     *  chance of being empty space.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(11);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.TREE;
            case 3: return Tileset.SAND;
            case 4: return Tileset.GRASS;
            case 5: return Tileset.FLOOR;
            case 6: return Tileset.PLAYER;
            case 7: return Tileset.LOCKED_DOOR;
            case 8: return Tileset.UNLOCKED_DOOR;
            case 9: return Tileset.MOUNTAIN;
            case 10: return Tileset.WATER;
            default: return Tileset.NOTHING;
        }
    }

    public static void main(String[] args) {
        Position[] positions = new Position[19];
        int s = 6;
        setPositions(positions, s);

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH,  HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        for (int i = 0; i < positions.length; i++) {
            TETile tile = randomTile();
            addHexagon(world, positions[i], s, tile);
        }

        ter.renderFrame(world);
    }


}
