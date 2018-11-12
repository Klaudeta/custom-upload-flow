package org.vaadin.klaudeta;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;

public interface FileValidator{

    ValidationResult validate(String filename, String mimeType);
}