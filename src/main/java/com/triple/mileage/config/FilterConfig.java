package com.triple.mileage.config;

import com.triple.mileage.web.filter.EventApiTypeFilter;
import com.triple.mileage.web.filter.EventsApiFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<EventsApiFilter> eventsApiFilter() {
        FilterRegistrationBean<EventsApiFilter> registrationBean = new FilterRegistrationBean<>(new EventsApiFilter());
        registrationBean.addUrlPatterns("/events");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<EventApiTypeFilter> eventsApiTypeFilter() {
        FilterRegistrationBean<EventApiTypeFilter> registrationBean = new FilterRegistrationBean<>(new EventApiTypeFilter());
        registrationBean.addUrlPatterns("/events");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
