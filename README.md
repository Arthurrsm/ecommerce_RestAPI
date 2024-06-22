# Ecommerce Rest API

## Descrição

Este projeto é uma aplicação RESTful de um sistema de e-commerce desenvolvido em Java utilizando Spring Boot. Ele fornece endpoints para autenticação de usuários e gerenciamento de produtos, pedidos e usuários.

## Estrutura do Projeto

![Diagrama](https://github.com/Arthurrsm/ecommerce_RestAPI/assets/125709335/57a9b85d-d75f-498e-ae9d-5ce698d094df)

O projeto está organizado nas seguintes camadas:

- `application`: Contém a classe principal que inicia a aplicação Spring Boot.
- `config`: Configurações de segurança da aplicação.
- `controller`: Controladores REST que lidam com as requisições HTTP.
- `model`: Entidades que representam os dados da aplicação.
- `repository`: Repositórios para persistência de dados.
- `security`: Utilitários de segurança para geração e validação de tokens JWT.
- `service`: Serviços que contêm a lógica de negócios da aplicação.

## Requisições do Insomnia

![Postme 2](https://github.com/Arthurrsm/ecommerce_RestAPI/assets/125709335/cbeb8ef1-66ea-4170-87d1-9bdba223f199)
![Postme 1](https://github.com/Arthurrsm/ecommerce_RestAPI/assets/125709335/42133d0e-ed73-4fc8-9e6c-501dbe04f164)

## Arquivos e Funções

### `EcommerceRestApiApplication.java`

Este é o ponto de entrada da aplicação Spring Boot.

```html
<pre><code class="language-java">
@SpringBootApplication(scanBasePackages = {"com.example"})
public class EcommerceRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcommerceRestApiApplication.class, args);
    }
}
</code></pre>
```
Esta classe é responsável por iniciar a aplicação Spring Boot. Ela usa a anotação @SpringBootApplication para configurar a aplicação e o método main para iniciar a execução.

### `SecurityConfig.java`
```
<pre><code class="language-java">
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeRequests(authorize -> authorize
                // Permissões de acesso aos endpoints
                .requestMatchers(HttpMethod.POST, "/cadastrar").permitAll()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/nomeusuario/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/edicao/**").hasRole("ADMIN")
                .requestMatchers("/update/**").hasRole("ADMIN")
                .requestMatchers("/produtos/**").hasRole("GERENTE")
                .requestMatchers("/gerente/**").hasRole("GERENTE")
                .requestMatchers("/vendedores/**").hasRole("VENDEDOR")
                .requestMatchers("/pedidos/**").hasRole("VENDEDOR")
                .requestMatchers("/produtos/**").hasRole("CLIENTE")
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    // Definição de usuários em memória
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("Mauro Admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        UserDetails gerente = User.builder()
                .username("Marcia Gerente")
                .password(passwordEncoder().encode("gerente"))
                .roles("GERENTE")
                .build();
        UserDetails vendedor = User.builder()
                .username("Marcelo Vendedor")
                .password(passwordEncoder().encode("vendedor"))
                .roles("VENDEDOR")
                .build();
        UserDetails cliente = User.builder()
                .username("Joao Cliente")
                .password(passwordEncoder().encode("cliente"))
                .roles("CLIENTE")
                .build();
        return new InMemoryUserDetailsManager(gerente, admin, vendedor);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
</code></pre>
```

SecurityConfig configura a segurança da aplicação usando Spring Security. Define permissões de acesso aos endpoints baseado em roles de usuário, configura autenticação básica HTTP e define usuários em memória com suas respectivas roles.

### `UserController.java`
```
<pre><code class="language-java">
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody UserEntity usuario) {
        String token = userService.generateToken(usuario.getNome());
        return "Token: " + token;
    }

    @GetMapping("/nomeusuario/{token}")
    public String extractUsername(@PathVariable String token) {
        String username = userService.extractUsername(token);
        return username;
    }

    @Secured("GERENTE")
    @GetMapping(value = "/gerente/{token}")
    public String buscaGerente(@PathVariable String token) {
        String nomeUsuario = userService.extractUsername(token);
        return "Gerente: " + nomeUsuario;
    }

    @Secured("ADMIN")
    @GetMapping(value = "/admin/{token}")
    public String buscaAdmin(@PathVariable String token) {
        String nomeUsuario = userService.extractUsername(token);
        return "Admin: " + nomeUsuario;
    }
}
</code></pre>
```
UserController define endpoints REST para operações relacionadas a usuários. Inclui métodos para login, extração de nome de usuário a partir de um token JWT e consultas específicas para gerente e administração baseadas em autorização.

### `UserEntity.java`
```
<pre><code class="language-java">
public class UserEntity {

    private Long id;
    private String nome;
    private String email;
    private String senha;

    public UserEntity(Long id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters
}
</code></pre>
```
UserEntity representa a entidade de usuário com campos como id, nome, email e senha. Inclui um construtor para inicializar os campos e métodos para acesso aos dados (getters e setters).

### `JwtUtil.java`
```
<pre><code class="language-java">
public class JwtUtil {
    // Métodos para geração e validação de tokens JWT
}
</code></pre>
```
### `UserService.java`
```
<pre><code class="language-java">
@Service
public class UserService {

    public String generateToken(String nomeUsuario) {
        return JwtUtil.generateToken(nomeUsuario);
    }

    public String extractUsername(String token) {
        return JwtUtil.extractUsername(token);
    }
}
</code></pre>
```
UserService contém métodos de serviço para gerenciamento de tokens JWT. Utiliza o JwtUtil para gerar tokens com base no nome do usuário e extrair o nome de usuário de um token fornecido.
JwtUtil fornece métodos para geração e validação de tokens JWT. Utiliza a biblioteca io.jsonwebtoken para manipulação segura de tokens JWT, incluindo a geração com uma chave secreta e a extração do nome de usuário a partir do token.


## Conclusão

Este documento fornece uma visão geral da estrutura e implementação da API Restful usando Spring Boot, Spring Security e JWT para autenticação e autorização. Cada classe e componente desempenha um papel crucial na funcionalidade geral da aplicação.
