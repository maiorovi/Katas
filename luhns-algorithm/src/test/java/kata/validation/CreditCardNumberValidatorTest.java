package kata.validation;

import kata.domain.CreditCardNumber;
import kata.domain.enums.CreditCardNumberValidationErrorType;
import kata.domain.error.CreditCardErrorItem;
import kata.domain.error.CreditCardNumberValidationResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static kata.domain.enums.CreditCardNumberValidationErrorType.CARD_NUMBER_OF_TOO_SMALL_LENGTH;
import static kata.domain.enums.CreditCardNumberValidationErrorType.IMPOSSIBLE_CREDIT_CARD_NUMBER;
import static kata.domain.enums.CreditCardNumberValidationErrorType.WRONG_CHARACTERS_USED_IN_CARD_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardNumberValidatorTest {

	@Mock private CreditCardNumber creditCardNumber;
	@Mock private CreditCardNumberValidationResult creditCardNumberValidationResult;

	private CreditCardNumberValidator creditCardNumberValidator;
	private CreditCardNumberValidator creditCardNumberValidatorSpy;
	private List<CreditCardErrorItem> creditCardErrorItems;

	@Before
	public void setUp() throws Exception {
		creditCardNumberValidator = new CreditCardNumberValidator();
		creditCardNumberValidatorSpy = spy(creditCardNumberValidator);
		creditCardErrorItems = new ArrayList<CreditCardErrorItem>();

		when(creditCardNumberValidatorSpy.creditCardNumberValidationResult()).thenReturn(creditCardNumberValidationResult);
	}


	@Test(expected = IllegalArgumentException.class)
	public void throwsIllegalArgumentExceptionOnNullInput() throws Exception {
		creditCardNumberValidatorSpy.isValid(null);
	}

	@Test
	public void returnsSpecificErrorWhenCreditCardContainsInvalidCharacter() throws Exception {
		mockInvocationOfAddErrorItems();
		when(creditCardNumber.getCardNumber()).thenReturn("1234 5678 9101 123!");

		CreditCardNumberValidationResult validationResult = creditCardNumberValidatorSpy.isValid(creditCardNumber);

		assertThat(validationResult.hasErrors()).isTrue();
		assertThat(validationResult.getErrors())
				.hasSize(1)
				.extracting("errorType", "errorDescription")
				.containsExactly(tuple(WRONG_CHARACTERS_USED_IN_CARD_NUMBER, "Credit Card number contains unsupported characters"));
	}

	@Test
	public void returnsUnsupportedCardNumberLengthErrorWhenCardNumberLengthIsTooSmall() throws Exception {
		mockInvocationOfAddErrorItems();
		when(creditCardNumber.getCardNumber()).thenReturn("1");

		CreditCardNumberValidationResult validationResult = creditCardNumberValidatorSpy.isValid(creditCardNumber);

		assertThat(validationResult.hasErrors()).isTrue();
		assertThat(validationResult.getErrors())
				.hasSize(1)
				.extracting("errorType", "errorDescription")
				.containsExactly(tuple(CARD_NUMBER_OF_TOO_SMALL_LENGTH, "credit card number length is too small"));
	}

	@Test
	public void validateCreditCardNumberForValidness() throws Exception {
		mockInvocationOfAddErrorItems();
		when(creditCardNumber.getCardNumber()).thenReturn("4539 1488 0343 6469");

		CreditCardNumberValidationResult validationResult = creditCardNumberValidatorSpy.isValid(creditCardNumber);

		assertThat(validationResult.hasErrors()).isTrue();
		assertThat(validationResult.getErrors())
				.hasSize(1)
				.extracting("errorType", "errorDescription")
				.containsExactly(tuple(IMPOSSIBLE_CREDIT_CARD_NUMBER, "provided credit card number can`t exist"));
	}

	@Test
	public void forCorrectCreditCardNumberReturnsValidationResultWithoutErrorItems() throws Exception {
		mockInvocationOfAddErrorItems();
		when(creditCardNumber.getCardNumber()).thenReturn("4539 1488 0343 6467");

		CreditCardNumberValidationResult validationResult = creditCardNumberValidatorSpy.isValid(creditCardNumber);

		assertThat(validationResult.hasErrors()).isFalse();
	}

	private void mockInvocationOfAddErrorItems() {
		doAnswer(
				new Answer() {
					public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
						CreditCardNumberValidationErrorType errorType = invocationOnMock.getArgument(0);
						String description = invocationOnMock.getArgument(1);
						creditCardErrorItems.add(new CreditCardErrorItem(errorType, description));
						when(creditCardNumberValidationResult.hasErrors()).thenReturn(true);
						when(creditCardNumberValidationResult.getErrors()).thenReturn(creditCardErrorItems);
						return null;
					}
				}
		).when(creditCardNumberValidationResult).addErrorItemWithParams(any(CreditCardNumberValidationErrorType.class), anyString());

	}
}
