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
		sber.append("����\n");
		sber.append("ó��\n");
		sber.append("��\n");
		sber.append("Һѹ\n");
		sber.append("���̼�\n");
		sber.append("��\n");
		sber.append("��װ\n");
		sber.append("��Ĥ\n");
		sber.append("ӡ\n");
		sber.append("��\n");
		sber.append("��\n");
		sber.append("��ҵ\n");
		sber.append("ֽ\n");
		sber.append("����\n");
		sber.append("��\n");
		sber.append("��ϸ\n");
		sber.append("�߷���\n");
		sber.append("�Զ���\n");
		sber.append("�Զ���=����\n");
		sber.append("����\n");
		sber.append("��\n");
		sber.append("����\n");
		sber.append("����\n");
		sber.append("����\n");
		sber.append("�칫�Ҿ�\n");
		sber.append("����\n");
		sber.append("ľ��Ʒ\n");
		sber.append("ʵҵ\n");
		sber.append("��ó\n");
		sber.append("�Ƽ�\n");
		sber.append("�豸");
		
		
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

        beginBtn.setText("��ʼɨ��");
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
    		beginBtn.setText("ֹͣɨ��");
    		myTask.setToRun(true);
    		if(null==task){
    			task=new Thread(myTask);
    			task.start();
    		}
    	}else{
    		myTask.setToRun(false);
    		beginBtn.setText("����ֹͣɨ��...");
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
    		  beginBtn.setText("����ɨ��");
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
		    			+ "\"selectCompanyName\":\"�Ϻ��캽��Ʊ����\",\"sourceDefault\":\"95095\","
		    			+ "\"vLeadSourceGroup\":[{\"name\":\"�ڲ�Ա����Դ\",\"value\":\"70\"},"
		    			+ "{\"name\":\"�����ࣨ��ϴleads/����/�ʼ�/���ϣ�\",\"value\":\"60\"},"
		    			+ "{\"name\":\"��������ר��\",\"value\":\"90\"},{\"name\":\"�ⲿ����������/��������/רҵ�г���\","
		    			+ "\"value\":\"50\"}],\"canSelectOppList\":[{\"loginId\":null,\"lastOperationType\":null,"
		    			+ "\"custRelType\":null,\"statusName\":\"��Ծ\",\"cityName\":\"�Ϻ�\",\"lastOperationDesc\":null,\"street\":\"�Ϻ�\","
		    			+ "\"phoneAreaCode\":\"021\",\"provinceName\":\"�Ϻ�\",\"canPick\":null,\"productType\":\"1\","
		    			+ "\"city\":\"2611\",\"orgId\":\"30\",\"phoneNumber\":\"32033323\",\"phoneExt\":null,"
		    			+ "\"nickName\":null,\"opportunityType\":null,\"province\":\"2610\",\"custmemo\""
		    			+ ":null,\"oppMemo\":null,\"originName\":\"��ע�ἰ���������-������ѻ�Ա\",\"ownerName\":null,"
		    			+ "\"phoneCountryCode\":\"86\",\"status\":\"active\",\"oppGmtCreated\":\"2012-07-09\","
		    			+ "\"companyName\":\"�Ϻ��캽��Ʊ����\",\"maturityName\":\"0.��δ�������\",\"country\":null,"
		    			+ "\"mobileCountryCode\":null,\"email\":null,\"lastOperationDescName\":\"\","
		    			+ "\"memberId\":\""+md.getMemberId()+"\",\"customerStatus\":null,"
		    			+ "\"corporateRepresent\":null,\"gmtLastOperate\":\""+md.getGmtlastOperate()+"\","
		    			+ "\"maturity\":\"999\",\"ownerId\":null,\"oppId\":\""+md.getOpportunityId()+"\","
		    			+ "\"mobilePhoneNum\":null,\"identity\":null,\"customerId\":null,"
		    			+ "\"productTypeName\":\"����ͨ\",\"district\":\"2612\",\"renewFlag\":\"n\","
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
		    			+ "\"statusName\":\"��Ծ\",\"cityName\":\""+md.getCityName()+"\",\"lastOperationDesc\":null,"
		    			+ "\"street\":\"�Ϻ�\",\"phoneAreaCode\":\"021\",\"provinceName\":\""+md.getProvinceName()+"\","
		    			+ "\"canPick\":null,\"productType\":\"1\",\"city\":\""+md.getCity()+"\","
		    			+ "\"orgId\":\""+md.getOrgId()+"\",\"phoneNumber\":\"32033323\",\"phoneExt\":null,"
		    			+ "\"nickName\":null,\"opportunityType\":null,\"province\":\""+md.getProvince()+"\","
		    			+ "\"custmemo\":null,\"oppMemo\":null,\"originName\":\"��ע�ἰ���������-������ѻ�Ա\",\"ownerName\":null,\"phoneCountryCode\":\"86\","
		    			+ "\"status\":\"active\",\"oppGmtCreated\":\"2013-07-09\",\"companyName\":\""+md.getCompanyName()+"\",\"maturityName\":\"0.��δ�������\",\"country\":null,"
		    			+ "\"mobileCountryCode\":null,\"email\":null,\"lastOperationDescName\":\"\",\"memberId\":\""+md.getMemberId()+"\",\"customerStatus\":null,"
		    			+ "\"corporateRepresent\":null,\"gmtLastOperate\":\""+md.getGmtlastOperate()+"\",\"maturity\":\"999\",\"ownerId\":null,"
		    			+ "\"oppId\":\""+md.getOpportunityId()+"\",\"mobilePhoneNum\":null,\"identity\":null,\"customerId\":null,\"productTypeName\":\"����ͨ\","
		    			+ "\"district\":\"2612\",\"renewFlag\":\"n\",\"site\":null,\"customerType\":null,\"website\":null,\"origin\":\"4\","
		    			+ "\"globalId\":\""+md.getGlobalId()+"\",\"orgName\":\"/Alibaba/����ͨ/����\",\"udbId\":null,\"companyId\":null,\"gmtContact\":null,"
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


