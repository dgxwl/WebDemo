package cn.abc.def.controller;

import cn.abc.def.domain.ResponseResult;
import cn.abc.def.entity.Order;
import cn.abc.def.entity.WXPayNotifyUrlEntity;
import cn.abc.def.entity.WXPrePayEntity;
import cn.abc.def.entity.WXPrePayResultEntity;
import cn.abc.def.service.IOrderService;
import cn.abc.def.util.AlipayCore;
import cn.abc.def.util.DateUtil;
import cn.abc.def.util.HttpUtil;
import cn.abc.def.util.OrderUtil;
import cn.abc.def.util.PayUtil;
import cn.abc.def.util.QRCodeUtil;
import cn.abc.def.util.StringUtils;
import cn.abc.def.util.XmlUtil;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 三方支付对接demo
 * @author Administrator
 *
 */
@RestController
public class ThreePartyPaymentController {
	
	@Resource
	private IOrderService orderService;

    @Value("${server.url}")
    private String serverUrl;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${weixin.appid}")
    private String weixinAppid;
    @Value("${weixin.mch_id}")
    private String weixinMchid;
    @Value("${weixin.apiKey}")
    private String weixinApiKey;
    @Value("${weixin.notify_url}")
    private String weixinNotifyurl;
    /**
     * APP支付-微信预支付
     * @param commodityId 商品id
     */
    @RequestMapping("/createTransForWeixin")
    public ResponseResult createTransForWeixin(String commodityId, HttpServletRequest request) {
        try {
            //从数据库查询商品价格
            BigDecimal amount = /*commodityService.getById(commodityId).getAmount()*/new BigDecimal(0.01);  //TODO 商品价格

            //创建本次购买的订单号
            String orderNo = OrderUtil.newOrderNo(6);
            // 生成微信支付的预支付信息
            String noncestr = OrderUtil.getRamCode(20);
            WXPrePayEntity wxPrePayEntity = new WXPrePayEntity();
            wxPrePayEntity.setAppid(weixinAppid);
            wxPrePayEntity.setMch_id(weixinMchid);
            wxPrePayEntity.setNonce_str(noncestr);
            String description = "商品描述";  //TODO 写具体的商品名称
            wxPrePayEntity.setBody(description);
            wxPrePayEntity.setDetail(description);
            wxPrePayEntity.setAttach("");// 附加数据
            wxPrePayEntity.setOut_trade_no(orderNo);//商户系统内部的订单号,32个字符内、可包含字母,
            wxPrePayEntity.setFee_type("CNY");// rmb
            String totalFee = amount.multiply(new BigDecimal(100)).toPlainString();  //微信支付金额单位为 分
            int index = totalFee.lastIndexOf('.');
            if (index == -1) index = totalFee.length();
            totalFee = totalFee.substring(0, index);
            wxPrePayEntity.setTotal_fee(totalFee);  //总金额
            wxPrePayEntity.setSpbill_create_ip(request.getRemoteAddr());  //用户的ip
            wxPrePayEntity.setTime_start(DateUtil.getFormattedDate(new Date(), "yyyyMMddHHmmss"));  //交易起始时间
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.HOUR_OF_DAY, 2);
            wxPrePayEntity.setTime_expire(DateUtil.getFormattedDate(c.getTime(), "yyyyMMddHHmmss"));  //交易结束时间2小时有效期
            wxPrePayEntity.setTrade_type("APP");
            wxPrePayEntity.setNotify_url(weixinNotifyurl);
            String sign = wxPrePayEntity.createPaySign(weixinApiKey);
            wxPrePayEntity.setSign(sign);
            // 将bean转成xml
            String payXml = XmlUtil.toXml(wxPrePayEntity).replaceAll("__", "_");
            logger.debug(payXml);

            // 发送到微信
//            String postResult = HttpsPostUtil.post("https://api.mch.weixin.qq.com/pay/unifiedorder", payXml, "utf-8");
            String postResult = HttpUtil.post("https://api.mch.weixin.qq.com/pay/unifiedorder", payXml);

            if (StringUtils.isNullOrEmpty(postResult)) {
                return new ResponseResult(-1, "请求预支付服务器的返回结果为空！");
            }
            WXPrePayResultEntity wxPrePayResultEntity = XmlUtil.toBean(postResult, WXPrePayResultEntity.class);
            if (null == wxPrePayResultEntity) {
                return new ResponseResult(-1, "解析请求预支付服务器的返回结果出现异常！");
            }
            // 检查预支付结果
            if ("FAIL".equals(wxPrePayResultEntity.getReturn_code())) {
                return new ResponseResult(-1, "预支付返回结果错误，原因：" + wxPrePayResultEntity.getReturn_msg());
            }
            if ("FAIL".equals(wxPrePayResultEntity.getResult_code())) {
                return new ResponseResult(-1, "预支付返回结果错误，原因："
                        + wxPrePayResultEntity.getErr_code() + ", " + wxPrePayResultEntity.getErr_code_des());
            }

            // 生成手机发起支付的sign
            String timestamp = (new Date().getTime() + "").substring(0, 10);
            String stringSignTemp = "appid=" + weixinAppid + "&noncestr=" + noncestr
                    + "&package=Sign=WXPay&partnerid=" + weixinMchid + "&prepayid="
                    + wxPrePayResultEntity.getPrepay_id() + "&timestamp=" + timestamp + "&key=" + weixinApiKey;
            logger.debug(stringSignTemp);
            String appsign = DigestUtils.md5Hex(stringSignTemp).toUpperCase();

            //保存预支付信息, 如我方订单号, 商品id, 金额, 支付方式等
            Order order = new Order();
            order.setOrderNo(orderNo);
            order.setCommodityId(commodityId);
            order.setAmount(amount);
            order.setPayType(Order.PAY_TYPE_WEIXIN);
            order.setStatus(Order.STATUS_PENDING);
            order.setCreatedDate(new Date());
            order.setCreatedBy(/*TODO getCurrentUserId()*/"记录付款人");
            orderService.addOrder(order);

            //预支付成功，给app返回预支付订单号等字段
            Map<String, String> map = new HashMap<>();
            map.put("prepayid", wxPrePayResultEntity.getPrepay_id());
            map.put("out_trade_no", wxPrePayEntity.getOut_trade_no());// 用于支付完毕之后查询支付结果
            map.put("timestamp", timestamp);
            map.put("appid", weixinAppid);
            map.put("partnerid", weixinMchid);
            map.put("package", "Sign=WXPay");
            map.put("noncestr", noncestr);
            map.put("sign", appsign);

            ResponseResult rr = new ResponseResult();
            rr.setData(map);
            rr.setMessage("获取微信预支付交易号成功");
            return rr;
        } catch (Exception e) {
            logger.error("/createtransforWeixin", e);
            return new ResponseResult(99, "服务器异常");
        }
    }
    
    /**
     * 微信支付后回调
     */
    @RequestMapping("/weixinPaidNotify")
    public String weixinNotify(HttpServletRequest request) {
        try {
            // 拿到request的xml，并转换成bean
            String requestXml = PayUtil.parseRequestXml(request);
            logger.debug("异步通知的返回xml：{}", requestXml);
            if (StringUtils.isNullOrEmpty(requestXml)) {
                return PayUtil.wxNotifyResponseXml("FAIL", "参数格式校验错误");
            }
            WXPayNotifyUrlEntity wxPayNotifyUrlEntity = XmlUtil.toBean(requestXml, WXPayNotifyUrlEntity.class);
            if (null == wxPayNotifyUrlEntity) {
                logger.debug("微信支付返回结果错误，参数格式校验错误");
                return PayUtil.wxNotifyResponseXml("FAIL", "参数格式校验错误");
            }
            // 判断支付结果
            if ("FAIL".equals(wxPayNotifyUrlEntity.getReturn_code())) {
                logger.debug("支付返回结果错误，原因：{}", wxPayNotifyUrlEntity.getReturn_msg());
                return PayUtil.wxNotifyResponseXml("FAIL", "参数格式校验错误");
            }
            if ("FAIL".equals(wxPayNotifyUrlEntity.getResult_code())) {
                logger.debug("支付返回结果错误，原因：({}){}", wxPayNotifyUrlEntity.getErr_code(), wxPayNotifyUrlEntity.getErr_code_des());
                return PayUtil.wxNotifyResponseXml("FAIL", "参数格式校验错误");
            }
            // 验证sign是否有效
            // 将请求的bean转成map
            Map<String, String> beanMap = AlipayCore.convertBean(wxPayNotifyUrlEntity);
            // 去掉空值
            Map<String, String> resultMap = AlipayCore.paraFilter(beanMap);
            String s1 = AlipayCore.createLinkString(resultMap);
            String wxTempSign = s1 + "&key=" + weixinApiKey;
            wxTempSign = DigestUtils.md5Hex(wxTempSign).toUpperCase();

            if (!wxPayNotifyUrlEntity.getSign().equals(wxTempSign)) {
                logger.error("签名不对！微信发过来的：{}", wxPayNotifyUrlEntity.getSign());
                logger.error("后台根据参数生成的: {}", wxTempSign);
                return PayUtil.wxNotifyResponseXml("FAIL", "签名失败");
            }
            logger.debug("{}微信回调签名验证成功");

            // 微信服务器提示支付成功
            //TODO 付款后的处理逻辑
            String orderNo = wxPayNotifyUrlEntity.getOut_trade_no();
            String flowNo = wxPayNotifyUrlEntity.getTransaction_id();
            logger.debug("{}开始更新预支付状态", orderNo);
            
            boolean result = orderService.thirdPaySuccess(orderNo, flowNo);

            if (result) {
                return PayUtil.wxNotifyResponseXml("SUCCESS", "OK");
            } else {
            	orderService.thirdPayFail(orderNo, flowNo);
                return PayUtil.wxNotifyResponseXml("FAIL", "我方平台处理错误");
            }
        } catch (Exception e) {
            logger.error("出现错误了", e);
            return PayUtil.wxNotifyResponseXml("FAIL", "参数格式校验错误");
        }
    }
    
    @Value("${apliay.app_id}")
    private String appId;
    @Value("${apliay.ali_public_key}")
    private String aliPublicKey;
    @Value("${apliay.private_key}")
    private String privateKey;
    @Value("${apliay.input_charset}")
    private String inputCharset;
    @Value("${apliay.sign_type}")
    private String signType;
    @Value("${alipay.gateway}")
    private String alipayGateway;
    @Value("${alipay.notifyurl}")
    private String alipayNotifyUrl;
    /**
     * APP支付-支付宝预支付
     * @param commodityId 商品id
     */
    @RequestMapping("/createTransForAlipay")
    public ResponseResult createTransforAlipay(String commodityId) {
    	//从数据库查询商品价格
        BigDecimal amount = /*commodityService.getById(commodityId).getAmount()*/new BigDecimal(0.01);  //TODO 商品价格
        //创建本次购买的订单号
        String orderNo = OrderUtil.newOrderNo(6);
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(
                    alipayGateway,
                    appId,
                    privateKey,
                    "json",
                    inputCharset,
                    aliPublicKey,
                    signType);
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            String description = "商品描述";  //TODO 写具体的商品名称
            model.setBody(description);
            model.setSubject(description);
            model.setOutTradeNo(orderNo);
            model.setTimeoutExpress("30m");
            model.setTotalAmount(amount.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
            model.setProductCode("QUICK_MSECURITY_PAY");
            request.setBizModel(model);
            request.setNotifyUrl(alipayNotifyUrl);
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            String body = response.getBody();
            if (StringUtils.isNullOrEmpty(body)) return new ResponseResult(-1, "支付宝预支付未返回内容");
            
            //保存预支付信息, 如我方订单号, 商品id, 金额, 支付方式等
            Order order = new Order();
            order.setOrderNo(orderNo);
            order.setCommodityId(commodityId);
            order.setAmount(amount);
            order.setPayType(Order.PAY_TYPE_WEIXIN);
            order.setStatus(Order.STATUS_PENDING);
            order.setCreatedDate(new Date());
            order.setCreatedBy(/*TODO getCurrentUserId()*/"记录付款人");
            orderService.addOrder(order);

            //把支付宝返回的预支付信息返回给APP
            ResponseResult rr = new ResponseResult();
            rr.setData(body);

            return rr;
        } catch (Exception e) {
            logger.error("/createTransforAlipay", e);
            return new ResponseResult(-1,"参数构建异常");
        }
    }

    /**
     * 支付宝支付后回调
     */
    @RequestMapping("/alipayPaidNotify")
    public String alipayNotify(HttpServletRequest request) {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Object o : requestParams.keySet()) {
            String name = (String) o;
            String[] values = requestParams.get(name);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
            	//乱码解决，这段代码在出现乱码时使用。
                //value = new String(value.getBytes("ISO-8859-1"), "utf-8");
                if (i == values.length - 1) {
                    builder.append(values[i]);
                } else {
                    builder.append(values[i]).append(",");
                }
            }
            
            params.put(name, builder.toString());
        }

        try {
        	String orderNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            String flowNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            //验签
            if (!AlipaySignature.rsaCheckV1(params, aliPublicKey, inputCharset, signType)) {
            	logger.error("支付宝回调参数校验错误，校验参数：{}", params.toString());
                orderService.thirdPayFail(orderNo, flowNo);
                return "failure";
            }
            
            if ("TRADE_FINISHED".equals(tradeStatus) || "TRADE_SUCCESS".equalsIgnoreCase(tradeStatus)) {
                logger.debug("{}开始更新预支付状态", orderNo);
                boolean result = orderService.thirdPaySuccess(orderNo, flowNo);
                if (result) {
                    return "success";
                } else {
                    logger.error("支付后处理失败，错误原因:{}，参数{}", result, params.toString());
                    orderService.thirdPayFail(orderNo, flowNo);
                    return "failure";
                }
            } else if ("WAIT_BUYER_PAY".equals(tradeStatus)) {
                return "success";
            } else {
            	logger.error("未知交易状态{},回调处理失败", tradeStatus);
                return "failure";
            }
        } catch (Exception e) {
            logger.error("支付宝回调异常，校验参数：" + params.toString(),e);
            return "failure";
        }
    }

    /*
     * 以下是二码合一支付接口
     * 流程:
     * 1.用户在商品页面, 点击付款, 前端调/multiplePaymentsQRCode获取到一张二维码, 显示出来;
     * 2.用户用微信或支付宝扫码, 进入一个页面, 这个页面可以获取到具体是哪个APP扫的;
     * 3.前端调二合一预支付接口/createTransForMultiple, 获取到预支付的返回数据后, 唤起对应的支付程序;
     * 4.用户完成付款, 后端回调, 前端查询支付状态, 流程结束.
     */

    /**
     * 流程1
     * 生成二维码图片,
     * 二维码的内容是微信/支付宝扫码后要打开的url, 并且带上了billId参数
     */
    @RequestMapping("/multiplePaymentsQRCode")
    public ResponseResult multiplePaymentsQRCode(String billId, HttpSession session) {
        try {
            /*Bill bill = billService.getById(billId);
            if (bill == null) {
                return new ResponseResult(-1, "找不到该账单");
            }
            if (bill.getStatus() == Bill.STATUS_FINISHED) {
                return new ResponseResult(-1, "该账单已支付");
            }*/

            String qrcodeUrl = serverUrl + "/multiplePaymentPage?orderId=" + billId;
            String filePath = "/" + billId + System.currentTimeMillis() + ".png";
            String realPath = session.getServletContext().getRealPath("/upload") + filePath;
            String accessPath = serverUrl + "/upload" + filePath;

            QRCodeUtil.generateQRCodeImage(qrcodeUrl, 240, 240, realPath);

            ResponseResult rr = new ResponseResult();
            rr.setData(accessPath);
            return rr;
        } catch (Exception e) {
            logger.error("", e);
            return new ResponseResult(-1, "获取二维码失败");
        }
    }

    @RequestMapping("/createTransForMultiple")
    public ResponseResult createTransForMultiple(Integer payType, String billId, HttpServletRequest request) {
        /*Bill bill = billService.getById(billId);
        if (bill == null) {
            return new ResponseResult(-1, "找不到该账单");
        }
        if (bill.getStatus() == Bill.STATUS_FINISHED) {
            return new ResponseResult(-1, "该账单已支付");
        }*/

        if (payType == null) {
            payType = -1;
            //根据请求头判断扫码APP是微信还是支付宝
            String s = request.getHeader("User-Agent").toLowerCase();
            if (StringUtils.isNullOrEmpty(s)) {
                return new ResponseResult(-1, "获取不到当前支付APP");
            }

            if (s.contains("micromessenger")) {
                payType = 0;
            } else if (s.contains("alipayclient")) {
                payType = 1;
            }
        }

        if (payType == 0) {
            //发起微信预支付
            //TODO
        } else if (payType == 1) {
            //发起支付宝预支付
            //TODO
        } else {
            return new ResponseResult(-1, "未知支付方式");
        }

        ResponseResult rr = new ResponseResult();
        rr.setData(null);  //TODO 预支付第三方返回数据放这里
        rr.setExtraData(payType);
        return rr;
    }
}
