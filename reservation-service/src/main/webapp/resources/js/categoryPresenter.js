define("categoryPresenter", [], function() {
    "use strict";

    function CategoryPresenter(categoryModel) {
        this.initVariable(categoryModel);
        this.addEventHandler();
        this.addTriggerHandler();
    }

    CategoryPresenter.prototype.initVariable = function(categoryModel) {
        this.$categoryList = $("._categoryList");
        this.categoryItem = "._item";
        this.name = "._name";
        this.editInput = "._editInput";

        this.categoryModel = categoryModel;
    }

    CategoryPresenter.prototype.addEventHandler = function() {
        this.$categoryList.on("click", "._modify", this.modifyBtnClickHandler.bind(this))
                          .on("click", "._destroy", this.destroyBtnClickHandler.bind(this))
                          .on("keyup", this.editInput, this.enterInputHandler.bind(this));

        $(document).ajaxError(function (event, xhr, ajaxOptions, thrownError) {
              alert("일시적 오류가 발생하였습니다.");
        });
    }

    CategoryPresenter.prototype.modifyBtnClickHandler = function(e) {
        var $item = $(e.currentTarget).parents(this.categoryItem);
        this.modify($item);
    }

    CategoryPresenter.prototype.modify = function($item) {
        if(this.getStatus($item)==="editing") {
            if(this.isBlank($item.find(this.editInput))) {
              return false;
            }
            this.categoryModel.reqModify(this.getId($item), $item.find(this.editInput).val());
        }
        else {
            this.setStatus($item, "editing");
            this.ToggleModifyInput($item);
        }
    }

    CategoryPresenter.prototype.destroyBtnClickHandler = function(e) {
        var $item = $(e.currentTarget).parents(this.categoryItem);
        this.categoryModel.reqDestroy(this.getId($item));
    }

    CategoryPresenter.prototype.enterInputHandler = function(e) {
        if(e.which == 13) {
          var $item = $(e.currentTarget).parents(this.categoryItem);
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

         $item.find(this.name).html(strToBeName);
         this.ToggleModifyInput($item);
         this.setStatus($item, "");
    }

    CategoryPresenter.prototype.isBlank = function($inputTarget) {
        var trimmedStr = $.trim($inputTarget.val());
        if(trimmedStr === "") {
            alert("빈값은 입력이 불가합니다.");
            $inputTarget.focus();
            return true;
        }
        return false;
    }

    CategoryPresenter.prototype.ToggleModifyInput = function($item) {
        $item.find(this.name).toggleClass("hide");
        $item.find(this.editInput).toggleClass("hide").val("");
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
