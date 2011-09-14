package com.googlecode.coss.common.utils.io.download;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFile {

	/**
	 * @param args
	 */
	static int len;//线程平均下载文件长度
	static int bn;//每个线程写入文件的字节数
	static int tn; //线程数
	static String urlt;//下载地址
	static String fileName;
	static RandomAccessFile osf; //文件操作

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			urlt = "http://im.baidu.com/download/BaiduHi_4.2_Beta.exe";
			fileName = "D:\\" + urlt.split("//")[1].split("/")[urlt.split("//")[1].split("/").length - 1];
			System.out.println(fileName);
			URL url = new URL(urlt);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();

			/**
			 * 此处设定5个线程下载一个文件tn = 5;
			 * 判断平均每个线程需下载文件长度：
			 */
			System.out.println("file size:" + http.getContentLength());
			tn = 3;
			len = http.getContentLength() / tn;//舍去余数（余数自动舍去）计算每个线程应下载平均长度，最后一个线程再加上余数，则是整个文件的长度,
			File f = new File(fileName);
			if (f.exists()) {
				f.delete();
			}
			osf = new RandomAccessFile(f, "rw");
			osf.seek(http.getContentLength() - 1);
			osf.write(0);
			System.out.println("temp 文件长度：" + f.length());
			Thread t;//下载子线程，
			for (int j = 0; j < tn; j++) {
				if (j == tn - 1) {//如果最后一个线程则加上余数长度字节
					bn = len + (http.getContentLength() % tn);
				} else {
					bn = len;
				}
				System.out.println("t" + j + "线程下载长度：" + bn + "起始字节：" + len * j);
				t = new DT(j, urlt, fileName, len * j, bn

				);
				t.start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}