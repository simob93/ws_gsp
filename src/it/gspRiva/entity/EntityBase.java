package it.gspRiva.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Type;
@MappedSuperclass
public class EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="DELETED_DATA", nullable = true)
	@Type(type="timestamp")
	private Date deletedData;
	
	@Column(name="INSERTDATA")
	@Type(type="timestamp")
	private Date insertData = new Date();

	public String getDeletedData() {
		String sdf = null;
		if (deletedData != null) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(deletedData.getTime());
		}
		return sdf;
	}

	@XmlTransient
	public Date getInsertData() {
		return insertData;
	}

	public void setInsertData(Date insertData) {
		this.insertData = insertData;
	}

}
