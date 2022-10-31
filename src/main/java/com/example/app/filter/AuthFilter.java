package com.example.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request,
			ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
//以下のURLへのリクエストを受け取った際に、
		String uri = req.getRequestURI();
		if (!uri.endsWith("/login") &&
				!uri.endsWith("/addMember") &&
				!uri.endsWith("/addMemberConfirm") &&
				!uri.endsWith("/addMemberDone") &&
				!uri.contains("/css/") &&
				!uri.contains("/js/")) {
//セッションにmemberIdが保存してあるか確認し、nullの場合、
			if (session.getAttribute("memberId") == null) {
//ログイン画面へリダイレクトする
				res.sendRedirect("/");
				return;
			}
		}
		chain.doFilter(request, response);

	}

}
