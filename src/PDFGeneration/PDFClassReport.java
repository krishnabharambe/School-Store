/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PDFGeneration;

import Database.db;
import static DbController.indiSalesTranscationController.getindiSalesTranscation;
import static DbController.indiSalesTranscationController.getindiSalesTranscationTotal;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class PDFClassReport {

    Font H16 = new Font(Font.FontFamily.HELVETICA, 16, Font.NORMAL, BaseColor.BLACK);
    Font H16B = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLACK);
    Font H15 = new Font(Font.FontFamily.HELVETICA, 15, Font.NORMAL, BaseColor.BLACK);
    Font H15B = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD, BaseColor.BLACK);
    Font H14 = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL, BaseColor.BLACK);
    Font H14B = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
    Font H13 = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL, BaseColor.BLACK);
    Font H13B = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD, BaseColor.BLACK);
    Font H12 = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
    Font H12B = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    Font H11 = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
    Font H11B = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK);
    Font H10 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
    Font H10B = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    Font H9 = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);
    Font H9B = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
    Font H8 = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
    Font H8B = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);

    private static DecimalFormat df2 = new DecimalFormat(".##");
    public static final String IMG1 = "logo.jpg";
    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    String dated = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

    String VcustomeContact = null, Vcustomerphone = null, Vspname = null, Vspphone = null, Vgrandtotal = null, Vddate = dated;
    String DEST = "Report/PDF/Visitor/" + "-" + timestamp + ".pdf";

    public PDFClassReport(String yearsession, String stdsession, String fromdate, String todate) {
        final String Destination = getpath(yearsession, stdsession);
        File file = new File(Destination);
        file.getParentFile().mkdirs();
        createPdf(Destination, yearsession, stdsession, fromdate, todate);

//        createPdf("Report/"+Vquotationno + "-" + timestamp + ".pdf");
    }

    private String getpath(String yearsession, String stdsession) {
        String desktopPath = System.getProperty("user.home") + "/Documents";
        System.out.print(desktopPath.replace("\\", "/"));
        Instant instant = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        String bpath1 = instant.toString().replace("-", "").replace(":", "");
        Date date66 = new Date(System.currentTimeMillis());
        String datenew = date66.toString().replace("-", "").replace(":", "");
        return desktopPath.replace("\\", "/") + "/Dpark/" + yearsession + "/" + stdsession + "/Class Report/" + datenew + bpath1 + ".pdf";
    }

    public static void main(String[] args) {
        new PDFBasketSales("2019-2020", "STD 10 C", "studentName", "6", "total");
    }

    private void createPdf(String Destination, String yearsession, String stdsession, String fromdate, String todate) {
        try {
            // step 1
            Document document = new Document(PageSize.A4);
            // step 2
            PdfWriter.getInstance(document, new FileOutputStream(Destination));
            // step 3
            document.open();
            // step 4
            /**
             * ***
             */

            PdfPTable t1 = new PdfPTable(1);
            t1.setWidthPercentage(100);
            t1.getDefaultCell().setBorder(0);
            PdfPCell p1 = new PdfPCell(new Paragraph("UNIQUE STUDENT STORE", H16B));
            p1.setHorizontalAlignment(Element.ALIGN_CENTER);
            p1.setBorder(0);
            t1.addCell(p1);
            PdfPCell p2 = new PdfPCell(new Paragraph("INDIVIUAL CLASS REPORT", H14B));
            p2.setHorizontalAlignment(Element.ALIGN_CENTER);
            p2.setBorder(0);
            t1.addCell(p2);
            document.add(t1);

            PdfPTable t2 = new PdfPTable(2);
            t2.setWidthPercentage(100);
            t2.getDefaultCell().setBorder(0);
            PdfPCell p3 = new PdfPCell(new Paragraph("SESSION : " + yearsession, H13B));
            p3.setHorizontalAlignment(Element.ALIGN_RIGHT);
            p3.setBorder(0);
            t2.addCell(p3);
            PdfPCell p4 = new PdfPCell(new Paragraph("CLASS : " + stdsession, H13B));
            p4.setHorizontalAlignment(Element.ALIGN_LEFT);
            p4.setBorder(0);
            t2.addCell(p4);

            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
            String date2 = new SimpleDateFormat("dd/MM/yyyy").format(date1);

            PdfPCell p31 = new PdfPCell(new Paragraph("From Date : " + date2, H13B));
            p31.setHorizontalAlignment(Element.ALIGN_RIGHT);
            p31.setBorder(0);
            t2.addCell(p31);

            Date date11 = new SimpleDateFormat("yyyy-MM-dd").parse(todate);
            String date21 = new SimpleDateFormat("dd/MM/yyyy").format(date11);
            PdfPCell p41 = new PdfPCell(new Paragraph("To Date : " + date21, H13B));
            p41.setHorizontalAlignment(Element.ALIGN_LEFT);
            p41.setBorder(0);
            t2.addCell(p41);

            document.add(t2);

            PdfPTable t21 = new PdfPTable(1);
            PdfPCell p5 = new PdfPCell(new Paragraph("REPORT", H14B));
            p5.setHorizontalAlignment(Element.ALIGN_CENTER);
            p5.setBorder(0);
            t21.addCell(p5);
            document.add(t21);

            ResultSet data = db.getdata("Select count(*) as checker from sales2 WHERE yearsessionName = '" + yearsession + "' AND  stdsection = '" + stdsession + "'  AND dated_on BETWEEN '" + fromdate + "' AND '" + todate + "'");
            int counter = data.getInt("checker");
//            while (data.next()) {
//                counter = counter + 1;
//
//            }
            data.close();

            ResultSet data2 = db.getdata("Select * from basketvalue where yearsessionName='" + yearsession + "' AND stdsection='" + stdsession + "'");
            int price = 0;

            price = data2.getInt("basketvalue");

            data2.close();

            Paragraph t3 = new Paragraph("No of Basket Sold : " + counter);
            Paragraph t4 = new Paragraph("Basket Price  : " + price);

            Paragraph t5 = new Paragraph("------------------------");
            Paragraph t6 = new Paragraph("Total : "+counter * price);

            document.add(t3);
            document.add(t4);
            document.add(t5);
            document.add(t6);

            PdfPCell p6 = new PdfPCell(new Paragraph("-------- END --------"));
            p6.setHorizontalAlignment(Element.ALIGN_CENTER);
            document.add(p6);

            /**
             *
             * ***
             */
            // step 5
            document.close();

            /* opening file if exists */
            try {
                if (new File(Destination).exists()) {

                    File myFile = new File(Destination);
                    Desktop.getDesktop().open(myFile);
                    System.out.println(Destination);

                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } catch (DocumentException ex) {
            Logger.getLogger(PDFIndiSales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PDFIndiSales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PDFIndiSales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(PDFClassReport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
