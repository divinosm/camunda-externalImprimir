package br.gov.mt.intermat;

import java.util.Random;

//import java.util.List;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.mt.intermat.service.ReportService;


@Component
@ExternalTaskSubscription ("exImprimirTitulo")
public class ImprimirTituloHandler implements ExternalTaskHandler {
 
    @Autowired
    private ReportService reportService;
    private String resultadoRelatorio;
    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        // lógica do processo
        Random random = new Random();
        String numTitulo = String.valueOf(random.nextInt(1000));
        String nome = (String) externalTask.getVariable("nome");
        String cpf = (String) externalTask.getVariable("cpf");
 
     //   Date dataAtual = new Date();
     //   DateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
     //   String dataFormatada = dataFormat.format(dataAtual);
     //   dataArquivamento = dataFormatada;
 
        System.out.println("====================");
        System.out.println("nome = "+nome);
        System.out.println("cpf = "+cpf);
        System.out.println("Num. Titulo = "+numTitulo);

        
        try {
            resultadoRelatorio = reportService.relatorioTitulo(nome,cpf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
       System.out.println("relatório ="+resultadoRelatorio);


       VariableMap variaveis = Variables.createVariables().putValue("numTitulo", Variables.stringValue(numTitulo));
       externalTaskService.complete(externalTask, variaveis);
    }
    
 }