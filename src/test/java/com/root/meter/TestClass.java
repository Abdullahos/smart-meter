package com.root.meter;

import com.root.meter.DTO.UserDTO;
import com.root.meter.api.UserApi;
import com.root.meter.model.Users;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "test")

public class TestClass {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserApi userApi;

    @LocalServerPort
    private int port;

    @Test
    public void createUser(){
        UserDTO user = new UserDTO(1L,1L,"user00","user00@gmail.com","pass_0000","pass_0000","Texas","01098765432","london/door/4");
        ResponseEntity<Users> res = restTemplate.getForEntity("localhost/" + port + "/user/create", Users.class);
        MatcherAssert.assertThat(res.getStatusCode(), equalTo(HttpStatus.CREATED));
    }
    @Test
    public void retrieveUser(){

    }
}
