import org.jsfml.audio.Music;
import org.jsfml.graphics.*;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.system.Vector2i;
import org.jsfml.system.Vector2f;
import java.lang.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class base {
    private Sprite f;
    int hit = 3, theta = 0;
    Double x, y;

    public base(Texture t) {
        f = new Sprite(t);
        theta = 10;
        f.scale(0.45f, 0.45f);
        f.setTextureRect(new IntRect(0, 0, 67, 69));
        FloatRect size = f.getLocalBounds();
        f.setOrigin(size.width / 2, size.height / 2);
        System.out.println("The origin is ");        
        // try {
        //     firetexture.loadFromFile(Paths.get("fire.png"));
        // } catch (IOException ex) {
        //     //Ouch! something went wrong
        //     ex.printStackTrace();
        // }
    }

    boolean shot() {
        if (hit <= 0)
            return true;
        hit -= 1;
        switch (hit) {
        case -1:
            return true;
        case 0:
            x = -50.0;
            y = -50.0;
            theta = 0;
            f.setPosition(x.floatValue(), y.floatValue());
            return true;
        case 1:
            f.setTextureRect(new IntRect(134, 0, 67, 69));
            return false;
        case 2:
            f.setTextureRect(new IntRect(67, 0, 67, 69));
            return false;
        case 3:
            f.setTextureRect(new IntRect(0, 0, 67, 69));
            return false;
        }
        return false;
    }

    public void set(Double x, Double y) {
        this.x = x;
        this.y = y;
        f.setPosition(x.floatValue(), y.floatValue());
    }

    public void move() {
        f.rotate(theta);
    }

    public Sprite getsprite() {
        return f;
    }
}