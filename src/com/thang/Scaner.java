package com.thang;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.thang.tools.DateUtils;

public class Scaner implements Runnable{

	private DefaultHttpClient hclient=new DefaultHttpClient();
	
	public static final String dataURL="http://www.sgs.gov.cn/shaic/workonline/../appStat!toNameAppList.action";
	
	
	public static String queryDate=DateUtils.formatDate(DateUtils.getLastDatedate(),DateUtils.YYYY_MM_DD);
	public List<NameValuePair> nvps=new ArrayList<NameValuePair>(5);
	
	
	private List<String> toSaveCompany=new ArrayList<String>();
	
	public Scaner(int p){
		nvps.add(new BasicNameValuePair("nameSearchCondition.startDate",queryDate));
		nvps.add(new BasicNameValuePair("nameSearchCondition.endDate",queryDate));
		nvps.add(new BasicNameValuePair("nameSearchCondition.acceptOrgan",""));//全部地区都查
	    nvps.add(new BasicNameValuePair("nameSearchCondition.checkName",Weber.companyName));
	    nvps.add(new BasicNameValuePair("p",String.valueOf(p).trim()));
	}
	
	public void queryImport(){
			String result=null;
			result=getPage();
			Document html=Jsoup.parse(result);
			if(null==html.getElementsByAttributeValue("class", "tgList")||html.getElementsByAttributeValue("class", "tgList").size()<1){
				return;
			}
			Elements trs=html.getElementsByAttributeValue("class", "tgList").get(0).getElementsByTag("tr");
			Elements tds=null;
			
			/**
			 * 解析页面数据
			 */
			int i=0;
			boolean hasKey=false;
			for(Element tr:trs){
				if(0==i){//第一行，标题不解析
					i++;
					continue;
				}
				
				tds=tr.getElementsByTag("td");
				result=tds.get(1).ownText();
				
				for(String keyword:Weber.keywords){//判断是否包含关键字
					if(result.contains(keyword)){
						hasKey=true;
						break;
					}
				}
				
				if(hasKey){
					hasKey=false;
					continue;
				}
				
				if(Weber.saveCompany.contains(result)){
					System.out.println(result+" ==> 数据己处理！");
				    continue;
				}else{
					//toSaveCompany.add(result);
					//Weber.saveCompany.add(result);
					//System.out.println(result);
					Weber.pools.execute(new MySaver(result));
				}
				
			}
			if(toSaveCompany.size()>0){
				//new Saver(toSaveCompany);
			}
			
	}
	
	/**
	 * 根公司名查指定页数的数据
	 * @param cname
	 * @param p
	 * @return
	 */
	public String getPage(){
		String result=null;
		try{
			
			HttpPost hpost=new HttpPost(dataURL);
			hpost.setEntity(new UrlEncodedFormEntity(nvps,Consts.UTF_8));
			HttpResponse response=hclient.execute(hpost);
			result=EntityUtils.toString(response.getEntity());
			EntityUtils.consume(response.getEntity());
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void run() {
		while(true){
			toSaveCompany.clear();
			queryImport();
		}
		
	}

}
