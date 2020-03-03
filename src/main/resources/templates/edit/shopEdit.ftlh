<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorsHandlers/errorOutput.ftlh" as error>

<@pad.page "Shop editing">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Змінити адресу магазину:</p>
    <form method="post" action="/shop/edit/${editShop.id}" class="form-inline needs-validation w-100" novalidate>
        <label class="sr-only" for="shopAddress">Адреса</label>
        <input type="text" class="form-control flex-grow-1" name="address" value="${editShop.address!}"
               placeholder="Адреса магазину" required/>
        <div class="invalid-feedback">Адреса магазину не може бути пустою!</div>
        <button type="submit" class="btn btn-danger ml-md-2 my-2">Зберегти</button>
    </form>
</@pad.page>