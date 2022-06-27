package it.akademija.document;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.akademija.user.User;

@Entity
@Table(name = "documents")
public class DocumentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private String type;


	@Column
	private long size;

	@Lob
	private byte[] data;


	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH })
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User uploader;

	@Column
	private LocalDate uploadDate;

	public DocumentEntity(Long id, String name, String type, byte[] data, long size, User uploader,
			LocalDate uploadDate) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.data = data;
		this.size = size;
		this.uploader = uploader;
		this.uploadDate = uploadDate;
	}

	public DocumentEntity(Long id, String name, LocalDate uploadDate) {
		super();
		this.id = id;
		this.name = name;
		this.uploadDate = uploadDate;
	}

	public DocumentEntity(String name, String type, byte[] data, long size, User uploader, LocalDate uploadDate) {
		super();
		this.name = name;
		this.type = type;
		this.data = data;
		this.size = size;
		this.uploader = uploader;
		this.uploadDate = uploadDate;
	}

	public DocumentEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public LocalDate getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDate uploadDate) {
		this.uploadDate = uploadDate;
	}

	public User getUploader() {
		return uploader;
	}

	public void setUploader(User uploader) {
		this.uploader = uploader;
	}

}
