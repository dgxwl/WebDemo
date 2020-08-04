package cn.abc.def.service;

import cn.abc.def.entity.Order;
import cn.abc.def.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class OrderService implements IOrderService {
	
	@Resource
	OrderMapper orderMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Integer addOrder(Order order) {
		return orderMapper.add(order);
	}
	
	@Override
	public Integer update(Order order) {
		return orderMapper.update(order);
	}

	@Override
	public Order getById(String orderId) {
		return orderMapper.getById(orderId);
	}
	
	@Override
	public Order getByOrderNo(String orderNo) {
		return orderMapper.getByOrderNo(orderNo);
	}

	@Transactional
	@Override
	public boolean thirdPaySuccess(String orderNo, String flowNo) {
		//更新订单状态
		Order order = orderMapper.getByOrderNo(orderNo);
		if (order == null) {
			return false;
		}
		order.setStatus(Order.STATUS_FINISHED);
		order.setFlowNo(flowNo);
		Integer result = orderMapper.update(order);
		
		//TODO 这里写订单支付完成后的逻辑
		
		return result > 0;
	}
	
	@Override
	public void thirdPayFail(String orderNo, String flowNo) {
		//更新订单状态
		Order order = orderMapper.getByOrderNo(orderNo);
		if (order == null) {
			logger.debug("找不到订单");
			return;
		}
		order.setStatus(Order.STATUS_FAILD);
		order.setFlowNo(flowNo);
		orderMapper.update(order);
	}
}
