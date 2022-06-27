package it.akademija.contract;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.layout.font.FontProvider;

import it.akademija.application.Application;
import it.akademija.application.ApplicationService;

@Service
public class ContractService {

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private ServletContext servletContext;

	@Transactional
	public byte[] generatePdf(String contractHtml) throws IOException {

		/* Converting from xhtml to pdf */
		ByteArrayOutputStream target = new ByteArrayOutputStream();
		ConverterProperties converterProperties = new ConverterProperties();

		FontProvider fontProvider = new FontProvider("Times");
		fontProvider.addFont("/fonts/times.ttf");
		converterProperties.setFontProvider(fontProvider);

		HtmlConverter.convertToPdf(contractHtml, target, converterProperties);
		byte[] bytes = target.toByteArray();
		return bytes;

	}

	@Transactional
	public WebContext fillDataToThymeleafTemplate(Long id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		/* Getting the application details to insert into the contract */
		Application application = new Application();
		application = applicationService.getApplicationById(id);

		/* Inserting application details into the thymeleaf template */
		WebContext context = new WebContext(request, response, servletContext);
		context.setVariable("name", application.getApprovedKindergarten().getName());
		context.setVariable("director", application.getApprovedKindergarten().getDirector());
		context.setVariable("mainGuardian",
				(application.getMainGuardian().getName() + " " + application.getMainGuardian().getSurname()));
		context.setVariable("mainGuardianEmail", (application.getMainGuardian().getEmail()));
		context.setVariable("mainGuardianPhone", (application.getMainGuardian().getPhone()));
		context.setVariable("mainGuardianAddress", (application.getMainGuardian().getParentDetails().getAddress()));
		context.setVariable("child", (application.getChildName() + " " + application.getChildSurname()));

		return context;
	}

	@Transactional
	public String getHTMLFromTemplate(WebContext context) {

		String contractHtml = templateEngine.process("contract", context);
		TemplateEngine templateEngine = new TemplateEngine();
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("templates/mail/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setOrder(0);
		templateEngine.setTemplateResolver(templateResolver);
		return contractHtml;
	}

	@Transactional
	public ResponseEntity<?> getPDF(Long id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		WebContext context = fillDataToThymeleafTemplate(id, request, response);
		String html = getHTMLFromTemplate(context);
		byte[] bytes = generatePdf(html);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sutartis.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(bytes);

	}

	public ContractService() {
	}

	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}

	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public ApplicationService getApplicationService() {
		return applicationService;
	}

	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
