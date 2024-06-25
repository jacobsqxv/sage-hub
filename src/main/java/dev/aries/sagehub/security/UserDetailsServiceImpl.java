package dev.aries.sagehub.security;

import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static dev.aries.sagehub.constant.ExceptionConstants.NO_USER_FOUND;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException(NO_USER_FOUND));
		return new UserDetailsImpl(user);
	}

}
