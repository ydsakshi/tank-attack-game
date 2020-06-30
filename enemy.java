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

public class enemy {
     Sprite f;
    Double x, y, xa, ya;
    int mulvalue=1;
    int counter = 0, counter1 = 0;
    player p;
    base b;
    String s;
    int hit = 3;
    int theta;
    ArrayList<gun> g;
    private Texture firetexture = new Texture();
    String path = "assets/";

    public Double sqrt(float a, float b, float c, float d) {
        float temp = (d - b) * (d - b) + (c - a) * (c - a);
        Double d1 = Math.sqrt(Double.valueOf((new Float(temp)).toString()));
        return d1;
    }
    public void setmulval(int p){mulvalue=p;}
        public enemy(String s, Double x, Double y, Texture t, player p, base b) {
        this.p = p;
        this.b = b;
        this.s = s;
        f = new Sprite(t);
        f.scale(0.06f, 0.06f);
        theta = 0;
        try {
            firetexture.loadFromFile(Paths.get(path + "fire.png"));
        } catch (IOException ex) {
            
            ex.printStackTrace();
        }
        f.setTextureRect(new IntRect(0, 0, 933, 364));
        g = new ArrayList<gun>();
        FloatRect size = f.getLocalBounds();
        f.setOrigin(size.width / 2, size.height / 2);
        f.rotate(90);
        this.x = x;
        this.y = y;
        xa = 0.0;
        ya = 1.0;
    }

    public void shoot() {
        if (counter % 10 == 0) {
            gun temp = new gun(firetexture);
            temp.assign(x, y, xa, ya);
            temp.fire.setColor(new Color(255, 0, 0));
            g.add(temp);
        }
        counter++;
    }

    public void bounce() {
        shoot();
        if (s.charAt(0)== 'n') {
            if (xa == 1 && ya == 0) {
                xa = -1.0;
                theta = 180;
            } else if (xa == -1 && ya == 0) {
                xa = 1.0;
                theta = 0;
            } else if (xa == 0 && ya == 1) {
                ya = -1.0;
                theta = 90;
            } else if (xa == 0 && ya == -1) {
                ya = 1.0;
                theta = 270;
            }
            counter1++;
        }
    }

    public void move() {
        Vector2f p1 = f.getPosition();
        Vector2f p2;
        if(s.charAt(1)=='p')
        p2=p.getsprite().getPosition();
        else
        p2=b.getsprite().getPosition();
        Double min = 10000000.0;
        if (counter1 % 75== 0) {
            if (Double.compare(sqrt(p1.x + 1, p1.y, p2.x, p2.y), min) < 0) {
                theta = 0;
                xa = 1.0;
                ya = 0.0;
                min = sqrt(p1.x + 1, p1.y, p2.x, p2.y);
            }
            if (Double.compare(sqrt(p1.x - 1, p1.y, p2.x, p2.y), min) < 0) {
                theta = 180;
                xa = -1.0;
                ya = 0.0;
                min = sqrt(p1.x - 1, p1.y, p2.x, p2.y);
            }
            if (Double.compare(sqrt(p1.x, p1.y + 1, p2.x, p2.y), min) < 0) {
                theta = 90;
                xa = 0.0;
                ya = 1.0;
                min = sqrt(p1.x, p1.y + 1, p2.x, p2.y);
            }
            if (Double.compare(sqrt(p1.x, p1.y - 1, p2.x, p2.y), min) < 0) {
                theta = 270;
                xa = 0.0;
                ya = -1.0;
                min = sqrt(p1.x, p1.y - 1, p2.x, p2.y);
            }
        }
        for (gun p : g)
            p.move();
        for (gun p : g)
            p.set();
        counter1++;
        x += mulvalue*xa;
        y += mulvalue*ya;
        f.setRotation(theta);
        f.setPosition(x.floatValue(), y.floatValue());
    }

    public void settrans() {
        f.setColor(new Color(255, 255, 255, 128));
    }

    public void setback() {
        f.setColor(new Color(255, 255, 255, 255));
    }

    public boolean shot() {
        if (hit <= 0)
            return true;
        hit -= 1;
        switch (hit) {
        case -1:
            return true;
        case 0:
            x = -50.0;
            y = -50.0;
            xa = 0.0;
            ya = 0.0;
            f.setPosition(x.floatValue(), y.floatValue());
            return true;
        case 1:
            f.setTextureRect(new IntRect(1866, 0, 933, 364));
            return false;
        case 2:
            f.setTextureRect(new IntRect(933, 0, 933, 364));
            return false;
        case 3:
            f.setTextureRect(new IntRect(0, 0, 933, 364));
            return false;
        }
        return false;
    }

    public Sprite getsprite() {
        return f;
    }

    public void del(int i) {
        if (i < g.size())
            g.remove(i);
    }

    public ArrayList<gun> getfire() {

        return g;
    }
}