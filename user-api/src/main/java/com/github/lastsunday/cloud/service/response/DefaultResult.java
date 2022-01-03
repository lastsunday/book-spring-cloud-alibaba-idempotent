package com.github.lastsunday.cloud.service.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultResult<T> implements Result<T>, Serializable {

    public static final String SUCCESS_CODE = "0";
    public static final String UNKNOWN_ERROR = "1";
    public static final String ERROR_PREFIX = "SYS_";

    private String code;
    private String message;
    private T data;
    private String errorClass;
    private String errorStack;
    private List<ViolationItem> violationItems;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public DefaultResult<T> setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public DefaultResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public DefaultResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public List<ViolationItem> getViolationItems() {
        return violationItems;
    }

    @Override
    public DefaultResult<T> setViolationItems(List<ViolationItem> violationItems) {
        this.violationItems = violationItems;
        return this;
    }

    @Override
    public Result<T> addViolationItem(String field, String message) {
        if (violationItems == null) {
            violationItems = new ArrayList<>();
        }
        violationItems.add(new DefaultViolationItem(field, message));
        return this;
    }

    @Override
    public String getErrorClass() {
        return errorClass;
    }

    @Override
    public DefaultResult<T> setErrorClass(String errorClass) {
        this.errorClass = errorClass;
        return this;
    }

    @Override
    public String getErrorStack() {
        return errorStack;
    }

    @Override
    public DefaultResult<T> setErrorStack(String errorStack) {
        this.errorStack = errorStack;
        return this;
    }

    @Override
    public boolean isSuccess() {
        return SUCCESS_CODE.equals(code);
    }

    @Override
    public boolean isError() {
        return UNKNOWN_ERROR.equals(code) || (code != null && code.startsWith(ERROR_PREFIX));
    }

    @Override
    public boolean isFailure() {
        return (!isSuccess()) && (!isError());
    }

    public static class DefaultViolationItem implements ViolationItem, Serializable{
        private String field;
        private String message;

        public DefaultViolationItem() {

        }

        public DefaultViolationItem(String field, String message) {
            this.field = field;
            this.message = message;
        }

        @Override
        public String getField() {
            return field;
        }

        @Override
        public void setField(String field) {
            this.field = field;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DefaultViolationItem that = (DefaultViolationItem) o;
            return Objects.equals(field, that.field);
        }

        @Override
        public int hashCode() {
            return Objects.hash(field);
        }

        @Override
        public String toString() {
            return "DefaultViolationItem{" +
                    "field='" + field + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
