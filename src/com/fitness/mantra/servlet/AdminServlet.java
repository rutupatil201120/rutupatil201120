package com.fitness.mantra.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitness.mantra.dao.UserDao;
import com.fitness.mantra.model.User;

/**
 * ControllerServlet.java This servlet acts as a page controller for the
 * application, handling all requests from the user.
 */

@WebServlet(urlPatterns = { "/admin/", "/admin/user-list", "/admin/update-user", "/admin/delete-user" })
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UserDao userDao;

	public AdminServlet() {
		userDao = new UserDao();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequests(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequests(request, response);
	}

	private void handleRequests(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/admin/delete-user":
				deleteUser(request, response);
				break;
			case "/admin/update-user":
				handleUpdate(request, response);
				break;
			case "/admin/user-list":
				listUser(request, response);
				break;
			default:
				adminHome(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		if (isPostRequest(request)) {
			updateUser(request, response);
		} else {
			showEditForm(request, response);
		}
	}

	private boolean isPostRequest(HttpServletRequest request) {
		return "POST".equals(request.getMethod());
	}

	private void adminHome(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("AdminHome.jsp");
		dispatcher.forward(request, response);
	}

	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<User> listUser = userDao.selectAllUsers();
		request.setAttribute("listUser", listUser);
		showAdminPage(request, response, "AdminUserList.jsp");
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser = userDao.selectUser(id);
		request.setAttribute("user", existingUser);
		showAdminPage(request, response, "AdminUserUpdate.jsp");

	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		userDao.updateUser(getUser(request));
		redirectToAdmin(request, response, "user-list");
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		userDao.deleteUser(id);
		redirectToAdmin(request, response, "user-list");
	}

	private void showAdminPage(HttpServletRequest request, HttpServletResponse response, String page)
			throws ServletException, IOException {
		request.setAttribute("page", page);
		RequestDispatcher dispatcher = request.getRequestDispatcher("AdminHome.jsp");
		dispatcher.forward(request, response);
	}

	private void redirectToAdmin(HttpServletRequest request, HttpServletResponse response, String page)
			throws IOException {
		response.sendRedirect(request.getContextPath() + "/admin/" + page);
	}

	private User getUser(HttpServletRequest request) {

		int id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : 0;
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String userName = request.getParameter("email");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		return new User(id, firstName, lastName, userName, email, password);
	}
}