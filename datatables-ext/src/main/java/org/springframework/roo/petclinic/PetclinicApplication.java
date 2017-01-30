package org.springframework.roo.petclinic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.springlets.data.jpa.repository.support.DetachableJpaRepositoryImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * = PetclinicApplication
 *
 * TODO Auto-generated class documentation
 *
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = DetachableJpaRepositoryImpl.class)
public class PetclinicApplication {

    /**
     * TODO Auto-generated method documentation
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(PetclinicApplication.class, args);
    }
}
