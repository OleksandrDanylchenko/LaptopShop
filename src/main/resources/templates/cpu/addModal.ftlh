<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro cpuAdd>
    <div class="modal fade" id="cpuAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="cpuAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="cpuAddLabel">Додати новий процесор</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="cpuAddForm" action="/cpu/add" class="form-group needs-validation w-100"
                          novalidate modelAttribute="newCPU" onsubmit="return validateFormAdd()">
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="model">Назва нової моделі:</label>
                                <input type="text" id="newModelInput" class="form-control" name="model" required/>
                                <div class="invalid-feedback">Назву нової моделі задано некоректно!</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="frequency">Тактова частота (GhZ):</label>
                                <input type="number" id="newFrequencyInput" step="0.1" min="0.1" max="10"
                                       class="form-control" name="frequency" required/>
                                <div class="invalid-feedback">Тактову частоту задано некоректно!</div>
                            </div>
                        </div>
                    </form>
                    <@eB.emptyBreaker inputIds=["newModelInput"]/>
                    <@vC.valueBreaker id="Add" inputIds=["newModelInput"]/>
                    <@fC.inputsCleaner modalId="cpuAddModal" inputIds=["newModelInput", "newFrequencyInput"]/>
                    <@fSc.fieldsStatusCleaner modalId="cpuAddModal" formId="cpuAddForm"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="cpuAddForm" class="btn btn-danger">Додати</button>
                </div>
            </div>
        </div>
    </div>
</#macro>