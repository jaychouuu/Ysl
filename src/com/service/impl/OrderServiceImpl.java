package com.service.impl;

import java.util.List;
import java.util.Map;

import com.bean.ItemBean;
import com.bean.OrderBean;
import com.bean.OrderDetail;
import com.dao.OrderDao;
import com.service.OrderService;

public class OrderServiceImpl implements OrderService {

	@Override
	public List<OrderBean> getAllorder() throws Exception {
		return OrderDao.getAllorder();
	}

	@Override
	public int getOrderCount(Map<String, String> paramMap) {

		return OrderDao.getOrderCount(paramMap);
	}

	@Override
	public List<OrderBean> getOrderList(Map<String, String> paramMap, int startIndex, int endIndex) {
		return OrderDao.getOrderList(paramMap, startIndex, endIndex);
	}

	@Override
	public List<OrderDetail> getdetailList(String orderid) throws Exception {
		return OrderDao.getdetailList(orderid);
	}

	@Override
	public void tofail(String orderid) throws Exception {

		OrderDao.tofail(orderid);
	}

	@Override
	public void topass(String orderid) throws Exception {
		OrderDao.topass(orderid);

	}

	@Override
	public void saveOrder(List<ItemBean> itemlist, OrderBean orderBean) throws Exception {
		OrderDao.saveOrder(itemlist, orderBean);
	}

}
