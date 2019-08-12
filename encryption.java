import java.math.*; // for BigInteger
import java.util.*; // for Random and vector
import java.io.*;   // for file handling

import rsaFunctionPackage.functions;

// module: encryption 
// input: message.txt, public_key.txt
// output: ciphertext.txt
// purpose: read in message and public key, then use modular exponentiation to encrypt
class encryption {	
	private static BigInteger ONE = new BigInteger("1");
	
	public static void main(String[] args) throws IOException {
		functions encrypt = new functions();
		BigInteger n, e, m, c;
		String pub = "public_key.txt";
		String message  = "message.txt";
		String cipher = "ciphertext.txt";
		
		//read in public key
		//check if public key is empty before moving on
		Vector<BigInteger> filePub = encrypt.fileRead(pub);
		n = filePub.get(0);	//System.out.println("n: " + n);
		//e = filePub.get(1);	//System.out.println("e: " + e);
		
		if (n.equals((ONE.negate()))) 
			System.out.println("Error: " + pub + " is empty. " );//+ message + " needs to have values for encryption.");
		else {
			//finish read from pub
			e = filePub.get(1);	//System.out.println("e: " + e);
			
			//read in message
			//check if message is empty before moving on to encryption
			Vector<BigInteger> fileMessage = encrypt.fileRead(message);
			m = fileMessage.get(0); //System.out.println("m: " + m);
			if (m.equals((ONE.negate()))) 
				System.out.println("Error: " + message + " is empty. " );//+ message + " needs to have values for encryption.");
			else {
				//encrypt using modularExp
				//c - ciphertext
				c = encrypt.modularExp(m, e, n); 
				encrypt.fileWrite(cipher, c);
				
				//success message
				encrypt.success(cipher);
			}
		}
	}
}