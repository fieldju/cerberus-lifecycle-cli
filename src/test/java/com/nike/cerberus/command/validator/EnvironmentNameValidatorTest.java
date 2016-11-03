package com.nike.cerberus.command.validator;

import com.beust.jcommander.ParameterException;
import org.junit.Test;

import javax.print.attribute.standard.MediaSize;

public class EnvironmentNameValidatorTest {

    private final static String NAME = "NAME";
    private final EnvironmentNameValidator validator = new EnvironmentNameValidator();

    @Test
    public void testValidateValidInput() {
        validator.validate(NAME, "valid");
        validator.validate(NAME, "valid_123");
        validator.validate(NAME, "Valid_123_UPPERCASE");
        validator.validate(NAME, "alphaNumericWithUnderscores_123_UPPERCASE");
        validator.validate(NAME, "1234");
        validator.validate(NAME, "1234_5678");
    }

    @Test(expected = ParameterException.class)
    public void testValidateInvalidComma() {
        validator.validate(NAME, ",");
    }

    @Test(expected = ParameterException.class)
    public void testValidateInvalidPeriod() {
        validator.validate(NAME, ".");
    }

    @Test(expected = ParameterException.class)
    public void testValidateInvalidPunc() {
        validator.validate(NAME, "punc!!");
    }

    @Test(expected = ParameterException.class)
    public void testValidateInvalidParen() {
        validator.validate(NAME, "(invalid)");
    }

    @Test(expected = ParameterException.class)
    public void testValidateInvalidSpace() {
        validator.validate(NAME, "invalid space");
    }

    @Test(expected = ParameterException.class)
    public void testValidateInvalidEmpty() {
        validator.validate(NAME, "");
    }
}