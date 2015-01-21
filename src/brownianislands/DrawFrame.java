/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package brownianislands;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Dasty
 */
class DrawPane extends JPanel implements Runnable
{

    private double x,y,oldx,oldy;
    private Random r1,r2;
    private Thread thread;
    BrownianBridge bb;
    BrownianBridge bb2;
    
    public DrawPane(int w,int h) 
    {
        this.setSize(w, h);
        r1 = new Random();
        r2 = new Random();
        oldy=oldx=y=x=w/2;
        bb = new BrownianBridge();
        bb2 = new BrownianBridge();
        thread = new Thread(this);
        thread.start();
//        for(int i = 0; i < bb.pathRes.length;i++)
//        {
//            System.out.println(bb.pathRes[i]+","+bb2.pathRes[i]);
//        }
        this.setVisible(true);
    }
    
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.red);
        //g.drawLine((int)oldx, (int)oldy, (int)x, (int)y);
        for(int i = 1; i < bb.pathRes.length;i++)
        {
            g.drawLine(250+(int)bb.pathRes[i-1],250+(int)bb2.pathRes[i-1],250+(int)bb.pathRes[i],250+(int)bb2.pathRes[i]);
        }
    }
    
    public void update()
    { 
        oldx=x;
        oldy=y;
        y+=(r1.nextGaussian()) * 1/.7;
        x+=(r2.nextGaussian()) * 1/.7;
        repaint();
    }

    @Override
    public void run() 
    {
        while(true)
        {
            try
            {
                Thread.sleep(28);
                update();
            }
            catch(Exception e)
            {

            }
        }
    }
    
}
