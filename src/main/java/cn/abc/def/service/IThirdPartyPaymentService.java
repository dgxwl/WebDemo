package cn.abc.def.service;

import cn.abc.def.domain.ResponseResult;
import com.alipay.api.AlipayApiException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 三方支付service
 */
public interface IThirdPartyPaymentService {

    ResponseResult createTransForWeixin(String commodityId, String tradeType, HttpServletRequest request) throws IOException;

    String weixinNotify(HttpServletRequest request);

    ResponseResult createTransForAlipay(String commodityId, String tradeType) throws AlipayApiException;

    String alipayNotify(HttpServletRequest request);

    ResponseResult getOrderStatus(String orderNo);
}
