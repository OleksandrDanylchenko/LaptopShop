<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "CPU">

    <div>
        <p><a href="/cpu/add">Додати новий процесор</a></p>
    </div>

    <div>
        <p>Знайти процесор за критеріями:</p>
        <form method="get" action="/cpu">
            <label>Модель: <input type="text" name="model" value="${RequestParameters.model!}"/></label>
            <label>Частота: <input type="number" step="0.1" name="frequency" min="0.1" max="5"
                                   value="${RequestParameters.frequency!}"/></label>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <#if cpus?has_content>
        <div><p>Список процесорів:</p></div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Модель</th>
                <th>Частота(GHz)</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list cpus as cpu>
                <tr>
                    <td>${cpu.id}</td>
                    <td>${cpu.model}</td>
                    <td>${cpu.frequency}</td>
                    <td><a href="/cpu/edit/${cpu.id}">Edit</a></td>
                    <td><a href="/cpu/delete/${cpu.id}" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h3>Не знайдено жодного процесору.</h3>
    </#if>
</@pad.page>