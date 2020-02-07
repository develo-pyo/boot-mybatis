package com.jpp.web.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConstantsEnum {
   
	protected static final Logger logger = LoggerFactory.getLogger(ConstantsEnum.class);
	
	public static enum API_RESULT {
        SUCCESS (1, "success"),
        FAILURE (0, "fail"),  //기타 에러
        E_LENGTH(101, "input parameter length error"),  // 자리수 초과 에러
        E_PARAM_NULL(102, "input parameter is null"),  // 필수값 누락 에러
        E_FORMAT(103, "input parameter data format error"), // 파싱 에러
        E_NETWORK(105, "network error"); //네트워크 에러
        
        private int code;
        private String message;
        
        private API_RESULT(int _code, String _message) {
            this.code = _code;
            this.message = _message;
        }
        
        public static API_RESULT search(int code) {
            for (API_RESULT status : API_RESULT.values()) {
                if(status.getCode() == code) {
                    return status;
                }
            }
            return null;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
	
}
