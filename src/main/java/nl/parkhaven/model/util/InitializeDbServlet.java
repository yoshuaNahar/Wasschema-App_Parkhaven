package nl.parkhaven.model.util;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/InitializeDbServlet")
public class InitializeDbServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);

    	try {
    		Database.getConnection();
    	} catch(SQLException | PropertyVetoException e) {
    		e.printStackTrace();
    	}
    }
}
