package com.thang;

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

public class MySaver implements Runnable {

	private String cName=null;
	public static String action="https://crm.alibaba-inc.com";
	private static final String biao="https://crm.alibaba-inc.com/noah/platform/customer/createCustomer.xul?&from=caenir";
	public MySaver(String s){
		this.cName=s;
	}
	
	public static void saveCrm(String name){
		try{
    	    HttpGet biao_get=new HttpGet(biao);
			HttpResponse biao_resp=Weber.httpclient.execute(biao_get);
			
			String biaoPage=EntityUtils.toString(biao_resp.getEntity(),Consts.UTF_8);
			EntityUtils.consume(biao_resp.getEntity());
			
			
			    List<NameValuePair> input=new ArrayList<NameValuePair>(5);
				action+=biaoPage.substring(biaoPage.indexOf("createCustomerAction=shy.mixin")+46, biaoPage.indexOf("'),{createCustomer", biaoPage.indexOf("createCustomerAction=shy.mixin")));
				HttpPost hpost_form=new HttpPost(action);
					String params="[{\"$rid\":\"\",\"phoneCountryCode\":\"86\",\"faxCountryCode\":\"86\",\"mobileCountryCode\":\"86\",\"depotLeadsSource2\":\"97080\",\"type\":\"enterprise\",\"country\":\"CN\",\"productLine\":\"1\",\"companyName\":\""+name+"\",\"corporateRepresent\":\""+Weber.person+"\",\"phoneAreaCode\":\"021\",\"phoneNumber\":\""+Weber.phone+"\",\"mobilePhoneNumber\":null,\"faxAreaCode\":null,\"faxPhoneNumber\":null,\"email\":null,\"select_countryField_zFkat\":\"CN\",\"distType\":\"owner\",\"distTarget\":\""+Weber.uname+"\"}]";	
				
					input.add(new BasicNameValuePair("_args_",params));
					input.add(new BasicNameValuePair("_id_","6oWypQ"));
					input.add(new BasicNameValuePair("t__",String.valueOf(Math.random())));
						hpost_form.setEntity(new UrlEncodedFormEntity(input,"UTF-8"));
						
						HttpResponse resp_form=Weber.httpclient.execute(hpost_form);
						if(200==resp_form.getStatusLine().getStatusCode()){
							String saveResult=EntityUtils.toString(resp_form.getEntity());
							EntityUtils.consume(resp_form.getEntity());//���Ͽ�����
							//System.out.println("CrmWeber line:363 result="+saveResult);
							
							if(saveResult.contains("�ַ�Ŀ��Ŀ�������")){
								System.out.println(name+"     �ַ�Ŀ��Ŀ���������     (���û����аѿ�����Ч����ɾ��!ϵͳ�������´α���.)            \n");
							}else if(saveResult.contains("��˾���Ͳֿ��еĿͻ��ظ�")){
								System.out.println(name+"     ��˾���Ͳֿ��еĿͻ��ظ���      (ϵͳ��ȡ���ñ���ü�¼)             \n");
								Weber.saveCompany.add(name);
							}else if(saveResult.contains("�����еĿͻ��ظ�")){
								System.out.println(name+"  ��˾���͹����еĿͻ��ظ�����ֱ�����룡");
								Weber.saveCompany.add(name);
							}else{
								Weber.saveCompany.add(name);
								System.out.println(name+"       ����ɹ���\n");
							}
						}else{
							System.out.println("�����쳣��");
							EntityUtils.consume(resp_form.getEntity());//���Ͽ�����
						}
	     }catch(Exception e){
	    	 e.printStackTrace();
	     }
	}
	
	
	@Override
	public void run() {

     try{
    	    HttpGet biao_get=new HttpGet(biao);
			HttpResponse biao_resp=Weber.httpclient.execute(biao_get);
			
			String biaoPage=EntityUtils.toString(biao_resp.getEntity(),Consts.UTF_8);
			EntityUtils.consume(biao_resp.getEntity());
			
			    List<NameValuePair> inputs=new ArrayList<NameValuePair>(5);
				action+=biaoPage.substring(biaoPage.indexOf("createCustomerAction=shy.mixin")+46, biaoPage.indexOf("'),{createCustomer", biaoPage.indexOf("createCustomerAction=shy.mixin")));
				HttpPost hpost_form=new HttpPost(action);
					String params="[{\"$rid\":\"\",\"phoneCountryCode\":\"86\",\"faxCountryCode\":\"86\",\"mobileCountryCode\":\"86\",\"depotLeadsSource2\":\"97080\",\"type\":\"enterprise\",\"country\":\"CN\",\"productLine\":\"1\",\"companyName\":\""+cName+"\",\"corporateRepresent\":\""+Weber.person+"\",\"phoneAreaCode\":\"021\",\"phoneNumber\":\""+Weber.phone+"\",\"mobilePhoneNumber\":null,\"faxAreaCode\":null,\"faxPhoneNumber\":null,\"email\":null,\"select_countryField_zFkat\":\"CN\",\"distType\":\"owner\",\"distTarget\":\""+Weber.uname+"\"}]";	
				
						inputs.add(new BasicNameValuePair("_args_",params));
						inputs.add(new BasicNameValuePair("_id_","6oWypQ"));
						inputs.add(new BasicNameValuePair("t__",String.valueOf(Math.random())));
						hpost_form.setEntity(new UrlEncodedFormEntity(inputs,"UTF-8"));
						
						HttpResponse resp_form=Weber.httpclient.execute(hpost_form);
						if(200==resp_form.getStatusLine().getStatusCode()){
							String saveResult=EntityUtils.toString(resp_form.getEntity());
							EntityUtils.consume(resp_form.getEntity());//���Ͽ�����
							//System.out.println("CrmWeber line:363 result="+saveResult);
							
							if(saveResult.contains("�ַ�Ŀ��Ŀ�������")){
								System.out.println(cName+"     �ַ�Ŀ��Ŀ���������     (���û����аѿ�����Ч����ɾ��!ϵͳ�������´α���.)            \n");
							}else if(saveResult.contains("��˾���Ͳֿ��еĿͻ��ظ�")){
								System.out.println(cName+"     ��˾���Ͳֿ��еĿͻ��ظ���      (ϵͳ��ȡ���ñ���ü�¼)             \n");
								Weber.saveCompany.add(cName);
							}else if(saveResult.contains("�����еĿͻ��ظ�")){
								System.out.println(cName+"  ��˾���͹����еĿͻ��ظ�����ֱ�����룡");
								Weber.saveCompany.add(cName);
							}else{
								Weber.saveCompany.add(cName);
								System.out.println(cName+"       ����ɹ���\n");
							}
						}else{
							System.out.println("�����쳣��");
							EntityUtils.consume(resp_form.getEntity());//���Ͽ�����
						}
	     }catch(Exception e){
	    	 e.printStackTrace();
	     }
	}
						
}
