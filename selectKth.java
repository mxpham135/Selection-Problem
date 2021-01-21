import java.util.Arrays;
import java.util.Random;


public class selectKth {
	public static void main(String[] args) {
		// create 4 arrays for 4 algorithms
		int[] array; 
	    int[] arrayA; 
	    int[] arrayB;
	    int[] arrayC;
	    int[] arrayD;
	     
	    // an array hold list of size n
	 	int[] arraySize = {10, 50, 100, 250, 500, 1000, 2500, 5000, 10000};
	 		
	 	// variables
	    int size, k, a, b, c, d; 
	    double x = 0;
	    long startTime, endTime1, endTime2, endTime3, endTime4;
	    
	    // loop through arraySize to assign how large the arrays should be
	    for (int i = 0; i < arraySize.length; i++) {
	    	size = arraySize[i];
	    	
	    	// generated array of size n and copy to all arrays
	    	array = generatedArray(size);
		    arrayB = arrayC = arrayD = arrayA = array;
	    	System.out.println("i = " + i + " size = " + size);
	    	
	    	// reset/initial x
	    	x = 0;
	    	
	    	// for each size n
	    	while (x <= 1) {
	    		// calculate the k value
	    		k = (int) (size - (size * x));
	    		if (k == 0)
	    			k = 1;
	    		
	    		// reset/initial timers
		    	endTime1 = endTime2 = endTime3 = endTime4 = 0;
		    	
		    	// for each kth value, run arrays 30 times of each algorithm 
		    	// to calculate the runtime 
	    		for (int j = 0; j < 30; j++) {
	    			startTime = System.nanoTime(); 
		    		a = selectKth1(arrayA, k);
		    		endTime1 += System.nanoTime() - startTime;
		    		
		    		startTime = System.nanoTime();
			    	b = selectKth2(arrayB, k);
			    	endTime2 += System.nanoTime() - startTime;
			    	
			    	startTime = System.nanoTime();
			        c = selectKth3(arrayC, k);
			        endTime3 += System.nanoTime() - startTime;
			        
			        startTime = System.nanoTime();
			    	d = selectKth4(arrayD, k);	
			    	endTime4 += System.nanoTime() - startTime;
			    	
			    	// reset arrays
			    	arrayB = arrayC = arrayD = arrayA = array;
	    		}
		    	 
	    		// update x for k later
		    	x += .25;
		    	
		    	// print out get the average runtime
				System.out.println("\nAverage RunTime for size n = " + size + " with k = " + k); 
				System.out.println("\tSelect kth 1: " + endTime1/30); 
				System.out.println("\tSelect kth 2: " + endTime2/30); 				
				System.out.println("\tSelect kth 3: " + endTime3/30); 				
				System.out.println("\tSelect kth 4: " + endTime4/30); 
	    	}
	    } 
	}

	// generated an array of size n with random integer
	private static int [] generatedArray(int n)	{
		Random rand = new Random();
		
		// create array with size n
		int arr[] = new int[n]; 
		
		// fill array with random integer
		for (int i = 0; i < n; i++)
			arr[i]= rand.nextInt(1000000);
		
		return arr;	
	}

	// swap the two giving position of an array
	private static void swap(int arr[], int x, int y) {
		int temp = arr[x];
		arr[x] = arr[y];
		arr[y] = temp;
	}

	// Select-kth 1: Algorithm 1 using Mergesort 
	private static int selectKth1(int arr[], int k) {
		mergeSort(arr, 0, arr.length - 1);
		return arr[k-1];
	}
	
	// the split part of merge sort
	private static void mergeSort(int arr[], int low, int high) {
		// check if low is smaller than high, if not then the array is sorted
		if (low < high) {
			
			// get the middle index 
			int mid = (low + high) / 2;
 
			// sort the left side
            mergeSort(arr, low, mid);
            
            // sort the right side
            mergeSort(arr, mid + 1, high);
            
            // merge the sorted arrays together
            merge(arr, low, mid, high);
		}
	}
	
	// merging part of mergeSort, merged arrays back together
	private static void merge(int arr[], int low, int mid, int high) {
		// assign indexes and create temp array
		int i = low, j = mid + 1, k = low;  
		int temp[] = new int [high+1];
		
		// go through array
		while(i <= mid && j <= high)	{  
			// if i is smaller, move i into temp array
			if (arr[i] < arr[j])  {  
				temp[k] = arr[i];  
		        i++;  
		    }
			// if j is smaller, move j into temp array
			else {  
				temp[k] = arr[j];  
				j++;
		    }  
		    k++;  
		}
		
		// if i greater than mid, move A(j) through A(high) to U(k) through U(high)
	 	if(i > mid)	{  
	        while(j <= high)  {  
	            temp[k] = arr[j];  
	            k++;  
	            j++;  
	        }  
	    }  
	 	// else move A(i) through A(mid) to U(k) through U(high)
	 	else	{  
	        while(i <= mid)  {  
	            temp[k] = arr[i];  
	            k++;  
	            i++;  
	        }  
	    }  
	 	
	 	// move temp array into array
	 	for (int p = low; p <= high; p++) {
	 		arr[p] = temp[p];
	 	} 
	 }	

	// Select-kth 2: Algorithm 2 using QuickSort iteratively
	private static int selectKth2(int arr[], int k) {
		return quickSortIterative(arr, 0, arr.length - 1, k-1);
	}

	public static int quickSortIterative(int arr[], int low, int high, int k) { 
		// if only 1 element left, return that element
		if (low == high)
			return arr[low];
		
		while (low <= high)  {   
			// find the pivot position by partition the array around the last element
            int pivotPosition = partitionLast(arr, low, high);  
      
    		// if pivot = k, return k
            if(k == pivotPosition)  
    			return arr[k];
    		// if positon is more than k, recursive left
            else if(k < pivotPosition ) 
            	high = pivotPosition - 1;
    		// if position is less than k, recursive right
            else 
            	low = pivotPosition + 1; 
        }  
        return -1;  
	} 
	
	// partition using the first element as pivot
	private static int partitionFirst(int arr[], int low, int high) { 
		// partitions the array around first element
		int v = arr[low]; 
		int j = low;
		 
		for (int i = low + 1; i <= high; i++) { 
			if (arr[i] < v) { 	
				j++;
				swap(arr, i, j); 	
			} 
	    } 
	    swap(arr, low, j); 
	    return j;
	} 

	// Select-kth 3: Algorithm 2 using QuickSort recursively  
	private static int selectKth3(int arr[], int k) {
		return quickSortRecursive(arr, 0, arr.length - 1, k-1);
	}

	public static int quickSortRecursive(int[] arr, int low, int high, int k) { 
		// if only 1 element left, return that element
		if (low == high)
			return arr[low];
		
		// find the pivot position by partition the array around the last element
		int pivotPosition = partitionLast(arr, low, high); 
		
		// if pivot = k, return k
		if(k == pivotPosition)  
			return arr[k];     
		// if positon is more than k, recursive left
		else if(k < pivotPosition ) 
			return quickSortRecursive(arr, low, pivotPosition - 1, k);  
		// if position is less than k, recursive right
		else   
			return quickSortRecursive(arr, pivotPosition + 1, high, k); 
	} 
	
	// partition using the last element as pivot
	private static int partitionLast(int arr[], int low, int high) { 
		// partitions the array around last element
		int v = arr[high]; 
	    int j = low; 
	   
	    for (int i = low; i <= high; i++) { 
	        if (arr[i] < v) { 
	            swap(arr, i, j); 
	            j++; 
	        } 
	    } 
	    swap(arr, high, j); 
	    return j;
	} 

	// Select-kth 4: Algorithm 3 using QuickSort MM
	private static int selectKth4(int arr[], int k) {
		return quickSortMM(arr, 0, arr.length-1, k-1);
		
	}

	private static int quickSortMM(int arr[], int low, int high, int k) {		
		// if only 1 element left, return that element
		if (low == high)
			return arr[low];
		
		// find the median of median from the array
		int medianOfMedian = medianOfMedian(arr, low, high);
		
		// used median of median as the pivot position
		int pivotPosition = partitionMM(arr, low, high, medianOfMedian); 
		
		// if pivot = k, return k
		if(k == pivotPosition)  
			return arr[k];   
		// if positon is more than k, recursive left
		else if(k < pivotPosition) 
			return quickSortMM(arr, low, pivotPosition - 1, k);  
		// if position is less than k, recursive right
		else   			
			return quickSortMM(arr, pivotPosition + 1, high, k); 
	}

	// find the median of median as pivot point for QuickSort
	private static int medianOfMedian(int arr[], int low, int high) {
		// add 1 to high for easy calculation
		high+=1;
		
		// if 5 or less element then sort the array and return median
		if(high - low <= 5){       
			return findMedian(arr, low, high);
		}
		
		// create an array to store list median
		int[] arrayOfMedian; 
		if (high % 5 == 0)
			arrayOfMedian = new int[high/5];
		else 
			arrayOfMedian = new int[high/5 + 1];
		
		// sorted every 5 elements of the array and find the median
		int i = 0;
		int mm = 0;
		while (i+5 < high) {
			arrayOfMedian[mm] = findMedian(arr, i, i + 5);
			i+=5;
			mm++;
		}
		
		// sorted the remaining element of the array and find the median
		if (high % 5 != 0) {
			arrayOfMedian[mm] = findMedian(arr, i, arr.length);	
		}
		
		// find the median of median
		int medianOfMedian = findMedian(arrayOfMedian, 0, arrayOfMedian.length);	
		return medianOfMedian;
	}
	
	// find the median from given low to high indexes of array
	private static int findMedian(int arr[], int low, int high) {
		// sorted the given part of the array
		Arrays.sort(arr, low, high);
		
		// return the median of the sorted part
		int med = (high-low)/2;
		return arr[med+low];
	}
	
	// partition using the MM as pivot
	private static int partitionMM(int arr[], int low, int high, int med) { 
		// search for MM in array and swap its position with last element
		for (int x = low; x < high; x++) {
	        if (arr[x] == med) {
	        	 swap(arr, x, high); 
	        	 break; 
	        }
		}  
	   
		// partitions the array around last element
		int v = arr[high]; 
	    int j = low; 
	   
	    for (int i = low; i <= high; i++) { 
	        if (arr[i] < v) { 
	            swap(arr, i, j); 
	            j++; 
	        } 
	    } 
	    swap(arr, high, j); 
	    return j;
	} 
}
