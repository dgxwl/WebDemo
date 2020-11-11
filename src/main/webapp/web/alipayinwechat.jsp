<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>微信内用支付宝给钱demo</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.js"></script>
    <script src="https://gw.alipayobjects.com/as/g/h5-lib/alipayjsapi/3.1.1/alipayjsapi.min.js"></script>
</head>
<body>
<button style="width: 200px; height: 100px" onclick="pay()" type="button">支付宝支付</button>
<h1>提示:在浏览器中打开, 付款后请回到微信刷新页面</h1>
</body>
<script>
    function pay() {
        $.ajax({
            url: '${pageContext.request.contextPath}/createH5TransForAlipay',
            type: 'POST',
            success: function (data) {
                console.log(data)
                let newWindow = window.open();
                newWindow.document.write(data.data);
            }
        })
    }
</script>
</html>
