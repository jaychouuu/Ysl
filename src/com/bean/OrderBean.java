package com.bean;

import java.math.BigDecimal;

public class OrderBean {
	private int orderid;
	private String orderuser;
	private String in_time;
	private String paytype;
	private String sendtype;
	private int totalcount;
	private BigDecimal totalprice;
	private String review;
	private String message;
	private String checkuser;
	private String checkdate;
	private String receivename;
	private String receiveaddress;
	private String receivecode;
	private String receivephone;
	private String receivemail;
	private String username;

	public int getOrderid() {
		return orderid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public String getOrderuser() {
		return orderuser;
	}

	public void setOrderuser(String orderuser) {
		this.orderuser = orderuser;
	}

	public String getIn_time() {
		return in_time;
	}

	public void setIn_time(String in_time) {
		this.in_time = in_time;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getSendtype() {
		return sendtype;
	}

	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public BigDecimal getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCheckuser() {
		return checkuser;
	}

	public void setCheckuser(String checkuser) {
		this.checkuser = checkuser;
	}

	public String getCheckdate() {
		return checkdate;
	}

	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}

	public String getReceivename() {
		return receivename;
	}

	public void setReceivename(String receivename) {
		this.receivename = receivename;
	}

	public String getReceiveaddress() {
		return receiveaddress;
	}

	public void setReceiveaddress(String receiveaddress) {
		this.receiveaddress = receiveaddress;
	}

	public String getReceivecode() {
		return receivecode;
	}

	public void setReceivecode(String receivecode) {
		this.receivecode = receivecode;
	}

	public String getReceivephone() {
		return receivephone;
	}

	public void setReceivephone(String receivephone) {
		this.receivephone = receivephone;
	}

	public String getReceivemail() {
		return receivemail;
	}

	public void setReceivemail(String receivemail) {
		this.receivemail = receivemail;
	}

}
