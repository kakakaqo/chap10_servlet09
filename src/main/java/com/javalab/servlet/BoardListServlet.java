package com.javalab.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalab.dao.BoardDao;
import com.javalab.dto.BoardModel;
/**
 * 싱글톤 패턴으로 생성된 DAO
 */
@WebServlet("/boardList")
public class BoardListServlet extends HttpServlet {
	// 톰캣에서 서블릿 객체를 생성해서 관리할 때도 Byte 단위로 전송(관리)함.
	// 이 때도 직렬화하는데 이때 필요함.
	private static final long serialVersionUID = 1L;

	// 싱글톤으로 만든 boardDao 객체 얻어옴.
	private BoardDao boardDao = BoardDao.getInstance(); // 다른곳에서도 이렇게 호출해서 사용
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 회원 목록 조회
		List<BoardModel> list = boardDao.selectList();
		
		// 2. 조회한 회원 목록을 request 객체에 저장
		request.setAttribute("boardList", list);
		
		// 3. 프로그램의 제어의 흐름을 boardList.jsp로 이동
		RequestDispatcher rd = request.getRequestDispatcher("boardList.jsp");
			rd.forward(request, response); 	
	}
}