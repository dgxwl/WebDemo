package cn.abc.def.util;

public class StringUtils {
	
	private StringUtils() {
    }
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
	
	/**
     *判断多个参数其中是否存在空值或空字符串
     * @param args 多个字符串
     * @return 存在则返回true 否则返回false
     */
    public static boolean multiIsNullOrEmpty(String... args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg == null || arg.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
	
	/**
     * 判断多个参数是否为空
     * @param args 判断的参数
     * @return 返回判断结果数组
     * @see getAllNullMessages(boolean[] flags, String delimiter, String... messages)
     */
    public static boolean[] multiIsNull(String... args) {
        boolean[] flags = new boolean[args.length];

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg == null || arg.trim().isEmpty()) {
                flags[i] = true;
            }
        }

        return flags;
    }

    /**
     * 根据判断多个参数是否为空的结果给出对应的提示信息
     * @param flags 判断多个参数是否为空的结果数组
     * @param delimiter 分隔符
     * @param messages 各参数为空时对应的提示
     * @return 返回拼接的所有提示
     * @see boolean[] multiIsNull(String... args)
     */
    public static String getAllNullMessages(boolean[] flags, String delimiter, String... messages) {
        if (flags.length != messages.length) {
            throw new RuntimeException("检测参数个数与错误信息个数不一致");
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < flags.length; i++) {
            if (flags[i]) {
                builder.append(messages[i]).append(delimiter);  //拼接多个信息
            }
        }
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }
    
    /**
     * 删除字符串的指定前缀
     * @param val 要处理的字符串
     * @param prefix 前缀
     * @return 去除前缀的字符串
     */
    public static String deletePrefix(String val, String prefix) {
    	return val.substring(prefix.length());
    }
    
    /**
	 * 首字母变大写
	 * @return 首字母大写的字符串
	 */
    public static String toTitleCase(String str) {
		char[] chs = str.toCharArray();
		if (chs[0] >= 97 && chs[0] <= 122) {
			chs[0] -= 32;
		}
		return String.valueOf(chs);
	}
    
    /**
     * 下划线命名转驼峰命名
     * @param str
     * @return
     */
    public static String underscoreCaseToCamelCase(String str) {
    	if (isNullOrEmpty(str))
    		return str;
    	String[] data = str.split("[_]+");
		if (data.length > 1) {
			StringBuilder sb = new StringBuilder();
			sb.append(data[0]);
			for (int i = 1; i < data.length; i++) {
				data[i] = toTitleCase(data[i]);
				sb.append(data[i]);
			}
			return sb.toString();
		} else {
			return str;
		}
    }
    
    /**
     * 驼峰命名转下划线命名
     * @param str
     * @return
     */
    public static String camelCaseToUnderscoreCase(String str) {
    	if (isNullOrEmpty(str))
    		return str;
    	StringBuilder builder = new StringBuilder(str);
    	char first = builder.charAt(0);
    	if (first >= 'A' && first <= 'Z') {
    		builder.replace(0, 1, String.valueOf(first += 32));
    	}
    	for (int i = builder.length() - 1; i > 0; i--) {
			char ch = builder.charAt(i);
			if (ch >= 'A' && ch <= 'Z') {
				builder.replace(i, i + 1, "_" + (char)(ch+32));
			}
		}
    	return builder.toString();
    }
    
    /**
     * 是否为min~max位数字+字母的字符串
     * @param str
     * @return
     */
    public static boolean isAlphanumeric(String str, int min, int max) {
    	if (isNullOrEmpty(str))
    		return false;
    	return str.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{" + min + "," + max + "}$");
    }
    
    /**
     * 是否为邮箱
     * @param string
     * @return
     */
    public static boolean isEmail(String str) {
		if (isNullOrEmpty(str))
			return false;
		return str.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
	}
}
