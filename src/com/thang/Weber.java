package com.thang;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Weber {

	public static String uname;//用户名
	public static String upass;//密码
	public static String companyName;//公司名 
	public static String person;//申请人
	public static String phone;//电话
	public static List<String> keywords;
	
	public static String LoginURL="https://login.alibaba-inc.com/ssoLogin.htm?APP_NAME=transformers&BACK_URL=https%3A%2F%2Fcrmchn.cn.alibaba-inc.com%2Fuser%2Fturbine%2Ftemplate%2Fuser%2CSignin&CONTEXT_PATH=%2Fuser%2Fturbine&CLIENT_VERSION=0.3.6-forcrm";
	
	public static DefaultHttpClient httpclient=null;
	public static ExecutorService pools=Executors.newFixedThreadPool(60);
	
	public static HashSet<String> saveCompany=new HashSet<String>();
	
	public Weber(String cname){
		companyName=cname;
		keywords=new ArrayList<String>();
		init();
	}
	
	/**
	 * 读取数据和配置信息，一定是在c盘根目录的config.xml
	 */
	public void init(){
		try{
			char[] c=new char[1024];
			File f=new File("C:/config.xml");
			FileReader fin=new FileReader(f);
			
			StringBuilder sber=new StringBuilder();
			org.dom4j.Document doc=null;
		    while(fin.ready()){
		    	fin.read(c);
		    	sber.append(c);
		    }
			
			
			fin.close();
			doc=DocumentHelper.parseText(sber.toString().trim());
			org.dom4j.Element data=doc.getRootElement();
			
			uname=data.elementText("name");
			upass=data.elementText("password");
			person=data.elementText("person");
			phone=data.elementText("phone");
			
			Iterator<org.dom4j.Element> keywordsData=data.element("keywords").elementIterator();
			while(keywordsData.hasNext()){
				keywords.add(keywordsData.next().getText());
			}
			System.out.println("读取配置文件成功!");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("读取配置文件失败！");
			return;
		}
		login();
	}
	
	/**
	 * 根据用户名和密码登陆到crm库
	 */
	public void login(){
		//MultiThreadedHttpConnectionManager connectionManager =  new MultiThreadedHttpConnectionManager();
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
		// Increase max total connection to 200
		cm.setMaxTotal(160);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(45);
		// Increase max connections for localhost:80 to 50
		HttpHost localhost = new HttpHost("https://crm.alibaba-inc.com", 80);
		cm.setMaxPerRoute(new HttpRoute(localhost), 50);
		httpclient=new DefaultHttpClient(cm);
		
		/**
		 * 设置跳转策略
		 */
		httpclient.setRedirectStrategy(new RedirectStrategy() {
			
			@Override
			public boolean isRedirected(HttpRequest request, HttpResponse response,
					HttpContext context) throws ProtocolException {
				int code=response.getStatusLine().getStatusCode();
				if(301==code||302==code){
					return true;
				}
				
				return false;
			}
			
			@Override
			public HttpUriRequest getRedirect(HttpRequest request, HttpResponse response,
					HttpContext context) throws ProtocolException {
				
				HttpPost hpost=new HttpPost(response.getFirstHeader("Location").getValue());
				
				HttpParams params=request.getParams();
				hpost.setParams(params);
				return hpost;
			}
		});
		/**
		 * 登陆CRM
		 * @return
		 */
		try{
		    
			    /**
			     * 解析登陆页面并获得变量_csrf_token的值.
			     */
				HttpGet hget=new HttpGet(LoginURL);
			    HttpResponse rep=httpclient.execute(hget);
			    HttpEntity getE=rep.getEntity();
			    Document html=Jsoup.parse(getE.getContent(),"UTF-8","https://login.alibaba-inc.com");
			    Element input=null;
			    if(null!=html&&html.getElementsByAttributeValue("name", "_csrf_token").size()>0){
			    	input=html.getElementsByAttributeValue("name", "_csrf_token").get(0);
			    }else{
			    	System.out.println("解析变量值失败！");
	                return;		    	
			    }
			    EntityUtils.consume(getE);
			
			
			    /**
			     * 提交登陆
			     */
			    HttpPost hpost=new HttpPost(LoginURL);
			    List<NameValuePair> nvps=new ArrayList<NameValuePair>();
			
			    nvps.add(new BasicNameValuePair("APP_NAME","transformers"));
			    nvps.add(new BasicNameValuePair("BACK_URL","https://crmchn.cn.alibaba-inc.com/user/turbine/template/user,Signin"));
			    nvps.add(new BasicNameValuePair("CLIENT_VERSION","0.3.6-forcrm"));
			    nvps.add(new BasicNameValuePair("CONTEXT_PATH","/user/turbine"));
			
			    nvps.add(new BasicNameValuePair("_csrf_token",input.attr("value")));
			    nvps.add(new BasicNameValuePair("action","SSOLoginAction"));
			    nvps.add(new BasicNameValuePair("return",""));
			    nvps.add(new BasicNameValuePair("_fm.l._0.a",uname));
			    nvps.add(new BasicNameValuePair("_fm.l._0.p",upass));
			    nvps.add(new BasicNameValuePair("event_submit_do_sso_login","true"));
			    nvps.add(new BasicNameValuePair("login_maintain","on"));
			    
			    hpost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
			
			    //hpost.getParams().setParameter(ClientPNames.COOKIE_POLICY, "easy");
			    HttpResponse response=httpclient.execute(hpost);
			
			    if(302==response.getStatusLine().getStatusCode()){
				    System.out.println("返回代码：302\n跳转页面：");
				    System.out.println(response.getFirstHeader("Location").getValue());
			    }
			    
			    String result=EntityUtils.toString(response.getEntity());
			
			    
			    if(result.contains("my_next_url")){
			    	
				   StringBuilder sber=new StringBuilder(result);
				
				   String my_next_url=sber.substring(sber.indexOf("my_next_url=\"")+13, sber.indexOf("\";",sber.indexOf("my_next_url=\"")));
				   
				   System.out.println(my_next_url);
				
				   HttpGet mainPage=null;
				   if(my_next_url.contains("?")){
					   mainPage=new HttpGet("https://crmchn.cn.alibaba-inc.com"+my_next_url+"&csrfToken=74244700");
				   }else{
					   mainPage=new HttpGet("https://crmchn.cn.alibaba-inc.com"+my_next_url+"/csrfToken/74244700");
				   }
				   System.out.println("第一次自动跳转："+my_next_url);
				
			       HttpResponse resp=httpclient.execute(mainPage);
			       result=EntityUtils.toString(resp.getEntity());
			    
			       if(result.contains("my_next_url")){
			    	   System.out.println("跳转成功！");
			    	   sber.delete(0, sber.length());
			    	   sber.append(result);
					
					   my_next_url=sber.substring(sber.indexOf("my_next_url=\"")+13, sber.indexOf("\";",sber.indexOf("my_next_url=\"")));
					   System.out.println("第二次自动跳转：");
					
					
					   HttpGet mainPage2=null;
					   if(my_next_url.contains("?")){
						   mainPage2=new HttpGet(my_next_url+"&csrfToken=");
					   }else{
						  mainPage2=new HttpGet(my_next_url+"/csrfToken/");
					   }
					   HttpResponse resp2=httpclient.execute(mainPage2);
				       result=EntityUtils.toString(resp2.getEntity());
				    
				       if(result.contains("id=\"mainFrame\"")){
				    	   System.out.println("跳转成功！");
				           String mp=result.substring(result.indexOf("id=\"mainFrame\" src=\"")+20, result.indexOf("\" style", result.indexOf("id=\"mainFrame\" src=\"")));
				    	   HttpGet mainPage3=new HttpGet(mp);
				    	
				    	   HttpResponse resp3=httpclient.execute(mainPage3);
					       result=EntityUtils.toString(resp3.getEntity());
					       if(result.indexOf("最新资讯")>0){
					    	   System.out.println("登陆成功！");
					    	   scan();//开始扫描
					       }else{
				    	       System.out.println("登陆失败！");
				    	       return;
					       }
				    }
			    }
			}
			
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("登陆失败！");
			}
			
	}
	
	public void scan(){
		System.out.println("开始扫描...");
		new Thread(new Scaner(1)).start();
		new Thread(new Scaner(2)).start();
		new Thread(new Scaner(3)).start();
		
	}
	
	public static void main(String[] args) {
		if(args.length>0&&null!=args[0]&&!"".equals(args[0])){
			Weber weber=new Weber(args[0].trim());	
		}else{
			System.out.println("请输入扫描公司名称!");
		}
        
	}

}
