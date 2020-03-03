<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorsHandlers/errorOutput.ftlh" as error>

<@pad.page "Label adding">
    <@error.errorOutput errorMessage!/>

    <p class="h1">Додати нове найменування:</p>
    <form method="post" action="/label/add" class="form-group needs-validation w-100" novalidate>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="brand">Назва бренду:</label>
                <input type="text" class="form-control" name="brand" value="${RequestParameters.brand!}" required/>
                <div class="invalid-feedback">Назва бренду не може бути пустою!</div>
            </div>
        </div>
        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="model">Назва нової моделі:</label>
                <input type="text" class="form-control" name="model" value="${RequestParameters.model!}" required/>
                <div class="invalid-feedback">Назва нової моделі не може бути пустою!</div>
            </div>
        </div>
        <button type="submit" class="btn btn-danger float-right">Додати</button>
    </form>
</@pad.page>