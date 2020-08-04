package cn.abc.def.service;

import cn.abc.def.entity.Order;

public interface IOrderService {

	Integer addOrder(Order order);
	
	Integer update(Order order);

	Order getById(String orderId);
	
	Order getByOrderNo(String orderNo);
	
	/**
	 * 支付成功的逻辑
	 * @param orderNo 订单号
	 * @param flowNo 第三方订单号
	 * @param payType 支付方式
	 * @return true处理成功, false处理失败
	 */
	boolean thirdPaySuccess(String orderNo, String flowNo);
	
	void thirdPayFail(String orderNo, String flowNo);
}
