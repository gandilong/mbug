package com.thang;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class Saver {

	private String cname=null;
	public static String action="https://crm.alibaba-inc.com";
	private static final String biao="https://crm.alibaba-inc.com/noah/platform/customer/createCustomer.xul?&from=caenir";
	private HttpGet biao_get=new HttpGet(biao);//得到crm form页面
	private List<NameValuePair> inputs=new ArrayList<NameValuePair>(4);
	
	public Saver(String cname){
		this.cname=cname;
	}
	
	public Saver(List<String> cnames){
		doImport(cnames);
	}
	
	public void doImport(List<String> cNames){
		HttpResponse resp_form=null;
		try{
			
			HttpResponse biao_resp=Weber.httpclient.execute(biao_get);
			
			String biaoPage=EntityUtils.toString(biao_resp.getEntity(),Consts.UTF_8);
			EntityUtils.consume(biao_resp.getEntity());
			int i=0;
			for(String cName:cNames){
				
				if(i%5==0){
					biao_resp=Weber.httpclient.execute(biao_get);
					biaoPage=EntityUtils.toString(biao_resp.getEntity(),Consts.UTF_8);
					EntityUtils.consume(biao_resp.getEntity());
				}
				
				action+=biaoPage.substring(biaoPage.indexOf("createCustomerAction=shy.mixin")+46, biaoPage.indexOf("'),{createCustomer", biaoPage.indexOf("createCustomerAction=shy.mixin")));
				HttpPost hpost_form=new HttpPost(action);
					String params="[{\"$rid\":\"\",\"phoneCountryCode\":\"86\",\"faxCountryCode\":\"86\",\"mobileCountryCode\":\"86\",\"depotLeadsSource2\":\"97080\",\"type\":\"enterprise\",\"country\":\"CN\",\"productLine\":\"1\",\"companyName\":\""+cName+"\",\"corporateRepresent\":\""+Weber.person+"\",\"phoneAreaCode\":\"021\",\"phoneNumber\":\""+Weber.phone+"\",\"mobilePhoneNumber\":null,\"faxAreaCode\":null,\"faxPhoneNumber\":null,\"email\":null,\"select_countryField_zFkat\":\"CN\",\"distType\":\"owner\",\"distTarget\":\""+Weber.uname+"\"}]";	
				
						inputs.add(new BasicNameValuePair("_args_",params));
						inputs.add(new BasicNameValuePair("_id_","6oWypQ"));
						inputs.add(new BasicNameValuePair("t__",String.valueOf(Math.random())));
						hpost_form.setEntity(new UrlEncodedFormEntity(inputs,"UTF-8"));
						
						resp_form=Weber.httpclient.execute(hpost_form);
						if(200==resp_form.getStatusLine().getStatusCode()){
							String saveResult=EntityUtils.toString(resp_form.getEntity());
							EntityUtils.consume(resp_form.getEntity());//不断开连接
							//System.out.println("CrmWeber line:363 result="+saveResult);
							
							if(saveResult.contains("分发目标的库容已满")){
								System.out.println(cName+"     分发目标的库容已满！     (请用户自行把库里无效数据删除!系统将尝试下次保存.)            \n");
							}else if(saveResult.contains("公司名和仓库中的客户重复")){
								System.out.println(cName+"     公司名和仓库中的客户重复！      (系统将取消该保存该记录)             \n");
								Weber.saveCompany.add(cName);
							}else if(saveResult.contains("公海中的客户重复")){
								System.out.println(cName+"  公司名和公海中的客户重复，请直接挑入！");
								Weber.saveCompany.add(cName);
							}else{
								Weber.saveCompany.add(cName);
								System.out.println(cName+"       保存成功！\n");
							}
						}else{
							System.out.println("连接异常！");
							EntityUtils.consume(resp_form.getEntity());//不断开连接
						}
						
						i++;
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				if(null!=resp_form){
					EntityUtils.consume(resp_form.getEntity());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}//不断开连接
			System.out.println("提交数据失败！");
			return;
		}
	}

}
