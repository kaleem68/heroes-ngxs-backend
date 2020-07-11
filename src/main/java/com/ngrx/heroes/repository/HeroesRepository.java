package com.ngrx.heroes.repository;

import com.ngrx.heroes.entity.Hero;
import com.ngrx.heroes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeroesRepository extends JpaRepository<Hero,Long>{

    List<Hero> findByUser(User user);

    Optional<Hero> findByUserAndId(User user, Long id);
}
