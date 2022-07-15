import processing.core.PApplet;

import java.util.Random;

public class Main extends PApplet {
    static final int DESK_SIZE = 400;

    float x, y, w, h;
    int squares, square_size, moves;

    static final int NUM_SHUFFLE_MOVES = 500;
    static Random rnd = new Random();
    static int[][] gameBoard = new int[5][5];
    static int emptyRow, emptyCol;

    public void settings() {
        fullScreen();
    }

    public void setup() {
        x = width / 2f - width / 8f;
        y = height / 4f;
        squares = 5;
        square_size = DESK_SIZE / squares;
        moves = 0;
        textAlign(CENTER, CENTER);
        initBoard();
        shuffleBoard();
    }

    public void draw() {
        background(0, 0, 0);

        fill(255, 255, 0);

        textSize(70);
        text("Game 15", width / 2f, height / 10f);

        textSize(50);
        text("Moves: " + moves, width / 2f + width / 3f, height / 2f);

        textSize(60);
        text("Start/Restart: Enter", width / 2f, height - height / 10f);

        fill(0, 0, 0);
        stroke(200, 200, 200);
        square(x, y, DESK_SIZE);

        for (int i = 0; i < squares; i++) {
            for (int j = 0; j < squares; j++) {
                w = x + i * square_size;
                h = y + j * square_size;
                if (gameBoard[i][j] == 25) {
                    continue;
                }
                fill(0, 0, 255);
                square(w, h, square_size);
                fill(255, 255, 0);
                textSize(80);
                text(gameBoard[i][j], w + square_size / 2f, h + square_size / 3f);
            }
        }
    }

    public void movingPeaces(int x, int y) {
        int newRow = emptyRow + x;
        int newCol = emptyCol + y;
        if (emptyRow + x >= 0 && emptyRow + x < 5 && emptyCol + y >= 0 && emptyCol + y < 5) {
            int temp = gameBoard[newRow][newCol];
            gameBoard[newRow][newCol] = 25;
            gameBoard[emptyRow][emptyCol] = temp;
            emptyRow = newRow;
            emptyCol = newCol;
            moves++;
        }
    }

    public void keyPressed() {
        switch (keyCode) {
            case UP -> movingPeaces(0, -1);
            case DOWN -> movingPeaces(0, 1);
            case LEFT -> movingPeaces(-1, 0);
            case RIGHT -> movingPeaces(1, 0);
            case ENTER -> {
                shuffleBoard();
                moves = 0;
            }
        }
    }

    private static void shuffleBoard() {
        int nMove = 0;
        while (nMove <= NUM_SHUFFLE_MOVES) {
            int dir = rnd.nextInt(4);
            int dc = 0;
            int dr = 0;
            switch (dir) {
                case 0 -> dr = -1;
                case 1 -> dc = 1;
                case 2 -> dr = 1;
                case 3 -> dc = -1;
            }
            if (emptyRow + dr >= 0 && emptyRow + dr < 5 && emptyCol + dc >= 0 && emptyCol + dc < 5) {
                gameBoard[emptyRow][emptyCol] = gameBoard[emptyRow + dr][emptyCol + dc];
                emptyRow += dr;
                emptyCol += dc;
                gameBoard[emptyRow][emptyCol] = 25;
                nMove++;
            }
        }
    }

    private static void initBoard() {
        int num = 1;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                gameBoard[i][j] = num;
                num++;
            }
        }
        emptyCol = 4;
        emptyRow = 4;
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}
