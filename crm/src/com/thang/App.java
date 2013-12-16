package com.thang;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thang.model.MData;
import com.thang.ui.LoginWin;
import com.thang.ui.MainWin;

public class App {

	
	private static String uname="shqhzwj11";
	private static String upass="zljiayou2013";
	
	private static DefaultHttpClient httpclient=null;
	private static MainWin mainWin=null;
	
	private static String LoginURL="https://login.alibaba-inc.com/ssoLogin.htm?APP_NAME=transformers&BACK_URL=https%3A%2F%2Fcrmchn.cn.alibaba-inc.com%2Fuser%2Fturbine%2Ftemplate%2Fuser%2CSignin&CONTEXT_PATH=%2Fuser%2Fturbine&CLIENT_VERSION=0.3.6-forcrm";
	private static String FormURL="https://crm.alibaba-inc.com";
	
	private static Gson gson=null;
	public static void main(String args[]) {
		
	        try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	                if ("Nimbus".equals(info.getName())) {
	                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                    break;
	                }
	            }
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(LoginWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(LoginWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(LoginWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(LoginWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        }
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	            	//LoginWin loginWin=new LoginWin();
	            	//loginWin.setVisible(true);
	            	if(App.login()){
	            		App.showMain();
	            	}
	            	
	            }
	        });
	    }
	 
	 
    public static boolean login(){
         	gson=new Gson();
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
			schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
			PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
			// Increase max total connection to 200
			cm.setMaxTotal(200);
			// Increase default max connection per route to 20
			cm.setDefaultMaxPerRoute(20);
			// Increase max connections for localhost:80 to 50
			HttpHost albb = new HttpHost("https://crm.alibaba-inc.com", 80);
			cm.setMaxPerRoute(new HttpRoute(albb), 50);
			httpclient=new DefaultHttpClient(cm);
			
			
			HttpParams params = httpclient.getParams();

		    HttpConnectionParams.setSoTimeout(params, 500);//设定连接等待时间

		    HttpConnectionParams.setConnectionTimeout(params, 600);//设定超时时间
			
		    
		    
		    
		    try {
		    
		    
			CookieSpecFactory csf = new CookieSpecFactory() {
			public CookieSpec newInstance(HttpParams params) {
			    return new BrowserCompatSpec() {   
				              @Override
				              public void validate(Cookie cookie, CookieOrigin origin)
				              throws MalformedCookieException {
				                  // Oh, I am easy
				              }
				          };
				     }
				};
			
			
				httpclient.getCookieSpecs().register("easy", csf);
				 httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, "easy");
			
			HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

			    public boolean retryRequest(IOException exception,int executionCount,HttpContext context) {
			        if (executionCount >= 5) {
			            // Do not retry if over max retry count
			            return false;
			        }
			        if (exception instanceof InterruptedIOException) {
			            // Timeout
			            return false;
			        }
			        if (exception instanceof UnknownHostException) {
			            // Unknown host
			            return false;
			        }
			        if (exception instanceof ConnectException) {
			            // Connection refused
			            return false;
			        }
			        if (exception instanceof SSLException) {
			            // SSL handshake exception
			            return false;
			        }
			        HttpRequest request = (HttpRequest) context.getAttribute(
			                ExecutionContext.HTTP_REQUEST);
			        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest); 
			        if (idempotent) {
			            // Retry if the request is considered idempotent 
			            return true;
			        }
			        return false;
			    }

			};

			httpclient.setHttpRequestRetryHandler(myRetryHandler);
			
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
		                return false;		    	
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
				
				    EntityUtils.consume(response.getEntity());
				    
				    if(result.contains("my_next_url")){
				    	
					   StringBuilder sber=new StringBuilder(result);
					
					   String my_next_url=sber.substring(sber.indexOf("my_next_url=\"")+13, sber.indexOf("\";",sber.indexOf("my_next_url=\"")));
					   
					  			
					   HttpGet mainPage=null;
					   if(my_next_url.contains("?")){
						   mainPage=new HttpGet("https://crmchn.cn.alibaba-inc.com"+my_next_url+"&csrfToken=74244700");
					   }else{
						   mainPage=new HttpGet("https://crmchn.cn.alibaba-inc.com"+my_next_url+"/csrfToken/74244700");
					   }
					   System.out.println("第一次自动跳转："+my_next_url);
					
				       HttpResponse resp=httpclient.execute(mainPage);
				       result=EntityUtils.toString(resp.getEntity());
				    
				       EntityUtils.consume(resp.getEntity());
				       
				       if(result.contains("my_next_url")){
				    	   System.out.println("跳转成功！");
				    	   sber.delete(0, sber.length());
				    	   sber.append(result);
						
						   my_next_url=sber.substring(sber.indexOf("my_next_url=\"")+13, sber.indexOf("\";",sber.indexOf("my_next_url=\"")));
						   System.out.println("第二次自动跳转："+my_next_url);
						
						
						   HttpGet mainPage2=null;
						   if(my_next_url.contains("?")){
							   mainPage2=new HttpGet(my_next_url+"&csrfToken=");
						   }else{
							  mainPage2=new HttpGet(my_next_url+"/csrfToken/");
						   }
						   HttpResponse resp2=httpclient.execute(mainPage2);
					       result=EntityUtils.toString(resp2.getEntity());
					    
					       EntityUtils.consume(resp2.getEntity());
					       
					       if(result.contains("id=\"mainFrame\"")){
					    	   System.out.println("跳转成功！");
					           String mp=result.substring(result.indexOf("id=\"mainFrame\" src=\"")+20, result.indexOf("\" style", result.indexOf("id=\"mainFrame\" src=\"")));
					    	   HttpGet mainPage3=new HttpGet(mp);
					    	
					    	   HttpResponse resp3=httpclient.execute(mainPage3);
						       result=EntityUtils.toString(resp3.getEntity());
						       if(result.indexOf("最新资讯")>0){
						    	   System.out.println("登陆成功！");
						    	   return true;	
						       }else{
					    	       System.out.println("登陆失败！");
						       }
					    }
				    }
				}
				
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("登陆失败！");
				}
			return false;	
				
    }catch(Exception e){
    	e.printStackTrace();
    }
		    return false;
    }

    public static List<MData> queryForm(String cname){
    	try{
    		List<MData> ps=null;
    		HttpGet hget=new HttpGet("https://crm.alibaba-inc.com/noah/presale/work/allCustomer.vm");
    		
    		HttpResponse hr=httpclient.execute(hget);
    		
    		
    		System.out.println("hr="+hr);
    		
    		
    		
    		String result=EntityUtils.toString(hr.getEntity());
    	
    		String formURL=result.substring(result.indexOf("var search_as_opportunity_rpc"));
    		                                                
    		       formURL=formURL.substring(55, formURL.indexOf(".do'),")+3);
    		       //formURL=formURL.replaceFirst("..do", ".do");
    		HttpPost hpost_form=new HttpPost(FormURL+formURL);
    		hpost_form.addHeader("X-Requested-With","XMLHttpRequest");
    	
    		List<NameValuePair> inputs=new ArrayList<NameValuePair>();
	    	
	    	String params="[\"\",{\"$rid\":\"\",\"companyName\":\""+cname+"\",\"via\":\"viaContact\",\"idType\":\"globalId\",\"idText\":\"\",\"searchType\":\"allOr\"},0,100]";
	    	System.out.println(params);
	    	inputs.add(new BasicNameValuePair("_args_",params));
			inputs.add(new BasicNameValuePair("_id_","ESHCRQ"));
			inputs.add(new BasicNameValuePair("t__","0.7728297320856651"));

			hpost_form.setEntity(new UrlEncodedFormEntity(inputs,"UTF-8"));
		
			HttpResponse resp=httpclient.execute(hpost_form);
			
			HttpEntity en=resp.getEntity();
			String queryResult=EntityUtils.toString(en);
			if(null!=queryResult&&!"".equals(queryResult.trim())){
				
				if(queryResult.contains("返回数据超过100")){
					System.out.println("返回数据超过100条");
					return null;
				}
				
				if(!queryResult.contains("{\"totalCount\"")){
					return null;
				}
				
				String rs=queryResult.substring(queryResult.indexOf("\"resultList\":[")+13, queryResult.indexOf(",\"errorMsg\""));
				if(null!=rs&&rs.trim().length()>0){
					ps = gson.fromJson(rs, new TypeToken<List<MData>>(){}.getType());
				}
				
			}
			return ps;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return null;
    }
    
    
    public static void showMain(){
    	mainWin=new MainWin();
    	mainWin.setVisible(true);
    }
    
	public static String getUname() {
		return uname;
	}


	public static void setUname(String uname) {
		App.uname = uname;
	}


	public static String getUpass() {
		return upass;
	}


	public static void setUpass(String upass) {
		App.upass = upass;
	}
	 
}
