<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorsHandlers/errorOutput.ftlh" as eO>
<#import "../parts/errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../parts/errorsHandlers/valueConsistentBreaker.ftlh" as vC>

<@pad.page "Shop adding">
    <@eO.errorOutput errorMessage!/>

    <p class="h1">Додати новий магазин:</p>
    <form method="post" action="/shop/add" class="form-group needs-validation w-100" novalidate
          modelAttribute="newShop" onsubmit="return validateForm()">
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="name">Адреса нового магазину:</label>
                <input type="text" class="form-control" name="address" value="${RequestParameters.address!}" required/>
                <div class="invalid-feedback">Адресу нового магазину задано некоректно!</div>
            </div>
        </div>
        <button type="submit" class="btn btn-danger float-right">Додати</button>
    </form>

    <@eB.emptyBreaker inputNames=["address"]/>
    <@vC.valueBreaker inputNames=["address"]/>
</@pad.page>