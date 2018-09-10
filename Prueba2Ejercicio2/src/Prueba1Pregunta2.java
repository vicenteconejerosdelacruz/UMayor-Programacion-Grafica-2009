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
public class Prueba1Pregunta2 extends Applet {

    public static int VIEWPORT_WIDTH    = 640;
    public static int VIEWPORT_HEIGHT   = 480;
    public static double BIG_TRIANGLE_SIDE	= 200.0;
    public static double ANGULAR_VELOCITY = 0.1;

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */

    public double currentAngle;
    
    public double x[];
    public double y[];
    
    public int xint[];
    public int yint[];
    
    public Image backbuffer;
    public Graphics backbufferGraphics;
    public Color[] colorList;
    
    @Override
    public void init() {
        
    	currentAngle = 0.0;
    	
    	x = new double[12];
    	y = new double[12];
    	
    	xint = new int[3];
    	yint = new int[3];
    	
    	x[0]= 0;
    	x[1]= -BIG_TRIANGLE_SIDE/4.0;
    	x[2]=  BIG_TRIANGLE_SIDE/4.0;
    	
    	y[0]= -2*Math.sqrt(3.0)*BIG_TRIANGLE_SIDE/4.0 + 0.5*BIG_TRIANGLE_SIDE*(Math.sqrt(3.0)/3.0);
    	y[1] = -Math.sqrt(3.0)*BIG_TRIANGLE_SIDE/4.0 + 0.5*BIG_TRIANGLE_SIDE*(Math.sqrt(3.0)/3.0);
    	y[2] = -Math.sqrt(3.0)*BIG_TRIANGLE_SIDE/4.0 + 0.5*BIG_TRIANGLE_SIDE*(Math.sqrt(3.0)/3.0);
    	    	
    	for(int i=0;i<3;i++)
    	{
    		x[i + 3] = x[i] - BIG_TRIANGLE_SIDE/4;   
    		x[i + 6] = x[i] + BIG_TRIANGLE_SIDE/4;
    		x[i + 9] = x[i];
    		
        	y[i + 3] = y[i] + Math.sqrt(3.0)*BIG_TRIANGLE_SIDE/4.0;
            y[i + 6] = y[i] + Math.sqrt(3.0)*BIG_TRIANGLE_SIDE/4.0;
            y[i + 9] = y[i] + Math.sqrt(3.0)*BIG_TRIANGLE_SIDE/4.0 - 0.5*BIG_TRIANGLE_SIDE*(Math.sqrt(3.0)/3.0);
            y[i + 9]*=-1;
            y[i + 9]-=Math.sqrt(3.0)*BIG_TRIANGLE_SIDE/4.0 - 0.5*BIG_TRIANGLE_SIDE*(Math.sqrt(3.0)/3.0);
    	}
    	
        backbuffer = this.createImage(VIEWPORT_WIDTH,VIEWPORT_HEIGHT);
        backbufferGraphics = backbuffer.getGraphics();
        int listSize = 4;
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
        
        currentAngle+=ANGULAR_VELOCITY;
        
        for(int i=0;i<x.length/3;i++)
        {
            double Arc = Math.toRadians(currentAngle);
            double cosArc = Math.cos(Arc);
            double sinArc = Math.sin(Arc);
            
            for(int j=0;j<3;j++)
            {
            	xint[j] = VIEWPORT_WIDTH/2 + (int)(cosArc*x[i*3+j] - sinArc*y[i*3+j]);
            	yint[j] = VIEWPORT_HEIGHT/2 + (int)(sinArc*x[i*3+j] + cosArc*y[i*3+j]);
            }
            
        	backbufferGraphics.setColor(colorList[i]);
        	backbufferGraphics.fillPolygon(xint, yint, 3);
        }
        
        backbufferGraphics.setColor(Color.black);

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
}

