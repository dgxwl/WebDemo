package cn.abc.def.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cn.abc.def.util.AlipayCore;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Description：微信预支付请求参数
 * @author lijianwei
 */
@XStreamAlias("xml")
public class WXPrePayEntity implements Serializable {
	private static final long serialVersionUID = -7712257468736467291L;
	private String appid;// 微信分配的公众账号ID（企业号corpid即为此appId）
	private String mch_id;// 微信支付分配的商户号
	private String device_info;// 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
	private String nonce_str;// 随机字符串，不长于32位。
	private String sign;// 签名
	private String body;// 商品或支付单简要描述
	private String detail;// 商品名称明细列表
	private String attach;// 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
	private String out_trade_no;// 商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
	private String fee_type;// 符合ISO 4217标准的三位字母代码，默认人民币：CNY，
	private String total_fee;// 订单总金额，只能为整数，
	private String spbill_create_ip;// APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
	private String time_start;// 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。
	private String time_expire;// 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则注意：最短失效时间间隔必须大于5分钟
	private String goods_tag;// 商品标记，代金券或立减优惠功能的参数
	private String notify_url;// 接收微信支付异步通知回调地址
	private String trade_type;// 取值如下：JSAPI，NATIVE，APP，WAP,
	private String product_id;// trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
	private String limit_pay;// no_credit--指定不能使用信用卡支付
	private String openid;// 用户标识
	
	/**
     * 生成微信支付的签名
     */
    public String createPaySign(String weixinApiKey) {
        try {
            //将bean转成map
            Map<String, String> beanMap = AlipayCore.convertBean(this);
            //去掉空值
            Map<String, String> resultMap = AlipayCore.paraFilter(beanMap);
            String string1 = AlipayCore.createLinkString(resultMap);
            String stringSignTemp = string1 + "&key=" + weixinApiKey;
            return DigestUtils.md5Hex(stringSignTemp).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getLimit_pay() {
		return limit_pay;
	}

	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
