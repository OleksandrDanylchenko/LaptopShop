<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Label adding">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Додати нове найменування:</p>
    <form method="post" action="/label/add" class="form-group needs-validation w-100" novalidate>
        <div class="form-row">
            <input type="text" class="form-control mr-1 mt-2" name="brand" value="${RequestParameters.brand!}"
                   placeholder="Новий бренд" required/>
            <div class="invalid-feedback">Назва нового бренду не може бути пустою!</div>
        </div>
        <div class="form-row">
            <input type="text" class="form-control mr-1 mt-2" name="model" value="${RequestParameters.model!}"
                   placeholder="Нова модель" required/>
            <div class="invalid-feedback">Назва нової моделі не може бути пустою!</div>
        </div>
        <button type="submit" class="btn btn-primary my-2 float-right">Додати</button>
    </form>
</@pad.page>