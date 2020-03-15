<#import "../parts/htmlPad.ftlh" as pad>
<#import "../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../errorsHandlers/valueConsistentBreaker.ftlh" as vC>

<@pad.page "SSD editing">
    <@eO.errorOutput errorMessage!/>

    <p class="h1">Змінити інформацію про SSD диск:</p>
    <form method="post" action="/ssd/edit/${editSSD.id}" class="form-group needs-validation w-100" novalidate
          onsubmit="return validateForm()">
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="model">Назва моделі:</label>
                <input type="text" class="form-control" name="model" value="${editSSD.model!}" required/>
                <div class="invalid-feedback">Назву змінюваної моделі задано некоректно!</div>
            </div>
        </div>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="memory">Обсяг пам'яті (GiB):</label>
                <input type="number" min="1" class="form-control" name="memory"
                       value="${(editSSD.memory??)?then(editSSD.memory?c, '')}" required/>
                <div class="invalid-feedback">Обсяг пам'яті не може бути пустим!</div>
            </div>
        </div>
        <button type="submit" class="btn btn-danger float-right">Зберегти</button>
    </form>

    <@eB.emptyBreaker inputNames=["model"]/>
    <@vC.valueBreaker inputNames=["model"]/>
</@pad.page>