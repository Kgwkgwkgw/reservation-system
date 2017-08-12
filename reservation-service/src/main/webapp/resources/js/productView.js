define("productView", ["Handlebars"], function(Handlebars) {
    "use strict";

    function ProductView() {
        this.initTemplate();
    }

    ProductView.prototype.initTemplate = function() {
        this.productListTemplate = Handlebars.compile($("#productList-template").html());
    }

    return ProductView;
});
