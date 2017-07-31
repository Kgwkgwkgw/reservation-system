(function($, Handlebars){
	"use strict";

	function Review(productId, userOption) {
		this.APIURL;
		this.IMAGE_URL;

		this.productId;
		this.option;
		this.criteria;
		this.isGet;
		this.$statSection;
		 
		this.THUMB_MAX_COUNT;
		this.MAX_COMMENT_LENGTH;
		this.MIN_COMMENT_LENGTH;
		this.$enrollForm;
		this.$imageInput;
		this.thumbnailCount;
		this.$listThumbnail;
		this.thumbItem;
		this.$thumbnailDeleteBtn;
		this.$TextArea;
		this.$commentLabel;
		this.strComment;
		this.$numOfText;
		this.scoreWrap;
		this.$scoreWrap;
		this.scoreRadioBtn;
		this.regCheckNoWhitespace;

		this.commentImageflicking;
		this.reviewTemplate;
		this.commentThumbnailTemplate;

		this.initVariable(productId, userOption);
		this.addEventHandling();
	}

	Review.prototype = new eg.Component();
	Review.prototype.constructor = Review;
	Review.prototype.initVariable = function(productId, userOption) {
		this.APIURL = "/reviews/api";
		this.IMAGE_URL = "images";

		this.productId = productId;
		this.criteria = {
                    		offset : 0,
                   			size : 10
						};
		this.option = userOption ? this.setOption(userOption) : this.getDefaultOption();
		this.isGet = false;
		this.$statSection = $(".short_review_area");

		this.THUMB_MAX_COUNT = 5;
		this.MAX_COMMENT_LENGTH = 400;
		this.MIN_COMMENT_LENGTH = 5;
		this.$enrollForm = $("._enrollForm");
		this.$imageInput = $("#reviewImageFileOpenInput");
		this.thumbnailCount = 0;
		this.$listThumbnail = $(".lst_thumb");
		this.thumbItem = "._thumb";
		this.$thumbnailDeleteBtn = $(".review_photos");
		this.$TextArea = $("#comment");
		this.$commentLabel = $("#commentLabel");
		this.strComment = "";
		this.$numOfText = $("._numOfText");
		this.scoreWrap = "._rating";
		this.$scoreWrap = $(this.scoreWrap);
		this.scoreRadioBtn = "._radio";
		this.regCheckNoWhitespace = /.*\S+.*/;

		this.reviewTemplate =  Handlebars.compile($("#review-template").html());
		this.commentThumbnailTemplate = Handlebars.compile($("#commentWrite-thumbnail-template").html());
		Handlebars.registerHelper("roundUpToFirstPoint", function (score) {
			return score.toFixed(1);
		});
	}

	Review.prototype.addEventHandling = function() {
		this.$scoreWrap.on("click", this.scoreRadioBtn, this.scoreClickHandling.bind(this));

		this.$enrollForm.on("submit", this.submitCommentHandling.bind(this));
		this.$commentLabel.on("click", function(e){
			$(this).addClass("hide");
		});

		this.$TextArea.on("focusout", this.commentFocusOutHandling.bind(this));
		this.$TextArea.on("keyup", this.keyUpHandling.bind(this));

		this.$imageInput.on("change", this.imageInputChangeHandling.bind(this));
		this.$thumbnailDeleteBtn.on("click", '.anchor', this.thumbnailDeleteHandling.bind(this));
		if(this.option.enableScrollRequest) {
			var objThis = this;
			$(window).on("scroll", function (event) {
				if (objThis.isGet) {
					return false;
				}
				if ($(window).scrollTop() >= $(document).height() - $(window).height() - 50) {
					objThis.isGet = true;
					objThis.criteria.offset += 10;
					objThis.getCommentList(objThis.criteria);
				}
			});
		}
	}

	Review.prototype.setOption = function(userOption) {
			$.each(this.getDefaultOption, function(index, value) {
				if(!userOption.hasOwnProperty(index))
					userOption[index]=value;
			});
			return userOption;
	}

	Review.prototype.getDefaultOption = function() {
		return {
				enableScrollRequest : false
		};
	}

	Review.prototype.scoreClickHandling = function(e) {
	  this.trigger("change", $(e.currentTarget).val());
	}

	Review.prototype.reqCreateImageFiles = function(formData){
		$.ajax({
			url : this.APIURL + this.IMAGE_URL,
			data : formData,
			type : "POST",
			contentType : false,
			processData : false
		}).done(this.createImageFilesDoneHandling.bind(this))
		  .fail(this.ajaxFailHandling.bind(this))
		  .always(this.createImageFilesAlwaysHandling.bind(this));
	}

	Review.prototype.createImageFilesDoneHandling = function(data, textStatus, xhr) {
		this.thumbnailCount++;
		var strCommentThumbnailTemplate = "";
		for(var i=0; i<data.length; i++){
			strCommentThumbnailTemplate += this.commentThumbnailTemplate(data[i]);
		}
		this.$listThumbnail.append(strCommentThumbnailTemplate);
	}

	Review.prototype.ajaxFailHandling = function(jqXHR){
		alert(jqXHR.responseJSON.message);
	}

	Review.prototype.createImageFilesAlwaysHandling = function() {
		this.$imageInput.val("");
	}

	Review.prototype.submitCommentHandling = function(e){
		if(!this.checkCommentValidation()){
				e.preventDefault();
		}
	}

	Review.prototype.checkCommentValidation = function(){
		this.strComment = this.$TextArea.val();
		if(this.isBlank(this.strComment)){
			alert("공백만 입력하면 안돼요");
			return false;
		}
		else if(this.strComment.length<=this.MIN_COMMENT_LENGTH){
			alert("최소 "+this.MIN_COMMENT_LENGTH+"자 이상 리뷰남겨주세요");
			return false;
		}
		return true;
	}

	Review.prototype.commentFocusOutHandling = function(e) {
		this.strComment = $(e.currentTarget).val();
		if(this.isBlank(this.strComment)){
			this.$commentLabel.removeClass("hide");
		}
	}

	Review.prototype.isBlank = function(str) {
		return !this.regCheckNoWhitespace.test(str);
	}

	Review.prototype.thumbnailDeleteHandling = function(e) {
		e.preventDefault();
		var $thumbDeleteBtn = $(e.currentTarget);
		var $thumbItem = $thumbDeleteBtn.closest(this.thumbItem);
		var fileId = $thumbItem.data("id");

		this.reqDeleteImageFile($thumbItem, fileId);
	}

	Review.prototype.reqDeleteImageFile = function($thumbItem, fileId){
		$.ajax({
			url : this.APIURL+this.IMAGE_URL+"/"+fileId,
			type : "DELETE"
		}).done(function(res){
			$thumbItem.remove();
		}).fail(this.ajaxFailHandling.bind(this));
	}

	Review.prototype.imageInputChangeHandling = function(e){
		var currentCount = this.thumbnailCount + this.$imageInput[0].files.length;

		if(currentCount>this.THUMB_MAX_COUNT) {
			alert("이미지 등록은 최대"+this.THUMB_MAX_COUNT+"개 입니다.");
			return false;
		} else {
			var formData = new FormData();
			$.each(this.$imageInput[0].files, function(i, file) {
				formData.append("images", file);
			});

			this.reqCreateImageFiles(formData);
		}
	}

	Review.prototype.keyUpHandling = function() {
		this.strComment = this.$TextArea.val();
		if(this.strComment.length > this.MAX_COMMENT_LENGTH){
			alert(this.MAX_COMMENT_LENGTH+"자 초과");
			this.strComment = this.strComment.substring(0, this.MAX_COMMENT_LENGTH);
			this.$TextArea.val(this.strComment);
		}
		this.$numOfText.text(this.strComment.length);
	}

	Review.prototype.getCommentList = function () {
		var objThis = this;
		$.ajax({
			url: this.APIURL + "?productId=" + this.productId,
			data: this.criteria
		}).done(function (res) {
			objThis.makeReviewElement(res);
			var commentStat = res.commentStats;
			var roundAverageScore = commentStat.averageScore.toFixed(1);
			var starBar = (roundAverageScore * 20) + "%";
			objThis.$statSection.find(".text_value > span").text(roundAverageScore);
			objThis.$statSection.find("em.graph_value").css("width", starBar);
			objThis.$statSection.find("em.green").text(commentStat.count + "건");
		});
	}

	Review.prototype.makeReviewElement = function (res) {
		var html = "";
		var data = res.userCommentCollection;
		for (var i = 0; i < data.length; i++) {
			html += this.reviewTemplate(data[i]);
		}
		$(".list_short_review").append(html);
		this.isGet = false;
	}

	

	window.reservation = window.reservation || {};
	window.reservation.Review = Review;


})(jQuery, Handlebars);
