/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
/**
 *
 * @author Vicente
 */
public class Areas extends Applet {

    public static int VIEWPORT_WIDTH    = 640;
    public static int VIEWPORT_HEIGHT   = 480;
    public static int CIRCLE_RADIUS = 200;
    public static int NUM_SECANTES = 3;
    public static double VELOCITY   = 3.0;

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */

    public double x[];
    public double y[];

    public int mask[][];

    public Image backbuffer;
    public Graphics backbufferGraphics;
    public Color[] colorList;
    
    @Override
    public void init() {
        
        x = new double[NUM_SECANTES*2];
        y = new double[NUM_SECANTES*2];

        mask = new int[VIEWPORT_HEIGHT][VIEWPORT_WIDTH];
        for(int i=0;i<VIEWPORT_HEIGHT;i++)
        {
            for(int j=0;j<VIEWPORT_WIDTH;j++)
            {
                mask[i][j]=0;
            }
        }

        for(int i=0;i<NUM_SECANTES*2;i++)
        {
            double ang=Math.toRadians(Math.random()*360);
            x[i]=(double)(CIRCLE_RADIUS)*Math.cos(ang);
            y[i]=(double)(CIRCLE_RADIUS)*Math.sin(ang);
        }

        backbuffer = this.createImage(VIEWPORT_WIDTH,VIEWPORT_HEIGHT);
        backbufferGraphics = backbuffer.getGraphics();
        int listSize = (1<<NUM_SECANTES);
        colorList = new Color[listSize];
        for(int i=0;i<colorList.length;i++)
        {
            colorList[i]=Color.getHSBColor((float)Math.random(),(float)Math.random(),(float)Math.random());
        }
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void paint(Graphics g) {

        this.resize(VIEWPORT_WIDTH,VIEWPORT_HEIGHT);
        backbufferGraphics.clearRect(0,0,VIEWPORT_WIDTH,VIEWPORT_HEIGHT);
        
        double Arc = Math.toRadians(VELOCITY);
        double cosArc = Math.cos(Arc);
        double sinArc = Math.sin(Arc);

        for(int i=0;i<NUM_SECANTES*2;i++)
        {
            x[i]=cosArc*x[i] - sinArc*y[i];
            y[i]=sinArc*x[i] + cosArc*y[i];
            //reajuste de distancia por perdida por aproximaciones
            double len = Math.sqrt(x[i]*x[i] + y[i]*y[i]);
            x[i]/=len;
            y[i]/=len;
            x[i]*=CIRCLE_RADIUS;
            y[i]*=CIRCLE_RADIUS;
        }

        for(int i=0;i<VIEWPORT_HEIGHT;i++)
        {
            for(int j=0;j<VIEWPORT_WIDTH;j++)
            {
                mask[i][j]=0;
            }
        }

        for(int i=0;i<NUM_SECANTES;i++)
        {
            double deltax = x[i*2+1] - x[i*2];
            double deltay = y[i*2+1] - y[i*2];

            double normalx = -deltay;
            double normaly = deltax;

            for(int xi=0;xi<VIEWPORT_WIDTH;xi++)
            {
                for(int yi=0;yi<VIEWPORT_HEIGHT;yi++)
                {
                    double px = (xi - VIEWPORT_WIDTH/2) - x[i*2];
                    double py = (yi - VIEWPORT_HEIGHT/2) - y[i*2];

                    double dot = getDotProduct(normalx,normaly,px,py);
                    if(dot<0.0)
                    {
                        mask[yi][xi]|=1<<i;
                    }
                }
            }
        }

        for(int xi=0;xi<VIEWPORT_WIDTH;xi++)
        {
            for(int yi=0;yi<VIEWPORT_HEIGHT;yi++)
            {
                double px = xi - VIEWPORT_WIDTH/2;
                double py = yi - VIEWPORT_HEIGHT/2;

                if(getDistance(px,py,0,0)>CIRCLE_RADIUS)
                {
                    continue;
                }

                int value=mask[yi][xi];
                backbufferGraphics.setColor(colorList[value]);
                backbufferGraphics.drawRect(xi,yi,1,1);
            }
        }

        backbufferGraphics.setColor(Color.black);
        backbufferGraphics.drawOval( VIEWPORT_WIDTH/2 - CIRCLE_RADIUS,
                    VIEWPORT_HEIGHT/2 - CIRCLE_RADIUS,
                    CIRCLE_RADIUS*2,
                    CIRCLE_RADIUS*2);

        for(int i=0;i<NUM_SECANTES;i++)
        {
           backbufferGraphics.drawLine(VIEWPORT_WIDTH/2 + (int)x[i*2],VIEWPORT_HEIGHT/2 + (int)y[i*2], VIEWPORT_WIDTH/2 +(int)x[i*2+1],VIEWPORT_HEIGHT/2 + (int)y[i*2+1]);
        }

        repaint();
    }

    @Override
   public void update( Graphics g ) {
      paint(g);
      g.clearRect(0,0,VIEWPORT_WIDTH,VIEWPORT_HEIGHT);
      g.drawImage( backbuffer, 0, 0, this );
     //pausa(40);
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

    public double getDotProduct(double x1,double y1,double x2,double y2)
    {
        return x1*x2 + y1*y2;
    }

       public double getDistance(double x1,double y1,double x2,double y2)
    {
        return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }

}
