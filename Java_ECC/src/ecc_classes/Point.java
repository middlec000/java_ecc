package ecc_classes;

import ecc_utils.ECC_Utils;

public class Point {

	private boolean pointAtInfinity;
	private boolean mod;
	private double x;
	private double y;

	// point no mod - doubles
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
		if (this.pointAtInfinity)
			return "Point is point at infinity.\n";
		if (this.mod)
			return "Point is (x, y) = (" + (int) this.x + ", " + (int) this.y + ")\n";
		else
			return "Point is (x, y) = (" + this.x + ", " + this.y + ")\n";
	} // end toString

	@Override
	public boolean equals(final Object obj) // called using P1.equals(P2)
	{
		// if object not of class Point
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		if (obj == this)
			return true;
		Point P2 = (Point) obj; // recast as a Point object
		if (this.getx() == P2.getx() && this.gety() == P2.gety())
			return true;
		else
			return false;
	} // end equals method

	public boolean isNegativeOf(final Object obj) {
		// if object not of class Point
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		Point P2 = (Point) obj; // recast as a Point object
		if (this.getx() == P2.getx() && this.gety() == -P2.gety() && this.pointAtInfinity == P2.pointAtInfinity)
			return true; // does not care about modulus
		else
			return false;
	} // end isP2NegativeP1

	public Point plus(final Point P2, final EllipticCurve myCurve) {
		if (myCurve == null || P2 == null)
			throw new IllegalArgumentException("Curve or P2 is null");
		double slope, m2, x3, y3;

		// cases common to both mod and no mod:
		if (this.isPointAtInfinity())
			return P2;
		else if (P2.isPointAtInfinity())
			return this;
		else if (this.isNegativeOf(P2)) // P1 = -P2
		{
			return new Point(0, 0, true); // say that result is point at infinity
		}

		// no modulus
		if (!myCurve.isMod()) {
			if (this.equals(P2)) { // P1 = P2
				slope = (3 * Math.pow(this.x, 2) + myCurve.getA()) / (2 * this.y);
				m2 = Math.pow(slope, 2);
				x3 = m2 - 2 * this.x;
				y3 = -(slope * (x3 - this.x) + this.y);
				return new Point(x3, y3, false);
			} else // otherwise do normal point addition
			{
				slope = (this.y - P2.y) / (this.x - P2.x);
				m2 = Math.pow(slope, 2);
				x3 = m2 - this.x - P2.x;
				y3 = -(slope * (x3 - this.x) + this.y);
				return new Point(x3, y3, false);
			}
		}
		// yes modulus
		else {
			int p = myCurve.getP();

			if (this.equals(P2)) // P1 = P2
			{
				slope = (((3 * this.x * this.x) + myCurve.getA()) * ECC_Utils.findInverseModP(2 * (int) this.y, p)) % p;
				if (slope < 0) // ensure values are positive
					slope += p;
				m2 = (slope * slope) % p;
				x3 = (m2 - 2 * this.x) % p;
				y3 = -(slope * (x3 - this.x) + this.y) % p;
				if (x3 < 0) // ensure values are positive
					x3 += p;
				else if (y3 < 0)
					y3 += p;
				return new Point(x3, y3, false);
			} else // otherwise do normal point addition
			{
				slope = ECC_Utils.makePosModP((int) (this.y - P2.y), p)
						* ECC_Utils.findInverseModP(ECC_Utils.makePosModP((int) (this.x - P2.x), p), p) % p;
				slope = ECC_Utils.makePosModP((int) slope, p);
				m2 = (int) (slope * slope) % p;
				x3 = ECC_Utils.makePosModP((int) (m2 - this.x - P2.x), p) % p;
				y3 = ECC_Utils.makePosModP(-(int) (slope * (x3 - this.x) + this.y), p) % p;
				if (x3 < 0) // ensure values are positive
					x3 += p;
				if (y3 < 0)
					y3 += p;
				return new Point(x3, y3, false);
			}
		}
	} // end addPoints
	
	public Point reduceModp(final int mod) {
		return new Point(((int) this.x) % mod, ((int) this.y) % mod, false);
	} // end reduceModp
} // end class











