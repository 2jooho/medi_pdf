package pdf_test;


import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleEdge;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;



public class jfreechart {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/DB이름?serverTimezone=UTC";
    static final String USERNAME = "";
    static final String PASSWORD = "";
/*  public static void main(String[] args) {
        writeChartToPDF2();
    }*/
    
    //db추출 및 그래프생성
    public static void writeChartToPDF2() throws Exception {
    	  gui gui=new gui();
    	String ID=gui.output();
        String sql0,sql1,sql2,sql3,sql4,sql5,sql6,sql7;

        String sql_TC=null,sql_body=null,sql_trig=null,sql_glucose=null,sql_pressure=null,sql_skin=null,sql_areata=null,sql_male=null;

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        //개인 유전자 수치 추출
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);


            sql0="select * from disease_table where barcode="+ID +" AND name_disease='TC'";
            sql1="select * from disease_table where barcode="+ID +" AND name_disease='Body Mass Index'";
            sql2="select * from disease_table where barcode="+ID +" AND name_disease='Triglycerides'";
            sql3="select * from disease_table where barcode="+ID +" AND name_disease='Blood Glucose'";
            sql4="select * from disease_table where barcode="+ID +" AND name_disease='Blood Pressure'";
            sql5="select * from disease_table where barcode="+ID +" AND name_disease='Skin Pigmentation'";
            sql6="select * from disease_table where barcode="+ID +" AND name_disease='Alopecia Areata'";
            sql7="select * from disease_table where barcode="+ID +" AND name_disease='Male Pattern Baldness'";
            stmt = conn.createStatement();

            rs = stmt.executeQuery(sql0);
            if(rs.next())
            {
                sql_TC = rs.getString("multiple_score");
            }
            rs = stmt.executeQuery(sql1);
            if(rs.next())
            {
                sql_body = rs.getString("multiple_score");
            }
            rs = stmt.executeQuery(sql2);
            if(rs.next())
            {
                sql_trig = rs.getString("multiple_score");
            }
            rs = stmt.executeQuery(sql3);
            if(rs.next())
            {
                sql_glucose = rs.getString("multiple_score");
            }
            rs = stmt.executeQuery(sql4);
            if(rs.next())
            {
                sql_pressure = rs.getString("multiple_score");
            }
            rs = stmt.executeQuery(sql5);
            if(rs.next())
            {
                sql_skin = rs.getString("multiple_score");
            }
            rs = stmt.executeQuery(sql6);
            if(rs.next())
            {
                sql_areata = rs.getString("multiple_score");
            }

            rs = stmt.executeQuery(sql7);
            if(rs.next())
            {
                sql_male = rs.getString("multiple_score");
            }


        }catch (SQLException e) {
            System.out.println("SQL Error : " + e.getMessage());
        } catch (ClassNotFoundException e1) {
            System.out.println("[JDBC Connector Driver 오류 : " + e1.getMessage() + "]");
        }
        
        //그래프 생성 
        try {
            //데이터 입력
            DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
            dataSet.setValue(Double.parseDouble(sql_TC), "본인", "콜레스테롤");
            dataSet.setValue(1.333, "평균", "콜레스테롤");
            dataSet.setValue(Double.parseDouble(sql_body), "본인", "체질량");
            dataSet.setValue(1.160, "평균", "체질량");
            dataSet.setValue(Double.parseDouble(sql_trig), "본인", "중성지방");
            dataSet.setValue(3.308, "평균", "중성지방");
            dataSet.setValue(Double.parseDouble(sql_glucose), "본인", "혈당");
            dataSet.setValue(1.591, "평균", "혈당");
            dataSet.setValue(Double.parseDouble(sql_pressure), "본인", "혈압");
            dataSet.setValue(1.800, "평균", "혈압");
            dataSet.setValue(Double.parseDouble(sql_skin), "본인", "피부색소침착");
            dataSet.setValue(0.960, "평균", "피부색소침착");
            dataSet.setValue(Double.parseDouble(sql_areata), "본인", "원형 탈모");
            dataSet.setValue(0.880, "평균", "원형 탈모");
            dataSet.setValue(Double.parseDouble(sql_male), "본인", "남성형 탈모");
            dataSet.setValue(1.320, "평균", "남성형 탈모");
            
            JFreeChart chart = ChartFactory.createBarChart(
                    "", "", "",
                    dataSet, PlotOrientation.VERTICAL, true, true, false);
            
            // chart.getLegend().setItemFont(new Font("NanumGothicBold", Font.PLAIN, 10));
            
            LegendTitle legend=chart.getLegend();
            legend.setPosition(RectangleEdge.TOP);
            legend.setItemFont(new Font("NanumGothicBold", Font.PLAIN, 10));
            
            //chart.setBackgroundPaint(Color.darkGray);
            //chart.getTitle().setPaint(new Color(102, 102, 102));
            
            CategoryPlot plot = chart.getCategoryPlot();
            plot.setBackgroundPaint(Color.white); //배경색 흰색으로
            plot.setRangeGridlinePaint(Color.gray); //배경 구분선 회색으로
            plot.getDomainAxis().setLabelFont(new Font("NanumGothicBold", Font.BOLD, 13)); //x축 제목
            plot.getDomainAxis().setTickLabelFont(new Font("NanumGothicBold", Font.BOLD, 8)); //x축 범례
            plot.getRangeAxis().setLabelFont(new Font("NanumGothicBold", Font.BOLD, 13)); //y축 제목
            plot.getRangeAxis().setTickLabelFont(new Font("NanumGothicBold", Font.BOLD, 8)); //y축 범례
            
            //렌더링 정보, 이미지 저장경로, 이미지 저장
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            mixpage folder=new mixpage();
            
            //  guifield1 gui=new guifield1();
            //  String title=gui.output();
            //    String fileName =  folder.addfolder()+ "\\(이미지)"+title+".jpeg";
            //그래프 생성위치 및 이름
            String fileName =  folder.addfolder()+"\\(img)"+ID+".jpeg";
            ChartUtilities.saveChartAsJPEG(new File(fileName),chart,600,480,info);
            
            System.out.print("생성 완료");
            //닫기
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e){}

        
    }
    /*
     //차트 생성 
    public static void writeChartToPDF() {

        PdfWriter writer = null;

        Document document = new Document();

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(
                    "D:\\jooho\\piechart.pdf"));
            document.open();
            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(500, 400);
            Graphics2D graphics2d = template.createGraphics(500, 400,
                    new DefaultFontMapper());
            Rectangle2D rectangle2d = new Rectangle2D.Double(0, 50, 300,
                    200);

            generateBarChart().draw(graphics2d, rectangle2d);

            graphics2d.dispose();
            contentByte.addTemplate(template, 0, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }



   //동그란 차트
     public static JFreeChart generatePieChart() {
   

        DefaultPieDataset dataSet = new DefaultPieDataset();
        dataSet.setValue("콜레스테롤", 19.64);
        dataSet.setValue("체질량", 17.3);
        dataSet.setValue("중성지방", 4.54);
        dataSet.setValue("혈당", 3.4);
        dataSet.setValue("혈압", 2.83);
        dataSet.setValue("카페인 대사", 2.48);
        dataSet.setValue("피부 상태 ", 2.38);
        dataSet.setValue("모발 상태", 2.38);
        dataSet.setValue("비타민 c", 2.38);
        JFreeChart chart = ChartFactory.createPieChart(
                "", dataSet, true, false, false);

        chart.setBackgroundPaint(Color.white);
        chart.setBackgroundImageAlpha(0.0f);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("China", Color.black);
        plot.setBackgroundPaint(Color.black);
        //chart.getTitle().setPaint(new Color(102, 102, 102));
        return chart;
    }

//BAR차트
    public static JFreeChart generateBarChart() {

        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        dataSet.setValue(791, "아", "콜레스테롤");
        dataSet.setValue(978, "아", "체질량");
        dataSet.setValue(1262, "아", "중성지방");
        dataSet.setValue(1650, "아", "혈당");
        dataSet.setValue(2519, "아", "혈압");
        dataSet.setValue(6070, "아", "카페인 대사");
        dataSet.setValue(6070, "아", "피부 상태");
        dataSet.setValue(6070, "아", "모발 상태");
        dataSet.setValue(6070, "아", "비타민 C");
        JFreeChart chart = ChartFactory.createBarChart(
                "", "가로", "세로",
                dataSet, PlotOrientation.VERTICAL, true, true, false);

        //chart.setBackgroundPaint(Color.darkGray);
        //chart.getTitle().setPaint(new Color(102, 102, 102));
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white); //배경색 흰색으로
        plot.setRangeGridlinePaint(Color.gray); //배경 구분선 회색으로
        plot.getDomainAxis().setLabelFont(new Font("NanumGothicBold", Font.BOLD, 13)); //x축 제목
        plot.getDomainAxis().setTickLabelFont(new Font("NanumGothicBold", Font.BOLD, 8)); //x축 범례
        plot.getRangeAxis().setLabelFont(new Font("NanumGothicBold", Font.BOLD, 13)); //y축 제목
        plot.getRangeAxis().setTickLabelFont(new Font("NanumGothicBold", Font.BOLD, 8)); //y축 범례
        return chart;
    }*/

}
