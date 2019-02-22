/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import java.awt.Color;
import java.math.BigDecimal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sf.dynamicreports.examples.Templates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.grp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.Units;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.group.GroupBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;


/**
 *
 * @author Kradvenko
 */
public class Reporte {
    
    private Venta ventaActual;
    private ObservableList<Venta> corte = FXCollections.observableArrayList();
    private Float totalEfectivo = 0f;
    private Float totalTarjeta = 0f;
    private Float totalVenta = 0f;
    
    private Apartado apartadoActual;
    private ObservableList<Abono> abonos = FXCollections.observableArrayList();
    
    private ObservableList<Articulo> articulosReporte = FXCollections.observableArrayList();
    
    public Reporte() {
        
    }
    
    public void setVentaActual(Venta ventaActual) {
        this.ventaActual = ventaActual;
    }
    
    public void setCorte(ObservableList<Venta> corte) {
        this.corte = corte;
    }
    
    public void setApartadoActual(Apartado apartadoActual) {
        this.apartadoActual = apartadoActual;
    }
    
    public void setAbonos(ObservableList<Abono> abonos) {
        this.abonos = abonos;
    }
    
    public void setArticulosReporte(ObservableList<Articulo> articulos) {
        this.articulosReporte = articulos;
    }
    
    public void crearTicket() {
            FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
            FontBuilder boldFontV2 = stl.fontArialBold().setFontSize(9);
            FontBuilder regularFont = stl.fontArial().setFontSize(7);            
            
            StyleBuilder boldStyle = stl.style().setFont(boldFont);
            StyleBuilder boldStyle2 = stl.style().setFont(boldFontV2);
            StyleBuilder plainStyle = stl.style().setFont(regularFont);

            TextColumnBuilder<String> articuloColumn = col.column("Artículo", "articulo", type.stringType()).setStyle(plainStyle);
            TextColumnBuilder<String> cantidadColumn = col.column("Cantidad", "cantidad", type.stringType()).setStyle(plainStyle);
            TextColumnBuilder<String> precioColumn = col.column("Precio", "precio", type.stringType()).setStyle(plainStyle);
            TextColumnBuilder<String> importeColumn = col.column("Importe", "importe", type.stringType()).setStyle(plainStyle);
            
            articuloColumn.setTitleStyle(plainStyle);
            cantidadColumn.setTitleStyle(plainStyle);
            precioColumn.setTitleStyle(plainStyle);
            importeColumn.setTitleStyle(plainStyle);
            
            Tienda t = Tienda.obtenerDatosTienda();
            
            try {
                int height = 4 + ventaActual.getDetalle().size();
                report()                            
                        .columns(articuloColumn, cantidadColumn, precioColumn, importeColumn)                            
                        .pageHeader(cmp.text(t.getNombre()).setStyle(boldStyle))
                        .pageHeader(cmp.text(ventaActual.getFecha()).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Tipo pago:" + String.valueOf(ventaActual.getTipo())).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Subtotal: $ " + String.valueOf(ventaActual.getSubTotalVenta())).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Descuento($): $ " + String.valueOf(ventaActual.getDescuentoEfectivo())).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Descuento(%):  " + String.valueOf(ventaActual.getDescuentoPorcentaje())).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Cambio: $ " + String.valueOf(ventaActual.getCambio())).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Total: $ " + String.valueOf(ventaActual.getTotalVenta())).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Vendedor:  " + ventaActual.getVendedor()).setStyle(boldStyle2))
                        .setDataSource(createDataSourceTicket())
                        .setPageFormat(Units.cm(5.5), Units.cm(height), PageOrientation.PORTRAIT)
                        .show(false);
            } catch (DRException e) {
                    e.printStackTrace();
            }
	}
    
    public void crearTicketImprimir() {
            FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
            FontBuilder boldFontV2 = stl.fontArialBold().setFontSize(9);
            FontBuilder regularFont = stl.fontArial().setFontSize(7);
            
            StyleBuilder boldStyle = stl.style().setFont(boldFont);
            StyleBuilder boldStyle2 = stl.style().setFont(boldFontV2);
            StyleBuilder plainStyle = stl.style().setFont(regularFont);

            TextColumnBuilder<String> articuloColumn = col.column("Artículo", "articulo", type.stringType()).setStyle(plainStyle);
            TextColumnBuilder<String> cantidadColumn = col.column("Cantidad", "cantidad", type.stringType()).setStyle(plainStyle);
            TextColumnBuilder<String> precioColumn = col.column("Precio", "precio", type.stringType()).setStyle(plainStyle);
            TextColumnBuilder<String> importeColumn = col.column("Importe", "importe", type.stringType()).setStyle(plainStyle);
            
            articuloColumn.setTitleStyle(plainStyle);
            cantidadColumn.setTitleStyle(plainStyle);
            precioColumn.setTitleStyle(plainStyle);
            importeColumn.setTitleStyle(plainStyle);
            
            Tienda t = Tienda.obtenerDatosTienda();
            
            try {
                int height = 4 + ventaActual.getDetalle().size();
                report()                            
                        .columns(articuloColumn, cantidadColumn, precioColumn, importeColumn)                            
                        .pageHeader(cmp.text(t.getNombre()).setStyle(boldStyle))
                        .pageHeader(cmp.text(ventaActual.getFecha()).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Tipo pago:" + String.valueOf(ventaActual.getTipo())).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Subtotal: $ " + String.valueOf(ventaActual.getSubTotalVenta())).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Descuento($): $ " + String.valueOf(ventaActual.getDescuentoEfectivo())).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Descuento(%):  " + String.valueOf(ventaActual.getDescuentoPorcentaje())).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Cambio: $ " + String.valueOf(ventaActual.getCambio())).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Total: $ " + String.valueOf(ventaActual.getTotalVenta())).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Vendedor:  " + ventaActual.getVendedor()).setStyle(boldStyle2))
                        .setDataSource(createDataSourceTicket())
                        .setPageFormat(Units.cm(5.5), Units.cm(height), PageOrientation.PORTRAIT)
                        .print(false);
            } catch (DRException e) {
                    e.printStackTrace();
            }
	}
    
    public void crearCorte() {
            FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
            FontBuilder boldFontV2 = stl.fontArialBold().setFontSize(9);
            FontBuilder regularFont = stl.fontArial().setFontSize(7);            
            
            StyleBuilder boldStyle = stl.style().setFont(boldFont);
            StyleBuilder boldStyle2 = stl.style().setFont(boldFontV2);
            StyleBuilder plainStyle = stl.style().setFont(regularFont);            

            TextColumnBuilder<String> numeroColumn = col.column("Número", "numero", type.stringType()).setStyle(plainStyle);
            TextColumnBuilder<String> fechaColumn = col.column("Fecha", "fecha", type.stringType()).setStyle(plainStyle);
            TextColumnBuilder<String> tipoColumn = col.column("Tipo", "tipo", type.stringType()).setStyle(plainStyle);
            TextColumnBuilder<String> totalColumn = col.column("Total", "total", type.stringType()).setStyle(plainStyle);
            
            numeroColumn.setTitleStyle(plainStyle);
            fechaColumn.setTitleStyle(plainStyle);
            tipoColumn.setTitleStyle(plainStyle);
            totalColumn.setTitleStyle(plainStyle);
            
            Tienda t = Tienda.obtenerDatosTienda();
            calcularCorte();
            
            try {
                int height = 4 + corte.size();
                report()                            
                        .columns(numeroColumn, fechaColumn, tipoColumn, totalColumn)                            
                        .pageHeader(cmp.text(t.getNombre()).setStyle(boldStyle))
                        .pageFooter(cmp.text("Total efectivo: $ " + String.valueOf(totalEfectivo)).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Total tarjeta: $ " + String.valueOf(totalTarjeta)).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Total: $ " + String.valueOf((totalEfectivo + totalTarjeta))).setStyle(boldStyle2))
                        .setDataSource(createDataSourceCorte())
                        .setPageFormat(Units.cm(5.5), Units.cm(height), PageOrientation.PORTRAIT)
                        .print(false);
            } catch (DRException e) {
                    e.printStackTrace();
            }
	}

	public JRDataSource createDataSourceTicket() {
                DRDataSource dataSource = new DRDataSource("articulo", "cantidad", "precio", "importe");
                ObservableList<Articulo> detalle = ventaActual.getDetalle();
                for (int i = 0; i < detalle.size(); i++) {
                    String cantidad = String.valueOf(detalle.get(i).getCantidadVenta());
                    String precio = String.valueOf(detalle.get(i).getPrecio());
                    String total = String.valueOf(detalle.get(i).getTotalVenta());
                    dataSource.add(detalle.get(i).getNombre(), cantidad, precio, total);
                }		
		return dataSource;
	}
        
        public void crerCorte() {
            
        }
        
        public JRDataSource createDataSourceCorte() {
		DRDataSource dataSource = new DRDataSource("numero", "fecha", "tipo", "total");
                
                for (int i = 0; i < corte.size(); i++) {                    
                    String numero = String.valueOf(corte.get(i).getIdVenta());
                    String fecha = String.valueOf(corte.get(i).getFecha());
                    String tipo = String.valueOf(corte.get(i).getTipo());
                    String total = String.valueOf(corte.get(i).getTotalVenta());
                    dataSource.add(numero, fecha, tipo, total);
                }		
		return dataSource;
	}
        
        public void calcularCorte() {        
            if (corte.size() > 0) {
                for (int i = 0; i < corte.size(); i++) {
                    Venta v = corte.get(i);
                    if (v.getEstado().contains("CANCELADA")) {
                        continue;
                    }
                    if (v.getTipo().contains("EFECTIVO")) {
                        totalEfectivo = totalEfectivo + v.getTotalVenta();
                    } else if (v.getTipo().contains("TARJETA")) {
                        totalTarjeta = totalTarjeta + v.getTotalVenta();
                    }
                }
            }
            totalVenta = totalEfectivo + totalTarjeta;
    }
        
    public void crearApartadoImprimir() {
            FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
            FontBuilder boldFontV2 = stl.fontArialBold().setFontSize(9);
            FontBuilder regularFont = stl.fontArial().setFontSize(7);
            
            StyleBuilder boldStyle = stl.style().setFont(boldFont);
            StyleBuilder boldStyle2 = stl.style().setFont(boldFontV2);
            StyleBuilder plainStyle = stl.style().setFont(regularFont);

            TextColumnBuilder<String> articuloColumn = col.column("Artículo", "articulo", type.stringType()).setStyle(plainStyle);
            TextColumnBuilder<String> cantidadColumn = col.column("Cantidad", "cantidad", type.stringType()).setStyle(plainStyle);
            TextColumnBuilder<String> precioColumn = col.column("Precio", "precio", type.stringType()).setStyle(plainStyle);
            TextColumnBuilder<String> importeColumn = col.column("Importe", "importe", type.stringType()).setStyle(plainStyle);
            
            articuloColumn.setTitleStyle(plainStyle);
            cantidadColumn.setTitleStyle(plainStyle);
            precioColumn.setTitleStyle(plainStyle);
            importeColumn.setTitleStyle(plainStyle);
            
            Tienda t = Tienda.obtenerDatosTienda();
            
            try {
                int height = 6 + apartadoActual.getDetalle().size();
                report()                            
                        .columns(articuloColumn, cantidadColumn, precioColumn, importeColumn)                            
                        .pageHeader(cmp.text(t.getNombre()).setStyle(boldStyle))
                        .pageHeader(cmp.text("Apartado").setStyle(boldStyle))
                        .pageHeader(cmp.text("Fecha de apartado: " + apartadoActual.getFecha()).setStyle(boldStyle2))
                        .pageHeader(cmp.text("Cliente " + apartadoActual.getCliente()).setStyle(boldStyle))
                        .pageFooter(cmp.text("Descuento($): $ " + String.valueOf(apartadoActual.getDescuentoEfectivo())).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Descuento(%):  " + String.valueOf(apartadoActual.getDescuentoPorcentaje())).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Resta: $ " + String.valueOf(apartadoActual.getCambio())).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Total: $ " + String.valueOf(apartadoActual.getTotalVenta())).setStyle(boldStyle2))
                        .pageFooter(cmp.text("Vendedor:  " + apartadoActual.getVendedor()).setStyle(boldStyle2))
                        .setDataSource(createDataSourceApartado())
                        .setPageFormat(Units.cm(5.5), Units.cm(height), PageOrientation.PORTRAIT)
                        .print(false);
                //crearApartadoAbonosImprimir();
            } catch (DRException e) {
                    e.printStackTrace();
            }
	}
    
    public JRDataSource createDataSourceApartado() {
		DRDataSource dataSource = new DRDataSource("articulo", "cantidad", "precio", "importe");
                ObservableList<Articulo> detalle = apartadoActual.getDetalle();
                for (int i = 0; i < detalle.size(); i++) {
                    String cantidad = String.valueOf(detalle.get(i).getCantidadVenta());
                    String precio = String.valueOf(detalle.get(i).getPrecio());
                    String total = String.valueOf(detalle.get(i).getTotalVenta());
                    dataSource.add(detalle.get(i).getNombre(), cantidad, precio, total);
                }		
		return dataSource;
    }
    
    public void crearApartadoAbonosImprimir() {
            FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
            FontBuilder boldFontV2 = stl.fontArialBold().setFontSize(9);
            FontBuilder regularFont = stl.fontArial().setFontSize(7);
            
            StyleBuilder boldStyle = stl.style().setFont(boldFont);
            StyleBuilder boldStyle2 = stl.style().setFont(boldFontV2);
            StyleBuilder plainStyle = stl.style().setFont(regularFont);

            TextColumnBuilder<String> fechaColumn = col.column("Fecha", "fecha", type.stringType()).setStyle(plainStyle);
            TextColumnBuilder<String> cantidadColumn = col.column("Cantidad", "cantidad", type.stringType()).setStyle(plainStyle);
            TextColumnBuilder<String> tipoColumn = col.column("Tipo", "tipo", type.stringType()).setStyle(plainStyle);
            
            fechaColumn.setTitleStyle(plainStyle);
            cantidadColumn.setTitleStyle(plainStyle);
            tipoColumn.setTitleStyle(plainStyle);
            
            
            Tienda t = Tienda.obtenerDatosTienda();
            
            try {
                int height = 4 + apartadoActual.getDetalle().size();
                report()                            
                        .columns(fechaColumn, cantidadColumn, tipoColumn)                        
                        .pageHeader(cmp.text("ABONOS").setStyle(boldStyle))
                        .setDataSource(createDataSourceApartadoAbonos())
                        .setPageFormat(Units.cm(5.5), Units.cm(height), PageOrientation.PORTRAIT)
                        .print(false);
            } catch (DRException e) {
                    e.printStackTrace();
            }
    }
    
    public JRDataSource createDataSourceApartadoAbonos() {
        DRDataSource dataSource = new DRDataSource("fecha", "cantidad", "tipo");
        ObservableList<Abono> detalle = Abono.obtenerAbonosApartado(apartadoActual.getIdApartado());
        for (int i = 0; i < detalle.size(); i++) {
            String fecha = String.valueOf(detalle.get(i).getFecha());
            String cantidad = String.valueOf(detalle.get(i).getAbono());
            String tipo = String.valueOf(detalle.get(i).getTipo());
            dataSource.add(fecha, cantidad, tipo);
        }		
        return dataSource;
    }
    
    public void crearApartadoAbonosCorteImprimir() {
            FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
            FontBuilder boldFontV2 = stl.fontArialBold().setFontSize(9);
            FontBuilder regularFont = stl.fontArial().setFontSize(7);
            
            StyleBuilder boldStyle = stl.style().setFont(boldFont);
            StyleBuilder boldStyle2 = stl.style().setFont(boldFontV2);
            StyleBuilder plainStyle = stl.style().setFont(regularFont);

            TextColumnBuilder<String> fechaColumn = col.column("Fecha", "fecha", type.stringType()).setStyle(plainStyle);
            TextColumnBuilder<String> cantidadColumn = col.column("Cantidad", "cantidad", type.stringType()).setStyle(plainStyle);
            TextColumnBuilder<String> tipoColumn = col.column("Tipo", "tipo", type.stringType()).setStyle(plainStyle);
            
            fechaColumn.setTitleStyle(plainStyle);
            cantidadColumn.setTitleStyle(plainStyle);
            tipoColumn.setTitleStyle(plainStyle);
            
            
            Tienda t = Tienda.obtenerDatosTienda();
            
            try {
                int height = 4 + abonos.size();
                report()                            
                        .columns(fechaColumn, cantidadColumn, tipoColumn)                        
                        .pageHeader(cmp.text("ABONOS").setStyle(boldStyle))
                        .setDataSource(createDataSourceApartadoAbonosCorte())
                        .setPageFormat(Units.cm(5.5), Units.cm(height), PageOrientation.PORTRAIT)
                        .print(false);
            } catch (DRException e) {
                    e.printStackTrace();
            }
    }
    
    public JRDataSource createDataSourceApartadoAbonosCorte() {
        DRDataSource dataSource = new DRDataSource("fecha", "cantidad", "tipo");
        ObservableList<Abono> detalle = abonos;
        for (int i = 0; i < detalle.size(); i++) {
            String fecha = String.valueOf(detalle.get(i).getFecha());
            String cantidad = String.valueOf(detalle.get(i).getAbono());
            String tipo = String.valueOf(detalle.get(i).getTipo());
            dataSource.add(fecha, cantidad, tipo);
        }		
        return dataSource;
    }
    
    public void crearReporteArticulos() {
        FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
        FontBuilder boldFontV2 = stl.fontArialBold().setFontSize(9);
        FontBuilder regularFont = stl.fontArial().setFontSize(10);

        StyleBuilder boldStyle = stl.style().setFont(boldFont);
        StyleBuilder boldStyle2 = stl.style().setFont(boldFontV2);
        StyleBuilder plainStyle = stl.style().setFont(regularFont);

        TextColumnBuilder<String> articuloColumn = col.column("Artículo", "articulo", type.stringType()).setStyle(plainStyle);
        TextColumnBuilder<String> cantidadColumn = col.column("Cantidad", "cantidad", type.stringType()).setStyle(plainStyle);
        TextColumnBuilder<String> precioColumn = col.column("Precio", "precio", type.stringType()).setStyle(plainStyle);
        TextColumnBuilder<String> categoriaColumn = col.column("Categoria", "categoria", type.stringType()).setStyle(plainStyle);
        TextColumnBuilder<String> costoColumn = col.column("Costo", "costo", type.stringType()).setStyle(plainStyle);

        articuloColumn.setTitleStyle(plainStyle);
        cantidadColumn.setTitleStyle(plainStyle);
        precioColumn.setTitleStyle(plainStyle);
        costoColumn.setTitleStyle(plainStyle);

        Tienda t = Tienda.obtenerDatosTienda();
        
        StyleBuilder gHeader = stl.style().setFont(boldFont);
        gHeader.setBackgroundColor(Color.BLACK);
        gHeader.setForegroundColor(Color.WHITE);
        
        categoriaColumn.setStyle(gHeader);
        
        GroupBuilder g = grp.group(categoriaColumn);

        try {
            //int height = 4 + ventaActual.getDetalle().size();
            report()                            
                    .columns(articuloColumn, cantidadColumn, precioColumn, costoColumn)
                    .pageHeader(cmp.text(t.getNombre()).setStyle(boldStyle))
                    .pageHeader(cmp.text("Reporte de artículos"))
                    /*
                    .pageFooter(cmp.text("Tipo pago:" + String.valueOf(ventaActual.getTipo())).setStyle(boldStyle2))
                    .pageFooter(cmp.text("Subtotal: $ " + String.valueOf(ventaActual.getSubTotalVenta())).setStyle(boldStyle2))
                    .pageFooter(cmp.text("Descuento($): $ " + String.valueOf(ventaActual.getDescuentoEfectivo())).setStyle(boldStyle2))
                    .pageFooter(cmp.text("Descuento(%):  " + String.valueOf(ventaActual.getDescuentoPorcentaje())).setStyle(boldStyle2))
                    .pageFooter(cmp.text("Cambio: $ " + String.valueOf(ventaActual.getCambio())).setStyle(boldStyle2))
                    .pageFooter(cmp.text("Total: $ " + String.valueOf(ventaActual.getTotalVenta())).setStyle(boldStyle2))
                    .pageFooter(cmp.text("Vendedor:  " + ventaActual.getVendedor()).setStyle(boldStyle2))
                    */
                    .setDataSource(createDataSourceReporteArticulos())
                    .groupBy(g)
                    .setHighlightDetailEvenRows(Boolean.TRUE)
                    //.setGroupHeaderStyle(g, gHeader)
                    //.setPageFormat(Units.cm(5.5), Units.cm(height), PageOrientation.PORTRAIT)
                    .print(false);
        } catch (DRException e) {
                e.printStackTrace();
        }
    }
    
    public JRDataSource createDataSourceReporteArticulos() {
        DRDataSource dataSource = new DRDataSource("articulo", "cantidad", "precio", "categoria", "costo");
        ObservableList<Articulo> articulos = articulosReporte;
        for (int i = 0; i < articulos.size(); i++) {
            String articulo = String.valueOf(articulos.get(i).getNombre());
            String cantidad = String.valueOf(articulos.get(i).getExistencia());
            String precio = String.valueOf(articulos.get(i).getPrecio());
            String categoria = String.valueOf(articulos.get(i).getCategoria());
            String costo = "_________________________";
            dataSource.add(articulo, cantidad, precio, categoria, costo);
        }		
        return dataSource;
    }
}
