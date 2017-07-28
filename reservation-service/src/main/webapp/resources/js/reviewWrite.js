(function($){
	"use strict";

	function ReviewWrite() {
		this.APIURL;
		this.$enrollForm;
		this.$imageInput;
		this.$listThumbnail;
		this.$thumbnailDeleteBtn;
		this.$outerTextArea;
		this.$innerTextArea;
		this.$numOfText;
		this.$enrollBtn;
		this.scoreWrap;
		this.$scoreWrap;
		this.scoreRadioBtn;

		this.initVariable();
		this.addEventHandling();
	}

	ReviewWrite.prototype = new eg.Component();
	ReviewWrite.prototype.constructor = ReviewWrite;
	ReviewWrite.prototype.initVariable = function() {
		this.APIURL = "/reviews/images";
		this.$enrollForm = $("._enrollForm");
		this.$imageInput = $("#reviewImageFileOpenInput");
		this.$listThumbnail = $('.lst_thumb');
		this.$thumbnailDeleteBtn = $('.review_photos');
		this.$outerTextArea = $('.review_contents');
		this.$innerTextArea = $('.review_textarea');
		this.$numOfText = $('.guide_review').find('span').eq(0);
		this.$enrollBtn = $('.box_bk_btn').find('.bk_btn');
		this.scoreWrap = "._rating";
		this.$scoreWrap = $(this.scoreWrap);
		this.scoreRadioBtn = "._radio";
	}
	ReviewWrite.prototype.scoreClickHandling = function(e) {
	  this.trigger("change", $(e.currentTarget).val());
	}
	ReviewWrite.prototype.makeComment = function(commentData, fileIdList){
		$.ajax({
			url : this.APIURL+"/form",
			method : "POST",
			data : {commentData: commentData, fileIdList : fileIdList},

			error:function(){
				console.log("comment insert failed");
			}
		}).done(function (){
			console.log("success");
		});
	}
	ReviewWrite.prototype.deleteImageFile = function(fileId){
		$.ajax({
			url : this.APIURL+"/"+fileId,
			type : "DELETE",

			error:function(){
				console.log("delete failed");

			}
		}).done(function(res){
			console.log(res);
			console.log("delete success");
		});
	}
	ReviewWrite.prototype.uploadImageFiles = function(formData){
		$.ajax({
			url : this.APIURL,
			data : formData,
			type : "POST",
			
			contentType : false,
			processData : false,

		}).done(this.ajaxUploadDoneHandling.bind(this))
		  .fail(this.ajaxFailHandling.bind(this));
	}

	ReviewWrite.prototype.ajaxUploadDoneHandling = function(data, textStatus, xhr){
		console.log(data);
		// if(data===""){
		// 	alert("이미지 파일(jpeg/png)만 입력해주세요.");
		// }
		// else{
		var tmp = "";
		var commentThumbnailTemplate = Handlebars.compile($("#commentWrite-thumbnail-template").html());
		for(var i=0; i<data.length; i++){
			tmp += commentThumbnailTemplate(data[i]);
		}
		this.$listThumbnail.append(tmp);	
		// }
	}

	ReviewWrite.prototype.ajaxFailHandling = function(jqXHR){
		alert(jqXHR.responseJSON.message);
	}
	ReviewWrite.prototype.enrollValidationHandling = function(event){
		var check = this.validation();
		if(!check===true){
				event.preventDefault();
		}else{
				alert("올바른 양식입니다. ");
		}
	}
	ReviewWrite.prototype.validation = function(){
		console.log("")
		var textContent = this.$innerTextArea.val();
		var blankPattern = /^\s+|\s+$/g;
		if(textContent.length<=5){
			alert("최소 5자 이상 리뷰남겨주세요");
			return false;
		}
		if(textContent.replace(blankPattern,"")==""){
			alert('공백만 입력하면 안돼요');
			return false;
		}
		return true;
	}
	ReviewWrite.prototype.addEventHandling = function() {
		this.$scoreWrap.on("click", this.scoreRadioBtn, this.scoreClickHandling.bind(this));
		this.$enrollForm.on("submit", this.enrollValidationHandling.bind(this));

		this.$outerTextArea.on("click", function(){
			$('.review_write_info').hide();
			$(this).focus(this.$innerTextArea);
		});

		this.$innerTextArea.focusout(function(){
			var textContent = $(this).val();
			if(textContent===""||textContent===" "||textContent.lenght<5){
				$('.review_write_info').show();
			}
		})
		this.$innerTextArea.on("keyup", this.keyUpHandling.bind(this));
		this.$imageInput.on("change", this.changeHandling.bind(this));

		this.$thumbnailDeleteBtn.on("click", '.anchor', this.thumbnailDeleteHandling.bind(this));
	}
	ReviewWrite.prototype.thumbnailDeleteHandling = function(e) {
		var $deleteBtn = $(e.currentTarget);
		var id = $deleteBtn.data('thumb-id');
		$deleteBtn.closest('li').remove();
		this.deleteImageFile(id);
	}
	ReviewWrite.prototype.addImageCapacityHandling = function(currentCapacity){
		if(currentCapacity >5){
			alert("더이상 이미지를 등록할 수 없습니다.");
			return false;
		}
		return true;
	}
	ReviewWrite.prototype.changeHandling = function(e){
		var formData = new FormData();
		var incrementCapacity = $("#reviewImageFileOpenInput")[0].files.length;

		$.each($("#reviewImageFileOpenInput")[0].files, function(i, file) {
			formData.append("images", file);
		});
		if($("#reviewImageFileOpenInput")[0].files.length>5){
			alert("이미지 등록은 최대 5개 입니다.");
		}else{
			var currentCapacity = $('.lst_thumb').children().length + incrementCapacity;
			if(this.addImageCapacityHandling(currentCapacity)){
				this.uploadImageFiles(formData);
			}
		}
	}
	ReviewWrite.prototype.keyUpHandling = function() {
		var textContent = this.$innerTextArea.val();
		if(textContent.length > 400){
			alert("400자 초과");
			var limitText = textContent.substring(0,400);
			console.log(limitText.length);
			this.$innerTextArea.val(limitText);
			textContent = limitText;
		}
		this.$numOfText.text(textContent.length);
	}
	window.reservation = window.reservation || {};
	window.reservation.ReviewWrite = ReviewWrite;
		

})(jQuery);