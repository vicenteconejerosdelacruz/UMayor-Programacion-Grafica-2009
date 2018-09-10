/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
/**
 *
 * @author Vicente
 */
public class Rebote extends Applet {

    public static int VIEWPORT_WIDTH    = 640;
    public static int VIEWPORT_HEIGHT   = 480;

    public static int WALL_DISTANCE_X   = 10;
    public static int WALL_DISTANCE_Y   = 10;

    public static int CIRCLE_RADIUS     = 20;
    public static double VELOCITY       = 10;
    public static double EPSILON        = 0.1;

    public double pos[];
    public double targetPos[];
    public double circlePos[];
    private int numRebotes;

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    @Override
    public void init() {
        pos=getNewPos();
        targetPos=getNewPos();
        circlePos = new double[2];
        circlePos[0] = pos[0];
        circlePos[1] = pos[1];
        numRebotes = 0;
        this.resize(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        dumpPositions();
        // TODO start asynchronous download of heavy resources
    }

    @Override
    public void paint(Graphics g) {

        this.resize(VIEWPORT_WIDTH,VIEWPORT_HEIGHT);
        String str = "Rebotes:"+numRebotes;

        g.clearRect(0,0,VIEWPORT_WIDTH,VIEWPORT_HEIGHT);

        g.drawBytes(str.getBytes(),0,str.length(),WALL_DISTANCE_X,WALL_DISTANCE_Y);
        g.drawLine(WALL_DISTANCE_X, WALL_DISTANCE_Y, VIEWPORT_WIDTH-WALL_DISTANCE_X, WALL_DISTANCE_Y);
        g.drawLine(WALL_DISTANCE_X, VIEWPORT_HEIGHT - WALL_DISTANCE_Y, VIEWPORT_WIDTH-WALL_DISTANCE_X, VIEWPORT_HEIGHT - WALL_DISTANCE_Y);
        g.drawLine(WALL_DISTANCE_X,WALL_DISTANCE_Y,WALL_DISTANCE_X,VIEWPORT_HEIGHT-WALL_DISTANCE_Y);
        g.drawLine(VIEWPORT_WIDTH-WALL_DISTANCE_X,WALL_DISTANCE_Y,VIEWPORT_WIDTH-WALL_DISTANCE_X,VIEWPORT_HEIGHT-WALL_DISTANCE_Y);
        if(numRebotes<20)
        {
            integrate();

            double dir[] = getDirection();
            double delta[] = getDelta();
            double dot = getDotProduct(dir,delta);
            if(dot<0.0)
            {
                numRebotes++;
                circlePos[0] = targetPos[0];
                circlePos[1] = targetPos[1];

                if(numRebotes<20)
                {
                    pos[0] = targetPos[0];
                    pos[1] = targetPos[1];

                    targetPos = getNewPos();
                }
            }
        }

        drawCircleAt(g,circlePos,CIRCLE_RADIUS);
        pausa(40);
        repaint();
    }

    double[] getNewPos()
    {
        double newpos[]=new double[2];
        double idx = Math.random()*4;
        if(idx>2)
        {
            newpos[1]=WALL_DISTANCE_Y + CIRCLE_RADIUS + Math.random()*(VIEWPORT_HEIGHT - WALL_DISTANCE_Y*2 - CIRCLE_RADIUS*2);

            if(idx>3)
            {
                newpos[0]=WALL_DISTANCE_X + CIRCLE_RADIUS;
            }
            else
            {
                newpos[0]=VIEWPORT_WIDTH - WALL_DISTANCE_X - CIRCLE_RADIUS;
            }
        }
        else
        {
            newpos[0]=WALL_DISTANCE_X + CIRCLE_RADIUS + Math.random()*(VIEWPORT_WIDTH - WALL_DISTANCE_X*2 - CIRCLE_RADIUS*2);

            if(idx>1)
            {
                newpos[1]=WALL_DISTANCE_Y + CIRCLE_RADIUS;
            }
            else
            {
                newpos[1]=VIEWPORT_HEIGHT - WALL_DISTANCE_Y - CIRCLE_RADIUS;
            }        
        }
        
        return newpos;
    }

    public double getDistance(double pos1[],double pos2[])
    {
        return Math.sqrt((pos1[0]-pos2[0])*(pos1[0]-pos2[0]) + (pos1[1]-pos2[1])*(pos1[1]-pos2[1]));
    }

    public double getDotProduct(double v1[],double v2[])
    {
        return v1[0]*v2[0] + v1[1]*v2[1];
    }

    public double[] getDirection()
    {
        double dir[] = new double[2];
        dir[0]=targetPos[0]-pos[0];
        dir[1]=targetPos[1]-pos[1];
        double len = Math.sqrt(dir[0]*dir[0] + dir[1]*dir[1]);
        dir[0]/=len;
        dir[1]/=len;
        return dir;
    }

    public double[] getDelta()
    {
        double dt[] = new double[2];
        dt[0] = targetPos[0]-circlePos[0];
        dt[1] = targetPos[1]-circlePos[1];
        return dt;
    }

    public void integrate()
    {
        double dir[] = getDirection();
        for(int i=0;i<2;i++)
        {
            dir[i]*=VELOCITY;
            circlePos[i]+=dir[i];
        }
    }

    public void dumpPositions()
    {
        System.out.println("OriginPos = ["+pos[0]+","+pos[1]+"]");
        System.out.println("TargetPos = ["+targetPos[0]+","+targetPos[1]+"]");
        System.out.println("circlePos = ["+circlePos[0]+","+circlePos[1]+"]");
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

    private void drawCircleAt(Graphics g,double[] circlePos, int radius)
    {
        g.drawOval((int)circlePos[0]-radius,(int)circlePos[1]-radius,2*radius,2*radius);
    }
    // TODO overwrite start(), stop() and destroy() methods
}
