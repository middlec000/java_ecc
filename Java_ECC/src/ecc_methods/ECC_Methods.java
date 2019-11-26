package ecc_methods;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import ecc_utils.*;
import ecc_classes.*;

public class ECC_Methods {

	public static int menu​(final Scanner kb) {
		if (kb == null)
			throw new IllegalArgumentException("kb is null");
		System.out.print("Curve is of form y^2 = x^3 + Ax + B. \n" + "1) Input new A, B, modulus (optional) \n"
				+ "2) Enter P1 and n to find n*P1 \n" + "3) Enter P1 and P2 to find P3 = P1 + P2 \n"
				+ "4) Enter P1 and P2 to find n: n*P1=P2 \n" + "5) Enter a .txt file, output an encrypted version using Diffie-Hilman Key exchange + Ceasar Cipher. \n"
						+ "6) Decrypt file created in 5).\n" + "7) Quit \n");
		System.out.println();
		int choice = 0;
		do {
			System.out.print("Enter choice: ");
			choice = Integer.parseInt(kb.nextLine());
		} while (choice < 1 || choice > 7); // TODO: Change if add options
		System.out.println();
		return choice;
	} // end menu

	public static EllipticCurve chooseCurve(final Scanner kb) {
		if (kb == null)
			throw new IllegalArgumentException("Scanner is null");
		double A, B;
		int p;
		boolean mod = false;
		String ans;

		do {
			System.out.println("Do you want to use modulus? (y/n) If so all inputs will be converted to integers. ");
			ans = kb.nextLine().trim();
		} while (!ans.equalsIgnoreCase("n") && !ans.equalsIgnoreCase("no") && !ans.equalsIgnoreCase("y")
				&& !ans.equalsIgnoreCase("yes"));
		if (ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("yes"))
			mod = true;
		System.out.println("If the curve entered has a singularity (4*A^3 + 27*B^2 = 0) you will be reprompted.");
		if (mod) {
			do {
				System.out.println("Enter A: ");
				A = Double.parseDouble(kb.nextLine());
				System.out.println("Enter B: ");
				B = Double.parseDouble(kb.nextLine());
				System.out.println("Enter p (modulus): ");
				p = Integer.parseInt(kb.nextLine());
			} while (4 * Math.pow(A, 3) + 27 * Math.pow(B, 2) % p == 0);
			return new EllipticCurve(A, B, p, mod);
		} else { // no modulus
			do {
				System.out.println("Enter A: ");
				A = Double.parseDouble(kb.nextLine());
				System.out.println("Enter B: ");
				B = Double.parseDouble(kb.nextLine());
			} while (4 * Math.pow(A, 3) + 27 * Math.pow(B, 2) == 0);
			return new EllipticCurve(A, B, 0, mod);
		}
	} // end chooseCurve

	public static Point choosePoint(final Scanner kb, final boolean mod) {
		if (kb == null)
			throw new IllegalArgumentException("Scanner is null");
		double x1, y1;
		String ptAtInfty;
		do {
			System.out.println("Point at infinity? (y/n)");
			ptAtInfty = kb.nextLine().trim();
		} while (!ptAtInfty.equalsIgnoreCase("y") && !ptAtInfty.equalsIgnoreCase("yes")
				&& !ptAtInfty.equalsIgnoreCase("n") && !ptAtInfty.equalsIgnoreCase("no"));
		if (ptAtInfty.equalsIgnoreCase("y") || ptAtInfty.equalsIgnoreCase("yes"))
			return new Point(0, 0, true);
		System.out.println("P = (x, y)");
		System.out.println("Enter x: ");
		x1 = Double.parseDouble(kb.nextLine());
		System.out.println("Enter y: ");
		y1 = Double.parseDouble(kb.nextLine());
		return new Point(x1, y1, false);
	} // end choosePoint

	public static Point findNP(final EllipticCurve myCurve, final Point P1, final Scanner kb) {
		if (myCurve == null || P1 == null || kb == null)
			throw new IllegalArgumentException("Curve or P1 or Scanner is null");
		System.out.println("Enter n (must be integer) ");
		int n = Integer.parseInt(kb.nextLine());
		return ECC_Utils.calculateNP(myCurve, P1, n);
	} // end findNP

	
	public static void encryptMessage(final EllipticCurve myCurve, final Scanner kb) throws FileNotFoundException {
		if(kb == null)
			throw new IllegalArgumentException("Scanner is null");
		System.out.println("Enter base point that generates subgroup. \n P = (x, y) \nx: ");
		int x = Integer.parseInt(kb.nextLine());
		System.out.println("y: ");
		int y = Integer.parseInt(kb.nextLine());
		Point G = new Point(x,y,false);
		int pointOrder = ECC_Utils.findPointOrder(myCurve, G);
		int curveOrder = ECC_Utils.findCurveOrder(myCurve);
		int cofactor = curveOrder/pointOrder;
		System.out.println("Enter name of text file to encode: ");
		String fileName = kb.nextLine().trim();
		File inf = ECC_File_Utils.openInputFile(fileName);
		ECC_File_Utils.getEncryptionKey(inf, myCurve, G, pointOrder, cofactor);
		
		// ask user for base point G that generates subgroup
		// find N = order of group, find n = order of G, find cofactor h = N/n
		// ask user for file to encode
		// pass file to encoding method - ouptuts encoded file
	} // end encryptMessage
} // end class
























