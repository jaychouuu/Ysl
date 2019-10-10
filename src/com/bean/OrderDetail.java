package com.bean;

import java.math.BigDecimal;

public class OrderDetail {

	private String detailid;
	private int orderid;
	private int itemid;
	private String itemname;
	private BigDecimal itemprice;
	private int itemcount;
	private BigDecimal sumprice;

	public String getDetailid() {
		return detailid;
	}

	public void setDetailid(String detailid) {
		this.detailid = detailid;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getItemid() {
		return itemid;
	}

	public void setItemid(int itemid) {
		this.itemid = itemid;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public BigDecimal getItemprice() {
		return itemprice;
	}

	public void setItemprice(BigDecimal itemprice) {
		this.itemprice = itemprice;
	}

	public int getItemcount() {
		return itemcount;
	}

	public void setItemcount(int itemcount) {
		this.itemcount = itemcount;
	}

	public BigDecimal getSumprice() {
		return sumprice;
	}

	public void setSumprice(BigDecimal sumprice) {
		this.sumprice = sumprice;
	}

}
