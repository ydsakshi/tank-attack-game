import org.jsfml.audio.Music;
import org.jsfml.graphics.*;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.system.Vector2i;
import org.jsfml.system.Vector2f;
import java.lang.*;
import java.io.IOException;
import java.nio.file.Paths;

public class brick {
    private Sprite f;
     int hit;

    brick(Texture t) {
        f = new Sprite(t);
        f.setTextureRect(new IntRect(0, 0, 29, 29));
        hit = -1;
    }

    brick(Texture t, int h) {
        f = new Sprite(t);
        f.setTextureRect(new IntRect(0, 0, 29, 29));
        hit = h;
    }

    public void set(int x, int y) {
        f.setPosition(x, y);
    }

    public void shot() {
        if (hit == -1)
            return;
        else {
            hit -= 1;
            switch (hit) {
            case -1:
                break;
            case 0:
                f.setPosition(-50,-50);
                break;
            case 1:
                f.setTextureRect(new IntRect(58, 0, 29, 29));
                break;
            case 2:
                f.setTextureRect(new IntRect(29, 0, 29, 29));
                break;
            case 3:
                f.setTextureRect(new IntRect(0, 0, 29, 29));
                break;
            }
        }
    }

    public Sprite getSprite() {
        return f;
    }
}