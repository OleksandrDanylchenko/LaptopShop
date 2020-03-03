<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorsHandlers/errorOutput.ftlh" as error>

<@pad.page "GPU adding">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Додати нову відеокарту:</p>
    <form method="post" action="/gpu/add" class="form-group needs-validation w-100" novalidate>
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
                <label class="d-inline ml-2" for="memory">Обсяг пам'яті (GiB):</label>
                <input type="number" min="1" class="form-control" name="memory"
                       value="${RequestParameters.memory!}" required/>
                <div class="invalid-feedback">Некоректно задано обсяг пам'яті!</div>
            </div>
        </div>
        <button type="submit" class="btn btn-danger float-right">Додати</button>
    </form>
</@pad.page>