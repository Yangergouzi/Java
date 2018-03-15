/*
 * ֽ����Ϸ��Сè����
 * �жϸ���Ϸ����Ӯ��
 * Ҫ�õ��������ݽṹ�����к�ջ
 * ������е���Ϊ���У����׳��ƣ���β����
 * ���ϵ���Ϊջ��ջ�����ƺͳ���
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
		//����ͳ�ʼ���������
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
		
		//��ʼ����Һ�����
		Map<Integer,Queue> players = new HashMap<Integer,Queue>();
		Queue player1 = new Queue();
		Queue player2 = new Queue();
		player1.addDataToTail(playersnum.get(0));
		player2.addDataToTail(playersnum.get(1));
		players.put(1, player1);
		players.put(2, player2);
		Stack table = new Stack();

		//ģ����Ϸ����
		while(players.size() > 1) {
			for(Map.Entry<Integer, Queue> player : players.entrySet()) {
				int poker = player.getValue().deleteHeadData();//��ҳ���
				int index = table.contains(poker);//�жϳ���ǰ�����Ƿ�����ͬ����
				table.push(poker);
				if(index > 0) {//���֮ǰ�����У�����
					int[] obtainpokers =  table.pop(index);
		/*			for (int start = 0, end = obtainpokers.length - 1; start < end; start++, end--) {//������
				        int temp = obtainpokers[end];
				        obtainpokers[end] = obtainpokers[start];
				        obtainpokers[start] = temp;
				    }*/
					player.getValue().addDataToTail(obtainpokers);//�������
				}
				if(player.getValue().isEmpty()) {//��������û�ƣ�������˳�
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
//���������࣬headָ����е�һ��Ԫ�أ�tailָ�����һ��Ԫ�ص���һλ��
class Queue{
	private int[] q = new int[1000];
	private int head;
	private int tail;
	
	private int deleteData;

	//ɾ������
	public int deleteHeadData() {
		if(head < tail) {
			deleteData = q[head];
			head++;
			return deleteData;
		}
		return -1;
	}
	//������ݵ���β
	public void addDataToTail(int[] addData) {
		for(int d : addData) {
			q[tail] = d;
			tail++;
		}
	}
	//�ж��Ƿ�Ϊ��
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
//ջ������
class Stack{
	private int[] s = new int[50];
	private int top;
	
	private int[] popData;
	//��ջ
	int i;
	public int[] pop(int subscript) {
		i = 0;
		popData = new int[top - subscript + 1];
		while(top >=  subscript) {
			popData[i++] = s[top--];
		}
		return popData;
	}
	//��ջ
	public void push(int data) {
		s[++top] = data;
	}
	//�Ƿ����ĳ������������±꣬�������-1
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
