<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html>
    <html lang="ko">

    <jsp:include page="inc/common/head.jsp">
        <jsp:param value="네이버 예약" name="title" />
    </jsp:include>

    <body>
        <div id="container">
            <!-- [D] 예약하기로 들어오면 header에 fade 클래스 추가로 숨김 -->
            <jsp:include page="inc/common/header.jsp" />
            <div class="wrap_review_list">
                <div class="review_header">
                    <div class="top_title gr">
                        <a href="#" class="btn_back" title="이전 화면으로 이동"> <i class="fn fn-backward1"></i> </a>
                        <h2><a class="title" href="#">오디컴퍼니 주식회사</a></h2>
                    </div>
                </div>
                <div class="section_review_list">
                    <div class="review_box">
                        <h3 class="title_h3">예매자 한줄평</h3>
                        <div class="short_review_area">
                            <div class="grade_area">
                                <span class="graph_mask">
                                    <em class="graph_value" style="width: 0%;"></em>
                                </span>
                                <strong class="text_value">
                                    <span>0.0</span>
                                    <em class="total">5.0</em>
                                </strong>
                                <span class="join_count">
                                    <em class="green">0건</em>
                                    등록
                                </span>
                            </div>
                            <ul class="list_short_review _reviewArea">
                               <!-- review-template Handlebar  -->
                            </ul>
                        </div>
                        <p class="guide"> <i class="spr_book2 ico_bell"></i> <span>네이버 예약을 통해 실제 방문한 이용자가 남긴 평가입니다.</span> </p>
                    </div>
                </div>
            </div>
        </div>
        <hr> </div>
        <footer>
            <div class="gototop">
                <a href="#" class="lnk_top"> <span class="lnk_top_text">TOP</span> </a>
            </div>
            <div id="footer" class="footer">
                <p class="dsc_footer">네이버(주)는 통신판매의 당사자가 아니며, 상품의정보, 거래조건, 이용 및 환불 등과 관련한 의무와 책임은 각 회원에게 있습니다.</p>
                <span class="copyright">© NAVER Corp.</span>
            </div>
        </footer>
        <div id="photoviwer" class="hide">
    		<div id="photo"></div>
    		<button class="close _close" type="button">
    			<span class="blind">닫기</span>
    			<span aria-role="presentation">X</span>
    		</button>
        </div>
        <script id="review-template" type="text/x-handlebars-template">
            <li class="list_item">
                <div>
                    <div class="review_area">
                        <div class="thumb_area">
                            {{#if commentImageList}}
                                <a href="#" class="thumb _commentThumb" title="이미지 크게 보기">
                                    {{#each commentImageList}} 
                                        {{#if @first}}
                                            <img width="90" height="90" class="_img img_vertical_top" src="/imgresources/{{saveFileName}}" alt="리뷰이미지">
                                        {{else}}
                                            <img width="90" height="90" class="_img img_vertical_top hide" src="/imgresources/{{saveFileName}}" alt="리뷰이미지">
                                        {{/if}}
                                    {{/each}} 
                                </a>
                                <span class="img_count">{{commentImageList.length}}</span>
                            {{/if}}
                        </div>
                        <h4 class="resoc_name">{{name}}</h4>
                        <p class="review">{{comment}}</p>
                    </div>
                    <div class="info_area">
                        <div class="review_info">
                            <span class="grade">{{roundUpToFirstPoint score}}</span>
                            <span class="name">{{username}}</span>
                            <span class="date">{{reservationDate}} 방문</span>
                        </div>
                    </div>
                </div>
            </li>
        </script>
        <script id="commentWrite-thumbnail-template" type="text/x-handlebars-template">
            <li class="item _thumb" data-id ="{{id}}">
                <a href="#" class="anchor">
                    <span class="spr_book ico_del">삭제</span>
                </a>
                <img src="/imgresources{{saveFileName}}" width="130" alt="" class="item_thumb">
                <span class="img_border"></span>
                <input type="hidden" name="fileIdList" value="{{id}}">
            </li>
    	</script>
        <script>
            var productId = ${param.productId};
            

            (function (Review) {
                var review = new Review(productId, {enableScrollRequest : true});
                review.getCommentList();

            })(window.reservation.Review)

            // function ReviewList(productId) {
            //     this.productId;
            //     this.APIURL;
            //     this.criteria;
            //     this.$statSection;
            //     this.isGet;
            //     this.$photoviwer;
            //     this.$reviewArea;
            //     this.photoId;
            //     this.$photo;
            //     this.commentImageflicking;

            //     this.initValue(productId);
            //     this.addEventHandler();
                
            //     this.getCommentList();
            // }
            
            // ReviewList.prototype.initValue = function(productId) {
            //     this.productId = productId;
            //     this.APIURL = "/reviews";
            //     this.$statSection = $(".short_review_area");
            //     this.criteria = {
            //         offset : 0,
            //         size : 10
            //     };
            //     this.isGet = false;
            //     this.$photoviwer = $("#photoviwer");
            //     this.$reviewArea = $("._reviewArea");
            //     this.photoId = "#photo";
            //     this.$photo = $("#photo");
            //     this.commentImageflicking;
            // }

            // ReviewList.prototype.getCommentList = function() {
            //     var objThis = this;
            //     $.ajax({
            //         url: this.APIURL + "/api?productId=" + this.productId,
            //         data: this.criteria
            //     }).done(function (res) {
            //         objThis.makeReviewElement(res);
            //         var commentStat = res.commentStats;
            //         var roundAverageScore = commentStat.averageScore.toFixed(1);
            //         var starBar = (roundAverageScore * 20) + "%";
            //         objThis.$statSection.find(".text_value > span").text(roundAverageScore);
            //         objThis.$statSection.find("em.graph_value").css("width", starBar);
            //         objThis.$statSection.find("em.green").text(commentStat.count +"건");
            //     });
            // }

            // ReviewList.prototype.makeReviewElement = function(res) {
            //     var html = "";
            //     var data = res.userCommentCollection;
            //     for(var i = 0; i<data.length; i++) {
            //         html += reviewTemplate(data[i]);
            //     }
            //     $(".list_short_review").append(html);
            //     this.isGet=false;
            // }

            // ReviewList.prototype.addEventHandler = function() {
            //     var objThis = this;
            //     $(window).on("scroll", function(event) {
            //         if(objThis.isGet) {
            //             return false;
            //         }
            //         if($(window).scrollTop()  >= $(document).height() - $(window).height() -50) {
            //             objThis.isGet = true;
            //             objThis.criteria.offset+=10;
            //             objThis.getCommentList(objThis.criteria);
            //         }
            //     });

            //     this.$reviewArea.on("click", "._commentThumb", this.commentThumbClickListener.bind(this));
                
            //     this.$photoviwer.on("click", "._close", this.photoviewCloseListener.bind(this));
            // }
            
            // ReviewList.prototype.commentThumbClickListener = function(e) {
            //     e.preventDefault();
            //     this.$photo.empty()
            //         .append($(e.currentTarget).find("._img")
            //             .clone()
            //             .removeClass("hide"));
            //     this.$photoviwer.removeClass("hide");
            //     //스크롤 막기
            //     $("body").css("overflow", "hidden");
            //     this.commentImageflicking = new eg.Flicking(this.photoId, {
            //         circular: true,
            //         defaultIndex: 0,
            //         duration: 300
            //     });
            // }

            // ReviewList.prototype.photoviewCloseListener = function() {
            //     this.$photoviwer.addClass("hide");
            //     $("body").css("overflow", "auto");
            //     this.commentImageflicking.destroy();
            // }

        </script>
        

    </body>

    </html>