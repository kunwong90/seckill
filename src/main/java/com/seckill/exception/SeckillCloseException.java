package com.seckill.exception;

/**
 * 秒杀关闭异常
 * @author wangkun
 *
 */
public class SeckillCloseException extends SeckillException {

	public SeckillCloseException(String message) {
		super(message);
	}
	
	public SeckillCloseException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
