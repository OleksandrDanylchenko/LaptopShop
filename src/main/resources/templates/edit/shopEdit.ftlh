<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Shop editing">
    <h2>${errorMessage!}</h2>

    <p class="h1">Зміна адреси магазину:</p>
    <div class="form-group">
        <form method="post" action="/shop/edit/${editShop.id}" class="form-inline">
            <input type="text" class="form-control" name="address" value="${editShop.address!}"
                   placeholder="Адреса магазину" style="width: 450px"/>
            <button type="submit" class="btn btn-primary ml-2">Зберегти</button>
        </form>
    </div>
</@pad.page>