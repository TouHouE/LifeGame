package element;

import javax.swing.*;

public class World extends JPanel {
    public World(int size) {
        this.size = size;
        init();
    }

    private int size;

    private void init() {
        this.setLayout(null);
        this.setLocation(0, 0);
        this.setSize(size, size + 40);
        this.setAutoscrolls(true);
    }
}
