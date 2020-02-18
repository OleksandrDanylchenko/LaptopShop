<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/mainRef.ftlh" as main>

<@pad.page "GPU">
    <@main.mainPageRef/>

    <div>
        <p><a href="/gpu/add">Додати нову відеокарту</a></p>
    </div>

    <div>
        <p>Знайти процесор за критеріями:</p>
        <form method="get" action="/gpu">
            <label>Модель: <input type="text" name="model" value="${model!}"/></label>
            <label>Обсяг пам'яті: <input type="number" name="memory" min="1" max="64" value="${memory!}"/></label>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <#if gpus?has_content>
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
            <#list gpus as gpu>
                <tr>
                    <td>${gpu.id}</td>
                    <td>${gpu.model}</td>
                    <td>${gpu.memory}</td>
                    <td><a href="/gpu/edit/${gpu.id}">Edit</a></td>
                    <td><a href="/gpu/delete/${gpu.id}" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h3>Не знайдено жодної відеокарти.</h3>
    </#if>
</@pad.page>