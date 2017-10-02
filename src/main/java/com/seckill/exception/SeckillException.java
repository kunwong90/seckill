package com.seckill.exception;

import com.seckill.enums.SeckillStatEnum;

public class SeckillException extends RuntimeException {

	public SeckillException(String message) {
		super(message);
	}
	
	public SeckillException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
