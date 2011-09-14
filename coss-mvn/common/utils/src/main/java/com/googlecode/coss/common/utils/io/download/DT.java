package com.googlecode.coss.common.utils.io.download;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class DT extends Thread {

	String urlt;
	int startl;
	int end;
	String fileName;
	RandomAccessFile osf;

	public DT(int i, String url, String fileName, int start, int end) {
		this.setName("t" + i); //子线程名称
		this.urlt = url; //下载地址
		this.fileName = fileName;
		this.startl = start; //子线程读取/写入起始字节
		this.end = end;//子线程写入结束字节长度
	}

	public void run() {
		try {
			osf = new RandomAccessFile(fileName, "rw");
			URL url = new URL(urlt);
			HttpURLConnection http2 = (HttpURLConnection) url.openConnection();
			http2.setRequestProperty("User-Agent", "NetFox");

			/*
			 * 断点续传和多线程下载的关键代码关键位置：即设置断点
			 * http2.setRequestProperty("RANGE", "bytes="+startl+"-");//设置断点位置，向服务器请求从文件的哪个字节开始读取。
			 * osf.seek(startl);//设置本地文件从哪个字节开始写入
			 * 如果是单线程，则首先要判断下载文件是否已经存在 及DownloadFile.java 里的 fileName = "C:\\eclipse.zip";
			 * 如果存在则开始断点续传，方法同多线程：
			 * 因为断点续传是从上次传输中断的字节开始，则首先要得到上次中断的位置，既是文件长度（针对单线程）f.length()
			 * 然后设置HTTP请求头属性RANGE，该属性告知服务器从哪开始读取文件。
			 * 设置本地文件写入起始字节，及接从上次传输断点继续写入（断点续传）
			 * osf.seek(offset) 该方法设定从offset后一个字节开始写入文件
			 * 注意：多线程不能用文件长度做为写文件起始字节，需有配置文件记录上次读写的位置，迅雷下载既是使用该种方法。
			 */
			http2.setRequestProperty("RANGE", "bytes=" + startl + "-");//设置断点位置，向服务器请求从文件的哪个字节开始读取。
			osf.seek(startl);//设置本地文件从哪个字节开始写入

			InputStream input = http2.getInputStream();
			byte b[] = new byte[1024];//设置缓冲池，每次只读1024字节
			Date d = new Date();//子线程开始下载时间
			int l;//计算子线程读取和写入的文件长度，当长度大于每个子线程平均下载长度则终止线程
			int i;
			l = 0;
			System.out.println(this.getName() + " 开始下载。。。");
			while ((i = input.read(b, 0, 1024)) != -1 && l < end) { //线程下载字节长度控制误差小于缓冲池大小，本示例为缓冲池1024字节
				osf.write(b, 0, i);
				b = new byte[1024];//重新赋值，避免重新读入旧内容
				l += i;
			}
			Date d2 = new Date();//子线程结束下载时间
			System.out.println(this.getName() + " 线程耗时： " + (d2.getTime() - d.getTime()) / 1000 + " 秒,实际共下载：" + l
					+ "字节");//子线程下载耗时（秒）
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}