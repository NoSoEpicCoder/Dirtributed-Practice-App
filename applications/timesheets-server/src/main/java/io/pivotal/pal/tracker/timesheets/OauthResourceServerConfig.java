package io.pivotal.pal.tracker.timesheets;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.web.client.RestOperations;

@Configuration
@ConditionalOnProperty(value = "application.oauth-enabled", matchIfMissing = true)
public class OauthResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Bean
    @LoadBalanced
    public RestOperations restTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext oAuth2ClientContext) {
        return new OAuth2RestTemplate(resource, oAuth2ClientContext);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(null);
    }
}