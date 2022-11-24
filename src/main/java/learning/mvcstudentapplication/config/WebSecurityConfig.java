package learning.mvcstudentapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/", "/students", "/webjars/**", "/css/**", "/img/**").permitAll()
                        .antMatchers("/students/new", "/students/update/**", "/students/delete/**",
                                "/groups/new", "/groups/update/**", "/groups/delete/**",
                                "/students/assessments/*/new", "/students/assessments/*/update/**",
                                "/students/assessments/*/delete/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout().logoutSuccessUrl("/");
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails root =
                User.withDefaultPasswordEncoder()
                        .username("root")
                        .password("root")
                        .roles("ADMIN")
                        .build();

        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(root, user);
    }

}
