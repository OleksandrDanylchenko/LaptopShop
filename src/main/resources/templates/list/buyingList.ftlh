<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Buying">
    <div>
        <p><a href="/buying/add">Додати нову покупку</a></p>
    </div>

    <div>
        <p>Знайти покупку за критеріями:</p>
        <form method="get" action="/buying">
            <label>ID кошику: <input type="number" step="1" min="1" name="basketId"
                                     value="${RequestParameters.basketId!}"/></label>
            <label>Модель ноутбуку: <input type="text" name="laptopModel"
                                           value="${RequestParameters.laptopModel!}"/></label>
            <label>Загальна ціна: <input type="number" step="10" min="1" name="totalPrice"
                                         value="${RequestParameters.totalPrice!}"/></label>
            <label>Час покупки: <input type="datetime-local" name="dateTimeStr"
                                       value="${RequestParameters.dateTimeStr!}"/></label>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <#if buyings?has_content>
        <div><p>Список покупок:</p></div>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Загальна ціна</th>
                <th>ID кошика</th>
                <th>Час покупки</th>
                <th>ID ноутбуку</th>
                <th>Модель ноутбуку</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list buyings as buying>
                <tr>
                    <td>${buying.id}</td>
                    <td>${buying.totalPrice}</td>
                    <td>${buying.basket.id!"Видалено"}</td>
                    <td>${buying.basket.dateTime.format('yyyy-MM-dd HH:mm')!"Видалено"}</td>
                    <td>${buying.laptop.id!"Видалено"}</td>
                    <td>${buying.laptop.label.model!"Видалено"}</td>
                    <td><a href="/buying/edit/${buying.id}">Edit</a></td>
                    <td><a href="/buying/delete/${buying.id}" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h3>Не знайдено жодної покупки.</h3>
    </#if>
</@pad.page>