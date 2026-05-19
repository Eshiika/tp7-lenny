package fr.ekod.cda.ja.tp7.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidReleaseYearValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidReleaseYear {

    String message() default "The year must be greater than or equal to 1888 and less than or equal to that year + 2";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}