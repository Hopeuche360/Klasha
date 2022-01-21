package com.klasha.services.serviceImpl;

import com.klasha.dtos.LoginDto;
import com.klasha.dtos.SignupDto;
import com.klasha.models.Delivery;
import com.klasha.models.User;
import com.klasha.repositories.DeliveryRepository;
import com.klasha.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private DeliveryRepository deliveryRepository;
    @Mock
    private MockHttpSession session;
    @InjectMocks
    private UserServiceImpl userService;
    User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register(){
        SignupDto signupDto = new SignupDto();
        signupDto.setEmail("hopechijuka@gmail.com");
        signupDto.setName("Hope Chijuka");
        signupDto.setPassword("12345667");

        user.setEmail(signupDto.getEmail());
        user.setPassword(signupDto.getPassword());
        user.setName(signupDto.getName());
        String returnedValue = userService.register(signupDto);
        assertThat(returnedValue).isNotNull();
        assertThat(returnedValue).isEqualTo("User Registered Successfully");
    }

    @Test
    void login() {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("hopechijuka@gmail.com");
        loginDto.setPassword("12345667");

        user.setEmail(loginDto.getEmail());
        user.setPassword(loginDto.getPassword());
        when(userRepository.findUserByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword()))
                .thenReturn(Optional.of(user));
        String value = userService.login(loginDto, session);
        assertThat(value).isNotNull();
        assertThat(value).isEqualTo("User login Successfully");
    }

    @Test
    void addDeliveryLocation() {
        Delivery newDelivery = new Delivery();
        newDelivery.setAddress("Kano");
        newDelivery.setUser(user);
        when((User) session.getAttribute("user")).thenReturn(user);
        String value = userService.addDeliveryLocation("Kano", session);
        assertThat(value).isNotNull();
        assertThat(value).isEqualTo("Location added Successfully");
    }

    @Test
    void updateDeliveryLocation() {
        Delivery newDelivery = new Delivery();
        newDelivery.setId(1L);
        newDelivery.setAddress("Kano");
        newDelivery.setUser(user);
        when((User) session.getAttribute("user")).thenReturn(user);
        when(deliveryRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(newDelivery));
        String value = userService.updateDeliveryLocation(1L,"Kebbi", session);
        assertThat(value).isNotNull();
        assertThat(value).isEqualTo("Location updated successfully");
    }

    @Test
    void removeLocation() {
        Delivery newDelivery = new Delivery();
        newDelivery.setId(1L);
        newDelivery.setAddress("Kano");
        newDelivery.setUser(user);
        when((User) session.getAttribute("user")).thenReturn(user);
        when(deliveryRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(newDelivery));
        String value = userService.removeLocation(1L, session);
        assertThat(value).isNotNull();
        assertThat(value).isEqualTo("Location removed successfully");
    }

    @Test
    void viewLocations() {
        List<Delivery> deliveries = userService.viewLocations();
        assertThat(deliveries).isNotNull();
    }

    @Test
    void optimalRouteAndDeliveryCost() {
    }
}