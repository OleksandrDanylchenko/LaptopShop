<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro clientEdit id clientFirstName clientSecondName clientDateReg>
    <div class="modal fade" id="clientEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="clientEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="clientEditLabel${id}">Змінити інформацію про клієнта</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="clientEditForm${id}" action="/client/edit/${id}"
                          class="form-group needs-validation w-100" novalidate onsubmit="return validateForm${id}()">
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="firstName">Ім'я змінюваного покупця:</label>
                                <input type="text" id="editFirstNameInput${id}" class="form-control" name="editFirstName"
                                       required/>
                                <div class="invalid-feedback">Ім'я змінюваного покупця задано некоректно!</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="secondName">Прізвище змінюваного покупця:</label>
                                <input type="text" id="editSecondNameInput${id}" class="form-control" name="editSecondName"
                                       required/>
                                <div class="invalid-feedback">Прізвище змінюваного покупця задано некоректно!</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="dateReg">Дата реєстрації:</label>
                                <input type="date" id="editDateRegInput${id}" class="form-control" name="editDateReg"
                                       min="2019-02-16" required/>
                                <div class="invalid-feedback">Уведено некоректну дату реєстрації!</div>
                            </div>
                        </div>
                    </form>
                    <@eB.emptyBreaker inputIds=["editFirstNameInput${id}", "editSecondNameInput${id}"]/>
                    <@vC.valueBreaker id="${id}" inputIds=["editFirstNameInput${id}", "editSecondNameInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="clientEditModal${id}" formId="clientEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $('#clientEditModal${id}').on('show.bs.modal', function () {
                            document.getElementById("editFirstNameInput${id}").value = "${clientFirstName}";
                            document.getElementById("editSecondNameInput${id}").value = "${clientSecondName}";
                            document.getElementById("editDateRegInput${id}").value = "${clientDateReg}";
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="clientEditForm${id}" class="btn btn-danger">Змінити</button>
                </div>
            </div>
        </div>
    </div>
</#macro>