<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "GPU adding">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Додати новий HDD диск:</p>
    <form method="post" action="/hdd/add" class="form-group needs-validation w-100" novalidate>
        <div class="form-row">
            <input type="text" class="form-control mr-1 mt-2" name="model"
                   value="${RequestParameters.model!}" placeholder="Нова модель" required/>
            <div class="invalid-feedback">Назва нової моделі не може бути пустою!</div>
        </div>
        <div class="form-row">
            <input type="number" min="1" class="form-control mr-1 mt-2" name="memory"
                   value="${RequestParameters.memory!}" placeholder="Пам'ять(GiB)" required/>
            <div class="invalid-feedback">Обсяг пам'яті не може бути пустим!</div>
        </div>
        <button type="submit" class="btn btn-primary my-2 float-right">Додати</button>
    </form>
</@pad.page>