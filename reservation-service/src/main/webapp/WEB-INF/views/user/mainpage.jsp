<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">

<jsp:include page="inc/common/head.jsp">
			<jsp:param value="네이버 예약" name="title"/>
</jsp:include>

<body>
    <div id="container">
    		<jsp:include page="inc/common/header.jsp"/>

        <div class="event">
        		<jsp:include page="inc/main/promotion.jsp"/>

            <jsp:include page="inc/main/event_tab.jsp">
				<jsp:param value="${categoryList }" name="categoryList"/>
			</jsp:include>

            <div class="section_event_lst">
                <p class="event_lst_txt">바로 예매 가능한 전시, 공연, 행사가 <span class="pink _categoryCount">0개</span> 있습니다</p>
                <div class="wrap_event_box">
                    <!-- [D] lst_event_box 가 2컬럼으로 좌우로 나뉨, 더보기를 클릭할때마다 좌우 ul에 li가 추가됨 -->
                    <ul class="lst_event_box" id="productListBefore">
                    </ul>
                    <ul class="lst_event_box" id="productListRear">
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="inc/common/footer.jsp"/>
</body>
<script id="productList-template" type="text/x-handlebars-template">
<li class="item" data-id="{{id}}">
    <a href="/products/{{id}}" class="item_book">
        <div class="item_preview">
			{{#if saveFileName}}
				<img alt="{{name}}" class="img_thumb" src="/imgresources{{saveFileName}}">
			{{else}}
				<img alt="{{name}}" class="img_thumb" src="/imgresources/no_img.png">
			{{/if}}
			<span class="img_border"></span>
		</div>

        <div class="event_txt">
            <h4 class="event_txt_tit"> <span>{{name}}</span> <small class="sm">{{placeName}}</small> </h4>
            <p class="event_txt_dsc">
              {{description}}
            </p>
        </div>
    </a>
</li>
</script>
<script>
    require(['product'], function(Product) {
        "use strict";
        var product = new Product();
    });
</script>
</html>
