(function ($ , Handlebars) {
	"use strict";

    function Review(productId, size) {
        this.APIURL;
        this.NUM_OF_STAR;

        this.productId;

        this.$scorePercentage;
        this.$score;
        this.$count;
        this.criteria;
        this.isRequesting;
        this.$reviewList;

        this.$photoviwer;
        this.$photoviwerInner;
        this.photoviwerInner;
        this.commentImageflicking;

        this.reviewTemplate;

        this.initVariable(productId, size);
        this.addEventHandler();
        this.getCommentList();
    }

	Review.prototype = new eg.Component();
	Review.prototype.constructor = Review;

    Review.prototype.initVariable = function(productId, size) {
        this.APIURL = "/reviews";
        this.NUM_OF_STAR = 5;

        this.productId = productId;
        this.$scorePercentage = $("._scorePercentage");
        this.$score = $("._score");
        this.$count = $("._count");

        var size = size || 10;
        this.criteria = {
            offset : 0,
            size : size
        };

        this.isRequesting = false;
        this.$photoviwer = $("#photoviwer");
        this.$reviewList = $("._reviewList");
        this.photoviwerInner = "#photoviwerInner";
        this.$photoviwerInner = $(this.photoviwerInner);
        this.commentImageflicking;

        this.reviewTemplate =  Handlebars.compile($("#review-template").html());
        Handlebars.registerHelper("roundUpToFirstPoint", function (score) {
            return score.toFixed(1);
        });
    }
    Review.prototype.getCommentList = function() {
        $.ajax({
            url: this.APIURL + "/api?productId=" + this.productId,
            data: this.criteria
        }).done(this.getCommentListDone.bind(this));
    }
    Review.prototype.getCommentListDone = function(res) {
        if(res.userCommentList.length > 0) {
            this.makeCommentList(res.userCommentList);
            this.setCommentStats(res.commentStats);
            this.criteria.offset+=this.criteria.size;
        }
        this.isRequesting = false;
    }
    Review.prototype.makeCommentList = function(userCommentObjList) {
        var html = "";
        for(var i = 0; i<userCommentObjList.length; i++) {
            html += this.reviewTemplate(userCommentObjList[i]);
        }
        $(".list_short_review").append(html);
    }
    Review.prototype.setCommentStats = function(commentStatsObj) {
        var roundAverageScore = commentStatsObj.averageScore.toFixed(1);
        var scorePercentage = (roundAverageScore * 100 / this.NUM_OF_STAR) + "%";

        this.$score.text(roundAverageScore);
        this.$scorePercentage.width(scorePercentage);
        this.$count.text(commentStatsObj.count);
    }
    Review.prototype.addEventHandler = function() {
        var objThis = this;
        $(window).on("scroll", this.scrollEventHandling.bind(this));
        this.$reviewList.on("click", "._commentThumb", this.commentThumbClickListener.bind(this));

        this.$photoviwer.on("click", "._close", this.photoviewCloseListener.bind(this));
    }

    Review.prototype.scrollEventHandling = function(e) {
        if(this.isRequesting) {
            return false;
        }
        if($(window).scrollTop()  >= $(document).height() - $(window).height() - 50) {
            this.isRequesting = true;
            this.getCommentList();
        }
    }

    Review.prototype.commentThumbClickListener = function(e) {
        e.preventDefault();
        this.$photoviwerInner.empty()
            .append($(e.currentTarget).find("._img")
                .clone()
                .removeClass("hide"));
        this.$photoviwer.removeClass("hide");
        //스크롤 막기
        $("body").css("overflow", "hidden");
        this.commentImageflicking = new eg.Flicking(this.photoviwerInner, {
            circular: true,
            defaultIndex: 0,
            duration: 300
        });
    }
    Review.prototype.photoviewCloseListener = function() {
        this.$photoviwer.addClass("hide");
        $("body").css("overflow", "auto");
        this.commentImageflicking.destroy();
    }
    window.reservation = window.reservation || {};
	window.reservation.Review = Review;

})(jQuery, Handlebars);
