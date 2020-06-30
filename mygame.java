import org.jsfml.audio.Music;
import org.jsfml.graphics.*;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.*;

// import sun.jvm.hotspot.debugger.posix.elf.ELFSectionHeader;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

// import com.sun.glass.events.KeyEvent;

/**
 * This is the short simple example from the front page of the SFML documentation "translated" to JSFML.
 */
public class mygame {
    public static void main(String[] args) throws IOException {
        //Create the main window
        RenderWindow window = new RenderWindow();
        window.create(new VideoMode(640, 480), "TANK<>ATTACK");
        player p = new player();
        boolean flagscore = true, level2 = false, flaghard = false, flageasy = false, gamestart = false,
                instructionflag = false, lostflag = false;
        Font f = new Font();
        int score = 0;
        Texture firetexture = new Texture();
        Texture bri = new Texture();
        Texture bri1 = new Texture();
        Texture mesh = new Texture();
        Texture tur = new Texture();
        Texture ball = new Texture();
        Texture ene = new Texture();
        Texture base = new Texture();
        String path = "assets/";

        Sprite[] m = new Sprite[64];
        try {
            f.loadFromFile(Paths.get(path + "arial.ttf"));
            firetexture.loadFromFile(Paths.get(path + "fire.png"));
            bri.loadFromFile(Paths.get(path + "brick1.png"));
            bri1.loadFromFile(Paths.get(path + "brick2.png"));
            mesh.loadFromFile(Paths.get(path + "mesh.png"));
            tur.loadFromFile(Paths.get(path + "turret1.png"));
            ball.loadFromFile(Paths.get(path + "rfire.png"));
            ene.loadFromFile(Paths.get(path + "enemy.png"));
            base.loadFromFile(Paths.get(path + "base.png"));
            // fire=new Sprite(firetexture);
        } catch (IOException ex) {
            //Ouch! something went wrong
            ex.printStackTrace();
        }
        int pl = 3, bl = 3;
        Text plife = new Text(new String("PLAYER:" + new Integer(pl).toString()), f, 20);
        Text blife = new Text(new String("BASE:" + new Integer(bl).toString()), f, 20);
        Text gtext = new Text("GAME OVER", f, 25);
        Text text = new Text("PLAY NOW", f, 12);
        Text text1 = new Text("INSTRUCTION", f, 13);
        Text text2 = new Text("EXIT", f, 12);
        blife.setStyle(Text.BOLD);
        plife.setStyle(Text.BOLD);
        gtext.setStyle(Text.BOLD);
        text.setStyle(Text.BOLD);
        text1.setStyle(Text.BOLD);
        text2.setStyle(Text.BOLD);
        plife.setPosition(480, 0);
        blife.setPosition(480, 25);
        Text inst = new Text(
                "\nAvoid the guns of enemy \ntanks/turrets while protecting your base at the same time...\nLonger the player remains alive,more the points\nPress Backspace to go back",
                f, 25);

        Text subtext = new Text("EASY", f, 12);
        Text subtext1 = new Text("MEDIUM", f, 13);
        Text subtext2 = new Text("DIFFICULT", f, 12);
        subtext.setStyle(Text.BOLD);
        subtext1.setStyle(Text.BOLD);
        subtext2.setStyle(Text.BOLD);

        brick b = new brick(bri, 3);
        brick[] br = new brick[48];
        brick[] nbr = new brick[12];
        for (int i = 0; i < 12; i++)
            nbr[i] = new brick(bri1);
        for (int i = 0; i < 48; i++)
            br[i] = new brick(bri, 3);

        turret t = new turret(tur, 5, 360, 60);
        base bs = new base(base);

        bs.set(320.0 - 2, 480.0 - 17);
        t.set(320.0, 220.0);
        for (int i = 0; i < 64; i++) {
            m[i] = new Sprite(mesh);
        }
        enemy e = new enemy("np", 200.0, 0.0, ene, p, bs);
        e.f.setColor(new Color(0,0,255));
        enemy e1 = new enemy("np", 400.0, 0.0, ene, p, bs);
        e1.f.setColor(new Color(255,0,255));        
        enemy e2 = new enemy("nb", 100.0, 0.0, ene, p, bs);
        e2.f.setColor(new Color(186,24,154));
        
        // enemy e1 = new enemy(400.0, 0.0, ene, p);
        // enemy e2 = new enemy(500.0, 0.0, ene, p);

        b.set(400, 200);
        nbr[0].set(0, 0);
        nbr[1].set(30, 0);
        nbr[2].set(0, 30);
        nbr[3].set(640 - 29, 0);
        nbr[4].set(640 - 29 - 30, 0);
        nbr[5].set(640 - 29, 30);
        nbr[6].set(0, 480 - 29);
        nbr[7].set(0, 480 - 29 - 30);
        nbr[8].set(30, 480 - 29);
        nbr[9].set(640 - 29, 480 - 29);
        nbr[10].set(640 - 29 - 30, 480 - 29);
        nbr[11].set(640 - 29, 480 - 29 - 30);

        br[0].set(320 - 17 - 29, 480 - 29);
        br[1].set(320 - 17 - 29, 480 - 29 - 30);
        br[2].set(320 + 12, 480 - 29);
        br[3].set(320 + 12, 480 - 29 - 30);
        br[4].set(320 - 17, 480 - 29 - 30);

        for (int i = 5; i < 26; i++) {
            if (i < 10)
                br[i].set((i - 5) * 29, 250);
            if (i >= 13 && i <= 17)
                br[i].set((i - 5) * 29, 250);
            if (i > 20)
                br[i].set((i - 5 + 1) * 29, 250);
        }
        brick b1 = new brick(bri1);
        b1.set(429, 200);
        boolean flagscreen = false, subflag = false;
        boolean flag = false, flag1 = true, flagp = false, flage = false, flage1 = false, flage2 = false, flagt = false;
        boolean flagbs = false, gameover = false, gameover1 = false;
        //Limit the framerate
        window.setFramerateLimit(30);
        RectangleShape rect = new RectangleShape(new Vector2f(640, 480));
        rect.setFillColor(new Color(255, 255, 255, 128));

        RectangleShape gbox = new RectangleShape(new Vector2f(160, 40));
        gbox.setFillColor(new Color(0, 255, 0));
        FloatRect s = gbox.getLocalBounds();
        gbox.setOrigin(s.width / 2, s.height / 2);
        gbox.setPosition(320, 240);
        gtext.setPosition(240, 223);

        RectangleShape menu1 = new RectangleShape(new Vector2f(160, 40));
        menu1.setFillColor(new Color(255, 0, 0));
        FloatRect size = menu1.getLocalBounds();
        menu1.setOrigin(size.width / 2, size.height / 2);
        menu1.setPosition(320, 180);
        text.setPosition(295, 180);
        subtext.setPosition(295, 180);

        RectangleShape menu2 = new RectangleShape(new Vector2f(160, 40));
        menu2.setFillColor(new Color(0, 255, 0));
        FloatRect size1 = menu2.getLocalBounds();
        menu2.setOrigin(size1.width / 2, size1.height / 2);
        menu2.setPosition(320, 240);
        text1.setPosition(280, 240);
        subtext1.setPosition(280, 240);

        RectangleShape menu3 = new RectangleShape(new Vector2f(160, 40));
        menu3.setFillColor(new Color(0, 0, 255));
        FloatRect size2 = menu3.getLocalBounds();
        menu3.setOrigin(size2.width / 2, size2.height / 2);
        menu3.setPosition(320, 300);
        text2.setPosition(305, 300);
        subtext2.setPosition(305, 300);

        RectangleShape submenu1 = new RectangleShape(new Vector2f(160, 40));
        submenu1.setFillColor(new Color(255, 255, 0));
        FloatRect subsize = submenu1.getLocalBounds();
        submenu1.setOrigin(subsize.width / 2, subsize.height / 2);
        submenu1.setPosition(320, 180);

        RectangleShape submenu2 = new RectangleShape(new Vector2f(160, 40));
        submenu2.setFillColor(new Color(0, 255, 255));
        FloatRect subsize1 = submenu2.getLocalBounds();
        submenu2.setOrigin(subsize1.width / 2, subsize1.height / 2);
        submenu2.setPosition(320, 240);

        RectangleShape submenu3 = new RectangleShape(new Vector2f(160, 40));
        submenu3.setFillColor(new Color(255, 0, 255));
        FloatRect subsize2 = submenu3.getLocalBounds();
        submenu3.setOrigin(subsize2.width / 2, subsize2.height / 2);
        submenu3.setPosition(320, 300);
        // Texture texture = new Texture();

        // texture.loadFromFile(Paths.get("player.png"));

        // Sprite sprite = new Sprite(texture);
        // CircleShape shape=new CircleShape(5);
        // shape.setOrigin((int)2.5,(int)2.5)   ;
        // shape.setFillColor(Color.GREEN);         
        // shape.setPosition((int)61.44/2,(int)47.175003/2);

        //Main loop
        Clock time = new Clock();
        Time deltatime;
        while (window.isOpen()) {
            //Fill the window with red
            window.clear(Color.RED);
            pl = p.hit;
            bl = bs.hit;
            plife.setString("PLAYER:" + new String(new Integer(pl).toString()));
            blife.setString("BASE:" + new String(new Integer(bl).toString()));

            //Display what was drawn (... the red color!)
            // window.draw(sprite);
            if (gamestart) {

                    p.move();
                    e.move();
                    e1.move();
                    e2.move();
                    if(flage){e.x=-50.0;e.y=-50.0;e.xa=0.0;e.ya=0.0;}
                    if(flage1){e1.x=-50.0;e1.y=-50.0;e1.xa=0.0;e1.ya=0.0;}
                    if(flage2){e2.x=-50.0;e2.y=-50.0;e2.xa=0.0;e2.ya=0.0;}
                bs.move();

                // e1.move();
                // e2.move();
                if (!flag1)
                    t.move(flag1, ball);
                ArrayList<gun> temp1 = t.getfire();
                ArrayList<Integer> st1 = new ArrayList<Integer>();
                for (gun g : temp1) {
                    if (!flag1 && g.fire.getGlobalBounds().intersection((p.getsprite()).getGlobalBounds()) != null) {
                        flag = p.kill();
                        st1.add(new Integer(temp1.indexOf(g)));
                    }
                    for (brick d : br) {
                        if (g.fire.getGlobalBounds().intersection((d.getSprite()).getGlobalBounds()) != null) {
                            d.shot();
                            System.out.println("ywetqur");
                            st1.add(new Integer(temp1.indexOf(g)));
                        }
                    }
                    for (brick d1 : nbr) {
                        if (g.fire.getGlobalBounds().intersection((d1.getSprite()).getGlobalBounds()) != null) {
                            d1.shot();
                            st1.add(new Integer(temp1.indexOf(g)));
                        }
                    }
                    if (!flag1 && !flagbs
                            && g.fire.getGlobalBounds().intersection((bs.getsprite()).getGlobalBounds()) != null) {
                        flagbs = bs.shot();
                        st1.add(new Integer(temp1.indexOf(g)));
                    }
                }
                for (Integer i : st1) {
                    t.del(i.intValue());
                }

                ArrayList<gun> temp2 = e.getfire();
                ArrayList<Integer> st2 = new ArrayList<Integer>();
                for (gun g : temp2) {
                    if (!flage && g.fire.getGlobalBounds().intersection((p.getsprite()).getGlobalBounds()) != null) {
                        flag = p.kill();
                        st2.add(new Integer(temp2.indexOf(g)));
                    }
                    for (brick c : br) {
                        if (g.fire.getGlobalBounds().intersection((c.getSprite()).getGlobalBounds()) != null
                                || g.fire.getGlobalBounds().intersection((c.getSprite()).getGlobalBounds()) != null) {
                            c.shot();
                            st2.add(new Integer(temp2.indexOf(g)));
                        }
                    }
                    if (!flagbs && g.fire.getGlobalBounds().intersection((bs.getsprite()).getGlobalBounds()) != null) {
                        flagbs = bs.shot();
                        st2.add(new Integer(temp2.indexOf(g)));
                    }
                }
                ArrayList<gun> temp21 = e1.getfire();
                ArrayList<Integer> st21 = new ArrayList<Integer>();
                for (gun g : temp21) {
                    if (!flage1 && g.fire.getGlobalBounds().intersection((p.getsprite()).getGlobalBounds()) != null) {
                        flag = p.kill();
                        st21.add(new Integer(temp21.indexOf(g)));
                    }
                    for (brick c : br) {
                        if (g.fire.getGlobalBounds().intersection((c.getSprite()).getGlobalBounds()) != null
                                || g.fire.getGlobalBounds().intersection((c.getSprite()).getGlobalBounds()) != null) {
                            c.shot();
                            st21.add(new Integer(temp21.indexOf(g)));
                        }
                    }
                    if (!flagbs && g.fire.getGlobalBounds().intersection((bs.getsprite()).getGlobalBounds()) != null) {
                        flagbs = bs.shot();
                        st21.add(new Integer(temp21.indexOf(g)));
                    }
                }
                ArrayList<gun> temp22 = e2.getfire();
                ArrayList<Integer> st22 = new ArrayList<Integer>();
                for (gun g : temp22) {
                    if (!flage2 && g.fire.getGlobalBounds().intersection((p.getsprite()).getGlobalBounds()) != null) {
                        flag = p.kill();
                        st22.add(new Integer(temp22.indexOf(g)));
                    }
                    for (brick c : br) {
                        if (g.fire.getGlobalBounds().intersection((c.getSprite()).getGlobalBounds()) != null
                                || g.fire.getGlobalBounds().intersection((c.getSprite()).getGlobalBounds()) != null) {
                            c.shot();
                            st22.add(new Integer(temp22.indexOf(g)));
                        }
                    }
                    if (!flagbs && g.fire.getGlobalBounds().intersection((bs.getsprite()).getGlobalBounds()) != null) {
                        flagbs = bs.shot();
                        st22.add(new Integer(temp22.indexOf(g)));
                    }
                }
                for (Integer i : st2) {
                    e.del(i.intValue());
                }
                for (Integer i : st21) {
                    e1.del(i.intValue());
                }
                for (Integer i : st22) {
                    e2.del(i.intValue());
                }
                for (Integer i : st1) {
                    t.del(i.intValue());
                }

                ArrayList<Integer> st = new ArrayList<Integer>();
                int index = 0;

                // gun temp=new gun();
                Vector2f p1 = e.getsprite().getPosition();
                Vector2f p11 = e1.getsprite().getPosition();
                Vector2f p12 = e2.getsprite().getPosition();

                Vector2f p2 = p.getsprite().getPosition();
                Vector2f p3 = bs.getsprite().getPosition();

                if (e.s.charAt(1) == 'p' && !flage && Double.compare(e.sqrt(p1.x, p1.y, p2.x, p2.y), 100.0) < 0)
                    e.shoot();

                if (e.s.charAt(1) == 'b' && !flagbs && Double.compare(e.sqrt(p1.x, p1.y, p3.x, p3.y), 100.0) < 0)
                    e.shoot();

                if ((e.getsprite().getGlobalBounds().intersection(p.getsprite().getGlobalBounds()) != null))
                    flag = p.kill();

                if (e1.s.charAt(1) == 'p' && !flage1 && Double.compare(e1.sqrt(p11.x, p11.y, p2.x, p2.y), 100.0) < 0)
                    e1.shoot();

                if (e1.s.charAt(1) == 'b' && !flagbs && Double.compare(e1.sqrt(p11.x, p11.y, p3.x, p3.y), 100.0) < 0)
                    e1.shoot();

                if ((e1.getsprite().getGlobalBounds().intersection(p.getsprite().getGlobalBounds()) != null))
                    flag = p.kill();

                if (e2.s.charAt(1) == 'p' && !flage2 && Double.compare(e2.sqrt(p12.x, p12.y, p2.x, p2.y), 100.0) < 0)
                    e2.shoot();

                if (e2.s.charAt(1) == 'b' && !flagbs && Double.compare(e2.sqrt(p12.x, p12.y, p3.x, p3.y), 100.0) < 0)
                    e2.shoot();

                if ((e2.getsprite().getGlobalBounds().intersection(p.getsprite().getGlobalBounds()) != null))
                    flag = p.kill();

                ArrayList<gun> temp = p.getfire();
                // System.out.println("Value="+t.getsprite().getGlobalBounds().intersection((p.getsprite()).getGlobalBounds())!= null);
                if (!flag
                        && !(p.getsprite().getGlobalBounds().intersection((bs.getsprite()).getGlobalBounds()) == null))
                    p.bounce();
                if (!flag1 && e.s.charAt(0) == 'n'
                        && !(e.getsprite().getGlobalBounds().intersection((t.getsprite()).getGlobalBounds()) == null))
                    e.bounce();
                if (!flag1 && e1.s.charAt(0) == 'n'
                        && !(e1.getsprite().getGlobalBounds().intersection((t.getsprite()).getGlobalBounds()) == null))
                    e1.bounce();
                if (!flag1 && e2.s.charAt(0) == 'n'
                        && !(e2.getsprite().getGlobalBounds().intersection((t.getsprite()).getGlobalBounds()) == null))
                    e2.bounce();

                for (brick bg : nbr) {
                    if (!flag && !(p.getsprite().getGlobalBounds()
                            .intersection((bg.getSprite()).getGlobalBounds()) == null)) {
                        p.bounce();
                        System.out.println("player bounces on nbr");
                    }
                    if (!flage && e.s.charAt(0) == 'n' && !(e.getsprite().getGlobalBounds()
                            .intersection((bg.getSprite()).getGlobalBounds()) == null))
                        e.bounce();
                    if (!flage && (e.s.charAt(0) == 't') && (!(e.getsprite().getGlobalBounds()
                            .intersection((bg.getSprite()).getGlobalBounds()) == null))) {
                        e.settrans();
                        // flagt = true;
                        // System.out.println("transHYgrje");
                    } else {
                        e.setback();
                        // flagt = false;
                    }

                    if (!flage1 && e1.s.charAt(0) == 'n' && !(e1.getsprite().getGlobalBounds()
                            .intersection((bg.getSprite()).getGlobalBounds()) == null))
                        e1.bounce();
                    if (!flage1 && (e1.s.charAt(0) == 't') && (!(e1.getsprite().getGlobalBounds()
                            .intersection((bg.getSprite()).getGlobalBounds()) == null))) {
                        e1.settrans();
                        // flagt = true;
                        // System.out.println("trans1HYgrje");
                    } else {
                        e1.setback();
                        // flagt = false;
                    }
                    if (!flage2 && e2.s.charAt(0) == 'n' && !(e2.getsprite().getGlobalBounds()
                            .intersection((bg.getSprite()).getGlobalBounds()) == null))
                        e2.bounce();
                    if (!flage2 && (e2.s.charAt(0) == 't') && (!(e2.getsprite().getGlobalBounds()
                            .intersection((bg.getSprite()).getGlobalBounds()) == null))) {
                        e2.settrans();
                        // flagt = true;
                        // System.out.println("trans2HYgrje");
                    } else {
                        e2.setback();
                        // flagt = false;
                    }
                }
                for (brick bg1 : br) {
                    if (!flag && !(p.getsprite().getGlobalBounds()
                            .intersection((bg1.getSprite()).getGlobalBounds()) == null)) {
                        p.bounce();
                        System.out.println("player bounces");//System.out.println();
                    }
                    if (!flage && e.s.charAt(0) == 'n' && !(e.getsprite().getGlobalBounds()
                            .intersection((bg1.getSprite()).getGlobalBounds()) == null))
                        e.bounce();
                    if (!flage && (e.s.charAt(0) == 't') && !(e.getsprite().getGlobalBounds()
                            .intersection((bg1.getSprite()).getGlobalBounds()) == null)) {
                        e.f.setColor(new Color(255, 255, 255, 128));
                        // e2.settrans();
                        // flagt = true;
                        // System.out.println("trans22HYgrje");
                    } else if (!flage && (e.s.charAt(0) == 't') && !(e.getsprite().getGlobalBounds()
                            .intersection((bg1.getSprite()).getGlobalBounds()) == null)) {
                        e.f.setColor(new Color(255, 255, 255));
                        // e2.setback();
                        // flagt = false;
                    }
                    if (!flage1 && e1.s.charAt(0) == 'n' && !(e1.getsprite().getGlobalBounds()
                            .intersection((bg1.getSprite()).getGlobalBounds()) == null))
                        e1.bounce();
                    if (!flage1 && (e1.s.charAt(0) == 't') && !(e1.getsprite().getGlobalBounds()
                            .intersection((bg1.getSprite()).getGlobalBounds()) == null)) {
                        e1.f.setColor(new Color(255, 255, 255, 128));
                        // e2.settrans();
                        // flagt = true;
                        // System.out.println("trans22HYgrje");
                    } else if (!flage1 && (e1.s.charAt(0) == 't') && !(e1.getsprite().getGlobalBounds()
                            .intersection((bg1.getSprite()).getGlobalBounds()) == null)) {
                        e1.f.setColor(new Color(255, 255, 255));
                        // e2.setback();
                        // flagt = false;
                    }
                    if (!flage2 && e2.s.charAt(0) == 'n' && !(e2.getsprite().getGlobalBounds()
                            .intersection((bg1.getSprite()).getGlobalBounds()) == null))
                        e2.bounce();
                    if (!flage2 && (e2.s.charAt(0) == 't') && !(e2.getsprite().getGlobalBounds()
                            .intersection((bg1.getSprite()).getGlobalBounds()) == null)) {
                        e2.f.setColor(new Color(255, 255, 255, 128));
                        // e2.settrans();
                        // flagt = true;
                        // System.out.println("trans22HYgrje");
                    } else if (!flage2 && (e2.s.charAt(0) == 't') && !(e2.getsprite().getGlobalBounds()
                            .intersection((bg1.getSprite()).getGlobalBounds()) == null)) {
                        e2.f.setColor(new Color(255, 255, 255));
                        // e2.setback();
                        // flagt = false;
                    }
                }
                // System.out.println(e.s);
                // if ((e.s == "t") && (!(e.getsprite().getGlobalBounds()
                //         .intersection((b1.getSprite()).getGlobalBounds()) == null)
                //         || !(e.getsprite().getGlobalBounds().intersection((b.getSprite()).getGlobalBounds()) == null))) {
                //     e.settrans();
                //     flagt = true;
                // System.out.println("HYgrje");
                // } else {
                //     e.setback();
                //     flagt = false;
                // }

                for (gun g : temp) {
                    for (brick bgg : br)
                        if (!(((g.fire).getGlobalBounds()).intersection((bgg.getSprite()).getGlobalBounds()) == null)) {
                            bgg.shot();
                            System.out.println("HYgrje78998");
                            st.add(new Integer(temp.indexOf(g)));
                            g.fire.setColor(new Color(0, 0, 0, 0));

                        }
                    for (brick bgg1 : nbr)
                        if (!(((g.fire).getGlobalBounds())
                                .intersection((bgg1.getSprite()).getGlobalBounds()) == null)) {
                            System.out.println("HYgrje78998");
                            bgg1.shot();
                            st.add(new Integer(temp.indexOf(g)));
                            g.fire.setColor(new Color(0, 0, 0, 0));

                        }
                    if (!((g.fire.getGlobalBounds()).intersection(t.getsprite().getGlobalBounds()) == null)) {
                        flag1 = t.shot();
                        g.fire.setColor(new Color(0, 0, 0, 0));
                        st.add(new Integer(temp.indexOf(g)));
                        // System.out.println("HYgrje");
                    }
                    if (g.fire.getGlobalBounds().intersection(e.getsprite().getGlobalBounds()) != null) {
                        {
                            flage = e.shot();
                            g.fire.setColor(new Color(0, 0, 0, 0));
                            st.add(new Integer(temp.indexOf(g)));
                        }
                    }
                    if (g.fire.getGlobalBounds().intersection(e1.getsprite().getGlobalBounds()) != null) {
                        {
                            flage1 = e1.shot();
                            g.fire.setColor(new Color(0, 0, 0, 0));
                            st.add(new Integer(temp.indexOf(g)));
                        }
                    }
                    if (g.fire.getGlobalBounds().intersection(e2.getsprite().getGlobalBounds()) != null) {
                        {
                            flage2 = e2.shot();
                            g.fire.setColor(new Color(0, 0, 0, 0));
                            st.add(new Integer(temp.indexOf(g)));
                        }
                    }

                }

                for (Integer i : st) {
                    p.del(i.intValue());
                }
                // if (flag || flagbs || (flage && flage1 && flage2)) {
                //     gameover = true;
                //     if(gameover) gameover1=true;
                //     flagscreen = false;
                // }
                if (flage && flage1 && flage2) {
                    if (flageasy) {
                        if (!gameover) {
                            gameover = true;
                            deltatime = time.restart();
                            score += Math.round(deltatime.asSeconds()) + 50;
                            System.out.println("1");
                        } else if (gameover && !gameover1) {
                            gameover1 = true;
                            deltatime = time.getElapsedTime();
                            score += Math.round(deltatime.asSeconds()) + 50;
                            System.out.println("2");
                        }
                    } else {
                        if (!gameover && flag1) {
                            gameover = true;
                            deltatime = time.restart();
                            score += Math.round(deltatime.asSeconds()) + 50;
                        } else if (!gameover1 && flag1) {
                            gameover1 = true;
                            deltatime = time.getElapsedTime();
                            score += Math.round(deltatime.asSeconds()) + 50;
                        }
                    }

                } else if (bl == 0 || pl == 0) {
                    flagscreen = true;
                    System.out.println("3");
                    lostflag = true;
                    deltatime = time.getElapsedTime();
                    score += Math.round(deltatime.asSeconds());
                }

            }
            if ((!gameover||!gameover1) && flaghard && (bl == 0 || pl == 0)) {
                flagscreen = true;
                // System.out.println("3");
                lostflag = true;
                deltatime = time.getElapsedTime();
                score += Math.round(deltatime.asSeconds());
            }

            // window.draw(shape);

            if (!flag)
                window.draw(p.getsprite());
            // window.draw(b.getSprite());
            // window.draw(b1.getSprite());
            // window.draw(bx.getSprite());
            // window.draw(by.getSprite());
            // window.draw(bz.getSprite());
            // window.draw(bw.getSprite());

            for (gun g : p.getfire())
                window.draw(g.fire);
            if (!flag1 && !flageasy) {
                window.draw(t.getsprite());
                for (gun g : t.getfire())
                    window.draw(g.fire);
            }
            if (!flage)
                window.draw(e.getsprite());
            for (gun g : e.getfire())
                window.draw(g.fire);
            if (!flage1)
                window.draw(e1.getsprite());
            for (gun g : e1.getfire())
                window.draw(g.fire);
            if (!flage2)
                window.draw(e2.getsprite());
            for (gun g : e2.getfire())
                window.draw(g.fire);
            // window.draw(e1.getsprite());
            // window.draw(e2.getsprite());
            window.draw(bs.getsprite());

            for (brick b2 : nbr)
                window.draw(b2.getSprite());
            for (brick b2 : br)
                window.draw(b2.getSprite());
            for (int i = 0; i < 64; i++) {
                window.draw(m[i]);
            }
            window.draw(plife);
            window.draw(blife);

            if (!level2 && gameover) {
                for (int i = 0; i < br.length; i++) {
                    br[i] = new brick(bri, 3);
                    br[i].set(-50, -50);
                }
                br[0].set(320 - 17 - 29, 480 - 240);
                br[1].set(320 - 17 - 29, 480 - 30 - 240);
                br[2].set(320 + 12, 480 - 240);
                br[3].set(320 + 12, 480 - 30 - 240);
                br[4].set(320 - 17, 480 - 30 - 240);
                br[5].set(320 - 17 - 29, 480 + 29 - 240);
                br[6].set(320 + 12, 480 + 29 - 240);
                br[7].set(320 - 17, 480 + 29 - 240);
                bs = new base(base);
                bs.set(320.0 - 2, 480.0 - 17 - 240 + 29);
                p = new player();
                flag = false;
                flage = false;
                flage1 = false;
                flage2 = false;
                e = new enemy("nb", 200.0, 0.0, ene, p, bs);
                e1 = new enemy("np", 200.0, 20.0, ene, p, bs);
                e2 = new enemy("np", 200.0, 40.0, ene, p, bs);
                t = new turret(tur, 5, 360, 45);
                t.set(320.0, 17.0);
                if (!flageasy) {
                    flag1 = false;
                    p.setmulval(3);
                    e.setmulval(2);
                    e1.setmulval(2);
                    e2.setmulval(2);
                    if (flaghard) {
                        e = new enemy("tb", 200.0, 0.0, ene, p, bs);
                        e1 = new enemy("tp", 200.0, 20.0, ene, p, bs);
                        e2 = new enemy("tp", 200.0, 40.0, ene, p, bs);
                        t.shootamt = 30;
                        p.setmulval(3);
                        e.setmulval(3);
                        e1.setmulval(3);
                        e2.setmulval(3);
                    }
                }

                for (int i = 0; i < 16; i++)
                    m[i].setPosition(140 - 29 - 29, i * 30);
                for (int i = 16; i < 32; i++)
                    m[i].setPosition(140, (i - 16) * 30);
                for (int i = 32; i < 48; i++)
                    m[i].setPosition(640 - (140 - 29), (i - 32) * 30);
                for (int i = 48; i < 64; i++)
                    m[i].setPosition(640 - (140 + 29), (i - 48) * 30);
                for (int i = 26; i < 36; i++)
                    br[i].set(140 + 30 * (i - 26 + 1), 90);
                for (int i = 36; i < 46; i++)
                    br[i].set(140 + 30 * (i - 36 + 1), 480 - 90);

                level2 = true;
                // window.draw(rect);
                // window.draw(gbox);
                // window.draw(gtext);
            }
            if ((flagscreen && (gameover || gameover1)) || (flagscreen && (!gameover && lostflag))) {
                int offset = 0;
                deltatime = time.getElapsedTime();
                if (flagscore && ((gameover && gameover1) || lostflag || (!gameover && lostflag))) {
                    System.out.println("4");
                    if (gameover && gameover1) {
                        offset = 100;
                        score += offset;
                        gtext.setString("YOU WON!!SCORE:" + new Integer(score + offset).toString());
                        gtext.setScale(0.52f, 0.52f);
                        gamestart = false;
                        window.draw(rect);
                        window.draw(gbox);
                        window.draw(gtext);
                        System.out.println("5");

                    } else if (lostflag) {
                        gtext.setString("YOU LOST!!SCORE:" + new Integer(score + offset).toString());
                        System.out.println("The application has run for " + new Float(deltatime.asSeconds()).toString()
                                + " seconds.");
                        gtext.setScale(0.52f, 0.52f);
                        gamestart = false;
                        window.draw(rect);
                        window.draw(gbox);
                        window.draw(gtext);
                        System.out.println("6");

                    }
                    flagscore = false;
                }
            }

            if (!gamestart && ((gameover) && gameover1 || lostflag)) {
                window.draw(rect);
                window.draw(gbox);
                window.draw(gtext);
            }
            if (!flagscreen && !gameover) {
                window.draw(rect);
                if (!subflag) {
                    window.draw(menu1);
                    window.draw(menu2);
                    window.draw(menu3);
                    window.draw(text);
                    window.draw(text1);
                    window.draw(text2);
                }
                if (subflag) {
                    window.draw(submenu1);
                    window.draw(submenu2);
                    window.draw(submenu3);
                    window.draw(subtext);
                    window.draw(subtext1);
                    window.draw(subtext2);
                }
            }
            if (instructionflag)
                window.draw(inst);
            window.display();

            //Handle events
            for (Event event : window.pollEvents()) {
                switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case MOUSE_MOVED:
                    if (menu1.getGlobalBounds().contains(event.asMouseEvent().position.x,
                            event.asMouseEvent().position.y)) {
                        menu1.setFillColor(new Color(255, 0, 0, 128));
                    } else {
                        menu1.setFillColor(new Color(255, 0, 0));
                    }
                    if (menu2.getGlobalBounds().contains(event.asMouseEvent().position.x,
                            event.asMouseEvent().position.y)) {
                        menu2.setFillColor(new Color(0, 255, 0, 128));
                    } else {
                        menu2.setFillColor(new Color(0, 255, 0));
                    }
                    if (menu3.getGlobalBounds().contains(event.asMouseEvent().position.x,
                            event.asMouseEvent().position.y)) {
                        menu3.setFillColor(new Color(0, 0, 255, 128));
                    } else {
                        menu3.setFillColor(new Color(0, 0, 255));
                    }
                    if (subflag) {
                        if (submenu1.getGlobalBounds().contains(event.asMouseEvent().position.x,
                                event.asMouseEvent().position.y)) {
                            submenu1.setFillColor(new Color(255, 255, 0, 128));
                        } else {
                            submenu1.setFillColor(new Color(255, 255, 0));
                        }
                        if (submenu2.getGlobalBounds().contains(event.asMouseEvent().position.x,
                                event.asMouseEvent().position.y)) {
                            submenu2.setFillColor(new Color(0, 255, 255, 128));
                        } else {
                            submenu2.setFillColor(new Color(0, 255, 255));
                        }
                        if (submenu3.getGlobalBounds().contains(event.asMouseEvent().position.x,
                                event.asMouseEvent().position.y)) {
                            submenu3.setFillColor(new Color(255, 0, 255, 128));
                        } else {
                            submenu3.setFillColor(new Color(255, 0, 255));
                        }
                    }
                    break;
                case MOUSE_BUTTON_PRESSED:
                    if (!subflag) {
                        if (menu1.getGlobalBounds().contains(event.asMouseButtonEvent().position.x,
                                event.asMouseButtonEvent().position.y)) {
                            menu1.setFillColor(new Color(255, 0, 0, 128));
                            subflag = true;
                        }
                        if (menu2.getGlobalBounds().contains(event.asMouseButtonEvent().position.x,
                                event.asMouseButtonEvent().position.y)) {
                            menu2.setFillColor(new Color(0, 255, 0, 128));
                            instructionflag = true;

                        }
                        if (menu3.getGlobalBounds().contains(event.asMouseButtonEvent().position.x,
                                event.asMouseButtonEvent().position.y)) {
                            System.out.println("tyfThe user pressed a keyboard key!");
                            window.close();
                        }
                    } else {
                        if (submenu1.getGlobalBounds().contains(event.asMouseEvent().position.x,
                                event.asMouseEvent().position.y)) {
                            // e.s = new String("tp");
                            // e1.s = new String("tb");
                            // e2.s = new String("tp");
                            flagscreen = true;
                            flag1 = true;
                            gamestart = true;
                            flageasy = true;
                            p.setmulval(2);
                        }
                        if (submenu2.getGlobalBounds().contains(event.asMouseEvent().position.x,
                                event.asMouseEvent().position.y)) {
                            flag1 = false;
                            gamestart = true;
                            flagscreen = true;
                            p.setmulval(3);
                            e.setmulval(2);
                            e1.setmulval(2);
                            e2.setmulval(2);
                            // submenu2.setFillColor(new Color(0, 255, 255, 128));
                            // } else {
                            //     submenu2.setFillColor(new Color(0, 255, 255));
                        }
                        if (submenu3.getGlobalBounds().contains(event.asMouseEvent().position.x,
                                event.asMouseEvent().position.y)) {
                            flag1 = false;
                            gamestart = true;
                            flaghard = true;
                            flagscreen = true;
                            e.s = new String("tp");
                            e1.s = new String("tb");
                            e2.s = new String("tp");
                            t.shootamt = 30;
                            p.setmulval(3);
                            e.setmulval(3);
                            e1.setmulval(3);
                            e2.setmulval(3);
                            // System.out.println("The user pressed a keyboard key!");
                            // submenu3.setFillColor(new Color(255, 0, 255, 128));
                            // } else {
                            //     submenu3.setFillColor(new Color(255, 0, 255));
                        }
                    }
                    break;
                case MOUSE_BUTTON_RELEASED:
                    if (submenu1.getGlobalBounds().contains(event.asMouseButtonEvent().position.x,
                            event.asMouseButtonEvent().position.y)) {
                        // submenu1.setFillColor(new Color(255, 0, 0));
                    }
                    if (submenu2.getGlobalBounds().contains(event.asMouseButtonEvent().position.x,
                            event.asMouseButtonEvent().position.y)) {
                        // submenu2.setFillColor(new Color(0, 255, 0));
                    }
                    if (submenu3.getGlobalBounds().contains(event.asMouseButtonEvent().position.x,
                            event.asMouseButtonEvent().position.y)) {
                        // menu3.setFillColor(new Color(0, 0, 255));
                    }
                    break;

                case KEY_PRESSED:
                    // System.out.println("The user pressed a keyboard key!"+event.key.code);                
                    KeyEvent keyEvent = event.asKeyEvent();
                    switch (keyEvent.key) {
                    case UP:
                        p.getdir(8);
                        break;
                    case DOWN:
                        p.getdir(2);
                        break;
                    case LEFT:
                        p.getdir(4);
                        break;
                    case RIGHT:
                        p.getdir(6);
                        break;
                    case BACKSPACE:
                        if (subflag) {
                            subflag = false;
                        }
                        if (instructionflag) {
                            instructionflag = false;
                        }
                    case SPACE:
                        if (!flag) {
                            p.shoot();
                            System.out.println("The user pressed the following key: " + keyEvent.key);
                        }
                        break;
                    }
                    //     // System.out.println("The user pressed the following key: " + keyEvent.key);
                    //     break;                   
                    //     // switch(keyEvent){
                    //     //     case 
                    // default:break;
                    //     }
                }
            }
        }
    }
}