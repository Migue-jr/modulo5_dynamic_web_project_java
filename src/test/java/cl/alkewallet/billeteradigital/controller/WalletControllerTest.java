package cl.alkewallet.billeteradigital.controller;

import cl.alkewallet.billeteradigital.model.User;
import cl.alkewallet.billeteradigital.service.WalletService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class WalletControllerTest {

    @InjectMocks
    private WalletController walletController;

    @Mock
    private WalletService walletService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoGetBalance() throws Exception {
        when(request.getParameter("action")).thenReturn("balance");
        when(request.getParameter("userId")).thenReturn("1");
        User user = new User("1", "Ronoroa Zoro", 1000);
        when(walletService.getUser("1")).thenReturn(user);
        when(request.getRequestDispatcher("/WEB-INF/views/balance.jsp")).thenReturn(requestDispatcher);

        walletController.doGet(request, response);

        verify(request).setAttribute("user", user);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostDeposit() throws Exception {
        when(request.getParameter("action")).thenReturn("deposit");
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("amount")).thenReturn("500");

        walletController.doPost(request, response);

        verify(walletService).deposit("1", 500);
        verify(response).sendRedirect("wallet?action=balance&userId=1");
    }

    @Test
    public void testDoPostWithdraw() throws Exception {
        when(request.getParameter("action")).thenReturn("withdraw");
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("amount")).thenReturn("500");

        walletController.doPost(request, response);

        verify(walletService).withdraw("1", 500);
        verify(response).sendRedirect("wallet?action=balance&userId=1");
    }

    @Test
    public void testDoPostTransfer() throws Exception {
        when(request.getParameter("action")).thenReturn("deposit");
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("targetUserId")).thenReturn("2");
        when(request.getParameter("amount")).thenReturn("500");

        walletController.doPost(request, response);

        verify(walletService).transfer("1", "2", 500);
        verify(response).sendRedirect("wallet?action=balance&userId=1");
    }
}
