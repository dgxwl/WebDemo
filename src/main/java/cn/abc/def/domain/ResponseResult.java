package cn.abc.def.domain;

public class ResponseResult {
	private int result = 1;
	private String message;
	private int total;
	private Object data;
	private Object extraData;
	
	public ResponseResult() {
	}

	public ResponseResult(String message) {
		this.message = message;
	}

	public ResponseResult(Object data) {
		this.data = data;
	}

	public ResponseResult(int result, String message) {
		this.result = result;
		this.message = message;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getExtraData() {
		return extraData;
	}

	public void setExtraData(Object extraData) {
		this.extraData = extraData;
	}
	
}
