package com.clases.dam.gestion.salesianos.Alumno;

import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Service
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
            String filePath = context.getRealPath("/descargas/alumno");
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
            paragraph.setSpacingBefore(50);
            document.add(paragraph);


            document.close();
            writer.close();
            return true;

        }catch (Exception e) {
            return false;
        }
    }
}
