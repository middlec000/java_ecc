package ecc_main;

import java.io.FileNotFoundException;
import java.util.Scanner;
import ecc_methods.*;
import ecc_utils.*;
import ecc_classes.*;

public class ECC_Main {

	public static void main(String[] args) throws FileNotFoundException {
		int choice;
		boolean mod = false;
		Scanner kb = new Scanner(System.in);
		EllipticCurve myCurve = null;

		do {
			choice = ECC_Methods.menu​(kb);

			switch (choice) {
			// 1) Input curve (modulus optional)
			case 1:
				myCurve = ECC_Methods.chooseCurve(kb);
				mod = myCurve.isMod();
				System.out.println(myCurve);
				break;

			// 2) Choose P1 and choose n to find n*P1
			case 2:
				if (myCurve == null) {
					System.out.println("Must first input a curve.");
					break;
				}
				Point P1 = ECC_Methods.choosePoint(kb, mod);
				P1.setMod(mod);
				Point P2 = ECC_Methods.findNP(myCurve, P1, kb);
				P2.setMod(mod);
				System.out.println(P2);
				break;

			// 3) Enter P1 and P2 to find P1 + P2
			case 3:
				if (myCurve == null) {
					System.out.println("Must first input a curve.");
					break;
				}
				Point P3 = ECC_Methods.choosePoint(kb, mod);
				Point P4 = ECC_Methods.choosePoint(kb, mod);
				P3.setMod(mod);
				P4.setMod(mod);
				Point P5 = ECC_Utils.addPoints(myCurve, P3, P4);
				P5.setMod(mod);
				System.out.println(P5);
				break;

			// 4) Enter P1 and P2 to find n: n*P1=P2
			case 4:
				break; // TODO: finish this

			// 5) Enter a .txt file, output an encrypted version
			case 5:
				if (myCurve == null) {
					System.out.println("Must first input a curve.");
					break;
				}
				ECC_Methods.encryptMessage(myCurve, kb);
				break;

			// TODO: case 6 get encrypted file and decrypt it
			case 6:
				break;

			// 6) Quit
			case 7:
				System.out.println("Quitting...");

			}// end switch

		} while (choice != 7); // TODO: update if change number of choices

		System.out.println("Good Bye");
	}
}
