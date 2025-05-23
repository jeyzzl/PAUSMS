package aca.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class JpgToPdf {
    public static void main(String arg[]) throws Exception {
        File root = new File("<folder containing images>");
        String outputFile = "output.pdf";
        List<String> files = new ArrayList<String>();
        files.add("page1.jpg");
        files.add("page2.jpg");
        
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(new File(root, outputFile)));
        document.open();
        for (String f : files) {
            document.newPage();
            Image image = Image.getInstance(new File(root, f).getAbsolutePath());
            image.setAbsolutePosition(0, 0);
            image.setBorderWidth(0);           
            image.scaleAbsolute(PageSize.LETTER.getWidth(), PageSize.LETTER.getHeight());
            document.add(image);
        }
        document.close();
    }
}