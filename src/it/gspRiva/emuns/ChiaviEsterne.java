package it.gspRiva.emuns;

public enum ChiaviEsterne {
	
	
	FK_ANAGRAFICA_CORSO_1("anagrafica_corso", "iscritto_corso", "ID", "IDANAGRAFICA_CORSO", "f.INSERITO = 'T'", "perchè il dato  è movimentato all'interno di un corso"),
	FK_ANAGRAFICA_1("anagrafica", "anagrafica_corso", "ID", "IDANAGRAFICA", "s.DELETED_DATA IS NULL", "perche è aperta una richiesta corso");
	
	private String entity;
	private String entityToCompare;
	private String fieldEntity;
	private String fieldEntityToCompare;
	private String condition;
	private String messaggio;
	
	ChiaviEsterne (String entity, String entityToCompare, String fieldEntity, String fieldEntityToCompare, String condition, String messaggio) {
		
		this.entity = entity;
		this.entityToCompare = entityToCompare;
		this.fieldEntity = fieldEntity;
		this.fieldEntityToCompare = fieldEntityToCompare;
		this.condition = condition;
		this.messaggio = messaggio;
	}
	
	
	public String getFieldEntity() {
		return fieldEntity;
	}
	public void setFieldEntity(String fieldEntity) {
		this.fieldEntity = fieldEntity;
	}
	public String getFieldEntityToCompare() {
		return fieldEntityToCompare;
	}
	public void setFieldEntityToCompare(String fieldEntityToCompare) {
		this.fieldEntityToCompare = fieldEntityToCompare;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getEntityToCompare() {
		return entityToCompare;
	}
	public void setEntityToCompare(String entityToCompare) {
		this.entityToCompare = entityToCompare;
	}
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	
}
