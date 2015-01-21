package brownianislands;

public class BrownianMotionDisplay extends StochasticProcessDisplay {

	BrownianMotionDisplay(double t) {
		super(t);
	}

	double increment(double t) {
		return t * rng.nextGaussian();
	}

	double interpolant(double s, double t, double X0, double Xt) {
		double mean = (X0 + Xt) / 2.0;
		double variance = s * (t - s) / t;
		return mean + rng.nextGaussian() * Math.sqrt(variance);
	}
}