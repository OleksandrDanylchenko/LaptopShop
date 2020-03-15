<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorsHandlers/errorOutput.ftlh" as eO>
<#import "../parts/errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../parts/errorsHandlers/valueConsistentBreaker.ftlh" as vC>

<@pad.page "HDD adding">
    <@eO.errorOutput errorMessage!/>

    <p class="h1">Додати новий HDD диск:</p>
    <form method="post" action="/hdd/add" class="form-group needs-validation w-100" novalidate
          modelAttribute="newHDD" onsubmit="return validateForm()">
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="model">Назва нової моделі:</label>
                <input type="text" class="form-control" name="model" value="${RequestParameters.model!}" required/>
                <div class="invalid-feedback">Назву нової моделі задано некоректно!</div>
            </div>
        </div>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="memory">Обсяг пам'яті (GiB):</label>
                <input type="number" min="1" class="form-control" name="memory"
                       value="${RequestParameters.memory!}" required/>
                <div class="invalid-feedback">Некоректно задано обсяг пам'яті!</div>
            </div>
        </div>
        <button type="submit" class="btn btn-danger float-right">Додати</button>
    </form>

    <@eB.emptyBreaker inputNames=["model"]/>
    <@vC.valueBreaker inputNames=["model"]/>
</@pad.page>