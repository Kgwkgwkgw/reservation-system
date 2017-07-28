(function ($ , Handlebars) {
	"use strict";
  	window.reservation = window.reservation || {};
	window.reservation.Myreservation = (function(){
		var APIURL = "/users/reservation";
		var CANCELURL = "/cancellation";

		var $all;
		var $schdule;
		var $completion;
		var $cancellationAndRefund;
		var $listCards;
		var $emptyBox;
		var $btnFilter;
		var $btnAll;
		var $btnSchedule;
		var $btnCompletion;
		var $btnCancellationAndRefund;
		var btnRemoveCancellation;
		var $btnfilterStatus;
		var btnReservationCancel;
		var reservationItem;
		var $cancellationLayerPopup;
		var popupCancel;
		var popupConfirm;
		var popupClose;

		var reservationAskingListTemplate;
		var reservationConfirmListTemplate;
		var reservationCompletionListTemplate;
		var reservationCancellationAndRefundTemplate;
		var cancellationPopupTemplate;
		var objType;

		function initVariable(objReservationType) {
			$all = $("._all");
			$schdule = $("._schedule");
			$completion = $("._completion");
			$cancellationAndRefund = $("._cancellationAndRefund");
			$listCards = $(".list_cards");
			$emptyBox = $("._emptyBox");
			$btnFilter = $("._btnFilter");
			$btnAll = $("._btnAll");
			$btnSchedule = $("._btnSchedule");
			$btnCompletion = $("._btnCompletion");
			$btnCancellationAndRefund = $("._btnCancellationAndRefund");
			btnRemoveCancellation = "._btnRemoveCancellation";
			$cancellationLayerPopup = $("._cancellationLayerPopup");
			$btnfilterStatus = $all;
			popupCancel = "._cancel";
			popupConfirm = "._confirm";
			popupClose = "._close"
			btnReservationCancel = "._btnReservationCancel";
			reservationItem = "._reservationItem";
			objType = objReservationType;


			reservationAskingListTemplate = Handlebars.compile($("#reservationList-asking-template").html());
			reservationConfirmListTemplate = Handlebars.compile($("#reservationList-confirm-template").html());
			reservationCompletionListTemplate = Handlebars.compile($("#reservationList-completion-template").html());
			reservationCancellationAndRefundTemplate = Handlebars.compile($("#reservationList-cancellationAndRefund-template").html());
			cancellationPopupTemplate = Handlebars.compile($("#cancellationPopup-template").html());
	  	}

		function reqRemoveCancellation() {
			$.ajax({
			  method : "delete",
			  url : APIURL+"/"+objType.CANCELLATION
			}).done(refreshCallback)
		}

		function reqCancelReservation(id) {
			$.ajax({
			  method : "put",
			  url : APIURL+CANCELURL+"/"+id
			}).done(refreshCallback)
		}

		function refreshCallback() {
			$btnfilterStatus.trigger("click");
		}

		function reqReservationList(type) {
			type = type || "";
			$.ajax({
			  url  : APIURL,
			  data : {
			    "type" : type
			  }
			}).done(makeReservationList);
		}

		function makeReservationList(res) {
			var arrAsking = [];
			var arrConfirm = [];
			var arrCompletion = [];
			var arrCancelledAndRefund = [];

			if(res.length !==0) {
				for(var i =0; i<res.length; i++) {
					var type = res[i].reservationType;
					if(type===objType.ASKING )
						arrAsking.push(res[i]);
					else if(type===objType.CONFIRM)
						arrConfirm.push(res[i]);
					else if(type===objType.COMPLETION)
						arrCompletion.push(res[i]);
					else if(type===objType.REFUND || type === objType.CANCELLATION)
						arrCancelledAndRefund.push(res[i]);
				}

				var temp= "";
				if(arrAsking.length > 0)
					temp += reservationAskingListTemplate({"reservationLists":arrAsking});
				if(arrConfirm.length > 0)
					temp += reservationConfirmListTemplate({"reservationLists":arrConfirm});
				if(arrCompletion.length > 0 )
				  	temp += reservationCompletionListTemplate({"reservationLists":arrCompletion})
				if(arrCancelledAndRefund.length > 0)
				  	temp += reservationCancellationAndRefundTemplate({"reservationLists":arrCancelledAndRefund})
				if(temp !=="")
				  	renderReservationList(temp);
				else
				  	$emptyBox.removeClass("hide");
			}

		}

		function renderReservationList(strElem) {
			$listCards.append(strElem);
			$emptyBox.addClass("hide");
		}

		function reqReservationCount() {
			$.ajax({
			  url : APIURL+"/count"
			}).done(function(res) {
			    $all.text(res.total);
			    $schdule.text(res.schedule);
			    $completion.text(res.completion);
			    $cancellationAndRefund.text(res.cancellationAndRefund);
			});
		}

		function toggleFilterBtn(e) {
			e.preventDefault();
			$listCards.empty();
			$btnFilter.removeClass("on");
			$(this).addClass("on");
			$btnfilterStatus = $(this);
		}

		function showCancellationPopup(e) {
			var $item = $(this).parents(reservationItem);
			var strElem = cancellationPopupTemplate(
							  {
								"id" : $item.data("id"),
								"name": $item.data("name"),
								"reservationDate" : $item.data("reservationDate")
							  }
					  	  );

			$cancellationLayerPopup.html(strElem);
			$cancellationLayerPopup.removeClass("hide");
		}

		function hideCancellationPopup(e) {
			e.preventDefault();
			$cancellationLayerPopup.addClass("hide");
		}

		function addEventHandling() {

		    $btnAll.on("click", function(e) {
					toggleFilterBtn.call(this, e);
		      reqReservationList();
		      reqReservationCount();
		    });

		    $btnSchedule.on("click", function(e) {
		      toggleFilterBtn.call(this, e);
		      reqReservationList(objType.ASKING);
		      reqReservationList(objType.CONFIRM);
		      reqReservationCount();
		    });

		    $btnCompletion.on("click", function(e) {
		      toggleFilterBtn.call(this, e);
		      reqReservationList(objType.COMPLETION);
		      reqReservationCount();
		    });

		    $btnCancellationAndRefund.on("click", function(e) {
		      toggleFilterBtn.call(this, e);
		      reqReservationList(objType.CANCELLATION);
		      reqReservationList(objType.REFUND);
		      reqReservationCount();
		    });

		    $listCards.on("click", btnReservationCancel, showCancellationPopup);
		    $listCards.on("click", btnRemoveCancellation, reqRemoveCancellation);
			$cancellationLayerPopup.on("click", popupCancel, hideCancellationPopup);
			$cancellationLayerPopup.on("click", popupClose, hideCancellationPopup);
			$cancellationLayerPopup.on("click", popupConfirm, cancelReservationHandling);
	  	}

		function cancelReservationHandling(e) {
			hideCancellationPopup(e);
			reqCancelReservation($(this).data("id"));
		}

		function init(objReservationType) {
			initVariable(objReservationType);
			addEventHandling();

			reqReservationList();
			reqReservationCount();
	  	}

		return {
			init : init
		}

	})();

})(jQuery, Handlebars);
