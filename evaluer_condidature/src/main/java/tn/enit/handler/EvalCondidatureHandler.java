package tn.enit.handler;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import tn.enit.service.EvalCondidature;


import java.util.HashMap;
import java.util.Map;
public class EvalCondidatureHandler implements JobHandler {
    private static final String ZEEBE_ADDRESS = "1c019510-e86d-47ec-8e62-d677c25c3fe0.dsm-1.zeebe.camunda.io:443";
    private static final String ZEEBE_CLIENT_ID = "~qZRYHh021Lr6B6LW-RXbokupu3hmR8L";
    private static final String ZEEBE_CLIENT_SECRET = "lEFWO9EqDzeF4T~m8SI7qNOtUPaHJF72QcGM8zidAJA3SeE5t_JarDVfIA8BPICK";
    private static final String ZEEBE_AUTHORIZATION_SERVER_URL = "https://login.cloud.camunda.io/oauth/token";
    private static final String ZEEBE_TOKEN_AUDIENCE = "zeebe.camunda.io";
    EvalCondidature EvalCondidatureService = new EvalCondidature();
    private static final String MESSAGE_NAME = "evaluationCondidature";
    private static final String id="1546823645";



    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {

        final Map<String, Object> inputVariables = job.getVariablesAsMap();
        final String exp_condidat = (String) inputVariables.get("exp_condidat");
        final boolean evaluationCondidature= EvalCondidatureService.evalCondidature(exp_condidat);

        final Map<String, Object> messageVariables = new HashMap<String, Object>();
        messageVariables.put("evaluationCondidature", evaluationCondidature);
        messageVariables.put("evaluationCondidatureString", String.valueOf(evaluationCondidature));

        final Map<String, Object> outputVariables = new HashMap<String, Object>();
        outputVariables.put("evaluationCondidature", evaluationCondidature);
        System.out.println("resultat de condidature" + exp_condidat);


        //Envoyer la variable confirmation à partir du Job
        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
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

            travelAgencyClient.newPublishMessageCommand()
                    .messageName(MESSAGE_NAME)
                    .correlationKey(id)
                    .variables(messageVariables)
                    .send()
                    .join();
            System.out.println("resultat de condidature envoyé " +String.valueOf(evaluationCondidature));

            client.newCompleteCommand(job.getKey()).send().join();

        }
    }
}