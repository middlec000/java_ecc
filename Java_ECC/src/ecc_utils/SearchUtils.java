package ecc_utils;
import ecc_classes.Point;

public class SearchUtils{
	
	public static int linearSearch(final Point[] array, final Point target) {
		if (array == null || array.length < 1 || target == null)
			throw new IllegalArgumentException("Bad params in searchUtils.linearSearch");
		for (int x = 0; x < array.length; x++)
			if (array[x].equals(target))
				return x;
		return -1;

	}// end linearSearch
	// If the target does not match any value in the array then -1 is returned.
	// from Lab 12 in my EWU class CSCD 210: Programming Fundamentals I
}
