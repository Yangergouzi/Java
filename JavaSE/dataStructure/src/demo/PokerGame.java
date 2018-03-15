/*
 * 纸牌游戏：小猫钓鱼
 * 判断该游戏最终赢家
 * 要用到两种数据结构：队列和栈
 * 玩家手中的牌为队列，队首出牌，队尾入牌
 * 桌上的牌为栈，栈顶入牌和出牌
 */
package demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PokerGame {
	public static void main(String[] args) {
		PokerGame game = new PokerGame();
		game.game();
	}
	
	public void game() {
		Scanner sc = new Scanner(System.in);
		String str;
		String[] ss;
		ArrayList<int[]> playersnum = new ArrayList<int[]>();
		//输入和初始化玩家牌面
		for(int i=1;i<3;i++) {
			System.out.println("input player" + i +"'s number:");//player1:2 4 1 2 5 6  player2:3 1 3 5 6 4
			str = sc.nextLine();
			ss = str.split("\\s+");
			int[] player = new int[ss.length];
			for(int j = 0;j < ss.length;j++) {
				player[j] = Integer.parseInt(ss[j]);
			}
			playersnum.add(player);
		}
		
		//初始化玩家和桌面
		Map<Integer,Queue> players = new HashMap<Integer,Queue>();
		Queue player1 = new Queue();
		Queue player2 = new Queue();
		player1.addDataToTail(playersnum.get(0));
		player2.addDataToTail(playersnum.get(1));
		players.put(1, player1);
		players.put(2, player2);
		Stack table = new Stack();

		//模拟游戏过程
		while(players.size() > 1) {
			for(Map.Entry<Integer, Queue> player : players.entrySet()) {
				int poker = player.getValue().deleteHeadData();//玩家出牌
				int index = table.contains(poker);//判断出牌前桌面是否有相同的牌
				table.push(poker);
				if(index > 0) {//如果之前桌面有，收牌
					int[] obtainpokers =  table.pop(index);
		/*			for (int start = 0, end = obtainpokers.length - 1; start < end; start++, end--) {//倒序处理
				        int temp = obtainpokers[end];
				        obtainpokers[end] = obtainpokers[start];
				        obtainpokers[start] = temp;
				    }*/
					player.getValue().addDataToTail(obtainpokers);//玩家收牌
				}
				if(player.getValue().isEmpty()) {//如果该玩家没牌，该玩家退出
					players.remove(player.getKey());
					if(players.size() == 1) {
						break;
					}
				}
			}
		}
		for(Map.Entry<Integer, Queue> player : players.entrySet()) {
			System.out.println("the winner is player" + player.getKey() + " and his pokers are " + player.getValue().toString());
		}
		System.out.println("the pokers on the table are " + table.toString());
	}
	

}
//队列数据类，head指向队列第一个元素，tail指向最后一个元素的下一位置
class Queue{
	private int[] q = new int[1000];
	private int head;
	private int tail;
	
	private int deleteData;

	//删除队首
	public int deleteHeadData() {
		if(head < tail) {
			deleteData = q[head];
			head++;
			return deleteData;
		}
		return -1;
	}
	//添加数据到队尾
	public void addDataToTail(int[] addData) {
		for(int d : addData) {
			q[tail] = d;
			tail++;
		}
	}
	//判断是否为空
	public boolean isEmpty() {
		return head == tail;
	}
	//toString
	@Override
	public String toString() {
		StringBuffer sBuffer = new StringBuffer();
		for(int i = head;i<tail;i++) {
			sBuffer = sBuffer.append(q[i] + " ");
		}
		return sBuffer.toString();
	}
	//Getters
	public int[] getQ() {
		return q;
	}
	public int getHead() {
		return head;
	}
	public int getTail() {
		return tail;
	}
	
	
	
}
//栈数据类
class Stack{
	private int[] s = new int[50];
	private int top;
	
	private int[] popData;
	//出栈
	int i;
	public int[] pop(int subscript) {
		i = 0;
		popData = new int[top - subscript + 1];
		while(top >=  subscript) {
			popData[i++] = s[top--];
		}
		return popData;
	}
	//入栈
	public void push(int data) {
		s[++top] = data;
	}
	//是否包含某数，是则输出下标，否则输出-1
	public int contains(int data) {
		for(int i=1;i<=top;i++) {
			if(s[i] == data) {
				return i;
			}
		}
		return -1;
	}
	//toString
	@Override
	public String toString() {
		StringBuffer sBuffer = new StringBuffer();
		for(int i=1;i<=top;i++) {
			sBuffer = sBuffer.append(s[i] + " ");
		}
		return sBuffer.toString();
	}
	//Getters
	public int[] getS() {
		return s;
	}
	public int getTop() {
		return top;
	}
	
}
