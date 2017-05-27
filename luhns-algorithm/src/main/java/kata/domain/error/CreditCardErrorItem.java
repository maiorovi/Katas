package kata.domain.error;

import kata.domain.enums.CreditCardNumberValidationErrorType;

public class CreditCardErrorItem {
	private CreditCardNumberValidationErrorType errorType;
	private String errorDescription;

	public CreditCardErrorItem(CreditCardNumberValidationErrorType errorType, String errorDescription) {
		this.errorType = errorType;
		this.errorDescription = errorDescription;
	}

	public CreditCardNumberValidationErrorType getErrorType() {
		return errorType;
	}

	public String getErrorDescription() {
		return errorDescription;
	}
}
