package com.ngrx.heroes.controller;


import com.ngrx.heroes.entity.Hero;
import com.ngrx.heroes.repository.HeroesRepository;
import com.ngrx.heroes.service.AuthService;
import com.ngrx.heroes.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/")
//@CrossOrigin(origins = "http://localhost:4200/")
public class HeroesController  {

    @Autowired
    private HeroesRepository heroesRepository;

    @Autowired
    private AuthService authService;


    @GetMapping("/{id}")
    public Hero getHeroById(@PathVariable Long id) {
        return heroesRepository.findByUserAndId(authService.getCurrentUser(),id).get();
    }
    
    @GetMapping("heroes")
    public List<Hero> getAllHeroes() {
        return heroesRepository.findByUser(authService.getCurrentUser());
    }

    @PostMapping("hero")
    public Hero createHero(@RequestBody Hero hero) {
        hero.setUser(authService.getCurrentUser());
        return  heroesRepository.save(hero);
    }

    

    @DeleteMapping("hero/{id}")
    public Boolean deleteHero(@PathVariable Long id) {

        return heroesRepository.findByUserAndId(authService.getCurrentUser(),id)
                .map(hero-> {
                    heroesRepository.delete(hero);
                    return true;
                }).orElseThrow(() -> new RuntimeException("Hero with id "+id+" does not exist"));


    }



    @PutMapping("hero/{id}")
    public Hero updateHero(@PathVariable Long id,
                                  @RequestBody Hero hero) {

	Optional<Hero> heroToBeUpdated = heroesRepository.findByUserAndId(authService.getCurrentUser(),id);

	if(heroToBeUpdated.isPresent()) {
       Hero hero2 = heroToBeUpdated.get();
        hero2.setName(hero.getName());
        hero2.setSaying(hero.getSaying());
        return heroesRepository.save(hero2);

	}
		throw new RuntimeException("hero with id "+id+" does not exist");

    }
}
