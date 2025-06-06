package br.com.proxinvest.proxinvest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // //retirado para não dar problema
    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // http
    // .csrf(csrf -> csrf.disable())
    // .authorizeHttpRequests(auth -> auth
    // .requestMatchers("/signup", "/signin").permitAll() // libera essas rotas
    // .anyRequest().authenticated() // o resto precisa estar logado
    // )
    // .sessionManagement(sess ->
    // sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    // .httpBasic(); // ou .formLogin() dependendo do que você quer

    // return http.build();
    // }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // libera todas as rotas
                );
        return http.build();
    }

    // você pode configurar aqui o filtro de autenticação JWT depois
}
