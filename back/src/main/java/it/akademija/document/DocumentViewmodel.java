package it.akademija.document;

import java.time.LocalDate;

public class DocumentViewmodel {

	private long documentId;
	private String name;
	private LocalDate uploadDate;
	private String fullName;

	public DocumentViewmodel(long documentId, String name, LocalDate uploadDate, String fullName) {
		super();
		this.documentId = documentId;
		this.name = name;
		this.uploadDate = uploadDate;
		this.fullName = fullName;
	}

	public DocumentViewmodel(String name, LocalDate uploadDate) {
		super();
		this.name = name;
		this.uploadDate = uploadDate;
	}

	public DocumentViewmodel(DocumentEntity documentEntity) {
		super();
		this.documentId = documentEntity.getId();
		this.name = documentEntity.getName();
		this.uploadDate = documentEntity.getUploadDate();
		this.fullName = documentEntity.getUploader().getName() + " " + documentEntity.getUploader().getSurname();
	}

	/**
	 * Create DocumentViewmodel from DocumentEntity
	 *
	 * @param DocumentEntity
	 * @return
	 */

	public static DocumentViewmodel from(DocumentEntity documentEntity) {
		return new DocumentViewmodel(documentEntity);
	}

	/**
	 * Convert to DocumentEntity
	 *
	 * @return
	 */
	public DocumentEntity toDocumentEntity() {
		return new DocumentEntity(documentId, name, uploadDate);
	}

	public DocumentViewmodel() {
		super();
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDate uploadDate) {
		this.uploadDate = uploadDate;
	}

}
