<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Shop adding">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Додати новий магазин:</p>
    <form method="post" action="/shop/add" class="form-inline needs-validation w-100" novalidate>
        <label class="sr-only" for="shopAddress">Адреса</label>
        <input type="text" class="form-control flex-grow-1" name="address" value="${RequestParameters.address!}"
               placeholder="Адреса нового магазину" required/>
        <button type="submit" class="btn btn-primary ml-sm-2 my-2">Додати</button>
        <div class="invalid-feedback">Адреса нового магазину не може бути пустою!</div>
    </form>

</@pad.page>