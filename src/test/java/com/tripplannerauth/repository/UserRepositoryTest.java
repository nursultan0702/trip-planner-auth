package com.tripplannerauth.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.tripplannerauth.entity.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

  @Autowired
  UserRepository userRepository;

  @Autowired
  JdbcConnectionDetails jdbcConnectionDetails;

  @BeforeEach
  void setUp() {
    User user = new User("awesome@mail.com", "Awesome", "Tester", true);
    User user2 = new User("awesome2@mail.com", "Awesome2", "Tester2", true);
    User user1 = new User("awesome3@mail.com", "Awesome3", "Tester3", true);
    List<User> users = List.of(user, user2, user1);
    userRepository.saveAll(users);
  }

  @Test
  void connectionEstablished() {
    assertThat(postgres.isCreated()).isTrue();
    assertThat(postgres.isRunning()).isTrue();
  }

  @Test
  void shouldReturnTrueWhenUserExist() {
    boolean existsByEmail = userRepository.existsByEmail("awesome@mail.com");
    assertThat(existsByEmail).isTrue();
  }
}