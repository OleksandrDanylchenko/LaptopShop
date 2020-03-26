<#import "../parts/htmlPad.ftlh" as pad>
<#import "../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../errorsHandlers/valueConsistentBreaker.ftlh" as vC>

<@pad.page "RAM editing">
    <@eO.errorOutput errorMessage!/>

    <div class="h1">Змінити інформацію про оперативну пам'ять:</div>
    <form method="post" action="/ram/edit/${editRam.id}" class="form-group needs-validation w-100" novalidate
          onsubmit="return validateForm()">
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="model">Назва змінюваної моделі:</label>
                <input type="text" class="form-control" name="model" value="${editRam.model!}" required/>
                <div class="invalid-feedback">Назва змінюваної моделі не може бути пустою!</div>
            </div>
        </div>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="memory">Обсяг пам'яті (GiB):</label>
                <input type="number" min="1" class="form-control" name="memory"
                       value="${(editRam.memory??)?then(editRam.memory?c, '')}" required/>
                <div class="invalid-feedback">Обсяг пам'яті не може бути пустим!</div>
            </div>
        </div>
        <button type="submit" class="btn btn-danger float-right">Зберегти</button>
    </form>

    <@eB.emptyBreaker inputIds=["model"]/>
    <@vC.valueBreaker inputIds=["model"]/>
</@pad.page>