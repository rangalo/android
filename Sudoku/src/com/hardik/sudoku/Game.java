package com.hardik.sudoku;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.security.KeyPair;

/**
 * Created by IntelliJ IDEA.
 * User: hardik
 * Date: 6/11/11
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class Game extends Activity {

    public static final String KYE_DIFFICULTY = "com.hardik.sudoku.DIFFICULTY";

    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_MEDIUM = 1;
    public static final int DIFFICULTY_HARD = 2;

    private final String easyPuzzle = "360000000004230800000004200"+
            "070460003820000014500013020"+
            "001900000007048300000000045";


    private final String mediumPuzzle = "650000070000506000014000005"+
            "007009000002314700000700800"+
            "500000630000201000030000097";


    private final String hardPuzzle = "009000000080605020501078000"+
            "000000700706040102004000000"+
            "000720903090301080000000600";

    private static final String TAG = "Sudoku";

    private final int used[][][] = new int[9][9][];

    private int puzzle[] = new int[9 * 9];
    private PuzzleView puzzleView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Game: onCreate start");
        int difficulty = getIntent().getIntExtra(KYE_DIFFICULTY,DIFFICULTY_EASY);
        puzzle = getPuzzle(difficulty);
        calculateUsedTiles();

        puzzleView = new PuzzleView(this);
        setContentView(puzzleView);
        puzzleView.requestFocus();
        Log.d(TAG, "Game: onCreate end");
    }

    public void showKeypadOrError(int x, int y) {
        int tiles[] = getUsedTiles(x,y);
        if(tiles.length == 9) {
            Toast toast = Toast.makeText(this, R.string.lbl_no_moves,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        } else {
            Log.d(TAG, "Game: showKeypadOrError: used=" + toPuzzleString(tiles));
            Dialog v = new Keypad(this, tiles, puzzleView);
            v.show();
        }

    }

    public int[] getUsedTiles(int x, int y) {
        return used[x][y];
    }

    public boolean setTileIfValid(int x, int y, int value) {
        int tiles[] = getUsedTiles(x,y);
        if (value != 0) {
            for (int tile: tiles) {
                if (tile == value)
                    return false;
            }
        }
        setTile(x,y,value);
        calculateUsedTiles();
        return true;
    }

    private void calculateUsedTiles() {
        for (int x =0; x < 9; x++) {
            for (int y =0; y < 9; y++) {
                used[x][y] = calculateUsedTiles(x,y);
            }
        }
    }


    private int[] calculateUsedTiles(int x, int y) {
        int c[] = new int[9];

        // horizontal
        for (int i =0; i < 9; i++) {
            if (i == y)
                continue;
            int t = getTile(x,i);
            if (t != 0) {
                c[t-1] = t;
            }

        }

        // vertical
        for (int i=0; i < 9; i++) {
            if (i == x)
                continue;
            int t = getTile(i,y);
            if (t != 0)
                c[t-1] = t;
        }

        // same cell block
        int startx = (x / 3) * 3;
        int starty = (x / 3) * 3;
        for (int i = startx; i < startx + 3; i++) {
            for (int j = starty; j < starty + 3; j++) {
                if (i == x && j == y)
                    continue;
                int t = getTile(i,j);
                if (t != 0)
                    c[t-1] = t;
            }
        }

        // compress
        int nused = 0;
        for (int t : c) {
            if (t != 0) {
                nused++;
            }
        }
        int c1[] = new int[nused];
        nused = 0;
        for (int t : c) {
            if (t != 0)
                c1[nused++] = t;
        }
        return c1;
    }

    private int[] getPuzzle(int difficulty) {
        String puz;
        switch (difficulty) {
            case DIFFICULTY_HARD:
                puz = hardPuzzle;
                break;
            case DIFFICULTY_MEDIUM:
                puz = mediumPuzzle;
                break;
            case DIFFICULTY_EASY:
                puz = easyPuzzle;
                break;
            default:
                puz = easyPuzzle;
                break;

        }
        return fromPuzzleString(puz);
    }

    private static String toPuzzleString(int[] puz) {
        StringBuilder buf = new StringBuilder();
        for (int element : puz) {
            buf.append(element);
        }
        return buf.toString();
    }

    private static int[] fromPuzzleString(String string) {
        int[] puz = new int[string.length()];
        for (int i =0; i < puz.length; i++) {
            puz[i] = string.charAt(i) - '0';
        }
        return puz;
    }


    private int getTile(int x, int y) {
        return puzzle[y * 9 + x];
    }

    private void setTile(int x, int y, int value) {
        puzzle[y * 9 + x] = value;
    }

    public String getTitleString(int x, int y) {
        int v = getTile(x,y);
        if (v == 0)
            return "";
        else
            return String.valueOf(v);
    }
}