<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro shopAdd>
    <div class="modal fade" id="shopAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="shopAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="shopAddLabel">Додати новий магазин</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="shopAddForm" action="/shop/add" class="form-group needs-validation w-100"
                          novalidate modelAttribute="newShop" onsubmit="return validateFormAdd()">
                        <label class="d-inline ml-2" for="name">Адреса нового магазину:</label>
                        <input type="text" id="newAddressInput" class="form-control" name="address" required/>
                        <div class="invalid-feedback">Адресу нового магазину задано некоректно!</div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["newAddressInput"]/>
                    <@vC.valueBreaker id="Add" inputIds=["newAddressInput"]/>
                    <@fC.inputsCleaner modalId="shopAddModal" inputIds=["newAddressInput"]/>
                    <@fSc.fieldsStatusCleaner modalId="shopAddModal" formId="shopAddForm"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="shopAddForm" class="btn btn-danger">Додати</button>
                </div>
            </div>
        </div>
    </div>
</#macro>