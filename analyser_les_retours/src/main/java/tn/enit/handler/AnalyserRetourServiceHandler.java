package tn.enit.handler;

import java.util.Map;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class AnalyserRetourServiceHandler implements JobHandler {
	
	//Process Variables
	private static final String VARIABLE_DECISON = "radio_decision";

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
    	
    	//Obtain the Process Variables
    	final Map<String, Object> inputVariables = job.getVariablesAsMap();
    	final String decision = (String) inputVariables.get(VARIABLE_DECISON);
		final String nomCondidat = (String) inputVariables.get("nom_condidat");
		final String nomTechnique = (String) inputVariables.get("nom_responsable_tech");
		final String dateEntretient = (String) inputVariables.get("datatime_entretient");

    	//Build the Output Process Variables
    	System.out.println("Données persistées avec succès ");
		System.out.println("Nom du condidat :" + nomCondidat);
		System.out.println("nom responsable technique :" + nomTechnique);
		System.out.println("date entretient :" + dateEntretient);
		System.out.println("decision : " + decision);

    	//Complete the Job
    	client.newCompleteCommand(job.getKey()).send().join();
    }
}
