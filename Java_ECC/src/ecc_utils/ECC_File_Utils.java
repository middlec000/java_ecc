package ecc_utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintStream;
import ecc_utils.*;
import ecc_classes.*;

public class ECC_File_Utils {
	public static File openInputFile(final Scanner kb) {
		if (kb == null)
			throw new IllegalArgumentException("Scanner is null.");
		String fileName = "";
		File inf = null;

		do {
			System.out.print("Enter name of input file: ");
			fileName = kb.nextLine(); // input buffer left clean
			inf = new File(fileName);
		} while (!inf.exists());

		return inf;
	} // end openInputFile

	public static File openInputFile(final String fileName) {
		if (fileName == null || fileName.isBlank())
			throw new IllegalArgumentException("fileName == null || fileName.isBlank()");
		File inf = new File(fileName);
		if (!inf.exists())
			throw new RuntimeException("file DNE");
		else
			return inf;
	} // end openInputFile

	public static void getEncryptionKey(final File inf, final EllipticCurve myCurve, final Point G,
			final int pointOrder, final int cofactor) throws FileNotFoundException {
		int da = (int) Math.floor(Math.random() * ((myCurve.getP()) - 1 + 1) + 1); // equivelant to random*(max-min+1)+1
																					// see Lab4
		System.out.println("Alice's private key is da where H = (da)G: " + da);
		Point Ha = ECC_Utils.calculateNP(myCurve, G, da);
		System.out.printf(
				"Alice's public key is: [Ha = dG = (x, y) = (%d, %d), p = %d, A = %d, B = %d, G = (%d, %d), "
						+ "order of subgroup = %d, cofactor of subgroup = %d] \n",
				(int) Ha.getx(), (int) Ha.gety(), (int) myCurve.getP(), (int) myCurve.getA(), (int) myCurve.getB(),
				(int) G.getx(), (int) G.gety(), pointOrder, cofactor);
		int db = (int) Math.floor(Math.random() * ((myCurve.getP()) - 1 + 1) + 1); // equivelant to random*(max-min+1)+1
																					// see Lab4
		Point Hb = ECC_Utils.calculateNP(myCurve, G, db);
		System.out.printf("Bob wants to send a message to Alice so he calculates db = %d and Hb = (db)G = (%d, %d) \n",
				db, (int) Hb.getx(), (int) Hb.gety());
		System.out.println("Alice and Bob excange Ha and Hb keeping da and db secret.");
		Point S = ECC_Utils.calculateNP(myCurve, Ha, db);
		System.out.printf("Alice calculates S=(da)(Hb)=(da)(db)(G)=(db)(Ha) = (%d, %d) \n", (int) S.getx(),
				(int) S.gety());
		System.out.printf(
				"Bob calculates the same S, so they can use some componenet of S to encrypt and decrypt their messages.\n"
						+ "For simplicity, let's just use the x component to perform a simple Ceasar Cipher.");
		ceasarCipher(inf, (int) S.getx());
	} // end encryptFile

	public static void ceasarCipher(final File inf, final int shift) throws FileNotFoundException {
		Scanner fileReader = new Scanner(inf);
		PrintStream fout = new PrintStream(new File("CeasarCipherOutput.txt"));
		String currentLine = "";
		String outLine = "";
		while (fileReader.hasNext()) {
			for (int i = 0; i < currentLine.length(); i++) {
				char c = (char) (currentLine.charAt(i) + shift);
				if (c > 'z')
					outLine += (char) (currentLine.charAt(i) - (26 - shift));
				else
					outLine += (char) (currentLine.charAt(i) + shift);
			}
			// code based on code by: Eric Leschinski
			// found at:
			// https://stackoverflow.com/questions/19108737/java-how-to-implement-a-shift-cipher-caesar-cipher
			fout.println(outLine);
		}
		fileReader.close();
	} // end ceasarCipher

} // end class
