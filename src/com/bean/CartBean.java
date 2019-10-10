package com.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartBean {
	private int totalcount = 0;
	private BigDecimal totalprice = new BigDecimal(0.0);
	private List<ItemBean> itemlist = new ArrayList<ItemBean>();
	private String totalprice_str;

	public List<ItemBean> getItemlist() {
		return itemlist;
	}

	public void setItemlist(List<ItemBean> itemlist) {
		this.itemlist = itemlist;
	}

	public BigDecimal getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	public String getTotalprice_str() {
		return totalprice_str;
	}

	public void setTotalprice_str(String totalprice_str) {
		this.totalprice_str = totalprice_str;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

}
