package stanford_divide_and_conquer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class QuickSort {
	
	private static void swap(int[] arr, int i, int j) {
		if (i == j) return;
		
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
		
	}
	
	// return the index of median of three candidate element in given array
	private static int medianOfthree(int[] arr, int l, int r) {
		int first = arr[l]; // first element of input array
		int last = arr[r]; // final element of input array
		int m; // index of middle element
		int arrlength = r - l + 1;
		
		// be careful with return the middle index
		// check the an array with even length
		if (arrlength % 2 == 0) {
			m = (arrlength / 2) - 1 + l;
		} else {
			m = arrlength / 2 + l;
		}
		
		int middle = arr[m];
		int maxVal = Math.max(first, Math.max(middle, last));
		int minVal = Math.min(first, Math.min(middle, last));
		
		// check for median of the three elements and return its index
		if (first != maxVal && first != minVal) 
			return l;
		else if (last != maxVal && last != minVal)
			return r;
		else
			return m;
	}
	
	// parition array arr[l...r]
	private static int partition(int[] arr, int l, int r) {
		// set the first element as pivot
		int pivot = arr[l];

		// intialize index i to declinate the boundary in partition
		int i = l + 1;
		
		// linear scan from j to r through the array and compare with the pivot
		for (int j = l + 1; j <= r; j++) {
			if (arr[j] < pivot) {
				// swap the newly seen element that is smaller than the pivot
				swap(arr, i, j);
				// increment i to delinate new boundary in parition
				i++;
			}
		}
		// place the pivot correctly
		// swap first element (pivot) with rightmost element less than pivot
		swap(arr, l, i - 1);
		
		// return the final pivot position
		return i - 1;
	}
	
	public static int quickSortAndcount(int[] arr, int l, int r) {
		// base case that 0 or 1 element in subarray that is already sorted
		if (l >= r) return 0;
		
		// count the number of comparisons between pairs of input element 
		// cannot pass arr.length to comparisons as the lenght of array is the same in recursion call
		int comparisons = r - l;
		
		// choose first element as pivot
		// int i = l;
		
		// chose last element as pivot
		// int i = r;

		// choose pivot from 3 candidate elements
		int i = medianOfthree(arr, l, r);
		
		// put the chosen pivot at the first 
		swap(arr, l, i);
		
		// compute the new pivot position 
		int j = partition(arr, l, r);
		// recurse on 1st part subarray arr[l,..,j-1] less than pivot
		comparisons += quickSortAndcount(arr, l , j - 1);
		// recurse on 2nd part subarray arr[j,...,r] greater than pivot
		comparisons += quickSortAndcount(arr, j + 1, r);
		
		return comparisons;
		
	}

	public static void main(String[] args) throws FileNotFoundException{
		// TODO Auto-generated method stub
		
		// declare file reader variable and read the given text file
		File myFile = new File("QuickSort.txt");
		Scanner sc = new Scanner(myFile);
		
		// intialize array to store integers
		int size = 10000;
		int[] arr = new int[size];
		
		// read the text file into integer array
		int i = 0;
		while (sc.hasNextInt()) {
			arr[i] = sc.nextInt();
			i++;
		}
		
		try {
			System.out.println("Length of the copied array: " + arr.length);
			System.out.println("No. of comparisons: " + quickSortAndcount(arr, 0, arr.length - 1));
			
			// check the sorted integer array
			for (int j = 0; j < arr.length; j++) {
				System.out.println(arr[j]);			
				}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("Main is executed.");
		}
		
		sc.close();
	}
}
