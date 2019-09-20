<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>上传文件功能 - jq实现</title>
<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
																		<!-- accept限定可上传的文件类型 -->
	<input id="filePicker" name="data" style="display: none;" type="file" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg"/>
	<button onclick="clickInput()">选择文件上传</button>
	<p id="hint"></p>
</body>
<script>
	function clickInput() {
		$("#filePicker").click();
	}
	
	$("#filePicker").change(function () {
		let file = document.querySelector("#filePicker").files[0];
	    let uploadData = new FormData();
		uploadData.append('data', file);
		
	    $.ajax({
			url: '${pageContext.request.contextPath}/upload',
			type: 'POST',
			data: uploadData,
			mimeType: "multipart/form-data; charset=UTF-8",
			contentType: false,
			processData: false,
			success: function (data) {
				document.querySelector("#hint").innerHTML = data;
			} 
		})
	})
</script>
</html>