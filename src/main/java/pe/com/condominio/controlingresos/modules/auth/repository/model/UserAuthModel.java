package pe.com.condominio.controlingresos.modules.auth.repository.model;

public class UserAuthModel {
	private long id;
	private String nombres;
	private String apellidos;
	private String username;
	private String passswordHash;
	private boolean disabled;
	private boolean locked;


	public UserAuthModel() {

	}

	public UserAuthModel(long id, String nombres, String apellidos, String username, String passswordHash, boolean disabled,
			boolean locked) {
		super();
		this.id = id;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.username = username;
		this.passswordHash = passswordHash;
		this.disabled = disabled;
		this.locked = locked;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getPassswordHash() {
		return passswordHash;
	}

	public void setPassswordHash(String passswordHash) {
		this.passswordHash = passswordHash;
	}
}
