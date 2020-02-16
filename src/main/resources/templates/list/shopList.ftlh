<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/mainRef.ftlh" as main>

<@pad.page "Shops">
    <@main.mainPageRef/>

    <div>
        <p><a href="/shop/add">Додати новий магазин</a></p>
    </div>

    <#if shops?has_content>
        <div>
            <form method="get" action="/shop">
                <label>Знайти магазин за адресою: <input type="text" name="filterAddress"
                                                         value="${filterAddress!}"/></label>
                <button type="submit">Шукати</button>
            </form>
        </div>

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
    </#if>
</@pad.page>