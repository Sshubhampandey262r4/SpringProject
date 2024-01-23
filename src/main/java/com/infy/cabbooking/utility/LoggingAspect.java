package com.infy.cabbooking.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.core.tools.picocli.CommandLine.Command;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.infy.cabbooking.exception.CabBookingException;

@Aspect
@Component
public class LoggingAspect
{

    private static final Log LOGGER = LogFactory.getLog(LoggingAspect.class);

    @AfterThrowing(pointcut = "execution(* com.infy.cabbooking.service.CabBookingServiceImpl.*(..))",throwing = "exception")
    public void logServiceException(CabBookingException exception)
    {
     LOGGER.error(exception.getMessage(),exception);
    }

}
