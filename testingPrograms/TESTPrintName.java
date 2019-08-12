/* import 'MyClass' class from 'names' myPackage */
import myPackage.TESTmyClass;

public class TESTPrintName 
{
	public static void main(String args[]) 
	{       
		// Initializing the String variable 
		// with a value 
		String name = "GeeksforGeeks";
		
		// Creating an instance of class MyClass in 
		// the package.
		TESTmyClass obj = new TESTmyClass();
		
		obj.getNames(name);
	}
}