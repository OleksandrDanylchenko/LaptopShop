<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Shop adding">
    <h2>${errorMessage!}</h2>

    <p class="h1">Додати новий магазин:</p>
    <div class="form-group">
        <form method="post" action="/shop/add" class="form-inline">
            <input type="text" class="form-control" name="address" value="${RequestParameters.address!}"
                   placeholder="Адреса нового магазину" style="width: 450px"/>
            <button type="submit" class="btn btn-primary ml-2">Додати</button>
        </form>
    </div>
</@pad.page>