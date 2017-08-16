define("fileModel", function() {
    return eg.Class.extend(eg.Component, {
        construct : function() {
            this.initVariable();
        },

        initVariable : function() {
            this.API_IMG_URL = "/api/images";
        },

        reqCreateImages : function(formData) {
            $.ajax({
    			url : this.API_IMG_URL,
    			data : formData,
    			type : "POST",
    			contentType : false,
    			processData : false
    		}).done(this.reqCreateImagesDoneCallback.bind(this))
    		  .fail(this.ajaxFail.bind(this))
    		  .always(this.reqCreateImagesAlwaysCallback.bind(this));
        },

        reqCreateImagesDoneCallback : function(res) {
            this.trigger("createImage",res);
        },

        ajaxFail : function(jqXHR) {
            this.trigger("ajaxFail", jqXHR);
        },

        reqCreateImagesAlwaysCallback : function() {
            this.trigger("createImageAlways");
        },

        reqDeleteImage : function($thumbItem, fileId) {
            $.ajax({
    			url : this.API_IMG_URL+"/"+fileId,
    			type : "DELETE"
    		}).done(this.reqDeleteImageDone.bind(this, $thumbItem))
              .fail(this.ajaxFail.bind(this));
        },

        reqDeleteImageDone : function($thumbItem) {
            this.trigger("deleteImage", $thumbItem);
        }

    })
});
