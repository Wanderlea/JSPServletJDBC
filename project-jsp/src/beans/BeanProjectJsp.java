package beans;

public class BeanProjectJsp {

	private Long id;
	private String login;
	private String password;
	private String name;
	private String phone;
	private String zipCode;
	private String street;
	private String neighbor;
	private String city;
	private String state;
	private String ibge;	
	private String photoBase64;
	private String photoBase64Miniature;
	private String cvBase64;	
	private String contentType;
	private String contentTypeCV;
	private String tempPhotoUser;
	private boolean active;
	private String sex;
	private String profile;
	
	private boolean updateImage = true;
	private boolean updatePdf = true;
	
	public String getTempPhotoUser() {
		tempPhotoUser = "data:" + contentType + ";base64,"+ photoBase64;
		return tempPhotoUser;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNeighbor() {
		return neighbor;
	}

	public void setNeighbor(String neighbor) {
		this.neighbor = neighbor;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIbge() {
		return ibge;
	}

	public void setIbge(String ibge) {
		this.ibge = ibge;
	}

	public String getPhotoBase64() {
		return photoBase64;
	}

	public void setPhotoBase64(String photoBase64) {
		this.photoBase64 = photoBase64;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCvBase64() {
		return cvBase64;
	}

	public void setCvBase64(String cvBase64) {
		this.cvBase64 = cvBase64;
	}

	public String getContentTypeCV() {
		return contentTypeCV;
	}

	public void setContentTypeCV(String contentTypeCV) {
		this.contentTypeCV = contentTypeCV;
	}

	public String getPhotoBase64Miniature() {
		return photoBase64Miniature;
	}

	public void setPhotoBase64Miniature(String photoBase64Miniature) {
		this.photoBase64Miniature = photoBase64Miniature;
	}

	public boolean isUpdateImage() {
		return updateImage;
	}

	public void setUpdateImage(boolean updateImage) {
		this.updateImage = updateImage;
	}

	public boolean isUpdatePdf() {
		return updatePdf;
	}

	public void setUpdatePdf(boolean updatePdf) {
		this.updatePdf = updatePdf;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
}
