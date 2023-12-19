package ipn.esimecu.labscan.services;

import ipn.esimecu.labscan.dto.AdminDTO;
import ipn.esimecu.labscan.dto.response.AdminResponse;
import ipn.esimecu.labscan.exception.EmailExistsException;
import ipn.esimecu.labscan.exception.RoleNotFoundException;
import ipn.esimecu.labscan.exception.UserNotFoundException;
import ipn.esimecu.labscan.service.SuperUserService;
import ipn.esimecu.labscan.repository.util.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@Disabled
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class SuperUserServiceTest {

    private final SuperUserService superUserService;

    @Autowired
    public SuperUserServiceTest(final SuperUserService superUserService) {
        this.superUserService = superUserService;
    }

    private AdminDTO refreshedAdmin;

    private static Optional<AdminResponse> firstAdmin = null;

    @Test
    @Order(1)
    public void loadAdminsTest() {
        List<AdminResponse> admins = superUserService.loadAdmins();
        Assertions.assertEquals(false, admins.isEmpty());
        firstAdmin = Optional.ofNullable(admins.get(0));
    }

    @Test
    @Order(2)
    public void existsEmailTest() {
        Assertions.assertDoesNotThrow(() -> superUserService.existsEmail("estemailnuncavaaexistir@email.com"));
    }

    @Disabled("Disabled because role admin is defined")
    @Test
    public void addAdminWithoutRoleDefinedTest() {
        Assertions.assertThrows(RoleNotFoundException.class, () -> superUserService.addAdmin(refreshedAdmin));
    }

    @Test
    @Order(3)
    public void checkLastAdminCannotBeNull() {
        Assertions.assertNotNull(firstAdmin.get());
    }

    @Test
    @Order(4)
    public void addAdminWithExistingEmailTest() {
        AdminDTO admin = new AdminDTO();
        admin.setEmail(firstAdmin.get().getEmail());
        admin.setPassword(firstAdmin.get().getPassword());
        Assertions.assertThrows(EmailExistsException.class, () -> superUserService.addAdmin(admin));
    }

    @Test
    @Order(5)
    public void updateAdminWithIdTest() {
        Assertions.assertDoesNotThrow(() -> refreshedAdmin.setAdminId(firstAdmin.map(AdminResponse::getAdminId).orElseThrow()));
        Assertions.assertDoesNotThrow(() -> superUserService.updateAdmin(refreshedAdmin));
    }

    @Test
    @Order(6)
    public void updateAdminWithoutIdTest() {
        Assertions.assertThrows(UserNotFoundException.class, () -> superUserService.updateAdmin(refreshedAdmin));
    }

    @Test
    @Order(7)
    public void removeAdminTest() {
        Assertions.assertDoesNotThrow(() -> superUserService.removeAdmin(firstAdmin.get().getAdminId()));
    }

    @Test
    @Order(8)
    public void addAdminTest() {
        final AdminResponse saved = superUserService.addAdmin(refreshedAdmin);
        Assertions.assertEquals(refreshedAdmin.getEmail(), saved.getEmail());
        Assertions.assertNotEquals(refreshedAdmin.getAdminId(), saved.getAdminId());
    }

    @BeforeEach
    public void beforeEach() {
        refreshedAdmin = new AdminDTO();
        refreshedAdmin.setEmail("remove".concat(Util.randomString(5)).concat("@example.com"));
        refreshedAdmin.setPassword(Util.randomString(8));
    }

}
