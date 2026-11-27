package pe.com.condominio.controlingresos.modules.auth.repository.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetailModel implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final Long id;
    private final String nombres;
    private final String apellidos;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;
    private final boolean locked;
    private final boolean disabled;
    
    public CustomUserDetailModel(
            Long id,
            String nombres,
            String apellidos,
            String username,
            String password,
            List<GrantedAuthority> authorities,
            boolean locked,
            boolean disabled) {

        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.locked = locked;
        this.disabled = disabled;
    }

	public Long getId() {
		return id;
	}

	public String getNombres() {
		return nombres;
	}

	public String getApellidos() {
		return apellidos;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	@Override
	public boolean isEnabled() {
		return !disabled;
	}
	@Override
	public boolean isAccountNonExpired() {
		return !locked;
	}
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
