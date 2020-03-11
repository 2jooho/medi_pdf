package pdf_test;




import org.apache.pdfbox.pdmodel.PDDocument;

import pdf_test.gui;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;



//저장위치 및 이름 지정, edgc_pdf 다운로드, 최종 pdf 생성 및 저장 클래스
public class mixpage {
	
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://best54.cafe24.com/best54?serverTimezone=UTC";
    static final String USERNAME = "best54";
    static final String PASSWORD = "medi1607!";
    
   static String sql_url=null;
    static String fileName=null;
    final static int size = 1024;
    public Map<Integer,String> gene = new HashMap<Integer, String>();

  /*  public static void main(String[] args) throws Exception {
        jfreechart_test barchart=new jfreechart_test();
        pdf_table pdf_create=new pdf_table();

        addfolder();
        strem();

        barchart.writeChartToPDF2();
        pdf_create.total_pdf();
        addDocument();
    }*/
    //edgc_pdf url 다운로드 및 저장 함수
    public static void strem() throws Exception  {
            String sql0=null;
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            
            gui gui=new gui();
           String id=gui.output();
           //db에서 url 추출
            try {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
                sql0="select * from order_table where barcode="+id;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql0);
                if(rs.next())
                {
                    sql_url = rs.getString("download_url");
                }
            }catch (SQLException e) {
                System.out.println("SQL Error : " + e.getMessage());
            } catch (ClassNotFoundException e1) {
                System.out.println("[JDBC Connector Driver 오류 : " + e1.getMessage() + "]");
            }

            //다운로드 위치 및 이름지정
        try(InputStream in = new URL(sql_url).openStream()){
            Files.copy(in, Paths.get(addfolder()+"\\(edgc)"+id+".pdf"), StandardCopyOption.REPLACE_EXISTING);
        }
        sql_url=null;
        rs.close();
        stmt.close();
        conn.close();
    }
    //저장위치 및 폴더 생성 함수
    public static String addfolder() throws Exception {
        gui gui=new gui();
        String title=gui.output();
        String title2=gui.output2();
        String path = "D:\\jooho\\고객 유전자pdf파일\\"+title+"_"+title2;

        File Folder = new File(path);
        if (!Folder.exists()) {
            try{
                Folder.mkdir(); //폴더 생성합니다.
                System.out.println("폴더가 생성되었습니다.");
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }else {
            System.out.println("이미 폴더가 생성되어 있습니다.");
        }
        return path;
    }
    
    //기존 edgc_pdf에 메디프레소pdf 삽입 함수
    public static void addDocument() throws Exception {
    	 gui gui=new gui();
         String id=gui.output();
       
        // create create=new create();
        //String title=create.getOrder_id();
        // String url=create.getDownload_url();

        //pdf위치 지정 변수
        String src = addfolder()+"\\(edgc)"+id+".pdf";
        String src2 = addfolder()+"\\(medi)"+id+".pdf";
        //pdf파일 load
        File file1 = new File(src);
        PDDocument doc1 = PDDocument.load(file1);
        //PdfReader doc1=new PdfReader(new URL(src).openStream());
        File file2 = new File(src2);
        PDDocument doc2 = PDDocument.load(file2);

        //new pdf 생성
        PDDocument doc_new = new PDDocument();

        //Setting the destination pdfDoc
        //페이지별 해당 pdf넣기
       for(int i=0;i<=2;i++) {
            if(doc1.getPage(i)!=null)doc_new.addPage(doc1.getPage(i));
        }
       //3,4쪽에 medi_pdf 1,2쪽 삽입 
        if(doc2.getPage(0)!=null)doc_new.addPage(doc2.getPage(0));
        if(doc2.getPage(1)!=null)doc_new.addPage(doc2.getPage(1));
        //edgc_pdf19쪽까지 삽입
        for(int i=3;i<=19;i++) {
            if(doc1.getPage(i)!=null)doc_new.addPage(doc1.getPage(i));
        }
        //medi_pdf 3,4쪽 삽입
        if(doc2.getPage(2)!=null)doc_new.addPage(doc2.getPage(2));
        if(doc2.getPage(3)!=null)doc_new.addPage(doc2.getPage(3));
        
        //save pdfDoc
        doc_new.save( addfolder() + "\\(total)"+id+".pdf");

        System.out.println("Document saved");
        
        //Closing the documents
        doc_new.close();
        doc1.close();
        doc2.close();


    }

}