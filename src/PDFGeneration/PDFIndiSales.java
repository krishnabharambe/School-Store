/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PDFGeneration;

import CMN.commonFile;
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
public class PDFIndiSales {

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

    public PDFIndiSales(String yearsession, String stdsession, String studentName, String SaleId, String total) {
        final String Destination = getpath(yearsession, stdsession);
        File file = new File(Destination);
        file.getParentFile().mkdirs();
        createPdf(Destination, yearsession, stdsession, studentName, SaleId, total);

//        createPdf("Report/"+Vquotationno + "-" + timestamp + ".pdf");
    }

    private String getpath(String yearsession, String stdsession) {
        String desktopPath = System.getProperty("user.home") + "/Documents";
        System.out.print(desktopPath.replace("\\", "/"));
        Instant instant = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        String bpath1 = instant.toString().replace("-", "").replace(":", "");
        Date date66 = new Date(System.currentTimeMillis());
        String datenew = date66.toString().replace("-", "").replace(":", "");
        return desktopPath.replace("\\", "/") + "/Dpark/" + yearsession + "/" + stdsession + "/Individual Sales/" + datenew + bpath1 + ".pdf";
    }

    public static void main(String[] args) {
        new PDFBasketSales("2019-2020", "STD 10 C", "studentName", "6", "total");
    }

    private void createPdf(String Destination, String yearsession, String stdsession, String studentName, String SaleId, String total) {
        try {
            int jk = 0;
            ResultSet re = db.getdata("SElect count(*) as checker FROM IndiSalesPrices Where yearsessionName ='" + yearsession + "' AND stdsection ='" + stdsession + "' AND indisaleid='" + SaleId + "'");
            jk = re.getInt("checker");
            re.close();
            if (jk == 0) {
                String date2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                db.setdata("INSERT INRO IndiSalesPrices(yearsessionName,stdsection,indisaleid,total,updated_on,dated_on,timestamp)"
                        + "VALUES('" + yearsession + "','" + stdsession + "','" + SaleId + "','" + total + "','" + commonFile.Fun_Date() + "','" + date2 + "','" + commonFile.Fun_Timestamp() + "')");

            } else {
                String date2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                db.setdata("UPDATE IndiSalesPrices SET yearsessionName='" + yearsession + "',stdsection='" + stdsession + "',total='" + total + "',updated_on='" + commonFile.Fun_Date() + "' WHERE indisaleid='" + SaleId + "'");
            }

            // step 1
            Document document = new Document(PageSize.A4.rotate(), 20f, 20f, 20f, 20f);
            // step 2
            PdfWriter.getInstance(document, new FileOutputStream(Destination));
            // step 3
            document.open();
            // step 4
            /**
             * ***
             */
            float[] columnWidths1 = {15, 1, 15, 1, 15, 1};
            PdfPTable t1 = new PdfPTable(columnWidths1);
            t1.setWidthPercentage(100);
            t1.getDefaultCell().setBorder(0);
            float[] columnWidths2 = {5};
            PdfPTable t2 = new PdfPTable(columnWidths2);
            t2.setWidthPercentage(100);
            t2.getDefaultCell().setBorder(0);
            PdfPCell cell1 = new PdfPCell(new Paragraph("UNIQUE STUDENT STORE", H12B));
            cell1.setColspan(2);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBorder(0);
            cell1.setPaddingBottom(3);
            t2.addCell(cell1);
            PdfPCell cell2 = new PdfPCell(new Paragraph(" ", H8B));
            cell2.setColspan(2);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setBorder(0);
            t2.addCell(cell2);
            float[] columnWidths3 = {6, 3};
            PdfPTable t3 = new PdfPTable(columnWidths3);
            t3.setWidthPercentage(100);
            t3.getDefaultCell().setBorder(0);
            t3.addCell(new Paragraph("NAME : " + studentName, H8B));
            t3.addCell(new Paragraph("SESSION : " + yearsession, H8B));
            t3.addCell(new Paragraph("STD : " + stdsession, H8B));
            t3.addCell(new Paragraph("DATE : " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()), H8B));
            t3.addCell(new Paragraph("SUN [    ]     MOON [    ]", H8B));
            t3.addCell(new Paragraph("BOY [    ]   GIRL [    ]", H8B));
            t2.addCell(t3);
            t2.addCell(new Paragraph(" "));
            float[] columnWidths4 = {1, 6, 2};
            PdfPTable t4 = new PdfPTable(columnWidths4);
            t4.setWidthPercentage(100);
            t4.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            t4.addCell(new Paragraph("Sr.No", H8B));
            t4.addCell(new Paragraph("Item", H9B));
            t4.addCell(new Paragraph("Qty", H9B));
            t4.getDefaultCell().setPadding(2);
            ResultSet data = getindiSalesTranscation(yearsession, stdsession, SaleId);
            int j = 1;
            while (data.next()) {
                t4.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                t4.addCell(new Paragraph(String.valueOf(j), H9));
                t4.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                t4.addCell(new Paragraph(data.getString("stockName"), H9));
                t4.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                t4.addCell(new Paragraph(data.getString("quantity"), H9));
                j++;
            }
            while (j <= 28) {
                t4.addCell(new Paragraph(" ", H9B));
                t4.addCell(new Paragraph(" ", H9B));
                t4.addCell(new Paragraph(" ", H9B));
                j++;

            }
            data.close();

            t4.addCell(new Paragraph(" ", H9B));
            t4.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            t4.addCell(new Paragraph("Total :", H9B));
            t4.addCell(new Paragraph(String.format("%.2f", getindiSalesTranscationTotal(yearsession, stdsession, SaleId) + 0.00), H9B));
            t2.addCell(t4);
            float[] columnWidths11 = {15, 15, 15};
            PdfPTable t5 = new PdfPTable(columnWidths11);
            t5.setWidthPercentage(100);
            t5.getDefaultCell().setBorder(0);
            t5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            t5.addCell(new Paragraph(" ", H10B));
            t5.addCell(new Paragraph(" ", H10B));
            t5.addCell(new Paragraph(" ", H10B));
            t5.addCell(new Paragraph(" ", H10B));
            t5.addCell(new Paragraph(" ", H10B));
            t5.addCell(new Paragraph(" ", H10B));
            t5.addCell(new Paragraph("Store In charge", H10B));
            t5.addCell(new Paragraph(" ", H10B));
            t5.addCell(new Paragraph("Parent's Sign", H10B));
            t2.addCell(t5);

            int i = 1;
            while (i <= 3) {

                t1.addCell(t2);

                t1.addCell(new Paragraph(" "));
                i++;
            }
            document.add(t1);

            /**
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
        }

    }

}
