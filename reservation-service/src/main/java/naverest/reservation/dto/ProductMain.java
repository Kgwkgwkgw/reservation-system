package naverest.reservation.dto;
public class ProductMain {
	private Integer id;
	private String name;
	private String description;
	private Integer fileId;
	private String saveFileName;
	private String placeName;
	
	public ProductMain(){}
	
	public ProductMain(Integer id, String name, String description, Integer fileId, String saveFileName,
			String placeName) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.fileId = fileId;
		this.saveFileName = saveFileName;
		this.placeName = placeName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	
	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	
}
