package com.mail.emailapplication;


import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;



@RestController
@RequestMapping("/alert")
public class EmailApplicationRestController {

    @Autowired
    ZeebeClient client;
    @Autowired
    EmailApplicationService emailService;

//    @PostMapping("startEvent")
//    public void startEvent() {
//        Map<String, String> map = new HashMap<>();
//        map.put("dkey", "1");
//        client.newPublishMessageCommand().messageName("receivemsg").correlationKey("receivemsg1").variables(map).send().join();
//    }

    @GetMapping("getemailformat")
    public String getTemplate(@RequestBody String messageCode) {

        ProcessInstanceResult op = client.newCreateInstanceCommand().bpmnProcessId("template_decision_id").latestVersion().variables(messageCode).withResult().send().join();
        return op.getVariables();
    }


    @PostMapping("sendemail")
    public Boolean startProcess(@RequestBody String messageFormat) {
        Boolean status;
        try {
            status = emailService.sendEmail(messageFormat);
        } catch (UnsupportedEncodingException e) {
            status = false;
        } catch (MessagingException e) {
            status = false;
        }
        return status;

    }


//    @JobWorker(type = "send_email")
//    public void sendEmail(final JobClient jobClient, final ActivatedJob job) {
//        System.out.println("Sending Email Event......");
//        client.newPublishMessageCommand().messageName("receive_email").correlationKey((String) job.getVariablesAsMap().get("caseId")).send().join();
//        jobClient.newCompleteCommand(job.getKey()).send().join();
//
//    }
//
//    @JobWorker(type = "collectingEmailTemplate")
//    public void collectingEmailTemplate(final JobClient jobClient, final ActivatedJob job) {
//
//        String templateName = job.getVariablesAsMap().get("templateName").toString();
//        System.out.println(templateName);
//
//        jobClient.newCompleteCommand(job.getKey()).send().join();
//
//    }
//
//    @JobWorker(type = "prepare_text")
//    public void prepareEmail(final JobClient jobClient, final ActivatedJob job) {
//
//    	String templateName = job.getVariablesAsMap().get("templateName").toString();
//    	System.out.println("Preparing Email Text......");
//        jobClient.newCompleteCommand(job.getKey()).send().join();
//    }


//    @JobWorker(type = "send_alert")
//    public void sendEmailAlert(final JobClient jobClient, final ActivatedJob job) {
//        System.out.println("Sending Email Alert......");
//        jobClient.newCompleteCommand(job.getKey()).send().join();
//    }
}
