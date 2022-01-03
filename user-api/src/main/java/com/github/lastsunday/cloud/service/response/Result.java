package com.github.lastsunday.cloud.service.response;

import java.io.Serializable;
import java.util.List;

public interface Result<T> extends Serializable {

    String getCode();
    Result<T> setCode(String code);
    String getMessage();
    Result<T> setMessage(String message);
    T getData();
    Result<T> setData(T data);
    List<ViolationItem> getViolationItems();
    Result<T> setViolationItems(List<ViolationItem> violationItems);
    Result<T> addViolationItem(String field, String message);
    String getErrorClass();
    Result<T> setErrorClass(String errorClass);
    String getErrorStack();
    Result<T> setErrorStack(String errorStack);
    boolean isSuccess();
    boolean isError();
    boolean isFailure();
    interface ViolationItem extends Serializable{

        String getField();

        void setField(String field);

        String getMessage();

        void setMessage(String message);
    }
}
