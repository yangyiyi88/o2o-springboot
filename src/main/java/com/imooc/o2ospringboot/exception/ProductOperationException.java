package com.imooc.o2ospringboot.exception;

public class ProductOperationException extends RuntimeException {
    public ProductOperationException (String msg) {
        super(msg);
    }
}
