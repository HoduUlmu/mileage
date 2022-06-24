package com.triple.mileage.config;

import com.triple.mileage.web.handlermapping.TypeHandlerMapping;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@Configuration
public class HandlerConfig extends WebMvcConfigurationSupport {
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping(ContentNegotiationManager contentNegotiationManager,
                                                                     FormattingConversionService conversionService,
                                                                     ResourceUrlProvider resourceUrlProvider) {
        TypeHandlerMapping handlerMapping = new TypeHandlerMapping();
        handlerMapping.setOrder(0);
        handlerMapping.setInterceptors(getInterceptors(conversionService, resourceUrlProvider));
        return handlerMapping;
    }
}
