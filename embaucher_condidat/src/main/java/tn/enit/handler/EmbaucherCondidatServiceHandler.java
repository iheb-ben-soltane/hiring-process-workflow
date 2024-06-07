package tn.enit.handler;

import java.util.Map;

import tn.enit.service.EmbaucherCondidat;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class EmbaucherCondidatServiceHandler implements JobHandler {


    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
    	
    	//Obtain the Process Variables
    	final Map<String, Object> inputVariables = job.getVariablesAsMap();
		final String nomCondidat = (String) inputVariables.get("nom_condidat_contat");
		final String startDateContrat = (String) inputVariables.get("start_date_contrat");
		final String endDateContrat = (String) inputVariables.get("end_date_contrat");
		final String salaireContrat = (String) inputVariables.get("textfield_v1u02x");

    	//Build the Output Process Variables
    	System.out.println("Données persistées avec succès ");
		System.out.println("Nom du condidat :" + nomCondidat);
		System.out.println("start date :" + startDateContrat);
		System.out.println("end date : " + endDateContrat);
		System.out.println("salaire : " + salaireContrat);


    	//Complete the Job
    	client.newCompleteCommand(job.getKey()).send().join();
    }
}
