package cl.alkewallet.billeteradigital.controller;

import cl.alkewallet.billeteradigital.model.User;
import cl.alkewallet.billeteradigital.service.WalletService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/wallet")
public class WalletController extends HttpServlet {
    private WalletService walletService = new WalletService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = request.getParameter("userId");

        if (userId == null || userId.isEmpty() || walletService.getUser(userId) == null) {
            request.setAttribute("error", "Usuario inexistente. Por favor, ingrese un User ID válido.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        if ("balance".equals(action)) {
            User user = walletService.getUser(userId);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/balance.jsp").forward(request, response);
        } else if ("deposit".equals(action)) {
            request.getRequestDispatcher("/deposit.jsp?userId=" + userId).forward(request, response);
        } else if ("withdraw".equals(action)) {
            request.getRequestDispatcher("/withdraw.jsp?userId=" + userId).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = request.getParameter("userId");
        double amount;
        try {
            amount = Double.parseDouble(request.getParameter("amount"));
            if (amount <= 0) {
                throw new NumberFormatException("El monto debe ser positivo");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Ingrese datos válidos.");
            if ("deposit".equals(action)) {
                request.getRequestDispatcher("/deposit.jsp").forward(request, response);
            } else if ("withdraw".equals(action)) {
                request.getRequestDispatcher("/withdraw.jsp").forward(request, response);
            }
            return;
        }

        User user = walletService.getUser(userId);
        if (user == null) {
            request.setAttribute("error", "Usuario inexistente. Por favor, ingrese un User ID válido.");
            if ("deposit".equals(action)) {
                request.getRequestDispatcher("/deposit.jsp").forward(request, response);
            } else if ("withdraw".equals(action)) {
                request.getRequestDispatcher("/withdraw.jsp").forward(request, response);
            }
            return;
        }

        if ("deposit".equals(action)) {
            String targetUserId = request.getParameter("targetUserId");
            User targetUser = walletService.getUser(targetUserId);

            if (targetUserId == null || targetUserId.isEmpty() || targetUser == null) {
                request.setAttribute("error", "Usuario de destino inexistente. Por favor, ingrese un User ID válido.");
                request.getRequestDispatcher("/deposit.jsp").forward(request, response);
                return;
            }

            if (user.getBalance() < amount) {
                request.setAttribute("error", "Saldo Insuficiente, intente con un monto válido.");
                request.getRequestDispatcher("/deposit.jsp").forward(request, response);
                return;
            }

            if (targetUserId.equals(userId)) {
                walletService.deposit(userId, amount);
            } else {
                walletService.transfer(userId, targetUserId, amount);
            }

        } else if ("withdraw".equals(action)) {
            if (user.getBalance() < amount) {
                request.setAttribute("error", "Saldo Insuficiente, intente con un monto válido.");
                request.getRequestDispatcher("/withdraw.jsp").forward(request, response);
                return;
            }
            walletService.withdraw(userId, amount);
        }

        response.sendRedirect("wallet?action=balance&userId=" + userId);
    }
}