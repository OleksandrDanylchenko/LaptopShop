<#import "../parts/htmlPad.ftlh" as pad>
<#import "../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../errorsHandlers/valueConsistentBreaker.ftlh" as vC>

<@pad.page "CPU editing">
    <@eO.errorOutput errorMessage!/>

    <p class="h1">Змінити інформацію про процесор:</p>
    <form method="post" action="/cpu/edit/${editCpu.id}" class="form-group needs-validation w-100" novalidate
          onsubmit="return validateForm()">
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="model">Назва змінюваної моделі:</label>
                <input type="text" class="form-control" name="model"
                       value="${editCpu.model!}" required/>
                <div class="invalid-feedback">Назву змінюваної моделі задано некоректно!</div>
            </div>
        </div>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="frequency">Тактова частота (GhZ):</label>
                <input type="number" step="0.1" min="0.1" class="form-control" name="frequency"
                       value="${editCpu.frequency!}" required/>
                <div class="invalid-feedback">Некоректна частота!</div>
            </div>
        </div>
        <button type="submit" class="btn btn-danger float-right">Зберегти</button>
    </form>

    <@eB.emptyBreaker inputIds=["model"]/>
    <@vC.valueBreaker inputIds=["model"]/>
</@pad.page>