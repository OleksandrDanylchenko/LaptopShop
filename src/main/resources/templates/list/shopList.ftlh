<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Shops">
    <div>
        <p><a href="/shop/add">Додати новий магазин</a></p>
    </div>

    <div>
        <form method="get" action="/shop">
            <label>Знайти магазин за адресою: <input type="text" name="address"
                                                     value="${RequestParameters.address!}"/></label>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <#if shops?has_content>
        <div><p>Список магазинів:</p></div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Адреса</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list shops as shop>
                <tr>
                    <td>${shop.id}</td>
                    <td>${shop.address}</td>
                    <td><a href="/shop/edit/${shop.id}">Edit</a></td>
                    <td><a href="/shop/delete/${shop.id}" onclick="return confirm('Are you sure?')">Delete</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h3>Не знайдено жодного магазину.</h3>
    </#if>
</@pad.page>