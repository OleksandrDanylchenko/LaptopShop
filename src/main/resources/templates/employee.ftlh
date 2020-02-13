<#import "parts/htmlPad.ftlh" as pad>

<@pad.page "Employee">
    <div>
        <p>Додати нового співробітника:</p>
        <form method="post" action="/employee">
            <label>Ім'я нового співробітника: <input type="text" name="firstName"/></label>
            <label>Прізвище нового співробітника: <input type="text" name="secondName"/></label>
            <label>Номер магазину:
                <select name="shopId">
                    <#list shopIds as id>
                        <option>${id}</option>
                    </#list>
                </select>
            </label>
            <button type="submit">Додати</button>
        </form>
    </div>

    <div>
        <p>Знайти співробітника за критеріями:</p>
        <form method="get" action="/employee">
            <label>Ім'я: <input type="text" name="firstName" value="${firstName!}"/></label>
            <label>Прізвище: <input type="text" name="secondName" value="${secondName!}"/></label>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <#if employees?has_content>
        <div><p>Список співробітників:</p></div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Ім'я</th>
                <th>Прізвище</th>
                <th>Магазин</th>
                <th>Посада</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list employees as employee>
                <tr>
                    <td>${employee.id}</td>
                    <td>${employee.firstName}</td>
                    <td>${employee.secondName}</td>
                    <td>${employee.shop.id}</td>
                    <td>${employee.postId!}</td>
                    <td><a href="/employee/edit/${employee.id}">Edit</a></td>
                    <td><a href="/employee/delete/${employee.id}" onclick="return confirm('Are you sure?')">Delete</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </#if>
</@pad.page>