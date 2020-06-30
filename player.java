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

// import com.sun.prism.paint.Color;

public class player {
    private int theta, index = 0;int hit = 3;int mulvalue=2;
    Double x, y, xa, ya, xf, yf, xaf, yaf;
    private Texture texture = new Texture();
    private Texture firetexture = new Texture();
    String path = "assets/";

    Sprite sprite;
    ArrayList<gun> g;

    public player() {
        x = 200.0;
        y = 200.0;
        xa = 1.0;
        ya = 0.0;
        xf = 0.0;
        yf = 0.0;
        xaf = 0.0;
        yaf = 0.0;

        theta = 0;
        try {
            texture.loadFromFile(Paths.get(path + "player.png"));
            firetexture.loadFromFile(Paths.get(path + "fire.png"));
            sprite = new Sprite(texture);
            sprite.setTextureRect(new IntRect(0, 0, 866, 408));
            g = new ArrayList<gun>();
            // fire=new Sprite(firetexture);
        } catch (IOException ex) {
            //Ouch! something went wrong
            ex.printStackTrace();
        }
        // Vector2f c1=sprite.getOrigin();
        // System.out.println("The origin is " + c1.x + "x" + c1.y);
        // shape.setFillColor(Color.GREEN);

        sprite.scale(0.06f, 0.06f);

        // fire.scale(0.25f, 0.25f);
        // Vector2f c2=sprite.getOrigin();
        // System.out.println("The origin is " + c2.x + "x" + c2.y);
        Vector2f c = sprite.getOrigin();
        System.out.println("The origin is " + c.x + "x" + c.y);

        // sprite.setPosition(x, y);
        FloatRect size = sprite.getLocalBounds();
        System.out.println("The texture is " + size.width + "x" + size.height);
        sprite.setOrigin(size.width / 2, size.height / 2);
        sprite.setPosition(x.floatValue(), y.floatValue());
        // Vector2f c=sprite.getOrigin();
        // System.out.println("The origin is " + c.x + "x" + c.y);

        //    Vector2i size = texture.getSize();.0

        // sprite.rotate(180);
    }
    public void setmulval(int p){mulvalue=p;}
    
    public void getdir(int p) {
        // theta=90;
        if (xa == 1 && ya == 0) {
            if (p == 8) {
                xa = 0.0;
                ya = -1.0;
                theta = -90;
            } else if (p == 2) {
                xa = 0.0;
                ya = 1.0;
                theta = 90;
            }
        } else if (xa == -1 && ya == 0) {
            if (p == 8) {
                xa = 0.0;
                ya = -1.0;
                theta = 90;
            } else if (p == 2) {
                xa = 0.0;
                ya = 1.0;
                theta = -90;
            }
        } else if (xa == 0 && ya == -1 || xa == 0 && ya == 1) {
            if (p == 4) {
                if (xa == 0 && ya == -1)
                    theta = -90;
                else
                    theta = 90;
                xa = -1.0;
                ya = 0.0;
            } else if (p == 6) {
                if (xa == 0 && ya == -1)
                    theta = 90;
                else
                    theta = -90;
                xa = 1.0;
                ya = 0.0;
            }
        }
        // move();
    }

    public void move() {
        if (x + xa <= 0) {
            xa = 1.0;
            ya = 0.0;
            theta = 180;
        }
        if (x + xa >= 640) {
            xa = -1.0;
            ya = 0.0;
            theta = 180;
        }
        if (y + ya <= 0) {
            ya = 1.0;
            xa = 0.0;
            theta = 180;
        }
        if (y + ya >= 480) {
            ya = -1.0;
            xa = 0.0;
            theta = 180;
        }
        x += mulvalue*xa;
        y += mulvalue*ya;
        for (gun p : g)
            p.move();
        // x =x+ (int)Math.sin(Math.toRadians(360.0f -theta));
        // y =y+ (int)Math.cos(Math.toRadians(360.0f -theta));        
        sprite.rotate(theta);
        theta = 0;
        // System.out.println("The rotation="+sprite.getRotation());        

        // fire.setPosition(xf,yf);
        for (gun p : g)
            p.set();
        sprite.setPosition(x.floatValue(), y.floatValue());
    }

    public void bounce() {
        theta = 180;
        System.out.println("BOUNCED!!!"+"xa:"+xa+" ya:"+ya);
        if (xa == 1.0 && ya == 0.0) {
            xa = -1.0;
        } else if (xa == -1.0 && ya == 0.0) {
            xa = 1.0;
        } else if (xa == 0.0 && ya == 1.0) {
            ya = -1.0;
        } else if (xa == 0.0 && ya == -1.0) {
            ya = 1.0;
        }
        System.out.println("BOUNCED"+"xa:"+xa+" ya:"+ya);        
    }

    public void shoot() {
        gun temp = new gun(firetexture);
        temp.assign(x, y, xa, ya);
        g.add(temp);
        // xf=x;
        // yf=y;
        // xaf=xa;
        // yaf=ya;

        // if(xaf==1&&yaf==0) {fire.setRotation(0);}
        // else if(xaf==-1&&yaf==0){fire.setRotation(180);}
        // else if(xaf==0&&yaf==1){fire.setRotation(90);}
        // else if(xaf==0&&yaf==-1){fire.setRotation(-90);}

        // xaf*=2;yaf*=2;
        // fire.setPosition(xf,yf);
    }

    public ArrayList<gun> getfire() {
        return g;
    }

    public boolean kill() {
        if(hit<=0) return true;
        hit -= 1;
        switch (hit) {
        case -1:
            return true;
        case 0:
            x = -50.0;
            y = -50.0;
            xa = 0.0;
            ya = 0.0;
            sprite.setPosition(x.floatValue(), y.floatValue());
            return true;
        case 1:
            sprite.setTextureRect(new IntRect(1732, 0, 866, 408));
            return false;
        case 2:
            sprite.setTextureRect(new IntRect(866, 0, 866, 408));
            return false;
        case 3:
            sprite.setTextureRect(new IntRect(0, 0, 866, 408));
            return false;
        }
        return false;
    }

    public void del(int i) {
        if(i<g.size())
        g.remove(i);
    }

    public Sprite getsprite() {
        return sprite;
    }
    //    public void draw(){w.draw(sprite);}

}
