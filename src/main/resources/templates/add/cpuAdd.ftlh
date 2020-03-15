<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorsHandlers/errorOutput.ftlh" as eO>
<#import "../parts/errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../parts/errorsHandlers/valueConsistentBreaker.ftlh" as vC>

<@pad.page "CPU adding">
    <@eO.errorOutput errorMessage!/>

    <p class="h1">Додати новий процесор:</p>
    <form method="post" action="/cpu/add" class="form-group needs-validation w-100" novalidate
          modelAttribute="newCPU" onsubmit="return validateForm()">
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="model">Назва нової моделі:</label>
                <input type="text" class="form-control" name="model"
                       value="${RequestParameters.model!}" required/>
                <div class="invalid-feedback">Назва нової моделі задано некоректно!</div>
            </div>
        </div>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="frequency">Тактова частота (GhZ):</label>
                <input type="number" step="0.1" min="0.1" class="form-control" name="frequency"
                       value="${RequestParameters.frequency!}" required/>
                <div class="invalid-feedback">Некоректно задано тактову частота!</div>
            </div>
        </div>
        <button type="submit" class="btn btn-danger float-right">Додати</button>
    </form>

    <@eB.emptyBreaker inputNames=["model"]/>
    <@vC.valueBreaker inputNames=["model"]/>
</@pad.page>