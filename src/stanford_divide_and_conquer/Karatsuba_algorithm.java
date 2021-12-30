package stanford_divide_and_conquer;
import java.util.Scanner;
import java.math.BigInteger;

// handle very large integer mutliplication by Class BigInteger

public class Karatsuba_algorithm {
	
	// Karatsuba multiplication assume the input number is even-digit integer
	public static BigInteger karatsuba(BigInteger x, BigInteger y){
	// recursion base case if each of x and y is single digit
		if (x.compareTo(BigInteger.valueOf(10)) < 0 || y.compareTo(BigInteger.valueOf(10)) < 0) {
			
			return x.multiply(y);		
		}
				
		// calculate the size of the number
		// convert the input number to string and return the length of the string
		int xLength = x.toString().length();
		int yLength = y.toString().length();
		int m = Math.max(xLength, yLength) / 2; // store N/2 in variable m
		int xSplit = xLength - m; // index for spliting x into half
		int ySplit = yLength - m; // index for spliting y into half
				
		// split the a, b, c, d by converting BigInteger into string
		// substring() in which end index is not captured
		String a1 = x.toString().substring(0, xSplit);
		String b1 = x.toString().substring(xSplit);
		String c1 = y.toString().substring(0, ySplit);
		String d1 = y.toString().substring(ySplit);
		
		// convert tmp variable to BigInteger
		BigInteger a = new BigInteger(a1);
		BigInteger b = new BigInteger(b1);
		BigInteger c = new BigInteger(c1);
		BigInteger d = new BigInteger(d1);
				
		// multiply ac, bd and (a+b)(c+d) recursively 
		BigInteger ac = karatsuba(a, c);
		BigInteger bd = karatsuba(b, d);
		BigInteger z = karatsuba((a.add(b)), (c.add(d))).subtract(ac).subtract(bd);
				
		// compute thre result
		BigInteger result =  ac.multiply(BigInteger.valueOf(10).pow(2 * m)).add(bd).add(z.multiply(BigInteger.valueOf(10).pow(m)));

		
		return result;
				
		}	
		

		public static void main(String[] args) {
			// TODO Auto-generated method stub
		
			Scanner sc = new Scanner(System.in);
			try{
				System.out.println("Integer Multiplication");
				// prompt for user input
				System.out.println("Please enter the first integer: ");
				String x = sc.next();
				System.out.println("Please enter the second integer: ");
				String y = sc.next();
				
				// compute the result of recursive multiplication
				BigInteger result = karatsuba(new BigInteger(x), new BigInteger(y));
				
				// convert the result to string
				String ans = result.toString();
				
				System.out.println("The product of x * y is " + ans);

			}
			finally {
				sc.close();

			}
	}

}
