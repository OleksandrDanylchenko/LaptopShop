<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro typeEdit id typeName>
    <div class="modal fade" id="typeEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="typeEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="typeEditLabel${id}">Змінити тип ноутбуків</div>
                    <button type="button" class="close closeHeader" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="typeEditForm${id}" action="/type/edit/${id}"
                          class="form-group needs-validation w-100" novalidate onsubmit="return validateForm${id}()">
                        <label class="d-inline ml-2" for="editName">Назва змінюваного типу:</label>
                        <input type="text" id="editNameInput${id}" class="form-control" name="editName" required/>
                        <div class="invalid-feedback">Назву змінюваного типу задано некоректно!</div>
                    </form>
                    <@eB.emptyBreaker inputIds=["editNameInput${id}"]/>
                    <@vC.valueBreaker id="${id}" inputIds=["editNameInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="typeEditModal${id}" formId="typeEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $('#typeEditModal${id}').on('show.bs.modal', function () {
                            document.getElementById("editNameInput${id}").value = "${typeName}";
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark closeFooter" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="typeEditForm${id}" class="btn btn-danger">Змінити</button>
                </div>
            </div>
        </div>
    </div>
</#macro>