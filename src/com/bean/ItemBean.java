package com.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemBean implements Serializable {

	private int itemid;
	private String itemname;
	private BigDecimal itemprice;
	private String filepath;
	private String filename;
	private int bigclass;
	private int smallclass;
	private String addtime;
	private String temp;
	private int isdel;
	private String smallname;
	private String bigname;
	private BigDecimal sumprice;
	private int count;
	private String sumprice_str;

	public String getSumprice_str() {
		return sumprice_str;
	}

	public void setSumprice_str(String sumprice_str) {
		this.sumprice_str = sumprice_str;
	}

	public BigDecimal getSumprice() {
		return sumprice;
	}

	public void setSumprice(BigDecimal sumprice) {
		this.sumprice = sumprice;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getSmallname() {
		return smallname;
	}

	public void setSmallname(String smallname) {
		this.smallname = smallname;
	}

	public String getBigname() {
		return bigname;
	}

	public void setBigname(String bigname) {
		this.bigname = bigname;
	}

	public int getBigclass() {
		return bigclass;
	}

	public void setBigclass(int bigclass) {
		this.bigclass = bigclass;
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

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public int getIsdel() {
		return isdel;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}

	public int getSmallclass() {
		return smallclass;
	}

	public void setSmallclass(int smallclass) {
		this.smallclass = smallclass;
	}

}
