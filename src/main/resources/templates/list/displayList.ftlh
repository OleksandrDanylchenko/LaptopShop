<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/mainRef.ftlh" as main>

<@pad.page "Display">
    <@main.mainPageRef/>

    <div>
        <p><a href="/display/add">Додати новий дисплей</a></p>
    </div>

    <div>
        <p>Знайти дисплей за критеріями:</p>
        <form method="get" action="/display">
            <label>Тип: <input type="text" name="type" value="${type!}"/></label>
            <label>Діагональ: <input type="number" step="0.1" name="diagonal" min="0.1" value="${diagonal!}"/></label>
            <label>Розширення: <input type="text" name="resolution" value="${resolution!}"/></label>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <#if displays?has_content>
        <div><p>Список співробітників:</p></div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Тип</th>
                <th>Діагональ</th>
                <th>Розширення</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list displays as display>
                <tr>
                    <td>${display.id}</td>
                    <td>${display.type}</td>
                    <td>${display.diagonal}</td>
                    <td>${display.resolution}</td>
                    <td><a href="/display/edit/${display.id}">Edit</a></td>
                    <td><a href="/display/delete/${display.id}" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h3>Не знайдено жодного дисплею.</h3>
    </#if>
</@pad.page>