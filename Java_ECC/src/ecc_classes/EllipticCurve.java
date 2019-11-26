package ecc_classes;

public class EllipticCurve {
	private double A;
	private double B;
	private int p;
	private boolean mod;

	public EllipticCurve(final double A, final double B, final int p, final boolean mod) {
		this.A = A;
		this.B = B;
		this.p = p;
		this.mod = mod;
	} // explicit value constructor

	@Override
	public String toString() {
		if (this.mod)
			return "Your Elliptic Curve is y^2 = x^3 +" + (int) A + "x + " + (int) B + "\n";
		else
			return "Your Elliptic Curve is y^2 = x^3 +" + A + "x + " + B + "\n";
	} // end toString

	public boolean hasSingularity() {
		if ((4 * Math.pow(this.getA(), 3) + 27 * Math.pow(this.getB(), 2)) % this.getP() == 0)
			return true;
		else
			return false;
	} // end hasSingularity

	public boolean hasPoint(final Point point) { // TODO: check this
		if (point == null)
			throw new IllegalArgumentException("Point is null.");
		Double y2 = Math.pow(point.gety(), 2);
		Double rightSide = Math.pow(point.getx(), 3) + this.A * point.getx() + this.B;
		return y2.equals(rightSide);
	} // end hasPoint
	
	public Point [] getPoints() { // TODO: finish this
		if(!this.mod)
			return null;
		for(int x = 0; x < p; x++) {
			
		}
			
		return null;
	} // end getPoints

	public double getA() {
		return this.A;
	}

	public void setA(final double a) {
		this.A = a;
	}

	public double getB() {
		return this.B;
	}

	public void setB(final double b) {
		this.B = b;
	}

	public int getP() {
		return this.p;
	}

	public void setP(final int p) {
		this.p = p;
	}

	public boolean isMod() {
		return this.mod;
	}

	public void setMod(final boolean mod) {
		this.mod = mod;
	}

} // end class
