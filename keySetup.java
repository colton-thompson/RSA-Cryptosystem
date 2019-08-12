import java.math.*; // for BigInteger
import java.util.*; // for Random and vector
import java.io.*; 	// for file handling
import java.security.SecureRandom; // for better randoms

import rsaFunctionPackage.functions; // my function package 

//BigInteger functions 
//https://www.tutorialspoint.com/java/math/java_math_biginteger.htm 
//used site above for reference when using BigInteger functions

// module: keySetup 
// input: -
// output: private_key.txt, public_key.txt
// purpose: generate both key files, with prime values that are at least 100 digits

public class keySetup {
	//declare these to for basic ops
	private static BigInteger ZERO = new BigInteger("0"); 
	private static BigInteger ONE = new BigInteger("1");
	private static BigInteger TWO = new BigInteger("2");
	private static BigInteger THREE = new BigInteger("3");
	private static BigInteger FOUR = new BigInteger("4");
	//private static BigInteger TEN = new BigInteger("10"); //used for testing purposes
		
	static boolean millerRabinTest(BigInteger d, BigInteger n) { 
		functions mrt = new functions();
		// Pick a random number in [2..n-2] 	
		//System.out.println("numbits: " + (n.subtract(TWO)).bitLength());
		BigInteger a = new BigInteger(n.subtract(TWO).bitLength(), new SecureRandom());
		a = a.add(TWO); //System.out.println("a: " + a);
		
		// Compute a^d % n  
		BigInteger x = mrt.modularExp(a, d, n); //System.out.println("x: " + x);
		if (x.equals(ONE) || ((x.equals(n.subtract(ONE)))))
			return true;
		
		//square x repeatedly while:
		//	d is not n - 1
		//	x^2 mod n != 1
		//	x^2 mod n != n - 1
		while (!(d.equals(n.subtract(ONE)))) {
			x = (x.multiply(x)).mod(n); //x = (x * x) % n; 
			d = d.multiply(TWO); 	//d *= 2; 
			
			if (x.equals(ONE)) //(x == 1) 
				return false; 
				
			if (x.equals(n.subtract(ONE))) //(x == n - 1) 
				return true; 
		} 
		return false; // Return composite 
	} //End millerRabinTest()

	// false if n is composite, true if n is probably prime 
	// input k is number of iterations, more iterations more accurate (1/4^k)
	static boolean isPrime(BigInteger num, int numIterations) { 
		// Corner cases 
		//if (n <= 1 || n == 4) 
		if (num.compareTo(ONE) == -1 || num.equals(FOUR))
			return false; 
		//if (n <= 3) 
		if (num.compareTo(THREE) == -1)
			return true; 

		//setup d:
		//	- subtract one to make it even
		//	- while d (mod 2) = 0, divide a two out to set up n - 1 = d * 2^r
		BigInteger d = num.subtract(ONE);	// d = n - 1; 
		while (d.mod(TWO).compareTo(ZERO) == 0) { //(d % 2) == 0
			d = d.divide(TWO);
		}

		// Iterate while i is less than numIterations 
		for (int i = 0; i < numIterations; i++) {
			if (!millerRabinTest(d, num)) {
				//Miller-Rabin Test found factor, therefore composite
				return false; 
			}
		}
		//passed all the tests, it most likely prime (safe to assume)
		return true; 
	} //End isPrime()
		
	public static Vector<BigInteger> getRand() {
		Vector<BigInteger> randVec = new Vector<BigInteger>();
		int iterations = 64, numBits = 330, numDigits;
		BigInteger randBigInt;
		
		// generate number at random
		// test if generated number is prime
		// if it is prime, end loop and add number to vector 
		// else generate new random until we get a large prime
		for (int i = 0; i < 2; i++) {
			do {
				// add the i value to gaurentee difference of 10^95
				randBigInt = new BigInteger(numBits + i, new SecureRandom());
				//count the number of digits in the generated prime
				numDigits = randBigInt.toString().length(); //System.out.println("numDigts " + i + ": " + numDigits);
			//do this while randBigInt is not prime or has less than 100 decimal digits	
			} while (numDigits < 100 || !(isPrime(randBigInt, iterations)) );
			randVec.add(randBigInt);
		}
			//System.out.println("Chance on not prime: 1/4^" + iterations);
			/*
			//used for testing that difference is greater than 10^95
			BigInteger test1 = randVec.get(1); BigInteger test2 = randVec.get(0); BigInteger test = (test1.subtract(test2));
			System.out.println("size test: " + test + "\t\tis larger?: " + test.compareTo(TEN.pow(95)));
			*/
		return randVec; //returns vector with p and q
	} //End getRand()
	
	public static Vector<BigInteger> extendedEuclid(BigInteger alpha, BigInteger beta){
		Vector<BigInteger> EEA = new Vector<BigInteger>();
			//EEA[0] - gcd(alpha, beta) 
			//EEA[1] - x
			//EEA[2] - y
		BigInteger temp = ZERO, quotient = ZERO;
		BigInteger currX = ZERO, prevX = ONE;
		BigInteger currY = ONE, prevY = ZERO;
		BigInteger currRemainder = beta, prevRemainder = alpha;
		
		while(!(currRemainder.equals(ONE))) {
			quotient = prevRemainder.divide(currRemainder);		
			
			//set currXXX in a tmp variable
			//do some good ol' value swapping and updating
			//set prevXXX to tmp value 
			//curr = prev - (quotient * curr)
			
			//Remainder switch and update 
			temp = currRemainder;
			currRemainder = prevRemainder.subtract(quotient.multiply(currRemainder));
			prevRemainder = temp;			
			//X switch and update
			temp = currX;
			currX = prevX.subtract((quotient.multiply(currX)));
			prevX = temp;
			//Y switch and update
			temp = currY;
			currY = prevY.subtract((quotient.multiply(currY)));		
			prevY = temp;
		}
		//add elements to vector eea
		EEA.add(currRemainder);
		EEA.add(currX);
		EEA.add(currY);
		return EEA;
	} //End extendedEuclid()
	
	//no errors known
	public static void fileWrite(String fileName, BigInteger n, BigInteger e) throws IOException {
		//create new writer object to be able to write to a file
		PrintWriter writer = new PrintWriter(fileName);
		writer.println(n); //line 1
		writer.println(e); 
		writer.close(); //close file
	} //End fileWrite()
	
	public static void main(String[] args) throws IOException  {
		//get functions from rsaFunctionPackage
		functions key = new functions();
		BigInteger n, m, q, p, res, d, e;
		Boolean acceptableNum = false;
		
		//file names
		String pub = "public_key.txt";
		String priv = "private_key.txt";
		
		System.out.println("generating primes... ");
		do {
			Vector<BigInteger> primeRands = new Vector<BigInteger>();
			primeRands = getRand();
			p = primeRands.get(0); q = primeRands.get(1);
			//System.out.println("rands:\n\tp: " + p + "\n\tq: " + q );
			
			//get e
			//verify that this e will work
			//provide contigency if it doesn't - see below
			e = new BigInteger("65537"); 
			
			//get n
			n = p.multiply(q); //System.out.println("\nn(p*q):\n" + n + "\n");
			
			//get m (rel prime to n) m = (p - 1)(q - 1)
			m = (p.subtract(ONE)).multiply(q.subtract(ONE));
			
			//extended euclid is working fine generally
			//BUG - occasional divide by zero error 
			//BUG fixed - due to not checking for primes dumbass
			Vector<BigInteger> eea = new Vector<BigInteger>();
			eea = extendedEuclid(e, m);
			//System.out.println("EEA:\n \tgcd: " + eea.get(0) + "\n\tx: " + eea.get(1) + "\n\ty: " + eea.get(2));
			d = eea.get(1);
				 
			//case if d is negative
			//take d % m, then add m where m is rel prime number (p - 1)(q -1)
			if (d.compareTo(ZERO) == -1 )
				d = (d.mod(m)).add(m); 
			
			//this check is to ensure d will work and our gcd is 1
			//e = 65537, for cases where this is a factor it will be caught
			//by this as the gcd will not equal 1 and new numbers will be generated
			//if (first != '-' && eea.get(0).equals(ONE)) 
			if (d.compareTo(ZERO) != -1 && eea.get(0).equals(ONE)) 
				acceptableNum = true;
		
		} while (!acceptableNum);
		
		//write the public and private key files out
		fileWrite(pub, n, e); 
		key.success(pub); //success message	
		fileWrite(priv, n, d); 
		key.success(priv); //success message		
	}
}