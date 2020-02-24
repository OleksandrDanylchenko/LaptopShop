<#import "../parts/htmlPad.ftlh" as pad>
<#setting locale="uk">

<@pad.page "Basket">
    <div>
        <p><a href="/basket/add">Додати новий кошик</a></p>
    </div>

    <div>
        <p>Знайти кошик за критеріями:</p>
        <form method="get" action="/basket">
            <label>ID продавця: <input type="number" step="1" min="1" name="employeeId"
                                       value="${RequestParameters.employeeId!}"/></label>
            <label>Ім'я продавця: <input type="text" name="employeeFirstName"
                                         value="${RequestParameters.employeeFirstName!}"/></label>
            <label>Прізвище продавця: <input type="text" name="employeeSecondName"
                                             value="${RequestParameters.employeeSecondName!}"/></label>
            <label>Адреса магазину: <input type="text" name="employeeShopAddress"
                                           value="${RequestParameters.employeeShopAddress!}"/></label>
            <p><label>ID покупця: <input type="number" step="1" min="1" name="clientId"
                                         value="${RequestParameters.clientId!}"/></label>
                <label>Ім'я покупця: <input type="text" name="clientFirstName"
                                            value="${RequestParameters.clientFirstName!}"/></label>
                <label>Прізвище покупця: <input type="text" name="clientSecondName"
                                                value="${RequestParameters.clientSecondName!}"/></label></p>
            <p><label>Час покупки: <input type="datetime-local" name="dateTimeStr"
                                          value="${RequestParameters.dateTimeStr!}"/></label></p>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <#if baskets?has_content>
        <div><p>Список співробітників:</p></div>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>ID продавця</th>
                <th>Ім'я продавця</th>
                <th>Прізвище продавця</th>
                <th>Адреса магазину</th>
                <th>ID покупця</th>
                <th>Ім'я покупця</th>
                <th>Прізвище покупця</th>
                <th>Час покупки</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list baskets as basket>
                <tr>
                    <td>${basket.id}</td>
                    <td>${basket.employee.id!"Видалено"}</td>
                    <td>${basket.employee.firstName!"Видалено"}</td>
                    <td>${basket.employee.secondName!"Видалено"}</td>
                    <td>${basket.employee.shop.address!"Видалено"}</td>
                    <td>${basket.client.id!"Видалено"}</td>
                    <td>${basket.client.firstName!"Видалено"}</td>
                    <td>${basket.client.secondName!"Видалено"}</td>
                    <td>${basket.dateTime.format('yyyy-MM-dd HH:mm')}</td>
                    <td><a href="/basket/edit/${basket.id}">Edit</a></td>
                    <td><a href="/basket/delete/${basket.id}" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h3>Не знайдено жодного кошика.</h3>
    </#if>
</@pad.page>