package http2;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class HttpServletResponse{
	private static WebXmlParser webXmlparse=new WebXmlParser("web.xml");
	private HttpServletRequest request;
	private OutputStream out;
	private int status=200;
	private String message="OK";
	private HashMap<String,String> headerMap =new HashMap<>();
	
	public HttpServletResponse(HttpServletRequest request,OutputStream out){
		
		super();
		this.request=request;
		this.out=out;
		
	}
		
	public void commit() throws IOException{
		
		String suffix =request.getRequestURL().substring(
							request.getRequestURL().lastIndexOf(".")+1
						);
		
		if(headerMap.containsKey("Content-Type")==false){
			String contentType=webXmlparse.getContentType(suffix);
			setContentType(contentType);
		}
		
		
//		//从web.xml 文件中解析出contentType 
//		String contentType=webXmlparse.getContentType(suffix);
//		//设置相应重定向
//		setContentType(contentType);
		
		//响应报文
		String resp="HTTP/1.1 "+status+" "+message+"\r\n";
//		resp +="Content-Type:"+contentType+"\r\n";
		for(Entry<String,String> entry :headerMap.entrySet()){
			resp+=entry.getKey()+":" +entry.getValue()+"\r\n";
		}
		
		for(Cookie cookie: cookies){
			resp+=cookie+"\r\n";
			
		}
		
		resp+="\r\n";
		out.write(resp.getBytes());
		
		if(status<300||status>399){
			if(caw.toString().isEmpty()){
				String rootPath="D:/moshou";
				String webPath =request.getRequestURL();
				//判断文件是否存在
				String diskPath=rootPath+webPath;
				if(new File(diskPath).exists()==false){
					diskPath=rootPath+"/404.html";
				}
				
				FileInputStream fis=new FileInputStream(diskPath);
				
				byte [] buf =new byte[1024];
				int count;
				//向浏览器发送报文
				while((count=fis.read(buf))>0){
					out.write(buf,0,count);
				}
				fis.close();
			}else{
				out.write(caw.toString().getBytes());
			}
			
		}
	}
	
	public void setContentType(String contentType) {
		this.headerMap.put("Content-Type", contentType);
	}
	
	CharArrayWriter caw =new CharArrayWriter();
	PrintWriter pw =new PrintWriter(caw);
	public PrintWriter getWrite(){
		return pw;
	}
	
	public void setStatus(int status,String message){
		this.status=status;
		this.message=message;
	}

	public void sendRedirect(String webPath) {
		/**
		 * 	1xx 接受请求,继续处理
		 * 	2xx 正常响应 200
		 * 	3xx 相应重定向 301  302
		 * 	4xx 浏览器端错位 404  405
		 * 	5xx 服务器端错误
		 * 
		 */
		this.setStatus(301, "Redirect");
		this.addHeader("Location" ,webPath);
	}

	public void addHeader(String key, String value) {
		this.headerMap.put(key, value);
	}
	
	private List<Cookie> cookies =new ArrayList<>();
	public void addCookie(Cookie cookie){
		cookies.add(cookie);
	}
	
}
