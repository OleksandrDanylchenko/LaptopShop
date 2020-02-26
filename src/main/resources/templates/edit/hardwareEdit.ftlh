<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Hardware editing">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Змінити збірку:</p>
    <form method="post" action="/hardware/edit/${editHardware.id}" class="form-group needs-validation w-100"
          novalidate>

        <div class="form-row my-2">
            <input type="text" class="form-control mr-1 mt-2" name="assemblyName"
                   value="${editHardware.assemblyName}" placeholder="Назва"
                   required/>
            <div class="invalid-feedback">Назва моделі не може бути пустою!</div>
        </div>

        <#if cpuModels?has_content>
            <div class="form-row my-2">
                <select class="custom-select" name="cpuModel">
                    <option value="" selected disabled>Оберіть модель процесору</option>
                    <#list cpuModels as cpuModel>
                        <#if editHardware.cpu??>
                            <option value="${cpuModel}"<#if cpuModel == editHardware.cpu.model>
                                selected="selected"</#if>>${cpuModel}</option>
                        <#else>
                            <option>${cpuModel}</option>
                        </#if>
                    </#list>
                </select>
            </div>
        </#if>

        <#if gpuModels?has_content>
            <div class="form-row my-2">
                <select class="custom-select" name="gpuModel">
                    <option value="" selected disabled>Оберіть модель відеокарти</option>
                    <#list gpuModels as gpuModel>
                        <#if editHardware.gpu??>
                            <option value="${gpuModel}"<#if gpuModel == editHardware.gpu.model>
                                selected="selected"</#if>>${gpuModel}</option>
                        <#else>
                            <option>${gpuModel}</option>
                        </#if>
                    </#list>
                </select>
            </div>
        </#if>

        <#if displayModels?has_content>
            <div class="form-row my-2">
                <select class="custom-select" name="displayModel">
                    <option value="" selected disabled>Оберіть модель дисплею</option>
                    <#list displayModels as displayModel>
                        <#if editHardware.display??>
                            <option value="${displayModel}"<#if displayModel == editHardware.display.model>
                                selected="selected"</#if>>${displayModel}</option>
                        <#else>
                            <option>${displayModel}</option>
                        </#if>
                    </#list>
                </select>
            </div>
        </#if>

        <#if ramModels?has_content>
            <div class="form-row my-2">
                <select class="custom-select" name="ramModel">
                    <option value="" selected disabled>Оберіть модель оперативної пам'яті</option>
                    <#list ramModels as ramModel>
                        <#if editHardware.ram??>
                            <option value="${ramModel}"<#if ramModel == editHardware.ram.model>
                                selected="selected"</#if>>${ramModel}</option>
                        <#else>
                            <option>${ramModel}</option>
                        </#if>
                    </#list>
                </select>
            </div>
        </#if>

        <#if ssdModels?has_content>
            <div class="form-row my-2">
                <select class="custom-select" name="ssdModel">
                    <option value="" selected disabled>Оберіть модель SSD диску</option>
                    <#list ssdModels as ssdModel>
                        <#if editHardware.ssd??>
                            <option value="${ssdModel}"<#if ssdModel == editHardware.ssd.model>
                                selected="selected"</#if>>${ssdModel}</option>
                        <#else>
                            <option>${ssdModel}</option>
                        </#if>
                    </#list>
                </select>
            </div>
        </#if>

        <#if hddModels?has_content>
            <div class="form-row my-2">
                <select class="custom-select" name="hddModel">
                    <option value="" selected disabled>Оберіть модель HDD диску</option>
                    <#list hddModels as hddModel>
                        <#if editHardware.hdd??>
                            <option value="${hddModel}"<#if hddModel == editHardware.hdd.model>
                                selected="selected"</#if>>${hddModel}</option>
                        <#else>
                            <option>${hddModel}</option>
                        </#if>
                    </#list>
                </select>
            </div>
        </#if>

        <button type="submit" class="btn btn-primary mt-1 float-right">Зберегти</button>
    </form>
</@pad.page>