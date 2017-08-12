define("detailPresenter", function() {
    "use strict";
    function DetailPresenter(reviewModel) {
        this.initVariable(reviewModel);
        this.addEventHandler();
        this.addTriggerHandler();
    }

    DetailPresenter.prototype.initVariable = function(reviewModel) {
        this.$header = $("#header");
        this.headerModifier="transparent";

        this.$detailContentWrapper = $("._detailContentWrapper");
        this.$detailContent = this.$detailContentWrapper.find("._content");
        this.detailContentModifier = "close3";
        this.$detailHinges = this.$detailContentWrapper.find("._hinge");

        this.$infoTabArea = $("._infoTabArea");
        this.$infoTabs = this.$infoTabArea.find("._tab");
        this.$infoContents = this.$infoTabArea.find("._content");
        this.setLazyImgPosition();

        this.reviewModel = reviewModel;
    }

    DetailPresenter.prototype.addEventHandler = function() {
        this.$infoTabArea.on("click", "._tab", this.infoTabClickListener.bind(this));
        this.$detailContentWrapper.on("click", "._hinge", this.hingeClickListener.bind(this));
        $(window).on("scroll", this.lazyLoadingHandler.bind(this));
        $(window).on("scroll", this.headerHandler.bind(this));
    }

    DetailPresenter.prototype.addTriggerHandler = function() {
        this.reviewModel.on("listArrive", this.listArriveCallback.bind(this));
    }

    DetailPresenter.prototype.infoTabClickListener = function(e) {
        e.preventDefault();
        var $currentTarget = $(e.currentTarget);

        this.toggleInfoTab($currentTarget);
        this.toggleInfoContent($currentTarget)
    }

    DetailPresenter.prototype.toggleInfoTab = function($currentTarget) {
        this.$infoTabs.removeClass("active");
        $currentTarget.addClass("active");
    }

    DetailPresenter.prototype.toggleInfoContent = function($currentTarget) {
        this.$infoContents.addClass("hide");
        var $contentToShow = $($currentTarget.data("content"));
        $contentToShow.removeClass("hide");
    }

    DetailPresenter.prototype.hingeClickListener = function(e) {
        e.preventDefault();
        this.$detailHinges.toggleClass("hide");
        this.$detailContent.toggleClass(this.detailContentModifier);
    }

    DetailPresenter.prototype.lazyLoadingHandler = function(e) {
        if (this.$lazyImg.offset().top < $(window).scrollTop() + $(window).height() + 100 ) {
            this.$lazyImg.attr("src" , this.$lazyImg.data("src"))
                         .removeClass("lazyImg")
                         .on("load",this.setLazyImgPosition.bind(this));
        }
    }

    DetailPresenter.prototype.headerHandler = function(e) {
        if ($(window).scrollTop() !== 0) {
            this.$header.removeClass(this.headerModifier);
        }
        else {
            this.$header.addClass(this.headerModifier);
        }
    }

    DetailPresenter.prototype.setLazyImgPosition = function() {
        this.$lazyImg = $(".lazyImg").eq(0);
    }

    DetailPresenter.prototype.listArriveCallback = function(res) {
        if(res.commentStats.count===0) {
            $(".section_review_list").addClass("hide");
        } else if (res.commentStats.count < 3) {
            $(".btn_review_more").addClass("hide");
        }
    }

    return DetailPresenter;
});
