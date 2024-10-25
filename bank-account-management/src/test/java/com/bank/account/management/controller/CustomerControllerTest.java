package com.bank.account.management.controller;

import com.bank.account.management.controller.exception.CustomerException;
import com.bank.account.management.model.dto.CustomerDTO;
import com.bank.account.management.service.ICustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICustomerService customerService;

    @Test
    void createCustomer_shouldReturnCreatedCustomer() throws Exception {
        CustomerDTO createdCustomer = new CustomerDTO(1L, "elpepe", "elpepe@example.com", "elpepe123");
        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(createdCustomer);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"elpepe\",\"email\":\"elpepe@example.com\",\"username\":\"elpepe123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("elpepe"))
                .andExpect(jsonPath("$.email").value("elpepe@example.com"))
                .andExpect(jsonPath("$.username").value("elpepe123"));

        verify(customerService, times(1)).createCustomer(any(CustomerDTO.class));
    }

    @Test
    void createCustomer_shouldReturnBadRequest_whenNameIsMissing() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO(null, null, "elpepe@example.com", "elpepe123"); // missing "name"
        String jsonRequestMissingName = "{\"email\":\"elpepe@example.com\",\"username\":\"elpepe123\"}";

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestMissingName))
                .andExpect(status().isBadRequest()); // 400 Bad Request

        verify(customerService, times(0)).createCustomer(any(CustomerDTO.class));
    }

    @Test
    void createCustomer_shouldReturnBadRequest_whenEmailIsMissing() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO(null, "elpepe", null, "elpepe123"); // missing "email"
        String jsonRequestMissingEmail = "{\"name\":\"elpepe\",\"username\":\"elpepe123\"}";

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestMissingEmail))
                .andExpect(status().isBadRequest()); // 400 Bad Request

        verify(customerService, times(0)).createCustomer(any(CustomerDTO.class));
    }

    @Test
    void createCustomer_shouldReturnBadRequest_whenUsernameIsMissing() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO(null, "elpepe", "elpepe@example.com", null); // missing "username"
        String jsonRequestMissingUsername = "{\"name\":\"elpepe\",\"email\":\"elpepe@example.com\"}";

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestMissingUsername))
                .andExpect(status().isBadRequest()); // 400 Bad Request

        verify(customerService, times(0)).createCustomer(any(CustomerDTO.class));
    }


    @Test
    void getAllCustomers_shouldReturnCustomers() throws Exception {
        List<CustomerDTO> customers = Arrays.asList(
                new CustomerDTO(1L, "John Doe", "john@example.com", "johndoe123"),
                new CustomerDTO(2L, "Jane Doe", "jane@example.com", "janedoe123")
        );
        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"))
                .andExpect(jsonPath("$[1].email").value("jane@example.com"));

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    void getAllCustomers_shouldReturnNoContent_whenNoCustomersExist() throws Exception {
        // Arrange
        when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // 204 No Content

        verify(customerService, times(1)).getAllCustomers();
    }


    @Test
    void getCustomerById_shouldReturnCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO(1L, "John Doe", "john@example.com", "johndoe123");
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(get("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(customerService, times(1)).getCustomerById(1L);
    }

    @Test
    void getCustomerById_shouldReturnNotFound_whenCustomerDoesNotExist() throws Exception {
        // Arrange
        when(customerService.getCustomerById(1L)).thenThrow(new CustomerException.CustomerNotFoundException(1L));

        // Act & Assert
        mockMvc.perform(get("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // 404 Not Found

        verify(customerService, times(1)).getCustomerById(1L);
    }

    @Test
    void deleteCustomer_shouldReturnNoContent() throws Exception {
        doNothing().when(customerService).deleteCustomer(1L);

        mockMvc.perform(delete("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService, times(1)).deleteCustomer(1L);
    }

    @Test
    void deleteCustomer_shouldReturnNotFound_whenCustomerDoesNotExist() throws Exception {
        // Arrange
        doThrow(new CustomerException.CustomerNotFoundException(1L)).when(customerService).deleteCustomer(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // 404 Not Found

        verify(customerService, times(1)).deleteCustomer(1L); // Verifica que se llamó al servicio una vez
    }
}
