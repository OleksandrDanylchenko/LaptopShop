<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro displayAdd>
    <div class="modal fade" id="displayAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="displayAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="displayAddLabel">Додати новий дисплей</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="displayAddForm" action="/display/add"
                          class="form-group needs-validation w-100" novalidate modelAttribute="newDisplay"
                          onsubmit="return validateFormAdd()">
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="model">Назва нової моделі:</label>
                                <input type="text" id="newModelInput" class="form-control" name="model" required/>
                                <div class="invalid-feedback">Назву нової моделі задано некоректно!</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="type">Назва типу:</label>
                                <input type="text" id="newTypeInput" class="form-control" name="type" required/>
                                <div class="invalid-feedback">Назву типу задано некоректно!</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="diagonal">Діагональ дисплею:</label>
                                <input type="number" id="newDiagonalInput" class="form-control"
                                       step="0.1" min="0.1" max="19" name="diagonal" required/>
                                <div class="invalid-feedback">Діагональ задано некоректно!</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="resolution">Розширення дисплею:</label>
                                <input type="text" id="newResolutionInput" class="form-control" name="resolution"
                                       required/>
                                <div class="invalid-feedback">Розширення задано некоректно!</div>
                            </div>
                        </div>
                    </form>
                    <@eB.emptyBreaker inputIds=["newModelInput", "newTypeInput", "newResolutionInput"]/>
                    <@vC.valueBreaker id="Add" inputIds=["newModelInput", "newTypeInput", "newResolutionInput"]/>
                    <@fC.inputsCleaner modalId="displayAddModal"
                    inputIds=["newModelInput", "newTypeInput", "newResolutionInput", "newDiagonalInput"]/>
                    <@fSc.fieldsStatusCleaner modalId="displayAddModal" formId="displayAddForm"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="displayAddForm" class="btn btn-danger">Додати</button>
                </div>
            </div>
        </div>
    </div>
</#macro>