package ecc_classes;

public class Point {

	private boolean pointAtInfinity;
	private boolean mod;
	private double x;
	private double y;

	public Point(final double x, final double y, final boolean pointAtInfinity) {
		this.x = x;
		this.y = y;
		this.pointAtInfinity = pointAtInfinity;
		this.mod = false;
	}

	public boolean isPointAtInfinity() {
		return this.pointAtInfinity;
	}

	public void setPointAtInfinity(final boolean pointAtInfinity) {
		this.pointAtInfinity = pointAtInfinity;
	}

	public double getx() {
		return this.x;
	}

	public void setx(final double x) {
		this.x = x;
	}

	public double gety() {
		return this.y;
	}

	public void sety(final double y) {
		this.y = y;
	}
	
	public boolean getMod() {
		return this.mod;
	}

	public void setMod(final boolean mod) {
		this.mod = mod;
	}

	@Override
	public String toString() {
		if(this.pointAtInfinity)
			return "Point is point at infinity.\n";
		if (this.mod)
			return "Point is (x, y) = (" + (int)this.x + ", " + (int)this.y + ")\n";
		else
			return "Point is (x, y) = (" + this.x + ", " + this.y + ")\n";
	} // end toString
	
	@Override
	public boolean equals(final Object obj) // called using P1.equals(P2)
	{
		// if object not of class Point
        if(obj == null || obj.getClass()!= this.getClass()) 
            return false;
        if(obj == this)
        	return true;
        Point P2 = (Point)obj; // recast as a Point object
		if(this.getx() == P2.getx() && this.gety() == P2.gety())
			return true;
		else
			return false;
	} // end equals method
	
	public boolean isNegativeOf(final Object obj)
	{
		// if object not of class Point
        if(obj == null || obj.getClass()!= this.getClass()) 
            return false;
        Point P2 = (Point)obj; // recast as a Point object
		if(this.getx() == P2.getx() && this.gety() == -P2.gety() && this.pointAtInfinity == P2.pointAtInfinity)
			return true; // does not care about modulus
		else
			return false;
	} // end isP2NegativeP1

} // end class



















