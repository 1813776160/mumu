package http2;

import java.util.HashMap;

public class HttpServletRequest {
	
	private String method;
	private String requestURL;
	private String protocol;
	private HashMap<String,String> headerMap=new HashMap<>();

	public HttpServletRequest(String content){
		String []lines =content.split("\r\n");
		
		for(int i=0;i<lines.length;i++){
			if(i==0){
				//解析头行
				String []topLines=lines[i].split("\\s");	//正则表达式 常用的空格 包括制表符，空格等等
				method=topLines[0];
				requestURL =topLines[1];
				protocol=topLines[2];
			}else{
				String [] headerLines =lines[i].split(":\\s");
				headerMap.put(headerLines[0],headerLines[1]);
			}
			
		}
	}
	
	public String getRequestURL(){
		return requestURL;
	}
	
	public String getMethod(){
		return method;
	}
	
	public String getProtocol(){
		return protocol;
	}
	
	public String getHeader(String header){
		return headerMap.get(header);
	}
	
	public void setRequestURL(String requestURL){
		this.requestURL=requestURL;
	}

	

	public RequestDispatcher getReqeustDispatcher(String string) {
		return new RequestDispatcher(string);
	}
	
	private Cookie[] cookies;
	public Cookie[] getCookies(){
		String cookieStr=headerMap.get("Cookie");
		if(cookieStr!=null){
			String [] cookieItems=cookieStr.split(";");
			cookies=new Cookie[cookieItems.length];
			int i=0;
			for(String cookieItem:cookieItems){
				String []cookieNameValue=cookieItem.split("=");
				String name =cookieNameValue[0];
				String value =cookieNameValue[1];
				cookies[i] =new Cookie(name,value);
				i++;
			}
		}
		return cookies;
	}
	
	
	
	
}
