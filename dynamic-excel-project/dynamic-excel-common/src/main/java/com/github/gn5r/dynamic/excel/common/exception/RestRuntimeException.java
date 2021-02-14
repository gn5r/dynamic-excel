package com.github.gn5r.dynamic.excel.common.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

/**
 * dynamic-excel独自例外クラス
 *
 * @author gn5r
 */
public class RestRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * HTTP STATUS
	 */
	private HttpStatus status;

	/**
	 * メッセージ
	 */
	private String message;

	/**
	 * バリデーションエラーリスト
	 */
	private List<FieldError> fieldErrors;

	public RestRuntimeException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public RestRuntimeException(HttpStatus status, String message, List<FieldError> fieldErrors) {
		this.status = status;
		this.message = message;
		this.fieldErrors = fieldErrors;
	}

	/**
	 * HttpStatusを取得する
	 *
	 * @return HttpStatus
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * HttpStatusをセットする
	 *
	 * @param status HttpStatus
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	/**
	 * メッセージを取得する
	 *
	 * @return メッセージ
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * メッセージをセットする
	 *
	 * @param message メッセージ
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * バリデーションエラーリストを取得する
	 *
	 * @return バリデーションエラーリスト
	 */
	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	/**
	 * バリデーションエラーリストをセットする
	 *
	 * @param fieldErrors バリデーションエラーリスト
	 */
	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

}
