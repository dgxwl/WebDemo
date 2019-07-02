package cn.abc.def.util;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 实现抽象类JsonSerializer, 指定泛型为转换前的原始类型,
 * 实现serialize()方法, 在方法中编写转换逻辑
 * @author Administrator
 *
 */
public class Date2MillisSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		/*
		 * 调用jsonGenerator的writeNumber(long arg0)将转换好的值传入参数;
		 * writeNumber()有其他数字类型参数的重载, 另外还有writeString()等支持其他数据类型的转换方法
		 */
		jsonGenerator.writeNumber(date.getTime());
	}

}
