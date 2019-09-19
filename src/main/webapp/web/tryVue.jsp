<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>vue试用</title>
<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue"></script>
</head>
<body>
	<ul id="list">
		<li>{{item}}</li>
	</ul>
</body>
<script>
	var i;
	$.ajax({
		url: "/vue",
		success: function(res) {
			i = res;
		}
	})

	$(function() {
		let vue = new Vue({
			el: "#list",
			data: {
				item: i
			}
		});
	})
</script>
</html>