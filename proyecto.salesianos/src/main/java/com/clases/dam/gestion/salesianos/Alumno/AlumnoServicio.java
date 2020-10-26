package com.clases.dam.gestion.salesianos.Alumno;

import com.clases.dam.gestion.salesianos.Horario.HorarioServicio;
import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import com.clases.dam.gestion.salesianos.SolicitudAmpliacionMatricula.SolicitudAmpliacionMatriculaServicio;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDiv;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private  static HorarioServicio horarioServicio;
    @Autowired
    private static SolicitudAmpliacionMatriculaServicio solicitudAmpliacionMatriculaServicio;

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
            /*Font tableHeader = FontFactory.getFont("Arial",10,BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial",9,BaseColor.BLACK);
            PdfPTable table =new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);
            float [] columWidth = {3f,3f,3f,3f,3f};
            table.setWidths(columWidth);
            PdfPCell lunes= new PdfPCell(new Paragraph("Lunes",tableHeader));
            lunes.setBorderColor(BaseColor.BLACK);
            lunes.setPaddingLeft(10);
            lunes.setHorizontalAlignment(Element.ALIGN_CENTER);
            lunes.setVerticalAlignment(Element.ALIGN_CENTER);
            lunes.setBackgroundColor(BaseColor.GRAY);
            table.addCell(lunes);
            PdfPCell martes= new PdfPCell(new Paragraph("Martes",tableHeader));
            martes.setBorderColor(BaseColor.BLACK);
            martes.setPaddingLeft(10);
            martes.setHorizontalAlignment(Element.ALIGN_CENTER);
            martes.setVerticalAlignment(Element.ALIGN_CENTER);
            martes.setBackgroundColor(BaseColor.GRAY);
            table.addCell(martes);
            PdfPCell miercoles= new PdfPCell(new Paragraph("Miércoles",tableHeader));
            miercoles.setBorderColor(BaseColor.BLACK);
            miercoles.setPaddingLeft(10);
            miercoles.setHorizontalAlignment(Element.ALIGN_CENTER);
            miercoles.setVerticalAlignment(Element.ALIGN_CENTER);
            miercoles.setBackgroundColor(BaseColor.GRAY);
            table.addCell(miercoles);
            PdfPCell jueves= new PdfPCell(new Paragraph("Jueves",tableHeader));
            jueves.setBorderColor(BaseColor.BLACK);
            jueves.setPaddingLeft(10);
            jueves.setHorizontalAlignment(Element.ALIGN_CENTER);
            jueves.setVerticalAlignment(Element.ALIGN_CENTER);
            jueves.setBackgroundColor(BaseColor.GRAY);
            table.addCell(jueves);
            PdfPCell viernes= new PdfPCell(new Paragraph("Viernes",tableHeader));
            viernes.setBorderColor(BaseColor.BLACK);
            viernes.setPaddingLeft(10);
            viernes.setHorizontalAlignment(Element.ALIGN_CENTER);
            viernes.setVerticalAlignment(Element.ALIGN_CENTER);
            viernes.setBackgroundColor(BaseColor.GRAY);
            table.addCell(viernes);

            System.out.println(horarioServicio.ordenarFinal(horarioServicio.horariosPorAlumno(al,solicitudAmpliacionMatriculaServicio.findAll())));*/

            document.close();
            writer.close();
            return true;

        }catch (Exception e) {
            return false;
        }
    }
}
