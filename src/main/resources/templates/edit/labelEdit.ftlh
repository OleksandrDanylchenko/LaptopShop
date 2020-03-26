<#import "../parts/htmlPad.ftlh" as pad>
<#import "../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../errorsHandlers/valueConsistentBreaker.ftlh" as vC>

<@pad.page "Label editing">
    <@eO.errorOutput errorMessage!/>

    <p class="h1">Змінити найменування:</p>
    <form method="post" action="/label/edit/${editLabel.id}" class="form-group needs-validation w-100" novalidate
          onsubmit="return validateForm()">
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="brand">Назва бренду:</label>
                <input type="text" class="form-control" name="brand" value="${editLabel.brand!}" required/>
                <div class="invalid-feedback">Назва бренду задано некоректно!</div>
            </div>
        </div>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="model">Назва змінюваної моделі:</label>
                <input type="text" class="form-control" name="model" value="${editLabel.model!}" required/>
                <div class="invalid-feedback">Назву змінюваної моделі задано некоректно!</div>
            </div>
        </div>
        <button type="submit" class="btn btn-danger float-right">Зберегти</button>
    </form>

    <@eB.emptyBreaker inputIds=["brand", "model"]/>
    <@vC.valueBreaker inputIds=["brand", "model"]/>
</@pad.page>