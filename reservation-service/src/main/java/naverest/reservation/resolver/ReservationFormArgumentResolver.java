package naverest.reservation.resolver;

import java.util.Date;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import naverest.reservation.annotation.ReservationForm;
import naverest.reservation.domain.ReservationInfo;
import naverest.reservation.sql.ReservationInfoSqls;

public class ReservationFormArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
        ReservationForm reservationForm = parameter.getParameterAnnotation( ReservationForm.class );
        if(reservationForm== null)
            return false;
        else
            return true;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        ServletModelAttributeMethodProcessor servletModelAttributeMethodProcessor = new ServletModelAttributeMethodProcessor(false);
        ReservationInfo reservationInfo = (ReservationInfo) servletModelAttributeMethodProcessor.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        reservationInfo.setReservationTel(reservationInfo.getReservationTel().replaceAll("[^0-9]", ""));
        reservationInfo.setReservationType(ReservationInfoSqls.ASKING);
        reservationInfo.setReservationDate(new Date());
        return reservationInfo;
	}

}
