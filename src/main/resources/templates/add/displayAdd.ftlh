<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Display adding">
    <@error.errorOutput errorMessage!/>

    <div>
        <div class="h1">Додати новий дисплей:</div>
        <form method="post" action="/display/add" class="form-group needs-validation w-100" novalidate>
            <div class="form-row my-2">
                <div class="col">
                    <input type="text" class="form-control mr-1 mt-2" name="model"
                           value="${RequestParameters.model!}" placeholder="Нова модель" required/>
                    <div class="invalid-feedback">Назва нової моделі не може бути пустою!</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <input type="text" class="form-control mr-1 mt-2" name="type"
                           value="${RequestParameters.type!}" placeholder="Тип" required/>
                    <div class="invalid-feedback">Назва типу не може бути пустою!</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <input type="number" class="form-control mr-1 mt-2" step="0.1" min="0.1" name="diagonal"
                           value="${RequestParameters.diagonal!}" placeholder="Діагональ" required/>
                    <div class="invalid-feedback">Некоректно задана діагональ!</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <input type="text" class="form-control mr-1 mt-2" name="resolution"
                           value="${RequestParameters.resolution!}" placeholder="Розширення" required/>
                    <div class="invalid-feedback">Некоректно задано розширення!</div>
                </div>
            </div>
            <button type="submit" class="btn btn-danger mt-1 float-right">Додати</button>
        </form>
    </div>
</@pad.page>