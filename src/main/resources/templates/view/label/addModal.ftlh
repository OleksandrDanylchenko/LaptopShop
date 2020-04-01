<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro labelAdd>
    <div class="modal fade" id="labelAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="labelAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="labelAddLabel">Додати нове найменування</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="labelAddForm" action="/label/add" class="form-group needs-validation w-100"
                          novalidate modelAttribute="newLabel" onsubmit="return validateFormAdd()">
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="brand">Назва бренду:</label>
                                <input type="text" id="newBrandInput" class="form-control" name="brand" required/>
                                <div class="invalid-feedback">Назву бренду задано некоректно!</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="model">Назва нової моделі:</label>
                                <input type="text" id="newModelInput" class="form-control" name="model" required/>
                                <div class="invalid-feedback">Назву нової моделі задано некоректно!</div>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["newBrandInput", "newModelInput"]/>
                    <@vC.valueBreaker id="Add" inputIds=["newBrandInput", "newModelInput"]/>
                    <@fC.inputsCleaner modalId="labelAddModal" inputIds=["newBrandInput", "newModelInput"]/>
                    <@fSc.fieldsStatusCleaner modalId="labelAddModal" formId="labelAddForm"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="labelAddForm" class="btn btn-danger">Додати</button>
                </div>
            </div>
        </div>
    </div>
</#macro>