<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>生成条形码示例</title>
<script src="https://cdn.jsdelivr.net/npm/jquery"></script>
<script src="https://cdn.jsdelivr.net/npm/jsbarcode@3.11.0/dist/JsBarcode.all.min.js"></script>
</head>
<body>
	<svg id="barcode"></svg>
	<!-- or -->
<!--<canvas id="barcode"></canvas> -->
	<!-- or -->
<!--<img id="barcode" /> -->
</body>
<script>
	//生成条形码, 以code39编码为例
	JsBarcode("#barcode", "${code}", {
		format : "CODE39"
	});
</script>
</html>