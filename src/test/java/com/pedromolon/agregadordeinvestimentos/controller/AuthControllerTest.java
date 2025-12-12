package com.pedromolon.agregadordeinvestimentos.controller;

import com.pedromolon.agregadordeinvestimentos.dto.request.LoginRequest;
import com.pedromolon.agregadordeinvestimentos.dto.request.RegisterRequest;
import com.pedromolon.agregadordeinvestimentos.entity.User;
import com.pedromolon.agregadordeinvestimentos.entity.enums.Role;
import com.pedromolon.agregadordeinvestimentos.repository.UserRepository;
import com.pedromolon.agregadordeinvestimentos.security.TokenConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private TokenConfig tokenConfig;

    @Test
    void deveRegistrarUsuarioComSucesso() throws Exception {

        RegisterRequest request = new RegisterRequest("Pedro", "pedro@email.com", "senha", Role.ROLE_USER);
        when(passwordEncoder.encode(any())).thenReturn("senhaCriptografada");

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("Pedro"))
                .andExpect(jsonPath("$.email").value("pedro@email.com"));

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deveRetornarErro404AoRegistrarUsuarioComEmailInvalido() throws Exception {
        RegisterRequest request = new RegisterRequest("Pedro", "pedro", "senha", Role.ROLE_USER);

        mockMvc.perform(post("/auth/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRealizarLoginComSucessoERetornarToken() throws Exception {
        LoginRequest request = new LoginRequest("pedro@email.com", "senha");
        User mockUser = new User();
        mockUser.setEmail("pedro@email.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(tokenConfig.generateToken(any())).thenReturn("token");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"));
    }

    @Test
    void deveRetornarErro401AoFazerLoginComEmailInvalido() throws Exception {
        LoginRequest request = new LoginRequest("pedro", "senha");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Usuário ou senha inválidos"));

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

}
