package com.service.impl;

import java.util.List;

import com.bean.ClassInfo;
import com.dao.ClassInfoDao;
import com.service.ClassInfoService;

public class ClassInfoServiceImpl implements ClassInfoService {

	private ClassInfoDao infoDao = new ClassInfoDao();

	@Override
	public List<ClassInfo> getList() throws Exception {
		return infoDao.getlist();
	}

	@Override
	public void save(ClassInfo classInfo) throws Exception {
		// TODO Auto-generated method stub

		String classname = classInfo.getClassname();
		if (classname == null || classname.equals("")) {

			throw new Exception("类别名称不能为空");

		}
		infoDao.save(classInfo);

	}

	@Override
	public void change(ClassInfo classInfo, ClassInfo oldclassInfo) throws Exception {
		// TODO Auto-generated method stub

		infoDao.change(classInfo, oldclassInfo);

	}

	@Override
	public void delete(String classid, String parentid) throws Exception {

		infoDao.delete(classid, parentid);

	}

	@Override
	public List<ClassInfo> getBigList() throws Exception {

		return infoDao.getBigList();
	}

	@Override
	public List<ClassInfo> getSmallList(int bigid) throws Exception {
		return infoDao.getSmallList(bigid);
	}

}
