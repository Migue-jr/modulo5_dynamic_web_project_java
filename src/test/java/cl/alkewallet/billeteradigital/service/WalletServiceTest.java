package cl.alkewallet.billeteradigital.service;

import cl.alkewallet.billeteradigital.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WalletServiceTest {

    private WalletService walletService;

    @BeforeEach
    public void setUp() {
        walletService = new WalletService();
    }

    @Test
    public void testGetUser() {
        User user = walletService.getUser("1");
        assertNotNull(user);
        assertEquals("Ronoroa Zoro", user.getUserName());
    }

    @Test
    public void testDeposit() {
        walletService.deposit("1", 500);
        User user = walletService.getUser("1");
        assertEquals(1500, user.getBalance());
    }

    @Test
    public void testWithdraw() {
        walletService.withdraw("1", 500);
        User user = walletService.getUser("1");
        assertEquals(500, user.getBalance());
    }

    @Test
    public void testTransfer() {
        walletService.transfer("1", "2", 500);
        User user1 = walletService.getUser("1");
        User user2 = walletService.getUser("2");
        assertEquals(500, user1.getBalance());
        assertEquals(2000, user2.getBalance());
    }
}
