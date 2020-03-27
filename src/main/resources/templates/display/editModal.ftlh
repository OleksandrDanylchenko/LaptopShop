<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro displayEdit id displayModel displayType displayDiagonal displayResolution>
    <div class="modal fade" id="displayEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="displayEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="displayEditLabel${id}">Змінити інформацію про дисплей</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="displayEditForm${id}" action="/display/edit/${id}"
                          class="form-group needs-validation w-100" novalidate onsubmit="return validateForm${id}()">
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="model">Назва змінюваної моделі:</label>
                                <input type="text" id="editModelInput${id}" class="form-control" name="editModel"
                                       required/>
                                <div class="invalid-feedback">Назву змінюваної моделі задано некоректно!</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="type">Назва типу:</label>
                                <input type="text" id="editTypeInput${id}" class="form-control" name="editType"
                                       required/>
                                <div class="invalid-feedback">Назву типу задано некоректно!</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="diagonal">Діагональ дисплею:</label>
                                <input type="number" id="editDiagonalInput${id}" class="form-control"
                                       step="0.1" min="0.1" max="19" name="editDiagonal" required/>
                                <div class="invalid-feedback">Діагональ задано некоректно!</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="resolution">Розширення дисплею:</label>
                                <input type="text" id="editResolutionInput${id}" class="form-control" name="editResolution"
                                       required/>
                                <div class="invalid-feedback">Розширення задано некоректно!</div>
                            </div>
                        </div>
                    </form>
                    <@eB.emptyBreaker inputIds=["editModelInput${id}", "editTypeInput${id}", "editResolutionInput${id}"]/>
                    <@vC.valueBreaker id="${id}"
                    inputIds=["editModelInput${id}", "editTypeInput${id}", "editResolutionInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="displayEditModal${id}" formId="displayEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $('#displayEditModal${id}').on('show.bs.modal', function () {
                            document.getElementById("editModelInput${id}").value = "${displayModel}";
                            document.getElementById("editTypeInput${id}").value = "${displayType}";
                            document.getElementById("editDiagonalInput${id}").value = "${displayDiagonal}";
                            document.getElementById("editResolutionInput${id}").value = "${displayResolution}";
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="displayEditForm${id}" class="btn btn-danger">Змінити</button>
                </div>
            </div>
        </div>
    </div>
</#macro>