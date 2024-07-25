package com.learn.reptile.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author zhangyugu
 * @Date 2021/4/11 9:33 下午
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> implements Serializable {

    private static final long serialVersionUID = 6044870841862371806L;

	private boolean ok = false;

    private Integer code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message = "请求出错";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> R<T> ok() {
        return new R<>().success();
    }

    public static <T> R<T> ok(String message) {
        return new R<>().success(message);
    }

    public static <T> R<T> ok(T data) {
        return new R(data).success();
    }

    public static <T> R<T> ok(T data, String message) {
        return new R(data).success(message);
    }

    public static <T> R<T> fail() {
        return new R().failure();
    }

    public static <T> R<T> fail(String message) {
        return new R().failure(message);
    }

    public static <T> R<T> failWithCode(Integer code, String message) {
        return new R().failure(code, message);
    }

    public static <T> R<T> fail(T data) {
        return new R(data).failure();
    }

    public static <T> R<T> fail(T data, String message) {
        return new R(data).failure(message);
    }

    public static <T> R<T> fail(Integer code, T data, String message) {
        return new R(data).failure(code, message);
    }

    private R(T data) {
        this.data = data;
    }

    private R success() {
        this.code = 200;
        this.ok = true;
        this.message = "请求成功";
        return this;
    }

    private R success(String message) {
        this.code = 200;
        this.ok = true;
        this.message = message;
        return this;
    }

    private R failure() {
        this.code = 500;
        this.ok = false;
        this.message = "请求失败";
        return this;
    }

    private R failure(String message) {
        this.code = 500;
        this.ok = false;
        this.message = message;
        return this;
    }

    private R failure(Integer code, String message) {
        this.code = code;
        this.ok = false;
        this.message = message;
        return this;
    }

	public boolean isSuccess() {
		return this.code == 200;
	}
}
