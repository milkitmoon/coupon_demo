package com.milkit.app.common.exception;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.milkit.app.common.ErrorCodeEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceExceptionTest {

	
    @Test
    public void ServiceException_TEST() {
    	try {
    		throw new ServiceException(ErrorCodeEnum.NotSupportExcelType.getCode(), new String[]{"txt"});
    	} catch(Exception ex) {
    		log.debug("result:"+ex.getMessage());
    	}
    }
    
    @Test
    public void Exception_TEST() {
        Exception exception = assertThrows(ServiceException.class, () -> exceptions());
        
log.debug("exception.message:"+exception.getMessage());

        assertTrue( exception.getMessage().contains("txt"));
    }
    
    private void exceptions() throws Exception  {
    	throw new ServiceException(ErrorCodeEnum.NotSupportExcelType.getCode(), new String[]{"txt"});
    }
}
