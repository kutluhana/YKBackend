package demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.entity.Game;

public interface GameRepo extends JpaRepository<Game, Integer>{

}
