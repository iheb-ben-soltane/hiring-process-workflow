package tn.enit;

import java.time.Duration;

import tn.enit.handler.AnalyserRetourServiceHandler;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

public class Main {
	
	//Zeebe Client Credentials
	private static final String ZEEBE_ADDRESS = "1c019510-e86d-47ec-8e62-d677c25c3fe0.dsm-1.zeebe.camunda.io:443";
	private static final String ZEEBE_CLIENT_ID = "~qZRYHh021Lr6B6LW-RXbokupu3hmR8L";
	private static final String ZEEBE_CLIENT_SECRET = "lEFWO9EqDzeF4T~m8SI7qNOtUPaHJF72QcGM8zidAJA3SeE5t_JarDVfIA8BPICK";
	private static final String ZEEBE_AUTHORIZATION_SERVER_URL = "https://login.cloud.camunda.io/oauth/token";
	private static final String ZEEBE_TOKEN_AUDIENCE = "zeebe.camunda.io";
	

	//Process Variables
	private static final long WORKER_TIMEOUT = 10;
	private static final int WORKER_TIME_TO_LIVE = 10000;

	//Process Definition Details
	private static final String ENVOI_ERP_JOB_TYPE = "envoiERP";


	
    public static void main(String[] args){
    	
    	final OAuthCredentialsProvider credentialsProvider =
    			new OAuthCredentialsProviderBuilder()
			    	.authorizationServerUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
			        .audience(ZEEBE_TOKEN_AUDIENCE)
			        .clientId(ZEEBE_CLIENT_ID)
			        .clientSecret(ZEEBE_CLIENT_SECRET)
			        .build();
	    	
		try (final ZeebeClient client =
		        ZeebeClient.newClientBuilder()
		            .gatewayAddress(ZEEBE_ADDRESS)
		            .credentialsProvider(credentialsProvider)
		            .build()) {
			
			//Request the Cluster Topology
			System.out.println("Connected to: " + client.newTopologyRequest().send().join());

			
			//Start a Job Worker
			final JobWorker envoiERP_worker =
				    client.newWorker()
				        .jobType(ENVOI_ERP_JOB_TYPE)
				        .handler(new AnalyserRetourServiceHandler())
				        .timeout(Duration.ofSeconds(WORKER_TIMEOUT).toMillis())
				        .open();
			
			//Wait for the Workers
			Thread.sleep(WORKER_TIME_TO_LIVE);
			
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }
}
