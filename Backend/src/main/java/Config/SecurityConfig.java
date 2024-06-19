package Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(authorizeRequests ->
                    authorizeRequests
                            .requestMatchers("/customers/**").hasRole("SALES")
                            .requestMatchers("/billing/**").hasAnyRole("ACCOUNTANT", "ADMIN")
                            .requestMatchers("/payroll/**").hasRole("HR")
                            .requestMatchers("/users/**").hasRole("ADMIN")
                            .anyRequest().authenticated()
            )
            .httpBasic(withDefaults()); // Enables HTTP Basic authentication

    // Custom CSRF configuration
    http.csrf(csrf -> csrf.disable());

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User.withUsername("sales")
            .password(passwordEncoder().encode("password"))
            .roles("SALES").build());
    manager.createUser(User.withUsername("accountant")
            .password(passwordEncoder().encode("password"))
            .roles("ACCOUNTANT").build());
    manager.createUser(User.withUsername("hr")
            .password(passwordEncoder().encode("password"))
            .roles("HR").build());
    manager.createUser(User.withUsername("admin")
            .password(passwordEncoder().encode("password"))
            .roles("ADMIN").build());
    return manager;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
