package it.akademija.security;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactErrorController implements ErrorController {

	@Value("classpath:public/index.html")
	private Resource index;

	@Value("classpath:public/myError500.html")
	private Resource error500;

	@RequestMapping("/error")
	public ResponseEntity<Resource> index(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.UNAUTHORIZED.value() || statusCode == HttpStatus.FORBIDDEN.value()) {
				return ResponseEntity.status(HttpStatus.valueOf(statusCode)).build();
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return ResponseEntity.ok(error500);
			}
		}
		return ResponseEntity.ok(index);
	}

}
