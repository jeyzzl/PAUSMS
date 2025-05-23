/* Pie chart Applet */
/* This program is in the public domain. 
 * "Share and enjoy."  And give me credit.
 * -- Tom Van Vleck
 *
 * THVV 06/18/97 Initial coding
 * THVV 09/08/02 Updated with suggestions from Robert Beck
 * THVV 04/08/03 Added feature to set the color of wedges
 */

import java.awt.*;
import java.applet.*;
import java.util.*;
import java.lang.Math;

// ================================================================
// A struct class to hold data for one wedge
class PieItem {
    public double frac;		// each one has a number
    public String label;	// and a label
    public Color color;		//  and an optional color

    PieItem (String s) { // constructor
	StringTokenizer t = new StringTokenizer(s, ",");
	frac = Double.valueOf(t.nextToken()).doubleValue();
	label = t.nextToken();
	color = null;
	if (t.hasMoreElements()) {
	    try {
		color = Color.decode(t.nextToken());
	    } catch (Exception e) { }     
	}
    } // constructor

} // PieItem

//The view of the pie.
class PieView extends Canvas {
    PieItem[] wedges;		// The data for the pie
    double total = 0.0;		// Total of all wedges

    Color wedgeColor[] = new Color[10];
    Color textColor = Color.black;
    Color otherColor = Color.gray;
    Color shadowColor = Color.gray;
    Color bgColor = Color.white;

    int pieViewSize;	        // size of square to incise pie into
    static final int pieBorderWidth = 10; // pixels from circle edge to side
    int pieDiameter;	        // derived from the view size
    int pieRadius;		// ..
    int pieCenterPos;		// ..

    int minLabeledWedge = 3;	// Min size of a wedge for labeling, degrees.
    int shadowOffset = 3;	// offset of shadow in pixels
  
    public PieView(int asize, PieItem[] alis, Color bc) { // constructor
	this.pieViewSize = asize; // copy args
	this.wedges = alis;
	this.bgColor = bc;

	pieDiameter = pieViewSize - 2*pieBorderWidth;
	pieRadius = pieDiameter/2;
	pieCenterPos = pieBorderWidth + pieRadius;
	this.setFont(new Font("Helvetica", Font.BOLD, 14));
	this.setBackground(bgColor);
	//this.setBackground(new Color(255, 255, 183));

	for (int i = 0; i < wedges.length; i++) {
	    total += wedges[i].frac;
	}

	wedgeColor[0] = new Color(156, 154, 255);
	wedgeColor[1] = new Color(156, 48, 99);
	wedgeColor[2] = new Color(255, 255, 206);
	wedgeColor[3] = new Color(206, 255, 255);
	wedgeColor[4] = new Color(99,    0, 99);
	wedgeColor[5] = new Color(255, 130, 132);
	wedgeColor[6] = new Color(0,	 101, 206);
	wedgeColor[7] = new Color(206, 207, 255);
	wedgeColor[8] = new Color(0,     0, 132);
	wedgeColor[9] = new Color(102, 153, 255);
    } // constructor

    public void paint(Graphics g) {
	int startDeg = 0;
	int arcDeg;
	int arcDegCount = 0;
	int x, y;
	double angleRad;

	g.setColor(shadowColor); // shadow, down and to the right 3 pixels
	g.fillOval(pieBorderWidth+shadowOffset, pieBorderWidth+shadowOffset, pieDiameter, pieDiameter);
	g.setColor(otherColor);	// "other" is gray
	g.fillOval(pieBorderWidth, pieBorderWidth, pieDiameter, pieDiameter);
    
	int wci = 0;
	int i;
	for (i = 0; i<this.wedges.length; i++) { // draw wedges
	    arcDeg = (int)Math.round(((this.wedges[i].frac / total) * 360));
	    arcDegCount += arcDeg;
	    if ((i+1)==this.wedges.length & arcDegCount != 360) {
		arcDeg += (360 - arcDegCount); // Avoid a gray wedge due to roundoff.
	    }
	    if (this.wedges[i].color != null) {
		g.setColor(this.wedges[i].color);
	    } else {
		g.setColor(wedgeColor[wci++]);
	    }
	    g.fillArc(pieBorderWidth, pieBorderWidth, pieDiameter, pieDiameter, startDeg, arcDeg);
	    if (wci >= wedgeColor.length) {
		wci = 1; // Rotate colors.  Don't reuse 0 in case first and last are same.
	    }
	    startDeg += arcDeg;
	} // draw wedges
	startDeg = 0; // Do labels so they go on top of the wedges.
	for (i = 0; i<this.wedges.length; i++) {
	    arcDeg = (int)Math.round(((this.wedges[i].frac / total) * 360));
	    if (arcDeg > minLabeledWedge) { // Don't label small wedges.
		angleRad = (float) (startDeg+ (arcDeg/2))*java.lang.Math.PI / 180.0;
		x = pieCenterPos + (int)((pieRadius/1.3)*java.lang.Math.cos(angleRad));
		y = pieCenterPos - (int)((pieRadius/1.3)*java.lang.Math.sin(angleRad))
		    + 5; // 5 is about half the height of the text
		g.setColor(new Color(190,190,190));
		g.drawString(this.wedges[i].label, x+1, y+1);
		g.setColor(textColor);
		g.drawString(this.wedges[i].label, x, y);
	    } // don't label small wedges
	    startDeg += arcDeg;
	} // for
    } // paint()

    public Dimension preferredSize () {
	return new Dimension (pieViewSize, pieViewSize);
    } // preferredSize
  
} // PieView

// ================================================================
// The Pie chart applet
public class Pie extends Applet {
    private PieView the_pie = null;

    public Pie() { // constructor
	// Nothing happens here, can't get args yet.
    } // constructor

    public void init () {
	String stemp;
	double dtemp;
	Color ctemp;
	int i;
	Color bgcolor = Color.white;

	// Read the applet arguments
	stemp = this.getParameter("title");
	String chartTitle = (stemp == null) ? "" : stemp;
	stemp = this.getParameter("subtitle");
	String chartSubTitle = (stemp == null) ? "" : stemp;
	int nargs = 0;
	while (this.getParameter("arg"+nargs) != null) {
	    nargs++; // just count the arguments
	} // while
	PieItem[] itemArray = new PieItem[nargs]; // allocate storage
	for (i=0; i < nargs; i++) {
	    itemArray[i] = new PieItem(getParameter("arg" + i)); // parse argument
	} // for
	stemp = this.getParameter("bg");
	if (stemp != null) {
	    try {
		bgcolor = Color.decode(stemp);
	    } catch (Exception e) { }     
	}

	int d = (i+1)/2; // Shell sort.
	do {
	    for (i=0; i < nargs-d; i++) {
		if (itemArray[i].frac < itemArray[i+d].frac) {
		    dtemp = itemArray[i].frac; // swap
		    itemArray[i].frac = itemArray[i+d].frac;
		    itemArray[i+d].frac = dtemp;
		    stemp = itemArray[i].label;
		    itemArray[i].label = itemArray[i+d].label;
		    itemArray[i+d].label = stemp;
		    ctemp = itemArray[i].color;
		    itemArray[i].color = itemArray[i+d].color;
		    itemArray[i+d].color = ctemp;
		}
	    } // for
	    d -= 1;
	} while (d > 0);
	int h = this.size().height;
	int w = this.size().width;
	the_pie = new PieView(h-50, itemArray, bgcolor); // shd be min(h-50, w)?
	this.setLayout(new BorderLayout(0, 0));
	this.setBackground(bgcolor);
	this.add("Center", the_pie);
	this.add("North", new Label(chartTitle));
	this.add("South", new Label(chartSubTitle));
    } // init

    // Boiler plate for HotJava
    public String getAppletInfo () {
	return "Pie 2003-04-08 THVV v3";
    } // getAppletInfo
    public String [][] getParameterInfo () {
	String [][] info = {
	};
	return info;
    } // getParameterInfo

    // Main program for testing only.
    public static void main(String args[]) {
	Frame f = new Frame("Pie");
	Pie p = new Pie();
	p.init();
	p.start();
	f.add("Center", p);
	f.resize(512, 512);
	f.show();
    } // main

} // Pie