/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package brownianislands;

import javax.swing.JFrame;

/**
 *
 * @author Dasty
 */
public class BrownianIslands extends JFrame
{
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    
    public BrownianIslands()
    {
        this.setSize(WIDTH, HEIGHT);
        this.add(new DrawPane(WIDTH, HEIGHT));
        this.setVisible(true);
    }
    
    public static void main(String[] args) 
    {
        BrownianIslands run = new BrownianIslands();
        run.setDefaultCloseOperation(EXIT_ON_CLOSE);
        

        
    }
}
