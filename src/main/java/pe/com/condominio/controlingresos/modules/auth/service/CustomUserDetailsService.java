package pe.com.condominio.controlingresos.modules.auth.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pe.com.condominio.controlingresos.modules.auth.repository.AuthRepository;
import pe.com.condominio.controlingresos.modules.auth.repository.model.CustomUserDetailModel;
import pe.com.condominio.controlingresos.modules.auth.repository.model.UserAuthModel;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private AuthRepository authRepository;
	
	public CustomUserDetailsService(AuthRepository authRepository) {
		this.authRepository = authRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAuthModel user =  authRepository.buscarUsuario(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
		
		List<String> roles = authRepository.obtenerRoles(user.getId());
		
		List<GrantedAuthority> authorities = roles.stream()
				.map(rol -> new SimpleGrantedAuthority("ROLE_"+rol))
				.map(authority -> (GrantedAuthority)authority)
				.toList();
		
		return new CustomUserDetailModel
				(user.getId(),
				 user.getNombres(), 
				 user.getApellidos(), 
				 user.getUsername(),
				 user.getPassswordHash(), 
				 authorities,
				 user.isLocked(), 
				 user.isDisabled());
		
   }

}
