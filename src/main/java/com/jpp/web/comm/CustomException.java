package com.jpp.web.comm;

import com.jpp.web.constants.ConstantsEnum;
import com.jpp.web.constants.ConstantsEnum.API_RESULT;

public class CustomException extends RuntimeException {

    private static final long serialVersionUID = -4848357483839823542L;
    private int ERR_CODE = API_RESULT.FAILURE.getCode();
    
    public CustomException()  {
        super();
    }

    public CustomException(String errDesc) {
        super(errDesc);
    }
    public CustomException(String errDesc, int code) {
        super(errDesc);
        this.ERR_CODE = code;
    }
    
    public CustomException(String errDesc, ConstantsEnum.API_RESULT e){
        super(errDesc);
        this.ERR_CODE = e.getCode(); 
    }
    
    public CustomException(ConstantsEnum.API_RESULT e){
        super(e.getMessage());
        this.ERR_CODE = e.getCode(); 
    }
    
    public String getErrorDesc() {
        return this.getMessage();
    }
    public int getErrCode() {
        return this.ERR_CODE;
    }
    
}
