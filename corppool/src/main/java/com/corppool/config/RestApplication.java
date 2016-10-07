package com.corppool.config;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

import com.corppool.mobile.service.FeedsService;

/**
 * For servlet 3.0, this class is required to boot rest APIs
 * This now extends ResourceConfig to load additional classes
 * 
 * Refer: https://www.packtpub.com/books/content/restful-services-jax-rs-20
 * @author USER
 *
 */
@ApplicationPath("/rest")
public class RestApplication extends ResourceConfig {
	
	public RestApplication() {
        // Resources.
        packages(FeedsService.class.getPackage().getName());
		// MVC.
        register(JspMvcFeature.class);

        // Logging.
        register(LoggingFilter.class);

        // Tracing support.
        property(ServerProperties.TRACING, TracingConfig.ON_DEMAND.name());
    }
	
}
