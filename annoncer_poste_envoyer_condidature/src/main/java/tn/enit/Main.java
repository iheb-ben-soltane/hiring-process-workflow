package tn.enit;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import tn.enit.handler.ClientNotifierHandler;
import tn.enit.handler.AnnoncePosteHandler;
import tn.enit.handler.EnvoyerCondidatureHandler;

import java.time.Duration;
import java.util.Scanner;


public class Main {

    private static final String ZEEBE_ADDRESS = "1c019510-e86d-47ec-8e62-d677c25c3fe0.dsm-1.zeebe.camunda.io:443";
    private static final String ZEEBE_CLIENT_ID = "~qZRYHh021Lr6B6LW-RXbokupu3hmR8L";
    private static final String ZEEBE_CLIENT_SECRET = "lEFWO9EqDzeF4T~m8SI7qNOtUPaHJF72QcGM8zidAJA3SeE5t_JarDVfIA8BPICK";
    private static final String ZEEBE_AUTHORIZATION_SERVER_URL = "https://login.cloud.camunda.io/oauth/token";
    private static final String ZEEBE_TOKEN_AUDIENCE = "zeebe.camunda.io";
    private static final String ANNONCE_DE_POSTE_JOB_TYPE = "annoncerLePoste";
    private static final String ENVOYER_CONDIDATURE_JOB_TYPE = "envoyerLaCondidature";



    public static void main(String[] args) {


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
            System.out.println("Connected to: " + client.newTopologyRequest().send().join());

            //Thread.sleep(10000);
            final JobWorker demandeDeReservationWorker = client.newWorker()
                    .jobType(ANNONCE_DE_POSTE_JOB_TYPE)
                    .handler(new AnnoncePosteHandler())
                    .timeout(Duration.ofSeconds(10).toMillis())
                    .open();

            final JobWorker annoncerLaCondidatureWorker = client.newWorker()
                    .jobType(ENVOYER_CONDIDATURE_JOB_TYPE)
                    .handler(new EnvoyerCondidatureHandler())
                    .timeout(Duration.ofSeconds(10).toMillis())
                    .open();


            final JobWorker notifierAssureWorker =
                    client.newWorker()
                            .jobType("notifierAssure")
                            .handler(new ClientNotifierHandler())
                            .timeout(Duration.ofSeconds(10).toMillis())
                            .open();
            //Thread.sleep(10000);
            //Wait for the Workers
            Scanner sc = new Scanner(System.in);
            sc.nextInt();
            sc.close();
            demandeDeReservationWorker.close();
            annoncerLaCondidatureWorker.close();
            notifierAssureWorker.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
