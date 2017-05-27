package kata.domain.error;

import kata.domain.enums.CreditCardNumberValidationErrorType;

import java.util.List;

public class CreditCardNumberValidationResult {
	private List<CreditCardErrorItem> errors;

	public boolean hasErrors() {
		return false;
	}

	public void addErrorItemWithParams(CreditCardNumberValidationErrorType errorType, String description) {

	}

	public List<CreditCardErrorItem> getErrors() {
		return errors;
	}
}
