<#import "../parts/htmlPad.ftlh" as pad>


<@pad.page "Employee">
    <div>
        <p><a href="/employee/add">Додати нового співробітника</a></p>
    </div>

    <div>
        <p>Знайти співробітника за критеріями:</p>
        <form method="get" action="/employee">
            <label>Ім'я: <input type="text" name="firstName" value="${RequestParameters.firstName!}"/></label>
            <label>Прізвище: <input type="text" name="secondName" value="${RequestParameters.secondName!}"/></label>
            <label>Адреса магазину: <input type="text" name="shopAddress"
                                           value="${RequestParameters.shopAddress!}"/></label>
            <#assign isActiveChoises = [' ', 'Так', 'Ні']>
            <label>Працюючий:
                <select name="isActive">
                    <#list isActiveChoises as choice>
                        <option>${choice}</option>
                    </#list>
                </select>
            </label>
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
                <th>№ магазину</th>
                <th>Адреса магазину</th>
                <th>Працюючий</th>
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
                    <#if employee.shop??>
                        <td>${employee.shop.id}</td>
                        <td>${employee.shop.address}</td>
                    <#else>
                        <td>Видалено</td>
                        <td>Видалено</td>
                    </#if>
                    <td>${employee.isActive?then("Так", "Ні")}</td>
                    <td><a href="/employee/edit/${employee.id}">Edit</a></td>
                    <td><a href="/employee/delete/${employee.id}" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h3>Не знайдено жодного співробітника.</h3>
    </#if>
</@pad.page>