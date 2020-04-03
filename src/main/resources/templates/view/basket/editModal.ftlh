<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro basketEdit id currentEmployeeId currentClientId currentDateTime>
    <div class="modal fade" id="basketEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="basketEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="basketEditLabel${id}">Зміна інформації про кошик</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" action="/basket/edit/${id}" id="basketEditForm${id}"
                          class="form-group needs-validation w-100" novalidate>
                        <#if employeeIds?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="employeeSelect${id}">ID співробітника:</label>
                                    <select class="custom-select" id="employeeSelect${id}" name="editEmployeeId">
                                        <#list employeeIds as employeeId>
                                            <option value="${employeeId}">${employeeId}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </#if>

                        <#if clientIds?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="clientSelect${id}">ID покупця:</label>
                                    <select class="custom-select" id="clientSelect${id}" name="editClientId">
                                        <#list clientIds as clientId>
                                            <option value="${clientId}">${clientId}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </#if>

                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="dateTimeSelect${id}">Дата/час покупки:</label>
                                <input type="datetime-local" id="dateTimeSelect${id}" class="form-control"
                                       name="editDateTime" min="2020-02-16T00:00" required/>
                                <div class="invalid-feedback">Некоректна дата/час покупки!</div>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@fSc.fieldsStatusCleaner modalId="basketEditModal${id}" formId="basketEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $("#basketEditModal${id}").on("show.bs.modal", function () {
                            <#if currentEmployeeId != "">
                            $("#employeeSelect${id}").val("${currentEmployeeId}").change();
                            </#if>
                            <#if currentClientId != "">
                            $("#clientSelect${id}").val("${currentClientId}").change();
                            </#if>
                            $("#dateTimeSelect${id}").val("${currentDateTime}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="basketEditForm${id}" class="btn btn-danger">Змінити</button>
                </div>
            </div>
        </div>
    </div>
</#macro>