package de.saxsys.dojo.bankocr;

import static org.hamcrest.Matchers.is;

import org.junit.Assert;
import org.junit.Test;

/**
 * Having done that, you quickly realize that the ingenious machine is not in
 * fact infallible. Sometimes it goes wrong in its scanning. The next step
 * therefore is to validate that the numbers you read are in fact valid account
 * numbers. A valid account number has a valid checksum. This can be calculated
 * as follows:
 * 
 * <pre>
 * account number:  3  4  5  8  8  2  8  6  5
 * position names:  d9 d8 d7 d6 d5 d4 d3 d2 d1
 * 
 * checksum calculation:
 * (d1+2*d2+3*d3 +..+9*d9) mod 11 = 0
 * </pre>
 * 
 * So now you should also write some code that calculates the checksum for a
 * given number, and identifies if it is a valid account number.
 * 
 * 
 * @author Sebastian Schmeck
 */
public class StoryTwoTest {

	@Test
	public void nineZerosAreValid() throws Exception {
		Assert.assertThat(AccountNumberValidator.isValid("000000000"), is(true));
	}

	@Test
	public void eigthZerosAndOneOneAreNotValid() throws Exception {

		Assert.assertThat(AccountNumberValidator.isValid("000000001"),
				is(false));
	}

	@Test
	public void NineteenAtTheEndIsValid() throws Exception {

		Assert.assertThat(AccountNumberValidator.isValid("000000019"), is(true));
	}

	@Test
	public void OneTwoThreeAtTheEndIsNotValid() throws Exception {

		Assert.assertThat(AccountNumberValidator.isValid("000000123"),
				is(false));
	}

	@Test
	public void OneTwoFourAtTheEndIsValid() throws Exception {

		Assert.assertThat(AccountNumberValidator.isValid("000000124"), is(true));
	}

	@Test
	public void OneInFrontAndTwoAtTheEndIsValid() throws Exception {

		Assert.assertThat(AccountNumberValidator.isValid("100000002"), is(true));
	}
}
