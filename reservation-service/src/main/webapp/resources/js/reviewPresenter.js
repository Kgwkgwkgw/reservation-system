define("reviewPresenter", ["reviewView"], function(ReviewView) {
    "use strict";

    function ReviewPresenter(reviewModel, objOption) {
        this.initVariable(reviewModel, objOption);
        this.addEventHandler();
        this.addTriggerHandler();

        this.reviewModel.reqCommentList();
    }

    ReviewPresenter.prototype.initVariable = function(reviewModel, objOption) {
        this.NUM_OF_STAR = 5;

        this.$scorePercentage = $("._scorePercentage");
        this.$score = $("._score");
        this.$count = $("._count");
        this.$btnReviewMore = $(".btn_review_more");
        this.objOption = objOption ? this.setOption(objOption) : this.defaultOption;

        this.$photoviwer = $("#photoviwer");
        this.$reviewList = $("._reviewList");
        this.photoviwerInner = "#photoviwerInner";
        this.$photoviwerInner = $(this.photoviwerInner);
        this.commentImageFlicking;

        this.reviewModel = reviewModel;
        this.reviewView = new ReviewView();

    }

    ReviewPresenter.prototype.addEventHandler = function(objOption) {
       if(this.objOption.isGetMoreCommentListWithScroll) {
           $(window).on("scroll", this.reviewModel.reqCommentList.bind(this.reviewModel));
       }
       this.$reviewList.on("click", "._commentThumb", this.commentThumbClickListener.bind(this));

       this.$photoviwer.on("click", "._close", this.photoviewCloseListener.bind(this));
   }

   ReviewPresenter.prototype.addTriggerHandler = function() {
       this.reviewModel.on("listArrive", this.reqCommentListDone.bind(this));
   }

   ReviewPresenter.prototype.defaultOption = {
       isGetMoreCommentListWithScroll : false
   }

   ReviewPresenter.prototype.setOption = function(objOption) {
       $.each(this.defaultOption, function (index, value) {
           if (!objOption.hasOwnProperty(index))
               objOption[index] = value;
       });
       return objOption;
   }

   ReviewPresenter.prototype.reqCommentListDone = function(res) {
       if(res.userCommentList.length > 0) {
           this.makeCommentList(res.userCommentList);
           this.setCommentStats(res.commentStats);
       }
       if(!this.objOption.isGetMoreCommentListWithScroll){
           if(res.commentStats.count <=3){
               this.$btnReviewMore.hide();
           }
       }
   }

   ReviewPresenter.prototype.makeCommentList = function(userCommentObjList) {
       var html = "";
       for(var i = 0; i<userCommentObjList.length; i++) {
           html += this.reviewView.reviewTemplate(userCommentObjList[i]);
       }
       this.$reviewList.append(html);
   }

   ReviewPresenter.prototype.setCommentStats = function(commentStatsObj) {
       var roundAverageScore = commentStatsObj.averageScore.toFixed(1);
       var scorePercentage = (roundAverageScore * 100 / this.NUM_OF_STAR) + "%";

       this.$score.text(roundAverageScore);
       this.$scorePercentage.width(scorePercentage);
       this.$count.text(commentStatsObj.count);
   }

   ReviewPresenter.prototype.commentThumbClickListener = function(e) {
       e.preventDefault();
       this.$photoviwerInner.empty()
                           .append($(e.currentTarget).find("._img")
                           .clone()
                           .removeClass("hide"));
       this.$photoviwer.removeClass("hide");

       $("body").css("overflow", "hidden");
       this.commentImageFlicking = new eg.Flicking(this.photoviwerInner, {
           circular: true,
           defaultIndex: 0,
           duration: 300
       });
   }

   ReviewPresenter.prototype.photoviewCloseListener = function(e) {
       this.$photoviwer.addClass("hide");
       $("body").css("overflow", "auto");
       this.commentImageFlicking.destroy();
   }

   return ReviewPresenter;
});
