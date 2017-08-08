package naverest.reservation.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer implements WebApplicationInitializer {
	private static final String CONFIG_LOCATION = "naverest.reservation.config";
    private static final String MAPPING_URL = "/";
	    
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub
		WebApplicationContext context = getContext();


        // encoding filter 설정
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("characterEncoding", characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");

        // dispatchder servlet 설정
        servletContext.addListener(new ContextLoaderListener(context));
        

        DispatcherServlet dp =  new DispatcherServlet(context);
        dp.setThrowExceptionIfNoHandlerFound(true);
        
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", dp);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(MAPPING_URL);
        
        
        // context-param 설정            
        servletContext.setInitParameter("STATIC_IMG_URL", "/resources/img");
        servletContext.setInitParameter("UPLOAD_IMG_URL", "/files");
	}
	
	private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);
        return context;
    }
}
