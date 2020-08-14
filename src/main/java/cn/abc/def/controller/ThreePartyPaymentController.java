package cn.abc.def.controller;

import cn.abc.def.domain.ResponseResult;
import cn.abc.def.service.IOrderService;
import cn.abc.def.service.IThirdPartyPaymentService;
import cn.abc.def.util.QRCodeUtil;
import cn.abc.def.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 三方支付对接demo
 * @author Administrator
 *
 */
@RestController
public class ThreePartyPaymentController {

    @Resource
    private IThirdPartyPaymentService thirdPartyPaymentService;
	@Resource
	private IOrderService orderService;

    @Value("${server.url}")
    private String serverUrl;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * APP支付-微信预支付
     * @param commodityId 商品id
     */
    @RequestMapping("/createAppTransForWeixin")
    public ResponseResult createAppTransForWeixin(String commodityId, HttpServletRequest request) {
        try {
            return thirdPartyPaymentService.createTransForWeixin(commodityId, "APP", request);
        } catch (Exception e) {
            logger.error("/createAppTransForWeixin", e);
            return new ResponseResult(99, "服务器异常");
        }
    }

    /**
     * PC网页支付-微信预支付
     * @param commodityId 商品id
     */
    @RequestMapping("/createPCTransForWeixin")
    public ResponseResult createPCTransForWeixin(String commodityId, HttpServletRequest request) {
        try {
            return thirdPartyPaymentService.createTransForWeixin(commodityId, "NATIVE", request);
        } catch (Exception e) {
            logger.error("/createPCTransForWeixin", e);
            return new ResponseResult(99, "服务器异常");
        }
    }

    /**
     * 手机网页支付-微信预支付
     * @param commodityId 商品id
     */
    @RequestMapping("/createH5TransForWeixin")
    public ResponseResult createH5TransForWeixin(String commodityId, HttpServletRequest request) {
        try {
            return thirdPartyPaymentService.createTransForWeixin(commodityId, "JSAPI", request);
        } catch (Exception e) {
            logger.error("/createH5TransForWeixin", e);
            return new ResponseResult(99, "服务器异常");
        }
    }
    
    /**
     * 微信支付后回调
     */
    @RequestMapping("/weixinPaidNotify")
    public String weixinNotify(HttpServletRequest request) {
        return thirdPartyPaymentService.weixinNotify(request);
    }

    /**
     * APP支付-支付宝预支付
     * @param commodityId 商品id
     */
    @RequestMapping("/createAppTransForAlipay")
    public ResponseResult createAppTransForAlipay(String commodityId) {
        try {
            return thirdPartyPaymentService.createTransForAlipay(commodityId, "APP");
        } catch (Exception e) {
            logger.error("/createAppTransForAlipay", e);
            return new ResponseResult(-1,"服务器异常");
        }
    }

    /**
     * PC网页支付-支付宝预支付
     * @param commodityId 商品id
     */
    @RequestMapping("/createPCTransForAlipay")
    public ResponseResult createPCTransForAlipay(String commodityId) {
        try {
            return thirdPartyPaymentService.createTransForAlipay(commodityId, "NATIVE");
        } catch (Exception e) {
            logger.error("/createPCTransForAlipay", e);
            return new ResponseResult(-1,"服务器异常");
        }
    }

    /**
     * 手机网页支付-支付宝预支付
     * @param commodityId 商品id
     */
    @RequestMapping("/createH5TransForAlipay")
    public ResponseResult createH5TransForAlipay(String commodityId) {
        try {
            return thirdPartyPaymentService.createTransForAlipay(commodityId, "JSAPI");
        } catch (Exception e) {
            logger.error("/createH5TransForAlipay", e);
            return new ResponseResult(-1,"服务器异常");
        }
    }

    /**
     * 支付宝支付后回调
     */
    @RequestMapping("/alipayPaidNotify")
    public String alipayNotify(HttpServletRequest request) {
        return thirdPartyPaymentService.alipayNotify(request);
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

            QRCodeUtil.generateQRCodeImageToPath(qrcodeUrl, 240, 240, realPath);

            ResponseResult rr = new ResponseResult();
            rr.setData(accessPath);
            return rr;
        } catch (Exception e) {
            logger.error("", e);
            return new ResponseResult(-1, "获取二维码失败");
        }
    }

    /**
     * 二合一预支付
     * @param payType 0微信, 1支付宝
     * @param billId 待支付账单id
     * @return 预支付结果
     */
    @RequestMapping("/createTransForMultiple")
    public ResponseResult createTransForMultiple(Integer payType, String billId, HttpServletRequest request) {
        try {
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

            if (payType == 0) {  //发起微信预支付
                return thirdPartyPaymentService.createTransForWeixin("", "JSAPI", request);
            } else if (payType == 1) {  //发起支付宝预支付
                return thirdPartyPaymentService.createTransForAlipay("", "JSAPI");
            } else {
                return new ResponseResult(-1, "未知支付方式");
            }
        } catch (Exception e) {
            logger.error("", e);
            return new ResponseResult(-1, "获取二维码失败");
        }
    }

    /**
     * 查询支付结果
     * @param orderNo 我方订单号
     * @return 支付结果
     */
    @RequestMapping(value = "/getOrderStatus")
    public ResponseResult getOrderStatus(String orderNo) {
        return thirdPartyPaymentService.getOrderStatus(orderNo);
    }

    @RequestMapping(value = "/getQRCodeTest", produces = "image/png")
    public byte[] getQRCodeTest() {
        try {
            return QRCodeUtil.getQRCodeImageBytes("Hello World!", 320, 320);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
