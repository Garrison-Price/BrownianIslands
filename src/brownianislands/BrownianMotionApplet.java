package brownianislands;

import java.awt.*;
import java.applet.Applet;

public class BrownianMotionApplet extends StochasticProcessApplet {

	public String getAppletInfo() {
		String info = "BrownianMotionApplet by Charles Geyer\n" +
			"(charlie@stat.umn.edu) public domain";
		return info;
	}

	public void init() {
		display = new BrownianMotionDisplay(1.0);
		super.init();
	}

}