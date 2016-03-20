package com.jee.model.exception;

public class AgeRangeException extends Exception {
	private static final long serialVersionUID = 6790466479205382718L;

	@Override
	public String getMessage() {
		return "Age not in the acceptable range > 1 and < 120";
	}
}
