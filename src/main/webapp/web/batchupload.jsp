<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>批量上传文件</title>
<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	<input id="filePicker1" type="file"/><br>
	<input id="filePicker2" type="file"/><br>
	<input id="filePicker3" type="file"/><br>
	<button onclick="upload()">批量上传</button>
	<p id="hint"></p>
</body>
<script>
	function upload() {
		let file1 = document.querySelector("#filePicker1").files[0];
		let file2 = document.querySelector("#filePicker2").files[0];
		let file3 = document.querySelector("#filePicker3").files[0];
		
		let uploadData = new FormData();
		uploadData.append('img', file1);
		uploadData.append('img', file2);
		uploadData.append('img', file3);
		
		$.ajax({
			url: '${pageContext.request.contextPath}/batch_upload',
			type: 'POST',
			data: uploadData,
			mimeType: "multipart/form-data; charset=UTF-8",
			contentType: false,
			processData: false,
			success: function (data) {
				document.querySelector("#hint").innerHTML = data;
			} 
		})
	}
</script>
</html>