package com.root.meter;

import com.root.meter.DTO.UserDTO;
import com.root.meter.model.Users;

import junitparams.JUnitParamsRunner;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.transaction.Transactional;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitParamsRunner.class)
@Transactional
public class TestClass {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void createUser(@Autowired WebTestClient webClient) {
        UserDTO user = new UserDTO(1L,1L,"user12","user12@gmail.com","pass_0000","pass_0000","Texas","01098765432","london/door/4");

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<UserDTO> request = new HttpEntity<>(user, headers);
        ResponseEntity<Users> res = testRestTemplate.postForEntity("http://localhost:" + port + "/user/create", request,Users.class);
        MatcherAssert.assertThat(res.getStatusCode(), equalTo(HttpStatus.CREATED));
    }
    @Test
    public void retrieveUser(){

    }
}
