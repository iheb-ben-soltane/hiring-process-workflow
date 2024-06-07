package tn.enit.handler;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

import java.util.HashMap;
import java.util.Map;
public class EnvoyerDecisionContratHandler implements JobHandler {

    private static final String ZEEBE_ADDRESS = "1c019510-e86d-47ec-8e62-d677c25c3fe0.dsm-1.zeebe.camunda.io:443";
    private static final String ZEEBE_CLIENT_ID = "~qZRYHh021Lr6B6LW-RXbokupu3hmR8L";
    private static final String ZEEBE_CLIENT_SECRET = "lEFWO9EqDzeF4T~m8SI7qNOtUPaHJF72QcGM8zidAJA3SeE5t_JarDVfIA8BPICK";
    private static final String ZEEBE_AUTHORIZATION_SERVER_URL = "https://login.cloud.camunda.io/oauth/token";
    private static final String ZEEBE_TOKEN_AUDIENCE = "zeebe.camunda.io";
    private static final String MESSAGE_NAME = "msg_decisionContrat";

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {

        final Map<String, Object> inputVariables = job.getVariablesAsMap();
        final int contratId = 123;
        final String negocierdecision = (String) inputVariables.get("radio_decision_negocier_contrat");
        final String evaluerDecision = (String) inputVariables.get("radio_decision_evaluer_contrat");


        final OAuthCredentialsProvider credentialsProvider =
                new OAuthCredentialsProviderBuilder()
                        .authorizationServerUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
                        .audience(ZEEBE_TOKEN_AUDIENCE)
                        .clientId(ZEEBE_CLIENT_ID)
                        .clientSecret(ZEEBE_CLIENT_SECRET)
                        .build();

        try (final ZeebeClient travelAgencyClient = ZeebeClient.newClientBuilder()
                .gatewayAddress(ZEEBE_ADDRESS)
                .credentialsProvider(credentialsProvider)
                .build()) {


            //Build the Message Variables
            final Map<String, Object> messageVariables = new HashMap<>();
            System.out.println("contratId" + contratId);



            String decision_contrat;
            if ("reussi".equals(negocierdecision) || "accepte".equals(evaluerDecision)) {
                decision_contrat = "accepte";
            } else {
                decision_contrat = "rejete";
            }
            messageVariables.put("decision_contrat", decision_contrat);



            //Send the message
            travelAgencyClient.newPublishMessageCommand()
                    .messageName(MESSAGE_NAME)
                    .correlationKey(String.valueOf(contratId))
                    .variables(messageVariables)
                    .send()
                    .join();

            System.out.println(contratId + " decision bien reçue et le message est envoyé " );
            System.out.println("Message variables sent: " + messageVariables);
            //Complete the Job
            client.newCompleteCommand(job.getKey()).send().join();
        }
    }
}
