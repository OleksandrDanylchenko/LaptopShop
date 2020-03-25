<#macro typeAdd>
    <#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
    <#import "../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
    <#import "../modalInputManage/fieldsCleaner.ftlh" as fC>

    <div class="modal fade" id="typeAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="typeAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="typeAddLabel">Додати новий тип ноутбуків</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="typeAddForm" action="/type/add" class="form-group needs-validation w-100"
                          novalidate modelAttribute="newType" onsubmit="return validateAddForm()">
                        <label class="d-inline ml-2" for="name">Назва нового типу:</label>
                        <input type="text" class="form-control" name="name" id="newNameInput" required/>
                        <div class="invalid-feedback">Назву нового типу задано некоректно!</div>
                    </form>
                    <@eB.emptyBreaker inputIds=["newNameInput"]/>
                    <@vC.valueBreaker type="Add" inputIds=["newNameInput"]/>
                    <@fC.fieldsCleaner modalId="typeAddModal" formId="typeAddForm" inputIds=["newNameInput"]/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="typeAddForm" class="btn btn-danger">Додати</button>
                </div>
            </div>
        </div>
    </div>
</#macro>