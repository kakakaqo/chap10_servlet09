package com.javalab.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalab.dao.BoardDao;
import com.javalab.dto.BoardModel;

/**
 * Servlet implementation class MemberServlet
 */
@WebServlet("/boardView")
public class BoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	private BoardDao boardDao = BoardDao.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// boardList.jsp => "get" => parameter no 값을 저장할 객체
		// 전달되는 parameter 명 : no
		BoardModel boardModel = new BoardModel();
		int n = Integer.parseInt(request.getParameter("no"));
		boardModel.setNo(n);
		
		// 게시물 조회수 증가
		boardDao.updateHit(boardModel);
		BoardModel boardOne = boardDao.selectOne(boardModel);
		// 해당 데이터만 조회
		request.setAttribute("board", boardOne);
		RequestDispatcher rd = request.getRequestDispatcher("boardView.jsp");
		rd.forward(request, response);
	}
}
