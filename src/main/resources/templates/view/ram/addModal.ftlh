<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro ramAdd>
    <div class="modal fade" id="ramAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="ramAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h4" id="ramAddLabel">Додати нову оперативну пам'ять</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="ramAddForm" action="/ram/add" class="form-group needs-validation w-100"
                          novalidate modelAttribute="newRAM" onsubmit="return validateFormAdd()">
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="model">Назва нової моделі:</label>
                                <input type="text" id="newModelInput" class="form-control" name="model" required/>
                                <div class="invalid-feedback">Назву нової моделі задано некоректно!</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="memory">Обсяг пам'яті (GiB):</label>
                                <input type="number" id="newMemoryInput" min="1" max="225" class="form-control"
                                       name="memory" required/>
                                <div class="invalid-feedback">Обсяг пам'яті задано некоректно!</div>
                            </div>
                        </div>
                    </form>
                    <@eB.emptyBreaker inputIds=["newModelInput"]/>
                    <@vC.valueBreaker id="Add" inputIds=["newModelInput"]/>
                    <@fC.inputsCleaner modalId="ramAddModal" inputIds=["newModelInput", "newMemoryInput"]/>
                    <@fSc.fieldsStatusCleaner modalId="ramAddModal" formId="ramAddForm"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="ramAddForm" class="btn btn-danger">Додати</button>
                </div>
            </div>
        </div>
    </div>
</#macro>