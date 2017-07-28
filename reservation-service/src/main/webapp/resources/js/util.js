(function($) {
	"use strict";
	
	function Rating(wrapSelector,main){
	  this.$wrapSelector;
	  this.$btns;
	  this.main;
	  this.btnModifier;
	  this.score;

	  this.initVariable(wrapSelector,main);
	  this.addEventHandling();
	}
	Rating.prototype = new eg.Component(); //extends
	Rating.prototype.constructor = Rating;
	Rating.prototype.initVariable = function(wrapSelector,main) {
	  this.$wrapSelector = $(wrapSelector);
	  this.$btns = this.$wrapSelector.find("input[type='radio']");
	  this.$score = this.$wrapSelector.find("._score");
	  this.main = main;
	  this.btnModifier = "checked";
	  this.scoreModifier ="gray_star";
	  this.score = 0;
	}
	Rating.prototype.getScore = function() {
	  return this.score;
	}
	Rating.prototype.addEventHandling = function(){
	  this.main.on("change", this.changeCallback.bind(this));
	}
	Rating.prototype.changeCallback = function(val) {
	  this.score = val;
	  this.paintScore();
	}
	Rating.prototype.paintScore = function () {
	    this.$btns.removeClass(this.btnModifier);
	    for(var i=0; i< this.score; i++) {
	      this.$btns.eq(i).addClass(this.btnModifier);
	    }
	    this.$score.text(this.score);
	    this.$score.removeClass(this.scoreModifier);
	}

	window.reservation = window.reservation || {}; 
	window.reservation.Rating = Rating;
})(jQuery)
