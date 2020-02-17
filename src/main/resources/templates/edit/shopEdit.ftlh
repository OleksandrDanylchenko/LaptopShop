<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Shop editing">
    <h2>${errorMessage!}</h2>

    <h1>Зміна адреси магазину:</h1>
    <form method="post" action="/shop/edit/${editShop.id}">
        <label>Адреса магазину:   <input type="text" name="address" value="${editShop.address!}"></label>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>