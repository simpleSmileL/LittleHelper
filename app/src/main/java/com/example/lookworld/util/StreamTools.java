package com.example.lookworld.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTools {
	/**
	 * 工具方法
	 * @param is 输入流
	 * @return 文本字符串
	 * @throws Exception
	 */
	public static String readStream(InputStream is) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String temp = "";
		byte[] buffer = new byte[1024];
		int len = -1;
		while((len = is.read(buffer))!=-1){
			baos.write(buffer, 0, len);
		}
		is.close();

		//String temp =  baos.toString();
		temp = new String(baos.toByteArray(),"UTF-8");
		return temp;
	}
}
