package naverest.reservation.handler;

import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerAdvisor {
	
	@ExceptionHandler(BindException.class)
	public String bindHandle(BindException ex, Model model) {
		model.addAttribute("error", ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
		return "error";
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String noHandlerHandle(NoHandlerFoundException ex, Model model) {
		model.addAttribute("error", "잘못된 경로입니다.");
		return "error";
	}
	
    @ExceptionHandler(Exception.class)
	public String handle(Exception ex) {
    	System.out.println(ex.getMessage());
		return "error";
	}
}
