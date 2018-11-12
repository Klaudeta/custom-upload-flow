# Custom Upload

Vaadin 11 Java integration of https://github.com/Klaudeta/custom-upload

This add-on allows to add a server validator the upload component to decide if the uploaded file is content valid to be accepted.

You can use the validation as follows:

```Java
upload.addFileValidator((filename, mimeType) -> {
	//do some server validation with the uploaded file and decide if the file is valid or not
	... server validation check ...
	return ValidationResult.error("File is of wrong template/format!");
});

```

If the server validation fails for any of the uploaded files, the files are marked with the validation error on the client.


