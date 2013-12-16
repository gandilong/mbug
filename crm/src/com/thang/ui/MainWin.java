package com.thang.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.List;

import com.thang.App;
import com.thang.model.MData;
import com.thang.tools.DateUtils;

public class MainWin  extends javax.swing.JFrame {  
	
	
	private static final long serialVersionUID = 4157836233111696345L;
	private static StringBuffer sber=null;
	private static boolean running=false;
	private static Thread task=null;
	private static HashSet<String> companys=null;
	
	public MainWin() {
		sber=new StringBuffer();
		companys=new HashSet<String>();
        initComponents();
        timeNum.setVisible(false);
        this.setLocationRelativeTo(null);
        
        
        this.addWindowListener(new WindowAdapter() {
        	
        	@Override
        	public void windowClosing(WindowEvent e) {
        		if(null!=task){
        			task.stop();
        		}
        		super.windowClosing(e);
        	}
        	
		});
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

        beginBtn.setText("ø™ º…®√Ë");
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
    	System.out.println("=running="+running);
    	if(!running){
    		beginBtn.setText("Õ£÷π…®√Ë");
    		running=true;
    		
    		if(null==task){
    			task=new Thread(new MyTask());
    			task.start();
    		}
    		
    	}else{
    		beginBtn.setText("∆Ù∂Ø…®√Ë");
    		running=false;
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

	
	
	@Override
	public void run() {
		
       while(true){
    	   System.out.println("thread running="+running);
    	  if(running){
		    	 
		    	   String[] keys=getKeywords();
		    	  for(String key:keys){
		    		  try{
		    	    	  Thread.sleep(1000);
		    	    	}catch(Exception e){
		    	    		e.printStackTrace();
		    	    	}
					List<MData> data=App.queryForm(key);
			    	
			    	if(null!=data&&data.size()>0){
			    		for(MData d:data){
			    			if(null!=d.getGmtlastOperate()&&d.getGmtlastOperate().trim().length()>0){
			    				if(DateUtils.isBig(d.getGmtlastOperate())){
			    					showCompany(d.getCompanyName());
			    				}
			    			}else{
			    			    showCompany(d.getCompanyName());
			    			}
			    		}
			    	}
		    	   }//for end
		    	
    	  }//if end
    	  
       }// while end
	}//run end
	
}//mytask end

}


