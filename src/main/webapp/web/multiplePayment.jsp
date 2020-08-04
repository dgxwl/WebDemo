<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<script src="https://cdn.jsdelivr.net/npm/jquery"></script>
</head>
<body>

</body>
<script>
	$(function() {
        var billId = getQueryVariable("billId");

        $.ajax({
            url: '/createTransForMultiple',
            data: 'billId=' + billId,
            type: 'POST',
            success: function(res) {
                if (res.result == 1) {
                    if (res.extraData == 0) {
                        alert('调起微信支付');
                    } else {
                        alert('调起支付宝支付');
                    }
                } else {
                    alert(res.message);
                }
            }
        })
	})

    function getQueryVariable(variable) {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i=0;i<vars.length;i++) {
            var pair = vars[i].split("=");
            if(pair[0] == variable){return pair[1];}
        }
        return(false);
    }
</script>
</html>