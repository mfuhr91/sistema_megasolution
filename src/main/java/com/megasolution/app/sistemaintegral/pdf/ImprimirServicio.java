package com.megasolution.app.sistemaintegral.pdf;

import java.awt.Color;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

@Component("/servicios/form-servicio")
public class ImprimirServicio extends AbstractPdfView {
    

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Servicio servicio = (Servicio) model.get("servicio");
        
        // LE COLOCO NOMBRE AL ARCHIVO
        response.setHeader("Content-Disposition", "filename=\"Orden de Servicio de " 
                                                    + servicio.getCliente().getDniCuit() 
                                                    + "_" 
                                                    + servicio.getCliente().getRazonSocial() 
                                                    + ".pdf\""); // attachment;  => colocandolo antes de filename se descarga el pdf automaticamente en Descargas del cliente
        doc.setPageSize(PageSize.A4);
        
        doc.addTitle("Orden de Servicio de " 
        + servicio.getCliente().getDniCuit() 
        + "_" 
        + servicio.getCliente().getRazonSocial() 
        + ".pdf");
        
        URL urlFont = this.getClass().getResource("/static/css/fonts/titilium.ttf");
        String FONT = urlFont.toString();

        URL url = this.getClass().getResource("/static/img/header_mega.jpg");
        Image imagen = Image.getInstance(url);

        // ENCABEZADO
        PdfPTable tabla = new PdfPTable(2);
        PdfPCell cel = null;
            
        // CELDA CONTACTO
        cel = new PdfPCell(new Phrase("GOBERNADOR PAZ 2098\n" +
                                        "Celular | WhatsApp: 2901 550863", FontFactory.getFont(FONT, 10)));

        cel.setBorder(Rectangle.NO_BORDER);
        cel.setPaddingTop(15f);
        cel.setPaddingLeft(10f);
        cel.setLeading(5, 1);
        cel.setHorizontalAlignment(Element.ALIGN_LEFT);
        tabla.addCell(cel);

        // CELDA IMAGEN
        cel = new PdfPCell();
        cel.addElement(imagen);
        cel.setPaddingTop(0f);
        cel.setBorder(Rectangle.NO_BORDER);
        tabla.addCell(cel);
        
        PdfPTable tabla1 = new PdfPTable(5); // 5 columnas
        
        tabla1.setSpacingBefore(7);
        // FIN ENCABEZADO
        
        

        // 1ra FILA
        Phrase tituloCliente = new Phrase("Cliente:", FontFactory.getFont(FONT,12, Font.BOLD));
        Phrase cliente = new Phrase(servicio.getCliente().getDniCuit() + " - " + servicio.getCliente().getRazonSocial(),FontFactory.getFont(FONT));
        cel = new PdfPCell();
        cel.addElement(tituloCliente);
        cel.addElement(cliente);
        cel.setBackgroundColor(new Color(247, 206, 170));
        cel.setColspan(3);
        
        cel.setPadding(8f);
        
        tabla1.addCell(cel);
        
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        
        Phrase tituloFecha = new Phrase("Fecha:", FontFactory.getFont(FONT,12, Font.BOLD));
        Phrase fecha = new Phrase(format.format(servicio.getFechaIngreso()),FontFactory.getFont(FONT));
        cel = new PdfPCell();
        cel.addElement(tituloFecha);
        cel.addElement(fecha);
        cel.setBackgroundColor(new Color(247, 206, 170));
        cel.setColspan(2);
        cel.setHorizontalAlignment(Element.ALIGN_CENTER);
        cel.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        cel.setPadding(8f);
        tabla1.addCell(cel);
        
        


        // 2da FILA
        PdfPTable tabla2 = new PdfPTable(5);
        tabla2.setSpacingBefore(5);
        
        Phrase tituloEquipo = new Phrase("Equipo:", FontFactory.getFont(FONT,12, Font.BOLD));
        Phrase equipo = new Phrase("    " + servicio.getEquipo(),FontFactory.getFont(FONT));
        cel = new PdfPCell();
        cel.addElement(tituloEquipo);
        cel.addElement(equipo);
                                                
        cel.setColspan(3);
        cel.setPadding(8f);
        cel.setPaddingTop(2f);
        cel.setLeading(5, 1);
        tabla2.addCell(cel);

        Phrase tituloTelefono = new Phrase("Teléfono:", FontFactory.getFont(FONT,12, Font.BOLD));
        Phrase telefono = new Phrase("    " + servicio.getCliente().getTelefono(),FontFactory.getFont(FONT));
        cel = new PdfPCell();
        cel.addElement(tituloTelefono);
        cel.addElement(telefono);
                                                
        cel.setColspan(2);
        cel.setPadding(8f);
        cel.setPaddingTop(2f);
        cel.setLeading(5, 1);
        tabla2.addCell(cel);
        

        // 3ra FILA
        PdfPTable tabla3 = new PdfPTable(5);
        tabla3.setSpacingBefore(5);
        if(servicio.getBateria()){
            cel = new PdfPCell(new Phrase("Bateria: SI",
                                    FontFactory.getFont(FONT,12,Font.BOLD)));
            
        }else{
            cel = new PdfPCell(new Phrase("Bateria: NO",
                                    FontFactory.getFont(FONT,12,Font.BOLD))); 
        }
        cel.setColspan(2);
       
        cel.setPadding(5f);
        tabla3.addCell(cel);
        
        if(servicio.getCargador()){
            cel = new PdfPCell(new Phrase("Cargador: SI",
                                    FontFactory.getFont(FONT,12,Font.BOLD)));
        }else{
            cel = new PdfPCell(new Phrase("Cargador: NO",
                                    FontFactory.getFont(FONT,12,Font.BOLD)));    
        }
        cel.setColspan(2);
        cel.setPadding(5f);
        tabla3.addCell(cel);

        String nombreSector = "";

        if(servicio.getSector() != null){
            nombreSector = servicio.getSector().getNombre();
        }else{
            nombreSector = "Entregado";
        }
        cel = new PdfPCell(new Phrase("Sector: " + nombreSector,
                                    FontFactory.getFont(FONT,12,Font.BOLD))); 
        cel.setColspan(1);
        cel.setPadding(5f);
        tabla3.addCell(cel);

        // 4ta FILA
        PdfPTable tabla4 = new PdfPTable(5);
        tabla4.setSpacingBefore(5);

        Phrase tituloProblema = new Phrase("Problema Reportado:", FontFactory.getFont(FONT,12, Font.BOLD));
        Phrase problema = new Phrase("  " + servicio.getProblemaReportado(),FontFactory.getFont(FONT));
        cel = new PdfPCell();
        cel.addElement(tituloProblema);
        cel.addElement(problema);
                                           

                                
        cel.setColspan(5);
        cel.setPadding(10f);
        cel.setPaddingTop(0f);
        cel.setLeading(7, 1);
        tabla4.addCell(cel);

        // 5ta FILA
        PdfPTable tabla5 = new PdfPTable(5);
        tabla5.setSpacingBefore(5);
        
        Phrase tituloObservaciones = new Phrase("Observaciones:", FontFactory.getFont(FONT,12, Font.BOLD));
        Phrase observaciones = new Phrase("  " + servicio.getObservaciones(),FontFactory.getFont(FONT));
        cel = new PdfPCell();
        cel.addElement(tituloObservaciones);
        cel.addElement(observaciones);
                                                                   
                                

        cel.setColspan(5);
        cel.setPadding(10f);
        cel.setPaddingTop(0f);
        cel.setLeading(7, 1);
        tabla5.addCell(cel);

        
        // 6ta FILA
        PdfPTable tabla6 = new PdfPTable(5);
        tabla6.setSpacingBefore(5);
        tabla6.setSpacingAfter(40);
        cel = new PdfPCell(new Phrase("La empresa Megasolution NO se hace responsable por la perdida del \n"+
                                        "equipo pasado los 120 días. Por favor no comprometa al personal.\n"+
                                        "",
        FontFactory.getFont(FONT,8)));
        cel.setColspan(3);
        cel.setPaddingTop(0f);
        cel.setLeading(2, 1);
        cel.setBorder(Rectangle.NO_BORDER);
        tabla6.addCell(cel);
        
        cel = new PdfPCell(new Phrase("Firma:",FontFactory.getFont(FONT,12, Font.BOLD)));
        cel.setColspan(2);
        cel.setPadding(8f);
        cel.setLeading(2, 1);
        
        cel.setHorizontalAlignment(Element.ALIGN_LEFT);
        tabla6.addCell(cel);
    

        // ASIGNO POR DUPLICADO LAS TABLAS AL DOCUMENTO
        doc.add(tabla);
        doc.add(tabla1);
        doc.add(tabla2);
        doc.add(tabla3);
        doc.add(tabla4);
        doc.add(tabla5);
        doc.add(tabla6);


        doc.add(tabla);
        doc.add(tabla1);
        doc.add(tabla2);
        doc.add(tabla3);
        doc.add(tabla4);
        doc.add(tabla5);
        doc.add(tabla6);

    

    }
    
}
