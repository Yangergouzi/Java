package com.yang.crm.pager;

import java.util.List;

/*
 * �����������ҳ��ص���Ϣ
 * 1.��ǰҳ��pageCurrent
 * 2.�ܼ�¼��totalRecord
 * 3.ÿҳ��¼��pageSize
 * 4.��ҳ��totalPage(��ǰ�����õ�)
 * 5.�����·������������ҳ������Ҫʹ������Ϊ�����ӵ�Ŀ��
 * 6.ÿҳ�����ݶ���beanList
 */
public class PageBean<T> {
	private int pageCurr;
	private int totalRec;
	private int pageSize;
	private String url;
	private List<T> beanList;
	
	//������ҳ��
	public int getTotalPage(){
		int tp = totalRec/pageSize;
		return totalRec % pageSize == 0 ? tp : (tp + 1);
	}

	public int getPageCurr() {
		return pageCurr;
	}

	public void setPageCurr(int pageCurr) {
		this.pageCurr = pageCurr;
	}

	public int getTotalRec() {
		return totalRec;
	}

	public void setTotalRec(int totalRec) {
		this.totalRec = totalRec;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<T> getBeanList() {
		return beanList;
	}

	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}
	
	
}
