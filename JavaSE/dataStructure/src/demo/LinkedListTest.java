package demo;

import java.util.Scanner;

import javax.xml.transform.Templates;

/*
 * 单向链表
 */
public class LinkedListTest {
	public static void main(String[] args) {
		LinkedListTest test = new LinkedListTest();
	//	test.newListTest();
	//	test.midInsertTest();
		test.deleteTest();
	}
	/**
	 * 新建链表测试
	 */
	public void newListTest() {
		System.out.println("请输入一串数据，以空格分开：");
		Scanner sc  = new Scanner(System.in);
		String str = sc.nextLine();
		while(!str.matches("(\\d+\\s*)+")) {
			System.out.println("inpu error!please input again:");
			str = sc.nextLine();
		}
		String[] strs = str.split("\\s+");
		//生成链表
		SinglyLinkedList<String> list = new SinglyLinkedList<String>(new Node<String>(strs[0]));//新建链表
		Node<String> lastNode = list.head;
		Node<String> newNode;
		for(int i=1;i<strs.length;i++) {//循环插入结点
			newNode = new Node<String>(strs[i]);
			list.insert(newNode, lastNode);
			lastNode = newNode;
		}
		System.out.println("新建链表的数据：");
		System.out.println(list.toString());//输出链表	
	}
	/**
	 * 插入链表测试
	 */
	public void midInsertTest() {
		int[] a = {2,5,6,8,10,16};
		//生成链表
		Node<Integer> head = new Node<Integer>(a[0]);
		SinglyLinkedList<Integer> linkedList = new SinglyLinkedList<Integer>(head);
		Node<Integer> lastnode = head;
		for(int i = 1;i < a.length;i++) {
			linkedList.insert(new Node<Integer>(a[i]), lastnode);
			lastnode = lastnode.next;
		}
		System.out.println("插入前链表：" + linkedList.toString());
		//新节点顺序插入链表
		Node<Integer> newnode = new Node<Integer>(9);
		linkedList.temp = linkedList.head;
		while(linkedList.temp != null) {//遍历链表
			 if(linkedList.temp.next.data > newnode.data){//如果当前结点的后继结点值大于新增结点值
				linkedList.insert(newnode, linkedList.temp);//新节点插入当前结点之后
				break;
			}
			 linkedList.temp = linkedList.temp.next;//当前结点指针后移
		}
		System.out.println("插入后链表：" + linkedList.toString());
	}
	/**
	 * 删除测试
	 */
	public void deleteTest() {
		int[] a = {2,5,6,8,10,16};
		//生成链表
		Node<Integer> head = new Node<Integer>(a[0]);
		SinglyLinkedList<Integer> linkedList = new SinglyLinkedList<Integer>(head);
		Node<Integer> lastnode = head;
		for(int i = 1;i < a.length;i++) {
			linkedList.insert(new Node<Integer>(a[i]), lastnode);
			lastnode = lastnode.next;
		}
		System.out.println("删除前的链表：" + linkedList.toString());
		//删除值为10的结点
		linkedList.temp = linkedList.head;
		while(linkedList.temp != null) {
			if(linkedList.temp.next.data == 10) {
				linkedList.delete(linkedList.temp.next, linkedList.temp);
				break;
			}
			linkedList.temp = linkedList.temp.next;
		}
		System.out.println("删除后的链表：" + linkedList.toString());
	}
	
}
//链表结点
class Node<T>{
	T data;
	Node<T> next = null;
	public Node(T data) {
		this.data = data;
	}
	
}

/**
 * @author yang
 * 单向链表
 */
class SinglyLinkedList<T>{
	Node<T> head;//链表的第一个结点
	Node<T> temp;//定义结点指针
	
	public SinglyLinkedList(Node<T> head) {
		this.head = head;
		this.temp = this.head;
	}
	/**
	 * 插入结点
	 * @param newNode（当前待插入的结点）
	 * @param lastPlace（要插入位置的上一个结点）
	 * @return
	 */
	public  boolean insert(Node<T> newNode,Node<T> lastPlace) {
		try {
		newNode.next =  lastPlace.next; //新增结点的后继结点指向上一个结点位置的后继结点
		lastPlace.next =  newNode;//上一个结点位置的后继节点指向新增结点
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 删除结点
	 * @param delNode
	 * @param lastNode
	 * @return
	 */
	public boolean delete(Node<T> delNode,Node<T> lastNode) {
		try {
			lastNode.next = delNode.next;//上个结点的后继结点指向待删除结点的后继结点
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
