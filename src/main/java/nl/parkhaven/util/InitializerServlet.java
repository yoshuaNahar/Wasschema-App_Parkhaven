package nl.parkhaven.util;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/InitializerServlet")
public class InitializerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);

    	try {
    		DBConnectionUtil.getConnection();
    	} catch(SQLException | PropertyVetoException e) {
    		e.printStackTrace();
    	}
    }
}
