package com.megasolution.app.sistemaintegral;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource; 
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/css/**","/js/**","/img/**","/servicios/monitor").permitAll()
                .antMatchers("/clientes/**").hasAnyAuthority("usuario","admin")
                .antMatchers("/servicios/**").hasAnyAuthority("usuario","admin")
                .antMatchers("/aviso-leido").hasAnyAuthority("usuario","admin")
                .antMatchers("/usuarios/**").hasAuthority("admin")
                .antMatchers("/llamados/**").hasAuthority("admin")
                .antMatchers("/mensajes/**").hasAuthority("admin")
                .antMatchers("/sectores/**").hasAuthority("admin")
                .anyRequest().authenticated()
                .and()
                    .formLogin().defaultSuccessUrl("/")
                    .loginPage("/login").permitAll()
                .and()
                    .logout().permitAll();
    }
  
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder auth) throws Exception {

         auth.jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(passwordEncoder())
            .usersByUsernameQuery("select nombre_usuario, contraseña, habilitado from usuarios where nombre_usuario = ?")
            .authoritiesByUsernameQuery("select u.nombre_usuario, a.authority from authorities a inner join usuarios u on (u.authority_id = a.id) where u.nombre_usuario = ?"); 
             
    }

}
