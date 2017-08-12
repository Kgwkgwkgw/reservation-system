<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${ !empty categoryList }">
	<div class="section_event_tab">
		<ul class="event_tab_lst tab_lst_min _option_lst">
			<!-- 활성화시 active 추가  -->
			<li class="item">
	                        <a class="_item anchor active" data-category-id=""> <span>전체</span> </a>
	        	</li>
			<c:forEach var="item" items= "${categoryList}" varStatus="status">
				<li class="item">
		                        <a class="_item anchor <c:if test="${status.last }">last</c:if>" data-category-id="${item.id }"> <span>${item.name }</span> </a>
		         </li>
		     </c:forEach>
		</ul>
	</div>
</c:if>
