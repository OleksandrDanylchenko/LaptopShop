<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Label">
    <div>
        <p><a href="/label/add">Додати нове найменування</a></p>
    </div>

    <div>
        <p>Знайти виробника за критеріями:</p>
        <form method="get" action="/label">
            <label>Бренд: <input type="text" name="brand" value="${RequestParameters.brand!}"/></label>
            <label>Модель: <input type="text" name="model" value="${RequestParameters.model!}"/></label>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <#if labels?has_content>
        <div><p>Список найменування:</p></div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Бренд</th>
                <th>Модель</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list labels as label>
                <tr>
                    <td>${label.id}</td>
                    <td>${label.brand}</td>
                    <td>${label.model}</td>
                    <td><a href="/label/edit/${label.id}">Edit</a></td>
                    <td><a href="/label/delete/${label.id}" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h3>Не знайдено жодного найменування.</h3>
    </#if>
</@pad.page>