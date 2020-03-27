<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro gpuAdd>
    <div class="modal fade" id="gpuAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="gpuAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="gpuAddLabel">Додати нову відеокарту</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="gpuAddForm" action="/gpu/add" class="form-group needs-validation w-100"
                          novalidate modelAttribute="newGPU" onsubmit="return validateFormAdd()">
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
                                <div class="invalid-feedback">Некоректно задано обсяг пам'яті!</div>
                            </div>
                        </div>
                    </form>
                    <@eB.emptyBreaker inputIds=["newModelInput"]/>
                    <@vC.valueBreaker id="Add" inputIds=["newModelInput"]/>
                    <@fC.inputsCleaner modalId="gpuAddModal" inputIds=["newModelInput", "newMemoryInput"]/>
                    <@fSc.fieldsStatusCleaner modalId="gpuAddModal" formId="gpuAddForm"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="gpuAddForm" class="btn btn-danger">Додати</button>
                </div>
            </div>
        </div>
    </div>
</#macro>