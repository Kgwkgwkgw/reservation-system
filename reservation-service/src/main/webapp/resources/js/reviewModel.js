define("reviewModel", function() {
    "use strict";

    return eg.Class.extend(eg.Component, {
        construct : function(objQuery) {
            this.initVariable(objQuery);
        },

        initVariable : function(objQuery) {
            this.APIURL = "/api/reviews";
            this.objQuery = objQuery
            this.isRequesting = false;

        },

        upOffset : function() {
            this.objQuery.offset += this.objQuery.size;
        },

        reqCommentList :  function() {
            if(!this.isRequesting) {
                this.isRequesting = true;
                $.ajax({
                    url: this.APIURL,
                    data: this.objQuery
                }).done(this.reqCommentListCallback.bind(this));
            }
        },

        reqCommentListCallback : function(res) {
            this.upOffset();
            this.trigger("listArrive", res);
            this.isRequesting = false;
        }

    });
});
