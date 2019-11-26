package ecc_utils;

import ecc_classes.*;

public class ECC_Utils {
	public static Point calculateNP(final EllipticCurve myCurve, final Point P1, final int n) {
		if (myCurve == null || P1 == null)
			throw new IllegalArgumentException("Curve or P1 is null");
		if (P1.isPointAtInfinity())
			return P1;
		Point nP = P1;
		for (int x = 1; x < n; x++)
			nP = addPoints(myCurve, nP, P1);
		return nP;
	} // end calculateP2

	// TODO: add in if one point is point at infinity
	public static Point addPoints(final EllipticCurve myCurve, final Point P1, final Point P2) {
		if (myCurve == null || P1 == null || P2 == null)
			throw new IllegalArgumentException("Curve or P1 or P2 is null");
		double x1 = P1.getx();
		double y1 = P1.gety();
		double x2 = P2.getx();
		double y2 = P2.gety();
		double slope, m2, x3, y3;
		if (!myCurve.isMod()) // no modulus
		{

			if (P1.equals(P2)) {
				slope = (3 * Math.pow(x1, 2) + myCurve.getA()) / (2 * y1);
				m2 = Math.pow(slope, 2);
				x3 = m2 - 2 * x1;
				y3 = -(slope * (x3 - x1) + y1);
				return new Point(x3, y3, false);
			}

			else if (P1.isNegativeOf(P2)) // P1 = -P2
			{
				return new Point(0, 0, true); // say that result is point at infinity
			} else // otherwise do normal point addition
			{
				slope = (y1 - y2) / (x1 - x2);
				m2 = Math.pow(slope, 2);
				x3 = m2 - x1 - x2;
				y3 = -(slope * (x3 - x1) + y1);
				return new Point(x3, y3, false);
			}
		} else // yes modulus
		{
			int p = myCurve.getP();

			if (x1 == x2 && y1 == y2) // P1 = P2
			{
				slope = (((3 * x1 * x1) + myCurve.getA()) * findInverseModP(2 * (int)y1, p)) % p;
				if(slope < 0) // ensure values are positive
					slope += p;
				m2 = (slope * slope) % p;
				x3 = (m2 - 2 * x1) % p;
				y3 = -(slope * (x3 - x1) + y1) % p;
				if(x3<0) // ensure values are positive
					x3 += p;
				else if(y3<0)
					y3 += p;
				return new Point(x3, y3, false);
			}

			else if (P1.isNegativeOf(P2)) // P1 = -P2
			{
				return new Point(0, 0, true); // point at infinity
			} else // otherwise do normal point addition
			{
				slope = makePosModP((int)(y1 - y2),p) * findInverseModP(makePosModP((int)(x1-x2),p), p) % p;
				slope = makePosModP((int)slope, p);
				m2 = (int)(slope * slope) % p;
				x3 = makePosModP((int)(m2 - x1 - x2),p) % p;
				y3 = makePosModP(-(int)(slope * (x3 - x1) + y1),p) % p;
				System.out.println("x3 is: " + x3 + " y3 is: " + y3);
				if(x3 < 0) // ensure values are positive
					x3 += p;
				if(y3 < 0)
					y3 += p;
				return new Point(x3, y3, false);
			}
		}
	} // end addPoints

	public static int findInverseModP(final int num, final int p) {
		int a = num % p;
		for (int x = 1; x < p; x++)
			if ((a * x) % p == 1)
				return x;
		return 1;
	}
	// This code is contributed by Nikita Tiwari.
	// Found at: https://www.geeksforgeeks.org/multiplicative-inverse-under-modulo-m/
	
	public static int makePosModP(final int num, final int p) {
		int a = num % p;
		if(a < 0)
			return a += p;
		else
			return a;
	} //end makePosModP
	
	public static int findCurveOrder(final EllipticCurve myCurve) {
		int i = 0;
		for(int x = 0; x < myCurve.getP(); x++)
			if(Math.sqrt(Math.pow(x, 3)-myCurve.getA()*x+myCurve.getB()) % 1 == 0) // y^2 = sqrt(x^3 + Ax + B)
				i ++;
		return 2*i; // count points y>0 and y<0
	} // end findCurveOrder

	public static int findPointOrder(final EllipticCurve myCurve, final Point pt) {
		int i = 0;
		Point Pout = pt;
		while(!Pout.isPointAtInfinity() && i < myCurve.getP()) {
			Pout = addPoints(myCurve, Pout, pt);
			i++;
		}
		return i;
	} // end findPointOrder
	
} // end class




















