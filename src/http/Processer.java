package http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

//处理器
public class Processer {
	
	public void process(Socket socket){
		InputStream in ;
		OutputStream out;
		try {
			in = socket.getInputStream();
			out= socket.getOutputStream();
			
			//获取请求报文内容
			byte[] buf=new byte[1024];
			int count = 0;
			count=in.read(buf);
			String content =new String(buf,0,count);
			System.out.println(content);//	获得报文
			//解析报文
			HttpServletRequest request=parseRequest(content);

			String suffix =request.getRequestURL().substring(request.getRequestURL().lastIndexOf(".")+1);
			String contentType;
			
			switch(suffix){
			case "js": contentType="application/x-javascript";break;
			case "css":contentType="text/css";break;
			case "bmp":contentType="image/bmp";break;
			case "gif":contentType="image/gif";break;
			case "png":contentType="image/png";break;
			case "jpg":contentType="image/jpg";break;
			default:contentType="text/html";
			}
			
			//响应报文
			String responseStr ="Http/1.1 20 OK \r\n";
			responseStr+="Content-Type:"+contentType+"\t\n";
			responseStr+="\r\n";
			out.write(responseStr.getBytes());
			
			
			String rootPath="D:/moshou";
			String filePath=request.getRequestURL();
			
			String diskPath=rootPath+filePath;
			System.out.println(diskPath);
//			if(new File(diskPath).exists()==false){
//				diskPath=rootPath+"/404.html";
//			}
			FileInputStream fis=new FileInputStream(diskPath);
			//向浏览器发送报文
			while((count=fis.read(buf))>0){
				out.write(buf,0,count);
			}
			fis.close();
		    
			//根据请求路径返回对应文件
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public HttpServletRequest parseRequest(String content){
		
		HttpServletRequest request =new HttpServletRequest(content);
		
		
		return request;
	}
	
	
	
	
	
}
