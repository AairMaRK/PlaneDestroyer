import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameEventListener extends KeyAdapter
{
    private GameField field;

    public GameEventListener(GameField field) {
        this.field = field;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        field.keyReleased(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        field.keyPressed(e);
    }
}
