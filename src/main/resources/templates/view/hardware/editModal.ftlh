<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import '../../errorsHandlers/valueConsistentBreaker.ftlh' as vC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro hardwareEdit id currentAssemblyName currentCpuModel currentGpuModel currentDisplayModel
currentRamModel currentSsdModel currentHddModel>
    <div class="modal fade" id="hardwareEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="hardwareEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="hardwareEditLabel${id}">Змінити інформацію про збірку</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" action="/hardware/edit/${id}"
                          id="hardwareEditForm${id}" class="form-group needs-validation w-100" novalidate
                          onsubmit="return validateForm${id}()">
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="editAssemblyNameInput${id}">
                                    Назва змінюваної збірки:
                                </label>
                                <input type="text" class="form-control mr-1 mt-2" id="editAssemblyNameInput${id}"
                                       name="editAssemblyName" required/>
                                <div class="invalid-feedback">Назву змінюваної збірки задано некоректно!</div>
                            </div>
                        </div>

                        <#if cpuModels?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="cpuSelect${id}">Модель процесору:</label>
                                    <select class="custom-select" id="cpuSelect${id}" name="editCpuModel" required>
                                        <#list cpuModels as cpuModel>
                                            <option value="${cpuModel}">${cpuModel}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не обрано модель процесору!</div>
                                </div>
                            </div>
                        </#if>

                        <#if gpuModels?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="gpuSelect${id}">Модель відеокарти:</label>
                                    <select class="custom-select" id="gpuSelect${id}" name="editGpuModel" required>
                                        <#list gpuModels as gpuModel>
                                            <option value="${gpuModel}">${gpuModel}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не обрано модель відеокарти!</div>
                                </div>
                            </div>
                        </#if>

                        <#if displayModels?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="displaySelect${id}">Модель дисплею:</label>
                                    <select class="custom-select" id="displaySelect${id}" name="editDisplayModel"
                                            required>
                                        <#list displayModels as displayModel>
                                            <option value="${displayModel}">${displayModel}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не обрано модель дисплею!</div>
                                </div>
                            </div>
                        </#if>

                        <#if ramModels?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="ramSelect${id}">Модель оперативної
                                        пам'яті:</label>
                                    <select class="custom-select" id="ramSelect${id}" name="editRamModel" required>
                                        <#list ramModels as ramModel>
                                            <option value="${ramModel}">${ramModel}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не обрано модель оперативної пам'яті!</div>
                                </div>
                            </div>
                        </#if>

                        <#if ssdModels?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="ssdSelect${id}">Модель SSD диску:</label>
                                    <select class="custom-select" id="ssdSelect${id}" name="editSsdModel" required>
                                        <#list ssdModels as ssdModel>
                                            <option value="${ssdModel}">${ssdModel}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не обрано модель SSD диску!</div>
                                </div>
                            </div>
                        </#if>

                        <#if hddModels?has_content>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="hddSelect${id}">Модель HDD диску:</label>
                                    <select class="custom-select" id="hddSelect${id}" name="editHddModel" required>
                                        <#list hddModels as hddModel>
                                            <option value="${hddModel}">${hddModel}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не обрано модель HDD диску!</div>
                                </div>
                            </div>
                        </#if>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["editAssemblyNameInput${id}"]/>
                    <@vC.valueBreaker id="${id}" inputIds=["editAssemblyNameInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="hardwareEditModal${id}" formId="hardwareEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $("#hardwareEditModal${id}").on("show.bs.modal", function () {
                            $("#editAssemblyNameInput${id}").val("${currentAssemblyName}").change();
                            $("#cpuSelect${id}").val("${currentCpuModel}").change();
                            $("#gpuSelect${id}").val("${currentGpuModel}").change();
                            $("#displaySelect${id}").val("${currentDisplayModel}").change();
                            $("#ramSelect${id}").val("${currentRamModel}").change();
                            $("#ssdSelect${id}").val("${currentSsdModel}").change();
                            $("#hddSelect${id}").val("${currentHddModel}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <button type="submit" form="hardwareEditForm${id}" class="btn btn-danger">Змінити</button>
                </div>
            </div>
        </div>
    </div>
</#macro>