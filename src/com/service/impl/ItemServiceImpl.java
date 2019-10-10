package com.service.impl;

import java.util.List;
import java.util.Map;

import com.bean.ItemBean;
import com.dao.ItemDao;
import com.service.ItemService;

public class ItemServiceImpl implements ItemService {
	private ItemDao itemDao = new ItemDao();

	@Override
	public List<ItemBean> getItemList(Map<String, String> paramMap, int startIndex, int endIndex) {
		return ItemDao.getItemList(paramMap, startIndex, endIndex);

	}

	@Override
	public int getItemCount(Map<String, String> paramMap) {

		return itemDao.getItemCount(paramMap);
	}

	@Override
	public void save(Map<String, String> valueMap) throws Exception {
		itemDao.save(valueMap);
	}

	@Override
	public ItemBean getItemBeanByID(String itemid) {

		if (itemid == null || itemid.equals("")) {
			throw new RuntimeException("id²»ÄÜÎª¿Õ");
		}
		return itemDao.getItemBeanByID(itemid);
	}

	@Override
	public void modify(Map<String, String> valueMap) throws Exception {

		itemDao.modify(valueMap);
	}

	@Override
	public void delete(String itemid) throws Exception {
		itemDao.delete(itemid);

	}
}
