define("reviewWritePresenter", ["Handlebars", "rating"], function(Handlebars, Rating) {
	"use strict";

	function ReviewWritePresenter(fileModel) {
		this.initVariable(fileModel);
		this.addEventHandling();
		this.addTriggerHandler();
	}

	ReviewWritePresenter.prototype.initVariable = function(fileModel) {
		this.THUMB_MAX_COUNT = 5;
		this.MIN_COMMENT_LENGTH = 5;
		this.MAX_COMMENT_LENGTH = 400;

		this.$commentLabel = $("#commentLabel");
		this.$commentText = $("#comment");
		this.$imageInput = $("#reviewImageInput");
		this.$thumbnailUploadList = $("._thumbnailUploadList");
		this.thumbnailUploadCount = 0;
		this.$numOfText = $("._numOfText");
		this.$score = $("._score");

		this.regCheckNoWhitespace = /.*\S+.*/;

		this.commentThumbnailTemplate = Handlebars.compile($("#commentWrite-thumbnail-template").html());

		this.rating = new Rating("._rating");
		this.fileModel = fileModel;
	}

	ReviewWritePresenter.prototype.addEventHandling = function() {
		$("._enrollForm").on("submit", this.submitCommentHandling.bind(this));
		$("._uploadWrapper").on("click", ".anchor", this.thumbnailDeleteHandling.bind(this));

		this.$commentLabel.on("click", function(e){
			$(this).addClass("hide");
		});

		this.$commentText.on("focusout", this.commentFocusOutHandling.bind(this));
		this.$commentText.on("keyup", this.keyUpHandling.bind(this));
		this.$imageInput.on("change", this.imageInputChangeHandling.bind(this));
	}

	ReviewWritePresenter.prototype.submitCommentHandling = function(e){
		var strEnteredComment = this.$commentText.val();

		if(this.isBlank(strEnteredComment)){
			alert("공백만 입력하면 안돼요");
			return false;
		}
		if(strEnteredComment.length<=this.MIN_COMMENT_LENGTH){
			alert("최소 "+this.MIN_COMMENT_LENGTH+"자 이상 리뷰남겨주세요");
			return false;
		}
		return true;
	}

	ReviewWritePresenter.prototype.thumbnailDeleteHandling = function(e) {
		e.preventDefault();
		var $thumbDeleteBtn = $(e.currentTarget);
		var $thumbItem = $thumbDeleteBtn.closest("._thumb");
		var fileId = $thumbItem.data("id");

		this.fileModel.reqDeleteImage($thumbItem, fileId);
	}

	ReviewWritePresenter.prototype.commentFocusOutHandling = function(e) {
		var strEnteredComment = this.$commentText.val();
		if(this.isBlank(strEnteredComment)){
			this.$commentLabel.removeClass("hide");
		}
	}

	ReviewWritePresenter.prototype.keyUpHandling = function() {
		var strEnteredComment = this.$commentText.val();

		if(strEnteredComment.length > this.MAX_COMMENT_LENGTH){
			alert(this.MAX_COMMENT_LENGTH+"자 초과");
			strEnteredComment = strEnteredComment.substring(0, this.MAX_COMMENT_LENGTH);
			this.$commentText.val(strEnteredComment);
		}
		this.$numOfText.text(strEnteredComment.length);
	}

	ReviewWritePresenter.prototype.imageInputChangeHandling = function(e){
		var currentCount = this.thumbnailUploadCount + this.$imageInput[0].files.length;

		if(currentCount>this.THUMB_MAX_COUNT) {
			alert("이미지 등록은 최대"+this.THUMB_MAX_COUNT+"개 입니다.");
			return false;
		} else {
			var formData = new FormData();
			$.each(this.$imageInput[0].files, function(i, file) {
				formData.append("images", file);
			});

			this.fileModel.reqCreateImages(formData);
		}
	}

	ReviewWritePresenter.prototype.addTriggerHandler = function() {
		this.rating.on("change", this.ratingChangeCallback.bind(this));
		this.fileModel.on("createImage", this.createImagesDoneHandling.bind(this));
		this.fileModel.on("createImageAlways", this.createImagesAlwaysHandling.bind(this));
		this.fileModel.on("ajaxFail", this.ajaxFailHandling.bind(this));
		this.fileModel.on("deleteImage", this.deleteImageDoneHandling.bind(this));
	}

	ReviewWritePresenter.prototype.ratingChangeCallback = function(res) {
		this.$score.text(res.score);
		this.$score.removeClass("gray_star");
	}

	ReviewWritePresenter.prototype.createImagesDoneHandling = function(res, textStatus, xhr) {
		this.thumbnailUploadCount += res.length;
		this.renderCommentThumbnail(res);
	}

	ReviewWritePresenter.prototype.renderCommentThumbnail = function(res) {
		var strCommentThumbnailTemplate = "";
		for(var i=0; i<res.length; i++){
			strCommentThumbnailTemplate += this.commentThumbnailTemplate(res[i]);
		}
		this.$thumbnailUploadList.append(strCommentThumbnailTemplate);
	}

	ReviewWritePresenter.prototype.createImagesAlwaysHandling = function() {
		this.$imageInput.val("");
	}

	ReviewWritePresenter.prototype.ajaxFailHandling = function(jqXHR){
		alert(jqXHR.responseJSON.message);
	}

	ReviewWritePresenter.prototype.deleteImageDoneHandling = function($thumbItem) {
		this.thumbnailUploadCount--;
		$thumbItem.remove();
	}

	ReviewWritePresenter.prototype.isBlank = function(str) {
		return !this.regCheckNoWhitespace.test(str);
	}

	return ReviewWritePresenter;
});
