<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "RAM">
    <div>
        <p><a href="/ram/add">Додати нову оперативну пам'ять</a></p>
    </div>

    <div>
        <p>Знайти оперативну пам'ять за критеріями:</p>
        <form method="get" action="/ram">
            <label>Модель: <input type="text" name="model" value="${RequestParameters.model!}"/></label>
            <label>Обсяг пам'яті: <input type="number" name="memory" min="1" max="128"
                                         value="${RequestParameters.memory!}"/></label>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <#if rams?has_content>
        <div><p>Список процесорів:</p></div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Модель</th>
                <th>Пам'ять(GiB)</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list rams as ram>
                <tr>
                    <td>${ram.id}</td>
                    <td>${ram.model}</td>
                    <td>${ram.memory}</td>
                    <td><a href="/ram/edit/${ram.id}">Edit</a></td>
                    <td><a href="/ram/delete/${ram.id}" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h3>Не знайдено жодної оперативної пам'яті.</h3>
    </#if>
</@pad.page>