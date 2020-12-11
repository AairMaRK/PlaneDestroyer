import javax.swing.*;

public class Starter extends JFrame
{
    public Starter() {
        add(new GameField());
        setTitle(Values.TITLE);
        try {
            setIconImage(ImageGenerator.newImage(Images.PLAYER));
        } catch (Exception EXC) {}
        setSize(Values.FIELD_WIDTH, Values.FIELD_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public  static void main(String[] args) {
        new Starter();
    }
}
