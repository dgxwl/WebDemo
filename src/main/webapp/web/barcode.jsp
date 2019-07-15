<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>生成条形码示例</title>
<script src="https://cdn.jsdelivr.net/npm/jquery"></script>
<script src="https://cdn.jsdelivr.net/npm/jsbarcode@3.11.0/dist/JsBarcode.all.min.js"></script>
</head>
<body>
<!--<svg id="barcode"></svg> -->
	<!-- or -->
<!--<canvas id="barcode"></canvas> -->
	<!-- or -->
	<img id="barcode" /> <!-- 如果要打印,还是用img比较好处理.. -->

	<button onclick="print()">打印</button>
</body>
<script>
	$(function() {
		//生成条形码, 以code39编码为例
		JsBarcode("#barcode", "${code}", {
			format : "CODE39"
		});
	})
	
	//打印条形码
	function print() {
		let newWindow = window.open();
		newWindow.focus();

		let content = "<html><head><meta charset='utf-8'/><title>打印</title></head><body>"
			+ $("#barcode")[0].outerHTML
			+ "</body></html>";
		newWindow.document.write(content);
		newWindow.print();
		newWindow.document.close();
		newWindow.close();
		return false;
	}
</script>
</html>