define("myReservationModel", function() {
    return eg.Class.extend(eg.Component, {
        construct : function(objReservationType) {
            this.initVariable(objReservationType);
        },

        initVariable : function() {
            this.APIURL = "/api/myreservation";
        },

        reqReservationList : function(type) {
    		type = type || "";
    		$.ajax({
    		  url  : this.APIURL,
    		  data : {
    		    "type" : type
    		  }
          }).done(this.reqReservationListCallback.bind(this));
    	},

        reqReservationListCallback : function(res) {
            this.trigger("listArrive", res);
        },

        reqReservationCount : function() {
            $.ajax({
    		  url : this.APIURL+"/count"
          }).done(this.reqReservationCountCallback.bind(this));
        },

        reqReservationCountCallback : function(res) {
            this.trigger("countArrive", res);
        },

        reqRemoveCancellation : function(fnCallback) {
            $.ajax({
    		  method : "delete",
    		  url : this.APIURL+"/cancellation"
          }).done(fnCallback)
        },

        reqCancelReservation : function(id, fnCallback) {
            $.ajax({
    		  method : "put",
              url : this.APIURL+"/"+id+"/cancellation"
          }).done(fnCallback)
        }
    })
});
