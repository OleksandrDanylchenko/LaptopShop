<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Shop adding">
    <div>
        <h1>Додати новий магазин:</h1>
        <form method="post" action="/shop/add">
            <label>Адреса нового магазину: <input type="text" name="address"/></label>
            <button type="submit">Додати</button>
        </form>
    </div>
</@pad.page>