define("productModel", function() {
    return eg.Class.extend(eg.Component, {

        construct : function(objQuery) {
            this.initVariable(objQuery);
        },

        initVariable : function(objQuery) {
            this.APIURL = "/api/products";
    		this.COUNTURL = "/count";
            this.objQuery = objQuery || this.defaultObjQuery;

        },

        defaultObjQuery : {
            categoryId : "",
            offset : 0,
            size : 10
        },

        setCategory : function(categoryId) {
            this.objQuery.categoryId = categoryId;
        },

        resetLimit : function() {
    			this.objQuery.offset = 0;
    	},

        upOffset : function() {
    			this.objQuery.offset += this.objQuery.size;
    	},

        reqGetList : function() {
            $.ajax({
    			method: "get",
    			url : this.APIURL,
    			data : $.param(this.objQuery)
    		}).done(this.reqGetListCallback.bind(this));
        },

        reqGetCount : function() {
    		$.ajax({
    			method: "get",
    			url : this.APIURL + this.COUNTURL,
    			data : $.param(this.objQuery)
    		}).done(this.reqGetCountCallback.bind(this));
    	},

        getMoreList : function() {
    		this.upOffset();
    		this.reqGetList();
    	},

        reqGetListCallback : function(res) {
            this.trigger("listArrive", res);
        },

        reqGetCountCallback : function(res) {
            this.trigger("countArrive", res);
        }
    });

});
