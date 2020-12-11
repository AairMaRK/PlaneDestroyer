import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class ImageGenerator implements Serializable
{
    public static BufferedImage newImage(Images image) throws IOException {
        BufferedImage bufferedImage;
        switch (image) {
            case BACKGROUND:
                File img = new File(Values.BACKGROUND_IMAGE);
                bufferedImage = ImageIO.read(img);
                break;
            case PLAYER_GUNSHOT:
                File img1 = new File(Values.PLAYER_GUNSHOT_IMAGE);
                bufferedImage = ImageIO.read(img1);
                break;
            case PLAYER:
                File img2 = new File(Values.PLAYER_IMAGE);
                bufferedImage = ImageIO.read(img2);
                break;
            case ENEMY_GUNSHOT:
                File img3 = new File(Values.ENEMY_GUNSHOT_IMAGE);
                bufferedImage = ImageIO.read(img3);
                break;
            case ENEMY:
                File img4 = new File(Values.ENEMY_IMAGE);
                bufferedImage = ImageIO.read(img4);
                break;
            default:
                return null;
        }
        return bufferedImage;
    }
}