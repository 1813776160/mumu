package http2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

//处理器
public class Processer {
	
	private HashMap<String,HttpServlet> servletContainer=new HashMap<String,HttpServlet>();
	
	{
		servletContainer.put("/hello.s", new HelloServlet());
		servletContainer.put("/forward.s", new ForwardServlet());
		servletContainer.put("/redirect.s", new RedirectServlet());
		servletContainer.put("/setCookie.s", new SetCookieServlet());
		servletContainer.put("/user/getCookie.s", new GetCookieServlet());
	}
	
	public void process(Socket socket){
		InputStream in ;
		OutputStream out;
		try {
			in = socket.getInputStream();
			out= socket.getOutputStream();
			
			byte [] buf =new byte[1024];
			int count;
			count=in.read(buf);
			System.out.println(count);
			String content=new String(buf,0,count);
			System.out.println(content);
			
			//解析报文
			HttpServletRequest request=parseRequest(content);
			
			HttpServletResponse response=new HttpServletResponse(request, out);
			
			/**
			 * 静态请求：对应一个html,js,css....(文件)
			 * 动态请求：hello.s
			 * 非法请求：404
			 * 
			 */
			
			String rootPath="D:/moshou";
			String webPath=request.getRequestURL();
			String diskPath=rootPath+webPath;
			if(new File(diskPath).exists()==true){
				
			}else if(servletContainer.containsKey(webPath)){
				HttpServlet servlet=servletContainer.get(webPath);
				servlet.service(request, response);
			}else{
				response.setStatus(404, "Not Found");
				request.setRequestURL("/404.html");
			}
			
			response.commit();
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
