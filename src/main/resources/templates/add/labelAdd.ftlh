<#import "../parts/htmlPad.ftlh" as pad>
<#import "../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../errorsHandlers/valueConsistentBreaker.ftlh" as vC>

<@pad.page "Label adding">
    <@eO.errorOutput errorMessage!/>

    <p class="h1">Додати нове найменування:</p>
    <form method="post" action="/label/add" class="form-group needs-validation w-100" novalidate
          modelAttribute="newLabel" onsubmit="return validateForm()">
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="brand">Назва бренду:</label>
                <input type="text" class="form-control" name="brand" value="${RequestParameters.brand!}" required/>
                <div class="invalid-feedback">Назва бренду задано некоректно!</div>
            </div>
        </div>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="model">Назва нової моделі:</label>
                <input type="text" class="form-control" name="model" value="${RequestParameters.model!}" required/>
                <div class="invalid-feedback">Назву нової моделі задано некоректно!</div>
            </div>
        </div>
        <button type="submit" class="btn btn-danger float-right">Додати</button>
    </form>

    <@eB.emptyBreaker inputNames=["brand", "model"]/>
    <@vC.valueBreaker inputNames=["brand", "model"]/>
</@pad.page>