package uk.co.sparktech.filedropalert.util.property;

public class EmailPropertyReader {
	// We will reuse the default file reading for the application with keys specific to the current class.
	private final static EmailPropertyReader PROPERTY_READER = new EmailPropertyReader();
	
	private final AppPropertyReader PROPERTY = AppPropertyReader.getInstance();

	public static enum FILEDROPALERT_EMAIL_ACTION {
		MAIL_USERNAME, 
		MAIL_PASSWORD,
		MAIL_SMTP_AUTH,
		MAIL_SMTP_STARTTLS_ENABLE,
		MAIL_SMTP_HOST,
		MAIL_SMTP_PORT, 
		MAIL_MESSAGE_SUBJECT, 
		MAIL_MESSAGE_BODY, 
		MAIL_MESSAGE_INCLUDE_TIMESTAMP, 
		MAIL_MESSAGE_RECEPIENTS
	}

	private EmailPropertyReader() {
		// Default constructor
	}
	
	public static EmailPropertyReader getInstance() {
		
		return PROPERTY_READER;
	}
	
	public String getValue(FILEDROPALERT_EMAIL_ACTION type) {
		String key = null; 

		if (type.equals(FILEDROPALERT_EMAIL_ACTION.MAIL_USERNAME)) {
			key = "mail.username";
		} else if (type.equals(FILEDROPALERT_EMAIL_ACTION.MAIL_PASSWORD)) {
			key = "mail.password";
		} else if (type.equals(FILEDROPALERT_EMAIL_ACTION.MAIL_SMTP_AUTH)) {
			key = "mail.smtp.auth";
		} else if (type.equals(FILEDROPALERT_EMAIL_ACTION.MAIL_SMTP_STARTTLS_ENABLE)) {
			key = "mail.smtp.starttls.enable";
		} else if (type.equals(FILEDROPALERT_EMAIL_ACTION.MAIL_SMTP_HOST)) {
			key = "mail.smtp.host";
		} else if (type.equals(FILEDROPALERT_EMAIL_ACTION.MAIL_SMTP_PORT)) {
			key = "mail.smtp.port";
		} else if (type.equals(FILEDROPALERT_EMAIL_ACTION.MAIL_MESSAGE_SUBJECT)) {
			key = "mail.message.subject";
		} else if (type.equals(FILEDROPALERT_EMAIL_ACTION.MAIL_MESSAGE_BODY)) {
			key = "mail.message.body.text";
		}  else if (type.equals(FILEDROPALERT_EMAIL_ACTION.MAIL_MESSAGE_INCLUDE_TIMESTAMP)) {
			key = "mail.message.subject.include.timestamp";
		} else if (type.equals(FILEDROPALERT_EMAIL_ACTION.MAIL_MESSAGE_RECEPIENTS)) {
			key = "mail.message.recepients.comma.separated";
		}
		String value = PROPERTY.getValue(key);
		
		if (value == null) {
			throw new RuntimeException("Invalid value defined for Key. " + key);
		}
		key = null;
		
		return value;
	}
}
