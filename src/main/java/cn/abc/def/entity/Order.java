package cn.abc.def.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
	
	/**微信支付*/
	public static final int PAY_TYPE_WEIXIN = 0;
	
	/**付款状态_预支付*/
	public static final int STATUS_PENDING = 0;
	/**付款状态_支付失败*/
	public static final int STATUS_FAILD = 1;
	/**付款状态_支付完成*/
	public static final int STATUS_FINISHED = 2;

	private String orderNo;
	private String commodityId;
	private BigDecimal amount;
	private Integer payType;
	private Integer status;
	private String flowNo;
	private Date payDate;
	private Date createdDate;
	private String createdBy;
	
	public Order() {}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
