<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>上传文件功能</title>
</head>
<body>
																		<!-- accept限定可上传的文件类型 -->
	<input id="filePicker" name="data" onchange="upload(this)" style="display: none;" type="file" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg"/>
	<button onclick="clickInput()">选择文件上传</button>
	<p id="hint"></p>
</body>
<script>
	function clickInput() {
		var clickEvent = new MouseEvent('click', {
			view : window, 
			bubble: true,
			cancelable: true
		});
		
		let filePicker = document.querySelector("#filePicker");
		filePicker.dispatchEvent(clickEvent);
	}
	
	function upload(i) {
		let file = i.files[0];
		let uploadData = new FormData();
		uploadData.append('data', file);
		
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && xhr.status == 200) {
				document.querySelector("#hint").innerHTML = xhr.responseText;
			}
		}
		xhr.open("POST", "${pageContext.request.contextPath}/upload", true);
		xhr.setRequestHeader("ContentType", "multipart/form-data; charset=UTF-8");
		xhr.send(uploadData);
	}
</script>
</html>