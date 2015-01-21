/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package brownianislands;

/* Stuff about thread groups explained by dbell@shvn.com (Doug Bell)
*  in message <dbell-1811961928460001@news2.cts.com> in
*  comp.lang.java.programmer
*
*  The point is that the animatorThread must be in the same thread
*  group as the applet, i. e. as "this" when the applet is doing the
*  calling.  Here the animatorThread is created in start() called
*  from mouseDown() which is called from (??) not, apparently from
*  the applet, but from elsewhere in the browser code.
*/

import java.awt.*;
import java.applet.Applet;

public class StochasticProcessApplet extends Applet implements Runnable {

	Thread animatorThread = null;
	ThreadGroup appletGroup;
	StochasticProcessDisplay display;

	Dimension offDimension;
	Image offImage;
	Graphics offGraphics;

	double zoom = 1.0;	// default value
	int delay = 100;	// default value
	boolean frozen = true;
	double center;

	protected double getNonNegativeDoubleParameter(String name,
		double defaultValue) {

		double tmp = defaultValue;
		String str = getParameter(name);

		try {
			if (str != null)
				tmp = Double.valueOf(str).doubleValue();
		} catch (NumberFormatException e) {}

		if (tmp >= 0.0)
			return tmp;
		else
			return 0.0;
	}

	public void init() {
		appletGroup = Thread.currentThread().getThreadGroup();
		zoom = getNonNegativeDoubleParameter("zoom", zoom);
	}

	public void start() {
		if (! frozen) {
			// Start animating thread
			if (animatorThread == null) {
				animatorThread = new Thread(appletGroup, this);
				animatorThread.start();
			}
		}
	}


	public void stop() {

		// Stop animating thread
		frozen = true;
		animatorThread = null;

		// Get rid of objects necessary for double buffering
		offGraphics = null;
		offImage = null;
	}

	public boolean mouseDown(Event e, int x, int y) {

		// if no zoom, ignore
		if (zoom <= 1.0)
			return true;

		// if already zooming, stop
		if (! frozen) {
			stop();
			return true;
		}

		// otherwise, start zooming

		Dimension d = size();

		// set center of zooming
		center = (double) x / (d.width - 1);

		frozen = false;
		start();

		return true;
	}

	public void run() {

		// just to be nice, lower thread priority
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

		// Animation loop
		 while (Thread.currentThread() == animatorThread) {

			long startTime = System.currentTimeMillis();

			// zoom
			display.zoom(center, zoom);
			repaint();

			try {
				long waitTime = startTime + delay
					- System.currentTimeMillis();
				if (waitTime > 0)
					Thread.sleep(waitTime);
				else
					Thread.yield();
			} catch (InterruptedException e) {
				break;
			}
		}

	}

	public void paint(Graphics g) {
		update(g);
	}

	public void update(Graphics g) {

		Dimension d = size();

		// Create offscreen graphics context
		if ((offGraphics == null)
			|| (d.width != offDimension.width)
			|| (d.height != offDimension.height)) {
			offDimension = d;
			offImage = createImage(d.width, d.height);
			offGraphics = offImage.getGraphics();
		}

		// Erase the previous image
		offGraphics.setColor(getBackground());
		offGraphics.fillRect(0, 0, d.width, d.height);
		offGraphics.setColor(getForeground());

		// Graph the stochastic process
		display.display(offGraphics, d);

		// Paint the image onto the screen
		g.drawImage(offImage, 0, 0, this);
	}

}


