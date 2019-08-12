import java.lang.Integer; 
import java.math.*;


class Gfg { 
	// driver code 
	private static BigInteger TEN = new BigInteger("10");
	public static void main(String args[]) 
	{ 
		//BigInteger a = new BigInteger("7650211272838729733379666102041829672071862092"); 
  		String p, q, n ;
		n = "111111111111111222222222222222333333333333333444444444444444555555555555555666666666666666777777777777777888888888888888999999999999999000000000000000";
		System.out.println(n);
		//get size of string
		int size = n.length();
		//convert int into a binary formated string 
  		//String str = BigInteger.toBinaryString(a);		
		// bit count will only count set bits
		//System.out.println("bit count: " + Integer.bitCount(a)); 
		//size will determine all bits used
		System.out.println("N size: " + size); 
		
		p = "316306029133211008595104957370172413240112358226494519632205505408807358472128760226337036128651939";
		System.out.println("\n" + p);
		size = p.length();
		System.out.println("P size: " + size);
		
		q = "3632352005599888954878099231594283113153269055859269485239450575669245133618552870543944390892028753";
		System.out.println("\n" + q);
		size = q.length();
		System.out.println("Q size: " + size);
		
		//BigInteger n = TEN.pow(95);
		//System.out.println(n);	
	} 
} 