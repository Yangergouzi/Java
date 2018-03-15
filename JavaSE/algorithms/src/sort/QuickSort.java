package sort;

import java.util.Scanner;

public class QuickSort {
	static int[] numArray;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("please input a list of number:");
		String str = sc.nextLine();
		String[] strings = str.split("\\s+");
		numArray = new int[strings.length];
		for(int i=0;i<strings.length;i++) {
			try {
				int num = Integer.parseInt(strings[i]);
				numArray[i] = num;
			}catch (Exception e) {
				e.printStackTrace();
			}		
		}
		QuickSort qs = new QuickSort();
		qs.quickSort(0, numArray.length - 1);
		for(int num : numArray) {
			System.out.print(num + " ");
		}
	}
	
	void quickSort(int left,int right) {
		int flag;
		int temp;
		int i = left;
		int j = right;
		if(left > right || left >= numArray.length) {
			return;
		}
		flag = numArray[i];
		while(left < right) {
			while(numArray[right] >= flag && right > 0) {
				right--;
			}
			while(numArray[left] <= flag && left < j) {
				left++;
			}
			if(left < right) {
				temp = numArray[left];
				numArray[left] = numArray[right];
				numArray[right] = temp;
			}
			else  {
				numArray[i] = numArray[right];
				numArray[right] = flag;
			}
		}
		
		//继续处理左边和右边		
		quickSort(i, right - 1);
		quickSort(right + 1, j);
	}
	
}
