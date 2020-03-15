<#import "../parts/htmlPad.ftlh" as pad>
<#import "../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../errorsHandlers/valueConsistentBreaker.ftlh" as vC>


<@pad.page "Display adding">
    <@eO.errorOutput errorMessage!/>

    <div>
        <div class="h1">Додати новий дисплей:</div>
        <form method="post" action="/display/add" class="form-group needs-validation w-100" novalidate
              modelAttribute="newDisplay" onsubmit="return validateForm()">
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="model">Назва нової моделі:</label>
                    <input type="text" class="form-control" name="model" value="${RequestParameters.model!}" required/>
                    <div class="invalid-feedback">Назву нової моделі задано некоректно!</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="type">Назва типу:</label>
                    <input type="text" class="form-control" name="type" value="${RequestParameters.type!}" required/>
                    <div class="invalid-feedback">Назву типу задано некоректно!</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="diagonal">Діагональ дисплею:</label>
                    <input type="number" class="form-control" step="0.1" min="0.1" name="diagonal"
                           value="${RequestParameters.diagonal!}" required/>
                    <div class="invalid-feedback">Некоректно задана діагональ!</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="resolution">Розширення дисплею:</label>
                    <input type="text" class="form-control" name="resolution"
                           value="${RequestParameters.resolution!}" required/>
                    <div class="invalid-feedback">Розширення задано некоректно!</div>
                </div>
            </div>
            <button type="submit" class="btn btn-danger mt-1 float-right">Додати</button>
        </form>

        <@eB.emptyBreaker inputNames=["model", "type", "resolution"]/>
        <@vC.valueBreaker inputNames=["model", "type", "resolution"]/>
    </div>
</@pad.page>