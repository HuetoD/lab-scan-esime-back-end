package ipn.esimecu.labscan.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ipn.esimecu.labscan.dto.AdminDTO;
import ipn.esimecu.labscan.dto.response.AdminResponse;
import ipn.esimecu.labscan.service.SuperUserService;
import ipn.esimecu.labscan.web.controller.AdminController;
import ipn.esimecu.labscan.web.security.SecurityConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@Disabled
@Import(SecurityConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    private static final String API = "http://localhost:8081/admin/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SuperUserService superUserService;

    private static boolean noAdmins;

    private static AdminDTO adminDTO;

    private AdminResponse adminResponse;

    @Test
    @Order(1)
    public void testPing() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API.concat("ping")).contentType("text/html"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(2)
    public void testWithEmptyAdmins() throws Exception {
        Mockito.when(superUserService.loadAdmins()).thenReturn(Arrays.asList(adminResponse));
        String jsonResponse = mockMvc.perform(MockMvcRequestBuilders.get(API.concat("load-admins")).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<AdminResponse> admins = objectMapper.readValue(jsonResponse, new TypeReference<>() { });
        Assertions.assertEquals(noAdmins, admins.isEmpty());
    }

    @Test
    @Order(3)
    public void testAddAdmin() throws Exception {
        adminResponse.setEmail(adminDTO.getEmail());
        Mockito.when(superUserService.addAdmin(ArgumentMatchers.any())).thenReturn(adminResponse);
        String jsonResponse = mockMvc.perform(MockMvcRequestBuilders.post(API.concat("add-admin"))
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(objectMapper.writeValueAsString(adminDTO))
                                    )
                                    .andExpect(MockMvcResultMatchers.status().isCreated())
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();
        AdminResponse response = objectMapper.readValue(jsonResponse, AdminResponse.class);
        Assertions.assertEquals(response.getEmail(), adminDTO.getEmail());
    }

    @BeforeAll
    public static void setup() {
        noAdmins = false;
        adminDTO = new AdminDTO();
        adminDTO.setEmail("alguien@email.com");
        adminDTO.setPassword("Password");
    }

    @BeforeEach
    public void beforeEach() {
        adminResponse = new AdminResponse();
        adminResponse.setAdminId(12);
        adminResponse.setEmail("OnuEtMNDCG@example.com");
        adminResponse.setPassword("LZdvVEqf");
    }


}
