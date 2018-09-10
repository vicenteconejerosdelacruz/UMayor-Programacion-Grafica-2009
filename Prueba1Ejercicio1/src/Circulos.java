/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.applet.Applet;
import java.awt.Graphics;
/**
 *
 * @author Vicente
 */


public class Circulos extends Applet {

    public static int VIEWPORT_WIDTH    = 640;
    public static int VIEWPORT_HEIGHT   = 480;
    public static int BIG_CIRCLE_RADIUS = 200;
    public static int SMALL_CIRCLE_RADIUS = 20;
    public boolean collide;
    double x[];
    double y[];
    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    public void init() {
        // TODO start asynchronous download of heavy resources
        this.resize(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        collide = false;

        x=new double[2];
        y=new double[2];

        y[0]=0;
        y[1]=0;

        x[0]=-BIG_CIRCLE_RADIUS-SMALL_CIRCLE_RADIUS;
        x[1]=BIG_CIRCLE_RADIUS+SMALL_CIRCLE_RADIUS;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        this.resize(VIEWPORT_WIDTH,VIEWPORT_HEIGHT);

        if(!collide)
        {
            for(int i=0;i<2;i++)
            {
                double Arc=Math.toRadians(1.0 + 4.0*Math.random());

                //si es que se es impaciente
                //habilitar estas lineas para chequear
                //que funcionan las colisiones
                //if(i==0)
                //   Arc=0;

                x[i]=Math.cos(Arc)*x[i] - Math.sin(Arc)*y[i];
                y[i]=Math.sin(Arc)*x[i] + Math.cos(Arc)*y[i];
                //reajuste de distancia por perdida por aproximaciones
                double len = Math.sqrt(x[i]*x[i] + y[i]*y[i]);
                x[i]/=len;
                y[i]/=len;
                x[i]*=(BIG_CIRCLE_RADIUS+SMALL_CIRCLE_RADIUS);
                y[i]*=(BIG_CIRCLE_RADIUS+SMALL_CIRCLE_RADIUS);
            }
            double dist = Math.sqrt((x[0]-x[1])*(x[0]-x[1]) + (y[0]-y[1])*(y[0]-y[1]));
            if(dist<2*SMALL_CIRCLE_RADIUS)
                collide=true;
        }

        g.clearRect(0,0,VIEWPORT_WIDTH,VIEWPORT_HEIGHT);
        
        g.drawOval(VIEWPORT_WIDTH/2-BIG_CIRCLE_RADIUS,VIEWPORT_HEIGHT/2-BIG_CIRCLE_RADIUS,BIG_CIRCLE_RADIUS*2,BIG_CIRCLE_RADIUS*2);
        g.drawOval(VIEWPORT_WIDTH/2 - (int)x[0] - SMALL_CIRCLE_RADIUS,VIEWPORT_HEIGHT/2 - (int)y[0] - SMALL_CIRCLE_RADIUS,SMALL_CIRCLE_RADIUS*2,SMALL_CIRCLE_RADIUS*2);
        g.drawOval(VIEWPORT_WIDTH/2 - (int)x[1] - SMALL_CIRCLE_RADIUS,VIEWPORT_HEIGHT/2 - (int)y[1] - SMALL_CIRCLE_RADIUS,SMALL_CIRCLE_RADIUS*2,SMALL_CIRCLE_RADIUS*2);

        pausa(40);

        repaint();
    }

    void pausa(int n)
    {
        try
        {
            Thread.sleep(n);
        }
        catch(Exception e)
        {

        }
    }

    // TODO overwrite start(), stop() and destroy() methods
}
