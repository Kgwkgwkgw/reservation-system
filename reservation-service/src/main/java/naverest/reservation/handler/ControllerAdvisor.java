package naverest.reservation.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerAdvisor {
	private final Logger log = LoggerFactory.getLogger(ControllerAdvisor.class);
	
	@ExceptionHandler(BindException.class)
	public String bindHandle(BindException ex, Model model) {
		model.addAttribute("error", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
		return "error";
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String noHandlerHandle(NoHandlerFoundException ex, Model model) {
		model.addAttribute("error", "잘못된 경로입니다.");
		return "error";
	}
	
    @ExceptionHandler(Exception.class)
	public String handle(Exception ex) {
    		log.error(ex.getMessage());
    		ex.printStackTrace();
		return "error";
	}
}
