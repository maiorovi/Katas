package kata.validation;

import com.google.common.collect.Sets;
import kata.domain.CreditCardNumber;
import kata.domain.error.CreditCardNumberValidationResult;
import org.springframework.util.Assert;

import java.util.Set;

import static kata.domain.enums.CreditCardNumberValidationErrorType.*;

public class CreditCardNumberValidator {

	private Set<Character> allowedCharacters = Sets.newHashSet('1', '2', '3', '4', '5', '6', '7', '8', '9', '0');

	public CreditCardNumberValidationResult isValid(CreditCardNumber creditCardNumber) {
		Assert.notNull(creditCardNumber, "credit card number is null");
		String cardNumberWithoutSpaces = creditCardNumber.getCardNumber().replaceAll("\\s+", "");

		CreditCardNumberValidationResult validationResult = creditCardNumberValidationResult();
		//TODO: usage of chain of responsibility looks appropriate here
		if (containsInvalidCharacters(cardNumberWithoutSpaces)) {
			validationResult.addErrorItemWithParams(WRONG_CHARACTERS_USED_IN_CARD_NUMBER, "Credit Card number contains unsupported characters");
		} else if (cardNumberWithoutSpaces.length() <= 1) {
			validationResult.addErrorItemWithParams(CARD_NUMBER_OF_TOO_SMALL_LENGTH, "credit card number length is too small");
		} else if (isCardInvalidByLuhnsAlgorithm(cardNumberWithoutSpaces)) {
			validationResult.addErrorItemWithParams(IMPOSSIBLE_CREDIT_CARD_NUMBER, "provided credit card number can`t exist");
		}

		return validationResult;
	}

	private boolean isCardInvalidByLuhnsAlgorithm(String creditCardNumber) {
		int totalSum = 0;

		for(int i = 1; i <= creditCardNumber.length(); i++) {
			int position = creditCardNumber.length() - i;
			if (i % 2 == 0) {
				int doubleNumber = 2 * toInt(creditCardNumber.charAt(position));
				int result = doubleNumber < 10 ? doubleNumber : doubleNumber - 9;
				totalSum += result;
			} else {
				totalSum += toInt(creditCardNumber.charAt(position));
			}

		}
		return (totalSum % 10 != 0);
	}

	private boolean containsInvalidCharacters(String cardNumber) {
		for (Character c : cardNumber.toCharArray()) {
			if (!allowedCharacters.contains(c)) {
				return true;
			}
		}


		return false;
	}

	public CreditCardNumberValidationResult creditCardNumberValidationResult() {
		return new CreditCardNumberValidationResult();
	}

	private int toInt(char c) {
		return Integer.parseInt(String.valueOf(c));
	}
}
