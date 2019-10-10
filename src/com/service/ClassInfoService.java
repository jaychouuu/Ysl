package com.service;

import java.util.List;

import com.bean.ClassInfo;
import com.dao.ClassInfoDao;

public interface ClassInfoService {

	public List<ClassInfo> getList() throws Exception;

	public List<ClassInfo> getBigList() throws Exception;

	public List<ClassInfo> getSmallList(int bigid) throws Exception;

	public void save(ClassInfo classInfo) throws Exception;

	public void change(ClassInfo classInfo, ClassInfo oldclassInfo) throws Exception;

	public void delete(String classid, String parentid) throws Exception;

	public static int getClassListCount() {
		return ClassInfoDao.getCount();
	}

	public static List<ClassInfo> getClassInfoList(int startIndex, int endIndex) throws Exception {
		return ClassInfoDao.getClassInfoList(startIndex, endIndex);
	}

}
