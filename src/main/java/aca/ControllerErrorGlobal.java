package aca;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerErrorGlobal{
	
	public static final String DEFAULT_ERROR_VIEW = "errores";

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		
		// En otro caso, configura y envía al usuario a la vista de error por defecto.
	    ModelAndView mav = new ModelAndView();
	    mav.addObject("exception", e);
	    mav.addObject("url", req.getRequestURL());
	    mav.setViewName(DEFAULT_ERROR_VIEW);
	    return mav;
	}	
}