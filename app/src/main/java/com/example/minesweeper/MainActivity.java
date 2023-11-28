package com.example.minesweeper;

import static com.example.minesweeper.BlockButton.MINES;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    static final int ROW = 9;
    static final int COL = 9;
    private static BlockButton[][] buttons = new BlockButton[ROW][COL];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        for (int i = 0; i < ROW; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < COL; j++) {
                buttons[i][j] = new BlockButton(this, i, j);
                buttons[i][j].setText(" ");

                // 버튼 크기 동일하게 하기 위해 weight 설정
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f);
                buttons[i][j].setLayoutParams(layoutParams);

                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        ((BlockButton)view).toggleFlag();

                    }
                });
                tableRow.addView(buttons[i][j]);
            }
            tableLayout.addView(tableRow);
        }

  /*      toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isToggleButtonOn = ((ToggleButton) view).isChecked();

                if (isToggleButtonOn) {
                    // On 상태: 깃발 꽂기/해제
                    // 남은 지뢰 수 표시
                    updateRemainingMines(); // 남은 지뢰 수 갱신 메서드를 정의해야 합니다.
                } else {
                    // Off 상태: 블록 열기
                    openBlock();
                }
*/
    }


    private void checkGameResult() {
        if (BlockButton.getFlags() == MINES && BlockButton.getBlocks() == 0) {
            disableAllBlocks();
            BlockButton.gameOver(this);
        } else if (BlockButton.getFlags() != MINES && BlockButton.getBlocks() == 0) {
            disableAllBlocks();
            BlockButton.win(this);
        }
    }

    protected static void disableAllBlocks() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                buttons[i][j].setClickable(false);
            }
        }
    }
}