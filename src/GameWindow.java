import element.Cell;
import element.World;
import mode.Mode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame{
    public GameWindow(int windowSize, int worldSize, int cell) {
        this.windowSize = windowSize;
        this.worldSize = worldSize;
        this.cellNum = cell;
        init();
    }

    public GameWindow() {}

    private World world;
    private final int BUTTON_H = 30, BUTTON_W = 150, MARGIN = 10, SPEED_SIDE = 100;
    private final boolean DEATH = false, ALIVE = true;
    private int windowSize;
    public int worldSize;
    public int cellNum;

    public boolean[][] nextStat;
    public Cell[][] cells;
    public boolean Stop = true;
    public boolean isClearRound = false;
    public int roundTime;
    public long roundSpeed;

    public TextField SPEED_GETTER = new TextField();
    public JButton SPEED_PLUS = new JButton("+");
    public JButton SPEED_DOWN = new JButton("-");
    public JLabel SPEED_E2_RANGE = new JLabel("100");

    private void init() {
        this.setDefaultCloseOperation(3);
        this.setSize(windowSize, windowSize);
        this.setVisible(true);
        this.setLayout(null);
        this.roundSpeed = 200L;
        world = new World(worldSize);
        this.add(world);
        initCell();
        initFuncBtn();
    }

    public void syncCellStat() {
        for(int i = 1; i < cellNum - 1; ++i) {
            for(int j = 1; j < cellNum - 1; ++j) {
                nextStat[i][j] = cells[i][j].stat;
            }
        }
    }

    private void clear() {
        for(int i = 0; i < cellNum; ++i) {
            for(int j = 0; j < cellNum; ++j) {
                cells[i][j].clear();
                nextStat[i][j] = false;
            }
        }
    }

    private void initCell() {
        cells = new Cell[cellNum][cellNum];
        nextStat = new boolean[cellNum][cellNum];
        int x = 0, y = 0, cellSize = worldSize / (cellNum - 2);
        System.out.printf("cell Size:%d\n", cellSize);

        for(int i = 0; i < cellNum; ++i) {
            for(int j = 0; j < cellNum; ++j) {
                nextStat[i][j] = DEATH;
                cells[i][j] = new Cell(DEATH);
                cells[i][j].setBackground(Color.WHITE);
                cells[i][j].setLocation(x, y);
                cells[i][j].setSize(cellSize, cellSize);
                cells[i][j].setVisible(true);
                cells[i][j].serial(i, j);
                world.add(cells[i][j]);
                x += cellSize;
            }
            x = 0;
            y += cellSize;
        }
    }

    private void initFuncBtn() {
        JButton STOP = new JButton("暫停");
        STOP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Stop = true;
            }
        });

        JButton CLEAR = new JButton("清空");
        CLEAR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Stop = true;
                clear();
            }
        });

        JButton START = new JButton("開始");
        START.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Stop = false;
            }
        });

        JButton RESET = new JButton("回合數清空");
        RESET.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //roundTime = 1;
                isClearRound = true;
                Stop = true;
            }
        });

        JButton STATE1 = new JButton("加斯帕機槍");
        STATE1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mode mode1 = new Mode();

                int[][] go = mode1.GOSPER_GLIDER_GUN;

                for(int i = 0; i < go.length; ++i) {
                    for(int j = 0; j < go[0].length; ++j) {

                        if(go[i][j] == 0) {
                            cells[3 + i][3 + j].setStat(false);
                        } else {
                            cells[3 + i][3 + j].setStat(true);
                        }
                    }
                }
            }
        });

        JButton EXIT = new JButton("離開");
        EXIT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        SPEED_DOWN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameWindow.this.roundSpeed -= 100;
                GameWindow.this.SPEED_GETTER.setText(String.valueOf(GameWindow.this.roundSpeed));
            }
        });

        SPEED_PLUS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameWindow.this.roundSpeed += 100;
                GameWindow.this.SPEED_GETTER.setText(String.valueOf(GameWindow.this.roundSpeed));
            }
        });


        SPEED_GETTER.addTextListener(new TextListener() {
            @Override
            public void textValueChanged(TextEvent e) {
                TextField tf = (TextField)e.getSource();
                //tf.select(0, tf.getCaretPosition());
                String sec = tf.getText();

                if(sec.equals("") ) {
                    sec = "0";
                }

                System.out.println(sec);
                setRoundSpeed(Long.parseLong(sec));
            }
        });

        int i = 0;



        JLabel label1 = new JLabel("回合時間(ms):");

        String fontName = label1.getFont().getName();
        int fontStyle = label1.getFont().getSize();
        int fontSize = 20;
        Font font = new Font(fontName, fontStyle, fontSize);

        label1.setFont(font);
        label1.setLocation(worldSize + 40, BUTTON_H * (i++));
        label1.setSize(BUTTON_W, BUTTON_H);

        SPEED_GETTER.setText("200");
        SPEED_GETTER.setFont(font);
        SPEED_GETTER.setLocation(worldSize + 40, BUTTON_H * (i++));
        SPEED_GETTER.setSize(BUTTON_W, BUTTON_H);

        START.setLocation(worldSize + 40, BUTTON_H * (i++));
        START.setSize(BUTTON_W, BUTTON_H);
        START.setFont(font);

        STOP.setLocation(worldSize + 40, BUTTON_H * (i++));
        STOP.setSize(BUTTON_W, BUTTON_H);
        STOP.setFont(font);

        CLEAR.setLocation(worldSize + 40, BUTTON_H * (i++));
        CLEAR.setSize(BUTTON_W, BUTTON_H);
        CLEAR.setFont(font);

        RESET.setLocation(worldSize +40, BUTTON_H * (i++));
        RESET.setSize(BUTTON_W, BUTTON_H);
        RESET.setFont(font);

        STATE1.setLocation(worldSize + 40, BUTTON_H * (i++));
        STATE1.setSize(BUTTON_W, BUTTON_H);
        STATE1.setFont(font);

        EXIT.setLocation(worldSize + 40, BUTTON_H * (i++));
        EXIT.setSize(BUTTON_W, BUTTON_H);
        EXIT.setFont(font);

        SPEED_PLUS.setFont(font);
        SPEED_PLUS.setSize(SPEED_SIDE, SPEED_SIDE);
        SPEED_PLUS.setLocation(SPEED_GETTER.getX() + BUTTON_W, 0);

        SPEED_E2_RANGE.setFont(font);
        SPEED_E2_RANGE.setSize(SPEED_SIDE, SPEED_SIDE);
        SPEED_E2_RANGE.setLocation(SPEED_GETTER.getX() + BUTTON_W, SPEED_SIDE + MARGIN);
        SPEED_E2_RANGE.setHorizontalAlignment(SwingConstants.CENTER);

        SPEED_DOWN.setFont(font);
        SPEED_DOWN.setSize(SPEED_SIDE, SPEED_SIDE);
        SPEED_DOWN.setLocation(SPEED_GETTER.getX() + BUTTON_W, 2 * (SPEED_SIDE + MARGIN));



        this.add(label1);
        this.add(SPEED_GETTER);
        this.add(START);
        this.add(STOP);
        this.add(CLEAR);
        this.add(RESET);
        this.add(STATE1);
        this.add(EXIT);
        this.add(SPEED_DOWN);
        this.add(SPEED_PLUS);
        this.add(SPEED_E2_RANGE);
    }

    public void setRoundSpeed(Long roundSpeed) {
        this.roundSpeed = roundSpeed;
    }


}
