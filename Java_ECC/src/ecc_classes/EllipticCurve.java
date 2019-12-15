package ecc_classes;

import ecc_utils.ECC_Utils;

public class EllipticCurve {
	private double A;
	private double B;
	private int p;
	private boolean mod;
	private int order;

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
			return false;
		if(this.mod){
			Integer y2 = Integer.valueOf((int) Math.pow(point.gety(), 2)) % this.p;
			Integer rightSide = Integer.valueOf((int) (Math.pow(point.getx(), 3) + this.A * point.getx() + this.B)) % this.p;
			return y2.equals(rightSide);
		} else {
			Double y2 = Math.pow(point.gety(), 2);
			Double rightSide = Math.pow(point.getx(), 3) + this.A * point.getx() + this.B;
			return y2.equals(rightSide);
		}
	} // end hasPoint

	public Point[] getnPoints(final int n, final EllipticCurve myCurve) { // TODO: check this
		Point[] pointArray = new Point[n];
		double y;
		int i = 0;
		int x = 0;
		Point temp;
		if (!this.mod) {
			while (i < pointArray.length) {
				y = Math.sqrt(Math.pow(x, 3) + this.A * x + this.B);
				temp = new Point(x, y, false);
				x++;
				if (myCurve.hasPoint(temp)) {
					pointArray[i] = temp;
					i++;
				}
			}
		} else { // TODO: check this
			while (i < pointArray.length) {
				y = (ECC_Utils.findSqrtModP((int) (Math.pow(x, 3) + this.A * x + this.B), this.p)) % this.p;
				temp = new Point(x, y, false);
				x++;
				if (myCurve.hasPoint(temp)) {
					pointArray[i] = temp;
					i++;
				}
			}
		}
		return pointArray;
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
	
	public int getOrder() {
		return this.order;
	}
	public void setOrder(final int order) {
		this.order = order;
	}

} // end class
