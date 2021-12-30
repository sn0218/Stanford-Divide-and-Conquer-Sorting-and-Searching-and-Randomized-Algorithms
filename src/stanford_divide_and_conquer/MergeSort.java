package stanford_divide_and_conquer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//compute the no. of inversions of given array by Merge Sort

public class MergeSort {
	
	// count the split inversions in merging two sorted subarrays
	public static long mergeAndcount(int[] arr, int[] leftArr, int[] rightArr, int left, int right) {
		long inversions = 0;
		// initialize the indices two subarrays and input array
		int i = 0, j = 0, k = 0;
		// compare elements of two sorted subarrays to the input array
		while (i < left && j < right) {
			if (leftArr[i] < rightArr[j]) {
				arr[k++] = leftArr[i++];
			}
			else {
				arr[k++] = rightArr[j++];
				// count inversions when leftArr[i] > rightArr[j] and i < j
				inversions += (left - i);
			}
		}
		// copy the remaing element in left sorted array to input array when reach the end of right sorted array
		while (i < left) {
			arr[k++] = leftArr[i++];
		}
		// copy the remaing element in right sorted array to input array when reach the end of left sorted array
		while (j < right) {
			arr[k++] = rightArr[j++];
		}
		
		return inversions;
		
	}
	
	// merge sort and count the no. of inversions for the left and right halves of the array
	public static long sortAndcount(int[] arr, int n) {
		// initialize the no.of inversions to zero
		long inversions = 0;
		
		// base case that arr length is one
		if (n == 1) return 0;
		
		// recursion calls on counting the inversions of two subarrays
		// compute the index of m (middle) to divide the array into two subarrays
		int m = n / 2;
		int[] leftArr = new int[m];
		int[] rightArr = new int[n - m]; 
		
		// recursively create two subarrays
		for (int i = 0; i < m; i++) {
			leftArr[i] = arr[i];
		}
		
		for (int j = m; j < n; j++) {
			rightArr[j - m] = arr[j];
		}
		
		// compute the inversions of first sorted array
		inversions += sortAndcount(leftArr, m);
		// compute the inversions of sec. sorted array
		inversions += sortAndcount(rightArr, n-m);
		// compute the splitInv when merging the two sorted arrays
		inversions += mergeAndcount(arr, leftArr, rightArr, m, n-m);
			
		return inversions;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException{
		// TODO Auto-generated method stub
		
		// declare file reader variable and read the given text file
		File myFile = new File("integerArray.txt");
		Scanner sc = new Scanner(myFile);
		
		// intialize array to store integers
		int size = 100000;
		int[] arr = new int[size];
			
		// read the text file into integer array
		int i = 0;
		while (sc.hasNextInt()) {
			arr[i] = sc.nextInt();
			i++;
		}
		
		try {
			System.out.println("Length of the copied array: " + arr.length);
			System.out.println("No. of Inversions: " + sortAndcount(arr, arr.length));
			
		} catch (Exception e){
			System.out.println(e.getMessage());
		} finally {
	        System.out.println("try-block entered.");
	    }
		
		sc.close();

	}

}
