<#macro typeEdit id editName editButtonId>
    <#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
    <#import "../errorsHandlers/valueConsistentBreaker.ftlh" as vC>

    <div class="modal fade" id="typeEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="typeEditLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="typeEditLabel">Змінити тип ноутбуків</div>
                    <button type="button" class="close closeHeader" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="typeEditForm${id}" action="/type/edit/${id}"
                          class="form-group needs-validation w-100" novalidate modelAttribute="newType"
                          onsubmit="return validateEdit${id}Form()">
                        <div class="form-row">
                            <div class="col">
                                <label class="d-inline ml-2" for="editName">Назва змінюваного типу:</label>
                                <input type="text" class="form-control" name="editName" id="editNameInput${id}"
                                       value="${editName}" required/>
                                <div class="invalid-feedback">Назву змінюваного типу задано некоректно!</div>
                            </div>
                        </div>
                    </form>
                    <@eB.emptyBreaker inputIds=["editNameInput${id}"]/>
                    <@vC.valueBreaker type="Edit${id}" inputIds=["editNameInput${id}"]/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark closeFooter" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="typeEditForm${id}" class="btn btn-danger">Змінити</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Restore initial value after closing of modal -->
    <script>
        $("#${editButtonId}").click(function () {
            document.getElementById("editNameInput${id}").value = "${editName}";
        });
    </script>
</#macro>