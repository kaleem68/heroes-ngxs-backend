package com.ngrx.heroes;

import com.ngrx.heroes.entity.Hero;
import com.ngrx.heroes.entity.User;
import com.ngrx.heroes.repository.HeroesRepository;
import com.ngrx.heroes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sun.security.util.Password;

@SpringBootApplication
public class HeroesApplication implements CommandLineRunner{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HeroesRepository heroesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(HeroesApplication.class, args);
	}

	@Override
	public void run(String... args) {

        heroesRepository.deleteAll();
        userRepository.deleteAll();

        userRepository.save(new User("user1",passwordEncoder.encode("password1")));
        userRepository.save(new User("user2",passwordEncoder.encode("password2")));

//        userRepository.findAll()
//                .stream()
//                .forEach(u-> System.out.println(u));

	    User user = userRepository.findByUsername("user1").get();
            Hero hero  = new Hero("John papa ","Iam one with angular and angular one with me");
            hero.setUser(user);
        heroesRepository.save(hero);

        User user2 = userRepository.findByUsername("user2").get();
            Hero hero2  = new Hero("Wardbell ","I love rxjs and ng grid.");
            hero2.setUser(user2);
        heroesRepository.save(hero2);


//        heroesRepository.findAll()
//                .stream()
//                .forEach(h-> System.out.println(h));
//
//        System.out.println("heroes for user 1 which is only john papa");
//        heroesRepository.findByUser(user)
//                .stream()
//                .forEach(p-> System.out.println(p));
	}

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:4200");
//            }
//        };
//    }

}
