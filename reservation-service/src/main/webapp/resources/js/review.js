(function($, Handlebars){
	"use strict";

	function Review() {
		this.APIURL;
		this.THUMB_MAX_COUNT;
		this.MAX_COMMENT_LENGTH;
		this.MIN_COMMENT_LENGTH;
		this.$enrollForm;
		this.$imageInput;
		this.thumbnailCount;
		this.$listThumbnail;
		this.thumbItem;
		this.$thumbnailDeleteBtn;
		this.$outerTextArea;
		this.$TextArea;
		this.$commentLabel;
		this.strComment;
		this.$numOfText;
		this.$enrollBtn;
		this.scoreWrap;
		this.$scoreWrap;
		this.scoreRadioBtn;
		this.regCheckNoWhitespace;

		this.commentThumbnailTemplate;

		this.initVariable();
		this.addEventHandling();
	}

	Review.prototype = new eg.Component();
	Review.prototype.constructor = Review;
	Review.prototype.initVariable = function() {
		this.APIURL = "/reviews/images";
		this.THUMB_MAX_COUNT = 5;
		this.MAX_COMMENT_LENGTH = 400;
		this.MIN_COMMENT_LENGTH = 5;
		this.$enrollForm = $("._enrollForm");
		this.$imageInput = $("#reviewImageFileOpenInput");
		this.thumbnailCount = 0;
		this.$listThumbnail = $(".lst_thumb");
		this.thumbItem = "._thumb";
		this.$thumbnailDeleteBtn = $(".review_photos");
		this.$outerTextArea = $(".review_contents");
		this.$TextArea = $("#comment");
		this.$commentLabel = $("#commentLabel");
		this.strComment = "";
		this.$numOfText = $(".guide_review").find("span").eq(0);
		this.$enrollBtn = $(".box_bk_btn").find(".bk_btn");
		this.scoreWrap = "._rating";
		this.$scoreWrap = $(this.scoreWrap);
		this.scoreRadioBtn = "._radio";
		this.regCheckNoWhitespace = /.*\S+.*/;
		this.commentThumbnailTemplate = Handlebars.compile($("#commentWrite-thumbnail-template").html());
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
	}

	Review.prototype.scoreClickHandling = function(e) {
	  this.trigger("change", $(e.currentTarget).val());
	}

	Review.prototype.reqCreateImageFiles = function(formData){
		$.ajax({
			url : this.APIURL,
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
			url : this.APIURL+"/"+fileId,
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
	window.reservation = window.reservation || {};
	window.reservation.Review = Review;


})(jQuery, Handlebars);
