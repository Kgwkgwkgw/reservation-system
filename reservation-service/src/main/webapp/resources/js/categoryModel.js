define("categoryModel", function() {
    return eg.Class.extend(eg.Component, {

        construct : function(objQuery) {
            this.initVariable(objQuery);
        },

        initVariable : function() {
            this.APIURL = "/api/categories";
        },

        reqDestroy : function(id) {
            $.ajax({
                method: "delete",
                url : this.APIURL+"/"+id,
                headers: {
                    "Content-Type" :"application/json"}
                }).done(this.reqDestroyCallback.bind(this, id));
        },

        reqDestroyCallback : function(id, res) {
            this.trigger("destroy", id, res);
        },

        reqModify : function(id, strToBeName) {
            $.ajax({
              method: "put",
              url : this.APIURL+"/"+id,
              headers: {
                "Content-Type" :"application/json",
                },
              data : JSON.stringify({ name : strToBeName})
          }).done(this.reqModifyCallback.bind(this, id, strToBeName));
        },

        reqModifyCallback : function(id, strToBeName, res) {
            this.trigger("modify", id, strToBeName, res);
        }

    });
});
