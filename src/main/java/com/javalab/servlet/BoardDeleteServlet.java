	package com.javalab.servlet;
	
	import java.io.IOException;
	import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	
	import com.javalab.dao.BoardDao;
	import com.javalab.dto.BoardModel;
	
	/*
	 * 게시물 삭제 기능 서블릿
	 */
	
	@WebServlet("/boardDelete")
	public class BoardDeleteServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;
		
		private BoardDao boardDao = BoardDao.getInstance();
		
		/*
		 * 삭제 기능 처리 doGet 메소드
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// boardView.jsp => "get" => parameter no 값을 저장할 객체
			// 전달되는 parameter 명 : no
			BoardModel boardModel = new BoardModel();
			int n = Integer.parseInt(request.getParameter("no"));
			boardModel.setNo(n);
			
			boardDao.delete(boardModel);
			
			// 수정 작업완료 후에 게시물 목록 보기 서블릿으로 요청 재지정
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/boardList");
		}
	
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		}
	
	}
