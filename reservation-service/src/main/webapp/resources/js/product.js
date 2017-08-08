define("product", ["Handlebars", "rolling"], function(Handlebars, Rolling) {
	"use strict";

	function Product() {
		this.initVariable();
		this.addEventHandler();

		this.reqGetList();
		this.reqGetCount();
	}

	Product.prototype.initVariable = function () {
		this.APIURL = "/api/products";
		this.COUNTURL = "/count";
		this.item = "._item";
		this.productListSize = 10;
		this.objQuery = {
			categoryId : "",
			offset : 0,
			size : this.productListSize
		};

		this.$optionList = $("._option_lst");
		this.$categoryCount = $("._categoryCount");
		this.$optionItems = this.$optionList.find(this.item);
		this.$productListBefore = $("#productListBefore");
		this.$productListRear = $("#productListRear");

		this.listTemplate = Handlebars.compile($("#productList-template").html());

		this.rolling = new Rolling("._rolling",  {
            "prevBtn" :"._prev", "nextBtn" : "._next", "isTouch" : true});
	}

	Product.prototype.addEventHandler = function () {
		this.$optionList.on("click", this.item, this.categoryClickListener.bind(this));

		if (!$("body").height() < $(window).height()) {
          $(window).on("scroll", this.scrollListener.bind(this));
        }
	}

	Product.prototype.categoryClickListener = function(e) {
		e.preventDefault();
		this.resetLimit();
		this.resetList();

		var $clickedBtn = $(e.currentTarget);
		this.objQuery.categoryId = $clickedBtn.data("category-id");

		this.reqGetList();
		this.reqGetCount();

		this.toggleCategoryView($clickedBtn);
	}

	Product.prototype.toggleCategoryView = function($clickedBtn) {
		this.$optionItems.removeClass("active");
		$clickedBtn.addClass("active");
	}

	Product.prototype.getMoreList = function() {
		this.upLimit(this.productListSize);
		this.reqGetList();
	}

	Product.prototype.resetList = function() {
		this.$productListBefore.empty();
		this.$productListRear.empty();
	}

	Product.prototype.reqGetList = function() {
		$.ajax({
			method: "get",
			url : this.APIURL,
			data : $.param(this.objQuery)
		}).done(this.appendToList.bind(this));
	}

	Product.prototype.reqGetCount = function() {
		$.ajax({
			method: "get",
			url : this.APIURL+this.COUNTURL,
			data : $.param(this.objQuery)
		}).done(this.editViewCount.bind(this));
	}

	Product.prototype.editViewCount = function(res) {
		this.$categoryCount.html(res+"개");
	}

	Product.prototype.appendToList = function(res) {
		var half = Math.ceil(res.length/2);
		var list="";
		for(var i = 0; i < half; i++) {
			list += this.listTemplate(res[i]);
		};
		this.$productListBefore.append(list);

		list="";
		for(var i = half; i< res.length; i++) {
			list +=this.listTemplate(res[i]);
		}
		this.$productListRear.append(list);
	}

	Product.prototype.resetLimit = function() {
			this.objQuery.offset = 0;
	}

	Product.prototype.upLimit = function(size) {
			this.objQuery.offset += size;
	}

	Product.prototype.scrollListener = function(e) {
		if ($(window).scrollTop() === $(document).height() - $(window).height()) {
			this.getMoreList();
		}
	}

	return Product;

});
