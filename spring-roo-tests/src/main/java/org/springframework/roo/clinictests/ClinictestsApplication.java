package org.springframework.roo.clinictests;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.springlets.data.jpa.repository.support.DetachableJpaRepositoryImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * = ClinictestsApplication
 *
 * TODO Auto-generated class documentation
 *
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = DetachableJpaRepositoryImpl.class)
public class ClinictestsApplication {

    /**
     * TODO Auto-generated method documentation
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ClinictestsApplication.class, args);
    }
}
