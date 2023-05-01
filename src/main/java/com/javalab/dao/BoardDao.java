package com.javalab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.javalab.dto.BoardModel;

/**
 * 데이터 베이스 관련된 C/R/U/D 메소드 구현
 * - 실제 Oracle DB에 접속하는 역할을 하며 각 Servlet에서 사용
 */

public class BoardDao {

	// 멤버 변수
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static DataSource dataSource;
	
	/*
	 * 자신의 참조 변수를 private static 으로 선언
	 */
	private static BoardDao instance;
	
	/*
	 * [싱글턴 패턴 생성자]
	 * 생성자를 private으로 선언
	 * - 외부에서는 이 생성자를 부를 수 없음.
	 * - getInstance()메소드에서 최초로 한번만 객체로 생성됨.
	 */
	private BoardDao() {
		System.out.println("여기는 BoardDao 생성자");
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/oracle");		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 현재 객체의 참조 변수를 반환해주는 메소드
	 * - 이 메소드가 최초로 호출 될 때 단 한번만 자신이 속한 클래스의 객체를 생성
	 * - 다음 부터는 최초에 생성도니 그 객체의 주소만 반환하게됨.
	 */
	public static BoardDao getInstance() {
		if(instance == null)
			instance = new BoardDao();
		return instance;
	}
	
	/*
	 * 게시판 목록 조회 : BoardListServlet의 doGet() 에서 호출
	 */
	public List<BoardModel> selectList(){
		String sql_query = "select no, subject, writer, hit from tbl_board";
		List<BoardModel> list = null;
		try {
			con = dataSource.getConnection(); // 커넥션 객체 얻기
			
			pstmt = con.prepareStatement(sql_query);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<BoardModel>();
			BoardModel model = null;
			
			while(rs.next()) {
				model = new BoardModel();
				model.setNo(rs.getInt("no"));
				model.setSubject(rs.getString("subject"));
				model.setWriter(rs.getString("writer"));
				model.setHit(rs.getInt("hit"));
				list.add(model);
				model = null;
			}
		} catch (SQLException e) {
			System.out.println("selectLust() ERR => " + e.getMessage());
		}finally {
			close();
		}
		return list;
	}
	
	/*
	 * 사용이 끝난 자원 해제
	 */
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(con != null) {
				con.close();
			}
		} catch (SQLException e) {
			System.out.println("CLOSED ERR => " + e.getMessage());
		}
	}
	
	// 게시물 한개 조회
	public BoardModel selectOne(BoardModel boardModel) {
		String sql_query = "select * from tbl_board where no = ?";
		BoardModel model = null;
		try {
			con = dataSource.getConnection(); // 커넥션 객체 얻기

			pstmt = con.prepareStatement(sql_query);
			pstmt.setInt(1, boardModel.getNo());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				model = new BoardModel();
				model.setNo(rs.getInt("no"));
				model.setSubject(rs.getString("subject"));
				model.setWriter(rs.getString("writer"));
				model.setContents(rs.getString("contents"));
				model.setHit(rs.getInt("hit"));
			}
		} catch (SQLException e) {
			System.out.println("selectOne() ERR = > " + e.getMessage());
		} finally {
			close();
		}
		return model;
	} // selectOne() end
	
	/*
	 * 게시글 수정 처리 : BoardModifyServlet의 doPost() 에서 호출
	 */
	public void update(BoardModel boardModel) {
		String sql_query = "update tbl_board";
		sql_query += " set subject=?,";
		sql_query += " writer=?,";
		sql_query += " contents=?";
		sql_query += " where no=?";
		
		try {
			con = dataSource.getConnection(); // 커넥션 객체 얻기

			pstmt = con.prepareStatement(sql_query);
			pstmt.setString(1, boardModel.getSubject());
			pstmt.setString(2, boardModel.getWriter());
			pstmt.setString(3, boardModel.getContents());
			pstmt.setInt(4, boardModel.getNo());
			int result = pstmt.executeUpdate();
			if(result > 0) {
				System.out.println(" => UPDATE SUCCESS !!");
			}

		} catch (SQLException e) {
			System.out.println("update() ERR = > " + e.getMessage());
		} finally {
			close();
		}
	} // update() end

	/*
	 * 게시글 등록 처리 :BoardWriteServlet의 doPost() 에서 호출
	 */
	public void inesrt(BoardModel boardModel) {
		String sql_query = "insert into tbl_board(no, subject, writer, contents)";
		sql_query += " values(seq_board.nextval, ?, ?, ?)";
		
		try {
			con = dataSource.getConnection(); // 커넥션 객체 얻기
			pstmt=con.prepareStatement(sql_query);
			pstmt.setString(1, boardModel.getSubject());
			pstmt.setString(2, boardModel.getWriter());
			pstmt.setString(3, boardModel.getContents());
			int result=pstmt.executeUpdate();
			if(result > 0) {
				System.out.println(" => INSERT SUCCESS !!");
			}
		} catch (SQLException e) {
			System.out.println("insert() ERR => " + e.getMessage());
		}finally {
			close();
		}
		
	}
	
	/**
	 * 게시글 조회수 증가 처리 및 조회수 수정 :
	 * BoardViewServlet의 doGet() 에서 데이터 조회전에 호출
	 * 
	 * @param boardModel
	 */
	public void updateHit(BoardModel boardModel) {
		String sql_query = "update tbl_board set hit=hit+1 where no=?";
		try {
			con = dataSource.getConnection(); // 커넥션 객체 얻기
			
			pstmt=con.prepareStatement(sql_query);
			pstmt.setInt(1, boardModel.getNo());
			int reuslt = pstmt.executeUpdate();
			if(reuslt > 0) {
				System.out.println(" => UPDATE HIT SUCCESS !!");
			}
		} catch (SQLException e) {
			System.out.println(" updateHit ERR() ERR => " + e.getMessage());
		}finally {
			close();
		}
	}
	
	/*
	 * 게시글 삭제
	 */
	public void delete(BoardModel boardModel) {
		String sql_query = "delete tbl_board where no = ?";
		try {
			con = dataSource.getConnection();
			
			pstmt=con.prepareStatement(sql_query);
			pstmt.setInt(1, boardModel.getNo());
			int reuslt = pstmt.executeUpdate();
			if(reuslt > 0) {
				System.out.println(" => DELETE SUCCESS !!");
			}
			
		} catch (SQLException e) {
			System.out.println(" delete ERR =>" + e.getMessage());
		}
		
	}
}