<!-- boardWrite.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 본 어플리케이션의 컨텍스트를 경로를 갖고와서 변수에 저장해놓고 아래서 사용한다. -->
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>게시판 등록 폼</title>
</head>
<body>
 <h3>게시글 등록</h3>
 
   <form action="<c:url value='/boardWrite' />" method="post" >
   
	   <table border="1" summary="게시판 등록 폼">
	      <caption>게시글 등록 화면</caption>
	      <colgroup>
	         <col width="100" />
	         <col width="500" />
	      </colgroup>
	     <tbody> 
	         <tr>
	            <th align="center">제목</th>
	            <%-- required 반드시 입력값을 넣어야함 --%>
	            <td><input type="text" name="subject" size="80" maxlength="100" required /> 
	            </td>
	         </tr>
	         <tr>
	            <th align="center">작성자</th>
	            <td><input type="text" name="writer" maxlength="20" required />
	            </td>            
	         </tr>
	         <tr>
	            <td colspan="2">
	            <textarea name="contents" cols="80" rows="10" required></textarea>
	            </td>
	         </tr>   
	      </tbody>
	   </table>
   	<p>
   		<input type="submit" value="저장" />
   		<input type="button" value="목록" onclick="location.href='${contextPath}/boardList'" />
   	</p>
	</form>
</body>
</html>