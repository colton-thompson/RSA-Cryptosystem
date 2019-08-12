import java.math.*; // for BigInteger
import java.util.*; // for Random and vector
import java.io.*; 
//import javax.sound.sampled.*;
import java.security.SecureRandom; //better randoms

//used for file handling

public class testing {
	//declare these to for basic ops
	private static BigInteger ZERO = new BigInteger("0"); 
	private static BigInteger ONE = new BigInteger("1");
	private static BigInteger TWO = new BigInteger("2");
	
	
	public static BigInteger modularExp(BigInteger base, BigInteger exponent, BigInteger modulus) {
		System.out.println("base: " + base + "\texp: " + exponent + "\tmod: " + modulus);
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
			//System.out.println("iloop");
			exponent = exponent.divide(TWO); 
			base = ((base.multiply(base))).mod(modulus); 
		} 
		//System.out.println("result: " +  result);
		return result;
	} //End modularExp()  
	
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
			//System.out.println("test: gcd:" + prevRemainder + "\tx: " + prevX + "\ty: " + prevY);
		}
		EEA.add(currRemainder);
		EEA.add(currX);
		EEA.add(currY);
		return EEA;
	}
	
	public static BigInteger getRand() {
		Random rand = new Random();
		int bonusRand = 0;//rand.nextInt(100);
		System.out.println("bonus Rand: " + bonusRand);
		BigInteger randBigInt = new BigInteger(330 + bonusRand , new SecureRandom());
		return randBigInt;
	}
	
	public static void main(String[] args) throws IOException  {
		//public BigInteger n; // public modulus
		//public BigInteger e = new BigInteger("3"); // encryption exponent
		//public String userName; // attach name to each public/private key pair
		BigInteger n, res, baseTest, baseExp, baseMod, a, b, lineTest, randTest;
		//int res = 0;
		n = new BigInteger("1234567890123456789012345678901234567890");
		System.out.println("Message n:\n" + n + "\n");
		
		//modExp(123, 456, 100);
		//modExp is working perfectly 
		baseTest = new BigInteger("999999999921412421349");
		baseExp = new BigInteger("45678934123412413477543487654888648568484000000");
		baseMod = new BigInteger("1234567890098765");
		res = modularExp(baseTest, baseExp, baseMod);
		System.out.println("modExp: " + res + "\n");
		
		//extended euclid is working fine generally
		//bug - occasional divide by zero error
		a = new BigInteger("123453412341595682346"); b = new BigInteger("351341234231423");
		Vector<BigInteger> eea = new Vector<BigInteger>();
		eea = extendedEuclid(a, b);
		System.out.println("EEA: gcd:" + eea.get(0) + "\tx: " + eea.get(1) + "\ty: " + eea.get(2));
		
		//how write to a file in 
		//PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
		PrintWriter writer = new PrintWriter("test.txt");
		writer.println(n);
		writer.println(res);
		writer.println("EEA: gcd:" + eea.get(0) + "\tx: " + eea.get(1) + "\ty: " + eea.get(2));
		writer.close();
		
		//how to read from a file 
		BufferedReader reader = new BufferedReader(new FileReader("test.txt"));
		String line1 = reader.readLine();
		String line2 = reader.readLine();
		String line3 = reader.readLine();
		reader.close();
		//this will convert the string to BigInteger
		lineTest = new BigInteger(line1);
		
		System.out.println("line1: " + lineTest);
		System.out.println("line2: " + line2);
		System.out.println("line3: " + line3);
		
		randTest = getRand();
		System.out.println("\nrand: " + randTest);


	}
}