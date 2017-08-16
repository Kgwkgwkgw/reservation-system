define("bookingPresenter", ["ticket", "Handlebars"], function(Ticket, Handlebars) {
    "use strict";
    function BookingPresenter() {
        this.initVariable();
        this.addEventHandler();
        this.addTriggerHandler();
    }

    BookingPresenter.prototype.initVariable = function() {
        this.sum = 0;
        this.$sum = $("._sum");
        this.sumPrice = 0;
        this.$sumPrice = $("._sumPrice");
        this.$bookingBtn = $("._bookingBtn");
        this.$agreeCheckBox = $("._agree");
        this.isAgree = false;
        this.$tel = $("#tel");
        this.$email = $("#email");
        this.$requiredInputs = $("._required");

        this.priceTypeCountTemplate = Handlebars.compile($("#price-type-count-template").html());
        this.$priceTypeCountTemplateTarget = $("._priceTypeCountTemplateTarget");

        this.regTel = /^\d{2,3}-\d{3,4}-\d{4}$/;
        this.regEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

        this.arrTicket = [];
        $("._priceTypeArea").each(function(index, element) {
            var ticket = new Ticket("._priceTypeArea:nth-child("+(++index)+")");
            this.arrTicket.push(ticket);
        }.bind(this));
    }

    BookingPresenter.prototype.addEventHandler = function() {
        this.$requiredInputs.on("keyup", this.checkEssentialValidation.bind(this, false));
        this.$agreeCheckBox.on("change", this.clickAgreeCheckBoxHandling.bind(this));
        $("#form").on("submit", this.submitHandling.bind(this));

        $(".btn_agreement").on("click", function(e) {
            e.preventDefault();
            var $this = $(this);
            var isOpened = $this.closest(".agreement").hasClass("open");
            $this.closest(".agreement").toggleClass("open", !isOpened);
            $this.find("._arw").toggleClass("fn-down2", isOpened)
                               .toggleClass("fn-up2", !isOpened);
        });
    }

    BookingPresenter.prototype.addTriggerHandler = function() {
        this.arrTicket.forEach(function(ticket) {
            ticket.on("change", this.changeTicketHandler.bind(this));
        }.bind(this));
    }

    BookingPresenter.prototype.changeTicketHandler = function() {
        var arrViewObj = [];
        var sum = 0;
        var sumPrice = 0;

        this.arrTicket.forEach(function(ticket) {
            var amount = ticket.getAmount();

            sum += amount;
            sumPrice += ticket.getPrice();

            if(amount > 0) {
                arrViewObj.push({
                    priceTypeName : ticket.getPriceTypeName(),
                    amount : amount });
            }
        });

        this.$priceTypeCountTemplateTarget.html(this.priceTypeCountTemplate(arrViewObj))
        this.sum = sum;
        this.$sum.text(sum);
        this.$sumPrice.text(sumPrice.toLocaleString());
        this.checkEssentialValidation(false);
    }

    BookingPresenter.prototype.submitHandling = function(e) {
        if(!this.checkEssentialValidation(true)) {
            return false;
        }
        if (this.$email.val()!=="") {
            return this.checkValidEmail();
        }
            return true;
    }

    BookingPresenter.prototype.isBlank = function() {
        this.$requiredInputs.each(function(index, element) {
            var input = $(this).val().trim();
            if(input.length===0) {
                return true;
            }
        })
        return false;
    }

    BookingPresenter.prototype.isValidTel = function() {
        return this.regTel.test(this.$tel.val());
    }

    BookingPresenter.prototype.checkValidEmail = function(strEmail) {
        var valid = this.regEmail.test(this.$email.val());

        if(!valid) {
            alert("이메일을 확인해주세요.");
        }

        return valid;
    }

    BookingPresenter.prototype.checkEssentialValidation = function(isShowMessage) {
        this.$bookingBtn.addClass("disable");

        var msg = "";
        var valid = true;

        if(this.sum <= 0) {
            msg = "1개이상 예약부탁드립니다.";
            valid = false;
        }

        if(this.isBlank()) {
            msg = "필수입력사항을 입력해주세요.";
            valid = false;
        }

        if(!this.isValidTel()) {
            msg = "전화번호를 확인해주세요. 예) 010-2434-3434";
            valid = false;

        }
        
        if(!this.isAgree) {
            msg = "이용약관을 동의해주세요~";
            valid = false;
        }

        if (isShowMessage &&  msg !== "") {
            alert(msg);
            return false;
        }

        if(valid) {
            this.$bookingBtn.removeClass("disable");
        }

        return valid;
    }

    BookingPresenter.prototype.clickAgreeCheckBoxHandling = function(e) {
        this.isAgree = !this.isAgree;
        this.checkEssentialValidation(true);
    }

    return BookingPresenter;
});
