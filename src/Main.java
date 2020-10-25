import java.awt.*;
import java.util.Scanner;
/*
        遊戲名稱：康威生命機

        每個細胞與以自身為中心的周圍八格細胞產生互動（如圖，黑色為存活，白色為死亡）
        aliveCellRule
        當前細胞為存活狀態時，當周圍的存活細胞低於2個時（不包含2個），該細胞變成死亡狀態。（模擬生命數量稀少）
        當前細胞為存活狀態時，當周圍有2個或3個存活細胞時，該細胞保持原樣。
        當前細胞為存活狀態時，當周圍有超過3個存活細胞時，該細胞變成死亡狀態。（模擬生命數量過多）
        deathCellRule
        當前細胞為死亡狀態時，當周圍有3個存活細胞時，該細胞變成存活狀態。（模擬繁殖）

        check round :1 2 3
                     8 0 4
                     7 6 5

        What is Column :--------
        What is Row :|
                     |
                     |
                     |
*/
public class Main {

    private static final Scanner INPUT = new Scanner(System.in);
    private static GameWindow window = null;
    private static final boolean DEATH = false, ALIVE = true;

    private static final int[] AROUND_I = {-1, 0, 1, 1, 1, 0, -1, -1};
    private static final int[] AROUND_J = {-1, -1, -1, 0, 1, 1, 1, 0};

    private static int cellNum;


    public static void main(String[] args) {
        setup();
    }

    private static void setup() {
        System.out.printf("Set game window size:");
        int windowSize = INPUT.nextInt();
        System.out.printf("Set World Size:");
        int worldSize = INPUT.nextInt();
        System.out.printf("Set cell :");
        cellNum = INPUT.nextInt();
        window = new GameWindow(windowSize, worldSize, cellNum);

        gameStart();
    }

    private static void gameStart() {
        syncStat();

        while(true) {

            if(!window.Stop) {
                window.setTitle("Round: " + window.roundTime);
                chooseRule();
                changeColor();
                window.roundTime += 1;
            }

            if(window.isClearRound) {
                window.roundTime = 1;
                window.setTitle("Round: " + window.roundTime);
                window.isClearRound = !window.isClearRound;
            }
            delay(window.roundSpeed);
        }
    }

    private static void changeColor() {
        for(int i = 0; i < window.cellNum; ++i) {
            for(int j = 0; j < window.cellNum; ++j) {
                window.cells[i][j].stat = window.nextStat[i][j];

                if(window.cells[i][j].stat == ALIVE) {
                    window.cells[i][j].setBackground(Color.BLACK);
                } else {
                    window.cells[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    private static void syncStat() {
        for(int i = 0; i < window.cellNum; ++i) {
            for(int j = 0; j < window.cellNum; ++j) {
                window.nextStat[i][j] = window.cells[i][j].stat;
            }
        }
    }

    private static void delay(long delayTime) {
        try {
            Thread.sleep(delayTime);
        } catch(Exception e) {

        }
    }

    private static void chooseRule() {
        for(int i = 0; i < window.cellNum; ++i) {
            for(int j = 0; j < window.cellNum; ++j) {
                if(window.cells[i][j].stat == DEATH) {
                    deathCellRule(i, j);
                } else {
                    aliveCellRule(i, j);
                }
            }
        }
    }

    private static void deathCellRule(int i, int j) {
        int aliveCell = 0;

        for(int k = 0; k < 8; ++k) {

            if(i + AROUND_I[k] < 0 || j + AROUND_J[k] < 0 || i + AROUND_I[k] >= cellNum || j + AROUND_J[k] >= cellNum) {
                continue;
            }

            if(window.cells[i + AROUND_I[k]][j + AROUND_J[k]].stat == ALIVE) {
                aliveCell += 1;
            }
        }

        if(aliveCell == 3) {
            window.nextStat[i][j] = ALIVE;
        }

    }

    private static void aliveCellRule(int i, int j) {
        int aliveCell = 0;

        for(int k = 0; k < 8; ++k) {

            if(i + AROUND_I[k] < 0 || j + AROUND_J[k] < 0 || i + AROUND_I[k] >= cellNum || j + AROUND_J[k] >= cellNum) {
                continue;
            }

            if(window.cells[i + AROUND_I[k]][j + AROUND_J[k]].stat == ALIVE) {
                aliveCell += 1;
            }
        }

        if(aliveCell < 2 || aliveCell > 3) {
            //window.cells[i][j].stat = DEATH;
            //window.cells[i][j].setBackground(Color.WHITE);
            window.nextStat[i][j] = DEATH; //Over three alive cells or less than two
        } else {
            window.nextStat[i][j] = ALIVE;//between 2 and 3
        }

    }
}

