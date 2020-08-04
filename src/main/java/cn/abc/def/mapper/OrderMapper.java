package cn.abc.def.mapper;

import cn.abc.def.entity.Order;

public interface OrderMapper {

	Integer add(Order order);
	
	Integer update(Order order);

	Order getById(String orderId);
	
	Order getByOrderNo(String orderNo);
}
