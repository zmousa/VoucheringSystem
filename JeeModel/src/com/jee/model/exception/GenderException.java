package com.jee.model.exception;

public class GenderException extends Exception{
	private static final long serialVersionUID = 5191549703003751639L;

	@Override
	public String getMessage() {
		return "Gender accepted value M and F";
	}
}
