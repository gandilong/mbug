package com.thang.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.thang.App;
import com.thang.model.HData;
import com.thang.model.MData;
import com.thang.tools.DateUtils;

public class MainWin  extends javax.swing.JFrame {  
	
	
	private static final long serialVersionUID = 4157836233111696345L;
	private static StringBuffer sber=null;
	private static Thread task=null;
	private static HashSet<String> companys=null;
	
	private static MyTask myTask=null;
	public MainWin() {
		
		sber=new StringBuffer();
		sber.append("金属\n");
		sber.append("贸易\n");
		sber.append("泵\n");
		sber.append("液压\n");
		sber.append("紧固件\n");
		sber.append("阀\n");
		sber.append("包装\n");
		sber.append("薄膜\n");
		sber.append("印\n");
		sber.append("胶\n");
		sber.append("塑\n");
		sber.append("工业\n");
		sber.append("纸\n");
		sber.append("传动\n");
		sber.append("仪\n");
		sber.append("精细\n");
		sber.append("高分子\n");
		sber.append("自动化\n");
		sber.append("自动化=流体\n");
		sber.append("流体\n");
		sber.append("铝\n");
		sber.append("机电\n");
		sber.append("化工\n");
		sber.append("电器\n");
		sber.append("办公家具\n");
		sber.append("电缆\n");
		sber.append("木制品\n");
		sber.append("实业\n");
		sber.append("商贸\n");
		sber.append("科技\n");
		sber.append("设备");
		
		
		companys=new HashSet<String>();
		
        initComponents();
        timeNum.setVisible(false);
        this.setLocationRelativeTo(null);
        
       
        myTask=new MyTask();
        this.addWindowListener(new WindowAdapter() {
        	
        	@Override
        	public void windowClosing(WindowEvent e) {
        		if(null!=task){
        			task.stop();
        		}
        		super.windowClosing(e);
        	}
        	
        	@Override
        	public void windowOpened(WindowEvent e) {
        		super.windowOpened(e);
        	}
        	
		});
        
        keywordText.setText(sber.toString());
        keywordText.setEditable(false);
        sber.delete(0, sber.length());
    }

	public static void showCompany(String s){
		if(!companys.contains(s)){
			companys.add(s);
			sber.append(s);
			sber.append("\n");
			companyText.setText(sber.toString());
		}
		
	}
	
   @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        keywordText = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        companyText = new javax.swing.JTextArea();
        beginBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        timeNum = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        keywordText.setColumns(20);
        keywordText.setRows(5);
        jScrollPane1.setViewportView(keywordText);

        companyText.setColumns(20);
        companyText.setRows(5);
        jScrollPane2.setViewportView(companyText);

        beginBtn.setText("开始扫描");
        beginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beginBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(timeNum, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(beginBtn)
                        .addGap(43, 43, 43)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(beginBtn)
                    .addComponent(jLabel1)
                    .addComponent(timeNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

   public String[] getKeywords(){
	   if(null!=keywordText.getText()&&keywordText.getText().trim().length()>0){
		   return keywordText.getText().split("\n");   
	   }
	   return null;
   }
   
 
    private void beginBtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
    	if(!myTask.isToRun()){
    		beginBtn.setText("停止扫描");
    		myTask.setToRun(true);
    		if(null==task){
    			task=new Thread(myTask);
    			task.start();
    		}
    	}else{
    		myTask.setToRun(false);
    		beginBtn.setText("正在停止扫描...");
    	}
    	
    }                                     
// Variables declaration - do not modify                     
private javax.swing.JButton beginBtn;
private static javax.swing.JTextArea companyText;
private javax.swing.JLabel jLabel1;
private javax.swing.JScrollPane jScrollPane1;
private javax.swing.JScrollPane jScrollPane2;
private javax.swing.JTextArea keywordText;
private javax.swing.JTextField timeNum;

class MyTask implements Runnable{

	public boolean toRun=false;
	
	@Override
	public void run() {
		
       while(true){
    	  if(toRun){
		    	  String[] keys=getKeywords();
		    	  if(null!=keys&&keys.length>0){
		    		  for(String key:keys){
			    		  try{
			    	    	  Thread.sleep(800);
			    	    	}catch(Exception e){
			    	    		e.printStackTrace();
			    	    	}
			    		if(!toRun){
			    			break;
			    		}
						List<HData> data=App.queryForm(key);
				    	
				    	if(null!=data&&data.size()>0){
				    		for(HData d:data){
				    			if(!DateUtils.isBig(d.getGmtCreate())){
				    				showCompany(d.getCompanyName());
				    			}
				    		}
				    	}
			    	   }//for end
		    	  }
		    	 
		    	
    	  }else{
    		  beginBtn.setText("启动扫描");
    	  }
    	  
       }// while end
	}//run end
	
	public boolean isToRun() {
		return toRun;
	}



	public void setToRun(boolean toRun) {
		this.toRun = toRun;
	}
	



	@Deprecated
	public void pickIt(MData md){
		DefaultHttpClient client=App.getDefaultHttpClient();
		
		try{
		    HttpGet hget=new HttpGet("https://crm.alibaba-inc.com/noah/opportunity/pickInfo.cxul?globalId="+md.getGlobalId()+"&from=leads");
		
		    HttpResponse hr=client.execute(hget);
		    String html=EntityUtils.toString(hr.getEntity());
		    EntityUtils.consume(hr.getEntity());
		    
		    if(null!=html&&html.trim().length()>0){
		    	int k=html.indexOf("rpc:shy.mixin(shy.createRPC('");
		    	String tiaoURL=html.substring(k+29, html.indexOf(".do",k));
		    	System.out.println("tiaoURL:"+tiaoURL);
		    	
		    	String postURL="https://crm.alibaba-inc.com"+tiaoURL;
		    	HttpPost hpost=new HttpPost(postURL);
		    	
		    	List<NameValuePair> inputs=new ArrayList<NameValuePair>();
		    	
		    	String params="[{\"errorMessage\":null,\"groupDefault\":null,"
		    			+ "\"selectCompanyName\":\"上海天航售票中心\",\"sourceDefault\":\"95095\","
		    			+ "\"vLeadSourceGroup\":[{\"name\":\"内部员工开源\",\"value\":\"70\"},"
		    			+ "{\"name\":\"培育类（清洗leads/短信/邮件/线上）\",\"value\":\"60\"},"
		    			+ "{\"name\":\"渠道销售专用\",\"value\":\"90\"},{\"name\":\"外部渠道（工商/政府合作/专业市场）\","
		    			+ "\"value\":\"50\"}],\"canSelectOppList\":[{\"loginId\":null,\"lastOperationType\":null,"
		    			+ "\"custRelType\":null,\"statusName\":\"活跃\",\"cityName\":\"上海\",\"lastOperationDesc\":null,\"street\":\"上海\","
		    			+ "\"phoneAreaCode\":\"021\",\"provinceName\":\"上海\",\"canPick\":null,\"productType\":\"1\","
		    			+ "\"city\":\"2611\",\"orgId\":\"30\",\"phoneNumber\":\"32033323\",\"phoneExt\":null,"
		    			+ "\"nickName\":null,\"opportunityType\":null,\"province\":\"2610\",\"custmemo\""
		    			+ ":null,\"oppMemo\":null,\"originName\":\"新注册及网上申请表单-网上免费会员\",\"ownerName\":null,"
		    			+ "\"phoneCountryCode\":\"86\",\"status\":\"active\",\"oppGmtCreated\":\"2012-07-09\","
		    			+ "\"companyName\":\"上海天航售票中心\",\"maturityName\":\"0.暂未定成熟度\",\"country\":null,"
		    			+ "\"mobileCountryCode\":null,\"email\":null,\"lastOperationDescName\":\"\","
		    			+ "\"memberId\":\""+md.getMemberId()+"\",\"customerStatus\":null,"
		    			+ "\"corporateRepresent\":null,\"gmtLastOperate\":\""+md.getGmtlastOperate()+"\","
		    			+ "\"maturity\":\"999\",\"ownerId\":null,\"oppId\":\""+md.getOpportunityId()+"\","
		    			+ "\"mobilePhoneNum\":null,\"identity\":null,\"customerId\":null,"
		    			+ "\"productTypeName\":\"诚信通\",\"district\":\"2612\",\"renewFlag\":\"n\","
		    			+ "\"site\":null,\"customerType\":null,\"website\":null,\"origin\":\"4\","
		    			+ "\"globalId\":\""+md.getGlobalId()+"\",\"orgName\":\""+md.getOrgFullNamePath()+"\""
		    			+ ",\"udbId\":null,\"companyId\":null,\"gmtContact\":null,\"busiRegNumber\":null,"
		    			+ "\"$rid\":\"_0\",\"$index\":0}],\"specicalSalesDefault\":null,\"from\":\"leads\","
		    			+ "\"isProtected\":\"N\",\"doConflictCheck\":\"N\",\"sourceDefaultType\":\"90\","
		    			+ "\"specicalSales\":null,\"nickName\":\"cbu-panpan521\",\"rankDefault\":null,"
		    			+ "\"isIncall\":null,\"isVasSeaSales\":null,\"group\":null,\"selectMemberId\":\""+md.getMemberId()+"\""
		    			+ ",\"$rid\":\"\",\"userType\":\"sepcialSales\",\"salesCheckBinding\":\"true\","
		    			+ "\"groupCheckBinding\":\"false\",\"depotLeadsSource3\":\"96770\",\"salesType\":\"\","
		    			+ "\"selectedOpp\":[{\"loginId\":null,\"lastOperationType\":null,\"custRelType\":null,"
		    			+ "\"statusName\":\"活跃\",\"cityName\":\""+md.getCityName()+"\",\"lastOperationDesc\":null,"
		    			+ "\"street\":\"上海\",\"phoneAreaCode\":\"021\",\"provinceName\":\""+md.getProvinceName()+"\","
		    			+ "\"canPick\":null,\"productType\":\"1\",\"city\":\""+md.getCity()+"\","
		    			+ "\"orgId\":\""+md.getOrgId()+"\",\"phoneNumber\":\"32033323\",\"phoneExt\":null,"
		    			+ "\"nickName\":null,\"opportunityType\":null,\"province\":\""+md.getProvince()+"\","
		    			+ "\"custmemo\":null,\"oppMemo\":null,\"originName\":\"新注册及网上申请表单-网上免费会员\",\"ownerName\":null,\"phoneCountryCode\":\"86\","
		    			+ "\"status\":\"active\",\"oppGmtCreated\":\"2013-07-09\",\"companyName\":\""+md.getCompanyName()+"\",\"maturityName\":\"0.暂未定成熟度\",\"country\":null,"
		    			+ "\"mobileCountryCode\":null,\"email\":null,\"lastOperationDescName\":\"\",\"memberId\":\""+md.getMemberId()+"\",\"customerStatus\":null,"
		    			+ "\"corporateRepresent\":null,\"gmtLastOperate\":\""+md.getGmtlastOperate()+"\",\"maturity\":\"999\",\"ownerId\":null,"
		    			+ "\"oppId\":\""+md.getOpportunityId()+"\",\"mobilePhoneNum\":null,\"identity\":null,\"customerId\":null,\"productTypeName\":\"诚信通\","
		    			+ "\"district\":\"2612\",\"renewFlag\":\"n\",\"site\":null,\"customerType\":null,\"website\":null,\"origin\":\"4\","
		    			+ "\"globalId\":\""+md.getGlobalId()+"\",\"orgName\":\"/Alibaba/诚信通/销售\",\"udbId\":null,\"companyId\":null,\"gmtContact\":null,"
		    			+ "\"busiRegNumber\":null,\"$rid\":\"_0\",\"$index\":0}],\"depotLeadsSourceFinal\":\"96770\"}]";
		    	
		    	System.out.println(params);
		    	inputs.add(new BasicNameValuePair("_args_",params));
				inputs.add(new BasicNameValuePair("_id_","ESHCRQ"));
				inputs.add(new BasicNameValuePair("t__",String.valueOf(Math.random())));

				hpost.setEntity(new UrlEncodedFormEntity(inputs,"UTF-8"));
		    	
		    	HttpResponse resp=client.execute(hpost);
		    }
		    
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}//mytask end

}


