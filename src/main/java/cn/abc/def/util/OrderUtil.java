package cn.abc.def.util;

import java.util.Random;

public class OrderUtil {

	private OrderUtil() {
	}

	/*
	 * 获得指定长度随机数
	 */
	private static String getFixLenthString(int strLength) {
		Random rm = new Random();
		//获得随机数
		double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
		//返回固定的长度的随机数
		return String.valueOf(pross).substring(1, strLength + 1);
	}

	/**
	 * 返回一个新订单号
	 * @param i 随机数位数
	 * @return yyyyMMddHHmmssSSS+?
	 */
	public static String newOrderNo(int i) {
		return DateUtil.getFormattedCurrentDate("yyyyMMddHHmmssSSS") + getFixLenthString(i);
	}
	
	public static String getRamCode(int num) {
        Random random = new Random();
        int max = (int)Math.pow(10, (double)num);
        return padRight(String.valueOf(random.nextInt(max)), num, '0');
    }
	
	public static String padRight(String str, int size, char fill) {
        if(str == null) {
            str = "";
        }

        int str_size = str.length();
        int pad_len = size - str_size;
        StringBuilder retvalue = new StringBuilder();

        for(int i = 0; i < pad_len; ++i) {
            retvalue.append(fill);
        }

        return retvalue.insert(0, str).toString();
    }
}
