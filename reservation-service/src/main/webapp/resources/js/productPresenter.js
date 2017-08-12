define("productPresenter", ["productView"], function(ProductView) {
	"use strict";

	function ProductPresenter(productModel) {
		this.initVariable(productModel);
		this.addEventHandler();
		this.addTriggerHandler();

		this.productModel.reqGetList();
		this.productModel.reqGetCount();
	}

	ProductPresenter.prototype.initVariable = function (productModel) {
		this.$categoryList = $("._option_lst");
		this.$categoryCount = $("._categoryCount");
		this.$categoryItems = this.$categoryList.find("._item");
		this.$productListBefore = $("#productListBefore");
		this.$productListRear = $("#productListRear");
		this.$top = $("._top");

		this.productModel = productModel;

		this.productView = new ProductView();
	}

	ProductPresenter.prototype.addEventHandler = function() {
		this.$categoryList.on("click", "._item", this.categoryClickListener.bind(this));
      	$(window).on("scroll", this.scrollListener.bind(this));
	}

	ProductPresenter.prototype.addTriggerHandler = function() {
		this.productModel.on("listArrive", this.appendToList.bind(this));
		this.productModel.on("countArrive", this.editViewCount.bind(this));
	}

	ProductPresenter.prototype.categoryClickListener = function(e) {
		e.preventDefault();
		this.productModel.resetLimit();
		this.resetList();

		var $clickedItem = $(e.currentTarget);
		this.productModel.setCategory($clickedItem.data("category-id"));
		this.toggleCategoryView($clickedItem);

		this.productModel.reqGetList();
		this.productModel.reqGetCount();
	}

	ProductPresenter.prototype.scrollListener = function(e) {
		if (this.$top.offset().top < $(window).scrollTop() + $(window).height() + 100) {
			this.productModel.getMoreList(this.appendToList.bind(this));
		}
	}

	ProductPresenter.prototype.toggleCategoryView = function($clickedItem) {
		this.$categoryItems.removeClass("active");
		$clickedItem.addClass("active");
	}

	ProductPresenter.prototype.resetList = function() {
		this.$productListBefore.empty();
		this.$productListRear.empty();
	}

	ProductPresenter.prototype.editViewCount = function(res) {
		this.$categoryCount.html(res+"개");
	}

	ProductPresenter.prototype.appendToList = function(res) {
		var half = Math.ceil(res.length/2);
		var list="";
		for(var i = 0; i < half; i++) {
			list += this.productView.productListTemplate(res[i]);
		};
		this.$productListBefore.append(list);

		list="";
		for(var i = half; i< res.length; i++) {
			list +=this.productView.productListTemplate(res[i]);
		}
		this.$productListRear.append(list);
	}

	return ProductPresenter;

});
