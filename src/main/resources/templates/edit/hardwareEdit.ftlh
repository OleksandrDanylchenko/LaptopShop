<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Hardware editing">
    <h2>${errorMessage!}</h2>

    <h1>Змінити збірку:</h1>
    <form method="post" action="/hardware/edit/${editHardware.id}">
        <label>Назва збірки: <input type="text" name="assemblyName" value="${editHardware.assemblyName}"/></label>

        <#if cpuModels?has_content>
            <label>Модель процесору:
                <select name="cpuModel">
                    <#list cpuModels as cpuModel>
                        <#if editHardware.cpu??>
                            <option value="${cpuModel}"<#if cpuModel == editHardware.cpu.model>
                                selected="selected"</#if>>${cpuModel}</option>
                        <#else>
                            <option>${cpuModel}</option>
                        </#if>
                    </#list>
                </select>
            </label>
        </#if>

        <#if gpuModels?has_content>
            <label>Модель відеокарти:
                <select name="gpuModel">
                    <#list gpuModels as gpuModel>
                        <#if editHardware.gpu??>
                            <option value="${gpuModel}"<#if gpuModel == editHardware.gpu.model>
                                selected="selected"</#if>>${gpuModel}</option>
                        <#else>
                            <option>${gpuModel}</option>
                        </#if>
                    </#list>
                </select>
            </label>
        </#if>

        <#if displayModels?has_content>
            <label>Модель дисплею:
                <select name="displayModel">
                    <#list displayModels as displayModel>
                        <#if editHardware.display??>
                            <option value="${displayModel}"<#if displayModel == editHardware.display.model>
                                selected="selected"</#if>>${displayModel}</option>
                        <#else>
                            <option>${displayModel}</option>
                        </#if>
                    </#list>
                </select>
            </label>
        </#if>

        <#if ramModels?has_content>
            <label>Модель оперативної пам'яті:
                <select name="ramModel">
                    <#list ramModels as ramModel>
                        <#if editHardware.ram??>
                            <option value="${ramModel}"<#if ramModel == editHardware.ram.model>
                                selected="selected"</#if>>${ramModel}</option>
                        <#else>
                            <option>${ramModel}</option>
                        </#if>
                    </#list>
                </select>
            </label>
        </#if>

        <#if ssdModels?has_content>
            <label>Модель SSD диску:
                <select name="ssdModel">
                    <#list ssdModels as ssdModel>
                        <#if editHardware.ssd??>
                            <option value="${ssdModel}"<#if ssdModel == editHardware.ssd.model>
                                selected="selected"</#if>>${ssdModel}</option>
                        <#else>
                            <option>${ssdModel}</option>
                        </#if>
                    </#list>
                </select>
            </label>
        </#if>

        <#if hddModels?has_content>
            <label>Модель HDD диску:
                <select name="hddModel">
                    <#list hddModels as hddModel>
                        <#if editHardware.hdd??>
                            <option value="${hddModel}"<#if hddModel == editHardware.hdd.model>
                                selected="selected"</#if>>${hddModel}</option>
                        <#else>
                            <option>${hddModel}</option>
                        </#if>
                    </#list>
                </select>
            </label>
        </#if>

        <button type="submit">Зберегти</button>
    </form>
</@pad.page>