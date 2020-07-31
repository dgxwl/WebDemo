<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>jsonp示例</title>
<script src="https://cdn.jsdelivr.net/npm/jquery"></script>
</head>
<body>
	<button id="btn">点击</button>
	<button id="btn2">点击</button>
	<br>
	<button id="btn3">点击</button>
</body>
<script>

	//示例1.利用script标签的src属性进行jsonp跨域
    $('#btn').click(function(){
        let s = document.createElement('script');
        s.src = 'http://localhost:8081/cross/jsonpDemo?param=kkkqqq&callback=cb999';
        $('body').append(s);
    });

    //示例2.使用jQuery进行jsonp跨域
    $('#btn2').click(function(){
        $.ajax({
			url: "http://localhost:8081/cross/jsonpDemo?param=kkkqqq",  //jQuery会自动加一个callback参数, 接口回调函数入参叫callback就好
            type: "GET",
            dataType: "jsonp",
//            jsonp: "callbackFunc",  //自定回调函数参数名, 默认callback
//            jsonpCallback: "cb999",  //可指定回调函数
            success: function(res) {
                alert(res.message);
            }
        });
    });

    function cb999(res){
        alert(res.message);
    }

    $('#btn3').click(function(){
        $.ajax({
            url: "http://localhost:8081/cross/crossDemo?param=qqqkkk",
            type: "GET",
            success: function(res) {
                alert(res.message);
            }
        });
    });
</script>
</html>