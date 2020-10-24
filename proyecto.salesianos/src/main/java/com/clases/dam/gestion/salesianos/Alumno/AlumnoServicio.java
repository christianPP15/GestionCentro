package com.clases.dam.gestion.salesianos.Alumno;

import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDiv;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Service
@Transactional
public class AlumnoServicio extends BaseServiceImpl<Alumno,Long,AlumnoRepository> {

    public AlumnoServicio(AlumnoRepository repo) {
        super(repo);
    }

    public List<Alumno> listaAlumnos(){
    return this.repositorio.alumnos();
    }

    public static boolean createPdf(Alumno al, HttpServletRequest request,
                                    HttpServletResponse response, ServletContext context) {
        Document document = new Document(PageSize.A4,15,15,45,30);
        try {
            String filePath = context.getRealPath("/resources/reports");
            File file= new File(filePath);
            boolean exists = new File(filePath).exists();
            if(!exists) {
                new File(filePath).mkdirs();
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"alumno"+".pdf"));
            document.open();

            Font mainFont = FontFactory.getFont("Arial",10, BaseColor.BLACK);
            Paragraph paragraph= new Paragraph("Informacion del Alumno",mainFont);

            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(50);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingBefore(50);
            document.add(paragraph);
            PdfDiv div=new PdfDiv();
            Paragraph newParagraph=new Paragraph("Nombre del alumno: "+al.getNombre());
            newParagraph.setSpacingAfter(15);
            newParagraph.setIndentationLeft(15);
            newParagraph.setSpacingBefore(15);

            Paragraph newParagraph1=new Paragraph("Apellidos del alumno: "+al.getApellidos());
            newParagraph1.setSpacingAfter(15);
            newParagraph1.setIndentationLeft(15);
            newParagraph1.setSpacingBefore(15);

            Paragraph newParagraph2=new Paragraph("Email del alumno: "+al.getEmail());
            newParagraph2.setSpacingAfter(15);
            newParagraph2.setIndentationLeft(15);
            newParagraph2.setSpacingBefore(15);
            div.setBackgroundColor(BaseColor.CYAN);
            div.setBorderTopStyle(PdfDiv.BorderTopStyle.SOLID);
            div.setWidth(400f);
            div.setTextAlignment(Element.ALIGN_CENTER);
            div.addElement(newParagraph);
            div.addElement(newParagraph1);
            div.addElement(newParagraph2);
            document.add(div);
            document.close();
            writer.close();
            return true;

        }catch (Exception e) {
            return false;
        }
    }
}
