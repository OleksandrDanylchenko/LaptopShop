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

    <div class="row mt-5">
    <#if baskets?has_content>
        <div class="h2">Список кошиків:</div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th class="text-danger" scope="col">ID</th>
                <th scope="col">ID продавця</th>
                <th scope="col">Ім'я продавця</th>
                <th scope="col">Прізвище продавця</th>
                <th scope="col">Адреса магазину</th>
                <th scope="col">ID покупця</th>
                <th scope="col">Ім'я покупця</th>
                <th scope="col">Прізвище покупця</th>
                <th scope="col">Час покупки</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list baskets as basket>
                <tr>
                    <th class="text-danger" scope="row">${basket.id}</th>
                    <#if basket.employee??>
                        <td>${basket.employee.id}</td>
                        <td>${basket.employee.firstName}</td>
                        <td>${basket.employee.secondName}</td>
                        <td>${basket.employee.shop.address}</td>
                    <#else>
                        <td>Видалено</td>
                        <td>Видалено</td>
                        <td>Видалено</td>
                        <td>Видалено</td>
                    </#if>

                    <#if basket.client??>
                        <td>${basket.client.id}</td>
                        <td>${basket.client.firstName}</td>
                        <td>${basket.client.secondName}</td>
                    <#else>
                        <td>Видалено</td>
                        <td>Видалено</td>
                        <td>Видалено</td>
                    </#if>

                    <td>${basket.dateTime.format('yyyy-MM-dd HH:mm')}</td>
                    <td><a href="/basket/edit/${basket.id}">Edit</a></td>
                    <td><a href="/basket/delete/${basket.id}" onclick="return confirm('Ви впевнені?')">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <div class="h3">Не знайдено жодного кошика.</div>
    </#if>
    </div>
</@pad.page>