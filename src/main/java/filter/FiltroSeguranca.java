package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UsuarioBean;

@WebFilter("/*")
public class FiltroSeguranca implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String path = ((HttpServletRequest) request).getRequestURI();
		if (path.contains("/login.jsp")) {
			chain.doFilter(request, response);
		} else {
			HttpSession session = ((HttpServletRequest) request).getSession();
			UsuarioBean usuario = (UsuarioBean) session.getAttribute("usuario");
			if (usuario == null) {
				session.setAttribute("msg", "Você não está logado no sistema!");
				((HttpServletResponse) response).sendRedirect("/EstacionamentoWeb/login.jsp");
			} else {
				session.setMaxInactiveInterval(300);
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
