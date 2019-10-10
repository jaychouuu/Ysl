package com.service;

import java.util.List;
import java.util.Map;

import com.bean.ItemBean;

public interface ItemService {

	List<ItemBean> getItemList(Map<String, String> paramMap, int startIndex, int endIndex);

	int getItemCount(Map<String, String> paramMap);

	void save(Map<String, String> valueMap) throws Exception;

	ItemBean getItemBeanByID(String itemid);

	void modify(Map<String, String> valueMap) throws Exception;

	void delete(String itemid) throws Exception;

}
