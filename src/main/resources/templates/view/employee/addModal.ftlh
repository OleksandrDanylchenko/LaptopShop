<#import "../../errorsHandlers/datesOrderBreaker.ftlh" as dO>
<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/selectCleaner.ftlh" as sC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro employeeAdd>
    <div class="modal fade" id="employeeAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="employeeAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="employeeAddLabel">Додати нового співробітника</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <#if shopAddresses?has_content>
                    <div class="modal-body">
                        <form method="post" id="employeeAddForm" action="/employee/add"
                              class="form-group needs-validation w-100" novalidate
                              onsubmit="return validateFormAdd()">
                            <div class="form-row mb-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="firstName">Ім'я нового співробітника:</label>
                                    <input type="text" id="newFirstNameInput" class="form-control" name="firstName"
                                           required/>
                                    <div class="invalid-feedback">Ім'я нового співробітника задано некоректно!</div>
                                </div>
                            </div>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="secondName">Прізвище нового співробітника:</label>
                                    <input type="text" id="newSecondNameInput" class="form-control" name="secondName"
                                           required/>
                                    <div class="invalid-feedback">Прізвище нового співробітника задано некоректно!</div>
                                </div>
                            </div>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="shopAddress">Адреса магазину:</label>
                                    <select class="custom-select" id="shopSelect" name="shopAddress" required>
                                        <option value="" selected disabled></option>
                                        <#list shopAddresses as address>
                                            <option>${address}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не обрано магазину!</div>
                                </div>
                            </div>
                        </form>
                        <@eB.emptyBreaker inputIds=["newFirstNameInput", "newSecondNameInput"]/>
                        <@vC.valueBreaker id="Add" inputIds=["newFirstNameInput", "newSecondNameInput"]/>
                        <@sC.selectsCleaner modalId="employeeAddModal" selectIds=["shopSelect"]/>
                        <@fC.inputsCleaner modalId="employeeAddModal"
                        inputIds=["newFirstNameInput", "newSecondNameInput"]/>
                        <@fSc.fieldsStatusCleaner modalId="employeeAddModal" formId="employeeAddForm"/>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                        <button type="submit" form="employeeAddForm" class="btn btn-danger">Додати</button>
                    </div>
                <#else>
                    <div class="modal-body">
                        <div class="alert alert-warning" role="alert">
                            <div class="h1 text-center">Не знайдено жодного активного магазину.</div>
                            <div class="h3 text-center">
                                <a href="/shop">Додайте новий магазин</a> для створення співробітника.
                            </div>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</#macro>