/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.applet.Applet;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
/**
 *
 * @author Vicente
 */
public class Prueba1Pregunta1 extends Applet {

    public static int VIEWPORT_WIDTH    = 640;
    public static int VIEWPORT_HEIGHT   = 480;
    public static int POINTS_PER_SQUARE = 4;
    public static int SQUARE_DIMENTION	= 16;
    public static int SQUARES_PER_AXIS	= 8;
    public static int SQUARES_PER_FACE	= SQUARES_PER_AXIS*SQUARES_PER_AXIS;
    public static int TOTAL_FACES		= 3;
    public static int POINTS_IN_FACE	= POINTS_PER_SQUARE*SQUARES_PER_FACE;
    public static int POINTS_PER_ROW	= POINTS_IN_FACE*SQUARES_PER_AXIS;
    public static int NUM_TOTAL_POINTS	= TOTAL_FACES*POINTS_IN_FACE;
    
    
    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */

    public double x[];
    public double y[];
    
    public int xint[];
    public int yint[];
    
    public int position[];
    
    public Image backbuffer;
    public Graphics backbufferGraphics;
    public Color[] colorList;
    
    @Override
    public void init() {     

    	position = new int[3];
    	position[0]=0;
    	position[1]=0;
    	position[2]=0;
    	
    	double axis1[]=new double[2];
    	double axis2[]=new double[2];
    	double pivot[]=new double[2];
    	
    	x = new double[NUM_TOTAL_POINTS];
    	y = new double[NUM_TOTAL_POINTS];
    	
    	xint = new int[4];
    	yint = new int[4];
    	
    	//ni siquiera son necesarios los pivotes
    	pivot[0]=0;
    	pivot[1]=0;    	
    	
    	double cos30=Math.sqrt(3.0)/2.0;
    	double sin30=0.5;    	
    	
    	axis1[0]=-cos30;
    	axis1[1]=-sin30;
    	
    	axis2[0]=0;
    	axis2[1]=1;
    	
    	createSquare(x,y,axis1,axis2,pivot,0);
    	
    	axis1[0]=cos30;
    	axis1[1]=-sin30;
    	
    	createSquare(x,y,axis1,axis2,pivot,POINTS_IN_FACE);	
    	
    	axis1[0]=cos30;
    	axis1[1]=-sin30;
    	
    	axis2[0]=-cos30;
    	axis2[1]=-sin30;
    	
    	createSquare(x,y,axis1,axis2,pivot,POINTS_IN_FACE*2);    	
    	
        backbuffer = this.createImage(VIEWPORT_WIDTH,VIEWPORT_HEIGHT);
        backbufferGraphics = backbuffer.getGraphics();
    }

    public void createSquare(double xv[],double yv[],double axis1[],double axis2[],double pivot[],int auxoffset)
    {
    	for(int yindex=0;yindex<SQUARES_PER_AXIS;yindex++)
    	{   	    		
    		for(int xindex=0;xindex<SQUARES_PER_AXIS;xindex++)
    		{
    			int offset = yindex*SQUARES_PER_AXIS + xindex;    			
    				offset*=POINTS_PER_SQUARE;
    				offset+=auxoffset;
    				
    				x[offset] = axis2[0]*xindex*SQUARE_DIMENTION + yindex*axis1[0]*SQUARE_DIMENTION + pivot[0];
    				x[offset+1] = axis2[0]*(xindex+1)*SQUARE_DIMENTION + yindex*axis1[0]*SQUARE_DIMENTION + pivot[0];
    				x[offset+2] = axis2[0]*(xindex+1)*SQUARE_DIMENTION + (yindex+1)*axis1[0]*SQUARE_DIMENTION + pivot[0];
    				x[offset+3] = axis2[0]*xindex*SQUARE_DIMENTION + (yindex+1)*axis1[0]*SQUARE_DIMENTION + pivot[0];
    				
    				y[offset] = axis2[1]*xindex*SQUARE_DIMENTION + yindex*axis1[1]*SQUARE_DIMENTION + pivot[1];
    				y[offset+1] = axis2[1]*(xindex+1)*SQUARE_DIMENTION + yindex*axis1[1]*SQUARE_DIMENTION + pivot[1];
    				y[offset+2] = axis2[1]*(xindex+1)*SQUARE_DIMENTION + (yindex+1)*axis1[1]*SQUARE_DIMENTION + pivot[1];
    				y[offset+3] = axis2[1]*xindex*SQUARE_DIMENTION + (yindex+1)*axis1[1]*SQUARE_DIMENTION + pivot[1];
    		}
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
       
        drawSquare(x,y,0,POINTS_IN_FACE,position[2],position[0],position[1]==0);
        drawSquare(x,y,POINTS_IN_FACE,POINTS_IN_FACE,position[2],position[1],position[0]==0);
        drawSquare(x,y,POINTS_IN_FACE*2,POINTS_IN_FACE,position[0],position[1],(position[2]==0));
        
        repaint();
    }
    
    public void drawSquare(double x[],double y[],int offset,int numToDraw,int xpos,int ypos,boolean drawFill)
    {
        for(int i=0;i<numToDraw;i+=4)
        {
			for(int j=0;j<4;j++)
			{
				xint[j]=(int)x[offset+i+j] + VIEWPORT_WIDTH/2;
				yint[j]=(int)y[offset+i+j] + VIEWPORT_HEIGHT/2;
			}	
			
			if(drawFill && xpos==((i/4)%SQUARES_PER_AXIS) && ypos==((i/4)/SQUARES_PER_AXIS))
			{
				backbufferGraphics.setColor(Color.red);
				backbufferGraphics.fillPolygon(xint,yint,4);
			}
			
			{
				backbufferGraphics.setColor(Color.black);
				backbufferGraphics.drawPolygon(xint,yint,4);
			}			    				
        }  
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
    
    public boolean keyDown(Event evt, int key)
    {
    	int pos0=position[0];
    	int pos1=position[1];
    	int pos2=position[2];
    	
    	if(key==Event.UP)
    	{	
    		if(pos2==0)
    		{
    			if(pos1>=0)
    			{
    				position[0]++;
    			}
    			if(pos0>=0)
    			{
    				position[1]++;
    			}    			
    		}
    		else
    		{
    			position[2]--;
    		}
    	}
    	else if(key==Event.DOWN)
    	{
    		if(pos2==0 && (pos1>0 && pos0>0))
    		{
				if(pos1>=0)
				{
					position[0]--;
				}
				if(pos0>=0)
				{
					position[1]--;
				}
    		}
    		else
    		{    		
    			position[2]++;
    		}
    	}
    	else if(key==Event.LEFT)
    	{
    		if(pos1>0 && pos0>0)
    		{
    			position[0]++;
    			position[1]--;
    		}
    		else
    		{    		
    			if(position[1]==0)
    			{
    				position[0]++;
    			}
    			else
    			{
    				position[1]--;
    			}
    		}
    	}
    	else if(key==Event.RIGHT)
    	{
    		if(pos0>0 && pos1>0)
    		{
    			position[0]--;
    			position[1]++;
    		}
    		else    		
    		{
	    		if(pos0==0)
	    		{
	    			position[1]++;
	    		}
	    		else
	    		{
	    			position[0]--;
	    		}
    		}
    	}    
    	
    	position[0]=Clamp(position[0],0,SQUARES_PER_AXIS-1);
    	position[1]=Clamp(position[1],0,SQUARES_PER_AXIS-1);
    	position[2]=Clamp(position[2],0,SQUARES_PER_AXIS-1);
    	return true;
    }
    
    int Clamp(int value,int min,int max)
    {
    	if(value<min)
    	{
    		value=min;
    	}
    	else if(value>max)
    	{
    		value=max;
    	}
    	
    	return value;
    }
}
