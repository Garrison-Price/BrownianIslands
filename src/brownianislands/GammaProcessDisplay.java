package brownianislands;

public class GammaProcessDisplay extends StochasticProcessDisplay {

	GammaProcessDisplay(double t) {
		super(t);
	}

	double increment(double t) {
		return rng.nextGamma(t);
	}

	double interpolant(double s, double t, double X0, double Xt) {

		/* (Xs - X0) / (Xt - X0) is Beta(s, t - s)
		*  and V / (V + W) is Beta(s, t - s)
		*  if V ~ Gam(s) and W ~ Gam(t - s)
		*  hence Xs = X0 + (Xt - X0) V / (V + W)
		*/

		double V = rng.nextGamma(s);
		double W = rng.nextGamma(t - s);

		// care required here to assure X0 < z < Xt
		// even with rounding error in floating point arithmetic
		double z;
		if (W > V)
			z = X0 + (Xt - X0) * V / (V + W);
		else
			z = Xt - (Xt - X0) * W / (V + W);

		return z;
	}
}

