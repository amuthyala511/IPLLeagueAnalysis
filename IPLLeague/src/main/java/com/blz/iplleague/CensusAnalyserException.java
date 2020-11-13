package com.blz.iplleague;

public class CensusAnalyserException extends Exception {

	enum ExceptionType {
		NO_SUCH_FILE, NO_SUCH_FIELD;
	}

	ExceptionType type;

	public CensusAnalyserException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}

	public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
		super(message, cause);
		this.type = type;
	}
}
