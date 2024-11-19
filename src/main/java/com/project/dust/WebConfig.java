package com.project.dust;

import com.project.dust.web.filter.LogFilter;
import com.project.dust.web.filter.LoginCheckFilter;
import com.project.dust.web.interceptor.LogInterceptor;
import com.project.dust.web.interceptor.LoginCheckInterceptor;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); //등록할 필터를 지정
        filterRegistrationBean.setOrder(1); // 필터는 체인으로 동작하기 때문에 순서가 중요. 낮을 수록 선동작
        filterRegistrationBean.addUrlPatterns("/*"); // 필터를 적용할 URL 패턴 지정
        return filterRegistrationBean;
    }

//    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LogInterceptor()).order(1)
//                .addPathPatterns("/**");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/board/add",
                        "/board/update/*",
                        "/members/toggleFavorite",
                        "/members/getFavorites");
    }


}
