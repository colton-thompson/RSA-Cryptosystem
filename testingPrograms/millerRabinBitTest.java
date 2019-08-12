import java.io.*; 
import java.math.*; 
import java.util.*;
import java.security.SecureRandom; //better randoms

class millerRabinBigTest { 
	private static BigInteger ZERO = new BigInteger("0"); 
	private static BigInteger ONE = new BigInteger("1");
	private static BigInteger TWO = new BigInteger("2");
	private static BigInteger THREE = new BigInteger("3");
	private static BigInteger FOUR = new BigInteger("4");

	public static BigInteger modularExp(BigInteger base, BigInteger exponent, BigInteger modulus) {
		//System.out.println("base: " + base + "\texp: " + exponent + "\tmod: " + modulus);
		BigInteger result = ONE;
		//exponent.compareTo(ZERO) - (exponent > 0)
		//will return 1 if greater, 0 if equal, -1 if less than
		while (exponent.compareTo(ZERO) > 0) {
				//exp % 2 == 1
			if ((exponent.mod(TWO)).equals(ONE)) {
				//result = (result * base) % modulus
				result = ((result.multiply(base))).mod(modulus);
			}
			//dividing by 2 is same as 1 bit shift (>>)
			exponent = exponent.divide(TWO); 
			base = ((base.multiply(base))).mod(modulus); 
		} 
		//System.out.println("result: " +  result);
		return result;
	} //End modularExp()
	
	// This function is called for all k trials. 
	// It returns false if n is composite and 
	// returns false if n is probably prime. 
	// d is an odd number such that d*2<sup>r</sup> 
	// = n-1 for some r >= 1 
	static boolean millerTest(BigInteger d, BigInteger n) { 
		// Pick a random number in [2..n-2] 	
		System.out.println("numbits: " + n.subtract(TWO).bitLength());
		BigInteger a = TWO.add(new BigInteger(n.subtract(TWO).bitLength(), new SecureRandom()));
		//a = a.add(TWO);
		System.out.println("a: " + a);
		// Compute a^d % n 
		//int x = power(a, d, n); 
		BigInteger x = modularExp(a, d, n);
		System.out.println("x: " + x);
		//if (x == 1 || x == n - 1) 
		//	return true; 
		//if(x.compareTo(ONE) == 0) 
		if (x.equals(ONE) || ((x.equals(n.subtract(ONE)))))
			return true;
	
		// Keep squaring x while one of the 
		// following doesn't happen 
		// (i) d does not reach n-1 
		// (ii) (x^2) % n is not 1 
		// (iii) (x^2) % n is not n-1 
		//while (d != n - 1) { 
		System.out.println("!= : " + d.equals(n.subtract(ONE)));
		while (!(d.equals(n.subtract(ONE)))) {
			//x = (x * x) % n; 
			x = (x.multiply(x)).mod(n);
			//d *= 2; 
			d = d.multiply(TWO);
			//if (x == 1) 
			if (x.equals(ONE))
				return false; 
			//if (x == n - 1) 
			if (x.equals(n.subtract(ONE)))
				return true; 
		} 
	
		// Return composite 
		return false; 
	} 
	
	// It returns false if n is composite 
	// and returns true if n is probably 
	// prime. k is an input parameter that 
	// determines accuracy level. Higher 
	// value of k indicates more accuracy. 
	static boolean isPrime(BigInteger n, int k) { 
		
		// Corner cases 
		//if (n <= 1 || n == 4) 
		if (n.compareTo(ONE) == -1 || n.equals(FOUR))
			return false; 
		//if (n <= 3) 
		if (n.compareTo(THREE) == -1)
			return true; 
	
		// Find r such that n = 2^d * r + 1 
		// for some r >= 1 
		//int d = n - 1; 
		BigInteger d = n.subtract(ONE);
		System.out.println("d: " + d + "\n");
		/*
		while (d % 2 == 0) {
			d /= 2; 
			//System.out.println("\nd: " + d); */
		//this will factor out the twos in d
		while (d.mod(TWO).compareTo(ZERO) == 0) {
			d = d.divide(TWO);
		}

		// Iterate given nber of 'k' times 
		for (int i = 0; i < k; i++) 
			if (!millerTest(d, n)) 
				return false; 
	
		return true; 
	} 
	
	// Driver program 
	public static void main(String args[]) { 
		
		//BigInteger k = new BigInteger("123435"); // Number of iterations 
		Vector<BigInteger> randVec = new Vector<BigInteger>();
		int k = 4;
		Boolean arePrime = false;
		//BigInteger n = new BigInteger("82589933");
		//System.out.println("All primes smaller "
		//						+ "than 100: "); 
								
		//for (int n = 1; n < 100; n++) 
	//	while (!arePrime) {
			for(int i = 0; i < 2; i++) {
				//add the i value to gaurentee difference of 10^95
				BigInteger randBigInt = new BigInteger(20 + i, new SecureRandom());
				//		x = (2 * x) + 1 will be odd
				//randBigInt = (randBigInt.multiply(TWO)).add(ONE);
				randVec.add(randBigInt);
			}
			//used for testing that difference is greater than 10^95
			/*
			BigInteger test1 = randVec.get(1);
			BigInteger test2 = randVec.get(0);
			BigInteger test = (test1.subtract(test2)).abs();
			System.out.println("size test: " + test + "\t\tis larger?: " + test.compareTo(TEN.pow(95)));
			*/
			
			//insert miller-rabin 
			//calculate and send it to check for primality 
			
			BigInteger n = (randVec.get(0).multiply(randVec.get(1)));
			
			System.out.println("rV(1): " + randVec.get(1));
			
			if (isPrime(randVec.get(1), k)) {
				System.out.print(n + " is probably prime. Chance on not prime: 1/4^" + k); 
				arePrime = true;
			} else {
				System.out.println(n + ": composite; tested " + k + " times");
			} 
		} 
//	}
} 