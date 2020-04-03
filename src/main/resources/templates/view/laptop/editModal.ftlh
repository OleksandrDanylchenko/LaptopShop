<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro laptopEdit id currentLabelModel currentType currentAssembly>
    <div class="modal fade" id="laptopEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="laptopEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="laptopEditLabel${id}">Змінити інформацію про ноутбук</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" action="/laptop/edit/${id}" id="laptopEditForm${id}" class="form-group">
                        <#if labelModels?has_content>
                            <div class="form-row mb-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="labelSelect${id}">Назва моделі:</label>
                                    <select class="custom-select" id="labelSelect${id}" name="editLabelModel">
                                        <#list labelModels as labelModel>
                                            <option value="${labelModel}">${labelModel}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </#if>

                        <#if typeNames?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="typeSelect${id}">Назва типу:</label>
                                    <select class="custom-select" id="typeSelect${id}" name="editTypeName">
                                        <#list typeNames as typeName>
                                            <option value="${typeName}">${typeName}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </#if>

                        <#if hardwareAssemblyNames?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="assemblyNameSelect${id}">Назва збірки:</label>
                                    <select class="custom-select" id="assemblyNameSelect${id}" name="editAssemblyName">
                                        <#list hardwareAssemblyNames as hardwareAssemblyName>
                                            <option value="${hardwareAssemblyName}">${hardwareAssemblyName}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </#if>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@fSc.fieldsStatusCleaner modalId="laptopEditModal${id}" formId="laptopEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $("#laptopEditModal${id}").on("show.bs.modal", function () {
                            $("#labelSelect${id}").val("${currentLabelModel}").change();
                            $("#typeSelect${id}").val("${currentType}").change();
                            $("#assemblyNameSelect${id}").val("${currentAssembly}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="laptopEditForm${id}" class="btn btn-danger">Змінити</button>
                </div>
            </div>
        </div>
    </div>
</#macro>