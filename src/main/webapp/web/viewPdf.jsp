<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>测试预览pdf</title>
<script src="${pageContext.request.contextPath}/js/pdfobject.min.js"></script>
</head>
<body>
	<div id="example1" style="width: 100%;"></div>
	<script>
		var options = {
			height : "1000px",
			pdfOpenParams : {
				view : 'FitV',
				page : '0'
			},
			name : "mans",
			fallbackLink : "<p>您的浏览器暂不支持此pdf，请下载最新的浏览器</p>"
		};
		PDFObject.embed('${url}', "#example1", options);
	</script>
</body>
</html>