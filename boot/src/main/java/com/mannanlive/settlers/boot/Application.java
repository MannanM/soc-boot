package com.mannanlive.settlers.boot;

import com.mannanlive.settlers.core.factory.game.GameFactory;
import com.mannanlive.settlers.core.factory.tile.TileGenerationStrategies;
import com.mannanlive.settlers.core.model.player.Player;
import com.mannanlive.settlers.core.repository.GameRepository;
import com.mannanlive.settlers.core.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

import static com.mannanlive.settlers.core.factory.tile.TileGenerationStrategies.RANDOM;
import static java.util.Collections.singletonList;

@SpringBootApplication(scanBasePackages = "com.mannanlive.settlers")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner createDefaultPlayerAndGame(ApplicationContext ctx) {
        return args -> {
            PlayerRepository playerRepository = ctx.getBean(PlayerRepository.class);
            Player player = playerRepository.add("Mannan");

            GameFactory factory = ctx.getBean(GameFactory.class);
            GameRepository gameRepository = ctx.getBean(GameRepository.class);
            gameRepository.add(factory.createDefault(4, singletonList(player), RANDOM));
        };
    }
}
