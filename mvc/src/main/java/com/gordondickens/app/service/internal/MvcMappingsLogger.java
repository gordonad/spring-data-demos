package com.gordondickens.app.service.internal;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author Gordon Dickens
 */
public class MvcMappingsLogger {
    private static final Logger logger = LoggerFactory.getLogger(MvcMappingsLogger.class);
    private static final String DIVIDER = StringUtils.repeat('*', 78);


    /**
     * Method level mappings
     */
    @Autowired(required = false)
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    @PostConstruct
    public void logMappedUrlPaths() {
        logger.debug("\n\n\n" + DIVIDER);
        if (requestMappingHandlerMapping != null) {
            Map<RequestMappingInfo, HandlerMethod> requestMap = requestMappingHandlerMapping.getHandlerMethods();
            for (RequestMappingInfo requestMappingInfo : requestMap.keySet()) {
                HandlerMethod handlerMethod = requestMap.get(requestMappingInfo);
                logger.debug("\n*** Request Mapping: {} = {} ***\n",
                        requestMappingInfo.getPatternsCondition().toString(),
                        handlerMethod.toString());
            }
        }
        logger.debug(DIVIDER + "\n\n\n");

    }

}
