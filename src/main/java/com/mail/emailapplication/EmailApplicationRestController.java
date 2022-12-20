package com.mail.emailapplication;


import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.websocket.server.PathParam;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/alert")
public class EmailApplicationRestController {

    @Autowired
    ZeebeClient client;
    @Autowired
    EmailApplicationService emailService;

    @GetMapping("template/{messageCode}")
    public Map<String, String> getTemplateNew(@PathVariable("messageCode") String messageCode) {
        ProcessInstanceResult op = client.newCreateInstanceCommand().bpmnProcessId("template_decision_id").latestVersion().variables(new MessageCode(messageCode)).withResult().send().join();
        System.out.println("result" + op.getVariables());
        System.out.println("val" + messageCode);

        Map<String, String> op_result = new HashMap<String, String>();
        op_result.put("htmlTemplate", ((Map<String, Object>) op.getVariablesAsMap().get("output")).get("htmlTemplate").toString());
        op_result.put("recipient", ((Map<String, Object>) op.getVariablesAsMap().get("output")).get("recipient").toString());

        return op_result;
    }


    @PostMapping("email")
    public Boolean startProcess(@RequestBody Email emailConfig) {
        System.out.println("Email IP " + emailConfig.toString());
        Boolean status;

        try {
            status = emailService.sendEmail(emailConfig.getMessageBody(), emailConfig.getRecipients());
        } catch (UnsupportedEncodingException e) {
            status = false;
        } catch (MessagingException e) {
            status = false;
        }
        return status;
    }


}
