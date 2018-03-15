package com.yang.crm.pager;

import java.util.List;

/*
 * 保存所有与分页相关的信息
 * 1.当前页码pageCurrent
 * 2.总记录数totalRecord
 * 3.每页记录数pageSize
 * 4.总页数totalPage(由前两个得到)
 * 5.请求的路径及参数，分页导航需要使用其作为超链接的目标
 * 6.每页的数据对象beanList
 */
public class PageBean<T> {
	private int pageCurr;
	private int totalRec;
	private int pageSize;
	private String url;
	private List<T> beanList;
	
	//计算总页数
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
