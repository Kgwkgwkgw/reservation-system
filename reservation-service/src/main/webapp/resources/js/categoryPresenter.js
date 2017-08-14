define("categoryPresenter", [], function() {
    "use strict";

    function CategoryPresenter(categoryModel) {
        this.initVariable(categoryModel);
        this.addEventHandler();
        this.addTriggerHandler();
    }

    CategoryPresenter.prototype.initVariable = function(categoryModel) {
        this.$categoryList = $("._categoryList");
        this.CATEGORY_ITEM_SELECTOR = "._item";
        this.CATEGORY_NAME = "._name";
        this.CATEGORY_EDIT_INPUT = "._editInput";
        this.ENTER_KEY = 13;

        this.categoryModel = categoryModel;
    }

    CategoryPresenter.prototype.addEventHandler = function() {
        this.$categoryList.on("click", "._modify", this.modifyBtnClickHandler.bind(this))
                          .on("click", "._destroy", this.destroyBtnClickHandler.bind(this))
                          .on("keyup", this.CATEGORY_EDIT_INPUT, this.enterInputHandler.bind(this));

        $(document).ajaxError(function (event, xhr, ajaxOptions, thrownError) {
              alert("일시적 오류가 발생하였습니다.");
        });
    }

    CategoryPresenter.prototype.modifyBtnClickHandler = function(e) {
        var $item = $(e.currentTarget).closest(this.CATEGORY_ITEM_SELECTOR);
        this.modify($item);
    }

    CategoryPresenter.prototype.modify = function($item) {
        if(this.getStatus($item)==="editing") {
            if(this.validateBlank($item.find(this.CATEGORY_EDIT_INPUT))) {
              return false;
            }
            this.categoryModel.reqModify(this.getId($item), $item.find(this.CATEGORY_EDIT_INPUT).val());
        }
        else {
            this.setStatus($item, "editing");
            this.toggleModifyInput($item);
        }
    }

    CategoryPresenter.prototype.destroyBtnClickHandler = function(e) {
        var $item = $(e.currentTarget).closest(this.CATEGORY_ITEM_SELECTOR);
        this.categoryModel.reqDestroy(this.getId($item));
    }

    CategoryPresenter.prototype.enterInputHandler = function(e) {
        if(e.which === this.ENTER_KEY) {
          var $item = $(e.currentTarget).closest(this.CATEGORY_ITEM_SELECTOR);
          this.modify($item);
        }
    }

    CategoryPresenter.prototype.addTriggerHandler = function() {
        this.categoryModel.on("destroy", this.destroyDone.bind(this));
        this.categoryModel.on("modify", this.modifyDone.bind(this));
    }

    CategoryPresenter.prototype.destroyDone = function(id, res) {
        this.$categoryList.find("[data-id="+id+"]").slideUp("slow", function(){ $(this).remove() });
    }

    CategoryPresenter.prototype.modifyDone = function(id, strToBeName, res) {
         var $item = this.$categoryList.find("[data-id="+id+"]");

         $item.find(this.CATEGORY_NAME).html(strToBeName);
         this.toggleModifyInput($item);
         this.setStatus($item, "");
    }

    CategoryPresenter.prototype.validateBlank = function($inputTarget) {
        var trimmedStr = $.trim($inputTarget.val());
        if(trimmedStr === "") {
            alert("빈값은 입력이 불가합니다.");
            $inputTarget.focus();
            return true;
        }
        return false;
    }

    CategoryPresenter.prototype.toggleModifyInput = function($item) {
        $item.find(this.CATEGORY_NAME).toggleClass("hide");
        $item.find(this.CATEGORY_EDIT_INPUT).toggleClass("hide").val("");
    }

    CategoryPresenter.prototype.getId = function($item) {
        return $item.data("id");
    }

    CategoryPresenter.prototype.setStatus = function($item, strStatus) {
        $item.data("status", strStatus);
    }

    CategoryPresenter.prototype.getStatus = function($item) {
        return $item.data("status");
    }

    return CategoryPresenter

});
