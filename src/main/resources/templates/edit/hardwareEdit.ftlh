<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Hardware editing">
    <h2>${errorMessage!}</h2>

    <h1>Змінити збірку:</h1>
    <form method="post" action="/hardware/edit/${editHardware.id}">
        <label>Назва збірки: <input type="text" name="assemblyName" value="${editHardware.assemblyName}"/></label>
        <label>Модель процесору:
            <select name="cpuModel">
                <#list cpuModels as cpuModel>
                    <option>${cpuModel}</option>
                </#list>
            </select>
        </label>
        <label>Модель відеокарти:
            <select name="gpuModel">
                <#list gpuModels as gpuModel>
                    <option>${gpuModel}</option>
                </#list>
            </select>
        </label>
        <label>Модель дисплею:
            <select name="displayModel">
                <#list displayModels as displayModel>
                    <option>${displayModel}</option>
                </#list>
            </select>
        </label>
        <label>Модель оперативної пам'яті:
            <select name="ramModel">
                <#list ramModels as ramModel>
                    <option>${ramModel}</option>
                </#list>
            </select>
        </label>
        <label>Модель SSD диску:
            <select name="ssdModel">
                <#list ssdModels as ssdModel>
                    <option>${ssdModel}</option>
                </#list>
            </select>
        </label>
        <label>Модель HDD диску:
            <select name="hddModel">
                <#list hddModels as hddModel>
                    <option>${hddModel}</option>
                </#list>
            </select>
        </label>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>