<#import "../parts/htmlPad.ftlh" as pad>
<#import "../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../errorsHandlers/valueConsistentBreaker.ftlh" as vC>


<@pad.page "Display editing">
    <@eO.errorOutput errorMessage!/>

    <div>
        <div class="h1">Зміна інформації дисплея:</div>
        <form method="post" action="/display/edit/${editDisplay.id}" class="form-group needs-validation w-100" novalidate
              onsubmit="return validateForm()">
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="model">Назва змінюваної моделі:</label>
                    <input type="text" class="form-control" name="model"
                           value="${editDisplay.model!}" required/>
                    <div class="invalid-feedback">Назва змінюваної моделі не може бути пустою!</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="type">Назва типу:</label>
                    <input type="text" class="form-control" name="type"
                           value="${editDisplay.type!}" required/>
                    <div class="invalid-feedback">Назва типу не може бути пустою!</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="diagonal">Діагональ дисплею:</label>
                    <input type="number" class="form-control" step="0.1" min="0.1" name="diagonal"
                           value="${editDisplay.diagonal!}" required/>
                    <div class="invalid-feedback">Некоректно задана діагональ!</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="resolution">Розширення дисплею:</label>
                    <input type="text" class="form-control" name="resolution"
                           value="${editDisplay.resolution!}" required/>
                    <div class="invalid-feedback">Некоректно задано розширення!</div>
                </div>
            </div>
            <button type="submit" class="btn btn-danger float-right">Зберегти</button>
        </form>

        <@eB.emptyBreaker inputIds=["model", "type", "resolution"]/>
        <@vC.valueBreaker inputIds=["model", "type", "resolution"]/>
    </div>
</@pad.page>