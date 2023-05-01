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


@WebServlet("/boardModify")
public class BoardModifyServlet extends HttpServlet {
   
	// 자바에서 서블릿 객체를 톰캣에게 전달 할 때도 Byte 단위로 전송
	// 이 때도 직렬화 하는데 이 때 필요
	private static final long serialVersionUID = 1L;
   // 싱글톤 패턴으로 생성된 Dao 객체의 주소 참조
   private BoardDao boardDao = BoardDao.getInstance();
   // get요청 처리(회원 한명 조회)
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
      // (boardView.jsp) => "get" => parameter 값들을 저장할 객체
      // 전달되는 parameter 명 : no
      
      // 게시물 상세 보기 화면에서 전달받은 no를 정수로 변환
      // Dao에서 쿼리할때 no 정수타입(BoardModel.no도 정수타입)
      
      int n = Integer.parseInt(request.getParameter("no"));
      
      // 객체를 생성하지 않고 no만 단독으로 Dao로 전달해도 됨.
      // 전달 내용이 많을 경우 객체 사용하면 편리
      BoardModel boardModel = new BoardModel();
      boardModel.setNo(n);
      
      // 내용 보고 싶은 게시물 한개 조회
      BoardModel boardOne = boardDao.selectOne(boardModel);
      
      request.setAttribute("board", boardOne);
      RequestDispatcher rd = request.getRequestDispatcher("boardModify.jsp");
      rd.forward(request, response);
   }

   // Post요청(게시물 수정, 처리후에는 게시물 목록 페이지로 이동) 
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   // 파라미터로 전달되는 값들을 UTF-8로 인코딩
	   request.setCharacterEncoding("utf-8");
	   
	   // boardModify.jsp => "post" => parameter 값들을 저장할 객체
	   // 전달되는 parameter 명 : no, subject, writer, contents
	   BoardModel boardModel = new BoardModel();
	   
	   int n = Integer.parseInt(request.getParameter("no"));
	   boardModel.setNo(n);
	   boardModel.setSubject(request.getParameter("subject"));
	   boardModel.setWriter(request.getParameter("writer"));
	   boardModel.setContents(request.getParameter("contents"));
	   
	   // Dao사용자 수정 요청
	   boardDao.update(boardModel);
	   // 수정 작업 완료 후에 게시물 목록 보기 서블릿으로 요청 재지정
	   String contextPath = request.getContextPath();
	   response.sendRedirect(contextPath + "/boardList");
   }
}