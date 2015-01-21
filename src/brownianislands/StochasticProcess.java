/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package brownianislands;

/*
*  Simulates a stochastic process on (s,t) with independent increments
*/

// import java.util.Random;

public abstract class StochasticProcess {

	protected double[] x;
	protected double tMin, tMax;
	protected MyRandom rng = new MyRandom();

	abstract double increment(double t);
		// returns random value of X(t) given X(0) = 0,
		// i. e. the increment for length t

	abstract double interpolant(double s, double t, double X0, double Xt);
		// returns random value of X(s) given X(0) and X(t), 0 < s < t

	StochasticProcess(double tMin, double tMax, double XtMin) {
		x = new double[2];
		this.tMin = tMin;
		this.tMax = tMax;
		x[0] = XtMin;
		x[1] = increment(tMax - tMin);
	}

	StochasticProcess(double s, double t) {
		this(s, t, 0.0);
	}

	StochasticProcess(double t) {
		this(0.0, t, 0.0);
	}

	public synchronized void densify() {

		// increase points at which process has been simulated
		// from n to 2 n - 1

		// new length
		int n = 2 * x.length - 1;
		double[] newx = new double[n];

		// copy x into every other entry of newx
		for (int i=0; i<x.length; i++)
			newx[2*i] = x[i];

		// fill in intermediate entries
		for (int i=1; i<newx.length; i+=2) {
			double s = (tMax - tMin) / (newx.length - 1);
			double t = (tMax - tMin) / (x.length - 1);
			newx[i] = interpolant(s, t, newx[i-1], newx[i+1]);
		}

		x = newx;
	}

	public synchronized void densify(double sMin, double sMax) {

		// trim x so that it covers the interval (sMin, sMax)
		// then densify

		double fMin = (sMin - tMin) / (tMax - tMin);
		double fMax = (sMax - tMin) / (tMax - tMin);
		int iMin = (int) Math.floor(fMin * (x.length - 1));
		int iMax = (int) Math.ceil(fMax * (x.length - 1));

		cut(iMin, iMax);
		densify();
	}

	public synchronized void cut(int i, int j) {

		// trim x to x[k], i <= k <= j

		// new length
		int n = j - i + 1;
		double[] newx = new double[n];

		// copy x into newx
		System.arraycopy(x, i, newx, 0, newx.length);

		double tmp = tMin + i * (tMax - tMin) / (x.length - 1);
		tMax = tMin + j * (tMax - tMin) / (x.length - 1);
		tMin = tmp;

		x = newx;
	}

}


