package com.example.minesweeper;

import static com.example.minesweeper.MainActivity.COL;
import static com.example.minesweeper.MainActivity.ROW;

import android.graphics.Color;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.Toast;

import java.util.Random;

public class BlockButton extends androidx.appcompat.widget.AppCompatButton {
    static final int MINES =10;
    private int x, y;
    private boolean mine;
    private boolean flag;
    private int neighborMines;
    private static int flags;
    private static int blocks;
    private static BlockButton[][] buttons; // 추가: 버튼 배열

    public BlockButton(Context context, int x, int y) {
        super(context);
        this.x = x;
        this.y = y;

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setBackgroundColor(Color.LTGRAY);
        setTextColor(Color.BLACK);
        setFlag(false);
        setMine(false);
        setNeighborMines(0);
        setOnClickListener(view -> openBlock());
    }

    public void toggleFlag() {
        setFlag(!flag);
        if (flag) {
            flags++;
        } else {
            flags--;
        }
    }

    public boolean openBlock() {
        if (!isEnabled() || isFlag()) {
            return false;
        }

        setClickable(false);
        blocks--;

        if (isMine()) {
            setText("*");
            gameOver(getContext());
            return true;
        } else {
            int neighborMines = getNeighborMines();

            if (neighborMines == 0) {
                openSurroundingBlocks();
            } else {
                setText(Integer.toString(neighborMines));
            }

            if (blocks == 0) {
                win(getContext());
            }

            return false;
        }
    }

    private void openSurroundingBlocks() {
        // 재귀 호출로 구현
        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, ROW - 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, COL - 1); j++) {
                buttons[i][j].openBlock();
            }
        }
    }

    public static void gameOver(Context context) {
        Toast.makeText(context, "지뢰가 터져서 게임 종료", Toast.LENGTH_SHORT).show();
    }

    public static void win(Context context) {
        Toast.makeText(context, "모든 지뢰 찾기 성공", Toast.LENGTH_SHORT).show();
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
        if (flag) {
            setText("+");
        } else {
            setText("");
        }
    }

    public int getNeighborMines() {
        return neighborMines;
    }

    public void setNeighborMines(int neighborMines) {
        this.neighborMines = neighborMines;
    }

    public static int getFlags() {
        return flags;
    }

    public static int getBlocks() {
        return blocks;
    }

    public static void placeMines(BlockButton[][] buttons) {
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < MINES) {
            int row = random.nextInt(ROW);
            int col = random.nextInt(COL);

            if (!buttons[row][col].isMine()) {
                buttons[row][col].setMine(true);
                minesPlaced++;
            }
        }

        calculateNeighborMines(buttons);
    }

    private static void calculateNeighborMines(BlockButton[][] buttons) {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                int count = countNeighborMines(buttons, i, j);
                buttons[i][j].setNeighborMines(count);
            }
        }
    }

    private static int countNeighborMines(BlockButton[][] buttons, int row, int col) {
        int count = 0;

        for (int i = Math.max(0, row - 1); i <= Math.min(row + 1, ROW - 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(col + 1, COL - 1); j++) {
                if (buttons[i][j].isMine()) {
                    count++;
                }
            }
        }

        if (buttons[row][col].isMine()) {
            count--;
        }

        return count;
    }
}
