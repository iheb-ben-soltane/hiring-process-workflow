package tn.enit.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import tn.enit.service.ClientNotifier;

public class ClientNotifierHandler implements JobHandler {

    ClientNotifier clientNotifierService = new ClientNotifier();

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        clientNotifierService.notifierAssure();
        //Pour dire au workflow engine que le client a bien terminé son Job
        client.newCompleteCommand(job.getKey()).send().join();
    }
}