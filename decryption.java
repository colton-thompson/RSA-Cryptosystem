import java.math.*; // for BigInteger
import java.util.*; // for Random and vector
import java.io.*; 	// for file handling

import rsaFunctionPackage.functions;

// module: decryption 
// input: ciphertext.txt, public_key.txt, private_key.txt
// output: decrypted_message.txt
// purpose: read in message, private key and public key, then use modular exponentiation to decrypt
class decryption {
	private static BigInteger ONE = new BigInteger("1");

	public static void main(String[] args) throws IOException {
		functions decrypt = new functions();
		BigInteger n, e, m, c, d;
		
		//file names
		String pub = "public_key.txt";
		String cipher = "ciphertext.txt";
		String priv = "private_key.txt";
		String dec_mes = "decrypted_message.txt";
		
		//read in public key 
		Vector<BigInteger> filePub = decrypt.fileRead(pub);
		n = filePub.get(0);	//System.out.println("n: " + n);
		
		if (n.equals((ONE.negate()))) 
			System.out.println("Error: " + pub + " is empty. " );
		else {
			//finish read from pub
			e = filePub.get(1);	//System.out.println("e: " + e);

			//read in private key 
			//only reading in second element because we got n from pub already
			Vector<BigInteger> filePriv = decrypt.fileRead(priv); 
			if (filePriv.get(0).equals((ONE.negate()))) 
				System.out.println("Error: " + priv + " is empty. " );
			else {
				//finish read from priv
				d = filePriv.get(1); //System.out.println("d: " + d);
												
				//read in ciphertext
				Vector<BigInteger> fileCipher = decrypt.fileRead(cipher);
				c = fileCipher.get(0); //System.out.println("c: " + c);
				if (c.equals((ONE.negate()))) 
					System.out.println("Error: " + cipher + " is empty. " );
				else {
					//get m and write it the file
					m = decrypt.modularExp(c, d, n); //System.out.println("\nm: " + m);
					decrypt.fileWrite(dec_mes, m);
					
					//success message
					decrypt.success(dec_mes);
				}
			}
		}
	}
}