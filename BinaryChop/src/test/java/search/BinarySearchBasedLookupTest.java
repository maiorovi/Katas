package search;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BinarySearchBasedLookupTest {

	private BinarySearchBasedLookup lookUp;

	@Before
	public void setUp() throws Exception {
		lookUp = new BinarySearchBasedLookup();
	}

	@Test
	public void lookUpInEmptyArrayReturnsMinusOne() throws Exception {
		assertThat(lookUp.find(new int[]{}, 7)).isEqualTo(-1);
	}

	@Test
	public void lookUpInArrayWithOneElementReturnsZeroForHit() throws Exception {
		assertThat(lookUp.find(new int[]{1}, 1)).isEqualTo(0);
	}

	@Test
	public void shouldReturnMinusOneWhenLookingUpForElementNotPresentInArray() throws Exception {
		assertThat(lookUp.find(new int[]{1,2}, 3)).isEqualTo(-1);
	}

	@Test
	public void findsLookedUpElementCorrectlyWhenItPlacedOnTheFirstPlaceInArray() throws Exception {
		assertThat(lookUp.find(new int[]{1,2,3}, 1)).isEqualTo(0);
	}

	@Test
	public void findsLookedUpElementCorrectlyWhenItPlacedOnTheSecondPlaceInArray() throws Exception {
		assertThat(lookUp.find(new int[]{1,2,3}, 2)).isEqualTo(1);
	}

	@Test
	public void findsLookedUpElementCorrectlyWhenItPlacedOnTheThirdPlaceInArray() throws Exception {
		assertThat(lookUp.find(new int[]{1,2,3}, 2)).isEqualTo(1);
	}

}
