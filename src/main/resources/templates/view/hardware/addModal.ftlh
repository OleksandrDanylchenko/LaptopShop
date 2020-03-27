<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/selectCleaner.ftlh" as sC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro hardwareAdd>
    <div class="modal fade" id="hardwareAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="hardwareAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="hardwareAddLabel">Додати нову збірку</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <#if cpuModels?has_content>
                    <#if ramModels?has_content>
                        <#if ssdModels?has_content>
                            <#if displayModels?has_content>
                                <#if hddModels?has_content>
                                    <#if gpuModels?has_content>
                                        <div class="modal-body">
                                            <form method="post" id="hardwareAddForm" action="/hardware/add"
                                                  class="form-group needs-validation w-100"
                                                  novalidate onsubmit="return validateFormAdd()">
                                                <div class="form-row mb-2">
                                                    <div class="col">
                                                        <label class="d-inline ml-2" for="assemblyName">
                                                            Назва нової збірки:</label>
                                                        <input type="text" id="newAssemblyNameInput"
                                                               class="form-control" name="assemblyName"
                                                               required/>
                                                        <div class="invalid-feedback">
                                                            Назву нової збірки задано некоректно!
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-row my-2">
                                                    <div class="col">
                                                        <label class="d-inline ml-2" for="cpuModel">
                                                            Модель процесору:</label>
                                                        <select class="custom-select" id="newCpuSelect" name="cpuModel"
                                                                required>
                                                            <option value="" selected disabled></option>
                                                            <#list cpuModels as cpuModel>
                                                                <option>${cpuModel}</option>
                                                            </#list>
                                                        </select>
                                                        <div class="invalid-feedback">Не обрано модель процесору!</div>
                                                    </div>
                                                </div>
                                                <div class="form-row my-2">
                                                    <div class="col">
                                                        <label class="d-inline ml-2" for="gpuModel">
                                                            Модель відеокарти:</label>
                                                        <select class="custom-select" id="newGpuSelect" name="gpuModel"
                                                                required>
                                                            <option value="" selected disabled></option>
                                                            <#list gpuModels as gpuModel>
                                                                <option>${gpuModel}</option>
                                                            </#list>
                                                        </select>
                                                        <div class="invalid-feedback">Не обрано модель відеокарти!</div>
                                                    </div>
                                                </div>
                                                <div class="form-row my-2">
                                                    <div class="col">
                                                        <label class="d-inline ml-2" for="displayModel">
                                                            Модель дисплею:</label>
                                                        <select class="custom-select" id="newDisplaySelect"
                                                                name="displayModel" required>
                                                            <option value="" selected disabled></option>
                                                            <#list displayModels as displayModel>
                                                                <option>${displayModel}</option>
                                                            </#list>
                                                        </select>
                                                        <div class="invalid-feedback">Не обрано модель дисплею!</div>
                                                    </div>
                                                </div>
                                                <div class="form-row my-2">
                                                    <div class="col">
                                                        <label class="d-inline ml-2" for="ramModel">
                                                            Модель оперативної пам'яті:</label>
                                                        <select class="custom-select" id="newRamSelect" name="ramModel"
                                                                required>
                                                            <option value="" selected disabled></option>
                                                            <#list ramModels as ramModel>
                                                                <option>${ramModel}</option>
                                                            </#list>
                                                        </select>
                                                        <div class="invalid-feedback">Не обрано модель оперативної
                                                            пам'яті!
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-row my-2">
                                                    <div class="col">
                                                        <label class="d-inline ml-2" for="ssdModel">
                                                            Модель SSD диску:</label>
                                                        <select class="custom-select" id="newSsdSelect" name="ssdModel"
                                                                required>
                                                            <option value="" selected disabled></option>
                                                            <#list ssdModels as ssdModel>
                                                                <option>${ssdModel}</option>
                                                            </#list>
                                                        </select>
                                                        <div class="invalid-feedback">Не обрано модель SSD диску!</div>
                                                    </div>
                                                </div>
                                                <div class="form-row my-2">
                                                    <div class="col">
                                                        <label class="d-inline ml-2" for="ssdModel">
                                                            Модель HDD диску:</label>
                                                        <select class="custom-select" id="newHddSelect" name="hddModel"
                                                                required>
                                                            <option value="" selected disabled></option>
                                                            <#list hddModels as hddModel>
                                                                <option>${hddModel}</option>
                                                            </#list>
                                                        </select>
                                                        <div class="invalid-feedback">Не обрано модель HDD диску!</div>
                                                    </div>
                                                </div>
                                            </form>
                                            <@eB.emptyBreaker inputIds=["newAssemblyNameInput"]/>
                                            <@vC.valueBreaker id="Add" inputIds=["newAssemblyNameInput"]/>
                                            <@fC.inputsCleaner modalId="hardwareAddModal" inputIds=["newAssemblyNameInput"]/>
                                            <@sC.selectsCleaner modalId="hardwareAddModal"
                                            selectIds=["newCpuSelect", "newGpuSelect", "newDisplaySelect",
                                            "newRamSelect", "newSsdSelect", "newHddSelect"]/>
                                            <@fSc.fieldsStatusCleaner modalId="hardwareAddModal" formId="hardwareAddForm"/>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати
                                            </button>
                                            <button type="submit" form="hardwareAddForm" class="btn btn-danger">Додати
                                            </button>
                                        </div>
                                    <#else>
                                        <div class="modal-body">
                                            <div class="alert alert-warning" role="alert">
                                                <div class="h1 text-center">Не знайдено жодної моделі відеокарти.</div>
                                                <div class="h3 text-center">
                                                    <a href="/gpu">Додайте нову відеокарту</a> для створення збірки.
                                                </div>
                                            </div>
                                        </div>
                                    </#if>
                                <#else>
                                    <div class="modal-body">
                                        <div class="alert alert-warning" role="alert">
                                            <div class="h1 text-center">Не знайдено жодної моделі HDD диску.</div>
                                            <div class="h3 text-center">
                                                <a href="/hdd">Додайте новий HDD диск</a> для створення збірки.
                                            </div>
                                        </div>
                                    </div>
                                </#if>
                            <#else>
                                <div class="modal-body">
                                    <div class="alert alert-warning" role="alert">
                                        <div class="h1 text-center">Не знайдено жодної моделі дисплею.</div>
                                        <div class="h3 text-center">
                                            <a href="/display">Додайте новий дисплей</a> для створення збірки.
                                        </div>
                                    </div>
                                </div>
                            </#if>
                        <#else>
                            <div class="modal-body">
                                <div class="alert alert-warning" role="alert">
                                    <div class="h1 text-center">Не знайдено жодної моделі SSD диску.</div>
                                    <div class="h3 text-center">
                                        <a href="/ssd">Додайте новий SSD диск</a> для створення збірки.
                                    </div>
                                </div>
                            </div>
                        </#if>
                    <#else>
                        <div class="modal-body">
                            <div class="alert alert-warning" role="alert">
                                <div class="h1 text-center">Не знайдено жодної моделі оперативної пам'яті.</div>
                                <div class="h3 text-center">
                                    <a href="/ram">Додайте нову оперативну пам'ять</a> для створення збірки.
                                </div>
                            </div>
                        </div>
                    </#if>
                <#else>
                    <div class="modal-body">
                        <div class="alert alert-warning" role="alert">
                            <div class="h1 text-center">Не знайдено жодної моделі процесору.</div>
                            <div class="h3 text-center"><a href="/cpu/add">Додайте новий процесор</a> для створення
                                збірки.
                            </div>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</#macro>