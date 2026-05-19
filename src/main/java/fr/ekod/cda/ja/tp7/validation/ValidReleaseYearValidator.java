package fr.ekod.cda.ja.tp7.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidReleaseYearValidator implements ConstraintValidator<ValidReleaseYear, Integer> {

    @Override
    public boolean isValid(Integer releaseYear, ConstraintValidatorContext context) {
        return releaseYear >= 1888 && releaseYear <= (LocalDate.now().getYear() + 2);
    }

}
