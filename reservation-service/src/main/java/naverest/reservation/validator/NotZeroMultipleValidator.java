package naverest.reservation.validator;

import static java.beans.Introspector.getBeanInfo;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import naverest.reservation.annotation.NotZeroMultiple;

public class NotZeroMultipleValidator implements ConstraintValidator<NotZeroMultiple, Object> {
    private String[] fields;
    private final Logger log = LoggerFactory.getLogger(NotZeroMultipleValidator.class);
    @Override
    public void initialize(NotZeroMultiple constraintAnnotation) {
        fields = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			
			BeanInfo info = getBeanInfo(value.getClass());
	        		long notNull = Stream.of(fields)
	        							 .map(field -> Stream.of(info.getPropertyDescriptors())
		                              .filter(desr -> desr.getName().equals(field))
		                              .findAny()
		                              .orElse(null))
	        							  .map(prop -> getProperty(prop, value))
	        							  .filter(ticketCount -> ticketCount != null)
	        							  .filter(ticketCount-> (Integer)ticketCount > 0)
	        							  .count();
			return notNull > 0;
		} catch (IntrospectionException e) {
			log.error("{}",e);
			return false;
		}
    }
    
    private Object getProperty(PropertyDescriptor prop, Object bean) {
        try {
            return prop.getReadMethod() == null ? null : prop.getReadMethod().invoke(bean);
        } catch (ReflectiveOperationException noAccess) {
            return null;
        }
    }
}
