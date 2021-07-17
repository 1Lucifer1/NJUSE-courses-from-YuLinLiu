package com.example.config;

import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.regex.Pattern;


@Configuration
//@ImportResource("classpath:applicationContext.xml")
@Import({JpaConfig.class, CacheConfig.class})
@ComponentScan(basePackages={"com.example"},
        excludeFilters={
                @Filter(type= FilterType.CUSTOM, value= RootConfig.WebPackage.class)
        })
public class RootConfig {
    public static class WebPackage extends RegexPatternTypeFilter {
        public WebPackage() {
            super(Pattern.compile("com\\.example\\.web"));
        }
    }
}
