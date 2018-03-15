package sort;

import java.util.Scanner;

public class BubbleSort {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("please input a list of number:");
		String str = sc.nextLine();
		String[] ss = str.split("\\s+");
		int[] nums = new int[ss.length];
		for(int t=0;t<ss.length;t++) {
			nums[t] = Integer.parseInt(ss[t]);
		}
		int len = nums.length;
		int temp;
		for(int i=0;i<len-1;i++) {
			for(int j = 1; j < len-i;j++) {
				if(nums[j-1] < nums[j]) {
					temp = nums[j-1];
					nums[j-1] = nums[j];
					nums[j] = temp;
				}
			}
		}
		for(int num : nums) {
			System.out.print(num + " ");
		}
	}
}
