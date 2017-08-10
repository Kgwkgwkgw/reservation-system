define("comment", ["Handlebars"], function(Handlebars) {
    "use strict";

    return eg.Class.extend(eg.Component, {
        construct : function(productId, objOption){
            this.initVariable(productId, objOption);
            this.addEventHandler();
            this.getCommentList();

        },

        defaultOption : {
            size : 10,
            isGetMoreCommentListWithScroll : false
        },

        initVariable : function(productId, objOption) {
            this.APIURL = "/api/comments";
            this.NUM_OF_STAR = 5;

            this.productId = productId;
            this.option = objOption ? this.setOption(objOption) : this.defaultOption;
            this.$scorePercentage = $("._scorePercentage");
            this.$score = $("._score");
            this.$count = $("._count");
            this.$btnReviewMore = $(".btn_review_more");
            this.criteria = {
                offset : 0,
                size : this.option.size
            };

            this.isRequesting = false;
            this.$photoviwer = $("#photoviwer");
            this.$reviewList = $("._reviewList");
            this.photoviwerInner = "#photoviwerInner";
            this.$photoviwerInner = $(this.photoviwerInner);
            this.commentImageFlicking;

            this.reviewTemplate =  Handlebars.compile($("#review-template").html());
            Handlebars.registerHelper("roundUpToFirstPoint", function (score) {
                return score.toFixed(1);
            });
        },

         addEventHandler : function() {
            if(this.option.isGetMoreCommentListWithScroll) {
                $(window).on("scroll", this.scrollEventHandling.bind(this));
            }
            this.$reviewList.on("click", "._commentThumb", this.commentThumbClickListener.bind(this));

            this.$photoviwer.on("click", "._close", this.photoviewCloseListener.bind(this));
        },

        getCommentList :  function() {
            this.isRequesting = true;
            $.ajax({
                url: this.APIURL + "?productId=" + this.productId,
                data: this.criteria
            }).done(this.getCommentListDone.bind(this));
        },

        getCommentListDone : function(res) {
            if(res.userCommentList.length > 0) {
                this.makeCommentList(res.userCommentList);
                this.setCommentStats(res.commentStats);
                this.criteria.offset+=this.criteria.size;
            }
            if(!this.option.isGetMoreCommentListWithScroll){
                if(res.commentStats.count <=3){
                    this.$btnReviewMore.hide();
                }
            }
            this.isRequesting = false;
        },

        setOption : function (objOption) {
            $.each(this.defaultOption, function (index, value) {
                if (!objOption.hasOwnProperty(index))
                    objOption[index] = value;
            });
            return objOption;
        },

        makeCommentList :  function(userCommentObjList) {
            var html = "";
            for(var i = 0; i<userCommentObjList.length; i++) {
                html += this.reviewTemplate(userCommentObjList[i]);
            }
            this.$reviewList.append(html);
        },

        setCommentStats : function(commentStatsObj) {
            var roundAverageScore = commentStatsObj.averageScore.toFixed(1);
            var scorePercentage = (roundAverageScore * 100 / this.NUM_OF_STAR) + "%";

            this.$score.text(roundAverageScore);
            this.$scorePercentage.width(scorePercentage);
            this.$count.text(commentStatsObj.count);
        },

        scrollEventHandling : function(e) {
            if(this.isRequesting) {
                return false;
            }
            if($(window).scrollTop()  >= $(document).height() - $(window).height() - 50) {
                this.getCommentList();
            }
        },

        commentThumbClickListener : function(e) {
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
        },

        photoviewCloseListener : function(e) {
            this.$photoviwer.addClass("hide");
            $("body").css("overflow", "auto");
            this.commentImageFlicking.destroy();
        }
    });
});
