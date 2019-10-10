package com.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bean.ClassInfo;
import com.bean.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.util.JdbcUtil;

public class TestJson {
	public static void main(String[] args) {
		// 对象转为json字符串
		TestJson.ObjectToJson();
		TestJson.MapToJson();
		TestJson.ListToJson();

		System.out.println("==========================");

		// json字符串转为对象
		TestJson.strToObject();
		TestJson.strToMap();
		TestJson.strToList();
	}

	private static void strToObject() {
		String str = "{\"userid\":1,\"username\":\"admin\",\"password\":\"123\",\"truename\":\"真实姓名\",\"address\":\"福州市\",\"is_admin\":0}";

		Gson gson = new Gson();
		UserBean userBean = gson.fromJson(str, UserBean.class);

		System.out.println(userBean.getUserid());
		System.out.println(userBean.getUsername());
		System.out.println(userBean.getPassword());
		System.out.println(userBean.getTruename());

	}

	private static void strToMap() {
		String str = "{\"dog_id\":\"1\",\"dog_name\":\"名称_1\",\"dog_color\":\"blue\"}";
		Gson gson = new Gson();
		Map<String, Object> dataMap = gson.fromJson(str, Map.class);

		System.out.println(dataMap.get("dog_id"));
		System.out.println(dataMap.get("dog_name"));
		System.out.println(dataMap.get("dog_color"));

	}

	private static void strToList() {
		String str = "[\"111\",\"222\",\"333\",\"444\"]";
		Gson gson = new Gson();

		List<String> strList = gson.fromJson(str, List.class);

		for (String tempStr : strList) {
			System.out.println(tempStr);
		}

		String str_2 = "[{\"classid\":3,\"classname\":\"NOKIA\",\"parentid\":1},{\"classid\":4,\"classname\":\"IPhone4\",\"parentid\":1}]";

		Type type = new TypeToken<List<ClassInfo>>() {
		}.getType();
		List<ClassInfo> classList = gson.fromJson(str_2, type);

		for (ClassInfo classInfo : classList) {
			System.out.println(classInfo.getClassname() + "\t" +classInfo.getClassid());
		}

	}

	private static void ObjectToJson() {
		UserBean userBean = new UserBean();
		userBean.setUserid(1);
		userBean.setUsername("admin");
		userBean.setPassword("123");
		userBean.setTruename("真实姓名");
		userBean.setAddress("福州市");

		Gson gson = new Gson();
		String jsonStr = gson.toJson(userBean);

		System.out.println(jsonStr);

	}

	private static void MapToJson() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("dog_id", "1");
		map.put("dog_name", "名称_1");
		map.put("dog_color", "blue");

		Gson gson = new Gson();
		String jsonStr = gson.toJson(map);

		System.out.println(jsonStr);
	}

	private static void ListToJson() {
		// 字符串的集合
		List<String> strList = new ArrayList<String>();
		strList.add("111");
		strList.add("222");
		strList.add("333");
		strList.add("444");

		// 对象的集合
		List<ClassInfo> classList = null;
		String sql = "Select * From T_Classinfo where parentid != 0";
		try {
			classList = JdbcUtil.executeQuery2(sql, ClassInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Gson gson = new Gson();
		String strListJson = gson.toJson(strList);

		System.out.println(strListJson);

		String objectListJson = gson.toJson(classList);

		System.out.println(objectListJson);

	}

}
