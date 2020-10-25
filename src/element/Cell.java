package element;

import javafx.collections.ObservableArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cell extends JButton {
    //false == death, true == alive

    public Cell(boolean stat) {
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = Cell.this.getBackground();

                if(color == Color.WHITE) {
                    Cell.this.setBackground(Color.BLACK);
                    Cell.this.stat = true;
                } else {
                    Cell.this.setBackground(Color.WHITE);
                    Cell.this.stat = false;
                }
                System.out.printf("cell convert stat : %s\n", Cell.this.toString());
                System.out.printf("Location is :\nx = %d\ny = %d\n", x, y);
            }
        });

        this.stat = stat;
    }

    public int x, y;
    public boolean stat;

    public void clear() {
        this.stat = false;
        this.setBackground(Color.WHITE);
    }

    public void setStat(boolean lifeState) {

        if(lifeState) {
            this.stat = true;
            this.setBackground(Color.BLACK);
        } else {
            this.stat = false;
            this.setBackground(Color.WHITE);
        }
    }

    public void serial(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        if(stat) {
            return "True";
        } else {
            return "False";
        }

        //return
    }
}
