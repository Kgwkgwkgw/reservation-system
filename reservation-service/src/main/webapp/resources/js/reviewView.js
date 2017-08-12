define("reviewView", ["Handlebars"], function(Handlebars) {
    "use strict";

    function ReviewView() {
        this.initTemplate();
    }

    ReviewView.prototype.initTemplate = function() {
        this.reviewTemplate =  Handlebars.compile($("#review-template").html());
        Handlebars.registerHelper("roundUpToFirstPoint", function (score) {
            return score.toFixed(1);
        });
    }

    return ReviewView;
});
