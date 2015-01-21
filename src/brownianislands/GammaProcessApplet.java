package brownianislands;

import java.awt.*;
import java.applet.Applet;

public class GammaProcessApplet extends StochasticProcessApplet {

	public String getAppletInfo() {
		String info = "GammaProcessApplet by Charles Geyer\n" +
			"(charlie@stat.umn.edu) public domain";
		return info;
	}

	private double t = 1.0;

	public void init() {
		t = getNonNegativeDoubleParameter("length", t);
		display = new GammaProcessDisplay(t);
		super.init();
	}
}

