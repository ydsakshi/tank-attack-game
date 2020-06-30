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

public class turret {
    private Sprite f;
    Double x, y, length;
    int theta, value;
    ArrayList<gun> g;
    Double xa, ya;
    FloatRect size;
    int hit = 3;
    int shootamt;

    public turret(Texture t, int th, int v,int v1) {
        shootamt=v1;
        f = new Sprite(t);
        f.setTextureRect(new IntRect(0, 0, 54, 69));
        theta = th;
        value = v;
        xa = 0.0;
        ya = -1.0;
        size = f.getLocalBounds();
        // length= size.height / 2;
        f.setOrigin(size.width / 2, size.height / 2);
        f.scale(0.5f, 0.5f);
        g = new ArrayList<gun>();
    }

    public void set(Double x, Double y) {
        this.x = x;
        this.y = y;
        f.setPosition(x.floatValue(), y.floatValue());
    }

    // public Double getlength() {
    //     FloatRect size = f.getLocalBounds();
    //     return size.height/2;
    // }
    public boolean shot() {
        hit -= 1;
        switch (hit) {
        case -1:
            break;
        case 0:
            f.setPosition(-50, -50);
            return true;
        case 1:
            f.setTextureRect(new IntRect(108, 0, 54, 69));
            return false;
        case 2:
            f.setTextureRect(new IntRect(54, 0, 54, 69));
            return false;
        case 3:
            f.setTextureRect(new IntRect(0, 0, 54, 69));
            return false;
        }
        return false;
    }

    public void move(boolean fl, Texture t) {
        if (!fl) {
            gun temp = new gun(t);
            Float q = f.getRotation();
            // System.out.println("abracadabra");        
            if (Math.round(q) % value == 0)
                theta = theta * -1;
            Double q1 = Double.valueOf(q.toString());
            if (Math.round(q1) % shootamt == 0) {
                temp.assign(this, x, y, Math.cos(Math.toRadians(q1 - 90)), Math.sin(Math.toRadians(q1 - 90)));
                temp.fire.setRotation(q);
                temp.fire.scale(0.2f, 0.2f);
                g.add(temp);
            }
        }
        f.rotate(theta);
        for (gun p : g)
            p.move();
        for (gun p : g)
            p.set();
    }

    public ArrayList<gun> getfire() {
        return g;
    }

    public void del(int i) {
        if(i<g.size())
        g.remove(i);
    }

    public Sprite getsprite() {
        return f;
    }
}