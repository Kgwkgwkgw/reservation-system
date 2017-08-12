define("rolling", [], function() {
		"use strict";

		return eg.Class.extend(eg.Component, {

			construct : function(parentSelector, userOption) {
				this.initVariable(parentSelector, userOption);
				this.addEventHandler();
			},

			defaultOption : {
				autoSlide : true,
				autoSlideSecond : 2000,
				circular : true,
				prevBtn : "",
				nextBtn : "",
				isTouch : false
			},

			touchStartListener : function(e) {
				this.startX = event.changedTouches[0].pageX;
			},

			touchEndListener : function(e) {
				var endX = event.changedTouches[0].pageX;

				var diff = this.startX - endX;
				if(diff>0) {
					this.sliding();
				}
				if(diff<= 0) {
					this.slidingBack();
				}

				this.resetAutoSlidingTimer();
			},

			setOption : function(userOption) {
				$.each(this.defaultOption, function(index, value) {
					if(!userOption.hasOwnProperty(index))
						userOption[index]=value;
				});
				return userOption;
			},

			initVariable : function(parentSelector, userOption) {
				this.$parentSelector = $(parentSelector);
				this.$parentSelector.css("will-change", "transform");
				this.$parentSelector.css("transition", "all, 1s");
				this.rollingSelector = "._item";
				this.rollingWidth= parseInt($(this.rollingSelector).outerWidth());
				this.option = userOption ? this.setOption(userOption) : this.defaultOption;
				this.size = $(parentSelector + ">" + this.rollingSelector).length;
				this.currentX = 0;
				this.minX = parseInt(this.rollingWidth * (this.size - 1 )* -1);
				this.intervalId;
				this.startX;
				this.currentNum = 1;
				this.isMoving = false;
			},

			addEventHandler : function() {
				if(this.option.autoSlide)
					this.autoSliding();
				if(this.option.prevBtn)
					$(this.option.prevBtn).on("click", this.prevBtnListener.bind(this) );
				if(this.option.nextBtn)
					$(this.option.nextBtn).on("click", this.nextBtnListener.bind(this) );
				if(this.option.isTouch) {
					this.$parentSelector.on("touchstart", this.touchStartListener.bind(this))
					this.$parentSelector.on("touchend", this.touchEndListener.bind(this))
				}

				this.$parentSelector.on("webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend",
					function(e) {
						this.isMoving = false;
					}.bind(this));
			},

			prevBtnListener : function(e) {
				e.preventDefault();
				if(this.checkCanPress()) {
					this.slidingBack();
					this.resetAutoSlidingTimer();
				}
			},

			nextBtnListener : function(e) {
				e.preventDefault();
				if(this.checkCanPress()) {
					this.sliding();
					this.resetAutoSlidingTimer();
				}
			},

			resetInterval : function() {
				clearInterval(this.intervalId);
				this.intervalId = 0;
			},

			resetAutoSlidingTimer : function() {
				this.resetInterval();
				setTimeout(this.autoSliding.bind(this), 2000);
			},

			checkCanPress : function() {
				return !this.isMoving;
			},

			checkCanSlide : function() {
				return this.currentX > this.minX;
			},

			autoSliding : function() {
				if(this.intervalId)
					return;
				if(this.option.autoSlide) {
					this.intervalId = setInterval(
						this.sliding.bind(this), this.option.autoSlideSecond);
				};
			},

			sliding : function() {
				if(this.checkCanSlide()) {
					this.currentX -= this.rollingWidth;
					this.moving(this.currentX);
					this.currentNum++;
					this.slidingTrigger();
				} else {
					if(this.option.circular) {
						this.currentX = 0;

						this.moving(this.currentX);
						this.currentNum = 1;
						this.slidingTrigger();
					}
					else
						this.resetInterval();
				}
			},

			slidingBack : function() {
				if (this.currentX !== 0) {
					this.currentX += this.rollingWidth;

					this.moving(this.currentX);
					this.currentNum--;
					this.slidingTrigger();
				}
			},

			moving : function(intX) {
				this.isMoving = true;
				this.$parentSelector.css({"transform" : "translate3d("+intX+"px, 0px, 10px)"});
			},

			slidingTrigger : function() {
				this.trigger("sliding", { currentNum : this.currentNum, size: this.size });
			}

		});

});
