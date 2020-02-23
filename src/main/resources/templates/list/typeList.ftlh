<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Type">
    <div>
        <p><a href="/type/add">Додати новий тип ноутбуків</a></p>
    </div>

    <div>
        <form method="get" action="/type">
            <label>Знайти тип за назвою: <input type="text" name="name" value="${RequestParameters.name!}"/></label>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <#if types?has_content>
        <div><p>Список типів:</p></div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Назва</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list types as type>
                <tr>
                    <td>${type.id}</td>
                    <td>${type.name}</td>
                    <td><a href="/type/edit/${type.id}">Edit</a></td>
                    <td><a href="/type/delete/${type.id}" onclick="return confirm('Are you sure?')">Delete</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h3>Не знайдено жодного типу.</h3>
    </#if>
</@pad.page>