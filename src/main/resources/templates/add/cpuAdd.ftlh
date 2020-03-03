<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "CPU adding">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Додати новий процесор:</p>
    <form method="post" action="/cpu/add" class="form-group needs-validation w-100" novalidate>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="model">Назва нової моделі:</label>
                <input type="text" class="form-control" name="model"
                       value="${RequestParameters.model!}" required/>
                <div class="invalid-feedback">Назва нової моделі не може бути пустою!</div>
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
</@pad.page>