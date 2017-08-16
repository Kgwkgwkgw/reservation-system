define("myReservationPresenter", ["Handlebars"], function(Handlebars) {
	"use strict";
	function MyReservationPresenter(myReservationModel, objReservationType) {
		this.initVariable(myReservationModel, objReservationType);
		this.addEventHandling();
		this.addTriggerHandling();

		this.myReservationModel.reqReservationList();
		this.myReservationModel.reqReservationCount();
	}

	MyReservationPresenter.prototype.initVariable = function(myReservationModel, objReservationType) {
		this.$myReservationList = $("._myReservationList");
		this.$emptyList = $("._emptyList");

		this.$btnFilters = $("._btnFilter");
		this.$btnAll = $("._btnAll");
		this.$btnSchedule = $("._btnSchedule");
		this.$btnCompletion = $("._btnCompletion");
		this.$btnCancellationAndRefund = $("._btnCancellationAndRefund");

		this.$allCount = $("._all");
		this.$schduleCount = $("._schedule");
		this.$completionCount = $("._completion");
		this.$cancellationAndRefundCount = $("._cancellationAndRefund");

		this.$cancellationLayerPopup = $("._cancellationLayerPopup");

		this.objReservationType = objReservationType;
		this.myReservationModel = myReservationModel;

		this.reservationAskingListTemplate = Handlebars.compile($("#reservationList-asking-template").html());
		this.reservationConfirmListTemplate = Handlebars.compile($("#reservationList-confirm-template").html());
		this.reservationCompletionListTemplate = Handlebars.compile($("#reservationList-completion-template").html());
		this.reservationCancellationAndRefundTemplate = Handlebars.compile($("#reservationList-cancellationAndRefund-template").html());
		this.cancellationPopupTemplate = Handlebars.compile($("#cancellationPopup-template").html());

		this.fnCache = this.getAllReservation;
	}

	MyReservationPresenter.prototype.addEventHandling = function() {
		this.$btnAll.on("click", function(e) {
			this.toggleFilterBtn.call(this, e);
			this.fnCache = this.getAllReservation;
			this.getAllReservation();
		}.bind(this));

		this.$btnSchedule.on("click", function(e) {
			this.toggleFilterBtn.call(this, e);
			this.fnCache = this.getScheduledReservation;
			this.getScheduledReservation();
			}.bind(this));

		this.$btnCompletion.on("click", function(e) {
			this.toggleFilterBtn.call(this, e);
			this.fnCache = this.getCompletedReservation;
			this.getCompletedReservation();
			}.bind(this));

		this.$btnCancellationAndRefund.on("click", function(e) {
		  this.toggleFilterBtn.call(this, e);
		  this.fnCache = this.getCancellatedAndRefundedReservation;
		  this.getCancellatedAndRefundedReservation();
			}.bind(this));

		this.$myReservationList.on("click", "._btnReservationCancel", this.clickCancellationBtnHandling.bind(this));
		this.$myReservationList.on("click", "._btnRemoveCancellation", this.removeCancellationHandling.bind(this));

		this.$cancellationLayerPopup.on("click", "._cancel", this.hideCancellationPopup.bind(this));
		this.$cancellationLayerPopup.on("click", "._close", this.hideCancellationPopup.bind(this));
		this.$cancellationLayerPopup.on("click", "._confirm", this.confirmCancellationHandling.bind(this));
  	}

	MyReservationPresenter.prototype.addTriggerHandling = function() {
		this.myReservationModel.on("listArrive", this.makeReservationList.bind(this));
		this.myReservationModel.on("countArrive", this.renderReservationCount.bind(this));
	}

	MyReservationPresenter.prototype.getAllReservation = function() {
		this.removeMyReservationList();
		this.myReservationModel.reqReservationList();
		this.myReservationModel.reqReservationCount();
	}

	MyReservationPresenter.prototype.getScheduledReservation = function() {
		this.removeMyReservationList();
		this.myReservationModel.reqReservationList(this.objReservationType.ASKING);
		this.myReservationModel.reqReservationList(this.objReservationType.CONFIRM);
		this.myReservationModel.reqReservationCount();
	}

	MyReservationPresenter.prototype.getCompletedReservation = function() {
		this.removeMyReservationList();
		this.myReservationModel.reqReservationList(this.objReservationType.COMPLETION);
		this.myReservationModel.reqReservationCount();
	}

	MyReservationPresenter.prototype.getCancellatedAndRefundedReservation = function() {
		this.removeMyReservationList();
		this.myReservationModel.reqReservationList(this.objReservationType.CANCELLATION);
		this.myReservationModel.reqReservationList(this.objReservationType.REFUND);
		this.myReservationModel.reqReservationCount();
	}

	MyReservationPresenter.prototype.removeMyReservationList = function() {
		this.$myReservationList.empty();
		this.$emptyList.removeClass("hide");
	}

	MyReservationPresenter.prototype.makeReservationList = function(res) {
		var arrAsking = [];
		var arrConfirm = [];
		var arrCompletion = [];
		var arrCancelledAndRefund = [];
		var strListElem = "";

		if(res.length !==0) {
			for(var i =0; i<res.length; i++) {
				var type = res[i].reservationType;

				if(type=== this.objReservationType.ASKING) {
					arrAsking.push(res[i]);
				}
				else if(type===this.objReservationType.CONFIRM) {
					arrConfirm.push(res[i]);
				}
				else if(type===this.objReservationType.COMPLETION) {
					arrCompletion.push(res[i]);
				}
				else if(type===this.objReservationType.REFUND || type === this.objReservationType.CANCELLATION) {
					arrCancelledAndRefund.push(res[i]);
				}
			}
			if(arrAsking.length > 0)
				strListElem += this.reservationAskingListTemplate({"reservationLists":arrAsking});
			if(arrConfirm.length > 0)
				strListElem += this.reservationConfirmListTemplate({"reservationLists":arrConfirm});
			if(arrCompletion.length > 0 )
			  	strListElem += this.reservationCompletionListTemplate({"reservationLists":arrCompletion})
			if(arrCancelledAndRefund.length > 0)
			  	strListElem += this.reservationCancellationAndRefundTemplate({"reservationLists":arrCancelledAndRefund})
		}
		this.renderReservationList(strListElem);
	}

	MyReservationPresenter.prototype.renderReservationList = function(strListElem) {
		if(strListElem !== "") {
			this.$emptyList.addClass("hide");
			this.$myReservationList.append(strListElem);
		}
	}

	MyReservationPresenter.prototype.renderReservationCount = function(res) {
		this.$allCount.text(res.total);
		this.$schduleCount.text(res.schedule);
		this.$completionCount.text(res.completion);
		this.$cancellationAndRefundCount.text(res.cancellationAndRefund);
	}

	MyReservationPresenter.prototype.toggleFilterBtn = function(e) {
		e.preventDefault();
		this.$btnFilters.removeClass("on");
		$(e.currentTarget).addClass("on");
	}

	MyReservationPresenter.prototype.clickCancellationBtnHandling = function(e) {
		var $item = $(e.currentTarget).closest("._reservationItem");
		var strElem = this.cancellationPopupTemplate({
			"id" : $item.data("id"),
			"name": $item.data("name"),
			"reservationDate" : $item.data("reservationDate")});

		this.renderCancellationPopup(strElem)
	}

	MyReservationPresenter.prototype.renderCancellationPopup = function(strElem) {
		this.$cancellationLayerPopup.html(strElem);
		this.$cancellationLayerPopup.removeClass("hide");
	}

	MyReservationPresenter.prototype.hideCancellationPopup = function(e) {
		e.preventDefault();
		this.$cancellationLayerPopup.addClass("hide");
	}

	MyReservationPresenter.prototype.confirmCancellationHandling = function(e) {
		this.hideCancellationPopup(e);
		this.myReservationModel.reqCancelReservation($(e.currentTarget).data("id"), this.fnCache.bind(this));
	}

	MyReservationPresenter.prototype.removeCancellationHandling = function() {
		this.myReservationModel.reqRemoveCancellation(this.fnCache.bind(this));
	}

	return MyReservationPresenter;

});
