<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/mainRef.ftlh" as main>

<@pad.page "Hardware">
    <@main.mainPageRef/>

    <div>
        <p><a href="/hardware/add">Додати нову збірку</a></p>
    </div>

    <div>
        <p>Знайти збірку за критеріями:</p>
        <form method="get" action="/hardware">
            <label>Назва збірки: <input type="text" name="assemblyName" value="${assemblyName!}"/></label>
            <p><label>Модель дисплею: <input type="text" name="displayModel" value="${displayModel!}"/></label>
            <label>Діагональ дисплею: <input type="number" name="displayDiagonal" step="0.1" min="0.1" value="${displayDiagonal!}"/></label>
            <label>Розширення дисплею: <input type="text" name="displayResolution" value="${displayResolution!}"/></label>
            <label>Тип дисплею: <input type="text" name="displayType" value="${displayType!}"/></label></p>
            <p><label>Модель процесору: <input type="text" name="cpuModel" value="${cpuModel!}"/></label>
            <label>Частота процесору: <input type="number" name="cpuFrequency" step="0.1" min="0.1" max="5" value="${cpuFrequency!}"/></label></p>
            <p><label>Модель відеокарти: <input type="text" name="gpuModel" value="${gpuModel!}"/></label>
            <label>Об'єм відеопам'яті: <input type="number" name="gpuMemory" min="1" max="64" value="${gpuMemory!}"/></label></p>
            <p><label>Модель оперативної пам'яті: <input type="text" name="ramModel" value="${ramModel!}"/></label>
            <label>Об'єм оперативної пам'яті: <input type="number" name="ramMemory" min="1" max="128" value="${ramMemory!}"/></label></p>
            <p><label>Модель SSD диску: <input type="text" name="ssdModel" value="${ssdModel!}"/></label>
            <label>Об'єм SSD диску: <input type="number" name="ssdMemory" min="1" value="${(ssdMemory??)?then(ssdMemory?c, '')}"/></label></p>
            <p><label>Модель HDD диску: <input type="text" name="hddModel" value="${hddModel!}"/></label>
            <label>Об'єм HDD диску: <input type="number" name="hddMemory" value="${(hddMemory??)?then(hddMemory?c, '')}"/></label></p>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <#if hardware?has_content>
        <div><p>Список співробітників:</p></div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Назва збірки</th>
                <th>Модель процесора</th>
                <th>Модель відеокарти</th>
                <th>Модель дисплею</th>
                <th>Модель оперативної пам'яті</th>
                <th>Модель SSD диску</th>
                <th>Модель HDD диску</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list hardware as assembly>
                <tr>
                    <td>${assembly.id}</td>
                    <th>${assembly.assemblyName}</th>
                    <th>${assembly.cpu.model}</th>
                    <th>${assembly.gpu.model}</th>
                    <th>${assembly.display.model}</th>
                    <th>${assembly.ram.model}</th>
                    <th>${assembly.ssd.model}</th>
                    <th>${assembly.hdd.model}</th>
                    <td><a href="/hardware/edit/${assembly.id}">Edit</a></td>
                    <td><a href="/hardware/delete/${assembly.id}" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h3>Не знайдено жодної збірки.</h3>
    </#if>
</@pad.page>