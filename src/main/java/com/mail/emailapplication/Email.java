package com.mail.emailapplication;

import java.util.ArrayList;

public class Email {
	@Override
	public String toString() {
		return "Email [messageBody=" + messageBody + ", recipients=" + recipients + "]";
	}

	private String messageBody;
	private ArrayList<String> recipients;

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public ArrayList<String> getRecipients() {
		return recipients;
	}

	public void setRecipient(ArrayList<String> recipients) {
		this.recipients = recipients;
	}

}
