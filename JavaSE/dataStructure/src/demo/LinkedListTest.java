package demo;

import java.util.Scanner;

import javax.xml.transform.Templates;

/*
 * ��������
 */
public class LinkedListTest {
	public static void main(String[] args) {
		LinkedListTest test = new LinkedListTest();
	//	test.newListTest();
	//	test.midInsertTest();
		test.deleteTest();
	}
	/**
	 * �½��������
	 */
	public void newListTest() {
		System.out.println("������һ�����ݣ��Կո�ֿ���");
		Scanner sc  = new Scanner(System.in);
		String str = sc.nextLine();
		while(!str.matches("(\\d+\\s*)+")) {
			System.out.println("inpu error!please input again:");
			str = sc.nextLine();
		}
		String[] strs = str.split("\\s+");
		//��������
		SinglyLinkedList<String> list = new SinglyLinkedList<String>(new Node<String>(strs[0]));//�½�����
		Node<String> lastNode = list.head;
		Node<String> newNode;
		for(int i=1;i<strs.length;i++) {//ѭ��������
			newNode = new Node<String>(strs[i]);
			list.insert(newNode, lastNode);
			lastNode = newNode;
		}
		System.out.println("�½���������ݣ�");
		System.out.println(list.toString());//�������	
	}
	/**
	 * �����������
	 */
	public void midInsertTest() {
		int[] a = {2,5,6,8,10,16};
		//��������
		Node<Integer> head = new Node<Integer>(a[0]);
		SinglyLinkedList<Integer> linkedList = new SinglyLinkedList<Integer>(head);
		Node<Integer> lastnode = head;
		for(int i = 1;i < a.length;i++) {
			linkedList.insert(new Node<Integer>(a[i]), lastnode);
			lastnode = lastnode.next;
		}
		System.out.println("����ǰ����" + linkedList.toString());
		//�½ڵ�˳���������
		Node<Integer> newnode = new Node<Integer>(9);
		linkedList.temp = linkedList.head;
		while(linkedList.temp != null) {//��������
			 if(linkedList.temp.next.data > newnode.data){//�����ǰ���ĺ�̽��ֵ�����������ֵ
				linkedList.insert(newnode, linkedList.temp);//�½ڵ���뵱ǰ���֮��
				break;
			}
			 linkedList.temp = linkedList.temp.next;//��ǰ���ָ�����
		}
		System.out.println("���������" + linkedList.toString());
	}
	/**
	 * ɾ������
	 */
	public void deleteTest() {
		int[] a = {2,5,6,8,10,16};
		//��������
		Node<Integer> head = new Node<Integer>(a[0]);
		SinglyLinkedList<Integer> linkedList = new SinglyLinkedList<Integer>(head);
		Node<Integer> lastnode = head;
		for(int i = 1;i < a.length;i++) {
			linkedList.insert(new Node<Integer>(a[i]), lastnode);
			lastnode = lastnode.next;
		}
		System.out.println("ɾ��ǰ������" + linkedList.toString());
		//ɾ��ֵΪ10�Ľ��
		linkedList.temp = linkedList.head;
		while(linkedList.temp != null) {
			if(linkedList.temp.next.data == 10) {
				linkedList.delete(linkedList.temp.next, linkedList.temp);
				break;
			}
			linkedList.temp = linkedList.temp.next;
		}
		System.out.println("ɾ���������" + linkedList.toString());
	}
	
}
//������
class Node<T>{
	T data;
	Node<T> next = null;
	public Node(T data) {
		this.data = data;
	}
	
}

/**
 * @author yang
 * ��������
 */
class SinglyLinkedList<T>{
	Node<T> head;//����ĵ�һ�����
	Node<T> temp;//������ָ��
	
	public SinglyLinkedList(Node<T> head) {
		this.head = head;
		this.temp = this.head;
	}
	/**
	 * ������
	 * @param newNode����ǰ������Ľ�㣩
	 * @param lastPlace��Ҫ����λ�õ���һ����㣩
	 * @return
	 */
	public  boolean insert(Node<T> newNode,Node<T> lastPlace) {
		try {
		newNode.next =  lastPlace.next; //�������ĺ�̽��ָ����һ�����λ�õĺ�̽��
		lastPlace.next =  newNode;//��һ�����λ�õĺ�̽ڵ�ָ���������
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * ɾ�����
	 * @param delNode
	 * @param lastNode
	 * @return
	 */
	public boolean delete(Node<T> delNode,Node<T> lastNode) {
		try {
			lastNode.next = delNode.next;//�ϸ����ĺ�̽��ָ���ɾ�����ĺ�̽��
			delNode = null;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuffer sBuffer = new StringBuffer();
		temp = this.head;
		while(temp != null) {
			sBuffer.append(temp.data.toString() + " ");
			temp = temp.next;
		}
		return sBuffer.toString();
	}
}
