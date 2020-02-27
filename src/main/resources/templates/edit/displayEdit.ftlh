<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Display editing">
    <@error.errorOutput errorMessage!/>

    <div>
        <div class="h1">Зміна інформації дисплея:</div>
        <form method="post" action="/display/edit/${editDisplay.id}" class="form-group needs-validation w-100" novalidate>
            <div class="form-row my-2">
                <div class="col">
                    <input type="text" class="form-control mr-1 mt-2" name="model"
                           value="${editDisplay.model!}" placeholder="Нова модель" required/>
                    <div class="invalid-feedback">Назва нової моделі не може бути пустою!</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <input type="text" class="form-control mr-1 mt-2" name="type"
                           value="${editDisplay.type!}" placeholder="Тип" required/>
                    <div class="invalid-feedback">Назва типу не може бути пустою!</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <input type="number" class="form-control mr-1 mt-2" step="0.1" min="0.1" name="diagonal"
                           value="${editDisplay.diagonal!}" placeholder="Діагональ" required/>
                    <div class="invalid-feedback">Некоректно задана діагональ!</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <input type="text" class="form-control mr-1 mt-2" name="resolution"
                           value="${editDisplay.resolution!}" placeholder="Розширення" required/>
                    <div class="invalid-feedback">Некоректно задано розширення!</div>
                </div>
            </div>
            <button type="submit" class="btn btn-danger mt-1 float-right">Зберегти</button>
        </form>
    </div>
</@pad.page>