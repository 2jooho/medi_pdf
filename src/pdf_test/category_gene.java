package pdf_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//영어 유전자별->한글 변환 클래스
public class category_gene {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/DB이름?serverTimezone=UTC";
    static final String USERNAME = "";
    static final String PASSWORD = "";
    public ArrayList name_gene=new ArrayList();
    public ArrayList genotype=new ArrayList();
    public ArrayList marker_result=new ArrayList();
    public ArrayList risk=new ArrayList();
    public ArrayList bmi=new ArrayList();
    public ArrayList explanation=new ArrayList();
    
    
   public void explan() throws Exception{
    	String sql1= null;
    	 Connection conn1 = null;
         Statement stmt1 = null;
         ResultSet rs1=null;
         
         try {
             Class.forName(JDBC_DRIVER);
         conn1 = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
         stmt1 = conn1.createStatement();

         for(int i=0;i<name_gene.size();i++) {
         sql1="select * from gene_explanation where gene='"+name_gene.get(i).toString()+"'";
         rs1 = stmt1.executeQuery(sql1);
         if(rs1.next())
       	  explanation.add(rs1.getString("explanation"));
         }

    }catch (SQLException e) {
        System.out.println("SQL Error : " + e.getMessage());
    } catch (ClassNotFoundException e1) {
        System.out.println("[JDBC Connector Driver 오류 : " + e1.getMessage() + "]");
    }
    }
    
    
    public void gene() throws Exception {
    	
    	gui gui=new gui();
        String id=gui.output();
    	String sql0= null, sql1=null;
    	
    	 Connection conn0 = null;
         Statement stmt0 = null,stmt1=null;
         ResultSet rs0=null,rs1=null;
         
         
         try {
             Class.forName(JDBC_DRIVER);
         conn0 = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
         sql0="select * from gene_table where barcode='"+id+"' AND marker_result='높음'";  //OR marker_result='다소 높음'
         

         
         stmt0 = conn0.createStatement();
         rs0 = stmt0.executeQuery(sql0);

        
          
          while(rs0.next())
          {
        	
        	  name_gene.add(rs0.getString("name_gene"));
        	  genotype.add(rs0.getString("genotype"));
        	  marker_result.add(rs0.getString("marker_result"));
        	  risk.add(rs0.getString("risk"));
    }
 
          for(int i=0;i<name_gene.size();i++)
          {
        	if(name_gene.get(i).toString().equals("FTO")||name_gene.get(i).toString().equals("MC4R")||name_gene.get(i).toString().equals("BDNF"))
        		bmi.add("체질량");
        	
        	else if(name_gene.get(i).toString().equals("GCKR")||name_gene.get(i).toString().equals("TBL2")||
        			name_gene.get(i).toString().equals("DOCK7 (1)")||name_gene.get(i).toString().equals("MLXIPL")||
        			name_gene.get(i).toString().equals("DOCK7 (2)")||name_gene.get(i).toString().equals("LOC105375745")||
        			name_gene.get(i).toString().equals("ANGPTL3")||name_gene.get(i).toString().equals("TRIB1")||
        			name_gene.get(i).toString().equals("BAZ1B"))
        		bmi.add("중성지방");
        	
        	else if(name_gene.get(i).toString().equals("CELSR2")||name_gene.get(i).toString().equals("MYL2")||
        			name_gene.get(i).toString().equals("HMGCR")||name_gene.get(i).toString().equals("CETP")||
        			name_gene.get(i).toString().equals("ABO")||name_gene.get(i).toString().equals("LIPG")||
        			name_gene.get(i).toString().equals("ABCA1"))
        		bmi.add("콜레스테롤");
        	
        	else if(name_gene.get(i).toString().equals("CDKN2A/B")||name_gene.get(i).toString().equals("MTNR1B")||
        			name_gene.get(i).toString().equals("GCK")||name_gene.get(i).toString().equals("DGKB-TMEM195")||
        			name_gene.get(i).toString().equals("GCKR")||name_gene.get(i).toString().equals("SLC30A8")||
        			name_gene.get(i).toString().equals("GLIS3")||name_gene.get(i).toString().equals("G6PC2"))
        		bmi.add("혈당");
        	
        	else if(name_gene.get(i).toString().equals("ATP2B1")||name_gene.get(i).toString().equals("GUCY1A3")||
        			name_gene.get(i).toString().equals("CSK")||name_gene.get(i).toString().equals("HECTD4")||
        			name_gene.get(i).toString().equals("CYP17A1")||name_gene.get(i).toString().equals("NPR3")||
        			name_gene.get(i).toString().equals("FGF5")||name_gene.get(i).toString().equals("NT5C2"))
        		bmi.add("혈압");
        	
        	else if(name_gene.get(i).toString().equals("AHR")||name_gene.get(i).toString().equals("CYP1A2"))
        		bmi.add("카페인대사");
        	
        	else if(name_gene.get(i).toString().equals("AGER"))
        		bmi.add("피부노화");  
        	
        	else if(name_gene.get(i).toString().equals("MMP1"))
        		bmi.add("피부탄력"); 
        	
        	else if(name_gene.get(i).toString().equals("OCA2 (1)")||name_gene.get(i).toString().equals("OCA2 (2)")||
        			name_gene.get(i).toString().equals("MC1R"))
        		bmi.add("색소침착"); 
        	
        	else if(name_gene.get(i).toString().equals("chr20p11 (1)")||name_gene.get(i).toString().equals("chr20p11 (2)"))
        		bmi.add("남성형 탈모");  
        	
        	else if(name_gene.get(i).toString().equals("IL2RA")||name_gene.get(i).toString().equals("HLA-DQB1"))
        		bmi.add("원형 탈모"); 
        	
        	else if(name_gene.get(i).toString().equals("EDAR"))
        		bmi.add("모발굵기"); 
        	
        	else if(name_gene.get(i).toString().equals("SLC23A1"))
        		bmi.add("비타민 C"); 
        
        	  }
        	  
          
          
} catch (SQLException e) {
    System.out.println("SQL Error : " + e.getMessage());
} catch (ClassNotFoundException e1) {
    System.out.println("[JDBC Connector Driver 오류 : " + e1.getMessage() + "]");
}
}
}