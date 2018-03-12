package com.nestos.dvdexchanger.exception;

/**
 * Ошибка нарушения бизнес логики приложения
 */
public class BusinessLogicConstraintViolationException extends RuntimeException {
    public BusinessLogicConstraintViolationException(String message) {
        super(message);
    }
}
