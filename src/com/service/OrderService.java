package com.service;

import java.util.List;
import java.util.Map;

import com.bean.ItemBean;
import com.bean.OrderBean;
import com.bean.OrderDetail;

public interface OrderService {

	List<OrderBean> getAllorder() throws Exception;

	int getOrderCount(Map<String, String> paramMap);

	List<OrderBean> getOrderList(Map<String, String> paramMap, int startIndex, int endIndex);

	List<OrderDetail> getdetailList(String orderid) throws Exception;

	void tofail(String orderid) throws Exception;

	void topass(String orderid) throws Exception;

	void saveOrder(List<ItemBean> itemlist, OrderBean orderBean) throws Exception;

}
