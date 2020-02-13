<#import "parts/htmlPad.ftlh" as pad>

<@pad.page "Shops">
    <div>
        <p>Додати новий магазин:</p>
        <form method="post" action="/shop">
            <label>Адреса нового магазину: <input type="text" name="address"/></label>
            <button type="submit">Додати</button>
        </form>
    </div>

    <div>
        <p>Знайти магазин за адресою:</p>
        <form method="get" action="/shop">
            <label>Адреса шуканого магазину: <input type="text" name="filterAddress" value="${filterAddress!}"/></label>
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
    </#if>
</@pad.page>