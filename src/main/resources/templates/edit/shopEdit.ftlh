<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorsHandlers/errorOutput.ftlh" as eO>
<#import "../parts/errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../parts/errorsHandlers/valueConsistentBreaker.ftlh" as vC>

<@pad.page "Shop editing">
    <@eO.errorOutput errorMessage!/>

    <p class="h1">Змінити адресу магазину:</p>
    <form method="post" action="/shop/edit/${editShop.id}" class="form-group needs-validation w-100" novalidate
          onsubmit="return validateForm()">
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="name">Адреса змінюваного магазину:</label>
                <input type="text" class="form-control" name="address" value="${editShop.address!}" required/>
                <div class="invalid-feedback">Адресу змінюваного магазину задано некоректно!</div>
            </div>
        </div>
        <button type="submit" class="btn btn-danger float-right">Зберегти</button>
    </form>

    <@eB.emptyBreaker inputNames=["address"]/>
    <@vC.valueBreaker inputNames=["address"]/>
</@pad.page>