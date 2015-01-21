/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package brownianislands;

/* Almost-exact inversion for Gamma(a), p. 141 in Devroye
*
*  N. B. !!!!!!!!  Devroye has the wrong sign on the r. h. s. in both tests!
* 
*  I think that's 2 buggy algorithms out of 3 I've implemented from this
*  book.  Great book, but you must rederive each algorithm.
*
*  Main algorithm only works for alpha > 1/3.  Use Stuart's theorem
*  p. 420 in Devroye for alpha < 1/2, i. e. if
*
*    X is Gamma(a + 1)
*    U is Uniform(0,1)
*
*  then
*
*    Y = X * U^{1/a} 
*
*  is Gamma(a)
*
*/

import java.util.Random;

public class MyRandom extends Random {

	MyRandom() {
		super();
	}

	MyRandom(long seed) {
		super(seed);
	}

	public double nextGamma(double a) {

		double oneThird = 1.0 / 3.0;

		if (a <= 0.0)
			return 0.0;

		if (a == 1.0)
			return - Math.log(nextDouble());

		if (a < 0.5)
			return nextGamma(a + 1.0) * Math.pow(nextDouble(),
				1.0 / a);

		double tmp = 3.0 * a;
		double tmp2 = tmp - 1.0;
		double z0 = Math.pow(tmp2 / tmp, oneThird);
		double sigma = Math.sqrt(1.0 / (9.0 * a * z0));
		double z1 = a - oneThird;

		for ( ; ; ) {

			double N = nextGaussian();
			double U = nextDouble();
			while (U == 0.0)
				U = nextDouble();

			double Z = z0 + sigma * N;
			double X = a * Z * Z * Z;

			double S = Math.log(U) - N * N / 2.0 + (X - z1);
			double W = sigma * N / z0;

			if ((Z >= 0.0) &&
				((S <= - W * (1.0 + W * (0.5 + W / 3.0))) ||
				(S <= (tmp2 * Math.log(Z / z0)))))
				return X;
		}

	}
}


