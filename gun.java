import org.jsfml.audio.Music;
import org.jsfml.graphics.*;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.system.Vector2i;
import org.jsfml.system.Vector2f;
import java.lang.*;
import java.io.IOException;
import java.nio.file.Paths;

public class gun {
    Double xf, yf, xaf, yaf;
    int index = 0;
    Sprite fire;

    public gun(Texture t) {
        xf = 0.0;
        yf = 0.0;
        xaf = 0.0;
        yaf = 0.0;
        fire = new Sprite(t);
        FloatRect size = fire.getLocalBounds();
        fire.setOrigin(size.width / 2, size.height / 2);
        fire.scale(0.25f, 0.25f);
    }

    public void assign(Double a, Double b, Double c, Double d) {
        xf = a;
        yf = b;
        xaf = c;
        yaf = d;
        if (xaf == 1.0 && yaf == 0.0) {
            // yf -= 23.58 / 2;
            xf += 28;
            fire.setRotation(0);
        } else if (xaf == -1.0 && yaf == 0.0) {
            // yf += 23.;
            xf -= 28;
            fire.setRotation(180);
        } else if (xaf == 0.0 && yaf == 1.0) {
            // xf += 23.58 / 2;
            yf += 28;
            fire.setRotation(90);
        } else if (xaf == 0.0 && yaf == -1.0) {
            // xf -= 23.58 / 2;
            yf -= 28;
            fire.setRotation(-90);
        }

        xaf *= 2;
        yaf *= 2;
        fire.setPosition(xf.floatValue(), yf.floatValue());
    }

    public void assign(turret t, Double a, Double b, Double c, Double d) {
        xf = a;
        yf = b;
        xaf = c;
        yaf = d;
        xaf *= 2;
        yaf *= 2;
        fire.setPosition(xf.floatValue(), yf.floatValue());

    }

    public void move() {
        xf += xaf;
        yf += yaf;
    }

    public void finish(int x, int y) {
    }

    public void set() {
        fire.setPosition(xf.floatValue(), yf.floatValue());
    }
}