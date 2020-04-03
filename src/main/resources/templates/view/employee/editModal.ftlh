<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import '../../errorsHandlers/valueConsistentBreaker.ftlh' as vC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro employeeEdit id currentFirstName currentSecondName currentShopAddress currentWorkingStatus>
    <div class="modal fade" id="employeeEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="employeeEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="employeeEditLabel${id}">Змінити інформацію про співробітника</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" action="/employee/edit/${id}"
                          id="employeeEditForm${id}" class="form-group needs-validation w-100" novalidate
                          onsubmit="return validateForm${id}()">
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="editFirstNameInput${id}">
                                    Ім'я змінюваного співробітника:
                                </label>
                                <input type="text" id="editFirstNameInput${id}" class="form-control"
                                       name="editFirstName" required/>
                                <div class="invalid-feedback">Ім'я змінюваного співробітника задано некоректно!</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="editSecondNameInput${id}">
                                    Прізвище змінюваного співробітника:
                                </label>
                                <input type="text" id="editSecondNameInput${id}" class="form-control"
                                       name="editSecondName" required/>
                                <div class="invalid-feedback">
                                    Прізвище змінюваного співробітника задано некоректно!
                                </div>
                            </div>
                        </div>

                        <#if shopAddresses?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="shopSelect${id}">Адреса магазину:</label>
                                    <select class="custom-select" id="shopSelect${id}" name="editShopAddress" required>
                                        <#list shopAddresses as address>
                                            <option value="${address}">${address}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не обрано магазину!</div>
                                </div>
                            </div>
                        </#if>

                        <div class="form-row my-2">
                            <div class="col">
                                <#assign isWorkingChoises = ['Працюючий', 'Звільнений']>
                                <label class="d-inline ml-2" for="workingSelect${id}">Статус співробітника:</label>
                                <select class="custom-select" id="workingSelect${id}" name="editIsWorking">
                                    <#list isWorkingChoises as choice>
                                        <option value="${choice}">${choice}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["editFirstNameInput${id}", "editSecondNameInput${id}"]/>
                    <@vC.valueBreaker id="${id}" inputIds=["editFirstNameInput${id}", "editSecondNameInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="employeeEditModal${id}" formId="employeeEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $("#employeeEditModal${id}").on("show.bs.modal", function () {
                            $("#editFirstNameInput${id}").val("${currentFirstName}").change();
                            $("#editSecondNameInput${id}").val("${currentSecondName}").change();
                            $("#shopSelect${id}").val("${currentShopAddress}").change();
                            $("#workingSelect${id}").val("${currentWorkingStatus}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="employeeEditForm${id}" class="btn btn-danger">Змінити</button>
                </div>
            </div>
        </div>
    </div>
</#macro>