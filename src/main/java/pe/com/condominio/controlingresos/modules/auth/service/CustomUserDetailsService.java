package pe.com.condominio.controlingresos.modules.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pe.com.condominio.controlingresos.modules.auth.model.AuthUser;
import pe.com.condominio.controlingresos.modules.auth.repository.AuthRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final AuthRepository authRepository;
	public CustomUserDetailsService(AuthRepository authRepository) {
		this.authRepository = authRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AuthUser user = authRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Credenciales Incorrectas.");
		}
		return user;
	}

}
