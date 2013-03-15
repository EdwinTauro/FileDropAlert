package uk.co.sparktech.filedropalert.action;

public interface Action {
	enum Actions {
		EMAIL,
		FTP,
		SFTP,
		COPY,
	}
	
	void process(ActionPayload payload);
}
