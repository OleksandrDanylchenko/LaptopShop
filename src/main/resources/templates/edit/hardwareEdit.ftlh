<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorsHandlers/errorOutput.ftlh" as error>

<@pad.page "Hardware editing">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Змінити збірку:</p>
    <form method="post" action="/hardware/edit/${editHardware.id}" class="form-group needs-validation w-100" novalidate>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="assemblyName">Назва збірки:</label>
                <input type="text" class="form-control mr-1 mt-2" name="assemblyName"
                       value="${editHardware.assemblyName}" required/>
                <div class="invalid-feedback">Назва збірки не може бути пустою!</div>
            </div>
        </div>

        <#if cpuModels?has_content>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="cpuModel">Модель процесору:</label>
                    <select class="custom-select" name="cpuModel" required>
                        <#list cpuModels as cpuModel>
                            <#if editHardware.cpu??>
                                <option value="${cpuModel}"<#if cpuModel == editHardware.cpu.model>
                                    selected="selected"</#if>>${cpuModel}</option>
                            <#else>
                                <option>${cpuModel}</option>
                            </#if>
                        </#list>
                    </select>
                    <div class="invalid-feedback">Не обрано модель процесору!</div>
                </div>
            </div>
        </#if>

        <#if gpuModels?has_content>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="gpuModel">Модель відеокарти:</label>
                    <select class="custom-select" name="gpuModel" required>
                        <#list gpuModels as gpuModel>
                            <#if editHardware.gpu??>
                                <option value="${gpuModel}"<#if gpuModel == editHardware.gpu.model>
                                    selected="selected"</#if>>${gpuModel}</option>
                            <#else>
                                <option>${gpuModel}</option>
                            </#if>
                        </#list>
                    </select>
                    <div class="invalid-feedback">Не обрано модель відеокарти!</div>
                </div>
            </div>
        </#if>

        <#if displayModels?has_content>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="displayModel">Модель дисплею:</label>
                    <select class="custom-select" name="displayModel" required>
                        <#list displayModels as displayModel>
                            <#if editHardware.display??>
                                <option value="${displayModel}"<#if displayModel == editHardware.display.model>
                                    selected="selected"</#if>>${displayModel}</option>
                            <#else>
                                <option>${displayModel}</option>
                            </#if>
                        </#list>
                    </select>
                    <div class="invalid-feedback">Не обрано модель дисплею!</div>
                </div>
            </div>
        </#if>

        <#if ramModels?has_content>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="ramModel">Модель оперативної пам'яті:</label>
                    <select class="custom-select" name="ramModel" required>
                        <#list ramModels as ramModel>
                            <#if editHardware.ram??>
                                <option value="${ramModel}"<#if ramModel == editHardware.ram.model>
                                    selected="selected"</#if>>${ramModel}</option>
                            <#else>
                                <option>${ramModel}</option>
                            </#if>
                        </#list>
                    </select>
                    <div class="invalid-feedback">Не обрано модель оперативної пам'яті!</div>
                </div>
            </div>
        </#if>

        <#if ssdModels?has_content>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="ssdModel">Модель SSD диску:</label>
                    <select class="custom-select" name="ssdModel" required>
                        <#list ssdModels as ssdModel>
                            <#if editHardware.ssd??>
                                <option value="${ssdModel}"<#if ssdModel == editHardware.ssd.model>
                                    selected="selected"</#if>>${ssdModel}</option>
                            <#else>
                                <option>${ssdModel}</option>
                            </#if>
                        </#list>
                    </select>
                    <div class="invalid-feedback">Не обрано модель SSD диску!</div>
                </div>
            </div>
        </#if>

        <#if hddModels?has_content>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="ssdModel">Модель HDD диску:</label>
                    <select class="custom-select" name="hddModel" required>
                        <#list hddModels as hddModel>
                            <#if editHardware.hdd??>
                                <option value="${hddModel}"<#if hddModel == editHardware.hdd.model>
                                    selected="selected"</#if>>${hddModel}</option>
                            <#else>
                                <option>${hddModel}</option>
                            </#if>
                        </#list>
                    </select>
                    <div class="invalid-feedback">Не обрано модель HDD диску!</div>
                </div>
            </div>
        </#if>

        <button type="submit" class="btn btn-danger float-right">Зберегти</button>
    </form>
</@pad.page>