<#import "../parts/htmlPad.ftlh" as pad>


<@pad.page "Hardware">
    <div>
        <p><a href="/hardware/add">Додати нову збірку</a></p>
    </div>

    <div>
        <p>Знайти збірку за критеріями:</p>
        <form method="get" action="/hardware">
            <label>Назва збірки: <input type="text" name="assemblyName"
                                        value="${RequestParameters.assemblyName!}"/></label>
            <p><label>Модель дисплею: <input type="text" name="displayModel"
                                             value="${RequestParameters.displayModel!}"/></label>
                <label>Діагональ дисплею: <input type="number" name="displayDiagonal" step="0.1" min="0.1"
                                                 value="${RequestParameters.displayDiagonal!}"/></label>
                <label>Розширення дисплею: <input type="text" name="displayResolution"
                                                  value="${RequestParameters.displayResolution!}"/></label>
                <label>Тип дисплею: <input type="text" name="displayType"
                                           value="${RequestParameters.displayType!}"/></label></p>
            <p><label>Модель процесору: <input type="text" name="cpuModel"
                                               value="${RequestParameters.cpuModel!}"/></label>
                <label>Частота процесору: <input type="number" name="cpuFrequency" step="0.1" min="0.1" max="5"
                                                 value="${RequestParameters.cpuFrequency!}"/></label></p>
            <p><label>Модель відеокарти: <input type="text" name="gpuModel"
                                                value="${RequestParameters.gpuModel!}"/></label>
                <label>Об'єм відеопам'яті: <input type="number" name="gpuMemory" min="1" max="64"
                                                  value="${RequestParameters.gpuMemory!}"/></label></p>
            <p><label>Модель оперативної пам'яті: <input type="text" name="ramModel"
                                                         value="${RequestParameters.ramModel!}"/></label>
                <label>Об'єм оперативної пам'яті: <input type="number" name="ramMemory" min="1" max="128"
                                                         value="${RequestParameters.ramMemory!}"/></label></p>
            <p><label>Модель SSD диску: <input type="text" name="ssdModel"
                                               value="${RequestParameters.ssdModel!}"/></label>
                <label>Об'єм SSD диску: <input type="number" name="ssdMemory" min="1"
                                               value="${RequestParameters.ssdMemory!}"/></label></p>
            <p><label>Модель HDD диску: <input type="text" name="hddModel"
                                               value="${RequestParameters.hddModel!}"/></label>
                <label>Об'єм HDD диску: <input type="number" name="hddMemory" value="${RequestParameters.hddMemory!}"/></label>
            </p>
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
                    <th>${assembly.cpu.model!"Видалено"}</th>
                    <th>${assembly.gpu.model!"Видалено"}</th>
                    <th>${assembly.display.model!"Видалено"}</th>
                    <th>${assembly.ram.model!"Видалено"}</th>
                    <th>${assembly.ssd.model!"Видалено"}</th>
                    <th>${assembly.hdd.model!"Видалено"}</th>
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