package vms.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;
import vms.configs.security.handlers.CustomAuthenticationSuccessHandler;
import vms.configs.security.service.AuthenticationService;


import javax.servlet.http.HttpServletRequest;


@Configuration
@ComponentScan("spring.security.project")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private CustomAuthenticationSuccessHandler successHandler;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		RequestMatcher csrfRequestMatcher = new RequestMatcher() {
			private AntPathRequestMatcher[] requestMatchers = {
					new AntPathRequestMatcher("/login")
			};

			@Override
			public boolean matches(HttpServletRequest httpServletRequest) {
				for (AntPathRequestMatcher requestMatcher : requestMatchers) {
					if (requestMatcher.matches(httpServletRequest)) {
						return true;
					}
				}
				return false;
			}
		};


		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http.csrf().disable().addFilterBefore(filter, CsrfFilter.class);
		http.authorizeRequests()
//				.antMatchers("/registration").permitAll()
//				.antMatchers("/user/**").hasAnyAuthority("USER")
//				.antMatchers("/main").hasAnyAuthority("ADMIN")
				.antMatchers("/").hasAnyAuthority("ADMIN", "USER")
				.and().formLogin().loginPage("/login").successHandler(successHandler)
				.usernameParameter("username").passwordParameter("password")
				.and().exceptionHandling().accessDeniedPage("/access_denied");
	}
}
