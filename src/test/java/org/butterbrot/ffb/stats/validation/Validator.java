package org.butterbrot.ffb.stats.validation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Defines a validation contract for data objects
 */
public abstract class Validator<T> {

    final static String PREFIX_DELIM = ".";

    private Logger logger;

    /**
     * Performs the validation, the method is expected to log differences and only differences between <code>baseline</code>
     * and <code>toValidate</code> or to delegate validation of complex types
     *
     * @param fieldPrefix prefix for log messages, this contains the path to the object to be validated within the
     *                    parent object hierarchy. Implementors are expected to concatenate this path with the
     *                    name of each child field that is evaluated, delimited by <code>PREFIX_DELIM</code>
     * @param baseline    the desired value
     * @param toValidate  the actual value
     */
    public abstract void validate(String fieldPrefix, T baseline, T toValidate);

    /**
     * Indicator method if this class can handle the passed object.
     *
     * @param data The object to be checked for handling
     * @return true if this class handles the given object
     */
    boolean handles(Object data) {
        Type type = ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        if (type instanceof ParameterizedType) {
            type = ((ParameterizedType) type).getRawType();
        }

        Class<T> genericClass = (Class<T>) type;
        return genericClass.isAssignableFrom(data.getClass());
    }

    protected Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(this.getClass());
        }

        return logger;
    }

    protected void logDifference(String fieldName, String expected, String actual) {
        getLogger().error("Field '{}' differs: '{}' does not match '{}'", fieldName, expected, actual);
    }

    protected String getCompoundName(String prefix, String fieldName) {
        return StringUtils.isEmpty(prefix) ? fieldName : String.join( PREFIX_DELIM, prefix,
               fieldName);
    }
}
