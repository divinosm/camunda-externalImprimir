package br.gov.mt.intermat.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import br.gov.mt.intermat.domain.Titulo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportService {
    public String relatorioTitulo (String nome,String cpf) throws FileNotFoundException, JRException{
        Titulo titulo = new Titulo(); 
        titulo.setCpf(cpf);
        titulo.setNome(nome);
        List<Titulo> titulos = new ArrayList<>();
             titulos.add(titulo);
        File file = ResourceUtils.getFile("classpath:FormTituloJasper.jrxml");
        Random gerador = new Random();
        String   saida = "F:\\Camunda\\Relatorio\\titulo"+cpf+Integer.toString(gerador.nextInt(100))+".pdf";
         
       JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
       JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(titulos);
        
       Map<String,Object> parameters = new HashMap<>();
       parameters.put ("cpf",cpf);
       parameters.put ("nome",nome);
       

       // recebi os parâmetros;
        
       JasperPrint jasperPrint = JasperFillManager
                   .fillReport(jasperReport, parameters, dataSource) ;
       JasperExportManager.exportReportToPdfFile(jasperPrint, saida);
       return "relatorio gerado "+saida;
         
    }
    
}
