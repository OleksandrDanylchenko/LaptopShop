<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/mainRef.ftlh" as main>

<@pad.page "SSD">
    <@main.mainPageRef/>

    <div>
        <p><a href="/ssd/add">Додати новий SSD диск</a></p>
    </div>

    <div>
        <p>Знайти процесор за критеріями:</p>
        <form method="get" action="/ssd">
            <label>Модель: <input type="text" name="model" value="${model!}"/></label>
            <label>Обсяг пам'яті: <input type="number" name="memory" min="1" value="${(memory??)?then(memory?c, '')}"/></label>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <#if ssds?has_content>
        <div><p>Список SSD дисків:</p></div>
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
            <#list ssds as ssd>
                <tr>
                    <td>${ssd.id}</td>
                    <td>${ssd.model}</td>
                    <td>${ssd.memory}</td>
                    <td><a href="/ssd/edit/${ssd.id}">Edit</a></td>
                    <td><a href="/ssd/delete/${ssd.id}" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h3>Не знайдено жодного SSD диску.</h3>
    </#if>
</@pad.page>