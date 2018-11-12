package org.vaadin.klaudeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("custom-upload")
@HtmlImport("bower_components/custom-upload/custom-upload.html")
public class CustomUpload extends PolymerTemplate<CustomUpload.CustomUploadModel> implements HasSize {

	public interface CustomUploadModel extends TemplateModel {

		/**
		 * Returns the current validity state of the client. If any if the listed files
		 * on the client is marked as having an error the upload is not valid
		 * 
		 * @return the validity state, <code>true</code> if all listed files on the
		 *         client has no error, otherwise <code>false</code>
		 */
		boolean isValid();
	}

	@Id("upload")
	private Upload internalUpload;

	private final List<FileValidator> validators = new ArrayList<>();

	/**
	 * Creates a new instance of CustomUpload.
	 * <p>
	 * The receiver must be set before performing an upload.
	 */
	public CustomUpload() {
	}

	/**
	 * Create a new instance of CustomUpload with the given receiver.
	 *
	 * @param receiver receiver that handles the upload
	 */
	public CustomUpload(Receiver receiver) {

		internalUpload.setReceiver(receiver);

		internalUpload.addSucceededListener(e -> {
			String errors = validators.stream().map(l -> l.validate(e.getFileName(), e.getMIMEType()))
					.filter(result -> result.isError()).map(ValidationResult::getErrorMessage).filter(x -> x != null)
					.collect(Collectors.joining(","));
			if (errors != null) {
				this.getElement().callFunction("validationFailed", e.getFileName(), errors);
			}
		});
	}

	/**
	 * Adds a file validator to the upload. If any of the validator returns a
	 * failure, the uploaded file is marked with the validation error message on the
	 * client.
	 *
	 * @param validator the file validator to add
	 * @return registration for removal of listener
	 */
	public CustomUpload addFileValidator(FileValidator validator) {
		validators.add(validator);
		return this;
	}

	/**
	 * Returns the current validity state of the upload. If any if the listed files
	 * on the client is marked as having an error the upload is not valid
	 * 
	 * @return the validity state, <code>true</code> if all listed files on the
	 *         client has no error, otherwise <code>false</code>
	 */
	public boolean isValid() {
		return getModel().isValid();
	}
}
