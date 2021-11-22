package ru.javawebinar.topjava.repository.jdbc;

import ru.javawebinar.topjava.model.AbstractBaseEntity;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class JdbcHibernateValidator {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    private final Validator validator = validatorFactory.getValidator();

    public Set<ConstraintViolation<AbstractBaseEntity>> validate(AbstractBaseEntity entity) {
        return validator.validate(entity);
    }
}
