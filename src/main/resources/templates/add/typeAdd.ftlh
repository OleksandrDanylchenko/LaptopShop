<#import "../parts/htmlPad.ftlh" as pad>
<#import "../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../errorsHandlers/valueConsistentBreaker.ftlh" as vC>

<@pad.page "Type adding">
    <@eO.errorOutput errorMessage!/>

    <p class="h1">Додати новий тип ноутбуків:</p>
    <form method="post" action="/type/add" class="form-group needs-validation w-100" novalidate
          modelAttribute="newType" onsubmit="return validateForm()">
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="name">Назва нового типу:</label>
                <input type="text" class="form-control" name="name" value="${RequestParameters.name!}" required/>
                <div class="invalid-feedback">Назву змінюваного типу задано некоректно!</div>
            </div>
        </div>
        <button type="submit" class="btn btn-danger float-right">Додати</button>
    </form>

    <@eB.emptyBreaker inputNames=["name"]/>
    <@vC.valueBreaker inputNames=["name"]/>
</@pad.page>