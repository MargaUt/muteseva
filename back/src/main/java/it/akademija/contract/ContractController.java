package it.akademija.contract;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.akademija.journal.JournalService;
import it.akademija.journal.ObjectType;
import it.akademija.journal.OperationType;

@Controller
@Api(value = "contract")
@RequestMapping("/api/contract")
public class ContractController {

	private static final Logger LOG = LoggerFactory.getLogger(ContractController.class);

	@Autowired
	private JournalService journalService;

	@Autowired
	private ContractService contractService;

	/**
	 * Get generated contract
	 *
	 * @return generated contract PDF
	 */
	@Secured({ "ROLE_USER" })
	@GetMapping(path = "/get")
	@ApiOperation(value = "Get generated contract")
	public ResponseEntity<?> getGeneratedPdf(Long id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		journalService.newJournalEntry(OperationType.CONTRACT_GENERATED, id, ObjectType.CONTRACT,
				"Sugeneruota ir atsisiųsta darželio sutartis");

		return contractService.getPDF(id, request, response);
	}

	public JournalService getJournalService() {
		return journalService;
	}

	public void setJournalService(JournalService journalService) {
		this.journalService = journalService;
	}

	public ContractService getContractService() {
		return contractService;
	}

	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}

	public static Logger getLog() {
		return LOG;
	}

}
