<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>请打开微信或支付宝扫码</title>
<script src="https://cdn.jsdelivr.net/npm/jquery"></script>
</head>
<body>
    <img id="qr">
</body>
<script>
	$(function() {
        $.get('/multiplePaymentsQRCode?billId=123456', function(res) {
            if (res.result == 1) {
                $("#qr").prop("src", res.data);
            } else {
                alert(res.message);
            }
        })
	})

</script>
</html>