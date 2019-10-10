package com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClassInfo implements Serializable {
	private int classid;
	private String classname;
	private int parentid;

	private List<ClassInfo> childList = new ArrayList<ClassInfo>();

	public int getClassid() {
		return classid;
	}

	public void setClassid(int classid) {
		this.classid = classid;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public List<ClassInfo> getChildList() {
		return childList;
	}

	public void setChildList(List<ClassInfo> childList) {
		this.childList = childList;
	}

}
