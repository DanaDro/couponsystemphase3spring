package com.johnbryce.couponsystemphase2.Exception;

public class AlreadyExistException extends Exception {

	public AlreadyExistException(String message) {
		super(message+" already exsist Exception");
	}
}
