<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorsHandlers/errorOutput.ftlh" as eO>
<#import "../parts/errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../parts/errorsHandlers/valueConsistentBreaker.ftlh" as vC>

<@pad.page "Type editing">
    <@eO.errorOutput errorMessage!/>

    <p class="h1">Зміна назви типу:</p>
    <form method="post" action="/type/edit/${editType.id}" class="form-group needs-validation w-100" novalidate
          onsubmit="return validateForm()">
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="name">Назва змінюваного типу:</label>
                <input type="text" class="form-control" name="name" value="${editType.name!}" required/>
                <div class="invalid-feedback">Назву змінюваного типу задано некоректно!</div>
            </div>
        </div>
        <button type="submit" class="btn btn-danger float-right">Зберегти</button>
    </form>

    <@eB.emptyBreaker inputNames=["name"]/>
    <@vC.valueBreaker inputNames=["name"]/>
</@pad.page>