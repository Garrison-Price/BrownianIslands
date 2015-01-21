/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package brownianislands;

/*
*  Simulates and displays a stochastic process with independent increments
*/

import java.awt.*;

public abstract class StochasticProcessDisplay extends StochasticProcess {

	/* min and max values of carrier in display window
	*  note initially the same as min and max values for process
	*  (tMin and tMax) defined in superclass, but after zooming
	*  process will typically extend outside display window.
	*/
	protected double sMin, sMax;

	StochasticProcessDisplay(double t) {
		super(t);
		sMin = 0.0;
		sMax = t;
	}

	public void display(Graphics g, Dimension d) {

		// if x isn't long enough, fill in
		while (x.length * (sMax - sMin) / (tMax - tMin) < d.width)
			densify(sMin, sMax);

		// find max and min of x in window
		double xmax = Double.NEGATIVE_INFINITY;
		double xmin = Double.POSITIVE_INFINITY;
		int imax = -1;
		int imin = x.length;
		for (int i=0; i<x.length; i++) {
			double ifrac = (double) i / (x.length - 1);
			double t = tMin + ifrac * (tMax - tMin);
			if (sMin <= t && t <= sMax) {
				if (x[i] > xmax) xmax = x[i];
				if (x[i] < xmin) xmin = x[i];
				if (i > imax) imax = i;
				if (i < imin) imin = i;
			}
		}

		if (xmax == xmin) {
			// flatline
			g.drawLine(0, d.height / 2, d.width - 1, d.height / 2);
			return;
		}

		// Plot to fill plot region.  Take xmax and xmin to be
		// centers of top and bottom rows (note top is row zero).
		// Take sMax and sMin to be centers of right and left rows.
		// Interpolate linearly.

		int x1 = scale(imin, sMin, sMax, d.width - 1);
		int y1 = scale(x[imin], xmax, xmin, d.height - 1);

		for (int i=imin+1; i<=imax; i++) {

			int x2 = scale(i, sMin, sMax, d.width - 1);
			int y2 = scale(x[i], xmax, xmin, d.height - 1);

			if (i > imin)
				g.drawLine(x1, y1, x2, y2);
			
			x1 = x2;
			y1 = y2;
		}
	}

	protected int scale(double x, double xmin, double xmax, int height) {
		double xScaled = (x - xmin) / (xmax - xmin);
		return (int) Math.round(xScaled * height);
	}

	protected int scale(int i, double xmin, double xmax, int height) {
		double ifrac = (double) i / (x.length - 1);
		double t = tMin + ifrac * (tMax - tMin);
		return scale(t, xmin, xmax, height);
	}

	public void zoom(double center, double zoom) {

		if (zoom <= 1.0) return;
		if (center <= 0.0) center = 0.0;
		if (center >= 1.0) center = 1.0;

		// convert from 0 < center < 1 to actual coordinates
		center = sMin + center * (sMax - sMin);

		sMax = center + (sMax - center) / zoom;
		sMin = center - (center - sMin) / zoom;

		return;
	}

}

