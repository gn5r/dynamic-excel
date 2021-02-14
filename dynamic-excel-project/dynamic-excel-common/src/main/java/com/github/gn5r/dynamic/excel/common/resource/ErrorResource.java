package com.github.gn5r.dynamic.excel.common.resource;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.List;

import com.github.gn5r.dynamic.excel.common.exception.RestRuntimeException;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * {@link RestRuntimeException} ハンドリングレスポンス向けリソース
 *
 * @author gn5r
 */
@Data
@ApiModel(description = "エラーリソース")
public class ErrorResource {

	@ApiModelProperty(value = "HttpStatus")
	private HttpStatus status;

	@ApiModelProperty(value = "エラーメッセージ")
	private String message;

	@ApiModelProperty(value = "バリデーションエラーリスト")
	private List<FieldError> fieldErrors;
}
